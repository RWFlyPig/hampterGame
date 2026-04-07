//Basic Game Application
// Basic Object, Image, Movement
// Threaded

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

//*******************************************************************************

public class BasicGameApp implements Runnable, KeyListener {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too


    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;


    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public boolean pressingKey;
    public boolean firstAsteroidCrash;
    public boolean firstCrash;
    public boolean pause;
    public boolean firstExit;
    public SoundFile song;

    public BufferStrategy bufferStrategy;


    BackGround Space;
    Image SpaceImage;
    BackGround Space2;
    Image SpaceImage2;

    Hamster hampter;
    Image hampterImage;
    Hamster hampterSteroids;
    Image hampterSteroidsImage;

    Food sunflower;
    Image sunflowerImage;
    Food sunflower2;



    Asteroid [] asteroids;
    Image asteroidImage;

    Image explosion = Toolkit.getDefaultToolkit().getImage("explosion.png");




    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();//creates a threads & starts up the code in the run( ) method
        Asteroid Asteroid = new Asteroid();
    }

    public Asteroid(){
        asteroids = new Asteroid[15];
        for(int i=0;i<asteroids.length;i++){
            Asteroid myasteroid = new Asteroid();
            asteroids [i] = myasteroid;
        }

    }


    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.

    public BasicGameApp() { // BasicGameApp constructor

        setUpGraphics();
        firstCrash = true;
        firstAsteroidCrash = true;
        hampter = new Hamster("hampter1.png", 300, 300, 0.75);
        hampterImage = Toolkit.getDefaultToolkit().getImage("hampter1.png");
        sunflower = new Food("sunflowerseed.png", 400, 400);
        sunflowerImage = Toolkit.getDefaultToolkit().getImage("sunflowerseed.png");
        sunflower2 = new Food("sunflower2.png", 100, 400);
        hampterSteroids = new Hamster("hampteronsteroids.png", 0, 0, 0);
        hampterSteroidsImage = Toolkit.getDefaultToolkit().getImage("hampteronsteroids.png");
        Space = new BackGround("space1",10,0);
        SpaceImage = Toolkit.getDefaultToolkit().getImage("space.jpg");
        Space2 = new BackGround("space2",1010,0);
        SpaceImage2 = Toolkit.getDefaultToolkit().getImage("space.jpg");
/*
        spaceImage1 = Toolkit.getDefaultToolkit().getImage("space.jpg");
*/
        song = new SoundFile("Woe Is Me!.wav");


        Asteroid[] field = new Asteroid[8];
        for (int i = 0; i < 8; i++) {
            field[i] = new Asteroid();
        }

/*        asteroid1 = new Asteroid("asteroid.png",100,300);
        asteroidImage1 = Toolkit.getDefaultToolkit().getImage("asteroid.png");
        asteroid2 = new Asteroid("asteroid.png",200,200);
        asteroidImage2 = Toolkit.getDefaultToolkit().getImage("asteroid.png");
        asteroid3 = new Asteroid("asteroid.png",400,600);
        asteroidImage3 = Toolkit.getDefaultToolkit().getImage("asteroid.png");
        asteroid4 = new Asteroid("asteroid.png",500,800);
        asteroidImage4 = Toolkit.getDefaultToolkit().getImage("asteroid.png");
        asteroid5 = new Asteroid("asteroid.png",300,300);
        asteroidImage5 = Toolkit.getDefaultToolkit().getImage("asteroid.png");
        asteroid6 = new Asteroid("asteroid.png",500,400);
        asteroidImage6 = Toolkit.getDefaultToolkit().getImage("asteroid.png");
        asteroid7 = new Asteroid("asteroid.png",600,100);
        asteroidImage7 = Toolkit.getDefaultToolkit().getImage("asteroid.png");*/

        run();


    } // end BasicGameApp constructor

//*******************************************************************************
//User Method Section
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        //for the moment we will loop things forever.
        while (true) {
            moveThings();  //move all the game objects
            render();  // paint the graphics
            pause(30); // sleep for 10 ms
            song.loop();
        }
    }

    public void jump() {
        int gravity = 10;
        hampter.dy = -30;
        if (hampter.ypos == 200) {
            hampter.dy = 0;
        }

    }

    public void moveThings() {
        //hampter.wrap();
        sunflower.wrap();
        sunflower2.wrap();
        hampter.wrap();
        checkCrash();
        Space.wrap();
        Space2.wrap();

        if (pressingKey) {
            hampter.move();
        }
        if (hampter.ypos == 500) {
            hampter.move();
            hampter.dy = 0;
        }
    }

    public void checkCrash() {
        if (hampter.rect.intersects(sunflower.rect) && firstCrash == true) {
            firstCrash = false;
            sunflower.width = 0;
            sunflower.height = 0;
            hampter.width += 50;
            hampter.height += 50;
            hampter.isAlive = true;
            sunflower.isAlive = false;
            hampter.onSteroids = true;
        }
        if (hampter.rect.intersects(sunflower2.rect)) {
            sunflower2.isAlive = false;
            sunflower.isAlive = true;
            sunflower2.width = 0;
            sunflower2.height = 0;
            hampter.width += 50;
            hampter.height += 50;
            hampter.isAlive = true;
            hampter.onSteroids = false;
        }
    }

    /*public void checkCrashAsteroid() {

        if (hampter.rect.intersects(asteroid1.rect) && firstAsteroidCrash == true) {
            asteroid1.dx = hampter.dx/2;
            asteroid1.dy = hampter.dy/2;
            asteroid1.bounce();
            firstAsteroidCrash = false;
        }
        if (!hampter.rect.intersects(asteroid1.rect)) {
            firstAsteroidCrash = true;
        }
        if (hampter.rect.intersects(asteroid2.rect) && firstAsteroidCrash == true) {
            asteroid2.dx = hampter.dx/2;
            asteroid2.dy = hampter.dy/2;
            asteroid2.bounce();
            firstAsteroidCrash = false;
        }
        if (!hampter.rect.intersects(asteroid2.rect)) {
            firstAsteroidCrash = true;
        }
        if (hampter.rect.intersects(asteroid3.rect) && firstAsteroidCrash == true) {
            asteroid3.dx = hampter.dx/2;
            asteroid3.dy = hampter.dy/2;
            asteroid3.bounce();
            firstAsteroidCrash = false;
        }
        if (!hampter.rect.intersects(asteroid3.rect)) {
            firstAsteroidCrash = true;
        }
        if (hampter.rect.intersects(asteroid4.rect) && firstAsteroidCrash == true) {
            asteroid4.dx = hampter.dx/2;
            asteroid4.dy = hampter.dy/2;
            asteroid4.bounce();
            firstAsteroidCrash = false;
        }
        if (!hampter.rect.intersects(asteroid4.rect)) {
            firstAsteroidCrash = true;
        }
        if (hampter.rect.intersects(asteroid5.rect) && firstAsteroidCrash == true) {
            asteroid5.dx = hampter.dx / 2;
            asteroid5.dy = hampter.dy / 2;
            asteroid5.bounce();
            firstAsteroidCrash = false;
        }

        if (hampter.rect.intersects(asteroid6.rect) && firstAsteroidCrash == true) {
            asteroid6.dx = hampter.dx / 2;
            asteroid6.dy = hampter.dy / 2;
            asteroid6.bounce();
            firstAsteroidCrash = false;
        }

        if (hampter.rect.intersects(asteroid7.rect) && firstAsteroidCrash == true) {
            asteroid7.dx = hampter.dx / 2;
            asteroid7.dy = hampter.dy / 2;
            asteroid7.bounce();
            firstAsteroidCrash = false;
        }
    }*/
    //Paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(SpaceImage, Space.xpos ,Space.ypos, Space.width, Space.height, null);
        g.drawImage(SpaceImage2, Space2.xpos, Space2.ypos, Space2.width, Space2.height,null);
        //draw the image
        if (hampter.onSteroids == true) {
            g.drawImage(hampterSteroidsImage, hampter.xpos, hampter.ypos, 200, 200, null);
            hampter.dx = 15;
            hampter.dy = 15;
            firstCrash = false;
        } else {
            g.drawImage(hampterImage, hampter.xpos, hampter.ypos, hampter.width, hampter.height, null);
        }
        g.drawImage(sunflowerImage, sunflower.xpos, sunflower.ypos, sunflower.width, sunflower.height, null);
        if (sunflower.isAlive == false) {
            g.drawImage(sunflowerImage, sunflower2.xpos, sunflower2.ypos, sunflower2.width, sunflower2.height, null);
        }
        if (sunflower2.isAlive == false) {
            g.drawImage(sunflowerImage, sunflower.xpos, sunflower.ypos, sunflower.width, sunflower.height, null);
        }
        g.dispose();
        bufferStrategy.show();

        for (int i = 0; i < asteroids.length; i++) {
            g.drawImage(asteroidImage, (int) (Math.random() * 1000), (int) (Math.random() * 700), 50, 50, null);
        }
    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();

        canvas.addKeyListener(this);

        System.out.println("DONE graphic setup");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //pressingKey = true;
        System.out.println(e.getKeyCode()); //space bar jump
        if (e.getKeyCode() == 32) {
            hampter.dx = 2;
            hampter.dy = -50;
        }
        if (e.getKeyCode() == 27) {
            hampter.dx = 0;
            hampter.dy = 0;
            sunflower.dx = 0;
            sunflower.dy = 0;
            sunflower2.dx = 0;
            sunflower2.dy = 0;
            song.pause();
            pause = true;
            Space.dx=0;
            Space2.dx=0;
            firstExit = true;
        }
        if (e.getKeyCode() == 27 && firstExit == true) {
            hampter.wrap();
            sunflower.wrap();
            sunflower2.wrap();
            song.resume();
            pause = false;
            Space.wrap();
            Space2.wrap();
        }
        if (pause == true && e.getKeyCode() == 87) {
            sunflower.dy = -5;
        }
        if (pause == true && e.getKeyCode() == 68) {
            sunflower.dx = 5;
        }
        if (pause == true && e.getKeyCode() == 65) {
            sunflower.dx = -5;
        }
        if (pause == true && e.getKeyCode() == 83) {
            sunflower.dy = 5;
        }
        if (e.getKeyCode() == 61) {
            hampter.width += 5;
            hampter.height += 5;
        }
        if (e.getKeyCode() == 45) {
            hampter.width -= 5;
            hampter.height -= 5;
        }

        if (e.getKeyCode() == 10) {
            sunflower.dx = (int) (Math.random() * 10);
            sunflower.dy = (int) (Math.random() * 10);
            sunflower2.dx = (int) (Math.random() * 10);
            sunflower2.dy = (int) (Math.random() * 10);
        }
        if (pause == true && e.getKeyCode() == 82) {
            pause = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //pressingKey = false;
        if (e.getKeyCode() == 32) {
            hampter.dx = 8;
            hampter.dy = 15;
        }
        if (pause == true && e.getKeyCode() == 87) {
            sunflower.dx = 0;
            sunflower.dy = 0;
        }
        if (pause == true && e.getKeyCode() == 68) {
            sunflower.dx = 0;
            sunflower.dy = 0;
        }
        if (pause == true && e.getKeyCode() == 65) {
            sunflower.dx = 0;
            sunflower.dy = 0;
        }
        if (pause == true && e.getKeyCode() == 83) {
            sunflower.dy = 0;
            sunflower.dx = 0;
        }
    }
}

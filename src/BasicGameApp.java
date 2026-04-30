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
    public boolean GameOver;

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
    Image asteroidsImage;
    Image explosionImage;

    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();//creates a threads & starts up the code in the run( ) method
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
        Space = new BackGround("space1",0,0);
        SpaceImage = Toolkit.getDefaultToolkit().getImage("space.jpg");
        Space2 = new BackGround("space2",1000,0);
        SpaceImage2 = Toolkit.getDefaultToolkit().getImage("space.jpg");

        asteroidsImage = Toolkit.getDefaultToolkit().getImage("asteroid.png");
        explosionImage = Toolkit.getDefaultToolkit().getImage("explosion.png");
        GameOver = false;


        song = new SoundFile("Woe Is Me!.wav");

        asteroids = new Asteroid[6];
        for (int i = 0; i < asteroids.length; i++) {
            asteroids[i] = new Asteroid();
            asteroids[i].width = 50;
            asteroids[i].height = 50;
            asteroids[i].dx = -5;
            asteroids[i].xpos = 1200;
            asteroids[i].ypos = i *((int)(Math.random()*200)+100);

        }



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

    public void reset(){
        GameOver = false;
        firstCrash = true;
        hampter.xpos = 300;
        hampter.ypos = 300;
        hampter.dx = 0;
        hampter.dy = 0;
        hampter.width = 80;
        hampter.height = 80;
        hampter.onSteroids = false;
        Space.dx = -10;
        Space2.dx = -10;
        sunflower.dx = ((int)(Math.random() * 20) + 5);
        sunflower.dy = ((int)(Math.random() * 20) + 5);
        sunflower.xpos = 400;
        sunflower.ypos = 400;
        sunflower.width = 50;
        sunflower.height = 50;
        sunflower.isAlive = true;

        sunflower2.dx = ((int)(Math.random() * 20) + 5);
        sunflower2.dy = ((int)(Math.random() * 20) + 5);
        sunflower2.xpos = 100;
        sunflower2.ypos = 400;
        sunflower2.width = 50;
        sunflower2.height = 50;
        sunflower2.isAlive = true;

        for (int i = 0; i < asteroids.length; i++) {
            asteroids[i].xpos = 1200;
            asteroids[i].ypos = i * ((int)(Math.random() * 200) + 100);
            asteroids[i].dx = -5;
        }

        //Resume music
        song.resume();
    }

    public void moveThings() {
        //hampter.wrap();

        sunflower.wrap();
        sunflower2.wrap();
        hampter.wrap();
        checkCrash();
        Space.wrap();
        Space2.wrap();
        for (int i = 0; i < asteroids.length; i++) {
            asteroids[i].wrap();
        }

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
            hampter.width -= 50;
            hampter.height -= 50;
            hampter.isAlive = true;
            hampter.onSteroids = false;
        }
        for (int i = 0; i < asteroids.length; i++) {
            if (hampter.rect.intersects(asteroids[i].rect)) {
                GameOver = true;
                song.pause();
            }
        }
    }

    //Paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(SpaceImage, Space.xpos ,Space.ypos, Space.width, Space.height, null);
        g.drawImage(SpaceImage2, Space2.xpos, Space2.ypos, Space2.width, Space2.height,null);
        if (GameOver == true) {
            hampter.dx=0;
            hampter.dy=0;
            Space.dx=0;
            Space2.dx = 0;
            sunflower.dx = 0;
            sunflower.dy = 0;
            sunflower2.dx = 0;
            sunflower2.dy = 0;
            hampterSteroids.dx =0;
            hampterSteroids.dy=0;
            for (int i = 0; i < asteroids.length; i++) {
                asteroids[i].dx = 0;
            }
            g.drawImage(explosionImage, hampter.xpos - 20, hampter.ypos - 20, 150, 150, null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 80));
            g.drawString("GAME OVER", 280, 350);
        }

        for (int i = 0; i < asteroids.length; i++) {
            g.drawImage(asteroidsImage, asteroids[i].xpos, asteroids[i].ypos, asteroids[i].width, asteroids[i].height, null);
            if(asteroids[i].xpos>= 1000){
                asteroids[i].ypos = i *((int)(Math.random()*200)+100);
            }
        }

        //draw the image
        if (hampter.onSteroids == true) {
            g.drawImage(hampterSteroidsImage, hampter.xpos, hampter.ypos, 200, 200, null);
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

        /*for (int i = 0; i < asteroids.length; i++) {
            g.drawImage(asteroidImage, (int) (Math.random() * 1000), (int) (Math.random() * 700), 50, 50, null);
        }*/
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
            hampter.dy = -20;
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
           if(GameOver==true){
               reset();
           }
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

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

    public BufferStrategy bufferStrategy;

    Hamster hampter;
    Image hampterImage;
    Hamster gigahampter;
    Image gigahampterImage;
    Food sunflower;
    Image sunflowerImage;
    Hamster hampterSteroids;
    Image hampterSteroidsImage;
    spaceShip spaceShip1;
    Image spaceShipImage;

    Asteroid asteroid1;
    Image asteroidImage1;
    Asteroid asteroid2;
    Image asteroidImage2;
    Asteroid asteroid3;
    Image asteroidImage3;
    Asteroid asteroid4;
    Image asteroidImage4;
    Asteroid asteroid5;
    Image asteroidImage5;
    Asteroid asteroid6;
    Image asteroidImage6;
    Asteroid asteroid7;
    Image asteroidImage7;
    Image space = Toolkit.getDefaultToolkit().getImage("space.jpg");
    Image explosion = Toolkit.getDefaultToolkit().getImage("explosion.png");


    public boolean pressingKey;
    public boolean firstAsteroidCrash;
    public boolean firstCrash;


    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.

    public BasicGameApp() { // BasicGameApp constructor

        setUpGraphics();
        firstCrash = true;
        firstAsteroidCrash = true;
        hampter = new Hamster("hampter1.png", 300, 300, 0.75);
        hampterImage = Toolkit.getDefaultToolkit().getImage("hampter1.png");
        gigahampter = new Hamster("gigahampter.png", 500, 500, 0.25);
        gigahampterImage = Toolkit.getDefaultToolkit().getImage("gigahampter.png");
        sunflower = new Food("sunflowerseed.png", 400,400);
        sunflowerImage = Toolkit.getDefaultToolkit().getImage("sunflowerseed.png");
        hampterSteroids = new Hamster("hampteronsteroids.png",0,0,0);
        hampterSteroidsImage = Toolkit.getDefaultToolkit().getImage("hampteronsteroids.png");
        spaceShip1 = new spaceShip(200,200);
        spaceShipImage = Toolkit.getDefaultToolkit().getImage("spaceship.png");


        asteroid1 = new Asteroid("asteroid.png",100,300);
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
        asteroidImage7 = Toolkit.getDefaultToolkit().getImage("asteroid.png");

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
//            if (hampter.isAlive == false){
//                hampter.width += 1;
//                hampter.height += 1;
//            }
            render();  // paint the graphics
            pause(30); // sleep for 10 ms
        }
    }

    public void moveThings() {
        //hampter.wrap();
        gigahampter.bounce();
        sunflower.wrap();
        hampter.move();
        checkCrash();
        checkCrashAsteroid();

        if (pressingKey){
            hampter.move();
        }
    }

    public void checkCrash() {
        if (hampter.rect.intersects(gigahampter.rect) && firstCrash == true){
            hampter.dx = -hampter.dx;
            hampter.dy = -hampter.dy;
            gigahampter.dx = -gigahampter.dx;
            gigahampter.dy = -gigahampter.dy;
            hampter.health =- 1;
            firstCrash = false;
            hampter.isAlive = false;
        }
        if (!hampter.rect.intersects(gigahampter.rect)){
            firstCrash = true;
        }
        if(hampter.rect.intersects(sunflower.rect) && firstCrash == true){
            firstCrash = false;
            sunflower.width -= 3;
            sunflower.height -= 3;
            hampter.dx += 3;
            hampter.dy += 3;

        }
        if(gigahampter.rect.intersects(sunflower.rect) && firstCrash == true){
            firstCrash = false;
            sunflower.width -= 3;
            sunflower.height -= 3;
            gigahampter.dx += 3;
            gigahampter.dy += 3;
        }
    }

    public void checkCrashAsteroid() {

        if (hampter.rect.intersects(asteroid1.rect) && firstAsteroidCrash == true) {
            asteroid1.dx = hampter.dx/2;
            asteroid1.dy = hampter.dy/2;
            asteroid1.bounce();
            firstAsteroidCrash = false;
        }
        if (!hampter.rect.intersects(asteroid1.rect)) {
            firstAsteroidCrash = true;
        }
        if (gigahampter.rect.intersects(asteroid1.rect) && firstAsteroidCrash == true) {
                asteroid1.dx = gigahampter.dx/2;
                asteroid1.dy = gigahampter.dy/2;
                asteroid1.bounce();
                firstAsteroidCrash = false;
            }
        if (!gigahampter.rect.intersects(asteroid1.rect)) {
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
        if (gigahampter.rect.intersects(asteroid2.rect) && firstAsteroidCrash == true) {
            asteroid2.dx = gigahampter.dx/2;
            asteroid2.dy = gigahampter.dy/2;
            asteroid2.bounce();
            firstAsteroidCrash = false;
        }
        if (!gigahampter.rect.intersects(asteroid2.rect)) {
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
        if (gigahampter.rect.intersects(asteroid3.rect) && firstAsteroidCrash == true) {
            asteroid3.dx = gigahampter.dx/2;
            asteroid3.dy = gigahampter.dy/2;
            asteroid3.bounce();
            firstAsteroidCrash = false;
        }
        if (!gigahampter.rect.intersects(asteroid3.rect)) {
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
        if (gigahampter.rect.intersects(asteroid4.rect) && firstAsteroidCrash == true) {
            asteroid4.dx = gigahampter.dx/2;
            asteroid4.dy = gigahampter.dy / 2;
            asteroid4.bounce();
            firstAsteroidCrash = false;
        }
        if (!gigahampter.rect.intersects(asteroid4.rect)) {
            firstAsteroidCrash = true;
        }
        if (hampter.rect.intersects(asteroid5.rect) && firstAsteroidCrash == true) {
            asteroid5.dx = hampter.dx / 2;
            asteroid5.dy = hampter.dy / 2;
            asteroid5.bounce();
            firstAsteroidCrash = false;
        }
        if (!gigahampter.rect.intersects(asteroid5.rect)) {
            firstAsteroidCrash = true;
        }
        if (gigahampter.rect.intersects(asteroid5.rect) && firstAsteroidCrash == true) {
            asteroid5.dx = gigahampter.dx / 2;
            asteroid5.dy = gigahampter.dy / 2;
            asteroid5.bounce();
            firstAsteroidCrash = false;
        }
        if (!gigahampter.rect.intersects(asteroid5.rect)) {
            firstAsteroidCrash = true;
        }
        if (hampter.rect.intersects(asteroid6.rect) && firstAsteroidCrash == true) {
            asteroid6.dx = hampter.dx / 2;
            asteroid6.dy = hampter.dy / 2;
            asteroid6.bounce();
            firstAsteroidCrash = false;
        }
        if (!gigahampter.rect.intersects(asteroid6.rect)) {
            firstAsteroidCrash = true;
        }
        if (gigahampter.rect.intersects(asteroid6.rect) && firstAsteroidCrash == true) {
            asteroid6.dx = gigahampter.dx / 2;
            asteroid6.dy = gigahampter.dy / 2;
            asteroid6.bounce();
            firstAsteroidCrash = false;
        }
        if (!gigahampter.rect.intersects(asteroid6.rect)) {
            firstAsteroidCrash = true;
        }
        if (hampter.rect.intersects(asteroid7.rect) && firstAsteroidCrash == true) {
            asteroid7.dx = hampter.dx / 2;
            asteroid7.dy = hampter.dy / 2;
            asteroid7.bounce();
            firstAsteroidCrash = false;
        }
        if (!gigahampter.rect.intersects(asteroid7.rect)) {
            firstAsteroidCrash = true;
        }
        if (gigahampter.rect.intersects(asteroid7.rect) && firstAsteroidCrash == true) {
            asteroid7.dx = gigahampter.dx / 2;
            asteroid7.dy = gigahampter.dy / 2;
            asteroid7.bounce();
            firstAsteroidCrash = false;
        }
        if (!gigahampter.rect.intersects(asteroid7.rect)) {
            firstAsteroidCrash = true;
        }
    }
    //Paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(space,0,0,WIDTH,HEIGHT,null);
        g.setColor(Color.RED);
        g.fillRect(800,20+(100-hampter.health), 15, hampter.health-5);
        g.drawImage(asteroidImage1,asteroid1.xpos,asteroid1.ypos,asteroid1.width,asteroid1.height,null);
        g.drawImage(asteroidImage2,asteroid2.xpos,asteroid2.ypos,asteroid2.width,asteroid2.height,null);
        g.drawImage(asteroidImage3,asteroid3.xpos,asteroid3.ypos,asteroid3.width,asteroid3.height,null);
        g.drawImage(asteroidImage4,asteroid4.xpos,asteroid4.ypos,asteroid4.width,asteroid4.height,null);
        g.drawImage(asteroidImage5,asteroid5.xpos,asteroid5.ypos,asteroid5.width,asteroid5.height,null);
        g.drawImage(asteroidImage6,asteroid6.xpos,asteroid6.ypos,asteroid6.width,asteroid6.height,null);
        g.drawImage(asteroidImage7,asteroid7.xpos,asteroid7.ypos,asteroid7.width,asteroid7.height,null);

        //draw the image
        if(hampter.isAlive == false) {
            g.drawImage(explosion, hampter.xpos, hampter.ypos, 300, 300, null);
            hampter.dx = 0;
            hampter.dy = 0;
            firstCrash = false;
            g.drawImage(hampterSteroidsImage, gigahampter.xpos, gigahampter.ypos,300,300, null);

        } else {
            g.drawImage(hampterImage, hampter.xpos, hampter.ypos, hampter.width, hampter.height, null);
            g.drawImage(gigahampterImage, gigahampter.xpos, gigahampter.ypos, gigahampter.width, gigahampter.height, null);

        }
        g.drawImage(sunflowerImage, sunflower.xpos,sunflower.ypos,sunflower.width,sunflower.height,null);
        g.dispose();
        bufferStrategy.show();

    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time ) {
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

        System.out.println(e.getKeyCode());
            if (e.getKeyCode() == 87) {
                hampter.dy = -20;
                hampter.dx = 0;
            }
            if (e.getKeyCode() == 83) {
                hampter.dy = 20;
                hampter.dx = 0;
            }
        if (e.getKeyCode() == 65) {
            hampter.dy = 0;
            hampter.dx = -20;
        }
        if (e.getKeyCode() == 68) {
            hampter.dy = 0;
            hampter.dx = 20;
        }
        if (e.getKeyCode() == 10){
            hampter.width += 5;
            hampter.height +=5;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //pressingKey = false;
        if (e.getKeyCode() == 87) {
            hampter.dy = 0;
            hampter.dx = 0;
        }
        if (e.getKeyCode() == 83) {
            hampter.dy = 0;
            hampter.dx = 0;
        }
        if (e.getKeyCode() == 65) {
            hampter.dy = 0;
            hampter.dx = 0;
        }
        if (e.getKeyCode() == 68) {
            hampter.dy = 0;
            hampter.dx = 0;
        }
    }
}

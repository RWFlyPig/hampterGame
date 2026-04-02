import java.awt.*;

public class BackGround {
    public String name;
    public int xpos;
    public int ypos;
    public int dx;
    public int dy;
    public int width;
    public int height;
    public Rectangle rect;

    public BackGround(String pName, int pXpos, int pYpos){
        name = pName;
        xpos = pXpos;
        ypos = pYpos;
        dx = -10;
        dy = 0;
        width = 1000;
        height = 700;
        rect = new Rectangle(xpos, ypos, width, height);
    }

    public void wrap(){
        if (xpos < -1000){
            System.out.println("x" + xpos);
            xpos = 1000;
        }
        xpos = xpos +dx;
        ypos = ypos +dy;
        rect = new Rectangle(xpos, ypos, width, height);
    }

}

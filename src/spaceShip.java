import java.awt.*;

public class spaceShip {
    public int xpos;
    public int ypos;
    public int dx;
    public int dy;
    public int width;
    public int height;

    public spaceShip(int pxPos, int pyPos){
        xpos = pxPos;
        ypos = pyPos;
        dx = 10;
        dy = 10;
        width = 80;
        height = 80;
    }

    public void move(){
        xpos = xpos + dx;
        ypos = ypos + dy;
    }

    public void wrap() {
        xpos = xpos + dx;
        ypos = ypos + dy;

        if (xpos > 1000) {
            xpos = 0;
        }
        if (xpos < 0) {
            xpos = 1000;
        }
        if (ypos < 0) {
            ypos = 1000;
        }
        if (ypos > 1000) {
            ypos = 0;
        }
    }
}

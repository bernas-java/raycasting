package objects;

import java.awt.*;

public class Boundary {

    private float x1;
    private float y1;

    private float x2;
    private float y2;

    public Boundary(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void draw(Graphics g) {

        g.setColor(Color.GREEN);
        g.drawLine(Math.round(x1),Math.round(y1),Math.round(x2),Math.round(y2));

    }

    public float getX1() {
        return x1;
    }
    

    public float getY1() {
        return y1;
    }


    public float getX2() {
        return x2;
    }


    public float getY2() {
        return y2;
    }

}

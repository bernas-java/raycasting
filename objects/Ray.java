package objects;

import java.awt.*;

public class Ray {

    private float x1;
    private float y1;

    private float x2;
    private float y2;

    public Ray(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void draw(Graphics g) {

        g.setColor(Color.WHITE);
        g.drawLine(Math.round(x1),Math.round(y1),Math.round(x2),Math.round(y2));

    }

}

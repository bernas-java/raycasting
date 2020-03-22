package main;

import objects.Boundary;
import objects.Ray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

public class AppPanel extends JPanel implements Runnable, MouseMotionListener
{

    static final int WIDTH = 1280, HEIGHT = 1280;

    private Thread t;
    private Random random = new Random();
    private boolean running;

    private int mx = 0;
    private int my = 0;

    private ArrayList<Boundary> boundaries = new ArrayList<>();
    private ArrayList<Ray> rays = new ArrayList<>();

    AppPanel() {

        setFocusable(true);
        addMouseMotionListener(this);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));

        // creating random lines to collide
        for (int i = 0; i < 5; i++) {
            int sx1 = random.nextInt(WIDTH);
            int sy1 = random.nextInt(HEIGHT);
            int sx2 = random.nextInt(WIDTH);
            int sy2 = random.nextInt(HEIGHT);

            boundaries.add(new Boundary(sx1,sy1,sx2,sy2));
        }

        start();
    }

    void start() {
        running = true;
        t = new Thread(this);
        try {
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void stop() {
        running = false;
        try {
            t.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void tick() {
        rays = calcRays(boundaries,mx,my,2000,Math.round(WIDTH*1.5f));
    }

    private ArrayList<Ray> calcRays(ArrayList<Boundary> boundaries, int mx, int my, int resolution, int maxDist) {
        ArrayList<Ray> rays = new ArrayList<>();

        for (int i = 0; i < resolution; i++) {
            double dir = (Math.PI * 2) * ((double) i / resolution);
            float minDist = maxDist;
            for (Boundary boundary : boundaries) {
                float dist = getRayCast(mx,my,mx + (float)Math.cos(dir) * maxDist, my + (float)Math.sin(dir) * maxDist,boundary.getX1(), boundary.getY1(), boundary.getX2(), boundary.getY2());
                if (dist < minDist && dist > 0) {
                    minDist = dist;
                }
            }
            // promień zostaje dodany do listy
            rays.add(new Ray(mx,my,mx + (float) Math.cos(dir) * minDist, my + (float) Math.sin(dir) * minDist ));
        }
        // lista zostaje zwrócona
        return rays;

    }

    public static float dist(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public static float getRayCast(float p0_x, float p0_y, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y) {
        float s1_x, s1_y, s2_x, s2_y;
        s1_x = p1_x - p0_x;
        s1_y = p1_y - p0_y;
        s2_x = p3_x - p2_x;
        s2_y = p3_y - p2_y;

        float s, t;
        s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
        t = (s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

        if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
            float x = p0_x + (t * s1_x);
            float y = p0_y + (t * s1_y);

            return dist(p0_x, p0_y, x, y);
        }

        return -1;
    }

    public void paint(Graphics g) {
        g.setColor(new Color(0));
        g.fillRect(0,0,WIDTH,HEIGHT);

        for (Boundary b : boundaries)
            b.draw(g);

        for (Ray ray : rays)
            ray.draw(g);
    }

    @Override
    public void run() {
        while (running) {
            repaint();
            tick();
            try {
                Thread.sleep(1000/120);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mx = e.getX();
        my = e.getY();
    }
}

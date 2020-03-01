package main;

import javax.swing.*;

public class Main {

    Main() {
        JFrame frame = new JFrame();
        AppPanel panel = new AppPanel();

        frame.setTitle("Raycasting");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }



    public static void main(String[] args) {
        new Main();
    }

}

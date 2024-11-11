/***************************
Name: Lisa Dang
Email: lisa.dang@tufts.edu
***************************/

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {


    // Constructor to set up the window and controls
    public Main() {
        setTitle("Space Traffic Control System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Create a custom canvas (MyCanvas) for drawing
        GameState gameState = new GameState(); 
        SpaceModel model = new SpaceModel(gameState);
        MyCanvas canvas = new MyCanvas(model, gameState);
        add(canvas, BorderLayout.CENTER);

        // Create control panel with buttons
        ControlPanel controlPanel = new ControlPanel(model, canvas, gameState);

        // Add control panel to the bottom of the window
        add(controlPanel, BorderLayout.SOUTH);

        // Make the window visible
        setVisible(true);
    }

    // Main method to run the program
    public static void main(String[] args) {
        new Main();
    }
}

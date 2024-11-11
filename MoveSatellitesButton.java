import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoveSatellitesButton extends JButton {

    public MoveSatellitesButton(SpaceModel model, MyCanvas canvas) {
        super("Move Satellites");  // Set button label

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Move all satellites and repaint the canvas
                for (Vehicle vehicle : model.getVehicles()) { // Iterate over all vehicles
                    if (vehicle instanceof Satellite) {
                        Satellite satellite = (Satellite) vehicle; // Safe casting
                        satellite.setX(satellite.getX() + (satellite.getSpeed())); // Move horizontally
                        satellite.setY(satellite.getY() + (satellite.getSpeed())); // Move vertically

                        // Keep the satellite within the bounds of the screen
                        if (satellite.getX() < 0) satellite.setX(0);
                        if (satellite.getY() < 0) satellite.setY(0);
                        if (satellite.getX() > 780) satellite.setX(canvas.getWidth());
                        if (satellite.getY() > 580) satellite.setY(canvas.getHeight());
                    }
                }
                canvas.repaint(); // Repaint to show updated positions
            }
        });
    }
}

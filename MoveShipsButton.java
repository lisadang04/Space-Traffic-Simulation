import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoveShipsButton extends JButton {

    public MoveShipsButton(SpaceModel model, MyCanvas canvas) {
        super("Move Ships");  // Set button label

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Move all ships and repaint the canvas
                for (Vehicle vehicle : model.getVehicles()) { // Changed to Vehicle to iterate over all vehicles
                    if (vehicle instanceof Spaceship) {
                        Spaceship ship = (Spaceship) vehicle; // Safe casting
                        ship.setX(ship.getX() + (ship.getdx() * ship.getSpeed()));
                        ship.setY(ship.getY() + (ship.getdy() * ship.getSpeed()));

                        // Keep the spaceship within the bounds of the screen
                        if (ship.getX() < 0) ship.setX(0);
                        if (ship.getY() < 0) ship.setY(0);
                        if (ship.getX() > 780) ship.setX(canvas.getWidth());
                        if (ship.getY() > 580) ship.setY(canvas.getHeight());
                    }
                }
                canvas.repaint(); // Repaint to show updated positions
            }
        });
    }
}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class AddShipButton extends JButton {
    private int shipCount = 1; 

    public AddShipButton(ControlPanel panel, SpaceModel model, MyCanvas canvas) {
        super("Add Ship");

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user for a custom label
                String inputLabel = JOptionPane.showInputDialog("Enter a label for the new spaceship:");
                
                // Assign a default label if the input is empty
                String label = (inputLabel == null || inputLabel.trim().isEmpty())
                               ? "Spaceship " + shipCount++
                               : inputLabel;

                // Create a new spaceship
                Spaceship newShip = new Spaceship((int) (Math.random() * 700), (int) (Math.random() * 500), 5, Color.RED);
                newShip.setLabel(label);  // Set the custom or default label

                // Add the new spaceship to the model and repaint the canvas
                model.addVehicle(newShip);
                panel.updateButtonStates();
                canvas.repaint();
            }
        });
    }
}
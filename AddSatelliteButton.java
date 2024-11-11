import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class AddSatelliteButton extends JButton {
    private int satelliteCount = 1;  

    public AddSatelliteButton(ControlPanel panel, SpaceModel model, MyCanvas canvas) {
        super("Add Satellite");

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user for a custom label
                String inputLabel = JOptionPane.showInputDialog("Enter a label for the new satellite:");
                
                // Assign a default label if the input is empty
                String label = (inputLabel == null || inputLabel.trim().isEmpty())
                               ? "Satellite " + satelliteCount++
                               : inputLabel;

                // Create a new satellite
                Satellite newSatellite = new Satellite((int) (Math.random() * 700), (int) (Math.random() * 500), 3, Color.BLUE);
                newSatellite.setLabel(label);

                // Add the new satellite to the model and repaint the canvas
                model.addVehicle(newSatellite);
                panel.updateButtonStates();
                canvas.repaint();
            }
        });
    }
}
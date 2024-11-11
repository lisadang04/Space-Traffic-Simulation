import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IncreaseSpeedButton extends JButton {

    public IncreaseSpeedButton(SpaceModel model, MyCanvas canvas) {
        super("Increase Speed");  // Set button label

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the currently selected vehicle from the canvas
                Vehicle selectedVehicle = canvas.getClicked();
                
                // Increase the speed of the selected vehicle
                int newSpeed = selectedVehicle.getSpeed() + 1;
                selectedVehicle.setSpeed(newSpeed);
                model.updateVehicleSpeed(selectedVehicle, newSpeed);  // Update speed in the model
                canvas.repaint();  // Refresh the canvas to reflect changes
            }
        });
    }
}

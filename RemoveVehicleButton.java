import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveVehicleButton extends JButton {

    public RemoveVehicleButton(ControlPanel panel, SpaceModel model, MyCanvas canvas) {
        super("Remove Vehicle");  // Set button label

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the currently selected vehicle from the canvas
                Vehicle selectedVehicle = canvas.getClicked();

                model.removeVehicle(selectedVehicle);
                panel.showDefaultButtons();
                panel.updateButtonStates();
                canvas.repaint(); // Repaint the canvas
            }
        });
    }
}

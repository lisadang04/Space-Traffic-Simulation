import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePositionButton extends JButton {

    public ChangePositionButton(SpaceModel model, MyCanvas canvas) {
        super("Change Position");  // Set button label

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the currently selected vehicle from the canvas
                Vehicle selectedVehicle = canvas.getClicked();
                
                // Change the position of the selected vehicle randomly within bounds
                int newX = (int) (Math.random() * (canvas.getWidth() - 20)); // Ensure it stays within bounds
                int newY = (int) (Math.random() * (canvas.getHeight() - 20)); // Ensure it stays within bounds
                
                selectedVehicle.setX(newX);
                selectedVehicle.setY(newY);
                canvas.repaint();
            }
        });
    }
}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CancelButton extends JButton {
    public CancelButton(ControlPanel panel, MyCanvas canvas) {
        super("Cancel");
        addActionListener(e -> panel.showDefaultButtons());
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the currently selected vehicle from the canvas
                Vehicle selectedVehicle = canvas.getClicked();
                
                selectedVehicle.setSelected(false);
                canvas.repaint();  // Refresh the canvas to reflect changes
            }
        });
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.function.Consumer;
import java.awt.event.MouseAdapter;
import java.util.List;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MyCanvas extends JPanel implements MouseMotionListener {
    private SpaceModel model;
    private String hoveredLabel = null;  // To store the label of the hovered vehicle
    private int mouseX, mouseY;
    private Consumer<Vehicle> vehicleClickListener;
    private Vehicle hoveredVehicle;  // Store the hovered vehicle
    private Vehicle clickedVehicle;  // Store the clicked vehicle
    private GameState gameState;
    private double zoomFactor = 1.0; // Default zoom level

    public MyCanvas(SpaceModel model, GameState gameState) {
        this.model = model;
        this.gameState = gameState;
        addMouseMotionListener(this);
        
        // Add a mouse listener to handle clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Check if the click was on a vehicle
                clickedVehicle = getClickedVehicle(e.getX(), e.getY());
                if (clickedVehicle != null) {
                    System.out.println("Clicked on vehicle: " + clickedVehicle.getLabel());
                    // Deselect all vehicles
                    for (Vehicle vehicle : model.getVehicles()) {
                        vehicle.setSelected(false);
                    }
                    clickedVehicle.setSelected(true);
                    hoveredVehicle = clickedVehicle;  // Set the hovered vehicle as the clicked vehicle
                    if (vehicleClickListener != null) {
                        vehicleClickListener.accept(clickedVehicle);
                    }
                    repaint();
                } 
            }
        });

        // Add a component listener to handle window resizing
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Perform necessary updates here after resizing
                repaint();  // Ensure canvas is redrawn after resize
            }
        });
    }

    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
        repaint(); // Repaint canvas to apply the zoom
    }

    public Vehicle getClicked() {
        return clickedVehicle;
    }

    // Method to allow other classes to register for vehicle click events
    public void addVehicleClickListener(Consumer<Vehicle> listener) {
        this.vehicleClickListener = listener;
    }

    public Vehicle getHoveredVehicle() {
        return hoveredVehicle;  // Return the hovered vehicle
    }

    private void checkHover() {
        List<Vehicle> vehicles = model.getVehicles();
        hoveredVehicle = null;
        hoveredLabel = null;
        // Calculate the canvas center
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        for (Vehicle vehicle : vehicles) {
            // Adjust vehicle's coordinates based on zoom factor
            int adjustedX = (int) (centerX + (vehicle.getX() - centerX) * zoomFactor);
            int adjustedY = (int) (centerY + (vehicle.getY() - centerY) * zoomFactor);

            if (isMouseOverVehicle(adjustedX, adjustedY, mouseX, mouseY)) {
                hoveredVehicle = vehicle;
                hoveredLabel = vehicle.getLabel();
                break;
            }
        }
    }

    private Vehicle getClickedVehicle(int x, int y) {
        for (Vehicle vehicle : model.getVehicles()) {
            if (vehicle.isClicked(x, y, this, zoomFactor)) {
                return vehicle;
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set background color to black
        setBackground(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());  // This ensures the radar background fills the canvas

        // Center the zoom transformation
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Draw the radar grid
        drawRadar(g2d);

        // Draw the vehicles in their zoomed positions
        for (Vehicle vehicle : model.getVehicles()) {
            vehicle.draw(g2d, getWidth(), getHeight(), zoomFactor);
        }

        // Display the player's score at the top of the screen
        drawScore(g2d);

        // Check if the mouse is hovering over a vehicle
        checkHover();

        // Draw the label of the hovered vehicle
        if (hoveredVehicle != null) {
            drawLabel(g2d, hoveredLabel, mouseX, mouseY);  // Draw the label at the current mouse position
        }
    }

    private void drawLabel(Graphics2D g2d, String label, int x, int y) {
        g2d.setColor(Color.WHITE);  // Set label color
        g2d.drawString(label, x + 10, y - 10);  // Draw the label slightly offset from the mouse
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        checkHover(); // Call checkHover without any arguments
        repaint();  // Trigger a repaint to update the label display
    }

    // Override the mouseDragged method for MouseMotionListener
    @Override
    public void mouseDragged(MouseEvent e) {
        // Not needed for this case, but still required by the interface
    }

    private boolean isMouseOverVehicle(int vehicleX, int vehicleY, int mouseX, int mouseY) {
        final int vehicleWidth = 20;
        final int vehicleHeight = 20;

        return mouseX >= vehicleX && mouseX <= vehicleX + vehicleWidth &&
            mouseY >= vehicleY && mouseY <= vehicleY + vehicleHeight;
    }

    private void drawRadar(Graphics2D g2d) {
        // Radar grid parameters
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;

        // Calculate the maximum possible radius that fills the canvas
        int maxRadius = (int) Math.sqrt(centerX * centerX + centerY * centerY);

        // Set the color for the radar grid (light green for a radar effect)
        g2d.setColor(new Color(0, 255, 0, 100));  // Light green with some transparency

        // Draw concentric circles (fill the entire canvas)
        for (int r = 50; r <= maxRadius; r += 50) {
            g2d.drawOval(centerX - r, centerY - r, 2 * r, 2 * r); // Draw circles with increasing radius
        }

        // Draw radial lines
        int numLines = 24;
        for (int i = 0; i < numLines; i++) {
            double angle = 2 * Math.PI * i / numLines;  // Calculate angle for each line
            int endX = centerX + (int) (maxRadius * Math.cos(angle));
            int endY = centerY + (int) (maxRadius * Math.sin(angle));
            g2d.drawLine(centerX, centerY, endX, endY);  // Draw the radial line
        }
    }

    private void drawScore(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);  // Set color for the score text
        g2d.setFont(new Font("Arial", Font.BOLD, 16));  // Set font for the score
        g2d.drawString("Score: " + gameState.getScore(), 10, 20);  // Draw score at the top left
    }
}

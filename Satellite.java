import java.awt.Color;
import java.awt.Graphics;

public class Satellite extends Vehicle {
    private String label;

    public Satellite(int x, int y, int speed, Color color) {
        super(x, y, speed, color, 20, 20); // Pass width and height to the superclass
        this.label = "Satellite";
    }

    // Getters for dx and dy
    public int getdx() { return dx; }
    public int getdy() { return dy; }
    
    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public void draw(Graphics g, int canvasWidth, int canvasHeight, double zoomFactor) {
        // Calculate the canvas center
        int centerX = canvasWidth / 2;
        int centerY = canvasHeight / 2;

        // Adjust x and y to be relative to the center and apply zoom
        int adjustedX = (int) (centerX + (x - centerX) * zoomFactor);
        int adjustedY = (int) (centerY + (y - centerY) * zoomFactor);

        g.setColor(color);
        g.fillRect(adjustedX, adjustedY, width, height);

        // Draw a white outline if selected
        if (isSelected()) {
            g.setColor(Color.WHITE);
            g.drawRect(adjustedX - 2, adjustedY - 2, width + 4, height + 4);
        }
    }
}

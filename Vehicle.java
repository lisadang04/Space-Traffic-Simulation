import java.awt.*;

public abstract class Vehicle {
    protected int x, y, speed;
    protected int dx, dy;
    protected Color color;
    protected int width, height; 
    private boolean selected = false;

    public Vehicle(int x, int y, int speed, Color color, int width, int height) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.color = color;
        this.dx = 1;
        this.dy = 1;
        this.width = 20;
        this.height = 20;
    }

    // Getters and setters for x, y, speed, color, and label
    public int getX() { return x; }
    public int getY() { return y; }
    public int getSpeed() { return speed; }
    public Color getColor() { return color; }
    public int getWidth() { return width; }  // Getter for width
    public int getHeight() { return height; } // Getter for height
    public int getDx() { return dx; }
    public int getDy() { return dy; }
    public boolean isSelected() { return selected; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setSpeed(int speed) { this.speed = speed; }
    public void setColor(Color color) { this.color = color; }
    public void setDx(int dx) { this.dx = dx; }
    public void setDy(int dy) { this.dy = dy; }
    public void setSelected(boolean selected) { this.selected = selected; }

    public abstract void draw(Graphics g, int canvasWidth, int canvasHeight, double zoomFactor);

    // Update position based on speed and direction
    public void tick(MyCanvas canvas) {
        int currX = this.getX();
        int currY = this.getY();
        int currDx = this.getDx();
        int currDy = this.getDy();
        int currSpeed = this.getSpeed();
        this.setX(currX + currDx * currSpeed);
        this.setY(currY + currDy * currSpeed);
    }

    // Add abstract method for getting the label
    public abstract String getLabel();

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // This is my "pick correlation" method
    public boolean isClicked(int mouseX, int mouseY, MyCanvas canvas, double zoomFactor) {
        // Calculate the canvas center
        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 2;
        // Adjust x and y to be relative to the center and apply zoom
        int adjustedX = (int) (centerX + (x - centerX) * zoomFactor);
        int adjustedY = (int) (centerY + (y - centerY) * zoomFactor);

        return (mouseX >= adjustedX && mouseX <= adjustedX + 20 && 
        mouseY >= adjustedY && mouseY <= adjustedY + 20);
    }
}
import javax.swing.*;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.awt.Rectangle;

// The SpaceModel class manages the simulation's vehicles and their updates
public class SpaceModel {
    // Declare a list that can hold instances of Vehicle (including any subclasses)
    private List<Vehicle> vehicles;
    private GameState gameState;
    private double zoomFactor = 1.0; // Default zoom factor

    // Constructor to initialize the list of vehicles
    public SpaceModel(GameState gameState) {
        vehicles = new ArrayList<>();  // Initialize the vehicles list
        this.gameState = gameState;
    }

    // Add a vehicle to the simulation (can be a spaceship, satellite, etc.)
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);  // Add the vehicle to the list
    }

    // Remove a vehicle from the simulation
    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);  // Remove the vehicle from the list
    }

    // Get all vehicles in the simulation
    public List<Vehicle> getVehicles() {
        return vehicles;  // Return the list of vehicles
    }

    // Update the speed of a specific vehicle
    public void updateVehicleSpeed(Vehicle vehicle, int newSpeed) {
        if (vehicles.contains(vehicle)) {
            vehicle.setSpeed(newSpeed);  // Update the speed of the vehicle
        }
    }

    // Add a setter method to adjust the zoom factor
    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }
    
    // Draw all vehicles on the canvas
    public void drawAllVehicles(Graphics g, int canvasWidth, int canvasHeight) {
        for (Vehicle vehicle : vehicles) {
            vehicle.draw(g, canvasWidth, canvasHeight, zoomFactor);  // Pass the zoomFactor variable
        }
    }

    // Method to check for collisions between vehicles
    public boolean checkCollisions(ControlPanel panel) {
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v1 = vehicles.get(i);
            for (int j = i + 1; j < vehicles.size(); j++) {
                Vehicle v2 = vehicles.get(j);

                // Check if bounding rectangles intersect
                if (v1.getBounds().intersects(v2.getBounds())) {
                    // Prompt user to decide which vehicle to delete
                    int choice = JOptionPane.showOptionDialog(
                        null,
                        "Collision detected between " + v1.getLabel() + " and " + v2.getLabel() + 
                        ". Which one would you like to delete?",
                        "Collision Detected",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{v1.getLabel(), v2.getLabel()},
                        null
                    );

                    // Remove the chosen vehicle based on user's choice
                    if (choice == JOptionPane.YES_OPTION) {
                        vehicles.remove(v1);
                    } else if (choice == JOptionPane.NO_OPTION) {
                        vehicles.remove(v2);
                    }

                    panel.showDefaultButtons();
                    panel.updateButtonStates();
                    return true;  // Exit the loop after handling a collision
                }
            }
        }
        return false;
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ControlPanel extends JPanel {
    private JButton addShipButton;
    private JButton addSatelliteButton;
    private JButton moveShipsButton;
    private JButton moveSatellitesButton;

    private Timer shipTimer;   // Timer to move ships
    private Timer satelliteTimer; // Timer to move satellites
    private SpaceModel model;
    private MyCanvas canvas;

    private GameState gameState;
    private JLabel scoreLabel;

    private JButton increaseSpeedButton;
    private JButton changePositionButton;
    private JButton removeVehicleButton;
    private JButton cancelButton;

    private JButton zoomInButton;
    private JButton zoomOutButton;
    private double zoomFactor = 1.0; // Start with a default zoom level of 1.0

    private final ControlPanel controlPanel = this;

    public ControlPanel(SpaceModel model, MyCanvas canvas, GameState gameState) {
        this.model = model;
        this.canvas = canvas;
        this.gameState = gameState;

        setLayout(new FlowLayout());

        scoreLabel = new JLabel("Score: " + gameState.getScore());

        // Initialize the buttons
        addShipButton = new AddShipButton(this, model, canvas);
        addSatelliteButton = new AddSatelliteButton(this, model, canvas);
        // moveShipsButton = new MoveShipsButton(model, canvas);
        // moveSatellitesButton = new MoveSatellitesButton(model, canvas);
        moveShipsButton = new JButton("Stop Ships");
        moveSatellitesButton = new JButton("Stop Satellites");

        increaseSpeedButton = new IncreaseSpeedButton(model, canvas);
        changePositionButton = new ChangePositionButton(model, canvas);
        removeVehicleButton = new RemoveVehicleButton(this, model, canvas);
        cancelButton = new CancelButton(this, canvas);

        // Initialize zoom buttons
        zoomInButton = new JButton("Zoom In");
        zoomOutButton = new JButton("Zoom Out");

        // Set actions for zoom buttons
        zoomInButton.addActionListener(e -> {
            zoomFactor *= 1.2; // Increase zoom level
            canvas.setZoomFactor(zoomFactor); // Update canvas zoom
        });

        zoomOutButton.addActionListener(e -> {
            zoomFactor /= 1.2; // Decrease zoom level
            canvas.setZoomFactor(zoomFactor); // Update canvas zoom
        });

        addShipButton.addActionListener(e -> {
            gameState.increaseScore();
            updateScoreDisplay();
        });

        addSatelliteButton.addActionListener(e -> {
            gameState.increaseScore();
            updateScoreDisplay();
        });

        // Create timers for ships and satellites
        shipTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Iterator<Vehicle> iterator = model.getVehicles().iterator();
                boolean collision = false;
                while (iterator.hasNext()) {
                    Vehicle vehicle = iterator.next();
                    if (vehicle instanceof Spaceship) {
                        vehicle.tick(canvas);
                        if (model.checkCollisions(controlPanel)) {
                            collision = true;
                        }
                    }
                }
                if (collision) {
                    gameState.decreaseScore();
                    updateScoreDisplay();
                }
                
                canvas.repaint();
            }
        });

        satelliteTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Iterator<Vehicle> iterator = model.getVehicles().iterator();
                boolean collision = false;
                while (iterator.hasNext()) {
                    Vehicle vehicle = iterator.next();
                    if (vehicle instanceof Satellite) {
                        vehicle.tick(canvas);
                        if (model.checkCollisions(controlPanel)) {
                            collision = true;
                        }
                    }
                }
                if (collision) {
                    gameState.decreaseScore();
                    updateScoreDisplay();
                }
                canvas.repaint();
            }
        });

        

        // Set button actions
        moveShipsButton.addActionListener(e -> toggleShipMovement());
        addShipButton.addActionListener(e -> shipTimer.start());
        addShipButton.addActionListener(e -> moveShipsButton.setText("Stop Ships"));
        moveSatellitesButton.addActionListener(e -> toggleSatelliteMovement());
        addSatelliteButton.addActionListener(e -> satelliteTimer.start());
        addSatelliteButton.addActionListener(e -> moveSatellitesButton.setText("Stop Satellites"));

        // Add the default buttons
        showDefaultButtons();

        updateButtonStates(); // Initially update button states based on vehicle counts

        // Set up listeners for canvas clicks to handle vehicle selection and reset
        canvas.addVehicleClickListener(vehicle -> {
            if (vehicle != null) {
                // Vehicle clicked, switch to vehicle control buttons
                showVehicleControlButtons();
            } else {
                // Reset to default if user clicks outside a vehicle
                showDefaultButtons();
            }
        });

        // Add a component listener to handle resizing
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                canvas.revalidate();  // Ensures that the canvas is properly sized
                canvas.repaint();      // Redraws the canvas
            }
        });
    }
    
    // Show the default control panel buttons
    public void showDefaultButtons() {
        removeAll();  // Clear current buttons
        add(addShipButton);
        add(addSatelliteButton);
        add(moveShipsButton);
        add(moveSatellitesButton);
        add(zoomInButton);
        add(zoomOutButton);
        revalidate();
        repaint();
    }

    // Show the vehicle control panel buttons when a vehicle is selected
    public void showVehicleControlButtons() {
        removeAll();  // Clear current buttons
        add(increaseSpeedButton);
        add(changePositionButton);
        add(removeVehicleButton);
        add(cancelButton);  // Add cancel button to return to default state
        revalidate();
        repaint();
    }

    // Method to toggle ship movement
    private void toggleShipMovement() {
        if (shipTimer.isRunning()) {
            shipTimer.stop();
            moveShipsButton.setText("Move Ships");
        } else {
            shipTimer.start();
            moveShipsButton.setText("Stop Ships");
        }
    }

    // Method to toggle satellite movement
    private void toggleSatelliteMovement() {
        if (satelliteTimer.isRunning()) {
            satelliteTimer.stop();
            moveSatellitesButton.setText("Move Satellites");
        } else {
            satelliteTimer.start();
            moveSatellitesButton.setText("Stop Satellites");
        }
    }

    // Update button states based on the presence of ships and satellites
    public void updateButtonStates() {
        boolean hasShips = model.getVehicles().stream().anyMatch(v -> v instanceof Spaceship);
        boolean hasSatellites = model.getVehicles().stream().anyMatch(v -> v instanceof Satellite);

        moveShipsButton.setEnabled(hasShips);
        moveSatellitesButton.setEnabled(hasSatellites);
    }

    public void updateScoreDisplay() {
        scoreLabel.setText("Score: " + gameState.getScore());
    }
}
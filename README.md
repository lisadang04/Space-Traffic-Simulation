# Space Traffic Control Simulation
## Description
This program simulates space traffic control at a futuristic spaceport. Users can add spaceships or satellites, control their speed, and set locations on a radar display. A central panel with buttons manages vehicles and allows users to zoom in/out on a map. 

## User Interface
- A black radar-style area is the main drawing section.
- Buttons at the bottom allow interaction with spaceships.
  - **Default Control Panel**:
    - "Add Ship" adds a new ship to the radar.
    - "Add Satellite" adds a new satellite to the radar.
    - "Stop Ships" moves all existing ships.
      - Starts out as deactivated/grayed out
      - Automatically changes to activated "Stop Ships" button and starts moving all ships if total ships go from 0 to 1.
        - When clicked, stops moving all existing ships and changes back to "Move Ships"
      - If all ships are deleted, whatever this button is, it becomes a deactivated/grayed out "Stop Ships" button
    - "Stop Satellites" moves all existing satellites.
      - Starts out as deactivated/grayed out
      - Automatically changes to activated "Stop Satellites" button and starts moving all satellites if total satellites go from 0 to 1.
        - When clicked, stops moving all existing satellites and changes back to "Move Satellites"
      - If all satellites are deleted, whatever this button is, it becomes a deactivated/grayed out "Stop Satellites" button
    - "Zoom In" zooms into the map of vehicles to see a narrower view
    - "Zoom Out" zooms out of the map of vehicles to see a broader view
      - For both zooming buttons, vehicles remember their "real" locations in the universe and users are still able to hover to view labels and click vehicles to access that vehicle's control panel
  - **Vehicle Control Panel** (when clicking on a specific vehicle):
    - "Increase Speed" boosts the speed of the selected vehicle.
    - "Change Position" allows users to change where the vehicle is.
    - "Remove Vehicle" deletes the selected vehicle.
    - "Cancel" returns to default control panel.
- Hovering over each vehicle displays its label
- Clicking a vehicle highlights it and allows users to edit features specific to that clicked vehicle
- If vehicles collide, user must choose to remove one of the vehicles in the collion, losing a point from their score
- Program is able to adjust to window resizing

## Game Rules!
- The player gains one point each time a new vehicle is added.
- The player loses one point whenever a collision (crash) is detected between vehicles.
- The current score is displayed on the top left of the screen for the user to monitor.
- The goal of the game is to get the highest score possible (the highest number of vehicles added with the least number of collisions)

## How to Compile and Run
1. Place all files (AddSatelliteButton.java, AddShipButton.java, ControlPanel.java, MoveShipsButton.java, MoveSatellitesButton.java, SpaceModel.java, ChangePositionButton.java, IncreaseSpeedButton.java, MyCanvas.java, Spaceship.java, Main.java, Vehicle.java, Satellite.java, RemoveVehicleButton.java, GameState.java) in the same directory.
2. Open a terminal and navigate to the folder containing the program files.
3. Compile the program using the command:
   `javac *.java`
4. Run the program using the command:
   `java Main`

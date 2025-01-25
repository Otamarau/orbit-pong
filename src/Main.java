import javax.swing.*;
import sim.SimulationPanel; // Import the SimulationPanel class from the sim package

public class Main {
    public static void main(String[] args) {
        // Create the main JFrame
        JFrame frame = new JFrame("Orbit Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setResizable(false);

        // Create an instance of SimulationPanel and add it to the frame
        SimulationPanel panel = new SimulationPanel();
        frame.add(panel);

        // Make the frame visible
        frame.setVisible(true);
    }
}

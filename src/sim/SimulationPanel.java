package sim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SimulationPanel extends JPanel implements ActionListener {
    private final Timer timer;
    private double angle = 0; // Angle for the orbiting object
    private final int orbitRadius = 200; // Radius of the orbit

    private final List<MovableRectangle> rectangles = new ArrayList<>(); // List of rectangles

    public SimulationPanel() {
        // Set up the timer for animation
        timer = new Timer(16, this);
        timer.start();

        // Initialize rectangles with different properties
        MovableRectangle rect1 = new MovableRectangle(50, 350, 20, 100, Color.GREEN);
        MovableRectangle rect2 = new MovableRectangle(100, 350, 20, 100, Color.ORANGE);

        rectangles.add(rect1);
        rectangles.add(rect2);

        // Set up key bindings for each rectangle
        setupKeyBindings(rect1, KeyEvent.VK_W, KeyEvent.VK_S); // Controls for rect1
        setupKeyBindings(rect2, KeyEvent.VK_UP, KeyEvent.VK_DOWN); // Controls for rect2
    }

    private void setupKeyBindings(MovableRectangle rect, int upKey, int downKey) {
        // Move rectangle up
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(upKey, 0), "moveUp" + rect.hashCode());
        getActionMap().put("moveUp" + rect.hashCode(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rect.moveUp();
                repaint();
            }
        });

        // Move rectangle down
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(downKey, 0), "moveDown" + rect.hashCode());
        getActionMap().put("moveDown" + rect.hashCode(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rect.moveDown();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set the background color
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw the planet (stationary in the center)
        g.setColor(Color.BLUE);
        int planetSize = 50;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        g.fillOval(centerX - planetSize / 2, centerY - planetSize / 2, planetSize, planetSize);

        // Calculate the position of the orbiting object
        int orbX = centerX + (int) (orbitRadius * Math.cos(angle));
        int orbY = centerY + (int) (orbitRadius * Math.sin(angle));

        // Draw the orbiting object
        g.setColor(Color.RED);
        int orbSize = 20;
        g.fillOval(orbX - orbSize / 2, orbY - orbSize / 2, orbSize, orbSize);

        // Draw all rectangles
        for (MovableRectangle rect : rectangles) {
            rect.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update the angle for orbital motion
        angle += 0.02; // Adjust speed by changing this value
        repaint(); // Trigger a repaint of the panel
    }
}

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
    private final List<MovableRectangle> rectangles = new ArrayList<>();
    private final FixedPlanet mainPlanet;
    private final Ball ball;

    public SimulationPanel() {
        // Set up the timer for animation
        timer = new Timer(16, this);
        timer.start();

        // Initialize rectangles
        MovableRectangle rect1 = new MovableRectangle(50, 350, 20, 100, Color.GREEN);
        MovableRectangle rect2 = new MovableRectangle(100, 350, 20, 100, Color.ORANGE);
        rectangles.add(rect1);
        rectangles.add(rect2);

        // Initialize planet
        mainPlanet = new FixedPlanet(400, 300, 50, Color.BLUE);

        // Initialize ball
        ball = new Ball(200, 200, 2, 3, 20, Color.RED);

        // Set up key bindings
        setupKeyBindings(rect1, KeyEvent.VK_W, KeyEvent.VK_S);
        setupKeyBindings(rect2, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
    }

    private void setupKeyBindings(MovableRectangle rect, int upKey, int downKey) {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(upKey, 0), "moveUp" + rect.hashCode());
        getActionMap().put("moveUp" + rect.hashCode(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rect.moveUp();
                repaint();
            }
        });

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

        // Set background color
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw planet
        mainPlanet.draw(g);

        // Draw ball
        ball.draw(g);

        // Draw rectangles
        for (MovableRectangle rect : rectangles) {
            rect.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update ball logic
        ball.move(getBounds(), rectangles, mainPlanet);

        // Trigger repaint
        repaint();
    }
}

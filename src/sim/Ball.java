package sim;

import java.awt.*;
import java.util.List;


public class Ball {
    private double x, y; // Position
    private double vx, vy; // Velocity
    private final int size; // Diameter of the ball
    private final Color color;

    public Ball(double x, double y, double vx, double vy, int size, Color color) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.size = size;
        this.color = color;
    }

    

    public void move(Rectangle panelBounds, List<MovableRectangle> rectangles, FixedPlanet planet) {
        // Gravity effect from the planet
        double dx = planet.getX() - x;
        double dy = planet.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Gravitational pull (inverse square law)
        double gravitationalForce = 0.5 / (distance * distance + 0.1); // Avoid division by zero
        vx += gravitationalForce * dx / distance;
        vy += gravitationalForce * dy / distance;

        // Magnetizing effect and directional orbit
        if (distance < planet.getSize() * 3) {  // Adjust to a larger radius for orbiting
            final double magnetizingStrength = 0.1; // Controls how strongly the ball aligns to orbit
            double angleToPlanet = Math.atan2(dy, dx);

            // Adjust the ball's velocity to make it orbit in the current direction
            double orbitVx = -Math.sin(angleToPlanet) * 3; // Orbital velocity in X
            double orbitVy = Math.cos(angleToPlanet) * 3;  // Orbital velocity in Y

            // Gradually align velocity with orbit
            vx = (1 - magnetizingStrength) * vx + magnetizingStrength * orbitVx;
            vy = (1 - magnetizingStrength) * vy + magnetizingStrength * orbitVy;

            // Gradually increase the speed as it orbits
            vx *= 1.02;  // Slight speed up in X direction
            vy *= 1.02;  // Slight speed up in Y direction
        }

        // Ensure the ball maintains speed above a threshold
        final double minSpeed = 2.0; // Minimum speed to avoid getting stuck near the planet
        double currentSpeed = Math.sqrt(vx * vx + vy * vy);
        if (currentSpeed < minSpeed) {
            double speedIncreaseFactor = minSpeed / currentSpeed;
            vx *= speedIncreaseFactor; // Increase speed
            vy *= speedIncreaseFactor;
        }

        // Cap the velocity
        final double maxSpeed = 30; // Maximum speed of the ball
        currentSpeed = Math.sqrt(vx * vx + vy * vy);
        if (currentSpeed > maxSpeed) {
            vx = (vx / currentSpeed) * maxSpeed; // Scale velocity to maxSpeed
            vy = (vy / currentSpeed) * maxSpeed;
        }

        // Update position
        x += vx;
        y += vy;

        // Bounce off panel edges (slowing effect and skew)
        final double edgeSlowFactor = 1.95; // Slow factor for edge collisions
        final double skewAmount = 2.6; // Small adjustment to prevent infinite back-and-forth
        if (x <= 0 || x >= panelBounds.width - size) {
            vx = -vx * edgeSlowFactor; // Reverse velocity and apply slow factor
            vy += skewAmount + (Math.random() * 1.5); // Add a slight random skew to vertical velocity
        }
        if (y <= 0 || y >= panelBounds.height - size) {
            vy = -vy * edgeSlowFactor; // Reverse velocity and apply slow factor
            vx += skewAmount + (Math.random() * 1.5); // Add a slight random skew to horizontal velocity
        }

        // Bounce off rectangles
        final double rectangleBoostFactor = 3.2; // Boost factor for bouncing off rectangles
        for (MovableRectangle rect : rectangles) {
            Rectangle rectBounds = rect.getBounds();
            if (rectBounds.intersects(getBounds())) {
                Rectangle intersection = rectBounds.intersection(getBounds());

                // Determine collision direction
                if (intersection.width > intersection.height) {
                    // Vertical collision (top or bottom)
                    vy = -vy * rectangleBoostFactor; // Boost speed
                    y += (vy > 0 ? intersection.height : -intersection.height);
                    vx += skewAmount + (Math.random() * 1.5); // Add a slight random skew to horizontal velocity
                } else {
                    // Horizontal collision (left or right)
                    vx = -vx * rectangleBoostFactor; // Boost speed
                    x += (vx > 0 ? intersection.width : -intersection.width);
                    vy += skewAmount + (Math.random() * 1.5); // Add a slight random skew to vertical velocity
                }
            }
        }
    }







    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval((int) x, (int) y, size, size);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, size, size);
    }
}

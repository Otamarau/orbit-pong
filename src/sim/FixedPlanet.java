package sim;

import java.awt.*;

public class FixedPlanet {
    private int x, y, planetSize;
    private Color color;

    public FixedPlanet(int x, int y, int planetSize, Color color) {
        this.x = x;
        this.y = y;
        this.planetSize = planetSize;
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x - planetSize / 2, y - planetSize / 2, planetSize, planetSize);
    }

    public int getX() {
        return x;
    }

    // Getter for y-coordinate
    public int getY() {
        return y;
    }

    public int getSize() {
        return planetSize;
    }



}
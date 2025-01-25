package sim;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class MovableRectangle {
    private int x, y, width, height; // Position and size
    private final Color color; // Rectangle color
    private final int moveAmount = 10; // Amount to move per key press

    public MovableRectangle(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public void moveUp() {
        y -= moveAmount;
    }

    public void moveDown() {
        y += moveAmount;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

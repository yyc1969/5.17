package view.game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class UnderBoxPanel extends JPanel
{
    private final int GRID_SIZE = 100;
    private int[][] map;

    public UnderBoxPanel(int[][] map)
        {
            this.map = map;
            int width = map[0].length * GRID_SIZE;
            int height = map.length * GRID_SIZE;
            this.setSize(width + 2, height + 2);
        }

    // draw the background of the panel
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // clear the background

// to do the animation
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight()); // fill the panel with grey color

        Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 2); //create an outline
        this.setBorder(border); // draw an outline
    }

    public void setMap(int[][] map) {
        this.map = map;
    }
}

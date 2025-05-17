
// paint the boxes.(color, select situation

package view.game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BoxComponent extends JComponent {
    private Color color;
    private int row;
    private int col;
    private boolean isSelected;

    // new BoxComponent(color, row, col);
    public BoxComponent(Color color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
        isSelected = false;
    }

    // color the boxes, and set the color of selected and unselected boxes' boundary
    @Override
    public void paintComponent(Graphics g) // Graphic g is set by the system, equal to a painting pen
    {
        super.paintComponent(g); // clean up previous content and fill the blank with background color of the component
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
        Border border ;
        if(isSelected){
            border = BorderFactory.createLineBorder(Color.red,3);
        }else {
            border = BorderFactory.createLineBorder(Color.DARK_GRAY, 1);
        }
        this.setBorder(border);
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        this.repaint();
    }

    public void setRow(int row) {
        this.row = row;
    }

    public  void setColor(Color color)
    {
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
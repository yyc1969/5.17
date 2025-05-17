// how to response to mouse and key event

package view.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * This class is only to enable key events.
 */
public abstract class ListenerPanel extends JPanel {

    // constructor
    public ListenerPanel() {
        enableEvents(AWTEvent.KEY_EVENT_MASK); // receive event source from the keyboard
        enableEvents(AWTEvent.MOUSE_EVENT_MASK); // receive event source from the mouse
        this.setFocusable(true);
    }

    // interpret the key event
    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e); // keep the default respond to key-pressed (just in case)
        if (e.getID() == KeyEvent.KEY_PRESSED) // if the key is pressed
        {
            switch (e.getKeyCode()) // find out which key is pressed
            {
                case KeyEvent.VK_RIGHT -> doMoveRight();
                case KeyEvent.VK_LEFT -> doMoveLeft();
                case KeyEvent.VK_UP -> doMoveUp();
                case KeyEvent.VK_DOWN -> doMoveDown();
            }
        }
    }

    // interpret the mouse event
    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_CLICKED) // if the mouse is clicked
        {
            doMouseClick(e.getPoint()); // return the position of where the mouse clicked
// if there is a box...
// if there is nothing...
        }
    }


    // the following are all abstract method to be implemented
    public abstract void doMouseClick(Point point);

    public abstract void doMoveRight();

    public abstract void doMoveLeft();

    public abstract void doMoveUp();

    public abstract void doMoveDown();


}

package view.game;

import model.MapModel;

import javax.swing.*;
import java.awt.*;

public class MovementPanel extends JPanel {
    private JButton btnUp, btnDown, btnLeft, btnRight;
    private JPanel empty1, empty2, empty3, empty4, empty5;
    private MapModel mapmodel;
    private final static int GRID_SIZE = 100;

    // constructor
    public MovementPanel(GamePanel gamePanel)
    {
        this.setVisible(true);
        this.setSize(3 * GRID_SIZE, 3 * GRID_SIZE);
        this.setLayout(new GridLayout(3, 3));

        empty1 = new JPanel();
        this.add(empty1);
//        ImageIcon up = new ImageIcon("/resources/icons/up.png");
//        Image scalesUp = up.getImage().getScaledInstance(GRID_SIZE, GRID_SIZE, Image.SCALE_SMOOTH);
//        ImageIcon scaleUp = new ImageIcon(scalesUp);
        btnUp = new JButton();
        this.add(btnUp);
        empty2 = new JPanel();
        this.add(empty2);
        btnLeft = new JButton();
        this.add(btnLeft);
        empty3 = new JPanel();
        this.add(empty3);
        btnRight = new JButton();
        this.add(btnRight);
        empty4 = new JPanel();
        this.add(empty4);
        btnDown = new JButton();
        this.add(btnDown);
        empty5 = new JPanel();
        this.add(empty5);

        btnUp.addActionListener(e -> {
            gamePanel.doMoveUp();
        });
        btnDown.addActionListener(e -> {
            gamePanel.doMoveDown();
        });
        btnLeft.addActionListener(e -> {
            gamePanel.doMoveLeft();
        });
        btnRight.addActionListener(e -> {
            gamePanel.doMoveRight();
        });
    }

    public JButton getBtnRight() {
        return btnRight;
    }
}

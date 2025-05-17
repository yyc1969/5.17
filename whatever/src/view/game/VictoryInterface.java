// say "Game Win!" and a bottom to end the program operation

package view.game;

import view.FrameUtil;

import javax.swing.*;
import java.awt.*;

public class VictoryInterface extends JFrame {
    private JButton btnEndProgram;
    private  JLabel labelGameWin;
    private JPanel gamePanel;

    public VictoryInterface(GamePanel gamePanel)
    {
        this.setTitle("Game Win Interface");
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(280, 280);
        this.setVisible(false);
        this.gamePanel = gamePanel;

        btnEndProgram = FrameUtil.createButton(this, "OK", new Point(this.getWidth() / 2 - 40,this.getHeight() / 2 + 20), 80, 40);
        labelGameWin = FrameUtil.createJLabel(this, new Point(this.getWidth() / 2 - 40,this.getHeight() / 2 - 50), 80, 40, "");

        btnEndProgram.addActionListener(e ->
        {
            // end the whole program operation
            System.exit(0);
        });
    }

    public JLabel getLabelGameWin()
    {
        return labelGameWin;
    }

    public JPanel getGamePanel()
    {
        return gamePanel;
    }
}

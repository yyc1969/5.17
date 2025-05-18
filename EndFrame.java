package view.game;

import view.FrameUtil;
import view.login.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EndFrame extends JFrame
{
    private  GameFrame gameFrame;
    public static ArrayList<LevelInterface> levelInterfaces = LoginFrame.levelInterfaces;

    private JButton restartBtn;
    private JButton changeLevelBtn;
    private JButton endGameBtn;
    private JLabel message;
    private JLabel imformation;
    private JWindow jWindow;
    private int timeMode;

    public EndFrame(GameFrame gameFrame)
    {
        // if time's up, bounce out a frame
        super("Time's up");
        this.setSize(300,280);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        restartBtn = FrameUtil.createButton(this,"Restart", new Point(10, 170), 80, 40);
        changeLevelBtn = FrameUtil.createButton(this, "Change Level", new Point(100, 170), 80, 40);
        endGameBtn = FrameUtil.createButton(this, "End Game", new Point(190, 170), 80, 40);
        message = FrameUtil.createJLabel(this, new Point(40, 30), 200, 40,"   Time's up!");
        imformation = FrameUtil.createJLabel(this, new Point(40, 80), 200, 40, String.format("Steps: %d Time: %02d: %02d",
                gameFrame.getGamePanel().getSteps(), gameFrame.getTimeUsed() / 60, gameFrame.getTimeUsed() % 60));

        restartBtn.addActionListener(e ->
        {
            this.setVisible(false);
            jWindow.toFront();
            gameFrame.toFront();
            gameFrame.setVisible(true);
            gameFrame.getController().restartGame();
            gameFrame.getGamePanel().requestFocusInWindow();//enable key listener
            gameFrame.setTimeUsed(0);
            if (gameFrame.getTimeMode() == 1)
            {
                gameFrame.getTimeLabel().setText(String.format("Time: %02d : %02d", gameFrame.getTimeAttack() / 60, gameFrame.getTimeAttack() % 60));
            }
            else if (gameFrame.getTimeMode() == 0)
            {
                gameFrame.getTimeLabel().setText("Time: 00 : 00");
            }
            gameFrame.getTimer().restart();

        });

        endGameBtn.addActionListener(e ->
        {
            System.exit(0);
        });

        changeLevelBtn.addActionListener(e ->
        {
            gameFrame.getController().restartGame();
            this.setVisible(false);
            jWindow.toFront();
            for (int i = LevelInterface.LevelNumber - 2; i >= 0; i--)
            {
                levelInterfaces.get(i).toFront();
                levelInterfaces.get(i).setVisible(true);
            }
        });
    }

    public JLabel getImformation() {
        return imformation;
    }

    public JLabel getMessage() {
        return message;
    }

    public void setGameFrame(GameFrame gameFrame)
    {
        this.gameFrame = gameFrame;
    }

    public static void setLevelInterfaces(ArrayList<LevelInterface> levelInterfaces) {
        EndFrame.levelInterfaces = levelInterfaces;
    }

    public void setJWindow(JWindow jWindow) {
        this.jWindow = jWindow;
    }

    public void setTimeMode(int timeMode) {
        this.timeMode = timeMode;
    }
}

// a huge constructor, set up the window of the game
// how the buttons on the frame work remain to be done

package view.game;

import com.sun.tools.javac.Main;
import controller.GameController;
import model.MapModel;
import model.User;
import view.FrameUtil;
import view.DigitalTimerLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {

    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;
    private JButton withdrawBtn;

    //add
    private JButton saveBtn;
    private User user;

    private JLabel userLabel;
    private DigitalTimerLabel advancedUserLabel;

    // transmit some variable to panel
    private Timer timer; //
    private int timeUsed = 0; //

    // advanced step label
    private DigitalTimerLabel advancedStepLabel;

//    private JLabel stepLabel;
    private GamePanel gamePanel;
    private MovementPanel movementPanel;
    private JPanel exitPanel;
    private JWindow jWindow; //

    // time mode

    private int timeAttack;
    private static  int[] timeAttacks = {10, 3, 180, 240, 300};

    // change the timeLabel
    private JLabel timeLabel;
    private EndFrame endFrame;
    private GameFrame gameFrame;
    private int level;
    private int timeMode; // timeMode = 0: count up; timeMode = 1: count down

    // prop
    private JButton propBtn;
    private int propTime;

    // try an advanced TimeLabel
    private DigitalTimerLabel advancedTimeLabel;

    // constructor, new GameFrame(width, height, mapMadel)
    public GameFrame(int width, int height, MapModel mapModel, User user) {
// the title can be modified
        this.setTitle("Klotski Puzzle");
        this.setLayout(null); // have no layout manager
        this.setSize(width, height);

        gamePanel = new GamePanel(mapModel);
        movementPanel = new MovementPanel(gamePanel);
        movementPanel.setLocation(700, 250);
        movementPanel.setBackground(Color.BLACK);
        this.add(movementPanel);
        int gamePanelX = 60;
        gamePanel.setLocation(80, 130); // the y coordinate is dependent on the size of the frame and panel

// to do the animation
        FrameUtil.setPanelImage(gamePanel, "gamePanel.png");

        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel);

        //add
        this.user = user;
//        this.userLabel = FrameUtil.createJLabel(this,(user != null) ? user.getUsername() : "Guest", new Font("serif", Font.ITALIC, 22), new Point(300, 80), 180, 50);
        advancedUserLabel = new DigitalTimerLabel(Color.YELLOW, 22);
        // 750, 730
        advancedUserLabel.setLocation(300, 80);
        // 270, 80
        advancedUserLabel.setSize(180, 50);
        advancedUserLabel.setFontSize(22);
        if (user == null)
        {
            advancedUserLabel.setText("Guest");
        }
        else
        {
            advancedUserLabel.setText(String.format("%s", "User: " + user.getUsername()));
        }
        this.add(advancedUserLabel);

        this.saveBtn = FrameUtil.createButton(this, "Save", new Point(200, 580), 80, 40);

        // tell the user where the exit is
        exitPanel = new JPanel();
        exitPanel.setVisible(true);
        exitPanel.setSize(40, 2 * gamePanel.getGRID_SIZE());
        exitPanel.setLocation(gamePanel.getX() + gamePanel.getWidth(), gamePanel.getY() + gamePanel.getWidth() / 4);
        exitPanel.setBackground(Color.cyan);
        this.add(exitPanel);

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(100, 80), 80, 40);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(200,80), 80, 40);
        this.withdrawBtn = FrameUtil.createButton(this, "Back", new Point(100, 580), 80, 40);

//        this.stepLabel = FrameUtil.createJLabel(this, "Start", new Font("serif", Font.ITALIC, 22), new Point(650, 80), 180, 50);
        this.advancedStepLabel = new DigitalTimerLabel(Color.YELLOW, 22);
        advancedStepLabel.setText("Start");
        // 400, 150
        advancedStepLabel.setSize(180, 50);
        // 650, 80
        advancedStepLabel.setLocation(650, 80);
        add(advancedStepLabel);

        gamePanel.setAdvancedStepLabel(advancedStepLabel);
        gamePanel.setjWindow(jWindow);

        // advanced time label
        if (level == 0)
        {
            level = 1;
        }
        this.timeAttack = timeAttacks[level - 1];
        advancedTimeLabel = new DigitalTimerLabel(Color.GREEN, 22);
        // 600, 100
        advancedTimeLabel.setSize(180, 50);
        add(advancedTimeLabel);
        // 550, 600
        advancedTimeLabel.setLocation(800, 80);
        this.getContentPane().setBackground(Color.BLACK);


//        if (timeMode == 0)
//        {
//            advancedTimeLabel.startCountdown(timeAttack);
//        }
//        if (timeMode == 1)
//        {
//            advancedTimeLabel.startCountUp();
//        }

        // consider the GameFrame created in Main
        if (level == 0)
        {
            level = 1;
        }
        // set a label to show time used
//        timeLabel = FrameUtil.createJLabel(this,String.format("Time: %02d : %02d", timeAttack / 60, timeAttack % 60), new Font("serif", Font.ITALIC, 22), new Point(800, 80),180, 50);
//        this.add(timeLabel);
        endFrame = new EndFrame(this);
        endFrame.setTimeMode(timeMode);
        gamePanel.setEndFrame(endFrame);
        gamePanel.setGameFrame(this);

        gamePanel.setTimeMode(timeMode);

        // prop
        propBtn = FrameUtil.createButton(this, "Prop", new Point(300, 580), 80, 40);
        this.propTime = 0;

        // int the field of an inner class in the public class, I should create a variable to substitute "this"
        this.gameFrame = this;
        // set a timer
        timer = new Timer(1000, new ActionListener()
        {
            int min;
            int sec;
            @Override
            public void actionPerformed(ActionEvent e)
            {
                timeUsed++;
                gamePanel.setTimeUsed(timeUsed);
                if (timeMode == 1)
                {
                    min = (timeAttack - timeUsed) / 60;
                    sec = (timeAttack - timeUsed) % 60;
//                    timeLabel.setText(String.format("Time: %02d : %02d", min, sec));
                    advancedTimeLabel.setText(String.format("Time: %02d : %02d", min, sec));
                    if (timeAttack - timeUsed <= 5) {
                        setForeground(Color.ORANGE); // 最后5秒警告
                    }
                    if (timeUsed == timeAttack)
                    {
                        timer.stop();
                        endFrame.getImformation().setText( String.format("Steps: %d Time: %02d: %02d",
                                gameFrame.getGamePanel().getSteps(), gameFrame.getTimeUsed() / 60, gameFrame.getTimeUsed() % 60));
                        endFrame.setVisible(true);
                        gameFrame.setVisible(false);
                    }
                }
                else
                {
                    min = timeUsed / 60;
                    sec = timeUsed % 60;
//                    timeLabel.setText(String.format("Time: %02d : %02d", min, sec));
                    advancedTimeLabel.setText(String.format("Time: %02d : %02d", min, sec));
                }
            }
        });
        gamePanel.setTimer(timer);

        this.restartBtn.addActionListener(e -> // e for event, induced by the system
        {

            propTime = 0;

            int[][] initialMatrix = new int[LevelInterface.levelMatrixs[level - 1].length][LevelInterface.levelMatrixs[level - 1][0].length];
            MapModel.copyMatrix(LevelInterface.levelMatrixs[level - 1], initialMatrix);
            gamePanel.getModel().setMatrix(initialMatrix);
            gamePanel.initialGame();
            if (timeMode == 1)
            {
//                timeLabel.setText(String.format("Time: %02d : %02d", timeAttack / 60, timeAttack % 60));
                advancedTimeLabel.setText(String.format("Time: %02d : %02d", timeAttack / 60, timeAttack % 60));
            }
            else if (timeMode == 0)
            {
//                timeLabel.setText(String.format("Time: %02d : %02d", 0, 0));
                advancedTimeLabel.setText(String.format("Time: %02d : %02d", 0, 0));
            }
            timeUsed = 0;
            timer.restart();
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
        });

// this loadBtn.addActionListener remains to be complete (store the information, i.e. mapModel, path)
        this.loadBtn.addActionListener(e -> {
            if (isGuest()) {
                JOptionPane.showMessageDialog(this, "Guests cannot save games!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String currentMapType = getCurrentMapType();

            MapModel loadedModel = controller.loadGame(user, currentMapType);
            timeUsed = controller.loadGameTime(user, currentMapType);
            if (loadedModel != null) {
                // 更新游戏界面
                gamePanel.updateGameModel(loadedModel);
                gamePanel.setSteps(loadedModel.getSteps());
//                gamePanel.getStepLabel().setText(String.format("Step: %d", loadedModel.getSteps()));
                gamePanel.getAdvancedStepLabel().setText(String.format("Step: %d", loadedModel.getSteps()));
                if (timeMode == 0)
                {
//                    timeLabel.setText(String.format("TIme: %02d : %02d", timeUsed / 60, timeUsed % 60));
                    advancedTimeLabel.setText(String.format("TIme: %02d : %02d", timeUsed / 60, timeUsed % 60));
                }
                else if (timeMode == 1)
                {
//                    timeLabel.setText(String.format("TIme: %02d : %02d", timeAttack - timeUsed / 60, timeAttack - timeUsed % 60));
                    advancedTimeLabel.setText(String.format("TIme: %02d : %02d", timeAttack - timeUsed / 60, timeAttack - timeUsed % 60));
                }
                timer.restart();

                JOptionPane.showMessageDialog(this, "Game loaded successfully from " + currentMapType + "!");
            } else {
                JOptionPane.showMessageDialog(this, "No saved game found for " + currentMapType, "Info", JOptionPane.INFORMATION_MESSAGE);
            }

            gamePanel.requestFocusInWindow();//enable key listener, 键盘

        });

        //add save button
        this.saveBtn.addActionListener( e -> {
            if (isGuest()) {
                JOptionPane.showMessageDialog(this, "Guests cannot save games!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String currentMapType = getCurrentMapType();// 需要实现这个方法

            // 获取当前游戏状态
            MapModel currentModel = gamePanel.getModel();
            currentModel.setSteps(gamePanel.getSteps());
            currentModel.setMapType(currentMapType); // 设置地图类型到模型

            if (controller.saveGame(user,currentModel, timeUsed)) {
                JOptionPane.showMessageDialog(this, "Game saved successfully to " + currentMapType + "!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save game", "Error", JOptionPane.ERROR_MESSAGE);
            }


            gamePanel.requestFocusInWindow();//enable key listener, 键盘

        });


        // get back to last state, and step --
        this.withdrawBtn.addActionListener(e ->{
            System.out.println("Withdraw");
            int[][] lastState = new int[gamePanel.getModel().getMatrix().length][gamePanel.getModel().getMatrix()[0].length];
            if (gamePanel.getStates().size() == 1) // the target last state is the initial state
            {
                lastState = gamePanel.getModel().getMatrixInitial();
            }
            else if (gamePanel.getStates().size() > 1)
            {
                // the last step is unwanted
                gamePanel.getStates().removeLast();
                lastState = gamePanel.getStates().getLast();
            }
            if (lastState != null) // when steps > 0
            {
                for ( int i = 0; i < gamePanel.getBoxes().size(); i++)
                {
                    gamePanel.remove(gamePanel.getBoxes().get(i));
                }
                gamePanel.getBoxes().clear();
                MapModel.copyMatrix(lastState, gamePanel.getModel().getMatrix());
                if (gamePanel.getSteps() > 0)
                {
                    gamePanel.setSteps(gamePanel.getSteps() - 1);
//                    gamePanel.getStepLabel().setText(String.format("Step: %d", gamePanel.getSteps()));
                    gamePanel.getAdvancedStepLabel().setText(String.format("Step: %d", gamePanel.getSteps()));
                }
                gamePanel.initialGame();
            }
        });

        propBtn.addActionListener(e ->
        {
            if (propTime == 0)
            {
                propTime++;
                JOptionPane.showMessageDialog(this, "You have 3 prop opportunities. This is the 1 one.", null, JOptionPane.INFORMATION_MESSAGE);
                for (int i = 0; i < gamePanel.getBoxes().size(); i++)
                {
                    if (gamePanel.getBoxes().get(i).getBoxType() == 2)
                    {
                        BoxComponent boxTonBeRemoved = gamePanel.getBoxes().get(i);
                        int col = boxTonBeRemoved.getCol();
                        int row = boxTonBeRemoved.getRow();
                        gamePanel.getBoxes().remove(i);
                        gamePanel.getModel().getMatrix()[row][col] = 0;
                        gamePanel.getModel().getMatrix()[row][col + 1] = 0;
                        gamePanel.removeAll();
                        gamePanel.initialGame();
                        return;
                    }
                }
            }

            if (propTime == 1)
            {
                propTime++;
                JOptionPane.showMessageDialog(this, "You have 3 prop opportunities. This is the 2 one.", null, JOptionPane.INFORMATION_MESSAGE);
                for (int i = 0; i < gamePanel.getBoxes().size(); i++)
                {
                    if (gamePanel.getBoxes().get(i).getBoxType() == 1)
                    {
                        BoxComponent boxTonBeRemoved = gamePanel.getBoxes().get(i);
                        int col = boxTonBeRemoved.getCol();
                        int row = boxTonBeRemoved.getRow();
                        gamePanel.getBoxes().remove(i);

                        gamePanel.getModel().getMatrix()[row][col] = 0;
                        printMatrix(gamePanel.getModel().getMatrix());

                        gamePanel.removeAll();
                        gamePanel.initialGame();
                        return;
                    }
                }
            }
            if (propTime == 2)
            {
                propTime++;
                JOptionPane.showMessageDialog(this, "You have 3 prop opportunities. This is the 3 one.", null, JOptionPane.INFORMATION_MESSAGE);
                for (int i = 0; i < gamePanel.getBoxes().size(); i++)
                {
                    if (gamePanel.getBoxes().get(i).getBoxType() == 2)
                    {
                        BoxComponent boxTonBeRemoved = gamePanel.getBoxes().get(i);
                        int col = boxTonBeRemoved.getCol();
                        int row = boxTonBeRemoved.getRow();
                        gamePanel.getBoxes().remove(i);
                        gamePanel.getModel().getMatrix()[row][col] = 0;
                        gamePanel.getModel().getMatrix()[row][col + 1] = 0;
                        gamePanel.removeAll();
                        gamePanel.initialGame();
                        return;
                    }
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "You have used up prop opportunities!", null, JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //todo: add other button here
        this.setLocationRelativeTo(null); // the window is placed in the centre of the screen
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // set the location of the victoryInterface
//        getGamePanel().getVictoryInterface().setLocationRelativeTo(getMovementPanel().getBtnRight());
    }

    public static void printMatrix(int[][] matrix)
    {
        for ( int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                System.out.printf("%d ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    //add
    public boolean isGuest() {
        return user == null;
    }

    private String getCurrentMapType() {
        // 从MapModel中获取地图类型
        if (gamePanel != null && gamePanel.getModel() != null) {
//            String mapType = gamePanel.getModel().getMapType();
            String mapType = LevelInterface.MAP_TYPES[level - 1];
            if (mapType != null) {
                return mapType;
            }
        }
        return LevelInterface.MAP_TYPES[0];
    }

    public User getUser() {
        return user;
    }

    public GamePanel getGamePanel()
    {
        return gamePanel;
    }

    public MovementPanel getMovementPanel()
    {
        return movementPanel;
    }

    public Timer getTimer()
    {
        return timer;
    }

    public GameController getController()
    {
        return controller;
    }

    public int getTimeAttack() {
        return timeAttack;
    }

    public int getTimeUsed() {
        return timeUsed;
    }

    public EndFrame getTimesUpFrame() {
        return endFrame;
    }

    public static int[] getTimeAttacks() {
        return timeAttacks;
    }

    public int getTimeMode() {
        return timeMode;
    }

    public DigitalTimerLabel getAdvancedTimeLabel() {
        return advancedTimeLabel;
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

    public void setjWindow(JWindow jWindow) {
        this.jWindow = jWindow;
    }

    public void setTimeAttack(int timeAttack) {
        this.timeAttack = timeAttack;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setTimeMode(int timeMode) {
        this.timeMode = timeMode;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

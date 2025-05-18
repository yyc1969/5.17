package view.game;

import model.MapModel;
import model.User;
import view.FrameUtil;
import view.login.LoginFrame;
import view.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class LevelInterface extends JFrame{
    private JButton lastLevelBtn;
    private JButton nextLevelBtn;
    private JButton normalModeGameBtn;
    private JButton timeAttackModeBtn;
    private JPanel levelPanel;
    private JPanel levelNamePanel;
    private ArrayList<Location> locations;
    ArrayList<LevelInterface> levelInterfaces;
    private GameFrame levelGameFrame;

    private User user;

    public static final String[] MAP_TYPES = {
            "map1_classic",
            "map2_easy",
            "map3_medium",
            "map4_hard",
            "map5_expert"
    };


    // multilevel
    public static int[][][] levelMatrixs = {
            {{10, 20, 20, 13}, {11, 30, 21, 21}, {12, 30, 40, 40}, {0, 0, 40, 40}},
            {{21, 21, 20, 20, 10}, {40, 40, 30, 12, 0}, {40, 40, 30, 13, 0}, {23, 23, 22, 22, 11}},
            {{0, 20, 20, 10, 11}, {40, 40, 21, 21, 12}, {40, 40, 22, 22, 30}, {0, 23, 23, 13, 30}},
            {{20, 20, 10, 21, 21}, {40, 40, 11, 30, 0}, {40, 40, 12, 30, 0}, {22, 22, 13, 23, 23}},
//            {{10, 11, 20, 20, 0}, {40, 40, 21, 21, 30}, {40, 40, 22, 22, 30}, {12, 13, 23, 23, 0}}
            {{0, 11, 0, 0, 0}, {40, 40, 21, 21, 30}, {40, 40, 22, 22, 30}, {0, 13, 23, 23, 0}} // for code testing
    };
    private int[][] levelMatrix;

    private int level;
    static int LevelNumber = 1; // level = 1, 2, 3, 4, 5

    // change the boxes' position
    private GamePanel gamePanel;
    private GameFrame gameFrame;
    private JWindow jWindow;
    private LevelInterface levelInterface;

    public LevelInterface(LoginFrame loginFrame) {
        this.setLayout(null);
        this.setSize(1200, 900);
        this.setTitle("Level Choosing");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.locations = loginFrame.getLocations();
        this.levelInterfaces = loginFrame.getLevelInterfaces();
        this.level = LevelNumber;
        this.levelMatrix = levelMatrixs[level - 1];
        this.levelInterface = this;
        LevelNumber++;

        lastLevelBtn = FrameUtil.createButton(this, "", new Point(20, this.getHeight() / 2 - 80), 40, 80);
        nextLevelBtn = FrameUtil.createButton(this, "", new Point(getWidth() - 100, getHeight() / 2 - 80), 40, 80);
        normalModeGameBtn = FrameUtil.createButton(this, "Normal Mode", new Point(getWidth() / 2 - 260, getHeight() / 2 + 310), 240, 80);
        timeAttackModeBtn = FrameUtil.createButton(this, "Time Attack Mode", new Point(getWidth() / 2 + 20, getHeight() / 2 + 310), 240, 80);
        levelPanel = FrameUtil.createPanel(this, new Point(getWidth() / 2 - 350, getHeight() / 2 - 300), 720, 600, Color.LIGHT_GRAY);
        levelNamePanel = FrameUtil.createPanel(this, new Point(getWidth() / 2 - 400, getHeight() / 2 - 430), 800, 100, Color.DARK_GRAY);


        // the same action done after normal mode button and the time attack mode button are clicked
        Action startGame = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // change the map
                // the initial gameFrame create in Main is substituted by levelGameFrame
                MapModel newModel = new MapModel(levelMatrix);// 创建新地图模型并设置地图类型
                newModel.setMapType(getCurrentMapType()); // 设置当前地图类型
                levelGameFrame = new GameFrame(gameFrame.getWidth(), gameFrame.getHeight(), new MapModel(levelMatrix), user);
                levelGameFrame.setjWindow(jWindow);
                levelGameFrame.getTimesUpFrame().setJWindow(jWindow);
                levelGameFrame.setTimeAttack(GameFrame.getTimeAttacks()[levelInterface.level - 1]);
                levelGameFrame.setLevel(levelInterface.level);
                levelGameFrame.getTimer().start();
                levelInterface.setVisible(false); // hide the register page
                for (int i = 0; i < LevelNumber - 1; i++)
                {
                    loginFrame.getLevelInterfaces().get(i).setVisible(false);
                }
            }
        };

        normalModeGameBtn.addActionListener(e ->
        {
            startGame.actionPerformed(e);
            levelGameFrame.setTimeMode(0);
            // There is no correct timeAttack when newing levelGameFrame
            levelGameFrame.getTimeLabel().setText(String.format("Time: %02d : %02d", 0, 0));
            levelGameFrame.setVisible(true); // turn to the game-start frame
            jWindow.toFront();
            levelGameFrame.toFront();
        });

        timeAttackModeBtn.addActionListener(e ->
        {
            startGame.actionPerformed(e);
            levelGameFrame.setTimeMode(1);
            // There is no correct timeAttack when newing levelGameFrame
            levelGameFrame.getTimeLabel().setText(String.format("Time: %02d : %02d", levelGameFrame.getTimeAttack() / 60, levelGameFrame.getTimeAttack() % 60));
            levelGameFrame.setVisible(true); // turn to the game-start frame
            jWindow.toFront();
            levelGameFrame.toFront();
        });

        nextLevelBtn.addActionListener(e ->{
        if (getLevel() != 5) // if there is a next level to choose
        {
            levelInterfaces.get(getLevel() - 1).setVisible(false);
            levelInterfaces.get(getLevel()).setVisible(false);
            levelInterfaces.get(getLevel() - 1).setVisible(true);
            levelInterfaces.get(getLevel()).setVisible(true);
        }
        });

        lastLevelBtn.addActionListener(e ->{
            if (getLevel() != 1)
            {
                levelInterfaces.get(getLevel() - 1).setVisible(false);
                levelInterfaces.get(getLevel() - 2).setVisible(false);
                levelInterfaces.get(getLevel() - 1).setVisible(true);
                levelInterfaces.get(getLevel() - 2).setVisible(true);
            }
        });
    }

    public GamePanel getGamePanel()
    {
        return gamePanel;
    }

    public GameFrame getGameFrame() {return gameFrame;}

    public String getCurrentMapType(){
        if (level >= 1 && level <= 5){
            return MAP_TYPES[level - 1];
        }
        return MAP_TYPES[0];
    }

    public void setGameFrame(GameFrame gameFrame)
    {
        this.gameFrame = gameFrame;
    }

    public void setGamePanel(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setjWindow(JWindow jWindow) {
        this.jWindow = jWindow;
    }

    public int getLevel()
    {
        return level;
    }

    public JPanel getLevelNamePanel()
    {
        return levelNamePanel;
    }

    public JPanel getLevelPanel()
    {
        return levelPanel;
    }
}

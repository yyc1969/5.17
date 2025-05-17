package view.game;

import model.MapModel;
import model.User;
import view.FrameUtil;
import view.login.LoginFrame;
import view.Location;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LevelInterface extends JFrame{
    private JButton lastLevelBtn;
    private JButton nextLevelBtn;
    private JButton startGameBtn;
    private JPanel levelPanel;
    private JPanel levelNamePanel;
    private ArrayList<Location> locations;
    private ArrayList<LevelInterface> levelInterfaces;
    public static final String[] MAP_TYPES = {
            "map1_classic",
            "map2_easy",
            "map3_medium",
            "map4_hard",
            "map5_expert"
    };

    // multilevel
    private int[][][] levelMatrixs = {
            {{10, 20, 20, 13}, {11, 30, 21, 21}, {12, 30, 40, 40}, {0, 0, 40, 40}},
            {{21, 21, 20, 20, 10}, {40, 40, 30, 12, 0}, {40, 40, 30, 13, 0}, {23, 23, 22, 22, 11}},
            {{0, 20, 20, 10, 11}, {40, 40, 21, 21, 12}, {40, 40, 22, 22, 30}, {0, 23, 23, 13, 30}},
            {{20, 20, 10, 21, 21}, {40, 40, 11, 30, 0}, {40, 40, 12, 30, 0}, {22, 22, 13, 23, 23}},
            {{10, 11, 20, 20, 0}, {40, 40, 21, 21, 30}, {40, 40, 22, 22, 30}, {12, 13, 23, 23, 0}}
    };
    private int[][] levelMatrix;

    private int level;
    static int LevelNumber = 1; // level = 1, 2, 3, 4, 5

    // change the boxes' position
    private GamePanel gamePanel;
    private GameFrame gameFrame;
    private User uesr;

    public LevelInterface(LoginFrame loginFrame) {
        this.setLayout(null);
        this.setSize(600, 450);
        this.setTitle("Level Choosing");
        this.gamePanel = gamePanel;
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.locations = loginFrame.getLocations();
        this.levelInterfaces = loginFrame.getLevelInterfaces();
        this.level = LevelNumber;
        this.levelMatrix = levelMatrixs[level - 1];
        LevelNumber++;

        lastLevelBtn = FrameUtil.createButton(this, "", new Point(10, this.getHeight() / 2 - 40), 20, 40);
        nextLevelBtn = FrameUtil.createButton(this, "", new Point(getWidth() - 50, getHeight() / 2 - 40), 20, 40);
        startGameBtn = FrameUtil.createButton(this, "Start Game", new Point(getWidth() / 2 - 60, getHeight() / 2 + 135), 120, 40);
        levelPanel = FrameUtil.createPanel(this, new Point(getWidth() / 2 - 250, getHeight() / 2 - 125), 480, 250, Color.LIGHT_GRAY);
        levelNamePanel = FrameUtil.createPanel(this, new Point(getWidth() / 2 - 200, getHeight() / 2 - 185), 400, 50, Color.DARK_GRAY);

        startGameBtn.addActionListener(e -> {
            if (uesr != null) {
                if (this.gameFrame != null) {
                    // change the map
                    MapModel newModel = new MapModel(levelMatrix);// 创建新地图模型并设置地图类型
                    newModel.setMapType(getCurrentMapType()); // 设置当前地图类型
                    GameFrame levelGameFrame = new GameFrame(gameFrame.getWidth(), gameFrame.getHeight(), new MapModel(levelMatrix), gameFrame.getUser());
                    //levelGameFrame.getUserLabel() = FrameUtil.createJLabel(this,(user != null) ? user.getUsername() : "Guest", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 18), 180, 50);
                    levelGameFrame.setVisible(true); // turn to the game-start frame
                    this.setVisible(false); // hide the register page
                    for (int i = 0; i < LevelNumber - 1; i++) {
                        loginFrame.getLevelInterfaces().get(i).setVisible(false);
                    }
                }
            }
            else {
                if (this.gameFrame != null) {
                    GameFrame levelGameFrame = new GameFrame(gameFrame.getWidth(), gameFrame.getHeight(), new MapModel(levelMatrix), gameFrame.getUser());
                    levelGameFrame.setVisible(true); // turn to the game-start frame
                    this.setVisible(false); // hide the register page
                    for (int i = 0; i < LevelNumber - 1; i++) {
                        loginFrame.getLevelInterfaces().get(i).setVisible(false);
                    }
                }
            }

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

    public String getCurrentMapType(){
        if (level >= 1 && level <= 5){
            return MAP_TYPES[level - 1];
        }
        return MAP_TYPES[0];
    }

    public GamePanel getGamePanel()
    {
        return gamePanel;
    }

    public GameFrame getGameFrame() {return gameFrame;}

    public void setGameFrame(GameFrame gameFrame)
    {
        this.gameFrame = gameFrame;
    }

    public void setGamePanel(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
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
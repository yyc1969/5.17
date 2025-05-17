// initialize the register window
// function the "confirm" button (to be done), and the "restart" button.

package view.login;

import model.User;
import view.FrameUtil;
import view.Location;
import view.game.GameFrame;
import view.game.LevelInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class LoginFrame extends JFrame {
    private JTextField username;
    private JPasswordField password;
    private JButton startGameBtn;
    private JButton resetBtn;
    private GameFrame gameFrame;

    private ArrayList<LevelInterface> levelInterfaces;
    private ArrayList<Location> locations;
    int levelNum = 5;

    // define the locations
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;
    int levelInterfaceWidth = 600;
    int levelInterfaceHeight = 450;
    int spacing = 40;
    int firstX = screenWidth / 2 - levelInterfaceWidth / 2 - spacing * 2;
    int firstY = screenHeight / 2 - levelInterfaceHeight / 2;

    //constructor, a window
    public LoginFrame(int width, int height, GameFrame gameframe) {
        this.setTitle("Login Frame");
        this.setLayout(null);
        this.setSize(width, height);
        JLabel userLabel = FrameUtil.createJLabel(this, new Point(50, 20), 70, 40, "username:");
        JLabel passLabel = FrameUtil.createJLabel(this, new Point(50, 80), 70, 40, "password:");
        username = FrameUtil.createJTextField(this, new Point(120, 20), 120, 40);
        password = FrameUtil.createJPasswordField(this, new Point(120, 80), 120, 40);
        this.gameFrame = gameframe;

        startGameBtn = FrameUtil.createButton(this, "Confirm", new Point(40, 140), 100, 40);
        resetBtn = FrameUtil.createButton(this, "Reset", new Point(160, 140), 100, 40);

        // create levelInterfaces
        levelInterfaces = new ArrayList<>();
        for (int i = 0; i < levelNum; i++)
        {
            setLevelInterface();
        }

        // set the locations
        ArrayList<Location> locations = new ArrayList<>();
        int x = firstX;
        int y = firstY;
        Location location;
        for (int i = 0; i < levelNum; i++)
        {
            location = new Location();
            location.setX(x);
            location.setY(y);
            locations.add(location);
            x += spacing;
        }

        // after the summit button is pressed
        startGameBtn.addActionListener(e -> {
            String inputUsername = username.getText().trim();
            String inputPassword = password.getText().trim();
             //检查输入是否为空
            if(username.getText().equals("") || inputUsername.equals("") || inputPassword.equals("")) {
                JOptionPane.showMessageDialog(this, "username or password can not be null!", "illegal", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //检查用户是否已注册
            if(!isUserRegistered(inputUsername)) {
                JOptionPane.showMessageDialog(this, "unregisted username", "illegal", JOptionPane.ERROR_MESSAGE);
                username.setText("");
                password.setText("");
                return;
            }

            //验证用户名与密码是否匹配
            if(!validateUser(inputUsername, inputPassword)) {
                JOptionPane.showMessageDialog(this, "wrong password", "illegal", JOptionPane.ERROR_MESSAGE);
                password.setText("");
                return;
            }

            User user = new User(inputUsername, inputPassword);

            gameframe.setVisible(true);
            this.setVisible(false);

                // set the location of the levelInterfaces
                for (int i = levelNum - 1; i >= 0; i--)
                {
                    levelInterfaces.get(i).setLocation(new Point(locations.get(i).getX(), locations.get(i).getY()));
                    levelInterfaces.get(i).setVisible(true);
                }

                // set image of levelInterfaces
                setPanelImage(levelInterfaces.get(0).getLevelPanel(), "beginerlevel.png");
                setPanelImage(levelInterfaces.get(1).getLevelPanel(), "hengdaolima.png");
                setPanelImage(levelInterfaces.get(2).getLevelPanel(), "jiangyongcaoying.png");
                setPanelImage(levelInterfaces.get(3).getLevelPanel(), "qitoubingjin.png");
                setPanelImage(levelInterfaces.get(4).getLevelPanel(), "zuoyoububing.png");

                // set the name of the level
//            setPanelImage(levelInterface1.getLevelNamePanel(), "black.png");

                // set the location of the VictoryFrame，why it acts as if this.getMovementPanel.getBtnRight = null
                this.gameFrame.getGamePanel().getVictoryInterface().setLocationRelativeTo(this.gameFrame.getMovementPanel().getBtnRight());
            });

        // if the reset button is pressed,clean up the textField
        resetBtn.addActionListener(e -> {
            username.setText("");
            password.setText("");
        });

        this.setLocationRelativeTo(null); // the register frame is in the middle of the screen
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setPanelImage(JPanel JPanelTargetPanel, String imageDocumentName)
    {
        // get the original image file from the resources document
        InputStream imageFile = getClass().getClassLoader().getResourceAsStream(imageDocumentName);
        try
        {
            BufferedImage originalImage = ImageIO.read(imageFile);

            // squeeze the original image
            int scale = Math.max(originalImage.getWidth() / JPanelTargetPanel.getWidth(), originalImage.getHeight() / JPanelTargetPanel.getHeight()) + 1;
            // if the original image is smaller than the panel, draw the original size image
            if (scale == 0)
            {
                scale = 1;
            }
            int targetWidth = originalImage.getWidth() / scale;
            int targetHeight = originalImage.getHeight() / scale;
            // put the image to the targetPanel
            Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImage);
            JLabel imageContainer = new JLabel(imageIcon);
            JPanelTargetPanel.add(imageContainer);

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }



    public void setLevelInterface()
    {
        // let the LevelInterface have the variable of gameFrame
        LevelInterface ALevelInterface = new LevelInterface(this);
        ALevelInterface.setGameFrame(gameFrame);
        ALevelInterface.setGamePanel(gameFrame.getGamePanel());
        levelInterfaces.add(ALevelInterface);
    }

    public ArrayList<Location> getLocations()
    {
        return locations;
    }

    public ArrayList<LevelInterface> getLevelInterfaces()
    {
        return levelInterfaces;
    }

    private boolean isUserRegistered(String username) {
        Path userDir = Paths.get("user_data", username);
        Path userFile = userDir.resolve("user_info.dat");
        return Files.exists(userFile);
    }

    private boolean validateUser(String username, String password) {
        Path userFile = Paths.get("user_data", username, "user_info.dat");

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(userFile))) {
            User savedUser = (User) ois.readObject();
            return savedUser.getPassword().equals(password);
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }


}
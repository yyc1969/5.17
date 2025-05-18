// initialize the register window
// function the "confirm" button (to be done), and the "restart" button.

package view.login;

import model.User;
import view.FrameUtil;
import view.Location;
import view.game.GameFrame;
import view.game.LevelInterface;
import view.game.EndFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
    private JPanel jPanel;
    private JWindow jWindow;
    private Action completeLevelInterface;

    public static ArrayList<LevelInterface> levelInterfaces;
    private ArrayList<Location> locations;
    int levelNum = 5;

    // define the locations
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;
    int levelInterfaceWidth = 1200;
    int levelInterfaceHeight = 900;
    int spacing = 40;
    int firstX = screenWidth / 2 - levelInterfaceWidth / 2 - spacing * 2;
    int firstY = screenHeight / 2 - levelInterfaceHeight / 2;

    // transmit variable user to levelGameFrame
    private User user;


//    //add
//    private JButton guestBtn = FrameUtil.createButton(this, "Guest", new Point(100, 200), 100, 40);
//    private JButton registerBtn = FrameUtil.createButton(this, "Register", new Point(100, 260), 100, 40);
//    private UserService userService = new UserService();


    //constructor, a window
    public LoginFrame(int width, int height, GameFrame gameframe) {
        this.setTitle("Login Frame");
        this.setLayout(null);
        this.setSize(width, height);

        this.jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setSize(280, 280);
        jPanel.setBackground(Color.LIGHT_GRAY);

        JLabel userLabel = FrameUtil.createJLabel(jPanel, new Point(50, 80), 70, 40, "username:");
        JLabel passLabel = FrameUtil.createJLabel(jPanel, new Point(50, 140), 70, 40, "password:");
        username = FrameUtil.createJTextField(jPanel, new Point(120, 80), 120, 40);
        password = FrameUtil.createJPasswordField(jPanel, new Point(120, 140), 120, 40);

        this.gameFrame = gameframe;

        startGameBtn = FrameUtil.createButton(jPanel, "Confirm", new Point(40, 200), 100, 40);

        //add
        char[] passwordChars = password.getPassword();
        String passwordText = new String(passwordChars);

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

        jPanel.setLocation(this.getWidth() / 2 - jPanel.getWidth() / 2, this.getHeight() / 2 - jPanel.getHeight() + 100);
        this.add(jPanel);

        completeLevelInterface = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jWindow.toFront();
                // set the location of the levelInterfaces
                for (int i = levelNum - 1; i >= 0; i--)
                {
                    levelInterfaces.get(i).toFront();
                    levelInterfaces.get(i).setjWindow(jWindow);
                    levelInterfaces.get(i).setLocation(new Point(locations.get(i).getX(), locations.get(i).getY()));
                    levelInterfaces.get(i).setVisible(true);
                }

                // set image of levelInterfaces
                setPanelImage(levelInterfaces.get(0).getLevelPanel(), "beginerlevel.png");
                setPanelImage(levelInterfaces.get(1).getLevelPanel(), "hengdaolima.png");
                setPanelImage(levelInterfaces.get(2).getLevelPanel(), "jiangyongcaoying.png");
                setPanelImage(levelInterfaces.get(3).getLevelPanel(), "qitoubingjin.png");
                setPanelImage(levelInterfaces.get(4).getLevelPanel(), "zuoyoububing.png");
            }
        };

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

            // create a user that is not null
            this.user = new User(inputUsername, inputPassword);
            for (int i = 0; i < levelInterfaces.size(); i++)
            {
                levelInterfaces.get(i).setUser(user);
            }

//            gameframe.setVisible(true);
            this.setVisible(false);

            completeLevelInterface.actionPerformed(e);

//            completeLevelInterface.actionPerformed(e);

            // set the name of the level
//            setPanelImage(levelInterface1.getLevelNamePanel(), "black.png");

            // when levelInterfaces the ArrayList is completely set, transmit it to TimesUpFrame
//            EndFrame.setLevelInterfaces(levelInterfaces);

            // set the location of the VictoryFrame，why it acts as if this.getMovementPanel.getBtnRight = null
            this.gameFrame.getGamePanel().getEndFrame().setLocationRelativeTo(this.gameFrame.getMovementPanel().getBtnRight());
        });

        resetBtn = FrameUtil.createButton(jPanel, "Reset", new Point(160, 200), 100, 40);

//        //add
//        char[] passwordChars = password.getPassword();
//        String passwordText = new String(passwordChars);
//
//        // create levelInterfaces
//        levelInterfaces = new ArrayList<>();
//        for (int i = 0; i < levelNum; i++)
//        {
//            setLevelInterface();
//        }
//
//        // set the locations
//        ArrayList<Location> locations = new ArrayList<>();
//        int x = firstX;
//        int y = firstY;
//        Location location;
//        for (int i = 0; i < levelNum; i++)
//        {
//            location = new Location();
//            location.setX(x);
//            location.setY(y);
//            locations.add(location);
//            x += spacing;
//        }
//
//        jPanel.setLocation(this.getWidth() / 2 - jPanel.getWidth() / 2, this.getHeight() / 2 - jPanel.getHeight() + 100);
//        this.add(jPanel);

//        // after the summit button is pressed
//        submitBtn.addActionListener(e -> {
//            System.out.println("Username = " + username.getText());
//            System.out.println("Password = " + password.getText());
//
//            //add
//            if(UserController.validateUser(username.getText(), password.getText())){
//                User user = new User(username.getText(), password.getText());
//                this.setVisible(false);
//
//                // set the location of the levelInterfaces
//                jWindow.toFront();
//                for (int i = levelNum - 1; i >= 0; i--)
//                {
//                    levelInterfaces.get(i).setGameFrame(gameFrame);
//                    levelInterfaces.get(i).toFront();
//                    levelInterfaces.get(i).setjWindow(jWindow);
//                    levelInterfaces.get(i).setLocation(new Point(locations.get(i).getX(), locations.get(i).getY()));
//                    levelInterfaces.get(i).setVisible(true);
//                }
//
//                // set image of levelInterfaces
//                setPanelImage(levelInterfaces.get(0).getLevelPanel(), "beginerlevel.png");
//                setPanelImage(levelInterfaces.get(1).getLevelPanel(), "hengdaolima.png");
//                setPanelImage(levelInterfaces.get(2).getLevelPanel(), "jiangyongcaoying.png");
//                setPanelImage(levelInterfaces.get(3).getLevelPanel(), "qitoubingjin.png");
//                setPanelImage(levelInterfaces.get(4).getLevelPanel(), "zuoyoububing.png");
//
//                // set the name of the level
////            setPanelImage(levelInterface1.getLevelNamePanel(), "black.png");
//
//                // when levelInterfaces the ArrayList is completely set, transmit it to TimesUpFrame
//                EndFrame.setLevelInterfaces(levelInterfaces);
//
//                // set the location of the VictoryFrame，why it acts as if this.getMovementPanel.getBtnRight = null
////                this.gameFrame.getGamePanel().getVictoryInterface().setLocationRelativeTo(this.gameFrame.getMovementPanel().getBtnRight());
//            }
//            else
//            {
//                JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//// if the user have registered or not; give out game record if this is a registered user
//// a time composition, break record
//        });
//
//        // if the reset button is pressed,clean up the textField
//        resetBtn.addActionListener(e ->
//        {
//            username.setText("");
//            password.setText("");
//        });
//
//        //add
//        guestBtn.addActionListener(e -> {
//            userService.setGuest();
////            launchGame();
//        });
//        registerBtn.addActionListener(e -> showRegisterDialog());

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

//            // squeeze the original image
//            int scale = Math.max(originalImage.getWidth() / JPanelTargetPanel.getWidth(), originalImage.getHeight() / JPanelTargetPanel.getHeight()) + 1;
//            // if the original image is smaller than the panel, draw the original size image
//            if (scale == 0)
//            {
//                scale = 1;
//            }
//            int targetWidth = originalImage.getWidth() / scale;
//            int targetHeight = originalImage.getHeight() / scale;
            int targetWidth = originalImage.getWidth() ;
            int targetHeight = originalImage.getHeight();

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

//    //add
//    private void showRegisterDialog() {
//        JTextField regUser = new JTextField();
//        JPasswordField regPass = new JPasswordField();
//        Object[] fields = {"Username:", regUser, "Password:", regPass};
//
//        int option = JOptionPane.showConfirmDialog(this, fields, "Register",
//                JOptionPane.OK_CANCEL_OPTION);
//
//        if (option == JOptionPane.OK_OPTION) {
//            String username = regUser.getText().trim();
//            String password = new String(regPass.getPassword());
//            if (username.isEmpty() || password.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Username and password cannot be empty!");
//                return;
//            }
//            User newUser = new User(username, password);
//            if (userService.register(newUser)) {
//                JOptionPane.showMessageDialog(this, "Registration successful!");
//            } else {
//                JOptionPane.showMessageDialog(this, "Username already exists!");
//            }
//        }
//    }

//    private void launchGame() {
//        User currentUser = userService.getCurrentUser();
//        int[][] initialMatrix = {
//                {10, 20, 20, 13},
//                {11, 30, 21, 21},
//                {12, 30, 40, 40},
//                {0, 0, 40, 40}
//        };
//        MapModel mapModel = new MapModel(initialMatrix);
//        GameFrame gameFrame = new GameFrame(800, 600, mapModel, currentUser);
//        GameFrame gameFrame1 = new GameFrame(800, 600, mapModel, null);
//        gameFrame.setVisible(true);
//        this.dispose();
//    }


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


    public void setLevelInterface()
    {
        // let the LevelInterface have the variable of gameFrame
        LevelInterface ALevelInterface = new LevelInterface(this);
        ALevelInterface.setGameFrame(gameFrame);
        ALevelInterface.setGamePanel(gameFrame.getGamePanel());
        ALevelInterface.setUser(user);
        levelInterfaces.add(ALevelInterface);
    }

    public void setJWindow(JWindow jWindow) {
        this.jWindow = jWindow;
    }

    public ArrayList<Location> getLocations()
    {
        return locations;
    }

    public ArrayList<LevelInterface> getLevelInterfaces()
    {
        return levelInterfaces;
    }

    public Action getCompleteLevelInterface() {
        return completeLevelInterface;
    }
}

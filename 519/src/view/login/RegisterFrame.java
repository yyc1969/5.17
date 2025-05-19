package view.login;
import model.MapModel;
import model.User;
import view.FrameUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RegisterFrame extends JFrame {

    // jPanel organizes layout
//    private JButton comfirmBtn = FrameUtil.createButton(this, "Comfirm", new Point(100, 140), 100, 40);
//    private JButton resetBtn = FrameUtil.createButton(this, "Reset", new Point(300, 140), 100, 40);
    private JButton conformBtn;
    private JButton resetBtn;

    private JTextField username;
    private JPasswordField password;
    private static final String[] MAP_NAMES = {
            "map1_classic",
            "map2_easy",
            "map3_medium",
            "map4_hard",
            "map5_expert"
    };

    // to organize components
    private JPanel jPanel;

    public RegisterFrame(int width, int height, LoginFrame loginFrame) {
        this.setTitle("Register Frame");
        this.setLayout(null);
        this.setSize(width, height);
        setLocationRelativeTo(null);

        this.jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setSize(200, 200);
        jPanel.setBackground(Color.LIGHT_GRAY);

//        startGameBtn = FrameUtil.createAdvancedButton1(jPanel, "Confirm", new Point(10, 130), 100, 40, Color.lightGray, Color.BLACK);
//        resetBtn = FrameUtil.createAdvancedButton1(jPanel, "Reset", new Point(110, 130), 90, 40, Color.LIGHT_GRAY

        conformBtn = FrameUtil.createAdvancedButton1(jPanel, "Conform", new Point(10, 130), 100, 40, Color.lightGray, Color.BLACK
        );
        resetBtn = FrameUtil.createAdvancedButton1(jPanel, "Reset", new Point(110, 130), 90, 40, Color.lightGray, Color.BLACK
        );
//        JLabel userLabel = FrameUtil.createJLabel(jPanel, new Point(50, 80), 70, 40, "username:");
//        JLabel passLabel = FrameUtil.createJLabel(jPanel, new Point(50, 140), 70, 40, "password:");
//        username = FrameUtil.createJTextField(jPanel, new Point(120, 80), 120, 40);
//        password = FrameUtil.createJPasswordField(jPanel, new Point(120, 140), 120, 40);
        JLabel userLabel = FrameUtil.createJLabel(jPanel, new Point(10, 10), 70, 40, "username:");
        JLabel passLabel = FrameUtil.createJLabel(jPanel, new Point(10, 70), 70, 40, "password:");
        username = FrameUtil.createJTextField(jPanel, new Point(90, 10), 100, 40);
        password = FrameUtil.createJPasswordField(jPanel, new Point(90, 70), 100, 40);

        jPanel.setLocation(this.getWidth() / 2 - jPanel.getWidth() / 2, this.getHeight() / 2 - jPanel.getHeight() + 100);
        add(jPanel);

        conformBtn.addActionListener(e -> {
            String usernameText = username.getText().trim();
            String passwordText = new String(password.getPassword()).trim();

            if (usernameText.equals("") || passwordText.equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter your username and password");
                loginFrame.setVisible(false);
                this.setVisible(true);
                return;
            }

            if (userExists(usernameText)) {
                JOptionPane.showMessageDialog(this, "this username already exists");
                loginFrame.setVisible(false);
                this.setVisible(true);
                return;
            }

            User user = new User(usernameText, passwordText);

            if (createUserDataFiles(user)) {
                System.out.println("Username = " + usernameText + " Password = " + passwordText + " has opened a new account");
                JOptionPane.showMessageDialog(this, "registered user successfully");

                // 返回登录界面
                loginFrame.setVisible(true);
                this.setVisible(false);
            }
            else {
                JOptionPane.showMessageDialog(this, "failed to register user");
            }
        });

        resetBtn.addActionListener(e -> {
            username.setText("");
            password.setText("");
        });
    }


    private boolean userExists(String username) {
        Path userDir = Paths.get("user_data", String.valueOf(username));
        return Files.exists(userDir);
    }


    private boolean createUserDataFiles(User user) {
        try {
            // 1. 创建用户目录
            Path userDir = Paths.get("user_data", user.getUsername());
            Files.createDirectories(userDir);

//            // 2. 为每种地图创建初始数据文件
//            for (String mapName : MAP_NAMES) {
//                Path dataFile = userDir.resolve(mapName + ".dat");
//
//                // 创建初始游戏数据 (空地图，0步，0分)
//                MapModel initialMap = new MapModel(new int[5][5]);    // 假设MapModel有默认构造函数
//                initialMap.saveToFile(dataFile.toString());
//            }

            // consider the time mode
            for (int i = 0; i < 5; i ++)
            {
                Path dataFile = userDir.resolve(MAP_NAMES[i] + "_count_up" + ".dat");
                MapModel initialMap = new MapModel(new int[5][5]);    // 假设MapModel有默认构造函数
                initialMap.saveToFile(dataFile.toString());
            }

            for (int i = 0; i < 5; i ++)
            {
                Path dataFile = userDir.resolve(MAP_NAMES[i] + "_count_down" + ".dat");
                MapModel initialMap = new MapModel(new int[5][5]);    // 假设MapModel有默认构造函数
                initialMap.saveToFile(dataFile.toString());
            }

            for (int i = 0; i < 5; i++)
            {
                Path timeFile = userDir.resolve(MAP_NAMES[i] + "_count_up" + "_time" + ".dat");
                int initialTimeUsed = 0;
                try (ObjectOutputStream oos = new ObjectOutputStream(
                        Files.newOutputStream(Paths.get(timeFile.toString())))) {
                    oos.writeObject(initialTimeUsed);
                }
            }

            for (int i = 0; i < 5; i++)
            {
                Path timeFile = userDir.resolve(MAP_NAMES[i] + "_count_down" + "_time" + ".dat");
                int initialTimeUsed = 0;
                try (ObjectOutputStream oos = new ObjectOutputStream(
                        Files.newOutputStream(Paths.get(timeFile.toString())))) {
                    oos.writeObject(initialTimeUsed);
                }
            }

            // 3. 保存用户基本信息
            Path userFile = userDir.resolve("user_info.dat");
            user.saveToFile(userFile.toString());

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}


















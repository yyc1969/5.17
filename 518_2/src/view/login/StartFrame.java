package view.login;

import view.FrameUtil;
import view.Location;
import view.game.LevelInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StartFrame extends JFrame {

    private JButton guestBtn = FrameUtil.createAdvancedButton(this, "Guest", new Point(500, 310), 200, 80);
    private JButton registerBtn = FrameUtil.createAdvancedButton(this, "Register", new Point(500, 410), 200, 80);
    private JButton loginBtn = FrameUtil.createAdvancedButton(this, "Login", new Point(500, 510), 200, 80);
    private ArrayList<LevelInterface> levelInterfaces;
    private ArrayList<Location> locations;
    int levelNum = 5;

    public StartFrame(int width, int height ,RegisterFrame registerFrame ,LoginFrame loginFrame) {
        this.setTitle("Start Frame");
        this.setLayout(null);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);

        guestBtn.addActionListener(e -> {
            //to do...


            System.out.println("Guest");
        });

        registerBtn.addActionListener(e -> {
            registerFrame.setVisible(true);
            this.setVisible(false);
            System.out.println("Now please register your new account");
        });

        loginBtn.addActionListener(e -> {
            loginFrame.setVisible(true);
            this.setVisible(false);
            System.out.println("Now please login your own account");
        });

        guestBtn.addActionListener(e ->
        {
            this.setVisible(false);
            loginFrame.getCompleteLevelInterface().actionPerformed(e);
        });
    }
}

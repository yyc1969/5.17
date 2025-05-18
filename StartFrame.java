package view.login;

import view.FrameUtil;
import view.Location;
import view.game.LevelInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StartFrame extends JFrame {

    private JButton guestBtn = FrameUtil.createButton(this, "Guest", new Point(100, 300), 100, 40);
    private JButton registerBtn = FrameUtil.createButton(this, "Register", new Point(100, 200), 100, 40);
    private JButton loginBtn = FrameUtil.createButton(this, "Login", new Point(100, 250), 100, 40);
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

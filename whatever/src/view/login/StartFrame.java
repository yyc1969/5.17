package view.login;

import model.MapModel;
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
    }
}

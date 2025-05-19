import model.MapModel;
import model.User;
import view.FrameUtil;
import view.game.*;
import view.login.LoginFrame;
import view.login.RegisterFrame;
import view.login.StartFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Main {



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // beginner level
            MapModel mapModel = new MapModel(new int[][]{
                    // represent the matrix in an improved way
                    {10, 20, 20, 13},
                    {11, 30, 21, 21},
                    {12, 30, 40, 40},
                    {0, 0, 40, 40}
            });

            User user = null;




            // set a background window
            JWindow jWindow = new JWindow();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            JPanel jPanelOnJWindow = new JPanel();
            jPanelOnJWindow.setSize(screenSize);
            jWindow.add(jPanelOnJWindow);
            jWindow.setSize(screenSize);
            jWindow.setVisible(true);

            // initialize the game frame`
            GameFrame gameFrame = new GameFrame(1200, 900, mapModel, user);
            gameFrame.setVisible(false);
            gameFrame.setjWindow(jWindow);

            // add photo to JWindow
            FrameUtil.setPanelImage( jPanelOnJWindow, "CaoCaoOnHorse.png");

            // initialize the login frame
            LoginFrame loginFrame = new LoginFrame(1200, 840, gameFrame);
            loginFrame.setVisible(false);
            loginFrame.setJWindow(jWindow);
            // set the image of loginFrame
            JPanel logFrameImage = new JPanel();
            logFrameImage.setSize(1200, 840);
            FrameUtil.setPanelImage( logFrameImage, "login2.png");
            loginFrame.add(logFrameImage);

            RegisterFrame registerFrame = new RegisterFrame(1200,900,loginFrame);

            JPanel registerFrameImage = new JPanel();
            registerFrameImage.setSize(1200, 900);
            FrameUtil.setPanelImage( registerFrameImage, "login.png");
            registerFrame.add(registerFrameImage);

            registerFrame.setVisible(false);
            StartFrame startFrame = new StartFrame(1200,900,registerFrame,loginFrame);

            // add photo to startFrame
            JPanel startFrameImage = new JPanel();
            startFrameImage.setSize(1200, 900);
            FrameUtil.setPanelImage( startFrameImage, "startFrame2.png");
            startFrame.add(startFrameImage);
            startFrame.setBackground(Color.black);
            startFrame.setVisible(true);
        });
    }
}

import model.MapModel;
import model.User;
import view.game.*;
import view.login.LoginFrame;
import view.login.RegisterFrame;
import view.login.StartFrame;

import javax.swing.*;
import java.awt.*;

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
            jWindow.setSize(screenSize);
            jWindow.setVisible(true);
            // initialize the game frame`
            GameFrame gameFrame = new GameFrame(1200, 900, mapModel, user);
            gameFrame.setVisible(false);
            gameFrame.setjWindow(jWindow);
            // initialize the login frame
            LoginFrame loginFrame = new LoginFrame(840, 840, gameFrame);
            loginFrame.setVisible(false);
            loginFrame.setJWindow(jWindow);



            RegisterFrame registerFrame = new RegisterFrame(800,600,loginFrame);
            registerFrame.setVisible(false);
            StartFrame startFrame = new StartFrame(1200,900,registerFrame,loginFrame);
            startFrame.setVisible(true);
        });
    }
}

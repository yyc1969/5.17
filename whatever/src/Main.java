import model.MapModel;
import model.User;
import view.game.*;
import view.login.LoginFrame;
import view.login.RegisterFrame;
import view.login.StartFrame;

import javax.swing.*;

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

            // initialize the game frame`
            GameFrame gameFrame = new GameFrame(600, 450, mapModel, user);
            gameFrame.setVisible(false);
            // initialize the login frame
            LoginFrame loginFrame = new LoginFrame(280, 280, gameFrame);
            loginFrame.setVisible(false);
            RegisterFrame registerFrame = new RegisterFrame(600,450,loginFrame);
            registerFrame.setVisible(false);
            StartFrame startFrame = new StartFrame(600,450,registerFrame,loginFrame);
            startFrame.setVisible(true);


        });
    }
}
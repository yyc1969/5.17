import model.MapModel;
import model.User;
import view.game.*;
import view.login.LoginFrame;
import view.login.RegisterFrame;
import view.login.StartFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void setPanelImage(Container getSource, JPanel JPanelTargetPanel, String imageDocumentName)
    {
        // get the original image file from the resources document
        InputStream imageFile = getSource.getClass().getClassLoader().getResourceAsStream(imageDocumentName);
        try
        {
            BufferedImage originalImage = ImageIO.read(imageFile);
            int targetWidth;
            int targetHeight;


                targetWidth = originalImage.getWidth();
                targetHeight = originalImage.getHeight();

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
            setPanelImage(gameFrame, jPanelOnJWindow, "jWindow3.png");

            // initialize the login frame
            LoginFrame loginFrame = new LoginFrame(840, 840, gameFrame);
            loginFrame.setVisible(false);
            loginFrame.setJWindow(jWindow);



            RegisterFrame registerFrame = new RegisterFrame(800,600,loginFrame);
            registerFrame.setVisible(false);
            StartFrame startFrame = new StartFrame(1200,900,registerFrame,loginFrame);

            // add photo to startFrame
            JPanel startFrameImage = new JPanel();
            startFrameImage.setSize(1200, 900);
            setPanelImage(startFrame, startFrameImage, "startFrame.png");
            startFrame.add(startFrameImage);
            startFrame.setBackground(Color.black);
            startFrame.setVisible(true);
        });
    }
}

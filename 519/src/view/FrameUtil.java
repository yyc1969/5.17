package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class is to create basic JComponent.
 */
public class FrameUtil {

    // return a JLabel variable
    // username: password
    public static JLabel createJLabel(JFrame frame, Point location, int width, int height, String text)
    {
        JLabel jLabel = new JLabel(text);
        jLabel.setSize(width, height);
        jLabel.setLocation(location);
        frame.add(jLabel);
        return jLabel;
    }

    public static JLabel createJLabel(JPanel jPanel, Point location, int width, int height, String text)
    {
        JLabel jLabel = new JLabel(text);
        jLabel.setSize(width, height);
        jLabel.setLocation(location);
        jPanel.add(jLabel);
        return jLabel;
    }

    // overload
    // step:
    public static JLabel createJLabel(JFrame frame, String name, Font font, Point location, int width, int height) {
        JLabel label = new JLabel(name);
        label.setFont(font);
        label.setLocation(location);
        label.setSize(width, height);
        frame.add(label);
        return label;
    }

    public static JTextField createJTextField(JFrame frame, Point location, int width, int height) {
        JTextField jTextField = new JTextField();
        jTextField.setSize(width, height);
        jTextField.setLocation(location);
        frame.add(jTextField);
        return jTextField;
    }

    public static JTextField createJTextField(JPanel jPanel, Point location, int width, int height) {
        JTextField jTextField = new JTextField();
        jTextField.setSize(width, height);
        jTextField.setLocation(location);
        jPanel.add(jTextField);
        return jTextField;
    }

    public static JButton createButton(JFrame frame, String name, Point location, int width, int height) {
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);
        frame.add(button);
        return button;
    }

    public static JButton createAdvancedButton(JFrame frame, String name, Point location, int width, int height, Color blackgroundColor, Color letterColor) {
        AdvancedButton button = new AdvancedButton(name, blackgroundColor
        , letterColor);
        button.setLocation(location);
        button.setSize(width, height);
        frame.add(button);
        return button;
    }

    public static JButton createAdvancedButton1(JPanel jPanel, String name, Point location, int width, int height, Color backgroundColor , Color letterColor) {
        AdvancedButton button = new AdvancedButton(name, backgroundColor
        , letterColor);
        button.setLocation(location);
        button.setSize(width, height);
        button.setLetterColor(letterColor);
        button.setLetterColor(backgroundColor);
        jPanel.add(button);
        return button;
    }

    public static JButton createButton(JPanel jPanel, String name, Point location, int width, int height) {
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);
        jPanel.add(button);
        return button;
    }

    public static JPanel createPanel(JFrame frame, Point location, int width, int height, Color color)
    {
        JPanel panel = new JPanel();
        panel.setLocation(location);
        panel.setSize(width, height);
        panel.setBackground(color);
        frame.add(panel);
        return panel;
    }

    public static JPasswordField createJPasswordField(JFrame frame, Point location, int width, int height) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setSize(width, height);
        passwordField.setLocation(location);
        frame.add(passwordField);
        return passwordField;
    }

    public static JPasswordField createJPasswordField(JPanel jPanel, Point location, int width, int height) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setSize(width, height);
        passwordField.setLocation(location);
        jPanel.add(passwordField);
        return passwordField;
    }

    public static void setPanelImage( JPanel JPanelTargetPanel, String imageDocumentName)
    {
//        // get the original image file from the resources document
//        InputStream imageFile = getSource.getClass().getClassLoader().getResourceAsStream(imageDocumentName);
        try
        {
            // 如果我知道我想读取的文件的Path， 我能不能不用InputStream imageFile = getSource.getClass().getClassLoader().getResourceAsStream(imageDocumentName);
            BufferedImage originalImage = ImageIO.read(new File(String.format("resources/%s", imageDocumentName)));
//            BufferedImage originalImage = ImageIO.read(imageFile);
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
    public static JLabel setPanelImage(String imageDocumentName)
    {
//        // get the original image file from the resources document
//        InputStream imageFile = getSource.getClass().getClassLoader().getResourceAsStream(imageDocumentName);
        try
        {
            // 如果我知道我想读取的文件的Path， 我能不能不用InputStream imageFile = getSource.getClass().getClassLoader().getResourceAsStream(imageDocumentName);
            BufferedImage originalImage = ImageIO.read(new File(String.format("resources/%s", imageDocumentName)));
//            BufferedImage originalImage = ImageIO.read(imageFile);
            int targetWidth;
            int targetHeight;


            targetWidth = originalImage.getWidth();
            targetHeight = originalImage.getHeight();

            // put the image to the targetPanel
            Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImage);
            JLabel imageContainer = new JLabel(imageIcon);
            return imageContainer;

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }

    public static void setPanelImage1(JFrame targetFrame, String imageDocumentName)
    {
        JPanel imagePanel = new JPanel();
        imagePanel.setSize(targetFrame.getWidth(), targetFrame.getHeight());
        FrameUtil.setPanelImage(imagePanel, imageDocumentName);
        targetFrame.add(imagePanel);

    }

}

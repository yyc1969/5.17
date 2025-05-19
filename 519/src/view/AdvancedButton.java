package view;

import javax.swing.*;
import java.awt.*;

public class AdvancedButton extends JButton
{
    private AdvancedButton advancedButton;
    private Color backgroundColor;
    private Color letterColor;

    public AdvancedButton(String buttonName, Color backgroundColor, Color letterColor)
    {
        super(buttonName);
        this.setBorder(null);
        this.setBackground(backgroundColor); // transparent background
        this.setForeground(letterColor); // text color
        this.setBorder(null);
        this.setFocusPainted(false); // 去除焦点边框
        this.setFont(new Font("BeforeMouseEnter", Font.BOLD, 20)); // 设置字体样式和大小
        this.advancedButton = this;

//        // add round corner
//        @Override
//        protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        if (getModel().isPressed()) {
//            g.setColor(Color.GRAY);
//        } else {
//            g.setColor(getBackground());
//        }
//        g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // 设置圆角
//    }
//    };

        // add mouse listener
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                advancedButton.setFont(new Font("AfterMouseEnter", Font.BOLD, 32)); // letter grows bigger when mouse enter
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                advancedButton.setFont(new Font("BeforeMouseEnter", Font.BOLD, 20));
            }
        });



    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getLetterColor() {
        return letterColor;
    }

    public void setLetterColor(Color letterColor) {
        this.letterColor = letterColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}

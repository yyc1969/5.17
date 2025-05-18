package view;

import javax.swing.*;
import java.awt.*;

public class AdvancedButton extends JButton
{
    private AdvancedButton advancedButton;

    public AdvancedButton(String buttonName)
    {
        super(buttonName);
        this.setBorder(null);
        this.setBackground(null); // transparent background
        this.setForeground(Color.black); // text color
        this.setBorder(null);
        this.setFocusPainted(false); // 去除焦点边框
        this.setFont(new Font("BeforeMouseEnter", Font.BOLD, 14)); // 设置字体样式和大小
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
                advancedButton.setFont(new Font("AfterMouseEnter", Font.BOLD, 20)); // letter grows bigger when mouse enter
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                advancedButton.setFont(new Font("BeforeMouseEnter", Font.BOLD, 14));
            }
        });



    }
}

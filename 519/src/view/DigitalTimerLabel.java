package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DigitalTimerLabel extends JLabel {
    private static final String FONT_PATH = "digital-7.ttf"; // 字体文件路径
    private  int fontSize;

    private Timer timer;
    private long endTime;
    private long timeUsed = 0;

    public DigitalTimerLabel(Color letterColor, int fontSize) {
        super("00:00", SwingConstants.CENTER);
        this.fontSize = fontSize;
        setOpaque(true);
        setBackground(Color.BLACK);
        setForeground(letterColor);
        loadDigitalFont();
        setPreferredSize(new Dimension(200, 100));
    }

    private void loadDigitalFont() {
        try {
            Font digitalFont = Font.createFont(Font.TRUETYPE_FONT, new File(FONT_PATH)).deriveFont(Font.PLAIN, fontSize);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(digitalFont);
            setFont(digitalFont);
        } catch (Exception e) {
            e.printStackTrace();
            setFont(new Font("Monospaced", Font.BOLD, fontSize)); // 回退字体
        }
    }

    public void startCountdown(int seconds) {
        endTime = System.currentTimeMillis() + seconds * 1000L;

        timer = new Timer(100, e -> {
            long remaining = endTime - System.currentTimeMillis();
            if (remaining <= 0) {
                setText("Time: 00:00");
                timer.stop();
                setForeground(Color.RED); // 时间到变红
            } else {
                int secs = (int)(remaining / 1000);
                setText(String.format("Time: 00:%02d", secs));
                if (secs <= 5) {
                    setForeground(Color.ORANGE); // 最后5秒警告
                }
            }
        });
        timer.start();
    }

    public void startCountUp() {
        timer = new Timer(100, e -> {
                timeUsed += 1000;
                setText(String.format("00:%02d", timeUsed));
        });
        timer.start();
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}


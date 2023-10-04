package ui;

import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JWindow {
    Image splashScreen;
    ImageIcon imageIcon;

    // EFFECT: show a welcome picture when open
    public WelcomePage() {
        splashScreen = Toolkit.getDefaultToolkit()
                .getImage("E:\\Cs210\\Labs\\project_q9l3b\\data\\sample picture.jpg");

        imageIcon = new ImageIcon(splashScreen);

        setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (screenSize.width - getSize().width / 2);

        int y = (screenSize.height - getSize().height / 2);

        setLocation(x,y);
        setVisible(true);
    }

    // Effect: paint image on JWindow
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(splashScreen, 0, 0, this);
    }

    public  static void main(String[]args) {
        WelcomePage welcome = new WelcomePage();
        try {
            Thread.sleep(2000);
            welcome.dispose();
            new LogInPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

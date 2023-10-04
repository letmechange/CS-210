package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WrongInformationInput extends JPanel implements ActionListener {
    JFrame in = new JFrame();
    JLabel information = new JLabel();
    JButton back = new JButton("Back");

    // EFFECT: show the window when there is someting wrong
    public WrongInformationInput() {
        information.setText("Fault");

        in.setSize(200,100);
        in.setLayout(new FlowLayout());
        in.add(information);
        in.add(back);

        back.setActionCommand("back");
        back.addActionListener(this);

        in.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("back")) {
            in.dispose();
        }
    }
}

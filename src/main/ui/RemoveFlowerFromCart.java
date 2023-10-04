package ui;

import model.Customer;
import model.Event;
import model.EventLog;
import model.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveFlowerFromCart extends JFrame implements ActionListener {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 100;

    private JFrame rc;
    private JButton remove = new JButton("remove");
    private JLabel nameL;
    private JTextField name;
    private Store store;
    private Customer customer;

    // EFFECT: a page used for removing flower from cart
    public RemoveFlowerFromCart(Store store, Customer customer) {
        this.store = store;
        this.customer = customer;

        nameL = new JLabel();
        nameL.setText("name");
        name = new JTextField(20);

        rc = new JFrame();
        rc.setSize(WIDTH, HEIGHT);
        rc.setLayout(new GridLayout());
        rc.add(nameL);
        rc.add(name);
        rc.add(remove);

        remove.setActionCommand("remove");
        remove.addActionListener(this);

        rc.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String n = name.getText();

        if (e.getActionCommand().equals("remove")) {
            customer.removeOneFlower(n);
            store.addStocks(n, 1);
            EventLog.getInstance().logEvent(new Event("remove flower " + n + " from the cart"));
            rc.dispose();
        }
    }
}

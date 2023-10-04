package ui;

import model.Customer;
import model.Event;
import model.EventLog;
import model.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddFlowerToCart extends JFrame implements ActionListener {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 100;

    private JFrame at;
    private JButton add = new JButton("add");
    private JLabel nameL;
    private JTextField name;
    private Store store;
    private Customer customer;

    // EFFECT: represent page of adding flower to the cart
    public AddFlowerToCart(Store store, Customer customer) {
        this.store = store;
        this.customer = customer;

        nameL = new JLabel();
        nameL.setText("name");
        name = new JTextField(20);

        at = new JFrame();
        at.setSize(WIDTH, HEIGHT);
        at.setLayout(new GridLayout());
        at.add(nameL);
        at.add(name);
        at.add(add);

        add.setActionCommand("add");
        add.addActionListener(this);

        at.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String n = name.getText();

        if (e.getActionCommand().equals("add")) {
            if (store.checkStock(n) > 1) {
                customer.addToCart(store.requiredFlower(n));
                EventLog.getInstance().logEvent(new Event("Add flower " + n + " into cart"));
                at.dispose();
            } else {
                new WrongInformationInput();
                EventLog.getInstance().logEvent(new Event("Fail to add flower"));
                at.dispose();
            }
        }
    }
}

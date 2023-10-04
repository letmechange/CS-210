package ui;

import model.Event;
import model.EventLog;
import model.Flower;
import model.Store;
import model.exception.LogException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddFlowerToStoreUI extends JFrame implements ActionListener {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 100;

    private JFrame af;
    private JButton add = new JButton("add");
    private JLabel priceL;
    private JLabel fmL;
    private JLabel nameL;
    private JLabel stockL;
    private JTextField price;
    private JTextField flowerMeaning;
    private JTextField name;
    private JTextField stock;
    private Store store;

    @SuppressWarnings("methodlength")
    // EFFECT: represent page of adding flower to the store
    public AddFlowerToStoreUI(Store store) {
        this.store = store;

        priceL = new JLabel();
        priceL.setText("price");
        price = new JTextField(20);

        fmL = new JLabel();
        fmL.setText("flower meaning");
        flowerMeaning = new JTextField(20);

        nameL = new JLabel();
        nameL.setText("name");
        name = new JTextField(20);

        stockL = new JLabel();
        stockL.setText("stock");
        stock = new JTextField(20);

        af = new JFrame();
        af.setSize(WIDTH, HEIGHT);
        af.setLayout(new GridLayout());
        af.add(priceL);
        af.add(price);
        af.add(fmL);
        af.add(flowerMeaning);
        af.add(nameL);
        af.add(name);
        af.add(stockL);
        af.add(stock);
        af.add(add);

        add.setActionCommand("add");
        add.addActionListener(this);

        af.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double p = Double.parseDouble(price.getText());
        String fm = flowerMeaning.getText();
        String n = name.getText();
        int s = Integer.parseInt(stock.getText());



        if (e.getActionCommand().equals("add")) {
            Flower flower = new Flower(p, fm, n, s);
            store.addNewFlower(flower);
            EventLog.getInstance().logEvent(new Event("Add flower "
                    + flower.getName() + " into store with the number " + flower.getStock()));
            af.dispose();
        }
    }
}

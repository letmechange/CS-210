package ui;

import model.Customer;
import model.Event;
import model.EventLog;
import model.Flower;
import model.Store;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class CustomerUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/flowerStore.json";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Customer customer;
    private Store store;
    private JsonWriter jsonWriter;
    private JFrame cu;

    private JButton cart = new JButton("Cart");
    private JButton shop = new JButton("Go Shopping");
    private JButton checkout = new JButton("Check Out");
    private JTextArea storeInformation = new JTextArea(40, 50);

    // EFFECTS: the page when logger is customer
    public CustomerUI(Customer customer, Store store) {
        jsonWriter = new JsonWriter(JSON_STORE);
        this.customer = customer;
        this.store = store;

        Box menu = Box.createVerticalBox();
        menu.add(cart);
        menu.add(shop);
        menu.add(checkout);

        Box subMenu = Box.createHorizontalBox();
        subMenu.add(storeInformation);

        cu = new JFrame();
        cu.setSize(WIDTH, HEIGHT);
        cu.setLayout(new GridLayout());
        cu.setTitle("Welcome" + customer.getName());
        cu.add(menu);
        cu.add(subMenu);

        cart.setActionCommand("cart");
        cart.addActionListener(this);
        shop.setActionCommand("shop");
        shop.addActionListener(this);
        checkout.setActionCommand("check");
        checkout.addActionListener(this);

        cu.setVisible(true);
        cu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // EFFECTS: show the information of the store in text field
    private void updateStoreInformation(Store store) {
        String storeI = "";
        for (Flower f : store.getFlowers()) {
            storeI = new StringBuilder().append(storeI)
                    .append(f.getName())
                    .append(" with the stock of ")
                    .append(f.getStock())
                    .append(" . ").toString();
        }

        storeInformation.setText(storeI);
    }

    // EFFECT: update the store in json data
    private void updateStore() {
        try {
            jsonWriter.open();
            jsonWriter.write(store);
            jsonWriter.close();
            EventLog.getInstance().logEvent(new Event("Updating the store"));
        } catch (FileNotFoundException e) {
            new WrongInformationInput();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "cart") {
            new CartUI(store, customer);
        } else if (e.getActionCommand() == "shop") {
            updateStoreInformation(store);
        } else if (e.getActionCommand() == "check") {
            updateStore();
            new LogInPage();
            cu.dispose();
        }
    }
}

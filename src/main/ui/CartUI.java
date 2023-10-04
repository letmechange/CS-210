package ui;

import model.Customer;
import model.Event;
import model.EventLog;
import model.Flower;
import model.Store;
import model.exception.LogException;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CartUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/flowerStore.json";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private JsonWriter jsonWriter;

    private Customer customer;
    private Store store;

    private JFrame cart = new JFrame();
    private JButton price = new JButton("Total Price");
    private JButton addF = new JButton("Add Flower To Cart");
    private JButton removeF = new JButton("Remove Flower From Cart");
    private JButton view = new JButton("View Cart");
    private JButton back = new JButton("Back");
    private JTextArea cartInformation = new JTextArea(40, 50);

    @SuppressWarnings("methodlength")
    public CartUI(Store store, Customer customer) {
        this.customer = customer;
        this.store = store;

        Box menu = Box.createVerticalBox();
        menu.add(price);
        menu.add(addF);
        menu.add(removeF);
        menu.add(view);
        menu.add(back);

        Box subMenu = Box.createHorizontalBox();
        subMenu.add(cartInformation);

        cart = new JFrame();
        cart.setSize(WIDTH, HEIGHT);
        cart.setLayout(new GridLayout());
        cart.setTitle("Welcome" + customer.getName());
        cart.add(menu);
        cart.add(subMenu);
        addMenu();

        price.setActionCommand("price");
        price.addActionListener(this);
        addF.setActionCommand("add");
        addF.addActionListener(this);
        removeF.setActionCommand("remove");
        removeF.addActionListener(this);
        view.setActionCommand("view");
        view.addActionListener(this);
        back.setActionCommand("back");
        back.addActionListener(this);

        cart.setVisible(true);
        cart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // EFFECTS: show the price information of customer's cart into textfield
    private void showPriceInTextField() {
        double i = 0.0;
        if (customer.getCart().isEmpty()) {
            cartInformation.setText("There is no flower.");
        } else {
            for (Flower f : customer.getCart()) {
                i = i + f.getStock() * f.getPrice();
            }
            cartInformation.setText("The total price in current cart is " + i);
        }

        EventLog.getInstance().logEvent(new Event("Customer check the total price"));
    }

    // EFFECTS: represent the cart information in text field
    private void viewCart() {
        if (customer.getCart().isEmpty()) {
            cartInformation.setText("Cart is empty.");
        } else {
            String cartI = "";
            for (Flower f : customer.getCart()) {
                cartI = cartI + f.getName() + " with the number of " + f.getStock()
                        + " . ";
            }

            cartInformation.setText(cartI);
        }
        EventLog.getInstance().logEvent(new Event("Customer check the cart"));
    }

    // EFFECTS: add menu for event log
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu eventLogMenu = new JMenu("Event Log");
        addMenuItem(eventLogMenu, new CartUI.PrintLogAction(), null);
        addMenuItem(eventLogMenu, new CartUI.ClearLogAction(), null);
        menuBar.add(eventLogMenu);

        cart.setJMenuBar(menuBar);
    }

    /**
     * Adds an item with given handler to the given menu
     * @param theMenu  menu to which new item is added
     * @param action   handler for new menu item
     * @param accelerator    keystroke accelerator for this menu item
     * referred from AlarmSystem
     */
    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }

    /**
     * Represents the action to be taken when the user wants to
     * print the event log.
     */
    private class PrintLogAction extends AbstractAction {
        PrintLogAction() {
            super("Print log to...");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            LogPrinter lp;
            try {
                lp = new ScreenPrinter(CartUI.this);
                cart.add((ScreenPrinter) lp);
                lp.printLog(EventLog.getInstance());
            } catch (LogException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Represents the action to be taken when the user wants to
     * clear the event log.
     */
    private class ClearLogAction extends AbstractAction {

        ClearLogAction() {
            super("Clear log");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            EventLog.getInstance().clear();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "price") {
            showPriceInTextField();
        } else if (e.getActionCommand() == "add") {
            new AddFlowerToCart(store, customer);
        } else if (e.getActionCommand() == "remove") {
            new RemoveFlowerFromCart(store, customer);
        } else if (e.getActionCommand() == "view") {
            viewCart();
        } else if (e.getActionCommand() == "back") {
            cart.dispose();
        }
    }

}

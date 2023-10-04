package ui;

import model.Assistant;
import model.Event;
import model.EventLog;
import model.Flower;
import model.Store;
import model.exception.LogException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class AssistantUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/flowerStore.json";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;


    private Assistant assistant;
    private Store store;
    private JsonWriter jsonWriter;

    private JFrame as;
    private JButton check = new JButton("Check Stock");
    private JButton addF = new JButton("Add to Flower");
    private JButton back = new JButton("Back to HomePage");
    private JButton logPrinter = new JButton("Print Log");
    private JButton save = new JButton("Update Store");
    private JTextArea storeInformation = new JTextArea(40, 50);

    @SuppressWarnings("methodlength")
    // EFFECTS: the page when an assistant log in
    public AssistantUI(Assistant assistant, Store store) throws IOException {
        jsonWriter = new JsonWriter(JSON_STORE);
        this.assistant = assistant;
        this.store = store;

        Box menu = Box.createVerticalBox();

        menu.add(addF);
        menu.add(back);
        menu.add(save);

        Box subMenu = Box.createVerticalBox();
        subMenu.add(check);
        subMenu.add(storeInformation);


        as = new JFrame();
        as.setSize(WIDTH, HEIGHT);
        as.setLayout(new GridLayout());
        as.setTitle("Welcome, " + assistant.getName());
        as.add(menu);
        as.add(subMenu);

        check.setActionCommand("check");
        check.addActionListener(this);
        addF.setActionCommand("addF");
        addF.addActionListener(this);
        back.setActionCommand("back");
        back.addActionListener(this);
        save.setActionCommand("save");
        save.addActionListener(this);

        addMenu();
        as.setVisible(true);
        as.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // EFFECTS: show the information of store in text field
    private void checkStoreStatus() {
        String storeI = "";
        for (Flower f : store.getFlowers()) {
            storeI = storeI + f.getName() + " with the stock of " + f.getStock()
                    + " . ";
        }

        storeInformation.setText(storeI);
        EventLog.getInstance().logEvent(new Event("Check Store"));
    }

    // EFFECTS: saves the store to file
    private void saveStore() {
        try {
            jsonWriter.open();
            jsonWriter.write(store);
            jsonWriter.close();
            EventLog.getInstance().logEvent(new Event("Save "
                    + store.getAccessCode() + " to " + JSON_STORE));
        } catch (FileNotFoundException e) {
            new WrongInformationInput();
        }
    }

    // EFFECTS: add menu for event log
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu eventLogMenu = new JMenu("Event Log");
        addMenuItem(eventLogMenu, new PrintLogAction(), null);
        addMenuItem(eventLogMenu, new ClearLogAction(), null);
        menuBar.add(eventLogMenu);

        as.setJMenuBar(menuBar);
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
                lp = new ScreenPrinter(AssistantUI.this);
                as.add((ScreenPrinter) lp);
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
        if (e.getActionCommand() == "check") {
            checkStoreStatus();
        } else if (e.getActionCommand() == "addF") {
            new AddFlowerToStoreUI(store);
        } else if (e.getActionCommand() == "back") {
            new LogInPage();
            as.dispose();
        } else if (e.getActionCommand() == "save") {
            saveStore();
        }
    }
}

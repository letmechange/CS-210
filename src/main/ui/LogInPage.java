package ui;

import model.Assistant;
import model.Customer;
import model.Event;
import model.EventLog;
import model.Store;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class LogInPage extends JFrame implements ActionListener {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 200;
    private static final String JSON_STORE = "./data/flowerStore.json";
    private JsonReader jsonReader;

    private JFrame starter;
    private JButton instructionA = new JButton("Assistant Log In");
    private JButton instructionC = new JButton("Customer Log In (NO NEED ACCESS CODE)");
    private JLabel nameLabel;
    private JLabel accessCodeLabel;
    final JTextField nameText;
    final JTextField accessCodeNum;
    private Store store;
    private Assistant assistant;
    private Customer customer;

    // EFFECT: the page for log-in as assistant or customer
    public LogInPage() {
        nameLabel = new JLabel();
        nameLabel.setText("name");
        nameText = new JTextField(20);
        jsonReader = new JsonReader(JSON_STORE);

        accessCodeLabel = new JLabel();
        accessCodeLabel.setText("accessCodeNum");
        accessCodeNum = new JTextField(20);

        starter = new JFrame();
        starter.setSize(WIDTH, HEIGHT);
        starter.setLayout(new GridLayout(3,1));
        starter.add(nameLabel);
        starter.add(nameText);
        starter.add(accessCodeLabel);
        starter.add(accessCodeNum);
        starter.add(instructionA);
        starter.add(instructionC);

        instructionA.setActionCommand("Assistant");
        instructionC.setActionCommand("Customer");
        instructionA.addActionListener(this);
        instructionC.addActionListener(this);

        starter.setVisible(true);
        starter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    // EFFECT: read the store from json data
    private void loadJsonFlowerStore() throws IOException {
        store = jsonReader.read();
    }

    // MODIFIES: this
    // EFFECTS: loads store from file
    private void loadStore(String accessCode) {
        try {
            String jsonData = readStore();
            if (!jsonData.isEmpty()) {
                store = jsonReader.read();
                if (!(store.getAccessCode().equals(accessCode))) {
                    store = new Store(accessCode);
                }
            }

            EventLog.getInstance().logEvent(new Event("The current store is "
                    + store.getAccessCode()));
        } catch (IOException e) {
            new WrongInformationInput();
        }
    }

    // EFFECTS: read source file as String and returns it
    private String readStore() throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(JSON_STORE), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    @SuppressWarnings("methodlength")
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Customer")) {
            if (!(accessCodeNum.getText().equals(""))) {
                new WrongInformationInput();
            } else {
                try {
                    loadJsonFlowerStore();
                } catch (IOException ex) {
                    new WrongInformationInput();
                }
                customer = new Customer(nameText.getText());
                new CustomerUI(customer, store);
                starter.dispose();
            }
        } else if (e.getActionCommand().equals("Assistant")) {
            if (!(accessCodeNum.getText().equals(""))
                    & !(nameText.getText() == "")) {
                try {
                    loadStore(accessCodeNum.getText());
                    assistant = new Assistant(nameText.getText(),Integer.parseInt(accessCodeNum.getText()));
                    new AssistantUI(assistant,store);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                starter.dispose();
            } else {
                new WrongInformationInput();
            }
        }
    }
}

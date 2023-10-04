/*
package ui;

import model.Assistant;
import model.Customer;
import model.Store;
import model.Flower;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;



public class StorePage {
    private static final String JSON_STORE = "./data/store.json";
    private Assistant assistant;
    private Customer customer;
    private Store store;
    private Scanner input;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: runs the store page
    public StorePage() throws IOException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        logIn();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void logIn() throws IOException {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // EFFECTS: display menu of options
    private void displayMenu() {
        System.out.println("\nSelect from");
        System.out.println("\nl -> log in");
        System.out.println("\nq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: initializes
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) throws IOException {
        if (command.equals("l")) {
            nextStep();
        } else {
            System.out.println("Selection not available");
        }
    }

    // MODIFIES: this
    // EFFECTS: next step after the first menu
    private void nextStep() throws IOException {
        System.out.print("Enter your nameText:");
        String name = input.next();
        selectLogger(name);
    }

    // EFFECTS: prompts user to select their status from customer or assistant
    private void selectLogger(String name) throws IOException {
        String selection = "";

        while (!(selection.equals("a") || selection.equals("c"))) {
            System.out.println("a -> assistant log in");
            System.out.println("c -> customer log in");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("a")) {
            System.out.print("\nEnter your accesscode:");
            int num = input.nextInt();
            assistant = new Assistant(name, num);
            loadStore(String.valueOf(num));
            assistantLogIn(assistant);
        } else {
            customer = new Customer(name);
            customerLogIn(customer);
        }
    }

    // All function about customer
    // MODIFIES: this
    // EFFECTS: log in this system with customer
    private void customerLogIn(Customer customer) {
        String selection = "";

        while (!(selection.equals("cart") || selection.equals("s") || selection.equals("f"))) {
            System.out.println("cart -> view cart");
            System.out.println("s -> shopping");
            System.out.println("f -> complete shopping");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("cart")) {
            cart(customer);
        } else if (selection.equals("f")) {
            System.out.print("\nGoodbye");
        } else {
            showStockInStore(customer);
        }
    }


    // EFFECTS: show all flowers' nameText in store's stock
    private void showStockInStore(Customer customer) {
        StringBuilder s = new StringBuilder();
        for (String i : showAllFlowerName()) {
            s.append(" ").append(i).append(" ");
        }

        System.out.println("Flowers in the store: " + s);
        customerLogIn(customer);
    }

    // EFFECTS: return a list of nameText of all flowers
    private List<String> showAllFlowerName() {
        List<Flower> flowers = store.getFlowers();
        List<String> names = new LinkedList<>();
        for (Flower flower : flowers) {
            names.add(flower.getName());
        }

        return names;
    }

    @SuppressWarnings("methodlength")
    // MODIFIES: this
    // EFFECTS: view cart, remove a flower, and add a flower
    private void cart(Customer customer) {
        String selection = "";

        while (!(selection.equals("p") || selection.equals("add")
                || selection.equals("r") || selection.equals("v") || selection.equals("b"))) {
            System.out.println("p -> total price");
            System.out.println("add -> add a flower into your cart");
            System.out.println("r -> remove a flower from your cart");
            System.out.println("v -> view cart");
            System.out.println("b -> back");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("p")) {
            System.out.println(customer.getPrice());
            cart(customer);
        } else if (selection.equals("add")) {
            System.out.println("Enter flower's nameText");
            String n = input.next();

            if (store.checkStock(n) > 1) {
                customer.addToCart(store.requiredFlower(n));
            } else {
                System.out.println("\nSorry, not available");
            }

            cart(customer);
        } else if (selection.equals("r")) {
            System.out.println("Enter flower's nameText");
            String n = input.next();
            customer.removeOneFlower(n);
            store.addStocks(n, 1);
            cart(customer);
        } else if (selection.equals("v")) {
            showStockInCart(customer);
            cart(customer);
        } else {
            customerLogIn(customer);
        }
    }

    // EFFECTS: show all flowers' nameText in cart
    private void showStockInCart(Customer customer) {
        StringBuilder s = new StringBuilder();
        for (String i : showAllFlowerNameInCart(customer)) {
            s.append(" ").append(i).append(" ");
        }

        System.out.println("Flowers in cart contain " + s);
    }

    // EFFECTS: return a list of nameText of all flowers in cart
    private List<String> showAllFlowerNameInCart(Customer customer) {
        List<Flower> flowers = customer.getCart();
        List<String> names = new LinkedList<>();
        for (Flower flower : flowers) {
            names.add(flower.getName());
        }

        return names;
    }


    // All functions about assistant
    @SuppressWarnings("methodlength")
    // MODIFIES: this
    // EFFECTS: log in as an assistant
    private void assistantLogIn(Assistant assistant) throws IOException {
        String selection = "";

        while (!(selection.equals("check") || selection.equals("add") || selection.equals("b")
                || selection.equals("s"))) {
            System.out.println("check -> check stock");
            System.out.println("add -> add flower to store");
            System.out.println("b -> back to homepage");
            System.out.println("s -> save this store");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("check")) {
            System.out.print("\nEnter the flower nameText you want to check:");
            String n = input.next();

            if (store.checkStock(n) == 0) {
                System.out.println("You need to add the stock");
            } else if (store.checkStock(n) == -1) {
                System.out.println("There is no this flower in our store");
            } else {
                System.out.println("This flower stock is " + store.checkStock(n));
            }

            assistantLogIn(assistant);
        } else if (selection.equals("b")) {
            logIn();
        } else if (selection.equals("s")) {
            saveStore();
        } else {
            System.out.println("\n Enter the nameText of flower you want to add:");
            String name = input.next();

            System.out.println("\n Enter the flower meaning of the flower you want to add:");
            String fm = input.next();

            System.out.println("\nEnter the price of the flower you want to add:");
            double p = input.nextDouble();

            System.out.println("\nEnter the stock of the flower you want to add");
            int s = input.nextInt();

            assistant.setFlower(p, fm, name, s);
            store.addNewFlower(assistant.getFlower());

            assistantLogIn(assistant);
        }
    }

    // Json Part
    // EFFECTS: saves the store to file
    private void saveStore() {
        try {
            jsonWriter.open();
            jsonWriter.write(store);
            jsonWriter.close();
            System.out.println("Saved " + store.getAccessCode() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads store from file
    private void loadStore(String accessCode) {
        try {
            String jsonData = readfile();
            if (!jsonData.isEmpty()) {
                store = jsonReader.read();
                if (store.getAccessCode().equals(accessCode)) {
                    System.out.println("Loaded " + store.getAccessCode() + " from " + JSON_STORE);
                } else {
                    System.out.println("Loaded a new store " + accessCode);
                    store = new Store(accessCode);
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: read source file as String and returns it
    private String readfile() throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(JSON_STORE), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

}

*/

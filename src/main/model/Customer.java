package model;

import java.util.LinkedList;
import java.util.List;

public class Customer extends LogIn {
    private List<Flower> cart;
    private double price;

    public Customer(String name) {
        super(name);
        cart = new LinkedList<>();
        price = 0;
    }

    // getter
    public List<Flower> getCart() {
        return cart;
    }

    public double getPrice() {
        return totalPrice();
    }


    // REQUIRES: checkStock
    // MODIFIES: this
    // EFFECTS: add one wanted flower into the customer's cart if there is no this flower in cart. otherwise
    //          add 1 stock into this given flower in our cart
    public void addToCart(Flower flower) {
        Flower f = new Flower(flower.getPrice(), flower.getFlowerMeaning(), flower.getName(), 1);
        if (!checkCart(flower.getName())) {
            cart.add(f);
        } else {
            for (Flower fl : cart) {
                if (f.getName().equals(fl.getName())) {
                    fl.addStock(1);
                }
            }
        }

        flower.minusStock(1);
    }

    // REQUIRES: cart is not empty
    // MODIFIES: this
    // EFFECTS: remove the type of unwanted flower from cart
    public void removeUnwantedFlower(String name) {
        for (Flower f: cart) {
            if (checkCart(name)) {
                cart.remove(f);
            }
        }
    }

    // REQUIRES: this flower in the cart
    // MODIFIES: this
    // EFFECTS: remove one flower from the given type of flower in cart. If the stock of this
    //          flower is equal to 0, remove this flower
    public void removeOneFlower(String name) {
        for (Flower f : cart) {
            if (checkCart(name)) {
                if (!(f.getStock() - 1 == 0)) {
                    f.minusStock(1);
                } else {
                    cart.remove(f);
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: return the number of the given flowers in cart. If there is no flower or no given flower, return
    //          0.
    public int numberFlowers(String name) {
        int number = 0;
        for (Flower f : cart) {
            if (f.getName().equals(name)) {
                number = f.getStock();
            }
        }

        return number;
    }

    // EFFECTS: calculate the total price in cart. If cart is empty, return 0.
    private double totalPrice() {
        price = 0;
        for (Flower f : cart) {
            price = price + f.getPrice() * f.getStock();
        }

        return price;
    }

    //EFFECTS: return false if there is the given flower in cart;
    //         otherwise, return true;
    private boolean checkCart(String name) {
        for (Flower i : cart) {
            if (i.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

}

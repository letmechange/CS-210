package model;

import org.json.JSONObject;
import persistence.Writable;

public class Flower implements Writable {
    private double price;
    private String flowerMeaning;
    private String name;
    private int stock;

    //EFFECTS: register a flower in the flower store
    public Flower(double p, String fm, String n, int stock) {
        price = p;
        flowerMeaning = fm;
        name = n;
        this.stock = stock;
    }

    // getter
    public double getPrice() {
        return price;
    }

    public String getFlowerMeaning() {
        return flowerMeaning;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    //MODIFIES: this
    //EFFECTS: add the given number of the flower
    public void addStock(int number) {
        stock = stock + number;
    }

    //REQUIRES: getStock > 0
    //MODIFIES: this
    //EFFECTS: minus the given number of the flower
    public void minusStock(int number) {
        stock = stock - number;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("flower meaning", flowerMeaning);
        json.put("price", price);
        json.put("stock", stock);
        return json;
    }
}
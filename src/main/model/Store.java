package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.List;

public class Store implements Writable {
    private List<Flower> flowers;
    private String accessCode;

    //EFFECTS: construct flower stock in Store
    public Store(String accessCode) {
        flowers = new LinkedList<>();
        this.accessCode = accessCode;
    }

    // getter
    public List<Flower> getFlowers() {
        return flowers;
    }

    public String getAccessCode() {
        return accessCode;
    }


    //MODIFIES: this
    //EFFECTS: return the number of the finding flower's stock. If there is no that flower,
    //         return -1.
    public int checkStock(String name) {
        for (Flower flower : flowers) {
            if (flower.getName().equals(name)) {
                return flower.getStock();
            }
        }
        return -1;
    }

    //REQUIRES: checkStock = -1
    //MODIFIES: this
    //EFFECTS: add a new flower into store
    public void addNewFlower(Flower flower) {
        flowers.add(flower);
    }

    // REQUIRES: flowers is not empty
    // MODIFIES: this
    // EFFECTS: remove flower from store
    public void removeFlower(String name) {
        flowers.removeIf(f -> f.getName().equals(name));
    }

    //REQUIRES: checkStock >= 0
    //MODIFIES: this
    //EFFECTS: add stock into existed flower in our store
    public void addStocks(String name, int num) {
        for (Flower f : flowers) {
            if (f.getName().equals(name)) {
                f.addStock(num);
            }
        }
    }

    // REQUIRES: the required flower in the store
    // MODIFIES: this
    // EFFECTS: return the required flower in the store
    public Flower requiredFlower(String name) {
        for (Flower f: flowers) {
            if (f.getName().equals(name)) {
                return f;
            }
        }

        return null;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("accessCode", accessCode);
        json.put("flowers", flowersToJson());
        return json;
    }

    private JSONArray flowersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Flower f: flowers) {
            jsonArray.put(f.toJson());
        }

        return jsonArray;
    }
}

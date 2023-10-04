package persistence;

import model.Flower;
import model.Store;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads store from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public Store read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStore(jsonObject);
    }

    // EFFECTS: read source file as String and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses store from JSON object and returns it
    private Store parseStore(JSONObject jsonObject) {
        String accessCode = jsonObject.getString("accessCode");
        Store store = new Store(accessCode);
        addFlowers(store, jsonObject);
        return store;
    }

    // MODIFIES: store
    // EFFECTS: parses flowers from JSON object and adds them to store
    private void addFlowers(Store store, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("flowers");
        for (Object json : jsonArray) {
            JSONObject nextFlower = (JSONObject) json;
            addFlower(store, nextFlower);
        }
    }

    // MODIFIES: store
    // EFFECTS: parses flower from JSON object and adds it to store
    private void addFlower(Store store, JSONObject nextFlower) {
        String name = nextFlower.getString("name");
        String flowerMeaning = nextFlower.getString("flower meaning");
        double price = nextFlower.getDouble("price");
        int stock = nextFlower.getInt("stock");
        Flower flower = new Flower(price, flowerMeaning, name, stock);
        store.addNewFlower(flower);
    }


}

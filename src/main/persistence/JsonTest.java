package persistence;

import model.Flower;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkFlower(String name, String flowerMeaning, double price, int stock, Flower flower) {
        assertEquals(name, flower.getName());
        assertEquals(flowerMeaning, flower.getFlowerMeaning());
        assertEquals(price, flower.getPrice());
        assertEquals(stock, flower.getStock());
    }
}

package test;

import model.Assistant;
import model.Flower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssistantTest {
    private Assistant assistant;

    @BeforeEach
    void runBefore() {
        assistant = new Assistant("a", 1220);
    }

    @Test
    void testGetter() {
        assertEquals("a", assistant.getName());
        assertEquals(1220, assistant.getAccessCode());
    }

    @Test
    void testGetAndSetFlower() {
        Flower flower = new Flower(1.2,"a","b", 2);
        assertTrue(testTheSameFlower(flower));
    }

    private boolean testTheSameFlower(Flower flower) {
        assistant.setFlower(1.2,"a", "b", 2);
        Flower testFlower = assistant.getFlower();
        return flower.getName().equals(testFlower.getName())
                && flower.getFlowerMeaning().equals(testFlower.getFlowerMeaning())
                && flower.getPrice() == testFlower.getPrice()
                && flower.getStock() == testFlower.getStock();
    }
}

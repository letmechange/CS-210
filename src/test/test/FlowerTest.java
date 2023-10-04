package test;

import model.Flower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlowerTest {
    private Flower flower;

    @BeforeEach
    void runBefore() {
        flower = new Flower(1.1,"a", "b", 0);
    }


    @Test
    void testGetter() {
        assertEquals(1.1, flower.getPrice());
        assertEquals("a", flower.getFlowerMeaning());
        assertEquals("b", flower.getName());
        assertEquals(0, flower.getStock());
    }

    @Test
    void testAddStock() {
        flower.addStock(1);
        assertEquals(1, flower.getStock());
    }

    @Test
    void testMinusStock() {
        flower.addStock(20);
        flower.minusStock(10);
        assertEquals(10, flower.getStock());
    }
}

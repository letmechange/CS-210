package test;

import model.Flower;
import model.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class StoreTest {
    private Store store;
    private Flower flower;

    @BeforeEach
    void runBefore() {
        store = new Store("1");
        flower = new Flower(1.1, "a", "b", 0);
    }

    @Test
    void testGetter() {
        assertEquals(0, store.getFlowers().size());
        assertEquals("1", store.getAccessCode());

    }

    @Test
    void testAddFlower() {
        store.addNewFlower(flower);
        assertEquals(1, store.getFlowers().size());
    }

    @Test
    void testCheckStockWithEmptyStore() {
        assertEquals(-1, store.checkStock("a"));
    }

    @Test
    void testCheckStockWithoutWantedFlower() {
        store.addNewFlower(flower);
        assertEquals(-1, store.checkStock("a"));
    }

    @Test
    void testCheckStockWithWantedFlower() {
        store.addNewFlower(flower);
        assertEquals(0, store.checkStock("b"));
    }

    @Test
    void testRemoveFlowerFromStore() {
        store.addNewFlower(flower);
        store.removeFlower("b");
        assertTrue(store.getFlowers().isEmpty());
    }

    @Test
    void testAddFlowerStockToStore() {
        store.addNewFlower(flower);
        store.addStocks("b", 3);
        assertEquals(3, store.checkStock("b"));
    }


    @Test
    void findRequiredFlower() {
        store.addNewFlower(flower);
        assertEquals(flower, store.requiredFlower("b"));
    }

    @Test
    void NotFindRequiredFlower() {
        assertNull(store.requiredFlower("b"));
    }
}

package test;

import model.Customer;
import model.Flower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerTest {
    private Customer customer;
    private Flower f1;
    private Flower f2;

    @BeforeEach
    void runBefore() {
        customer = new Customer("a");
        f1 = new Flower(1.2,"a","b", 2);
        f2 = new Flower(1.1, "c", "d", 3);
    }

    @Test
    void testTotalPriceWithEmptyCart() {
        assertEquals(0, customer.getPrice());
    }

    @Test
    void testTotalPriceWithNonEmptyCart() {
        customer.addToCart(f1);
        customer.addToCart(f2);
        customer.addToCart(f1);
        assertEquals(3.5, customer.getPrice());
    }

    @Test
    void testAddTheSameFlowerIntoCart() {
        customer.addToCart(f1);
        customer.addToCart(f1);
        assertEquals(1, customer.getCart().size());
        assertEquals(0, f1.getStock());
    }

    @Test
    void testAddTheDifferentFlowerIntoCart() {
        customer.addToCart(f1);
        customer.addToCart(f2);
        assertEquals(2, customer.getCart().size());
    }

    @Test
    void testNumberOfGivenFlowersInAnEmptyCart() {
        assertEquals(0, customer.numberFlowers("a"));
    }

    @Test
    void testNumberOfGivenFlowersInANonEmptyCart() {
        customer.addToCart(f1);
        customer.addToCart(f2);
        customer.addToCart(f1);
        assertEquals(2, customer.numberFlowers("b"));
    }

    @Test
    void testRemoveFlowerFromCart() {
        customer.addToCart(f1);
        customer.addToCart(f2);
        customer.addToCart(f1);
        customer.removeUnwantedFlower("b");
        assertEquals(1, customer.getCart().size());
    }

    @Test
    void testRemoveOneFlowerButTheStockNotZero() {
        customer.addToCart(f1);
        customer.addToCart(f1);
        customer.removeOneFlower("b");
        assertEquals(1, customer.numberFlowers("b"));

    }

    @Test
    void testRemoveOneFlowerAndTheStockZero() {
        customer.addToCart(f1);
        customer.removeOneFlower("b");
        assertTrue(customer.getCart().isEmpty());
    }

}

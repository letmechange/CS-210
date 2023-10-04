package persistence;

import model.Flower;
import model.Store;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Store s = new Store("1");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyStore() {
        try {
            Store store = new Store("1");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyStore.json");
            writer.open();
            writer.write(store);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyStore.json");
            store = reader.read();
            assertEquals("1", store.getAccessCode());
            assertEquals(0, store.getFlowers().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralStore() {
        try {
            Store store = new Store("1");
            store.addNewFlower(new Flower(1.1,"b", "a", 100));
            store.addNewFlower(new Flower(1.2, "d", "c", 200));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStore.json");
            writer.open();
            writer.write(store);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralStore.json");
            store = reader.read();
            assertEquals("1", store.getAccessCode());
            List<Flower> flowers = store.getFlowers();
            assertEquals(2, flowers.size());
            checkFlower("a","b",1.1,100, flowers.get(0));
            checkFlower("c","d",1.2,200, flowers.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

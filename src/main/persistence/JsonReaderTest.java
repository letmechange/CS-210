package persistence;

import model.Flower;
import model.Store;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest  extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Store store = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyStore.json");
        try {
            Store store = reader.read();
            assertEquals("1", store.getAccessCode());
            assertEquals(0, store.getFlowers().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStore.json");
        try {
            Store store = reader.read();
            assertEquals("1", store.getAccessCode());
            List<Flower> flowers = store.getFlowers();
            assertEquals(2, store.getFlowers().size());
            checkFlower("a","b",1.1,100, flowers.get(0));
            checkFlower("c","d",1.2,200, flowers.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

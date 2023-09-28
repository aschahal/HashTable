import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class HashtableMapTests {
    /**
     * Test case for putting and getting a value in the HashtableMap
     * This will also test for IllegalArgumentException when trying to put null or duplicate key
     */
    @Test
    public void putAndGetTest() {
        HashtableMap<String, Integer> hashtable = new HashtableMap<>();
        hashtable.put("one", 1);
        assertEquals(1, hashtable.get("one"));

        assertThrows(IllegalArgumentException.class, () -> {
            hashtable.put("one", 1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            hashtable.put(null, 1);
        });
    }

    /**
     * Test case for removing a value from the HashtableMap
     * This will also test for NoSuchElementException when trying to remove a non-existent key
     */
    @Test
    public void removeTest() {
        HashtableMap<String, Integer> hashtable = new HashtableMap<>();
        hashtable.put("one", 1);
        assertEquals(1, hashtable.remove("one"));
        assertEquals(0, hashtable.getSize());

        assertThrows(NoSuchElementException.class, () -> {
            hashtable.remove("one");
        });
    }

    /**
     * Test case for checking the size and capacity of HashtableMap
     * This will also test the dynamic growing of the HashtableMap when the load factor is exceeded
     */
    @Test
    public void sizeAndCapacityTest() {
        HashtableMap<String, Integer> hashtable = new HashtableMap<>();
        for (int i = 0; i < 6; i++) {
            hashtable.put("key" + i, i);
        }
        assertEquals(6, hashtable.getSize());
        assertEquals(8, hashtable.getCapacity());

        hashtable.put("key6", 6);
        hashtable.put("key7", 7);
        assertEquals(8, hashtable.getSize());
        assertEquals(16, hashtable.getCapacity());
    }
}
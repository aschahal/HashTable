import java.util.NoSuchElementException;

/**
 * This class implements a Hashtable Map ADT
 * @param <KeyType> type of keys maintained by this map
 * @param <ValueType> type of mapped value
 */
@SuppressWarnings("unchecked")
public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {
    private static final int DEFAULT_CAPACITY = 8;
    private static final double LOAD_FACTOR = 0.7;

    // Array of pairs to store the elements in the hash table
    protected Pair<KeyType, ValueType>[] hashtable;
    private int size;

    /**
     * Constructs a HashtableMap with specified capacity
     * @param capacity the capacity of the HashtableMap
     */
    public HashtableMap(int capacity) {
        hashtable = new Pair[capacity];
        size = 0;
    }

    /**
     * Constructs a HashtableMap with default capacity
     */
    public HashtableMap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Pair class used to hold key-value pairs in hashtable
     * @param <KeyType> type of the key
     * @param <ValueType> type of the value
     */
    private class Pair<KeyType, ValueType> {
        KeyType key;
        ValueType value;

        Pair(KeyType key, ValueType value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Helper method to calculate the load factor of the hashtable
     * @return the load factor of the hashtable
     */
    private double loadFactor() {
        return (double)size / hashtable.length;
    }

    /**
     * Puts a key-value pair into the hashtable.
     * @param key key to be mapped
     * @param value value to be mapped to the key
     * @throws IllegalArgumentException if key is null or duplicate
     */
    public void put(KeyType key, ValueType value) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        int index = findKey(key);
        if (hashtable[index] != null && hashtable[index].key.equals(key)) {
            throw new IllegalArgumentException("Duplicate Key");
        }
        hashtable[index] = new Pair<>(key, value);
        size++;

        if (loadFactor() >= LOAD_FACTOR) {
            rehash();
        }
    }

    /**
     * The number of key-value mappings in hashtable
     * @return The number of key-value mappings in hashtable
     */
    public int getSize() {
        return hashtable.length;
    }

    /**
     * Returns the current capacity of this hashtable
     * @return the current capacity of hashtable
     */
    public int getCapacity() {
        return hashtable.length;
    }

    /**
     * Checks if hashtable contains the specified key
     * @param key Key to be checked
     * @return true if hashtable contains key, false otherwise
     */
    public boolean containsKey(KeyType key) {
        int index = findKey(key);
        return hashtable[index] != null && hashtable[index].key.equals(key);
    }

    /**
     * Gets the value to which the specified key is mapped.
     * @param key Key with the value that is to be returned
     * @return the value in which the specified key is mapped to
     * @throws NoSuchElementException if key is not found
     * @throws IllegalArgumentException if key is null
     */
    public ValueType get(KeyType key) throws NoSuchElementException {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        int index = findKey(key);
        if (hashtable[index] == null || !hashtable[index].key.equals(key)) {
            throw new NoSuchElementException("Key not found");
        }
        return hashtable[index].value;
    }

    /**
     * Removes the mapping for the specified key from hashtable
     * @param key Key whose mapping is to be removed from hashtable
     * @return the previous value associated with key, or null if there
     * is no mapping for key
     * @throws NoSuchElementException if key not found
     * @throws IllegalArgumentException if key is null
     */
    public ValueType remove(KeyType key) throws NoSuchElementException {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        int index = findKey(key);
        if (hashtable[index] == null || !hashtable[index].key.equals(key)) {
            throw new NoSuchElementException("Key not found");
        }
        ValueType removedValue = hashtable[index].value;
        hashtable[index] = null;
        size--;

        // Rehash all the elements in the collision chain
        index = (index + 1) % hashtable.length;
        while (hashtable[index] != null) {
            Pair<KeyType, ValueType> rehashPair = hashtable[index];
            hashtable[index] = null;
            size--;
            put(rehashPair.key, rehashPair.value);
            index = (index + 1) % hashtable.length;
        }

        return removedValue;
    }

    /**
     * Clears hashtable, removing all mappings
     */
    public void clear() {
        hashtable = new Pair[hashtable.length];
        size = 0;
    }

    /**
     * Helper method to finding the index of a key in the hashtable
     * @param key Key to find index of
     * @return index of key specified
     */
    private int findKey(KeyType key) {
        int index = Math.abs(key.hashCode()) % hashtable.length;
        int startIndex = index;
        do {
            if (hashtable[index] == null || hashtable[index].key.equals(key)) {
                return index;
            }
            index = (index + 1) % hashtable.length;
        } while (index != startIndex);
        return index;
    }

    /**
     * Helper method to rehash the hashtable when load factor reaches threshold
     */
    private void rehash() {
        Pair<KeyType, ValueType>[] oldHashtable = hashtable;
        hashtable = new Pair[oldHashtable.length * 2];
        size = 0;
        for (Pair<KeyType, ValueType> pair : oldHashtable) {
            if (pair != null) {
                put(pair.key, pair.value);
            }
        }
    }


}
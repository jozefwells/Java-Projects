
/*
 * HashTable data structure that uses linear probing and double hashing
 *
 * @author Jozef Wells
 * @param <T>
 */
public class HashTable<T> {

    private final HashObject<T>[] table;
    private final int openAddressType;
    private int referenceCount, duplicates;
    private int size;
    private int globalProbes;

    /*
     * Creates a HashTable using defined capacity and address type
     *
     * @param capacity - the size of the table
     * @param addressType - 1 for linear probing, 2 for double hashing
     */
    public HashTable(int capacity, int addressType) {
        this.openAddressType = addressType;
        table = new HashObject[capacity];
        this.referenceCount = 0;
        this.size = 0;
    }

    /*
     * Inserts a HashObject into the HashTable using the addressType
     *
     * @param newObj - HashObject to be inserted
     */
    public void put(HashObject<T> newObj) { //returns index of placed item
        if (size == table.length) {
            System.out.println("Table is full");
            return;
        }
        int localProbes = 1;
        boolean duplicate = false;
        referenceCount++;

        int hashedKey = linHash(newObj.getKey(),0);

        /* LINEAR PROBING */
        if (openAddressType == 1) {
            while(this.occupied(hashedKey) && localProbes < table.length) {
                if (duplicate(table[hashedKey], newObj)) {
                    duplicate = true;
                    table[hashedKey].increaseDuplicates();
                    duplicates++;
                    break;
                } else {
                    hashedKey = linHash(newObj.getKey(), localProbes);
                    localProbes++;
                }
            }

          /* DOUBLE HASHING */
        } else {
            while(this.occupied(hashedKey) && localProbes < table.length) {
                if (duplicate(table[hashedKey], newObj)) {
                    duplicate = true;
                    table[hashedKey].increaseDuplicates();
                    duplicates++;
                    break;
                } else {
                    hashedKey = doubleHash(newObj.getKey(), localProbes);
                    localProbes++;
                }
            }
        }
        /* Insert object into table */
        if (!this.occupied(hashedKey) && !duplicate) {
            table[hashedKey] = newObj;
            size++;
            globalProbes += localProbes;
            newObj.setProbeCount(localProbes);
        }
    }

    /*
     * Retrieves HashObject at provided index
     *
     * @param index - index of desired HashObject
     */
    public HashObject<T> get(int index) {
        return table[index];
    }

    /*
     * Linear Hashing method
     *
     * @param key - key of HashObject
     * @param i - index of the probe sequence
     */
    private int linHash(T key, int i) {
        int h1 = PositiveMod(key.hashCode(), table.length); //primary hash function
        if ((h1 + i) > table.length-1) {
            return h1 % table.length;
        }
        return h1 + i;
    }

    /*
     * Double hash method
     *
     * @param key - key of HashObject
     * @param i - index of the probe sequence
     */
    private int doubleHash(T key, int i) {
        int h1 = PositiveMod(key.hashCode(), table.length); //primary hash function
        int h2 = 1 + PositiveMod(key.hashCode(), table.length-2); //secondary hash function
        int dubHash = PositiveMod(h1+(i * h2), table.length);

        if (dubHash > table.length-1) {
            dubHash %= table.length;
        }
        return dubHash;
    }

    /*
     * Method that produces positive results from modding
     *
     * @param dividend
     * @parma divisor
     */
    public int PositiveMod(int dividend, int divisor) {
        int value = dividend % divisor;
        if (value < 0) {
            value += divisor;
        }
        return value;
    }

    /*
     * Method that compares two HashObjects to see if they are identical
     *
     * @param one
     * @param two
     */
    public boolean duplicate(HashObject<T> one, HashObject<T> two) {
        return one.getKey().equals(two.getKey());
    }

    /*
     * Checks whether an index location is occupied or not
     *
     * @param index
     */
    public boolean occupied(int index) {
        return table[index] != null;
    }

    /*
     * Retrieves the total number of duplicates
     */
    public int getDuplicates() {
        return duplicates;
    }

    /*
     * Retrieves total number of HashObject references
     */
    public int getReferenceCount() {
        return referenceCount;
    }

    /*
     * Retrieves the total number of probes for inserted elements
     */
    public int totalProbes() {
        return globalProbes;
    }

    /*
     * Average number of probes taken to insert
     */
    public double averageProbes() {
        return ((double)totalProbes()/(double)size());
    }

    /*
     * Number of elements inserted into table
     */
    public int size() {
        return this.size;
    }
}

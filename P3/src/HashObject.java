public class HashObject<T> {
    private T key;
    private int duplicates, probeCount;

    /*
     * HashObject that contains generic data as key
     */
    public HashObject(T key) {
        this.duplicates = 0;
        this.probeCount = 0;
        this.key = key;
    }

    public T getKey() { return this.key; }

    public void increaseDuplicates() {
        this.duplicates++;
    }

    public int getDuplicateCount() { return this.duplicates; }

    public void setProbeCount(int count) {
        this.probeCount = count;
    }

    public int getProbeCount() { return this.probeCount; }

    public String toString() {
        return this.key.toString();
    }
}

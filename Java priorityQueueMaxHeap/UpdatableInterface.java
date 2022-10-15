
public interface UpdatableInterface <T extends Comparable<T>> extends Comparable<T>{

	
    /**
     * Increase the priorities for a given process in the queue if it has waited enough 
     * time but not to exceed its maximum priority.
     *
     * @param timeToIncrementPriority how long it should have waited for
     * @param maxPriority  the maximum priority value the process can have
     */
    public void update (int timeToIncrementPriority, int maxPriority, MaxHeap<T> heap, int index);
    
}

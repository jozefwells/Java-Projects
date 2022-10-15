public class MyPriorityQueue <T extends UpdatableInterface<T>> extends MaxHeap <T> implements PriorityQueueInterface<T> {

	
	public MyPriorityQueue() {
		super();
	}
	
	@Override
	public void enqueue(T task) {
		super.insert(task);
	}

	@Override
	public T dequeue() {
		return super.extractMax();
	}

	@Override
	public void update(int timeToIncrementPriority, int maxPriority) {
		for (int i = 0; i < super.size(); i++) {
			T taskOrPlayer = super.get(i);
			taskOrPlayer.update(timeToIncrementPriority, maxPriority, super.getHeap(), i);
		}		
	}
}

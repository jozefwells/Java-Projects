import java.util.Arrays;

/**
 * MaxHeap data structure that sorts data by priority in a complete binary tree
 * 
 * @author Jozef Wells
 * @param <T>
 * 
 */
public class MaxHeap<T extends Comparable<T>> {

	private int size;
	private int capacity;
	private T[] heap;

	

	/** default capacity of 50 */
	@SuppressWarnings("unchecked")
	public MaxHeap() {
		this.capacity = 50;
		heap = (T[])new Comparable[capacity];
	}

	/**
	 * Creates a maxHeap from existing array of tasks
	 * 
	 * @param array
	 */
	public MaxHeap(T[] array) {
		heap = array;
		this.capacity = array.length;
		for (T t : array) {
			if (t != null) 
				this.size++;
		}
	}

	public MaxHeap<T> getHeap() {
		return this;
	}
	
	/**
	 * Retrieves the parent node index from provided node index
	 * 
	 * @param index - index of child node
	 * @returns index - index of parent node
	 */
	public int getParent(int childIndex) {
		return (childIndex - 1) / 2;
	}

	/**
	 * Retrieves the child node index from provided node index
	 * 
	 * @param index - index of parent node
	 * @param left - whether the user wishes to retrieve the left child
	 * @returns index - index of parent node
	 */
	public int getChild(int index, boolean left) {
		return 2 * index + (left ? 1 : 2);
	}

	/**
	 * Inserts a given task into the heap
	 * 
	 * @param task - the task to insert into the heap
	 */
	public void insert(T task) {
		if (isFull()) {
			heap = Arrays.copyOf(heap, heap.length * 2);
		}

		heap[size] = task;
		heapifyUp(size++);
	}
	
	public void increaseKey(int i, T key) {
		heap[i] = key;
		while (i > 1 && (heap[getParent(i)]).compareTo(heap[i]) == 1) {
			T tmp = heap[i];
			heap[i] = heap[getParent(i)];
			heap[getParent(i)] = tmp;
			i = getParent(i);
		}
	}

	/**
	 * Retrieves the Task at the top of the heap
	 * 
	 * @returns Task - task at index 0
	 */
	public T max() {
		buildMaxHeap();
		return heap[0];
	}

	/**
	 * Retrieves and removes the Task at the top of the heap
	 * 
	 * @returns Task - task at index 0
	 */
	public T extractMax() {
//		buildMaxHeap();
		T max = heap[0];
		heap[0] = heap[size - 1];
		heapifyDown(0, size-1);
		size--;
		return max;
	}
	
	/**
	 * Builds a heap/tree by utilizing the heapifyUp method 
	 * 
	 */
	public void buildMaxHeap() {
		int start = (size /2) - 1;
		
		for (int i = start; i >= 0; i--) {
			heapifyDown(i, size-1);
		}
	}

	/**
	 * Swaps the index provided with its parent until it is in the proper location in the heap
	 * 
	 * @param index - index of the task to move up in the heap
	 */
	public void heapifyUp(int index) {
		if (index < 1 || index >= size)
			return;		
		
		T newValue = heap[index];
        while (index > 0 && ((newValue).compareTo(heap[getParent(index)]) == 1)) {
            heap[index] = heap[getParent(index)];
            index = getParent(index);
        }

        heap[index] = newValue;
	}

	/**
	 * Swaps the index provided with the largest of one of its children until it is in the proper location in the heap
	 * 
	 * @param index - index of the task to move up in the heap
	 * @param lastHeapIndex - index of the last element in the array/heap
	 */
	public void heapifyDown(int index, int lastHeapIndex) {
		int childToSwap;

        while (index <= lastHeapIndex) {
            int leftChild = getChild(index, true);
            int rightChild = getChild(index, false);
            if (leftChild <= lastHeapIndex) {
                if (rightChild > lastHeapIndex) {
                    childToSwap = leftChild;
                } else {
                    childToSwap = ((heap[leftChild]).compareTo(heap[rightChild]) == 1 ? leftChild : rightChild);
                }

                if ((heap[index]).compareTo(heap[childToSwap]) == -1) {
                    T tmp = heap[index];
                    heap[index] = heap[childToSwap];
                    heap[childToSwap] = tmp;
                } else {
                    break;
                }

                index = childToSwap;
            } else {
                break;
            }
        }
	}

	/**
	 * Sorts the heap from least to greatest in the array indices 	 
	 *  
	 */
	public void sort() {  
		buildMaxHeap();
        int lastHeapIndex = size - 1;
        for (int i = 0; i < lastHeapIndex; i++) {
            T tmp = heap[0];
            heap[0] = heap[lastHeapIndex - i];
            heap[lastHeapIndex - i] = tmp;

            heapifyDown(0, lastHeapIndex - i - 1);
        }
	}

	/**
	 * Checks the size of the heap to dictate if it is empty or not and returns true if empty
	 * 
	 * @returns boolean - true if the heap/array is empty otherwise false
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns the task at the provided index location
	 * 
	 * @param index - index location of a task
	 * @return Task - task at given index location 
	 */
	public T get(int index) {
		return heap[index];
	}

	/**
	 * Retrieves the index of the provided task from the heap
	 * 
	 * @param t - task
	 * @return int - index of provided task in heap array
	 */
	public int indexOf(Task t) {
		for (int i = 0; i < heap.length; i++) {
			if (heap[i] == t) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Checks whether the number of elements is equal to the size of the array
	 * 
	 * @return true if the size is equal to the capacity of the array
	 */
	public boolean isFull() {
		return size == capacity;
	}

	/**
	 * Retrieves the number of elements in the heap
	 * 
	 * @return int - size of the heap
	 */
	public int size() {
		return this.size;
	}
	

}

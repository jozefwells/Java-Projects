/**
 * The MaxHeapTest class is meant to test the various methods of the MaxHeap class and their functionality.
 * 
 * @author wells
 *
 */
public class MaxHeapTest {
	private MaxHeap<Task> heap;
	
	private TaskInterface.TaskType fishing = TaskInterface.TaskType.FISHING;
	private TaskInterface.TaskType feeding = TaskInterface.TaskType.FEEDING;
	private TaskInterface.TaskType foraging = TaskInterface.TaskType.FORAGING;
	private TaskInterface.TaskType mining = TaskInterface.TaskType.MINING;
	private TaskInterface.TaskType socializing = TaskInterface.TaskType.SOCIALIZING;

	private Task task1 = new Task(1, fishing , "Fishing in the lake");
	private Task task2 = new Task(2, feeding, "Feeding the pigs");
	private Task task3 = new Task(3, foraging, "Foraging for berries");
	private Task task4 = new Task(4, mining, "Mining for coal");
	private Task task5 = new Task(5, socializing, "Socializing with the blacksmith");
	private Task task6 = new Task(6, fishing, "Fishing in the pond");
	private Task task7 = new Task(7, foraging, "Foraging for flowers");
	
	
	private void runTests() {
		task1.setPriority(1);
		task2.setPriority(2);
		task3.setPriority(3);
		task4.setPriority(4);
		task5.setPriority(5);
		task6.setPriority(6);
		task7.setPriority(7);

		
		sortTest();
		System.out.println("---------------------");
		buildHeapTest();
		System.out.println("---------------------");
		maxTest();
		System.out.println("---------------------");
		extractMaxTest();
		System.out.println("---------------------");
		insertTest();
		System.out.println("---------------------");
		isEmptyTest();
	}
	
	public static void main(String[] args) {
		MaxHeapTest test = new MaxHeapTest();
		test.runTests();
	}
	
	public void sortTest() {
		try {
			System.out.println("Sort the heap");
			Task[] taskArray = {task2, task3, task4, task1, task7, task6, task5};
			heap = new MaxHeap<Task>(taskArray);
			System.out.println("Array: ");
			for (int i = 0; i < heap.size(); i++) {
				 System.out.print(heap.get(i).getPriority() + ", ");
			}
			System.out.println("\n" + "After sort(): ");
			heap.sort();
			for (int i = 0; i < heap.size(); i++) {
				 System.out.print(heap.get(i).getPriority() + ", ");
			}
			System.out.println();
		} catch (Exception e) {
			System.err.println("Exception thrown during sort test");
		}

	}
	
	public void buildHeapTest() {		
		try {
			System.out.println("Build a max heap from an existing array");
			Task[] taskArray = {task2, task3, task4, task1, task7, task6, task5};
			heap = new MaxHeap<Task>(taskArray);
			System.out.println("Array: ");
			for (int i = 0; i < heap.size(); i++) {
				 System.out.print(heap.get(i).getPriority() + ", ");
			}
			System.out.println("\n" + "After buildMaxHeap():");
			heap.buildMaxHeap();
			for (int i = 0; i < heap.size(); i++) {
				 System.out.print(heap.get(i).getPriority() + ", ");
			}
			System.out.println();
		} catch (Exception e) {
			System.err.println("Exception thrown during build heap test");
		}
	}
	
	public void extractMaxTest() {
		try {
			System.out.println("Extract the max number in List");
			Task[] taskArray = {task2, task3, task4, task1, task7, task6, task5};
			heap = new MaxHeap<Task>(taskArray);
			for (int i = 0; i < heap.size(); i++) {
				 System.out.print(heap.get(i).getPriority() + ", ");
			}
			System.out.println();
			heap.buildMaxHeap();
			System.out.println("Extracted number: " + heap.extractMax().getPriority());
			System.out.println("After extractMax(): ");
			for (int i = 0; i < heap.size(); i++) {
				 System.out.print(heap.get(i).getPriority() + ", ");
			}	
			System.out.println();
		} catch (Exception e) {
			System.err.println("Exception thrown during extract max test");
		}
	}
	
	public void maxTest() {
		try {
			System.out.println("Max number in List");
			Task[] taskArray = {task2, task3, task4, task1, task7, task6, task5};
			heap = new MaxHeap<Task>(taskArray);
			for (int i = 0; i < heap.size(); i++) {
				 System.out.print(heap.get(i).getPriority() + ", ");
			}
			System.out.println("\n" + "Max: " + ((Task) heap.max()).getPriority());
		} catch (Exception e) {
			System.err.println("Exception thrown during extract max test");
		}
	}
	
	public void insertTest() {
		try {
			System.out.println("Insert 1 to empty list");
			heap = new MaxHeap<Task>();
			heap.insert(task1);
			for (int i = 0; i < heap.size(); i++) {
				 System.out.print(heap.get(i).getPriority() + ", ");
			}
			System.out.println();
		} catch (Exception e) {
			System.err.println("Exception thrown during insert test");
		}
	}
	
	public void isEmptyTest() {
		try {
			System.out.println("Empty list");
			heap = new MaxHeap<Task>(); //instantiating empty list
			if (heap.isEmpty() == true) {
				System.out.println("Method works properly");
			} else {
				System.out.println("Method error");
			}
		} catch (Exception e) {
			System.err.println("Exception thrown during insert test");
		}
	}
}

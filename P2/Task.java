
public class Task implements TaskInterface,  UpdatableInterface<Task> {

	private int priority, waitingTime, hourCreated;
	private TaskInterface.TaskType taskType;
	private String description;

	public Task(int hourCreated, TaskInterface.TaskType taskType, String description) {
		this.waitingTime = 0;
		this.hourCreated = hourCreated;
		this.taskType = taskType;
		this.priority = 0;
		this.description = description;
	}
	

	@Override
	public int getPriority() {
		return priority;
	}


	@Override
	public void setPriority(int priority) {
		this.priority = priority;		
	}
	
	public void incrementPriority() {
		this.priority++;
	}

	@Override
	public TaskInterface.TaskType getTaskType() {
		return this.taskType;
	}
	
	public String getTaskDescription() {
		return description;
	}

	@Override
	public void incrementWaitingTime() {
		waitingTime++;
		
	}
	
	public int getHourCreated() {
		return hourCreated;
	}


	@Override
	public void resetWaitingTime() {
		waitingTime = 0;
		
	}


	@Override
	public int getWaitingTime() {
		return waitingTime;
	}

	@Override
	public int compareTo(Task t) {			
		if (priority > t.getPriority()) {
			return 1;
		} 
		if (t.getPriority() > priority) {
			return -1;
		} 
		if (priority == t.getPriority()) {
			if (hourCreated <  t.getHourCreated()) {
				return 1;
			} else {
				return -1;
			}
		} else {
			return 0;
		}		
	}
	
	public String toString() {
		return getTaskType() + " " + description + " at Hour: " + hourCreated + ":00";
	}


	@Override
	public void update(int timeToIncrementPriority, int maxPriority, MaxHeap<Task> heap, int index) {
		this.incrementWaitingTime();
		if (this.getWaitingTime() >= timeToIncrementPriority) {
			this.resetWaitingTime();
			if (this.getPriority() < maxPriority) {
				this.incrementPriority();
				heap.heapifyUp(index);
			}
		}
	}
}

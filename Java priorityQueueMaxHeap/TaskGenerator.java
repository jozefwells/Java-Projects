import java.util.Random;

public class TaskGenerator implements TaskGeneratorInterface {

	private int currentEnergyStorage = 200;
	private double probability;
	private Random rand;

	public TaskGenerator(double probability) {
		this.probability = probability;
		rand = new Random();
	}

	public TaskGenerator(double probability, long seed) {
		this.probability = probability;
		rand = new Random(seed);
	}

	@Override
	public Task getNewTask(int hourCreated, TaskInterface.TaskType taskType, String taskDescription) {
		Task newTask = new Task(hourCreated, taskType, taskDescription);
		return newTask;
	}

	@Override
	public void decrementEnergyStorage(TaskInterface.TaskType taskType) {
		currentEnergyStorage -= taskType.getEnergyPerHour();

	}

	@Override
	public void resetCurrentEnergyStorage() {
		currentEnergyStorage = 200;
	}

	@Override
	public int getCurrentEnergyStorage() {
		return this.currentEnergyStorage;
	}

	@Override
	public void setCurrentEnergyStorage(int newEnergyNum) {
		currentEnergyStorage = newEnergyNum;

	}

	@Override
	public boolean generateTask() {
		if (probability >= rand.nextDouble()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getUnlucky(Task task, double unluckyProbability) {

		if (unluckyProbability <= task.getTaskType().getPassingOutProbability()) {
			if (unluckyProbability <= task.getTaskType().getDyingProbabilityProbability()) {
				currentEnergyStorage = (int) (currentEnergyStorage * .25);
				task.setPriority(0);
				return 2; // died
			} else {
				currentEnergyStorage = currentEnergyStorage / 2;
				return 1; // passed out
			}
		} else {
			return 0; // no death/passout
		}
	}

	/**
	* Create a String containing the Task's information.
	*
	* @param task - the Task
	* @param taskType - the Task's type
	*/
	@Override
	public String toString(Task task, Task.TaskType taskType) {
	if(taskType == Task.TaskType.MINING) {
	return "     Mining " + task.getTaskDescription() + " at " +
	currentEnergyStorage + " energy points (Priority:" + task.getPriority() +")";
	}
	if(taskType == Task.TaskType.FISHING) {
	return "     Fishing " + task.getTaskDescription() + " at " +
	currentEnergyStorage + " energy points (Priority:" + task.getPriority() +")" ;
	}
	if(taskType == Task.TaskType.FARM_MAINTENANCE) {
	return "     Farm Maintenance " + task.getTaskDescription() + " at " + currentEnergyStorage + " energy points (Priority:" + task.getPriority()
	+")";
	}
	if(taskType == Task.TaskType.FORAGING) {
	return "     Foraging " + task.getTaskDescription() + " at " +
	currentEnergyStorage + " energy points (Priority:" + task.getPriority() +")" ;
	}
	if(taskType == Task.TaskType.FEEDING) {
	return "     Feeding " + task.getTaskDescription() + " at " +
	currentEnergyStorage + " energy points (Priority:" + task.getPriority() +")";
	}
	if(taskType == Task.TaskType.SOCIALIZING) {
	return "     Socializing " + task.getTaskDescription() + " at " +
	currentEnergyStorage + " energy points (Priority:" + task.getPriority() +")";
	}
	else { return "nothing to see here..."; }
	}


}

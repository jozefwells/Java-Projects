
public class Player implements UpdatableInterface<Player>{

	
	private int priority, waitingTime, money;
	private int playerNumber;

	public Player(int playerNumber) {
		this.priority = 0;
		this.money = 0;
		this.playerNumber = playerNumber;
	}
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;		
	}
	
	public void resetPriority() {
		this.priority = 0;
	}
	
	public void incrementPriority() {
		this.priority++;
	}
	
	public void incrementWaitingTime() {
		waitingTime++;
	}
	
	public void resetWaitingTime() {
		waitingTime = 0;
	}
	
	public int getWaitingTime() {
		return this.waitingTime;
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}

	public int getMoney() {
		return money;
	}
	
	public void depositMoney(int deposit) {
		money += deposit;
	}
	
	@Override
	public int compareTo(Player p) {		
		if (priority > p.getPriority()) {
			return 1;
		} else if (p.getPriority() > priority) {
			return -1;
		} else {
			if (money < p.getMoney()) {
				return 1;
			} else if (money > p.getMoney()){
				return -1;
			} else {
				if (waitingTime > p.getWaitingTime()) {
					return 1;
				} else if (waitingTime < p.getWaitingTime()){
					return -1;
				} else {
					return 0;
				}
			}
		} 
	}

	@Override
	public void update(int timeToIncrementPriority, int maxPriority, MaxHeap<Player> heap, int index) {
		this.incrementWaitingTime();
		if (this.getPriority() < maxPriority) {
			this.incrementPriority();
			heap.heapifyUp(index);
		}
	}

	
	
	
	
	
}

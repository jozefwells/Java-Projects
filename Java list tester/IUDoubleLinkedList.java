import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Double-linked node implementation of IndexedUnsortedList. 
 * @author jozef wells
 *
 * @param <T>
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T>
{
	private Node<T> head;
	private Node<T> tail;
	private int size;
	private int modCount;

	public IUDoubleLinkedList()
	{
		head = tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element)
	{
		Node<T> newNode = new Node<T>(element);
		newNode.setNext(head);
		if (isEmpty())
		{
			tail = newNode;
		}
		else
		{
			head.setPrev(newNode);
		}
		head = newNode;
		size++;
		modCount++;
	}

	@Override
	public void addToRear(T element)
	{
		Node<T> newNode = new Node<T>(element);
		newNode.setPrev(tail);
		if (isEmpty())
		{
			head = newNode;
		}
		else
		{
			tail.setNext(newNode);
		}
		tail = newNode;
		size++;
		modCount++;
	}

	@Override
	public void add(T element)
	{
		addToRear(element);		
	}

	@Override
	public void addAfter(T element, T target)
	{
		Node<T> targetNode = head;
		while (targetNode != null && !targetNode.getElement().equals(target))
		{
			targetNode = targetNode.getNext();
		}
		if (targetNode == null)
		{
			throw new NoSuchElementException();
		}
		
		Node<T> newNode = new Node<T>(element);
		
		newNode.setNext(targetNode.getNext());
		newNode.setPrev(targetNode);
		targetNode.setNext(newNode);

		if (targetNode == tail)
		{
			tail = newNode;
		}
		else
		{
			newNode.getNext().setPrev(newNode);
		}
		
		size++;
		modCount++;
	}

	@Override
	public void add(int index, T element)
	{
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> newNode = new Node<T>(element);
		Node<T> current = head;
		for(int i = 0; i < index; i++) {
			current = current.getNext();
		}
		if(head == null) {	//list is empty
			head = tail = newNode;
		}
		else if(current != null && index != 0){	//adding in middle of list
			newNode.setNext(current);
			newNode.setPrev(current.getPrev());
			newNode.getPrev().setNext(newNode);
			newNode.getNext().setPrev(newNode);
		}
		else if(current != null && index == 0) {	//adding new head of non-empty list
			newNode.setNext(head);
			head.setPrev(newNode);
			head = newNode;
		}
		else {	//adding new tail of non-empty list
			newNode.setPrev(tail);
			tail.setNext(newNode);
			tail = newNode;
		}

		size++;
		modCount++;	
	}

	@Override
	public T removeFirst()
	{
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = head.getElement();
		if(size == 1) {
			head = tail = null;
		}
		else { 
			head = head.getNext();
			head.setPrev(null);
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T removeLast()
	{
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = tail.getElement();
		Node<T> newTail = tail.getPrev();
		tail = newTail;
		if(size == 1) {
			head = null;
		}
		else {
			newTail.setNext(null);
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T remove(T element)
	{
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		Node<T> current = head;
		while(current != null && !current.getElement().equals(element)) {
			current = current.getNext();
		}
		if(current == null) {
			throw new NoSuchElementException();
		}
		T retVal = current.getElement();
		if (size() == 1) { //only node
			head = tail = null;
		}
		else if (current == head) { //first node
			head = current.getNext();
			head.setPrev(null);
		}
		else if (current == tail) { //last node
			tail = current.getPrev();
			tail.setNext(null);
		}
		else { //somewhere in the middle
			current.getPrev().setNext(current.getNext());
			current.getNext().setPrev(current.getPrev());
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T remove(int index)
	{
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> current = head;
		for(int i = 0; i < index; i++) {
			current = current.getNext();
		}
		T retVal = current.getElement();
		if (size() == 1) { //only node
			head = tail = null;
		}
		else if (current == head) { //first node
			head = current.getNext();
			head.setPrev(null);
		}
		else if (current == tail) { //last node
			tail = current.getPrev();
			tail.setNext(null);
		}
		else { //somewhere in the middle
			current.getPrev().setNext(current.getNext());
			current.getNext().setPrev(current.getPrev());
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public void set(int index, T element)
	{
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> current = head;
		for(int i = 0; i < index; i++) {
			current = current.getNext();
		}
		current.setElement(element);
		modCount++;		
	}

	@Override
	public T get(int index)
	{
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> current = head;
		for(int i = 0; i < index; i++) {
			current = current.getNext();
		}
		return current.getElement();
	}

	@Override
	public int indexOf(T element)
	{
		Node<T> current = head;
		int currentIndex = 0;
		while (current != null && !element.equals(current.getElement()))
		{
			current = current.getNext();
			currentIndex++;
		}
		if (current == null)
		{
			currentIndex = -1;
		}
		return currentIndex;
	}

	@Override
	public T first()
	{
		if (isEmpty()) 
		{
			throw new NoSuchElementException();
		}
		return head.getElement();
	}

	@Override
	public T last()
	{
		if (isEmpty())
		{
			throw new NoSuchElementException();
		}
		return tail.getElement();
	}

	@Override
	public boolean contains(T target)
	{
		return indexOf(target) > -1;
	}

	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

	@Override
	public int size()
	{
		return size;
	}
	
	/**
	 * Returns a string representation of this list
	 * 
	 * @return a string representation of list
	 */
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append("[");
		Iterator<T> it = iterator();
		while (it.hasNext()) {
			str.append(it.next());
			str.append(", ");
		}
		if (size() > 0) {
			str.delete(str.length() - 2, str.length());
		}
		str.append("]");
		return str.toString();
	}

	@Override
	public Iterator<T> iterator()
	{
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator()
	{
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex)
	{
		return new DLLIterator(startingIndex);
	}
	
	private class DLLIterator implements ListIterator<T> 
	{
		private Node<T> nextNode;
		private int nextIndex;
		private int iterModCount;
		private Node<T> lastReturned;
		
		public DLLIterator()
		{
			this(0);
		}
		
		public DLLIterator (int startingIndex)	
		{
			if (startingIndex < 0 || startingIndex > size)
			{
				throw new IndexOutOfBoundsException();
			}
			iterModCount = modCount;
			nextNode = head;
			for (int i = 0; i < startingIndex; i++)
			{
				nextNode = nextNode.getNext();
			}
			nextIndex = startingIndex;
			lastReturned = null;
		}

		@Override
		public boolean hasNext()
		{
			if (iterModCount != modCount)
			{
				throw new ConcurrentModificationException();		
			}
			return (nextNode != null);
		}

		@Override
		public T next()
		{
			if (!hasNext())
			{
				throw new NoSuchElementException();
			}
			T retVal = nextNode.getElement();
			lastReturned = nextNode;
			nextNode = nextNode.getNext();
			nextIndex++;
			
			return retVal;
		}

		@Override
		public boolean hasPrevious()
		{
			if (iterModCount != modCount)
			{
				throw new ConcurrentModificationException();
			}
			return (nextNode != head);
		}

		@Override
		public T previous()
		{
			if (!hasPrevious())
			{
				throw new NoSuchElementException();
			}
			if (nextNode == null)
			{
				nextNode = tail;
			}
			else
			{
				nextNode = nextNode.getPrev();
			}
			lastReturned = nextNode;
			nextIndex--;
			return nextNode.getElement();
		}

		@Override
		public int nextIndex()
		{
			if (iterModCount != modCount)
			{
				throw new ConcurrentModificationException();
			}
			return nextIndex;
		}

		@Override
		public int previousIndex()
		{
			if (iterModCount != modCount)
			{
				throw new ConcurrentModificationException();
			}
			return (nextIndex - 1);
		}

		@Override
		public void remove()
		{
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if(lastReturned == null) { //cant remove
				throw new IllegalStateException();
			}
			if(head == tail) { //Only node
				head = tail = null;
			}
			else if(lastReturned == tail) {	//remove tail
				tail = tail.getPrev();
				tail.setNext(null);
			}
			else if(lastReturned == head) {	//remove head
				head = head.getNext();
				head.setPrev(null);
			}
			else {	//remove middle
				if(lastReturned == nextNode) {	//previous was called
					nextNode.getPrev().setNext(nextNode.getNext());
					nextNode = nextNode.getNext();
					nextNode.setPrev(nextNode.getPrev().getPrev());
				}
				else {	//next was called
					nextNode.setPrev(nextNode.getPrev().getPrev());
					nextNode.getPrev().setNext(nextNode);
				}
				nextIndex--;
			}
			lastReturned = null;
			size--;
			iterModCount++;
			modCount++;
			
		}

		@Override
		public void set(T e)
		{
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if(lastReturned == null) {
				throw new IllegalStateException();
			}
			lastReturned.setElement(e);
			lastReturned = null;
			iterModCount++;
			modCount++;			
		}

		@Override
		public void add(T e)
		{
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			Node<T> newNode = new Node<T>(e);
			if(head == null) {	//only node
				head = tail = newNode;
			}
			else if(nextNode == null) { //new tail
				newNode.setPrev(tail);
				tail.setNext(newNode);
				tail = newNode;
			}
			else if(nextNode == head) { //new head
				newNode.setNext(head);
				head.setPrev(newNode);
				head = newNode;
			}
			else {	//new middle element
				newNode.setNext(nextNode);
				newNode.setPrev(nextNode.getPrev());
				nextNode.setPrev(newNode);
				newNode.getPrev().setNext(newNode);
			}
			nextIndex++;
			size++;
			modCount++;
			iterModCount++;			
		}
		
	}

}

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Single-linked node implementation of IndexedUnsortedList. An Iterator with
 * working remove() method is implemented, but ListIterator is unsupported.
 * 
 * @author
 * 
 * @param <T> type to store
 */
public class IUSingleLinkedList<T> implements IndexedUnsortedList<T>
{
	private Node<T> head, tail;
	private int size;
	private int modCount;

	/** Creates an empty list */
	public IUSingleLinkedList()
	{
		head = tail = null;
		size = 0;
		modCount = 0;
	}

	/**
	 * Adds the specified element to the front of the list
	 * 
	 * @param element
	 */
	public void addToFront(T element)
	{
		Node<T> addNode = new Node<T>(element);
		addNode.setNext(head);
		head = addNode;
		if (tail == null)
		{
			tail = addNode;
		}
		size++;
		modCount++;
	}

	/**
	 * Adds the specified element to the back/rear of the list
	 * 
	 * @param element
	 */
	public void addToRear(T element)
	{
		Node<T> addNode = new Node<T>(element);
		if (isEmpty())
		{
			head = addNode;
		} else
		{
			tail.setNext(addNode);
		}
		tail = addNode;
		size++;
		modCount++;
	}

	/**
	 * Adds the specified element to the back/rear of the list
	 * 
	 * @param element
	 */
	public void add(T element)
	{
		addToRear(element);
	}

	/**
	 * Adds the specified element to the location of 1 index space after the
	 * specified target
	 * 
	 * @param element
	 * @param target
	 * @throws NoSuchElementException
	 */
	public void addAfter(T element, T target)
	{
		if (isEmpty())
		{
			throw new NoSuchElementException();
		}

		Node<T> current = head;
		boolean found = false;
		while (!found && current != null)
		{
			if (current.getElement().equals(target))
			{
				found = true;
			} else
			{
				current = current.getNext();
			}
		}

		if (!found)
		{
			throw new NoSuchElementException();
		}

		Node<T> addNode = new Node<T>(element);
		addNode.setNext(current.getNext());
		current.setNext(addNode);

		if (current == tail)
		{
			tail = addNode;
		}
		size++;
		modCount++;
	}

	/**
	 * Adds the specified element to the specified index location of the list
	 * 
	 * @param index
	 * @param element
	 * @throws IndexOutOfBoundsException
	 */
	public void add(int index, T element)
	{
		if (index < 0 || index > size)
		{
			throw new IndexOutOfBoundsException();
		}
		Node<T> addNode = new Node<T>(element);
		Node<T> current = head;

		if (index == 0)
		{
			addToFront(element);
		} else if (index == size)
		{
			tail.setNext(addNode);
			tail = addNode;

			size++;
			modCount++;
		} else
		{
			for (int i = 0; i < index - 1; i++)
			{
				current = current.getNext();
			}
			addNode.setNext(current.getNext());
			current.setNext(addNode);

			size++;
			modCount++;
		}
	}

	/**
	 * Removes the first element in the list
	 * 
	 * @throws NoSuchElementException
	 * @returns the element removed
	 */
	public T removeFirst()
	{
		if (isEmpty())
		{
			throw new NoSuchElementException();
		}
		T retVal = head.getElement();
		head = head.getNext();
		if (head == null) // just removed the ONLY node
		{
			tail = null;
		}
		size--;
		modCount++;
		return retVal;
	}

	/**
	 * Removes the last element in the list
	 * 
	 * @throws NoSuchElementException
	 * @returns the element removed
	 */
	public T removeLast()
	{
		if (isEmpty())
		{
			throw new NoSuchElementException();
		}
		T retVal = tail.getElement();

		if (head == tail)
		{
			head = tail = null;
		} 
		else
		{
			Node<T> newTail = head;
			while (newTail.getNext() != tail)
			{
				newTail = newTail.getNext();
			}
			newTail.setNext(null);
			tail = newTail;
		}

		size--;
		modCount++;
		return retVal;
	}

	/**
	 * Removes the specified element from the list
	 * 
	 * @param element
	 * @throws NoSuchElementException
	 * @returns the element being removed
	 */
	public T remove(T element)
	{
		if (isEmpty())
		{
			throw new NoSuchElementException();
		}

		boolean found = false;
		Node<T> previous = null;
		Node<T> current = head;

		while (current != null && !found)
		{
			if (element.equals(current.getElement()))
			{
				found = true;
			} 
			else
			{
				previous = current;
				current = current.getNext();
			}
		}

		if (!found)
		{
			throw new NoSuchElementException();
		}

		if (size() == 1)
		{ // only node
			head = tail = null;
		} 
		else if (current == head)
		{ // first node
			head = current.getNext();
		} 
		else if (current == tail)
		{ // last node
			tail = previous;
			tail.setNext(null);
		} 
		else
		{ // somewhere in the middle
			previous.setNext(current.getNext());
		}

		size--;
		modCount++;
		return current.getElement();
	}

	/**
	 * Removes the element from the specified index location from the list
	 * 
	 * @param index
	 * @throws IndexOutOfBoundsException
	 */
	public T remove(int index)
	{
		if (index < 0 || index > size - 1)
		{
			throw new IndexOutOfBoundsException();
		}
		T retVal = null;
		Node<T> current = head;

		for (int i = 0; i < index - 1; i++)
		{
			current = current.getNext();
		}

		if (index == 0) // target is head
		{
			retVal = head.getElement();
			head = head.getNext();
			if (head == null)
			{
				tail = null;
			}
		} 
		else if (current.getNext() == tail)
		{
			retVal = tail.getElement();
			current.setNext(null);
			tail = current;
		} 
		else
		{
			retVal = current.getNext().getElement();
			current.setNext(current.getNext().getNext());
		}

		size--;
		modCount++;
		return retVal;
	}

	/**
	 * Replaces the element at the given index location with the given element
	 * 
	 * @param index
	 * @param element
	 * @throws IndexOutOfBoundsException
	 */
	public void set(int index, T element)
	{
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException();
		Node<T> current = head;
		for (int i = 0; i < index; i++)
		{
			current = current.getNext();
		}
		current.setElement(element);
		modCount++;
	}

	/**
	 * Returns a reference to the element at the specified index location
	 * 
	 * @param index - the index that the reference is being retrieved from
	 * @throws IndexOutOfBoundsException
	 * @return the element at the specified index
	 */
	public T get(int index)
	{
		if (index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException();
		}
		Node<T> current = head;
		for (int i = 0; i < index; i++)
		{
			current = current.getNext();
		}
		return current.getElement();
	}

	/**
	 * Returns the index of the specified element
	 * 
	 * @param element
	 * @return the index value of the specified element
	 */
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

	/**
	 * Returns the first element in the list
	 * 
	 * @throws NoSuchElementException
	 * @return a reference to the first element in the list 
	 */
	public T first()
	{
		if (isEmpty())
		{
			throw new NoSuchElementException();
		}
		return head.getElement();
	}

	/**
	 * Returns the last element in the list
	 * 
	 * @throws NoSuchElementException
	 * @return a reference to the last element in the list
	 */
	public T last()
	{
		if (isEmpty())
		{
			throw new NoSuchElementException();
		}
		return tail.getElement();
	}

	/**
	 * Returns true if the given target element is in the list otherwise false
	 * 
	 * @return true or false if the specified target is in list
	 */
	public boolean contains(T target)
	{
		return indexOf(target) > -1;
	}

	/**
	 * Checks the size of the list and if list is empty returns true, otherwise false
	 * 
	 * @return true or false if the list is empty 
	 */
	public boolean isEmpty()
	{
		return size == 0;
	}

	/**
	 * Returns the size of the list
	 * 
	 * @return the number of indexes in the list
	 */
	public int size()
	{
		return size;
	}

	/**
	 * Returns a well-formatted reference of the list
	 * 
	 * @return a reference of the list
	 */
	public String toString()
	{
		StringBuilder str = new StringBuilder();

		str.append("[");

		for (T element : this)
		{
			str.append(element.toString());
			str.append(", ");
		}

		if (!isEmpty())
		{
			str.delete(str.length() - 1, str.length());
		}

		str.append("]");

		return str.toString();
	}

	@Override
	public Iterator<T> iterator()
	{
		return new SLLIterator();
	}

	@Override
	public ListIterator<T> listIterator()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex)
	{
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUSingleLinkedList */
	private class SLLIterator implements Iterator<T>
	{
		private Node<T> nextNode;
		private int iterModCount;
		private boolean canRemove;

		/** Creates a new iterator for the list */
		public SLLIterator()
		{
			nextNode = head;
			iterModCount = modCount;
			canRemove = false;
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
			nextNode = nextNode.getNext();
			canRemove = true;
			return retVal;
		}

		@Override
		public void remove()
		{
			if (iterModCount != modCount)
			{
				throw new ConcurrentModificationException();
			}
			if (!canRemove)
			{
				throw new IllegalStateException();
			}
			canRemove = false;

			if (size == 1) // only one node
			{
				head = tail = null;
			} 
			else if (head.getNext() == nextNode) // removing head
			{
				head = nextNode;
				if (head == null)
				{
					tail = null;
				}
			} 
			else // general case - removing element somewhere inside a long list
			{

				Node<T> targetNode = head;
				while (targetNode.getNext().getNext() != nextNode)
				{
					targetNode = targetNode.getNext();
				}
				targetNode.setNext(nextNode); // removed old tail
				if (nextNode == null)
				{
					tail = targetNode;
				}
			}

			size--;
			modCount++;
			iterModCount++;
		}
	}
}

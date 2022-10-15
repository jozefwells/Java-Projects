import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Array-based implementation of IndexedUnsortedList. An Iterator with working
 * remove() method is implemented, but ListIterator is unsupported.
 * 
 * @Jozef Wells
 *
 * @param <T> type to store
 */
public class IUArrayList<T> implements IndexedUnsortedList<T>
{
	private static final int DEFAULT_CAPACITY = 10;
	private static final int NOT_FOUND = -1;

	private T[] array;
	private int rear;
	private int modCount;

	/** Creates an empty list with default initial capacity */
	public IUArrayList()
	{
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Creates an empty list with the given initial capacity
	 * 
	 * @param initialCapacity
	 */
	@SuppressWarnings("unchecked")
	public IUArrayList(int initialCapacity)
	{
		array = (T[]) (new Object[initialCapacity]);
		rear = 0;
		modCount = 0;
	}

	/** Double the capacity of array */
	private void expandCapacity()
	{
		if (rear == array.length)
		{
			array = Arrays.copyOf(array, array.length * 2);
		}
	}
	
	@Override
	public void addToFront(T element)
	{
		expandCapacity();
		add(0, element);
	}

	@Override
	public void addToRear(T element)
	{
		add(rear, element);
	}

	@Override
	public void add(T element)
	{
		if (array.length != 0)
		{
			expandCapacity();
			array[rear] = element;
			rear++;
			modCount++;
		}

	}

	@Override
	public void addAfter(T element, T target)
	{
		int targetIndex = indexOf(target);
		
		if (targetIndex == NOT_FOUND)
		{
			throw new NoSuchElementException();
		}
		else
		{
			expandCapacity();
			add(targetIndex + 1, element);
		}
		
	}

	@Override
	public void add(int index, T element)
	{
		if (index < 0 || index > rear)
		{
			throw new IndexOutOfBoundsException();
		}
		else
		{
			expandCapacity();
			rear++;
			for (int i = rear-1; i > index; i--)
			{
				if (rear > 1)
				{
					array[i] = array[i-1];
				}
			}
			
			array[index] = element; 
			modCount++;
		}
				
	}

	@Override
	public T removeFirst()
	{
		if (rear == 0)
		{
			throw new NoSuchElementException();
		}
		else 
		{
			return remove(0);
		}
		
	}

	@Override
	public T removeLast()
	{
		if (rear == 0)
		{
			throw new NoSuchElementException();
		}
		else 
		{
			T last = array[rear-1];
			rear--;
			return last;
		}
		
	}

	@Override
	public T remove(T element)
	{
		int index = indexOf(element);
		
		if (index == -1)
		{
			throw new NoSuchElementException();
		}
		else
		{
			return remove(index);
			
		}
	}

	
	public T remove(int index)
	{
		if (index >= rear || index < 0)
		{
			throw new IndexOutOfBoundsException();
		}
		else
		{
			T removeObject = array[index];

			array[index] = null;
			// shift elements
			for (int i = index; i < rear-1; i++)
			{
				array[i] = array[i + 1];
			}	
			
			rear--;
			array[rear] = null;
			modCount++;	
			return removeObject;
		}		
	}

	@Override
	public void set(int index, T element)
	{
		if (index < 0 || index >= rear)
		{
			throw new IndexOutOfBoundsException();
		}
		else
		{
			array[index] = element;
			modCount++;
		}
	}

	@Override
	public T get(int index)
	{
		if (index < 0 || index >= rear)
		{
			throw new IndexOutOfBoundsException();
		}
		else
		{
			return array[index];
		}
	}

	@Override
	public int indexOf(T element)
	{
		int index = 0;

		boolean complete = false;
		
		while (!complete &&  index < rear)
		{
			if (array[index] == element)
			{
				complete = true;
			}
			else 
			{
				index++;
			}
		}
		
		if(!complete)
		{
			index = -1;
		}
		
		return index;
	}

	@Override
	public T first()
	{
		if (rear == 0)
		{
			throw new NoSuchElementException();
		}
		else 
		{
			T output = array[0];
			return output;
		}
	}

	@Override
	public T last()
	{
		if (isEmpty())
		{
			throw new NoSuchElementException();
		}
		else 
		{
			T lastItem = array[rear - 1];
			return lastItem;
		}
	}

	@Override
	public boolean contains(T target)
	{
		
		for (int i = 0; i < rear; i++)
		{
			if (array[i] == target)
			{
				return true;
			}
		}	
		return false;
	}

	@Override
	public boolean isEmpty()
	{
		if (rear == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public int size()
	{
		return rear;
	}

	@Override
	public Iterator<T> iterator()
	{
		return new ALIterator();
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

	/** Iterator for IUArrayList */
	private class ALIterator implements Iterator<T>
	{
		private int nextIndex;
		private int iterModCount;
		private boolean canRemove;

		public ALIterator()
		{
			nextIndex = 0;
			iterModCount = modCount;
			canRemove = false;
		}

		@Override
		public boolean hasNext()
		{
			return (nextIndex < rear);
		}

		@Override
		public T next()
		{
			if (!hasNext())
			{
				throw new NoSuchElementException();
			}
			nextIndex++;
			canRemove = true;
			return array[nextIndex-1];
		}

		@Override
		public void remove()
		{
			if (!canRemove)
			{
				throw new IllegalStateException();
			}
			canRemove = false;
			for (int index = nextIndex-1; index < rear-1; index++)
			{
				array[index] = array[index+1];
			}
		iterModCount++;
		}
	}
	
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
			str.delete(str.length()-1, str.length());
		}
		
		str.append("]");
		
		return str.toString();
	}
}

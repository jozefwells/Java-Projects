import java.util.LinkedList;
/**
 * 
 * @author Jozef Wells
 * CS 321 Spring 2022
 * @param <T>
 */
public class cache<T> 
{
	LinkedList<T> newList;
	private int limit;
	
	/**
	 * sets the limit for the number of elements that can
	 * be stored in the cache
	 * 
	 * @param limit
	 */
	public cache(int limit)
	{
		newList = new LinkedList<T>();
		this.limit = limit;
	}
	
	/**
	 * 
	 * @param obj
	 * @return true if obj is already in the cache, otherwise false
	 */
	public boolean hit(T obj)
	{	
		if (newList.contains(obj))
		{
			return true;
		}
		return false;	
	}
	
	/**
	 * clears the contents of the cache
	 */
	public void clearCache()
	{
		newList.clear();

	}
	
	/**
	 * 
	 * @return the size of the cache
	 */
	public int size()
	{
		return newList.size();
	}
	
	/**
	 * adds an item to the front of the cache, 
	 * while removing item if already present
	 * 
	 * @param obj
	 */
	public void add(T obj)
	{
		if (newList.contains(obj))
		{
			newList.remove(obj);
		}
		if (size() >= limit)
		{
			newList.removeLast();
		}
		newList.addFirst(obj);
	}
	
	/**
	 * removes the last element of the cache
	 */
	public void removeLast()
	{
		newList.removeLast();
	}
	
	/**
	 * @return a string representation of the contents listed in the cache
	 */
	public String toString()
	{
		return newList.toString();
	}
		
}

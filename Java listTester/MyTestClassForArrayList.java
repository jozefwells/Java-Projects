
public class MyTestClassForArrayList
{
	
	public static void main(String[] args)
	{
		 IUArrayList<String> list = new IUArrayList<String>(10);
		 list.add("A");
		 list.add(0, "B");
		 System.out.println(list.toString());	//	 System.out.println(list.removeLast());
		 System.out.println("rear = " + list.size());
		 System.out.println("last = " + list.last());

	}
}

import java.util.NoSuchElementException;


public class BattingOrder implements Cloneable {

	private Batter[] data; //Holds all the players in the queue
	private int howMany; //Count for the number of players in the queue
	//reference to the front and rear of the queue
	private int front;
	private int rear;
	
	//CONSTRUCTOR
	public BattingOrder()
	{
		final int I_C= 10; //initial capacity
		howMany = 0;
		data = new Batter[I_C];
	}
	//PRECONDITION : capacity is greater than 0
	//POSTCONDITION : The array has been initialized with the defined size
	public BattingOrder(int capacity)
	{
		if(capacity < 0)
			throw new IllegalArgumentException("CAPACITY NO GOOD ...");
		howMany = 0;
		data = new Batter[capacity];
	}
	
	//Add a player into the queue
	public void add(Batter item)
	{
		//Array is full
		if(howMany == data.length)
			ensureCapacity(howMany*2 + 1); //double the capacity
		//FIND THE NEXT REAR
		if(howMany == 0)
		{
			rear = 0;
			front = 0;
		}
		else
			rear = nextIndex(rear); //Since items are inserted into the rear, calculates where is rear
		//ISERT THE ITEM
		data[rear] = item;
		howMany++;
	}
	public BattingOrder clone() //copy reference for the batters
	{
		BattingOrder out = null;
		try
		{
			out = (BattingOrder) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			System.out.println("No Good ...");
		}
		out.data = data.clone();
		return out;
	}
	//size of the array holding the players
	public int getCapacity()
	{
		return data.length;
	}
	public boolean isEmpty()
	{
		return (howMany == 0);
	}
	//For circular queue, finds where to insert an item
	private int nextIndex(int i)
	{
		if(++i == data.length) //assuming that array is not full, if you are at the end, go to the beginning
			return 0;
		else
			return i;
	}
	
	//POSTCONDITION : the size of the array is increased
	public void ensureCapacity(int minCap)
	{
		Batter[] out;
		int n1, n2;
		
		if(data.length >= minCap)
			return;
		
		else if (howMany == 0)
			data = new Batter[minCap];
		else if (front <= rear)
			
		{
			out = new Batter[minCap];
			System.arraycopy(data, front, out, front, howMany);
			data = out;
		}
		else
		{
			out = new Batter[minCap];
			n1 = data.length - front;
			n2 = rear + 1;
			System.arraycopy(data, front, out, 0, n1);
			System.arraycopy(data, 0, out, n1, n2);
			front = 0;
			rear = howMany -1;
			data = out;
		}
	}
	//Remove an item from the front of the queue
	public Batter remove()
	{
		Batter out;
		
		if(howMany == 0)
			throw new NoSuchElementException("Queue underflow ....");
		out = data[front];
		front = nextIndex(front);
		
		howMany--;
		return out;
	}
	public int size()
	{
		return howMany;
	}
	
	//Prints the data in the queue
	public void printQ()
	{
		for(int i = front; i <= rear; i++)
			System.out.print(data[i]+", ");
		System.out.println();
	}
}

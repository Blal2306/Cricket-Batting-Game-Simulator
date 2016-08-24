//-------------
//BooleanSource
//-------------

public class BooleanSource
{
	private double probability;
	
	//Constructor
	public BooleanSource(double p)
	{
		if((p < 0) || (1 < p))
			throw new IllegalArgumentException("NO GOOD !");
		probability = p;
	}
	//Returns true or false based on the current probability
	public boolean query()
	{
		return (Math.random() < probability);
	}
}

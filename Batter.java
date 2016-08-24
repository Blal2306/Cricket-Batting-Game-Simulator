
public class Batter implements Cloneable {

	String first_name;
	String last_name;
	int at_bat;
	int hits;
	boolean out; //To store if the batter was out
	boolean hit = false; //If the batter made a hit
	int out_Number;
	double average; // between 0 - 1
	
	BooleanSource prob; //Will return true or false based on the given probability
	
	//constructor
	public Batter(String first, String last, double avg)
	{
		first_name = first;
		last_name = last;
		average = avg;
		prob = new BooleanSource(average);
	}
	//Return true of false based on the probability
	public boolean hit()
	{
		boolean x = prob.query();
		return x;
	}
	
	//ACCESSOR AND MUTATORS METHODS
	public void setFirst(String first)
	{
		first_name = first;
	}
	public void setLast(String last)
	{
		last_name = last;
	}
	public void printData()
	{
		System.out.println(first_name+"\t"+last_name+"\t"+average);
	}
	public boolean isOut()
	{
		return out;
	}
	public void setisOut(boolean x)
	{
		out = x;
	}
	public void setOutNumber(int x)
	{
		out_Number = x;
	}
	public int getOutNumber()
	{
		return out_Number;
	}
	public Batter clone() //To create a copy of the batter
	{
		Batter out = null;
		try {
			out = (Batter) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return out;
	}
	
	//Get and set method for the hit, if the player makes a hit or not
	public boolean getHit()
	{
		return hit;
	}
	public void setHit(boolean x)
	{
		hit = x;
	}


}

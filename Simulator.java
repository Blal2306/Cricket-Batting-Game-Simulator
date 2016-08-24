import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.swing.JFileChooser;


public class Simulator {

	public static void main(String args[]) throws Exception
	{
		
		//QUEUE FOR THE VISITOR AND HOME TEAMS 
		BattingOrder visitor = new BattingOrder();
		BattingOrder home = new BattingOrder();
		
		//Holds the players for playing team, so that original data doesn't get destroyed
		BattingOrder hold;
		
		String temp_first = null;
		String temp_last = null;
		double temp_avg = 0;
		Batter temp_batter; //To store the data when each batter is pulled out of the queue
		
		
		//To Keep track of the total score and outs
		int Visitor_Score = 0;
		int Visitor_Out = 0;
		
		int Home_Score = 0;
		int Home_Out;

		//-----------------------------
		//ADD PLAYERS FOR THE HOME TEAM
		//-----------------------------
		
		System.out.println("Locate the file for home team : ");
		JFileChooser fileChooser = new JFileChooser();
		if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			java.io.File file = fileChooser.getSelectedFile();
			Scanner input = new Scanner(file);
				
			//read each line of data
			while (input.hasNext())
			{
				temp_first = input.next();
				temp_last = input.next();
				temp_avg = input.nextDouble();
				
				//put the data into each batter object
				temp_batter = new Batter(temp_first, temp_last, temp_avg);
				home.add(temp_batter);
			}	
			input.close();
		}
		//--------------------------------
		//ADD PLAYERS FOR THE VISITOR TEAM
		//--------------------------------
		System.out.println("Locate the file for visitor's team : ");
		JFileChooser fileChooser_b = new JFileChooser();
		if(fileChooser_b.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			java.io.File file_b = fileChooser_b.getSelectedFile();
			Scanner input_b = new Scanner(file_b);
						
			//read data for each player and store it in a batter object
			while (input_b.hasNext())
			{
				temp_first = input_b.next();
				temp_last = input_b.next();
				temp_avg = input_b.nextDouble();	
				temp_batter = new Batter(temp_first, temp_last, temp_avg);
				visitor.add(temp_batter);
			}
			
			input_b.close();
		}
		
		
		//--------------
		//START THE GAME
		//--------------
		for(int i = 1 ; i <= 9; i++) //Playing nine innings
		{
			//start of the inning
			System.out.println("----- INNING # "+i+" -----");
			System.out.println();
		
			//The visitor plays
			System.out.println("*** VISITOR ***");
			System.out.println();
		
			Visitor_Out = 0; //initialize the variable
			
			hold = visitor.clone(); //copy only references for the visitor queue, because the score
									//for each player shouldn't should be saved
			while(Visitor_Out < 3)
			{
				temp_batter = hold.remove(); //remove the player from the queue
				if(!(temp_batter.isOut())) //Not out, the player gets another change to play
				{
					if(temp_batter.hit()) //The player has made a hit
					{
						Visitor_Score ++;
						temp_batter.hits++; //player hits increase
						temp_batter.at_bat++; //at bats increase
						temp_batter.setHit(true);//Indicate that the player made a hit
						hold.add(temp_batter); //The player is not out, add him back in the array
					}
					else
					{
						temp_batter.at_bat++; //At bat's increase
						temp_batter.setisOut(true); //the player is out
						Visitor_Out++;
						temp_batter.setOutNumber(Visitor_Out);
						hold.add(temp_batter); //ADD HIM BACK IN THE QUEUE
					}
				}
				else
				{
					hold.add(temp_batter);
				}
			}
			//Print the details for the visitor team play
			while(!hold.isEmpty())
			{
				temp_batter = hold.remove();
				System.out.println(temp_batter.first_name+"\t"+temp_batter.last_name+"\t("+temp_batter.hits+"/"+temp_batter.at_bat+")\t: "+(temp_batter.isOut() ? "Out Number " + temp_batter.getOutNumber() : (temp_batter.getHit() ? "Hit" : "")));
			}
			System.out.println();
			//********************************************
			
			//The home team plays
			System.out.println("*** HOME ***");
			System.out.println();
			
			Home_Out = 0; //reset the variable
			hold = home.clone(); //reference are only copied because the hold queue will be emptied at the end and original 
								 //objects must remain unchanged 
			while(Home_Out < 3)
			{
				temp_batter = hold.remove(); //remove a player from the queue
				if(!(temp_batter.isOut())) //The player is not out, he gets another play
				{
					if(temp_batter.hit()) //There player has made a hit
					{
						Home_Score ++;
						temp_batter.hits++; //player hits increase
						temp_batter.at_bat++; //at bats increase
						temp_batter.setHit(true);//Indicate that the player made a hit
						hold.add(temp_batter); //The player is not out, add him back in the array
					}
					else
					{
						temp_batter.at_bat++; //At bat's increase
						temp_batter.setisOut(true); //the player is out
						Home_Out++;
						temp_batter.setOutNumber(Home_Out);
						hold.add(temp_batter); //ADD HIM BACK IN THE QUEUE
					}
				}
				else //the player is already out, put him back into the array
				{
					hold.add(temp_batter);
				}
			}
			//Print score for the home team play
			while(!hold.isEmpty())
			{
				temp_batter = hold.remove();
				System.out.println(temp_batter.first_name+"\t"+temp_batter.last_name+"\t("+temp_batter.hits+"/"+temp_batter.at_bat+")\t: "+(temp_batter.isOut() ? "Out Number " + temp_batter.getOutNumber() : (temp_batter.getHit() ? "Hit" : "")));
			}
			System.out.println();
			System.out.println("VISITOR SCORE : "+Visitor_Score+"\t"+"HOME SCORE : "+Home_Score);

			//Reset the player after each inning because all the player will get another chance to play
			resetTeams(visitor);
			resetTeams(home);
			System.out.println();
			
		}
		//End of the game, print out who is winning
		if(Visitor_Score > Home_Score)
			System.out.println("VISITOR WINS !!!!");
		else
			System.out.println("HOME TEAM WINS !!!!");
	}
	//PRECONDITION : Input a variable of type BattingOrder
	//POST CONDITION : The data for each batter have been set except for the at-bats and hits
	public static void resetTeams(BattingOrder x) //reset the data so that no one is out and all the players will get another change to play
	{
		Batter temp;
		for(int i = 0; i < x.size(); i++)
		{
			temp = x.remove(); //pull out an element
			temp.setOutNumber(0);
			temp.setisOut(false);
			temp.setHit(false);
			x.add(temp); //put it back into the array after changes are made
		}
	}

}

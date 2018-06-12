/**
* Class Node
*
* @author Alicja Zoń
*/
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/* Class representing a single node */
public class Node{

/* Public construnctor setting an unique id and starter values for all parameters */
	public Node()
	{	
		counter++;
		id = counter;
		is_candidate = false;
		is_in_cs = false;
		time_stamp = 0;
		responses_count = 0;
		respondedList = new Vector<Integer>();
		requestsList = new Vector<Integer>();
	}

/* Random int number generator from given range */
	public int GetRandom(int min, int max) 
	{
		int i = ThreadLocalRandom.current().nextInt(min, max + 1);
		return i;
	}

/* Setting time stamp for the node which wants to be a candidate to CS */
	public void Request() 
	{
		time_stamp = GetRandom(1, 100);
		is_candidate = true;
	}

/* Returns time stamp */
	public int GetTimeStamp()
	{
		return time_stamp;
	}

/* Returns unique id */
	public int GetId()
	{
		return id;
	}

/* Adds id of a node which sended a Go-Ahead message */
	public void AddToRespondedList(int id)
	{
		respondedList.addElement(id);
	}

/*  Adds id of a node which request cannot be yet answered */
	public void AddToRequestsList(int id)
	{
		requestsList.addElement(id);
	}

/* Entering critical section and leaving it after 2 seconds */
	public void CriticalState()
	{
		is_in_cs = true;
		System.out.println("** Node #" + id + " recieved all the responses and enters the Critical Section **"); 
		new Timer().schedule( 
        new TimerTask() {
            @Override
            public void run() {
        is_in_cs = false;
		is_candidate = false;
		System.out.println("Node #" + id + " comes out of the Critical Section");
            }
		
        }, 
        2000 
);
	}

/* Returns true if a node is a candidate to CS */
	public boolean IsCandidate()
	{
		return is_candidate;
	}

/* Returns true if a node is currently in CS */
	public boolean IsInCS()
	{
		return is_in_cs;
	}

/* Returns true if a given node has already sent a Go-Ahead message */
	public boolean Responded(int n)
	{
		for(int i = 0; i < respondedList.size(); i++)
		{
			if(n == respondedList.get(i))
				return true;
		}

		return false;

	}

/* Receving response and enters CS if a responses counter has given value */
	public int GoAhead(int n)
	{
		++responses_count;
		if(responses_count == n)
		{
			CriticalState();
			return 1;
		}

		return 0;
	}

/* List of nodes node which sended a Go-Ahead message */ 
	public Vector<Integer> respondedList;
/* List of nodes which request cannot be yet answered */
	public Vector<Integer> requestsList;

/* Unique id */
	private int id;
/* Time stamp for request */
	private int time_stamp;
/* Helper parameter for setting id  */
	private static int counter = 0;
/* Stores information if node is candidate for CS */
	private boolean is_candidate;
/* Stores information if node is in CS */
	private boolean is_in_cs;
/* Counter of responses recieved */
	private int responses_count;

}
/**
* Class Network
*
* @author Alicja Zoń
*/
import java.util.*;

public class Network{

public static void main(String args[])
{

	Scanner in = new Scanner(System.in);
	System.out.println("Enter a number of nodes: ");
	int N = in.nextInt();

	System.out.println("Enter a number of requests: ");
	int M = in.nextInt();

	////////////* Create a network */////////////////

	Node[] network = new Node[N];

		for(int i = 0; i < N; i++)
	{
		network[i] = new Node();
	}

	/////////* Randomly chose requesting nodes *//////////////

	Set<Integer> requesting = new HashSet<Integer>();

	 	while(requesting.size() < M)
	{
		int randTemp = network[0].GetRandom(0,N-1);
		requesting.add(randTemp);
	}

	////////////* List all nodes which are candidates to a request */////////

	System.out.println("Processes which are candidates to a request: ");

		for(int i : requesting)
	{
		network[i].Request();		
		System.out.println("	Node #" + (i+1) + "; time stamp: " + network[i].GetTimeStamp()); 
	
	}


	///////////* Start requesting *////////////////

	int counter = 0;

	while(counter < M){
		for(int i : requesting)
	{
				
			for(int j = 0; j < N; j++)
		{
			boolean temp = network[i].Responded(network[j].GetId());

			if(j != i && temp == false){

			if(network[j].IsCandidate() == false && network[j].IsInCS() == false){

				System.out.println("	Node #" + network[j].GetId() + " sends Go-Ahead to node #" + network[i].GetId());
					counter += network[i].GoAhead(N-1);
					network[i].AddToRespondedList(network[j].GetId());
				
			}
			else if(network[j].IsInCS() == false)
			{
				if(network[i].GetTimeStamp() < network[j].GetTimeStamp()){
					if(network[i].Responded(network[j].GetId()) == false)
				{
					System.out.println("Compare Node #" + network[i].GetId() + " and node #"  + network[j].GetId() + " time stamps");
					System.out.println("	Node #" + network[j].GetId() + " sends Go-Ahead to node #" + network[i].GetId());
					counter += network[i].GoAhead(N-1);
					network[i].AddToRespondedList(network[j].GetId());
				}
			}
			
				else{

					if(network[j].Responded(network[i].GetId()) == false)
				{
					System.out.println("Compare Node #" + network[i].GetId() + " and node #"  + network[j].GetId() + " time stamps");	
					network[j].AddToRespondedList(network[i].GetId());
					System.out.println("	Node #" + network[i].GetId() + " sends Go-Ahead to node #" + network[j].GetId());
					counter += network[j].GoAhead(N-1);
					network[j].AddToRequestsList(network[i].GetId());
				}
				}	

			}

			else
			{
				network[j].AddToRequestsList(network[i].GetId());
			}

			

		}


	}


}
}

}
}
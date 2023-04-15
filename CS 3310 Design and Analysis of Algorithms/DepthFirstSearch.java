/**************************************************************/
/* Benjamin Dinh                                              */
/* Login ID: btdinh                                           */
/* CS 3310, Spring 2023                                       */
/* Programming Assignment 1                                   */
/* Graph class: Basic system for creating undirected graphs   */
/**************************************************************/
import java.util.*;
@SuppressWarnings({"unchecked"})
public class DepthFirstSearch {
	private int Vertices;
	private LinkedList<Integer>[] adjacencyList;

/**************************************************************/
/* Constructor                                            
/* Purpose: Initializes the graph 		  
/* Parameters:                                                
/* int vertices: The number of vertices the graph will have
/**************************************************************/
	DepthFirstSearch(int vertices) 
	{
		Vertices = vertices;
		adjacencyList = new LinkedList[vertices]; 
		for (int i = 0; i < vertices; i++)
		{
			adjacencyList[i] = new LinkedList<>();
		}
	}

/**************************************************************/
/* Method: addEdge                                            
/* Purpose: Links the elements of the graph together 		  
/* Parameters:                                                
/* int node1: The original node
/* int node2: The node that is being added                  
/* Returns: Void
/**************************************************************/

	void addEdge(int node1, int node2)
	{
		adjacencyList[node1].add(node2);
	}


/**************************************************************/
/* Method: search                                            
/* Purpose: Uses DFS to find all of the connected nodes		  
/* Parameters:                                                
/* int node: The node that is going to be searched               
/* Returns: ArrayList with all of the connected elements of the graph 
/**************************************************************/

	ArrayList<Integer> search(int node)
	{
		boolean[] visited = new boolean[Vertices];
		ArrayList<Integer> connected = new ArrayList<>();
		searchHelper(node, visited, connected);
		return connected;
	}
	
/**************************************************************/
/* Method: helper                                            
/* Purpose: Traverses through the graph to find all adjacent nodes 		  
/* Parameters:                                                
/* int node: The origin node
/* boolean[] visited: An array of booleans recording which nodes have been visited                  
/* Returns: Void 
/**************************************************************/
	void helper(int node, boolean[] visited)
	{
		visited[node] = true;

		for (int neighbor : adjacencyList[node])
		{
			if (!visited[neighbor])
			{
				helper(neighbor, visited);
			}
		}
	}

/**************************************************************/
/* Method: searchHelper                                           
/* Purpose: Traverses through the graph to find all adjacent nodes for the search method		  
/* Parameters:                                                
/* int node: The origin node
/* boolean[] visited: An array of booleans recording which nodes have been visited
/* ArrayList<Integer> connected: Continuosly passes the arraylist for recursion                  
/* Returns: Void 
/**************************************************************/
	void searchHelper(int node, boolean[] visited, ArrayList<Integer> connected)
	{
		visited[node] = true;
		connected.add(node);

		for (int neighbor : adjacencyList[node])
		{
			if (!visited[neighbor])
			{
				searchHelper(neighbor, visited, connected);
			}
		}
	}

/**************************************************************/
/* Method: getConnections                                           
/* Purpose: Traverses through all of the nodes and counts how many sepparate components there are		  
/* Parameters: None                                                             
/* Returns: int: how many connected components
/**************************************************************/
	public int getConnections()
	{
		boolean[] visited= new boolean[Vertices];
		int numOfConnections = 0;
		for (int i = 0; i < Vertices; i++)
		{
			if(!visited[i])
			{
				helper(i, visited);
				numOfConnections++;
			}
		}
		return numOfConnections;
	}
}

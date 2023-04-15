/**************************************************************/
/* Benjamin Dinh                                              */
/* Login ID: btdinh                                           */
/* CS 3310, Spring 2023                                       */
/* Programming Assignment 1                                   */
/* Prog1 class: For a given undirected graph, outputs         */
/*                 the vertices of each connected component.  */
/**************************************************************/
import java.io.*;
import java.util.*;
public class GraphVertices {
	public static void main(String[] args) {
		String fileName;
		try
		{
			if (args.length == 0) // Checks if the input file was used as an argument
			{
				Scanner input = new Scanner(System.in); // If ther are no arguments asks user in terminal for name of text file
				System.out.println("Enter the name of the file: ");
				fileName = input.nextLine();
				input.close();
			}
			else
			{
				fileName = args[0]; // if file was an argument sets fileName to that argument
			}

			FileReader fileReader = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(fileReader); // Method of reading each line if the input file

			String line = " "; // Initializing the input line
			int graphNumber = 0;
			System.out.println("_______________________________________________________");
			while (line != null) 
			 {
				graphNumber++;
            	line = reader.readLine();
				if (line == null) // checks if there are no more lines left in the input
					break;
				System.out.println("Graph " + graphNumber + ":");
				System.out.println(separatedComponents(line));
			 }
			
			 System.out.println("_______________________________________________________");
        	reader.close();

		}
		catch(IOException error)
		{
			System.out.println("Error reading the given file");
		}
		catch(ArrayIndexOutOfBoundsException error)
		{
			System.out.println("Arguments not properly inputted");
		} 
	}



/**************************************************************/
/* Method: separatedComponents                                           
/* Purpose: Traverses through the graph to find all adjacent nodes for the search method		  
/* Parameters:                                                
/* String line: One line from the input file containing all of the data for one graph             
/* Returns: String: The processed string utilizing DFS to find the amount of 
/*          connected components as well as the elements in each component.
/**************************************************************/
	public static String separatedComponents(String line)
	{
		ArrayList<ArrayList<String>> stringComponents = new ArrayList<>(); // 2D ArrayList containing the strings of each connected component
		ArrayList<String> stringComponent = new ArrayList<>(); // Array list containing connected component
		ArrayList<Integer> alreadyAdded = new ArrayList<>(); // Array of integers keeping track of which numbers have already been dealt with

		String components = ""; // Initializing the output of the method.
		String[] parts = line.split("\\s+"); // String array of the argument line splitting up each of the items
		int vertices = Integer.parseInt(parts[0]); // Gets the amount of vertices from the input line
		int addComponent; // Int to check if a component needs to be added to the final string

		DepthFirstSearch graph = new DepthFirstSearch(vertices + 1); // initializes the graph with the number of vertices excluding zero
		
		
		for (int i = 1; i < parts.length; i++)
				{
					parts[i] = parts[i].replace("(", "").replace(")", "").replace(",", " "); // Removes unneeded characters from the sting
					String[] nodes = parts[i].split(" "); // splits the strings down further into individual integers
					int node1 = Integer.parseInt(nodes[0]);
					int node2 = Integer.parseInt(nodes[1]);
					
					graph.addEdge(node1, node2);
					graph.addEdge(node2, node1);
				}

			components += graph.getConnections() - 1; // Addeding how many components there are to output
			components += " connected components: ";

			for (int i = 0; i < graph.search(vertices).size(); i++) // Adds the first component to stringComponents ArrayList.
			{
				stringComponent.add((graph.search(vertices).get(i).toString()));
				alreadyAdded.add(graph.search(vertices).get(i));
			}
			stringComponents.add(stringComponent);
			
			for (int i = vertices - 1; i > 1; i--)
			{	
				addComponent = 1;
				for (int element : graph.search(i)) // Checks if an element is not added to the components.
				{
					if(alreadyAdded.contains(element))
					{
						addComponent = 0;
					}
				}
				if (addComponent == 1) //If component needs to be added it adds it to the stringComponents
					{
						stringComponent = new ArrayList<>();
						for (int k = 0; k < graph.search(i).size(); k++)
						{
							stringComponent.add((graph.search(i).get(k).toString()));
							alreadyAdded.add(graph.search(i).get(k));
						}
						stringComponents.add(stringComponent);
					}
			}

			for (int i = 0; i < stringComponents.size(); i++) // For each stringComponent adds the component to the string
			{
				components += stringComponents.get(i) + " ";
			}
		return components;
	}
}


import java.util.*;
import java.io.*;

/**************************************************************/
/* Benjamin Dinh                                              */
/* Login ID: btdinh                                           */
/* CS 3310, Spring 2023                                       */
/* Programming Assignment 2                                   */
/* Prog2 class: For a given dictionary file return sets of    */
/*              anagrams found.                               */
/**************************************************************/

public class Anagrams {
	public static void main(String[] args) {
			String fileName;
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

			HashMap<String, ArrayList<String>> anagram = anagrams(fileName);
			gramFile(anagram);
		}


/**************************************************************/
/* Method: anagrams                                         
/* Purpose: Generate a hashmap based on a dictionary text file 
/* with an alphabetized String key and list of matching words	  
/* Parameters:                                                
/* String fileName: The name of the file it is going to extract words from          
/* Returns: Sorted Hashmap with alphabetized keys and lists of words
/**************************************************************/
	public static HashMap<String, ArrayList<String>> anagrams(String fileName)
	{
		try
		{
		HashMap<String, ArrayList<String>> grams = new HashMap<>();
		String line = "";
		String alphaKey = "";

		FileReader fileReader = new FileReader(fileName);
		BufferedReader reader = new BufferedReader(fileReader);

		while (line != null) 
		{
		   line = reader.readLine();
		   if (line == null) // checks if there are no more lines left in the input
			   break;
			alphaKey = alphaSort(line); // Generates key for word

			if (grams.containsKey(alphaKey)) //if Hashmap already has key add word to arraylist
			{
				grams.get(alphaKey).add(line);
			}
			else{ // If the key is not in hashmap generate new entry and initialize the list
				ArrayList<String> wordList = new ArrayList<>();
				wordList.add(line);
				grams.put(alphaKey, wordList);
			}
		}
		reader.close();
		return grams;

		}catch(IOException error)
		{
			System.out.println("Error reading file");
		}

		HashMap<String, ArrayList<String>> empty = new HashMap<>();
		return empty;
	}


/**************************************************************/
/* Method: gramFile                                       
/* Purpose: Convert Hashmap of anagrams into a formatted text file		  
/* Parameters:                                                
/* HashMap Grams: The Hashmap it is going to get information form          
/* Returns: Void
/**************************************************************/
	public static void gramFile(HashMap<String, ArrayList<String>> grams)
	{
		try{
			StringBuilder listSet = new StringBuilder();
			FileWriter writer = new FileWriter("Anagrams.txt");
			int size = 0;
			int itteration = 0;
			writer.write("____________________________________________________\n");
			for (String key : grams.keySet()) 
			{
				size = grams.get(key).size(); //Checks if there is more than one item in the arraylist; Checking if word is an anagram
				if(size > 1)
				{
					listSet.setLength(0);
					itteration++;
					writer.write("Set " + itteration + ":\n");
					for(int i = 0; i < size; i++)
					{
						listSet.append(grams.get(key).get(i)); // Adds all the words in list to a string to be printed
						if((size - i - 1) > 0){listSet.append(", ");}
					}

					writer.write(listSet.toString());
					writer.write("\n- - - - - - - - - - - - - - - - - - - - - - - - - -\n");
				}
			}

		writer.write("____________________________________________________\n");
		writer.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}


/**************************************************************/
/* Method: alphaSort                                         
/* Purpose: Alphabetically sorts a string to use as a key for HashMap		  
/* Parameters:                                                
/* String word: the word it is going to alphabetize           
/* Returns: String: Alphabetically Sorted String
/**************************************************************/
	public static String alphaSort(String word)
	{
		word = word.toLowerCase();
		char[] charArray = word.toCharArray(); //Converts string to charArray to be alphabetically sorted
		Arrays.sort(charArray);
		String output = new String(charArray);
		return output;
	}
}

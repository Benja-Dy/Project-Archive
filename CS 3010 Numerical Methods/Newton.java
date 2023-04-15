import java.util.*;
import java.io.*;
public class Newton {
	public static void main(String[] args) {
		String filename = args[0];
		Scanner input = new Scanner(System.in);
		String inputS = "";
		float output = 0f;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
		String lineX = br.readLine();
		lineX = lineX.replace("x", "");
		String[] valuesX = lineX.split("\\s+");

		ArrayList<Float> xlist = new ArrayList<>();
        ArrayList<Float> ylist = new ArrayList<>();

		for (int i = 0; i < valuesX.length; i++)
		{
			xlist.add(Float.parseFloat(valuesX[i]));
		}

		
		String lineY = br.readLine();
		lineY = lineY.replace("y", "");
		String[] valuesY = lineY.split("\\s+");

		for (int i = 0; i < valuesY.length; i++)
		{
			ylist.add(Float.parseFloat(valuesY[i]));
		}

		ArrayList<Float> clist = coefficient(xlist, ylist);

		System.out.println("Enter number to evaluate: ");
		inputS = input.nextLine();
		while(!inputS.equals("q"))
		{
			output = evaluate(xlist, clist, Float.parseFloat(inputS));
			System.out.println("X = " + inputS + " evalutates to " + output);
			System.out.println("Enter number to evaluate: ");
			inputS = input.nextLine();
		}
		System.out.println("Exiting...");
		br.close();
		input.close();
		}
		catch (IOException e) 
		{
            System.err.println("File does not exists");
        }
		

	}

	public static float evaluate(ArrayList<Float> xlist, ArrayList<Float> clist, float x)
	{
		int n = clist.size() - 1;
		float result = clist.get(n);

		for (int i = n - 1; i >= 0; i--)
		{
			result = result * (x - xlist.get(i)) + clist.get(i);
		}

		return result;
	}

	public static ArrayList<Float> coefficient(ArrayList<Float> xlist, ArrayList<Float> ylist) {
		int n = ylist.size();
		ArrayList<Float> clist = new ArrayList<>();
		
		for (int i = 0; i < n; i++) {
			clist.add(ylist.get(i));
		}
	
		for (int i = 1; i < n; i++) {
			for (int j = n - 1; j >= i; j--) {
				float den = xlist.get(j) - xlist.get(j - i);
				float num = clist.get(j) - clist.get(j - 1);
				clist.set(j, num / den);
			}
		}
	
		return clist;
	}
}

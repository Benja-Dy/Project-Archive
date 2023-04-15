import java.util.*;
import java.io.*;

public class RandomDataSet {
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);

		Random random = new Random();
		File file = new File("dataset.txt");
		try
		{
			FileWriter writer = new FileWriter(file);
			for (int i = 0; i < n; i ++)
			{
				float x = random.nextFloat() * 200f - 100f;
				writer.write("x");
				writer.write(String.format("%.2f" , x));
				writer.write(" ");
			}
			writer.write("\n");
			for (int i = 0; i < n; i ++)
			{
				float y = random.nextFloat() * 200f - 100f;
				writer.write("y");
				writer.write(String.format("%.2f" , y));
				writer.write(" ");
			}
			System.out.println("File sucessfully written to dataset.txt");
			writer.close();
		}catch(IOException e)
		{
			System.out.println("Failed to write file.");
		}
	}
}

import java.util.*;
import java.io.*;
/**
 * Gaussian
 */
public class Gaussian {

	public static void main(String[] args) {
		String filename = args[0];

		boolean spp = false;

		if (filename.equals("--spp"))
		{
			try
			{
				filename = args[1];
				spp = true;
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				System.out.println("Arguments not properly inputted");
			}
		}
		try
		{
			FileReader fileReader = new FileReader(filename);
			Scanner scanner = new Scanner(fileReader);
			int count = scanner.nextInt();
			

			BufferedReader reader = new BufferedReader(new FileReader(filename));
        	int lineCount = 0;
       		while (reader.readLine() != null) 
			 {
            	lineCount++;
			 }
        	reader.close();
			double[][] coeff = new  double[count][lineCount - 1];
			double[] cons = new double[count];

			for (int i = 0; i < lineCount - 2; i++)
			{
				for (int j = 0; j < count; j++)
				{
						coeff[i][j] = scanner.nextDouble();
				}
			}
			for (int i = 0; i < count; i++)
			{
				cons[i] = scanner.nextDouble();
			} 
			double[] sol = new double[coeff.length];
			if(!spp)
			{
				sol = NaiveGaussian(coeff, cons);
			}
			else
			{
				sol = SPPGaussian(coeff, cons);
			}

			FileWriter writer = new FileWriter("sys1.sol");
			for (int i = 0; i < sol.length; i++) {
                writer.write(sol[i] + " ");
            }
            writer.close();

			scanner.close();
		}
		catch(IOException ex)
		{
			System.out.println("Error reading the file: " + filename);
		}

	}

public static double[] NaiveGaussian(double[][] coeff, double[] cons)
	{
		double[] sol = new double[coeff.length];
		FwdElimination(coeff, cons);
		BackSubst(coeff, cons, sol);
		return sol;
	}

public static void FwdElimination(double[][] coeff, double[] cons)
{
	int n = coeff.length;
	for (int k = 0; k < n - 1; k++)
	{
		for (int i = k + 1; i < n; i++)
		{
			double mult = coeff[i][k] / coeff[k][k];
			for (int j = k + 1; j < n; j++)
			{
				coeff[i][j] = coeff[i][j] - mult * coeff[k][j];
			}
			cons[i] =  cons[i] - mult * cons[k];
			
		}
	}
}
public static void BackSubst (double[][] coeff, double[] cons, double[] sol)
{
	int n = coeff.length;
	for (int i = n - 1; i >= 0; i--)
	{
		double sum = 0.0;
		for (int j = i + 1; j < n; j++)
		{
			sum += coeff[i][j] * sol[j];
		}
		sol[i] = (cons[i] - sum) / coeff[i][i];
	}
}
public static double[] SPPGaussian(double[][] coeff, double[] cons)
{
	double[] sol = new double[coeff.length];
	Integer[] ind = new Integer[coeff.length];

	for (int i = 0; i < coeff.length; i++)
	{
		ind[i] = i;
	}

	SPPFwdElimination(coeff, cons, ind);
	SPPBackSubst(coeff, cons, sol, ind);
	return sol;
}

public static void SPPFwdElimination(double[][] coeff, double[] cons, Integer[] ind)
{
	int n = coeff.length ;
	double[] scaling = new double[n];
	for (int i = 0; i < n; i++)
	{
		double smax = 0;

		for (int j = 0; j < n; j++)
		{
			smax = Math.max(smax, Math.abs(coeff[i][j]));
		}
		scaling[i] = smax;
	}

	for (int k = 0; k < n - 1; k++)
	{
		double rmax = 0;
		int maxInd = k;

		for (int i = k; i < n; i++)
		{
			double r = Math.abs(coeff[ind[i]][k] / scaling[ind[i]]);
			if (r > rmax)
			{
				rmax = r;
				maxInd = i;
			}
		}
		int temp = ind[maxInd];
		ind[maxInd] = ind[k];
		ind[k] = temp;

		for (int i = k + 1; i < n; i++)
		{
			double mult = coeff[ind[i]][k] / coeff[ind[k]][k];

			for (int j = k + 1; j < n; j++)
			{
				coeff[ind[i]][j] = coeff[ind[i]][j] - mult * coeff[ind[k]][j];
			} 
			cons[i] = cons[ind[i]] - mult * cons[ind[k]];
		}
	}
}

public static void SPPBackSubst(double[][] coeff, double[] cons, double[] sol, Integer[] ind)
{
	int n = ind.length;
	sol[n - 1] = cons[ind[n - 1]] / coeff[ind[n - 1]][n - 1];

	for (int i = n - 2; i >= 0; i--)
	{
		double sum = cons[ind[i]];
		for (int j = i + 1; j < n; j++)
		{
			sum = sum - coeff[ind[i]][j] * sol[j];
		}
		sol[i] = sum / coeff[ind[i]][i];
	}
}



}

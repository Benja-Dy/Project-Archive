import java.util.*;
import java.io.*;

public class polRoot {
	int iterations = 0;
	boolean outcome = false;

	public static void main(String[] args) {
		int maxIter = 10000;
		int algorithm = 0;
		int argnumber = 1;
		float initP = 0;
		float initP2 = 0;
		float eps = 0.000001f;
		float answer = 0;
		polRoot pr = new polRoot();

		boolean initialized = false;
		String filename = args[args.length - 1];

		if (args[0].equals("-newt"))
		{algorithm = 1;}
		else if (args[0].equals("-sec"))
		{algorithm = 2;}
		else if (args[0].equals("-hybrid"))
		{algorithm = 3;}
		else if (args[0].equals("-maxIter"))
		{maxIter = Integer.parseInt(args[1]); argnumber++;}
		else 
		{initP = Float.parseFloat(args[0]); initialized = true;}

		for (int i = argnumber; i < args.length - 1; i++)
		{
				if(args[i].equals("-maxIter"))
					{maxIter = Integer.parseInt(args[i + 1]); i++;}
				else
				{
					if (!initialized)
						{initP = Float.parseFloat(args[i]); initialized = true;}
					else {initP2 = Float.parseFloat(args[i]);}
				}
		}

		float[] polynomial = pr.getPolynomial(filename);
		answer = pr.getRoot(polynomial, algorithm, answer, initP, initP2, maxIter, eps);
		pr.toFile(filename, answer);
	}

	public float bisection(float[] f, float a, float b, int maxIter, float eps)
	{
		float c = 0.0f;
		float fa = functionSolver(f, a);
		float fb = functionSolver(f, b);
		float fc;
		
		if (fa * fb >= 0)
		{
			System.out.println("Inadequate values for a and b.");
			return -1.0f;
		}
		float error = b - a;
		for (int i = 1; i < maxIter; i++)
		{
			iterations++;
			error = error / 2;
			c = a + error;
			fc = functionSolver(f, c);
			if (error < eps || fc == 0)
				{outcome = true; return c;}
			if (fa * fc < 0)
				{b = c; fb = fc;}
			else
				{a = c; fa = fc;}
		}
		return c;
	}

	public float newton(float[] f, float[] derF, float x, int maxIter, float eps, float delta)
	{
		float fx = functionSolver(f, x);
		float fd;
		float d;
		for (int i = 1; i < maxIter; i++)
		{
			iterations++;
			fd = functionSolver(derF, x);

			if (Math.abs(fd) < delta)
			{
				System.out.println("Small Slope!");
				return x;
			}
			d = fx / fd;
			x = x - d;
			fx = functionSolver(f, x);
			if (Math.abs(d) < eps)
			{
				outcome = true;
				return x;
			}
		}
		System.out.println("Max iterations reached without convergence...");
		return x;
	}

	public float secant(float[] f, float a, float b, int maxIter, float eps)
	{
		float fa = functionSolver(f, a);
		float fb = functionSolver(f, b);
		float d;
		
		if (Math.abs(fa) > Math.abs(fb))
		{
			float tempA = a;
			float tempFa = fa;
			a = b;
			fa = fb;
			b = tempA;
			fb = tempFa;
		}

		for (int i = 1; i < maxIter; i++)
		{
			iterations++;
			if (Math.abs(fa) > Math.abs(fb))
			{
			float tempA = a;
			float tempFa = fa;
			a = b;
			fa = fb;
			b = tempA;
			fb = tempFa;
			}

			d = (b - a) / (fb - fa);
			b = a;
			fb = fa;
			d = d * fa;

			if (Math.abs(d) < eps)
			{
				outcome = true;
				return a;
			}

			a = a - d;
			fa = functionSolver(f, a);
		}
		System.out.println("Maximum number of iterations reached!");
		return a;
	}

	public float hybrid(float[] f, float[] derivative, float a, float b, int maxIter, float eps, float delta)
	{
		float answer = bisection(f, a, b, 10, eps);
		answer = newton(f, derivative, answer, maxIter, eps, delta);
		return answer;
	}

	public float[] derivative(float[] f)
	{
		float[] derivative = new float[f.length - 1];
		for (int i = 0; i < f.length - 1; i++)
		{
			derivative[i] = f[i] * (derivative.length - i);
		}
		return derivative;
	}

	public float functionSolver(float[] f, float x)
	{
		float y = 0;
		for (int i = 0; i < f.length; i++)
		{
			y += f[i] * Math.pow(x,(float)f.length - i - 1);
		}
		return y;
	}

	public float[] getPolynomial(String filename)
	{
		try
		{
		FileReader fileReader = new FileReader(filename);
		Scanner scanner = new Scanner(fileReader);
		int count = scanner.nextInt();
		float[] polynomial = new float[count + 1];
		for (int i = 0; i < count + 1; i ++)
		{
			polynomial[i] = scanner.nextFloat();
		}
		scanner.close();
		return polynomial;
		}
		catch (IOException ex)
		{
			float[] polynomial = new float[1];
			System.out.println("Error reading the file: " + filename);
			return polynomial;
		}
	}

	public void toFile(String filename, float answer)
	{
		try
		{
			filename = filename.substring(0, filename.lastIndexOf('.')) + ".sol";
			String outcomeW;
			FileWriter writer = new FileWriter(filename);
			if (outcome)
				{outcomeW = "Success";}
			else
				{outcomeW = "Unsuccessful";}
			writer.write(answer + " " + iterations + " " + outcomeW);

			writer.close();
		}
		catch (IOException ex)
		{
			System.out.println("Failed to write file");
		}
	}

	public float getRoot(float[] polynomial, int algorithm,float answer, float initP,float initP2,int maxIter,float eps)
	{
		if (algorithm == 0)
		{
			answer = bisection(polynomial, initP, initP2, maxIter, eps);
		}
		else if (algorithm == 1)
		{
			float[] derivative = derivative(polynomial);
			answer = newton(polynomial, derivative, initP, maxIter, eps, eps);
		}
		else if (algorithm == 2)
		{
			answer = secant(polynomial, initP, initP2, maxIter, eps);
		}
		else if (algorithm == 3)
		{
			float[] derivative = derivative(polynomial);
			answer = hybrid(polynomial, derivative, initP, initP2, maxIter, eps, eps);
		}
		return answer;
	}
}
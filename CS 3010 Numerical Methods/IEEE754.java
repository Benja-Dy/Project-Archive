import java.util.Scanner;
import java.lang.Math;

public class IEEE754
{
	public static String decToIEEE(float dec)
	{
		int ltz = 0;
		int subcount = 0;
		String sbit;
		StringBuilder wString = new StringBuilder("");
		StringBuilder expo = new StringBuilder("");

		if (dec >= 0){sbit = "0";} // Get the sign bit
			else {sbit = "1"; dec *= -1;}

		int whole = (int)dec; //Convert the whole part of the number to binary
		while (whole > 0)			   
		{
			if ((whole % 2) > 0) {wString.insert(0,'1'); whole = whole/2; } //Divides number by two, if remainer is 1 adds it to wString
			else {wString.insert(0,'0'); whole = whole/2; }
			if (whole == 1) {whole--;} //Dont add the front bit as it is implicit
		}

		if(wString.length() > 0) //If the absolute value of the number is greater than 0
		{
			int exponent = wString.length() + 127; //Add exponent to the bias
			while (exponent > 0)			   
			{
				if ((exponent % 2) > 0) {expo.insert(0,'1'); exponent = exponent/2; } //Same process as getting the binary of whole number
				else {expo.insert(0,'0'); exponent = exponent/2; }
				if (exponent == 1) {expo.insert(0,'1'); exponent--;} //Add the front bit for the exponent
			}
		}
		else{ltz = 1;}
		float remainder = dec - (int)dec;

		for (int i = 0; i < 100; i++) // For the worst case (Length of Mantissa) calculates the binary of the decimal part
		{	
			remainder *= 2;
			int front = (int) remainder;
			wString.append(front);
			remainder -= front;
		}

		if (ltz == 1)
		{
			int count= 0;
			
			
			while (wString.charAt(count) != '1')
			{
				count += 1;
			}
			int exponent = 126 - count;
			subcount = count;
			count = 0;
			while (count < 8)			   
			{
				if ((exponent % 2) > 0) {expo.insert(0,'1'); exponent = exponent/2; } //Same process as getting the binary of whole number
				else if (exponent == 1) {expo.insert(0,'0'); exponent--;} //Add the front bit for the exponent
				else {expo.insert(0,'0'); exponent = exponent/2; }
				
				count++;
			}
			wString.delete(0, subcount + 1);
		}
		wString.setLength(23); //Shrinks/enlarges string to the correct size of the mantissa
		return "IEEE 754: " + sbit + " " + expo + " " + wString;
	}
	
	public static float toFloat(String input)
	{
		int numend = 0, expend = 0;
		float output;
		double exp;
		String number, exponent;
		StringBuilder scient = new StringBuilder();
		scient.append(input);
		if (scient.charAt(scient.length() - 1) == ')')
		{
			for (int i = 0; i < scient.length(); i++)
			{
				if (scient.charAt(i) == ' ')
				{
					numend = i;
					break;
				}
			}
			number = scient.substring(0, numend);
			for (int i = 0; i < scient.length(); i++)
			{
				if (scient.charAt(i) == '(')
				{
					expend = i;
					break;
				}
			}
			exponent = scient.substring(expend + 1, scient.length() - 1);
			output = Float.parseFloat(number);
			exp = Integer.parseInt(exponent);
			exp = Math.pow(10, exp);
			output *= exp;

		}
		else
		{
			number = scient.toString();
			output = Float.parseFloat(number);
		}
		
		
		return output;
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter number: ");
		String input = scan.nextLine();
		float converted = toFloat(input);
		System.out.println(decToIEEE(converted));
		
	}
} 
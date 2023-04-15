// 
// Name: Dinh, Benjamin
// Project:# 5
// Due:      11/24/2021
// Course: cs-2560-01-f21 
// 
// Description: 
//    Program that uses the rational class to print out the value of pi
//    at a decimal place length determined by the user.
//

#include <iostream>
#include <cstdlib>
#include "Rational.h"
#include "Rational.cpp"
using namespace std;

int main(int argc, char *argv[])
{
   int n = 0;
   int f = 0;;
   cout << "Rational by B. Dinh" << endl;

   if (n == 0)
   {
      cout << "enter n and f? ";
      cin >> n >> f;
   }

   //Calculating pi
   Rational pi(4,1);
   int sign = -1;
   for(int i = 0; i < n - 1; i ++)
   {
      int denominator = 1 + (2*(i+1));
      if(i % 2 == 1)
      {
         sign = 1;
      }
      else
      {
         sign = -1;
      }
      Rational temp(4,denominator);
      if(sign > 0)
         pi = pi + (temp);
      else
         pi = pi - (temp);
   }

   // Output starts here
   cout << pi.to_string() << endl;
   cout << endl<< pi.print(f, "", true) << endl;
   Rational pi2 = pi * 2;
   cout << "2 * pi = " << pi2.to_string() << endl;
   cout << endl << "Other Tests: " << endl;

   Rational r1;
   cout << endl << "Testing default constructor and get and set methods" << endl;
   cout << "Get Numerator: " << r1.getNumerator() << endl <<"Get Denominator: " << r1.getDenominator() << endl;
   Rational r2(2);
   
   cout << endl << "Testing set and equality methods" << endl;
   r1.Set(2,1);
   bool same = r1 == r2;
   cout << "r1 set to 2 / 1: " << r1.to_string() << " and if it is equal to r2 (0 means it is true): " << same << endl;
   r1.Set(5,9);
   double r3 = r1; 
   cout << endl << "Testing Rational to double"<< endl << "The double of "<< r1.to_string() << " is: " << r3 << endl;
   return 0;
}
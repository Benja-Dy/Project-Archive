// 
// Name: Dinh, Benjamin
// Project:# 5
// Due:      11/24/2021
// Course: cs-2560-01-f21 
// 
// Description: 
//    Program that allows the creation, mutability, and access of the Rational Class
//


#include <iostream>
#include <cstdlib>
#include "Rational.h"
using namespace std;

// Constructors and set method
Rational::Rational()
{
   numerator = 0;
   denominator = 1;
}
Rational::Rational(int num)
{
   numerator = num;
   denominator = 1;
}
Rational::Rational(int num, int den)
{
   Set(num, den);
}


void Rational::Set(int num, int den)
{
   if(den == 0)
      throw "Invalid Argument";
   numerator = num;
   denominator = den;
}

void Rational::Set(int num)
{
   numerator = num;
}
 


// Other functions 

string Rational::to_string()
{
   return std::to_string(numerator) + " / " + std::to_string(denominator);
}

string Rational::print(int x = 15, string s = "", bool b = false)
{
   string printed;
   int whole = numerator/denominator;
   printed += s;
   printed += std::to_string(whole);
   printed += ".";
   int p = numerator;
   int q = denominator;
   int temp;
   
   for(int i = 0; i < x - 1; i++)
   {
      p = p % q * 10;
      temp = p/q;
      printed += std::to_string(temp);
   }
   p = p % q * 10;
   temp = p / q;
   p = p % q * 10;

   if((p / q) > 4)
      temp += 1;
   printed += std::to_string(temp);



   if(b == true)
      printed += "\n";
   return printed;
}


// Overloading functions

Rational Rational::operator+(const Rational & other)
{
   int tempNum = (this->numerator * other.denominator) + (this->denominator * other.numerator);
   int tempDen = (this->denominator * other.denominator);
   Rational rat(tempNum, tempDen);
   return(rat);
}

Rational Rational::operator+(int n)
{
   int tempNum = this->numerator + (this->denominator * n);
   int tempDen = this->denominator;
   Rational rat(tempNum, tempDen);
   return(rat);
}

Rational Rational::operator-(const Rational & other)
{
   int tempNum = (this->numerator * other.denominator) - (this->denominator * other.numerator);
   int tempDen = (this->denominator * other.denominator);
   Rational rat(tempNum, tempDen);
   return(rat);
}
Rational Rational::operator*(int n)
{
   int tempNum = (this->numerator * n);
   int tempDen = (this->denominator);
   Rational rat(tempNum, tempDen);
   return(rat);
}

Rational::operator double()
{
   double value;
   value = (static_cast<double>(numerator)/denominator);
   return value;
}

bool Rational::operator==(const Rational & other)
{
   bool equal = true;
   int left = this->numerator * other.denominator;
   int right = this->denominator * other.numerator;
   
   if(left == right)
   {
      equal = false;
   }
   return(equal);
}


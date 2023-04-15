// 
// Name: Dinh, Benjamin
// Project:# 5
// Due:      11/24/2021
// Course: cs-2560-01-f21 
// 
// Description: 
//    Header file for the Rational function
//

#ifndef RATIONAL_H
#define RATIONAL_H

#include <iostream>
#include <string>
using namespace std;

class Rational
{
   private:
      int numerator = 0;
      int denominator = 1;
      string out;
      double value;


   public:
      Rational();
      Rational(int num);
      Rational(int num, int den);
      
      void Set(int num, int den);
      void Set(int num);

      Rational operator+(const Rational & other);
      Rational operator+(int n);
      Rational operator-(const Rational & other);
      Rational operator*(int n);
      bool operator==(const Rational & other);
      operator double();
      
      int getNumerator() {return numerator;}
      int getDenominator() {return denominator;}
      string to_string();
      string print(int x, string s, bool b);
};

#endif
#include <iostream>
#include <array>
#include <iomanip>

using namespace std;

class Complex
{
   private:
      double a;
      double b;

   public:
      Complex(double tempA, double tempB);
      void set(double tempA, double tempB);
      double geta();
      double getb();
      bool operator==(const Complex & other);
      friend std::ostream& operator<< (std::ostream& out, const Complex& Complex);

};

Complex::Complex(double tempA, double tempB)
{
   a = tempA;
   b = tempB;
}

void Complex::set(double tempA, double tempB)
{
   a = tempA;
   b = tempB;
}

double Complex::geta()
{
   return a;
}

double Complex::getb()
{
   return b;
}

bool Complex::operator==(const Complex & other)
{
   if(this->a == other.a && this->b == other.b)
   {
      return true;
   }
   else
   {
      return false;
   }
}

std::ostream& operator<< (std::ostream& out, const Complex& comp)
{
   out << fixed << setprecision(1) << comp.a << " + " << comp.b << endl;
   return out;
}
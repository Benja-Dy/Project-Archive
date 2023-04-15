#include <iostream>
#include <iomanip>
#include "Element.h"
using namespace std;

void printTable(Element** pTable, const int size)
{
   cout << "Periodic Table by B. Dinh \n" << endl;
   cout << "Name " << setw(22) << "Sym" << setw(4) << "No" << setw(7) << "Mass" << endl;
   cout << "----------------------- --- --- ------" << endl;
   for(int i = 0; i < size; i++)
   {
      cout << left << setw(23) << pTable[i]->name;
      cout << setw(4) << right << pTable[i]->symbol;
      cout << right << setw(4) << pTable[i]->atomNum;
      cout << fixed << setprecision(2) << setw (7) << pTable[i]->mass << endl;
   }
}
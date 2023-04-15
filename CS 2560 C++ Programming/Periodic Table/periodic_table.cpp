// 
// Name: Dinh, Benjamin
// Project:# 4
// Due:      10/28/2021
// Course: cs-2560-01-f21 
// 
// Description: 
//    Reads a file with data from the periodic table and dynamically reads and outputs that data.
//

#include <iostream>
#include <iomanip>
#include <cstdlib>
#include "Element.h"

using namespace std;


int sortName(const void* a, const void* b)
{
Element *first = *(Element**) a;
Element *second = *(Element**) b;
if ( first->name < second->name)
   return -1;
else if (second->name < first->name)
   return 1;
else
   return 0;
}

double avgMass(Element** pTable, const int size)
{
   double sum;
   for(int i = 0; i < size; i++)
   {
      sum += pTable[i]->mass;
   }
   return sum/size;
}

int main()
{
   int size = 0;
   string fileName = "periodictable.dat";
   Element **pTable = get_table (fileName, size);

   qsort(pTable, size, sizeof (Element*), sortName);  
   printTable(pTable, size);
   cout << "The average mass:= " << setw(19) << fixed << setprecision(2) << avgMass(pTable, size) << endl;
   cout << "The number of elements: " << size << endl;
   delete [] pTable;
   pTable = nullptr;
   return 0;
}
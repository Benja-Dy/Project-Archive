#ifndef ELEMENT_H
#define ELEMENT_H
#include <string>

using namespace std;

struct Element
{
   string name;
   int atomNum;
   double mass;
   string symbol;
};
void printTable(Element** pTable, const int size);
Element **get_table (string file, int& size);


#endif 
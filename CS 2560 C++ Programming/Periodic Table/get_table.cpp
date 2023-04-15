#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <vector>
#include "Element.h"

using namespace std;

struct Node
{
   Element *elem;
   Node *next;
};

Element **get_table (string file, int& size) 
{ 
   ifstream infile;
   string nextline;
   infile.open(file);
   size = 0;
   Node *head = NULL;

   if (!infile)
   {
      cout << "File " << file << " failed to open";
      return NULL;
   }
   while (getline(infile, nextline))
   {
      stringstream s (nextline);
      string name;
      string symbol;
      int num;
      float mass;
      if((s >> num >> symbol >> mass >> name))
      {
      Node * node = new Node;
      node->elem = new Element;
      node->elem->atomNum = num;
      node->elem->symbol = symbol;
      node->elem->mass = mass;
      node->elem->name = name;
      node->next = head;
      head = node;
      size++;
      
      }
   }
   Element **pTable = new Element*[size];
   int i = 0;
   Node *temp = head;
   while (temp != NULL)
   {
      pTable[i] = temp->elem;
      temp = temp->next;
      i++;
   }

   infile.close();
   return pTable;
}


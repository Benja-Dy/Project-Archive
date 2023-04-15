// 
// Name: Dinh, Benjamin
// Project:# 2
// Due:      9/27/2021
// Course: cs-2560-01-f21 
// 
// Description: 
//    Generates a calendar output file based on user input. 
//
#include <iostream>
#include <fstream>
#include <string>
using namespace std;
int main()
{
   int month; 
   int year;
   int days;
   int dow;
   int dowMod;
   string monthName;

   cout << "Calendar by B. Dinh" << endl << "\nEnter month year?" << endl;
   cin >> month >> year;
   string months = to_string(month);
   string years = to_string(year);
   string name = months + "-" + years;
   name = name + ".txt;";
   ofstream outfile;
   outfile.open(name);

   switch(month) {
case 1:
   monthName = "January";
   days = 31;
   dowMod = 0;
   break;
case 2:
   monthName = "February";
   if (year %  400 == 0 || (year % 4 == 0 &&year % 100 != 0))
      days = 29;
   else
      days = 28;
   dowMod = 3;
   break;
case 3:
   monthName = "March";
   days = 31;
   dowMod = 2;
   break;
case 4:
   monthName = "April";
   days = 30;
   dowMod = 5;
   break;
case 5:
   monthName = "May";
   days = 31;
   dowMod = 0;
   break;
case 6:
   monthName = "June";
   days = 30;
   dowMod = 3;
   break;
case 7:
   monthName = "July";
   days = 31;
   dowMod = 5;
   break;
case 8:
   monthName = "August";
   days = 31;
   dowMod = 1;
   break;
case 9:
   monthName = "September";
   days = 30;
   dowMod = 4;
   break;
case 10:
   monthName = "October";
   days = 31;
   dowMod = 6;
   break;
case 11:
   monthName = "November";
   days = 30;
   dowMod = 2;
   break;
case 12:
   monthName = "December";
   days = 31;
   dowMod = 4;
   break;
   }
   dow = (year + int(year/4) - int(year/100) + int(year/400) + dowMod * (month-1) + days) % 7;

   outfile << monthName << " " << year << endl;
   outfile <<"Sun  Mon  Tue  Wed  Thu  Fri  Sat" << endl;
   outfile <<"---------------------------------" << endl;
   int i;
   for (i = 0; i <= (dow - 1); i++)
      outfile << "     ";
   outfile << "  ";
   
   for (i = 1; i <+ 8 - dow; i++)
      outfile << i << "    ";
   outfile << endl << "  ";
   
   for (i = 8 - dow + 1; i <= 15 - dow; i++)
      if (i >= 9)
         outfile << i << "   ";
      else
         outfile << i << "    ";
   outfile << endl << " ";

   for (i = 15 - dow + 1; i <= 22 - dow; i++)
      if (i >= 9)
         outfile << i << "   ";
      else
         outfile << i << "    ";
   outfile << endl << " ";

   for (i = 22 - dow + 1; i <= 29 - dow; i++)
         outfile << i << "   ";
   outfile << endl << " ";
   if (36 - dow > days)
   {
      for (i = 29 - dow + 1; i <= days; i++)
         outfile << i << "   ";
      outfile << endl << " ";
   }
      
   else 
   {
      for (i = 29 - dow + 1; i <= 36 - dow; i++)
            outfile << i << "   ";
      outfile << endl << " ";
   } 

   for (i = 36 - dow + 1; i <= days; i++)
         outfile << i << "   ";
   outfile << endl << " ";
  
   
   cout << name << " generated" << endl;
   outfile.close();
   return 0;
}
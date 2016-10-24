#include <iostream>
#include <algorithm>

using namespace std;

int main()
{
    string a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    string b[26];
    int i;
    for(i = 0; i < 26; i++) {
        string n = a;
        rotate(n.begin(), n.begin() + i, n.end());
        b[i] = n;
    }
   for(i = 0; i < 26; i++) cout<<b[i]<<endl;
   string d = "agmboihkpvc"; string key = "egnaro";
   int j = 0;
   for(i = 0; i < d.length(); i++) {
       if(j == key.length()) j = 0;
       cout<<b[key[j] - 'a'][d[i] - 'a'];
       j++;
   }
   return 0;
}

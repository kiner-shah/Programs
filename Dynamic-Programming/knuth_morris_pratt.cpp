/*Knuth-Morris-Pratt algorithm
* By, Kiner Shah
*/
#include <iostream>
#include <cstring>
#include <algorithm>
using namespace std;
vector<int> prefix(string p) {
	int m=p.length();
  vector<int> pi(m);
	pi[0]=0; int k=-1;
	for(int q=1;q<m;q++) {
		while(k>0 && p[k+1]!=p[q]) k=pi[k];
		if(p[k+1]==p[q]) k++;
		pi[q]=k;
	}
	return pi;
}

vector<int> kmp(string t, string p) {
	vector<int> off;
	int n=t.length(),m=p.length();
	vector<int> pi=prefix(p);
	//for(int i=0;i<pi.size();i++) cout<<pi[i]<<" "; cout<<endl;
	int q=-1;
	for(int i=0;i<n;i++) {
		while(q>0 && p[q+1]!=t[i]) q=pi[q];
		if(p[q+1]==t[i]) q++;
		if(q==m-1) { off.push_back(i-m+1); q=pi[q]; }
	}
	return off;
}

int main() {
// your code goes here
	string t,p;
	cin>>t>>p;
	vector<int> pos=kmp(t,p);
	for(int i=0;i<pos.size();i++) cout<<pos[i]<<" ";
	return 0;
}

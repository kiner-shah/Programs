#include <iostream>
#include <stack>
using namespace std;
stack<char> s;
void push(char c) {
	s.push(c);
}
char pop() {
	char temp=s.top();
	s.pop();
	return temp;
}
void intopost(char a[]) {
	int i=0;
	while(a[i]!='\0') {
		if(a[i]=='(')
			push(a[i]);
		else if(a[i]>='a' && a[i]<='z')
			cout<<a[i];
		else if(a[i]=='+' || a[i]=='-' || a[i]=='*' || a[i]=='/' || a[i]=='^')
			push(a[i]);
		else if(a[i]==')') {
			while(s.top()!='(') {
				cout<<pop();
			}
			s.pop();
		}
		i++;
	}
}
int main() {
	// your code goes here
	int t;
	char a[401];
	cin>>t;
	while(t-- > 0) {
		cin>>a;
		intopost(a);
		cout<<endl;
	}
	return 0;
}

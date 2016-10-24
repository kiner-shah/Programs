/*
  KINER SHAH
  PROGRAM FOR CAESAR CIPHER
  BE COMPS B3, 1311113
*/
#include <stdio.h>
#include <string.h>
int main() {
    /* Enter your code here. Read input from STDIN. Print output to STDOUT */
    int inc,i; char a[100], b[100];
    clrscr();
    /* ENCRYPTION */
    printf("Enter plaintext and key: ");
    scanf("%s %d",a,&inc);
    for(i=0;i<strlen(a);i++) {
	if(a[i]>='a' && a[i]<='z') {
	    if(a[i]+inc > 'z') {
		if((inc-('z'-a[i]))%26 == 0)
		    a[i]='z';
		else
		    a[i]='a'+(inc-('z'-a[i]))%26-1;
	    }
	    else {
		a[i]=a[i]+inc;
	    }
	}
	else if(a[i]>='A' && a[i]<='Z') {
	    if(a[i]+inc > 'Z') {
		if((inc-('Z'-a[i]))%26 == 0)
		    a[i]='Z';
		else
		    a[i]='A'+(inc-('Z'-a[i]))%26-1;
	    }
	    else {
		a[i]=a[i]+inc;
	    }
	}
    }
    printf("Ciphertext is: %s\n\n",a);
    /* DECRYPTION */

    printf("Enter ciphertext recieved and key: ");
    scanf("%s %d",b,&inc);
    inc %= 26;
    for(i = 0; i < strlen(b); i++) {
	if(b[i] >= 'a' && b[i] <= 'z') {
		int val = b[i] - 'a';
		if(val - inc < 0) {
			val = 26 - (inc - val);
			b[i] = 'a' + val;
		}
		else {
			val -= inc;
			b[i] = 'a' + val;
		}
	}
	else if(b[i] >= 'A' && b[i] <= 'Z') {
		int val = b[i] - 'A';
		if(val - inc < 0) {
			val = 26 - (inc - val);
			b[i] = 'A' + val;
		}
		else {
			val -= inc;
			b[i] = 'A' + val;
		}
	}
    }
    printf("Plaintext retrieved is: %s",b);
    getch();
    return 0;
}

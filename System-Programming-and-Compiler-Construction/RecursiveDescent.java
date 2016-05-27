/*
Author: KINER SHAH 
Experiment : Recursive Descent Parser
Subject: System Programming and Compiler Construction
Date: 6th March 2016
*/
import java.util.*;
import java.io.*;
class RecursiveDescent {
    /* 
    E -> x+T
    T -> (E)
    T -> x  
    */
    public static int lookahead_ptr; //look-ahead pointer
    public static char input_buffer[]; //buffer for storing characters of string
    public static boolean eflag; //error flag
    public static void E() {
        try {
            if(lookahead_ptr == input_buffer.length) return;
            if(input_buffer[lookahead_ptr] == 'x') {
                lookahead_ptr++;
                if(input_buffer[lookahead_ptr] == '+') {
                    lookahead_ptr++;
                    T();
                    if(lookahead_ptr == input_buffer.length) return;
                }
            }
            else { eflag = true; return; }
        }
        catch(Exception e) {
            eflag = true;
            return;
        }
    }
    public static void T() {
        try {
            if(lookahead_ptr == input_buffer.length) return;
            if(input_buffer[lookahead_ptr] == 'x') {
                lookahead_ptr++; return;
            }
            else if(input_buffer[lookahead_ptr] == '(') {
                lookahead_ptr++;
                E();
                if(input_buffer[lookahead_ptr] == ')') {
                    lookahead_ptr++;
                    return;
                } 
            }
            else { eflag = true; return; }
        }
        catch(Exception e) {
            eflag = true;
            return;
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter a string: "); 
        String a = r.readLine();
        input_buffer = a.toCharArray();
        if(input_buffer.length <= 2) System.out.println("Error parsing string. String is not valid.");
        else {
            lookahead_ptr = 0;
            eflag = false;
            E();
            if(eflag) {
                System.out.println("Error parsing string. String is not valid.");
            }
            else {
                if(lookahead_ptr == input_buffer.length)
                    System.out.println("Parsing successfull. String is valid.");
                else
                    System.out.println("Error parsing string. String is not valid.");
            }
        }
    }
}
/*
OUTPUT
Enter a string: xxx
Error parsing string. String is not valid.
Enter a string: x+x+x
Error parsing string. String is not valid.
Enter a string: x+x
Parsing successfull. String is valid.
Enter a string: x+(x+x)
Parsing successfull. String is valid.
Enter a string: x+(x+(x+x))
Parsing successfull. String is valid.
Enter a string: x+(x+x)+x
Error parsing string. String is not valid.
Enter a string: x+(x+(x+x)
Error parsing string. String is not valid.
Enter a string: x+
Error parsing string. String is not valid.
Enter a string: x
Error parsing string. String is not valid.
*/

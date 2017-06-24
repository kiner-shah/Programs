/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalculatorClientServer;
import java.net.*;
import java.util.regex.*;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author Kiner Shah
 */
public class CalculatorServer {
    public static boolean checkExpressionValidity(String expr) {
        Pattern pat = Pattern.compile("([0-9]+[+-/*])+");
        Matcher m = pat.matcher(expr);
        if(m.find()) return true;
        else return false;
    }
    public static int priority(char c) {
        if(c == '/' || c == '*') return 2;
        else if(c == '-') return 1;
        else return 0;
    }
    public static String infixToPostfix(String b) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Character> l = new ArrayList();
        for(int i = 0; i < b.length(); i++) {
            char d = b.charAt(i);
            if(d >= '0' && d <= '9') sb.append(d);
            else if(d == '+' || d == '-' || d == '*' || d == '/') {
                while(l.size() > 0 && priority(d) < priority(l.get(l.size() - 1))) {
                    sb.append(l.get(l.size() - 1));
                    char cc = l.remove(l.size() - 1);
                }
                l.add(d);
            }
        }
        for(int i = l.size() - 1; i >= 0; i--) sb.append(l.get(i));
        return sb.toString();
    }
    public static double evalPostfix(String p) {
        ArrayList<Double> l = new ArrayList();
        System.out.println("Steps:");
        for(int i = 0; i < p.length(); i++) {
            char d = p.charAt(i);
            if(d >= '0' && d <= '9') l.add((double) d - '0');
            else {
                double op1 = l.remove(l.size() - 1), res = 0.0;
                double op2 = l.remove(l.size() - 1);
                switch(d) {
                    case '+': res = op1 + op2; System.out.println(op1 + " + " + op2); break;
                    case '-': res = op2 - op1; System.out.println(op2 + " - " + op1); break;
                    case '*': res = op1 * op2; System.out.println(op1 + " * " + op2); break;
                    case '/': res = op2 / op1; System.out.println(op2 + " / " + op1); break;
                }
                l.add(res);
            }
        }
        return l.remove(l.size() - 1);
    }
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(3000);
            Socket socket = ss.accept();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            System.out.println("Accepted Connection from Client");
            dos.writeUTF(String.valueOf(true));
            dos.flush();
            String expr;
            while((expr = dis.readUTF()) != null) {
                if(expr.trim().isEmpty()) { System.out.println("Received: " + expr.trim().isEmpty()); dos.writeUTF("NaN"); dos.flush(); continue; }
                System.out.println("Received: " + expr);
                boolean cev = checkExpressionValidity(expr);
                if(!cev) { System.out.println("Expression validity: false"); dos.writeUTF("Wrong expression format entered"); dos.flush(); continue; }
                String postfix = infixToPostfix(expr);
                System.out.println("Transform obtained: " + postfix);
                double res = evalPostfix(postfix);
                dos.writeUTF(String.valueOf(res)); dos.flush();
                System.out.println("Result sent to client: " + res);
            }
            if(expr == null) { dos.writeUTF("Null Input Provided"); dos.flush(); }
        }
        catch(UnknownHostException e) { System.out.println(e); }
        catch(IOException e) { socket.close(); System.out.println("Connection terminated"); }
    }
}

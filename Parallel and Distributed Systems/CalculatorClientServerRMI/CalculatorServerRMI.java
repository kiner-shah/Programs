/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalculatorClientServerRMI;

/**
 *
 * @author Kiner Shah
 */
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class CalculatorServerRMI implements CalculatorInterfaceRMI {
    //public String sayHello() { return "Hello World!"; }
    public boolean checkExpressionValidity(String expr) {
        Pattern pat = Pattern.compile("([0-9]+[+-/*])+");
        Matcher m = pat.matcher(expr);
        return m.find();
    }
    public int priority(char c) {
        if(c == '/' || c == '*') return 2;
        else if(c == '-') return 1;
        else return 0;
    }
    public String infixToPostfix(String b) {
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
    public double evalPostfix(String p) {
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
            CalculatorServerRMI server = new CalculatorServerRMI();
            CalculatorInterfaceRMI stub = (CalculatorInterfaceRMI) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.createRegistry(6666);
            registry.bind("CalculatorInterfaceRMI", stub);
            System.err.println("Server is running");
        }
        catch(Exception e) { 
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace(); 
        }
    }
}

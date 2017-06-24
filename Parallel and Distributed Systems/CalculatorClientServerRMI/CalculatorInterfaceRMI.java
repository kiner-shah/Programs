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
import java.rmi.*;
public interface CalculatorInterfaceRMI extends Remote {
    //String sayHello() throws RemoteException;
    boolean checkExpressionValidity(String expr) throws RemoteException;
    int priority(char c) throws RemoteException;
    String infixToPostfix(String b) throws RemoteException;
    double evalPostfix(String p) throws RemoteException;
}

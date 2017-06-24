package CalculatorClientServerRMI;

/**
 *
 * @author Kiner Shah
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.*;
public class CalculatorClientRMI {
    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 6666);
            //String registryEntries[] = registry.list();
            //for(String re : registryEntries) System.out.println(re);
            CalculatorInterfaceRMI stub = (CalculatorInterfaceRMI) registry.lookup("CalculatorInterfaceRMI");
            //String resp = stub.sayHello();
            //System.out.println(resp);
            BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter an expression: ");
            String expr = r.readLine();
            if(expr.trim().isEmpty()) System.err.println("Empty expression entered");
            else {
                boolean cev = stub.checkExpressionValidity(expr);
                if(!cev) System.err.println("Wrong expression format entered");
                String postfix = stub.infixToPostfix(expr);
                double res = stub.evalPostfix(postfix);
                System.out.println("Result obtained is: " + res);
            }
        }
        catch(Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

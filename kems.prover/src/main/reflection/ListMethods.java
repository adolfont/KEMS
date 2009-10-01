package main.reflection;
import java.lang.reflect.Method;

public class ListMethods {
    
	public static void main(String[] args) 
    {
        if(args.length < 1)
        {        
            System.out.println("Usage : ListMethods <classname>");
            System.exit(-1);
        }
            
        String classname = args[0];        
                
        try 
        {
           Class<?> c = Class.forName(classname);
           Method m[] = c.getDeclaredMethods();
           
           System.out.println("Number of methods for " + classname + " = " + m.length);
           System.out.println();
           
           for (int i = 0; i < m.length; i++)
           {           
                   // Print out each of the methods signatures
                   System.out.println(m[i].toString());
           }
        }
        catch (Exception e) {
           System.out.println("Exception : " + e);
        }
    }
} 

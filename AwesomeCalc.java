/*        CSC3410 ‐Spring 2015
 *        Sidney Seay ‐sseay5@student.gsu.edu
 *        DateDue: 03‐26‐2015
 *        Assignment: 4, Calculator Code
 *        File(s): AwesomeCalc.java
 /*
  * 
  */
package awesomeCalc;


/*
 AwesomeCalc class
*/

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.script.*;
import awesomeCalc.Stack;
import awesomeCalc.Node;
import awesomeCalc.StackInterface;

/*
 * 
 * This program create a simple calculator by reading in an input
 * file that contain arithmetic expression(s) on each line. 
 * The program evaluate the expression and print the result.
 * The input expression file is received by the program as a 
 * command line argument
 *
 */
public class AwesomeCalc {
	  
	// constructor
	public AwesomeCalc() {
				
	}
	
   /*
    * M A I N
   */
   public static void main(String[] args){
	   
	   String fileName = "";
	   String line = null;
	   Scanner input = new Scanner(System.in);
	   
	   System.out.println("Enter the name of the Arithmetic Expression file with directory location: ");
	   input = new Scanner(System.in);
	   fileName = input.nextLine();
	   
	   try {
		   // input arithmetic expression file
		   // input file format: operands, operators are separated by spaces
		   File fExpression = new File(fileName);
		   // create buffer reader for the input file
		   BufferedReader buffReader = new BufferedReader(new FileReader(fExpression));

		   while ((line = buffReader.readLine()) != null) {
			        System.out.println("Input arithmetic expression: " + line);
			        // call method  PostfixConvert
	                PostfixConverter.convertInfixExpression(line);
            }
        }
	   catch (Exception exp) {
		   System.out.println("Error occurred while reading input file: " + exp.getMessage());
	    }
   }
   
 /*
 *    Method PostfixConverter parameter arithmetic expression
 */
   public static class PostfixConverter {
	    public static String convertInfixExpression(String infixExpression) {

            String operator = "";
            String popValue = "";
            String saveExpression = "";
            
	        StringTokenizer tokens = new StringTokenizer(infixExpression,"()+-*/",true);

	        int iLength = 999;
	        int tokenCounter = 1;
            boolean validExpression = false;
            
	        final ScriptEngineManager engineManager = new ScriptEngineManager();
            final ScriptEngine engine = engineManager.getEngineByName("JavaScript");
            
	        // initialize to empty string
	        String[] tokenList = new String[iLength];
	        for(int j = 0; j < iLength; j++){
	        	tokenList[j] = "";
	        }	        
            iLength = 0;
	        
	        // set string array size from arithmetic expression length
            // load string array with arithmetic expression
	        while(tokens.hasMoreTokens()){
	        	tokenList[iLength] = tokens.nextToken();
	            iLength++;
	        }
	        //*******************************************************************
	        // See class Stack.java
	        // create instance of Stack.java method Stack<String>
	        //*******************************************************************
	        final Stack<String> operatorStack = new Stack<String> ();
	        
	        StringBuilder result = new StringBuilder();
	        //*******************************************************************	        
	        // evaluate arithmetic expression
	        //*******************************************************************	        
	        try {
		        for (final String token : tokenList) {
	    	        try {
	    	            // check for valid arithmetic expression
	    	        	engine.eval(infixExpression); // validate arithmetic expression
    	        		saveExpression = infixExpression;	    	        	
	    	        	validExpression = true;
	    	        } catch (Exception exp3) {
	    	        	if (saveExpression != infixExpression){
		    	        	System.out.println("Arithmetic expression is not valid: " + infixExpression);
	    	        		saveExpression = infixExpression;
	    	        	}
                        validExpression = false;
	    	        }		        				        	
		        	if ((token.isEmpty()) || (tokenCounter > iLength)) {
		        		break;
		        	}
		        	else if (!validExpression) {
		        		continue;
		        	}
		        	else if (token.equals("(")) {  // check for (
		            	operatorStack.push(token);
		            } else if (token.equals(")")) {   // check for )
		                while (!"(".equals(operator = operatorStack.pop())) {
		                	// save operand in variable result
		                    append(result, operator);
		                }
		            } else if (Operator.isOperator(token)) { // check for operator + - * /
		                if (!operatorStack.isEmpty() && Operator.isPrecedent(token, operatorStack.top())) {
		                	// get operator from Stack in order of priority
		                    append(result, (String)operatorStack.pop());
		                }
		                // load operator to Stack
		                operatorStack.push(token);
		            } else {
		            	// load operand to variable result
		                append(result, token);
		            }
		        	++tokenCounter;
		        }
	        }
	        catch(Exception exp3) {
	        	System.out.println("Operators: Error occurred in method convertInfixExpression: " + exp3.getMessage());
	        }
	        //*******************************************************************
	        try {
		        while ((!operatorStack.isEmpty()) || (operatorStack.value != null)) {
		        	// get value from Stack
		        	popValue = (String)operatorStack.pop();
		        	// null value indicate end of Stack
		        	if (popValue == null) {
		        		break;
		        	}
		        	// value from Stack
		            append(result, popValue);
		        }	        	
	        }
	        catch(Exception exp4) {
	        	System.out.println("OperatorStack: Error occurred in method convertInfixExpression: " + exp4.getMessage());
	        }

	        //*******************************************************************
	        // perform calculation
	        //*******************************************************************
	        String code = result.toString();
	        // utilize Java math engine
	        try {
	            // print infix arithmetic expression
                System.out.println("Infix converted expression: " + code);
	        	System.out.println("Answer: " + engine.eval(infixExpression)); // prints arithmetic expression result
	        } catch (Exception exp3) {
	        	//System.out.println("Arithmetic expression is not valid: " + infixExpression);
	            //exp3.getMessage();
	        }
	        
	        return result.toString();
	    }
       /*
       *  Stack method(s) used to load and get value 
      */
	    public static void append(final StringBuilder builder, String value) {
	        builder.append(' ').append(value);
	    }

	    enum Operator {
	        PLUS('+', 1, 2), MINUS('-', 1, 2), MULTIPLY('*', 3, 4), DIVIDE('/', 3, 4), EXPONENT('^', 6, 5);

	        private final char value;
	        private final int icpValue;
	        private final int ispValue;

	        Operator(char value, int icpValue, int ispValue) {
	            this.value = value;
	            this.icpValue = icpValue;
	            this.ispValue = ispValue;
	        }

	        private final static Map<String, Operator> operatorMap = new HashMap();

	        static {
	            for (Operator operator : Operator.values()) {
	                operatorMap.put(String.valueOf(operator.value), operator);
	            }
	        }

	        private static Operator getOperator(final String operator) {
	            return operatorMap.get(operator);
	        }

	        static boolean isOperator(final String operator) {
	            return getOperator(operator) != null;
	        }

	        private static int getIcpValue(final String value) {
	            final Operator operator = getOperator(value);
	            return operator == null ? 0 : operator.icpValue;
	        }

	        private static int getIspValue(final String value) {
	            final Operator operator = getOperator(value);
	            return operator == null ? 0 : operator.ispValue;
	        }

	        static boolean isPrecedent(final String firstToken, final String secondToken) {
	            return getIcpValue(firstToken) < getIspValue(secondToken);
	        }
	    }  // end enum
	    
   }  // end PostfixConverter
   
}   // end class AwesomeCalc
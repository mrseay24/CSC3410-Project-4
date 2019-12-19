/*        CSC3410 ‐Spring 2015
 *        Sidney Seay ‐sseay5@student.gsu.edu
 *        DateDue: 03‐26‐2015
 *        Assignment: 4, Calculator Code
 *        File(s): Stack.java
 /*
  * 
  */
package awesomeCalc;

/*
  Stack class
*/

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

 /*
 * Stack class implement method from
 * class StackIntercace 
 */
public class Stack<T> implements StackInterface<T>  {

     // define top position of Node
	 Node<String> top;
	 // create Map of Node item
	 public Map<T, Integer> countMap = new HashMap<T, Integer> ();    
     public Map<Integer, Set<T>> stackMap = new HashMap<Integer, Set<T>> ();
     
     // define variable
     private int maxCount = 0;
     public String value = "";     

     public boolean isEmpty() {
    	 
    	 return false;
     }

     /*
      * Push item unto Node 
      * @see method StackInterface.push(java.lang.Object)
      */
     public void push(T o) {
    	 Integer c = countMap.get(o);
    	 if (c == null) {
    		 countMap.put(o,  c = 1);
    	 }
    	 else {
    		 countMap.put(o,  ++c);
    	 }
    	 Set<T> set = stackMap.get(c);
    	 if (set == null) {
    		 stackMap.put(c,  set = new LinkedHashSet<T>());
    	 }
   		 set.add(o);
    	 if (c > maxCount) {
    		 maxCount = c;
    	 }
     }
     
     /*
      * Get item from top Node
      * @see method StackInterface.pop()
      */
     public T pop() {
    	 if (maxCount == 0) {
    		 return null;
    	 }
    	 Set<T> set = stackMap.get(maxCount);
    	 T o = set.iterator().next();
    	 set.remove(o);
    	 if (maxCount == 1) {
    		 countMap.remove(o);
    	 }
    	 if (set.size() == 0) {
             stackMap.remove(maxCount);
             --maxCount;
    	 }
    	 return o;
   	 }
     
     /*
      * Check maxCount - number of item in Node
      */
     public T top() {
    	 if (maxCount == 0) {
    		 return null;
    	 }
    	 return stackMap.get(maxCount).iterator().next();
     }     

     /*
      * Clear Node list
      * @see method StackInterface.clear()
      */
     public void clear() {
    	 
     }
}

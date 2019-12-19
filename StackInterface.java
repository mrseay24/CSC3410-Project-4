/*        CSC3410 ‐Spring 2015
 *        Sidney Seay ‐sseay5@student.gsu.edu
 *        DateDue: 03‐26‐2015
 *        Assignment: 4, Calculator Code
 *        File(s): StackInterface.java
 /*
/*
 * 
 */
package awesomeCalc;

/*
  StackInterface class
*/

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

 public interface StackInterface<T> {

    public boolean isEmpty();
    public T pop() throws java.util.EmptyStackException;
    public void push(T n);
    public void clear();

}

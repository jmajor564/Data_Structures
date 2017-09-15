/* File: Proj02_BST.java
 * Class: Computer Science 345
 * Author: John Major
 * Purpose: This program takes in a file of commands from the command line.
 *          it reads the file and then runs each of the following commands to
 *          create or modify a BST. THe folwing methods are in place and can be
 *          used: insert, search, delete, debug, preOrder, inOrder, and count.
 */

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class Proj02_BST
{
    public static void main(String[] args)
    {
        if (args.length == 0)     //checks if arg[0] exists. If not then no file was inputted
        {
            System.err.println("Error, no file was inputted");
            return;              
        }
        String fileName = args[0];              //pulls out the file to be read and stores it in fileName
        File instructions = new File(fileName); //create a file object that will be used by Scanner
        if (instructions.length() == 0)         //checks if file has any data. If not then it returns an error
        {
            System.err.println("Error, the file could not be found");
            return;
        }
        try {
            Scanner in = new Scanner(instructions);    //create a scanner object to read from the file
            Node root = null;                          //creates root to hold the first node
            String line = "";      //holds each line of the command
            String command = "";   //a string object that holds the command
            String integer = "";   //a string object that holds the number (if one is entered);
            int count = 0;
            while (in.hasNextLine())            //will loop until the end of the file
            {  
                line = in.nextLine();
                int i = 0;
                boolean num = false;            //determines if reading the command or number
                if (line.equals(""))            //checks for blank lines
                    continue;
                while (i < line.length())       //will read the whole line
                {
                    if (line.charAt(i) == 32)   //if a space is found then it signals that the rest of data is to be stored in the integer string
                    {
                        num = true;             //sets the boolean num to true
                        i++;                    //increments i
                    }
                    else if (num == true)       //if integer then add the charAt(i) to end of integer
                    {
                        integer += line.charAt(i);
                        i++;
                    }
                    else                        //if not an int or space then add char to the command section
                    {
                        command += line.charAt(i);
                        i++;                      
                    }
                } 
                //This is where I will take the reulting line and call the required method
                if (command.equals("insert"))   //if command is equal to "insert" then run insert method
                {
                    int number = Integer.parseInt(integer);   //parse the string to an int
                    root = insert(root, number);              //run insert method
                    command = "";                             //reset command
                    integer = "";                             //reset integer
                    continue;                                 //read next line
                }
                else if (command.equals("search"))  //if command is equal to "search" then run search method
                {
                    int number = Integer.parseInt(integer);   //parse string to an int
                    search(root, number);                     //run the search method
                    command = "";
                    integer = "";
                    continue;
                }
                else if (command.equals("debug")) //if command is equal to "debug" then run debug method
                {
                    debug(root, integer);         //call debug
                    command = "";
                    integer = "";
                    continue;
                }            
                else if (command.equals("delete")) //if command is equal to "delete" then run delete method
                {
                    int number = Integer.parseInt(integer);  //parse string integer to a int
                    root = delete(root, number);             //call delete
                    command = "";
                    integer = "";
                    continue;
                }             
                else if (command.equals("count")) //if command is equal to "count" then run count method
                {
                    count(root);    //call count
                    command = "";
                    integer = "";
                    continue;
                }
                else if (command.equals("preOrder"))  //if command is equal to "preOrder" then run preOrder method
                {
                    preOrder(root, root);     //call preOrder
                    System.out.println();     //print newline
                    command = "";
                    integer = "";
                    continue;
                }
                else if (command.equals("inOrder")) //if command is equal to "inOrder" then run inOrder method
                {
                    inOrder(root);          //call inOrder
                    System.out.println();   //print new line
                    command = "";
                    integer = "";
                    continue;
                } 
                else
                {
                    System.err.println("Error, not a valid command.");  //catches invalid commands, even though its not necessary i decided to add it just for good habits
                    command = "";
                    integer = "";
                    continue;                   
                }
            }
            
        } catch (IOException e) {   //catchs an IOExecption 
        }
        return;
    }
    
     /* Method: insert
      * Purpose:
      * This method takes in a Node object representing the root and an int which is the new node's value that  
      * needs to be added. It iterates through the tree until it find the proper leaf node to add the new value.
      * If the value you are trying to enter already exists then it prints an error and returns the original tree.
      * This method returns a Node that is equal to the root.
      * 
      */
     public static Node insert(Node root, int newValue)
     {
         if (root == null)      //if tree is empty
         {
             root = new Node(newValue);   //make new node and set it as root
             return root;
         }         
                  
         Node tempNode = root;   //make a temp pointer to iterate through the tree
         
         while (tempNode != null)
         {
             if (tempNode.getData() == newValue)   //base case if number already exists
             {
                 System.err.println("ERROR: Could not insert " + newValue + " because it is already in the tree.");
                 return root;
             }
             else if (tempNode.getLeftChild() == null && tempNode.getRightChild() == null)   //base case if both children are null;
             {
                 break;
             }
             else if (newValue < tempNode.getData())  //if newValue is less than the tempNode node then go left
             {
                 if (tempNode.getLeftChild() == null)
                     break;
                 else
                     tempNode = tempNode.getLeftChild();
             }
             else                                        //if newValue is greater than the tempNode then go right
             {
                 if (tempNode.getRightChild() == null)
                     break;
                 else
                     tempNode = tempNode.getRightChild();             
             } 
         }

         //here we have found the last node where the new number should be inputted
         if (newValue < tempNode.getData())      //checks if it should be inputted as left node  
         {
             Node newNode = new Node(newValue);
             tempNode.setLeftChild(newNode);
         }
         else                                    //checks if it should be inputted as right node
         {
             Node newNode = new Node(newValue);
             tempNode.setRightChild(newNode); 
         }
         return root;
     }
     
     
     /* Method: search
      * Purpose:
      * This method takes a Node object and an int integer as input. It will recursivly iterate through the tree
      * until it eaither finds the node it is looking for (the integer input) or it reaches a null value of a leaf node.
      * It will then print out wheather it found the node it was looking for or not. It has no return value.
      */ 
     public static void search(Node node, int integer)
     {
         if (node == null)   //if we reached the end of the tree and the node was not found
         {
             System.out.println(" The node " + integer + " was not found.");  //print that ti wasn't found
             return;
         }
         else if (integer == node.getData())   //if we found the integer
         {
             System.out.println("The node " + integer + " exists in the tree");  //print that the node exists
             return;
         }
         else if (integer < node.getData())    //if integer is less than the current nodes value, iterate to the left child
         {
             search(node.getLeftChild(), integer);
             return;
         }
         else                                  //if the integer is greater than the current node, then iterate to the right child
         {
             search(node.getRightChild(), integer);
             return;             
         }
     }
     
     
     /* Method: delete
      * Purpose:
      * This method takes a Node object (root) and an int as input. It then will search through the list until it 
      * finds that value or reaches the end of the list. If it finds the value it will delete the node. It will then
      * return the root node that contains the tree without the deleted node.
      */ 
     public static Node delete(Node root, int number)
     {
         Node prev = root;   //tracks the Node before curr
         Node curr = root;   //this is the tracker Node
         Node tempNode = null;      //this will be used for if we have to delete 2 nodes
         boolean wentRight = false; //determines if we went right or left
         while (curr != null)       //iterates until we get to the end (bottom) of tree
         {
             if (curr.getData() == number)   //if you found the node to delete
             {
                 if (prev == curr)     //if prev == curr then you are still on the root (therefore this is used to delete the root)
                 {
                     if (root.getLeftChild() == null && root.getRightChild() == null)   //if root has one node
                     {
                         root = null;   //set root to null
                         return root;
                     }
                     else if (root.getLeftChild() == null && root.getRightChild() != null)  //if root has a right child
                     {
                         root = root.getRightChild();   //set root equal to the right child
                         return root;
                     }
                     else if (root.getLeftChild() != null && root.getRightChild() == null)  //if root has a left child
                     {
                         root = root.getLeftChild();   //set root equal to the left child 
                         return root;
                     }
                     else    //if root has two children
                     {
                         Node tempNode2 = root.getLeftChild();  //used to make the parent node of tempNode
                         tempNode = root.getLeftChild();        //sent tempNode equal to the left child of root
                         if (tempNode.getRightChild() != null)  //if tempNode has a right child
                         {
                             while (tempNode.getRightChild() != null) //keep move to the right child until you find the last right child in the list (next largest node)
                             {
                                 tempNode2 = tempNode;
                                 tempNode = tempNode.getRightChild();
                             }
                             root.setData(tempNode.getData());    //set roots data equal to the largest node found
                             if (tempNode.getLeftChild() == null) //if the predicessor has no left child then just sent its parent's right child to null
                                 tempNode2.setRightChild(null);
                             else                                 //if the node taken has a left child
                                 tempNode2.setRightChild(tempNode.getLeftChild());   //sets the parents right child equal to the left child
                             return root;
                         }
                         else   //if the left child of root has no right childern
                         {
                             root.setData(tempNode.getData());     //set root equal to that node
                             root.setLeftChild(tempNode.getLeftChild()); //set roots left child equal to tempNode's left child
                             return root;
                         }
                     }
                 }
                 else if (curr.getLeftChild() == null && curr.getRightChild() == null)   //if the value to delete is a leaf
                 {
                     if (wentRight == true)                        //if a right node of the parent 
                         prev.setRightChild(null);                 //set parents right child to null
                     else                                          //if left child child
                         prev.setLeftChild(null);                  //set parents left node equal to null
                     return root;
                 }
                 else if (curr.getLeftChild() != null && curr.getRightChild() == null)  //if the node to delete has a left child
                 {
                     if (wentRight == true)                        //if a right node of the parent            
                         prev.setRightChild(curr.getLeftChild());  //set parents right node equal to node to deletes left child
                     else                                          //if a left node of the parent
                         prev.setLeftChild(curr.getLeftChild());   //set parents left child equal to node to deletes left child
                     return root;
                 }
                 else if (curr.getLeftChild() == null && curr.getRightChild() != null)  //if the node has a right child
                 {
                     if (wentRight == true)  //if node is parents right child
                         prev.setRightChild(curr.getRightChild());             //set parent's right child node equal to deleted nodes right child
                     else                    //if node is parents left child 
                         prev.setLeftChild(curr.getRightChild());              //set parent's left child node equal to deleted nodes right child
                     return root;
                 }
                 else   //if node to be delete contains two nodes
                 {
                     tempNode = curr.getLeftChild();       //pointer node that starts at node to be deleted's left child 
                     Node tempNode2 = curr.getLeftChild();  //pointer to the parent of the node that tempNode is on
                     while (tempNode.getRightChild() != null)   //while tempNode has a right child continue
                     {
                         tempNode2 = tempNode;                  //tempNode2 is set to tempNode's parent
                         tempNode = tempNode.getRightChild();   //tempNode is set to its right child
                     }
                     
                     curr.setData(tempNode.getData());  //replace the node to be deleted's data with new nodes data
                     if (tempNode == tempNode2)         //if there were no right nodes
                     {
                         if (curr.getLeftChild() == null)     //if tempNode has no left child
                             curr.setLeftChild(null);         //set the curr node's left child to null
                         else                                 //if tempNode has a left child
                             curr.setLeftChild(tempNode.getLeftChild());  //set currs left child to tempNodes left child
                         return root;
                     }
                     else   //if it iterated at least once
                     {
                         if (tempNode.getLeftChild() == null)   //if tempNode has no left child
                             tempNode2.setRightChild(null);     //set tempNode2's left right child to null.
                         else                                   //if tempNode has left child
                             tempNode2.setRightChild(tempNode.getLeftChild());  //place the left child into the right child of tempNode2
                         return root;
                     }
                 }
             }
             else if (number > curr.getData())   //if number to delete is greater than current node then go to right node
             {
                 prev = curr;
                 curr = curr.getRightChild();
                 wentRight = true;
                 continue;
             }
             else                                //if number ot delete is less than current node, then go left
             {
                 prev = curr;
                 curr = curr.getLeftChild(); 
                 wentRight = false;
                 continue;
             }
         }
         System.err.println("ERROR: Could not delete " + number + " because it was not in the tree.");  //if node was not found print error
         return root;
     }
     
     /* Method: count, countRecurse
      * Purpose: 
      * The only input for count is the root of a BST. It then takes the root and inputs it into countRecurse.
      * CountRecurse takes the node and recursivly adds up each node in the tree. It add 1 plus the return values of 
      * countRecurse(leftChild) and countRecurse(rightChild). It then reutrns the final count to count, which prints the .
      * number of nodes counted.
      */ 
     
     public static void count(Node node)
     {
         int numberOfNodes = countRecurse(node);  //numberOfNodes is equal to the return value of countRecursive
         System.out.println("The tree has " +numberOfNodes+ " nodes.");  //print the total count
         return;
     }
     
     public static int countRecurse(Node node)
     {
         if (node == null)  //if a leaf then return 0;
             return 0;
         else
             return 1 + countRecurse(node.getRightChild()) + countRecurse(node.getLeftChild());  //return 1 + recursion of left child + recursion of right child
     }
     
     /* Method: preOrder
      * Purpose: 
      * This method recursivly prints the preOrder of the BST. This the node, then print left child, then print
      * right child.
      */ 
     public static void preOrder(Node node, Node root)
     {
         if (node == null)   //if null don't print
             return;
         System.out.print(node.getData() + " ");  //print node's value
         preOrder(node.getLeftChild(), root);     //call method on left child
         preOrder(node.getRightChild(), root);    //call method on right child
         return;
         
     }
     /* Method: inOrder
      * Purpose: 
      * This method recursivly prints the inOrder of the BST. This is print left child, print node, then print
      * right child.
      */      
     public static void inOrder(Node node)
     {
         if (node == null)    //if node is null then don't print and return
             return;
         inOrder(node.getLeftChild());           //call method  on left child   
         System.out.print(node.getData() + " "); //print node's data
         inOrder(node.getRightChild());          //call function on right child
         return;
     }
     
     /* Method: debug
      * Purpose: 
      * This takes the BST and uses it to create a digraph. The inputs are the root Node and the name of
      * the file as a string. it calls two other function (debugGetVals and debugGetRel). It then uses a bufferedWriter
      * object to write the digraph into the file name that was inputted.
      * 
      */ 
     
     public static void debug(Node root, String filename)
     {
         try {
         BufferedWriter out = new BufferedWriter(new FileWriter(filename));  //create BufferedWriter object & FileWriter object
         out.write("digraph \n{\n");            //write start of diagrpah into file
         String values = debugGetVals(root);    //get node variable by calling debugGetVals
         out.write(values + "\n");              //write all variable into file
         String relations = debugGetRel(root);  //get node relations as a string by calling debugGetRel
         out.write(relations);                  //write node relation into a file
         out.write("}");     
         out.close();   //close file
         return; 
         } catch (java.io.IOException e) {   //catch an IOExecption if filename is incorrect
         }
     }
     
      /*Method: debugGetVals
       * Purpose: 
       * Takes the root as an input and recursivly finds all of the variables an adds them together by adding 
       * current nodes data plus newline + call method on left child + call method on right child. THis will return
       * all of the variables on seperate lines. It returns the result as a string
       */ 
      public static String debugGetVals(Node node)
      {
          if (node == null)
              return "";
          return node.getData() + ";\n" + debugGetVals(node.getLeftChild()) + debugGetVals(node.getRightChild()); 
      }
     
      /* Method: debugGetRel
       * Purpose:
       * This method returns the relations for the dot graph. It iterates through the tree and returns the relationships
       * in the form of a string.
       * 
       */ 
      public static String debugGetRel(Node node)
      {
          if (node == null)  //if null return "" (empty string);
              return "";
          else if (node.getLeftChild() == null && node.getRightChild() == null)  //if a leaf then no relations, therfore return empty string
              return "";
          else if (node.getLeftChild() != null && node.getRightChild() == null)  //if node has left child then return node.data + arrow + left child's data + label left and newline, then recursive call on left child
          {
              return node.getData()+  " -> " + node.getLeftChild().getData() + "[label=\"left\"]; " + "\n" + 
                  debugGetRel(node.getLeftChild());
          }
          else if (node.getLeftChild() == null && node.getRightChild() != null) //if node has right child then return node.data + arrow + right child's data + label right and newline, then recursive call on right child
          {
              return node.getData()+  " -> " + node.getRightChild().getData() + "[label=\"right\"]; " + "\n" + 
                  debugGetRel(node.getRightChild());
          }
          else  //if node has two children create the realtions for both of them then add the recursive call for both left and right children
          {
              return node.getData()+  " -> " + node.getLeftChild().getData() + "[label=\"left\"]; " + node.getData() + " -> " + node.getRightChild().getData() + "[label=\"right\"]; " + "\n" +
                  debugGetRel(node.getLeftChild())  + debugGetRel(node.getRightChild());
          }
      }
}

class Node
{
    private int data;    //holds the node's data
    
    private Node leftChild;  //holds the node's left child
    
    private Node rightChild;  //holds the node's right child
    
    public Node(int newData)  //constructor to set up a new node given an int
    {
        data = newData;
        leftChild = null;
        rightChild = null;
    }
    
    public Node getLeftChild()   //return the left child
    {
        return leftChild; 
    }
    
    public Node getRightChild()  //return the right child
    {
        return rightChild;       
    }
    
    public void setRightChild(Node newNode)  //sets the right child of the current node given a Node object input
    {
        rightChild = newNode;
    } 
    
    public void setLeftChild(Node newNode) //sets the left child of the current node given a Node object input
    {
        leftChild = newNode; 
    } 

    public int getData()   //return the data variable
    {
        return data;    
    }
    
    public void setData(int newData)  //sets the data variable to the newData integer that was entered 
    {
        data = newData;
        return;
    }
}
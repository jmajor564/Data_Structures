/* File: Proj03_AVL.java
 * Class: Computer Science 345
 * Author: John Major
 * Purpose: This program takes in a file of commands from the command line.
 *          it reads the file and then runs each of the following commands to
 *          create or modify a AVL tree. The folwing methods are in place and 
 *          can be used: insert, search, delete, debug, preOrder, inOrder, and 
 *          count.
 */

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
public class Proj03_AVL
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
            AVLTree avlArray[] = new AVLTree[4];  //creates root to hold the first node
            avlArray[0] = new AVLTree();
            avlArray[1] = new AVLTree();
            avlArray[2] = new AVLTree();
            avlArray[3] = new AVLTree();
            String line = "";      //holds each line of the command
            String command = "";   //a string object that holds the command
            String integer = "";   //a string object that holds the number (if one is entered);
            char treeNum = ' ';
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
                    if (i == 0)
                    {
                        treeNum = line.charAt(i);
                        if (treeNum < 48 || treeNum > 51)
                        {
                            System.err.println("Error, '" +line+ "' is an invalid command");
                            
                        }
                        i++;
                    }
                    else if (line.charAt(i) == 32)   //if a space is found then it signals that the rest of data is to be stored in the integer string
                    {
                        if (i > 1)
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
                } ;
                int treeNumber = treeNum - 48;
                if (command.equals("insert"))   //if command is equal to "insert" then run insert method
                {
                    int number = Integer.parseInt(integer);   //parse the string to an int
                    avlArray[treeNumber].insert(number);      //run insert method
                    command = "";                             //reset command
                    integer = "";                             //reset integer
                    continue;                                 //read next line
                }
                else if (command.equals("search"))  //if command is equal to "search" then run search method
                {
                    int number = Integer.parseInt(integer);   //parse string to an int
                    avlArray[treeNumber].search(number);      //run the search method
                    command = "";
                    integer = "";
                    continue;
                }
                else if (command.equals("debug")) //if command is equal to "debug" then run debug method
                {
                    avlArray[treeNumber].debug(integer);         //call debug
                    command = "";
                    integer = "";
                    continue;
                }            
                else if (command.equals("delete")) //if command is equal to "delete" then run delete method
                {
                    int number = Integer.parseInt(integer);  //parse string integer to a int
                    avlArray[treeNumber].delete(number);             //call delete
                    command = "";
                    integer = "";
                    continue;
                }             
                else if (command.equals("preOrder"))  //if command is equal to "preOrder" then run preOrder method
                {
                    avlArray[treeNumber].print_preOrder();     //call preOrder
                    System.out.println();                    //print newline
                    command = "";
                    integer = "";
                    continue;
                }
                else if (command.equals("inOrder")) //if command is equal to "inOrder" then run inOrder method
                {
                    avlArray[treeNumber].print_inOrder();          //call inOrder
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
}
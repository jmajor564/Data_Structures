/* Program: UnsortedArray
 * Author: John Major
 * Date: 4/14/2017
 * Purpose: The purpose of this program is to create a unsorted array that can perform the following
 *          operations: Insert, Delete, Search, getKeys, getSuccessor. It does so by using 
 *          an arraylist structure to store the values.
 */

import java.util.ArrayList;

public class UnsortedArray implements Proj04Dictionary
{
    private ArrayList<ArrayNode> list;   //an arraylist object that will hold all of the values
    public UnsortedArray()               //a constructor that initalizes the array
    {
        list = new ArrayList<ArrayNode>();
    }
    
    // insert(int,String)
    //
    // This function inserts a new record into the dictionary.
    // Any key is allowed.  However, the String must always be non-null.
    //
    // This function throws IllegalArgumentException if either of the
    // following are true:
    //     - key already exists in the data structure
    //     - String is null
    public void insert(int key, String data) throws IllegalArgumentException
    {
        if (data == null)                               //if data is null (string == null) then throw IAE
            throw new IllegalArgumentException();
        ArrayNode newNode = new ArrayNode(key, data);   //create new node with the proper data
        int num = list.size();
        for (int i = 0; i < num; i++)                   //find next availabile slot
        {
            if (key == list.get(i).integer)             //if the key exists throw an exception
                throw new IllegalArgumentException();   
            else
                continue;                               //else continue
        }
        list.add(newNode);       //if key doesn't already eist then add to end of list
    }
    
    // delete(int)
    //
    // This function deletes the key from the dictionary.
    //
    // This function throws IllegalArgumentException if:
    //     - the key does not exist in the data structure    
    public void delete(int key) throws IllegalArgumentException
    {
        int num = list.size();                 //iterate through the list and look for key
        for (int i = 0; i < num; i++)
        {
            if (key == list.get(i).integer)    //if key is found
            {
                 list.remove(i);               //remove the key
                 return;
            }
            else
                continue;
        }
        throw new IllegalArgumentException();    //if key is not found then throw IAE
    }
    
    
    // String search(int)
    //
    // This function searches the data structure for the key.  If the key
    // is found, then this returns the String associated with it; if the
    // key is not found, it returns null.
    public String search(int key)
    {
        int num = list.size();
        for (int i = 0; i < num; i++)          //iterate through the list looking for key
        {
            if (key == list.get(i).integer)    //if key is found
            {   
                 return list.get(i).string;     //return the node's string
            }
            else
                continue;
        }
        return null;        //if not found then return null
    }
    
    // Integer[] getKeys()
    //
    // This function generates an exhaustive list of all keys in the
    // data structure, and returns them as an array.  A new array may be
    // allocated for each call; do *NOT* reuse the same array over and
    // over.
    //
    // NOTE: The keys in the array are *NOT* necessarily sorted.
    //
    // IMPLEMENTATION HINT: While you must return an array at the end,
    // you may use some other form - such as an ArrayList - as a
    // temporary variable as you build the retval.  
    // Then you can convert an ArrayList<Integer> to Integer[] with
    // the toArray() method.   
    public Integer[] getKeys()
    {
        int num = list.size();
        Integer[] array = new Integer[num];     //create Integer array that is the size of the array
        for (int i = 0; i < num; i++)
        {
             array[i] = list.get(i).integer;    //set each element in the array
        }
        return array;  //return the array
    }
    
    // int getSuccessor(int)
    //
    // Searches the tree for the given key, and then returns the
    // successor key in the data structure - that is, the next key in a
    // sorted list of the keys.
    //
    // This function throws IllegalArgumentException if either of the
    // following are true:
    //     - the key does not exist
    //     - the key does not have any successor   
    public int getSuccessor(int key) throws IllegalArgumentException
    {
        int num = list.size();
        int successor = key;           //holds the successor value (starts as value of key)
        boolean foundKey = false;      //a marker to let you know if key is found
        for (int i = 0; i < num; i++)  //iterate through the list 
        {
            if (key == list.get(i).integer)    //if key is found then set foundKey marker as true
            {   
                 foundKey = true;
                 continue;
            }
            else if (list.get(i).integer > key)    //if value is larger than key
            {
                if (successor == key)             //if successor has not be set
                {
                    successor = list.get(i).integer;  //set successor equal to node.val
                    continue;
                }
                else if (successor != key && list.get(i).integer < successor)   //if successor is already set and this value is smaller than other succcessor
                {
                    successor = list.get(i).integer;  //set key as new succesor
                    continue;
                }
                else
                    continue;  //else do nothing
            }
            else
                continue;
        }
        if (foundKey && successor != key)        //if key was found and the successor has been set then return the successor
            return successor;
        else                                     //otherwise throw new IAE
            throw new IllegalArgumentException();
    }
}

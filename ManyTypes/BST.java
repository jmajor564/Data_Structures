/* Program: BST
 * Author: John Major
 * Date: 4/14/2017
 * Purpose: The purpose of this program is to create a Binary Search Tree that can perform the following
 *          operations: Insert, Delete, Search, getKeys, getSuccessor. It does so by using 
 *          an binary tree structure to store the values.
 */

import java.util.ArrayList;

public class BST implements Proj04Dictionary
{
    private BSTNode root;
    public BST()
    {
        // See my comment in the constructor for AVLNode.  I like
        // to explicitly set things to null, instead of simply
        // using the default values.
        // - Russ
        root = null;
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
        if (data == null)
            throw new IllegalArgumentException();
        try {
            
            root = insert(root, key, data);  //call the recursive method insert and input the root of this tree
        } catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException();
        }
    }
    
    private static BSTNode insert(BSTNode subtree, int val, String data) throws IllegalArgumentException
    {
        if (subtree == null)    //if you have reached the end of the tree (leave node)
        {
            BSTNode newNode = new BSTNode(val, data);   //create new node
            return newNode;                       //return it to create the new leaf node
        }
        else if (subtree.val == val)   //if value already exists then throw an illegalArguementException
            throw new IllegalArgumentException();
        else if (val > subtree.val)      //if value is > than current node's value
        {
            subtree.right = insert(subtree.right, val, data);     //recurse right
            return subtree;        //return node
        }
        else
        {
            subtree.left = insert(subtree.left, val, data);       //recurse left
            return subtree;        //return node          
        }
    }    
    
    
    // delete(int)
    //
    // This function deletes the key from the dictionary.
    //
    // This function throws IllegalArgumentException if:
    //     - the key does not exist in the data structure    
    public void delete(int key) throws IllegalArgumentException
    {
        try{
            root = delete(root, key);    //calls the recursive delete method
        }catch (IllegalArgumentException e)  //catches an illegalArguement if the value was not found in the tree
        {
            throw new IllegalArgumentException();
        }        
    }
    
    public static BSTNode delete(BSTNode tree, int val) throws IllegalArgumentException
    {
        if (tree == null)                                       //if we reached a null value (reached end of tree) throw IllegalArguementException
            throw new IllegalArgumentException();
        else if (tree.val == val)                               //if we found the value
        {
            if (tree.left == null && tree.right == null)        //if it is a leaf then just return null
                return null;
            else if (tree.left != null && tree.right == null)   //if it only has a left node then return the left node
                return tree.left;
            else if (tree.left == null && tree.right != null)   //if it only has a right node then return the right node
                return tree.right;
            else                                                //if it has two children
            {
                int newValue = deleteHelper(tree.left);    //find the new value with the helper method (deleteHelper)
                tree.val = newValue;                       //tree's value is set to its predecessor
                tree.string = deleteHelper2(tree.left);    //sets node's string equal to the predecessor's string
                tree.left = delete(tree.left, newValue);   //delete the predecessor from the tree since we swapped it to current spot
                return tree;
            }
        }
        else if (val > tree.val)      //if value is > than the current node's value                 
        {
            tree.right = delete(tree.right, val);   //recurse right and set right node equal to the result
            return tree;  //return node
        }
        else
        {
            tree.left = delete(tree.left, val);     //recurse left and set left node equal to the result
            return tree;  //return node
        }
    }
    
    /* deleteHelper and deleteHelper2: 
     * Purpose: DH is used to find the predecessors value nad return that. DH2 is used to find the predecessor's 
     *          string and return that. Both of these are used to replace the node that is being deleted with two
     *          children's data. 
     */
    public static int deleteHelper(BSTNode tree)
    {
        if (tree.right == null)  //if node has no right node then return it (it is the predecessor)
            return tree.val;
        else                     //recurse right
            return deleteHelper(tree.right);
    }
    public static String deleteHelper2(BSTNode tree)
    {
        if (tree.right == null)  //if node has no right node then return it (it is the predecessor)
            return tree.string;
        else                     //recurse right
            return deleteHelper2(tree.right);
    }   
    // String search(int)
    //
    // This function searches the data structure for the key.  If the key
    // is found, then this returns the String associated with it; if the
    // key is not found, it returns null.
    public String search(int key)
    {
        if (root == null)             //if tree is empty return null
            return null;
        BSTNode newNode = search(root, key);  //newNode will hold result of search
        if (newNode != null)                  //if newNode is not null return newNode's string
            return newNode.string;
        return null;                          //else return null (value was not found)
    }
    
    public BSTNode search(BSTNode tree, int val)
    {
        if (tree == null)                   //base case 1 : if node is null then return null
            return null;
        else if (tree.val == val)           //base case 2 : if value is found return the node  
            return tree;
        else if (val > tree.val)            //if value is > than current node recurse right
            return search(tree.right, val);
        else                                //if value is < than current node recurse left
            return search(tree.left, val);
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
        Integer[] array = new Integer[count()];              //create new list, that is the size of the tree (count result)
        ArrayList<Integer> list = new ArrayList<Integer>();  //create array list
        
        list = getKeysRecurse(root, list); //set list equal to the getKeysRecurse result (fills in the arrayList)
        int size = list.size(); 
        for (int i = 0; i < size; i++)  //set the array equal to the arraylist (input the keys) 
        {
            array[i] = list.get(i);
        }
        return array;   //return the array
    }
    
    public ArrayList<Integer> getKeysRecurse(BSTNode node, ArrayList<Integer> list)
    {
        if (node == null)    //if node == null, return the list, unmodified 
            return list;
        else
        {
            list = getKeysRecurse(node.left, list);  //list is equal to recurse left (add left subtrees values)
            list.add(node.val);                      //add node.val
            list = getKeysRecurse(node.right, list); //list is equal to recurse right (add right subtrees values)
        }
        return list;
    }
    // Count: used to return the number of nodes in the tree.
    public int count()    
    {
        int num = countRecurse(root);   //calls the countRecurse method and retrives the int, which it returns. 
        return num;
    }
    //recurses through the tree. Returns 1 + children's subtree count if node is not null. If null it returns 0.
    public int countRecurse(BSTNode node)
    {
        if (node == null)  //if a leaf then return 0;
            return 0;
        else
            return 1 + countRecurse(node.right) + countRecurse(node.left);  //return 1 + recursion of left child + recursion of right child  
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
        try {              
            BSTNode successor = getSuccessorRec(root, key);   //sets successor node equal to the result of the getSuccessor method
            if (successor == null)                            //if node == null it throws a IAE
                throw new IllegalArgumentException();
            else
                return successor.val;                        //if successor was successfully found then it returns the successor 
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();   //if the key did not exist it catches and throws a IAE
        }
    }    
    
    public BSTNode getSuccessorRec(BSTNode node, int key) throws IllegalArgumentException
    {
        if (node == null)   //if we reached a leaf then the key was not found. Throws IAE
        {
            throw new IllegalArgumentException();
        }
        else if (node.val == key)   //if we found the key, look right. If no right child then return null.
        {
            if (node.right != null)
            {
                BSTNode newNode = node.right;     //holds right childs value
                while (newNode.left != null)      //if left child not null keep iterationg to it
                    newNode = newNode.left;
                return newNode;                   //return result
            }
            else
                return null;                      //if not right child then return null
        }
        else
        {
            BSTNode temp;                                  //holds a node value
            if (key > node.val)                            //if key is larger than the current node value                         
                temp = getSuccessorRec(node.right, key);         //recurse right
            else
                temp = getSuccessorRec(node.left, key);     //if key is smaller than the current nodes value recurse left
            
            if (temp == null)          //if temp is null then we know that the success is above of it (it had no right child)
            {
                if (node.val > key)    //if current node is larger than key, it is successor
                    return node;            //return it
                else
                    return null;       //otherwise return null (signals to keep looking 
            }
            else
                return temp;            //if temp is not null then just return it (successor has already been found)
        }
    }
}

class BSTNode
{
    public BSTNode left,right;   //holds the children values
    
    public int val;       //holds integer value
    public String string; //holds the string value
    
    public BSTNode(int val, String str)
    {
        this.val = val;   //sets node's val to the input value
        string = str;     //sets the string value equal to the input str value
        
        // Java defaults all fields to zero/false/null, so the next
        // two lines aren't actually necessary.  But personally, I
        // think it's better to explicitly say "this field should be
        // zero" instead of use the default - otherwise, people who
        // read the code later won't know if you intended the values
        // to be zero, or simply just forgot to initialize them.
        // - Russ
        left = right = null;   //sets both children to null
    }
}

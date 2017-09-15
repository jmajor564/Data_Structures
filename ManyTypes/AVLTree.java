/* Program: AVLTree
 * Author: John Major
 * Date: 4/14/2017
 * Purpose: The purpose of this program is to create a AVL Tree that can perform the following
 *          operations: Insert, Delete, Search, getKeys, getSuccessor. It does so by using 
 *          an binary tree structure to stroe the values. However, it will also use a rotation
 *          method to balance the tree should it become unbalanced.
 */

import java.util.ArrayList;

public class AVLTree implements Proj04Dictionary
{
    private AVLNode root;
    public AVLTree()
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
    
    private static AVLNode insert(AVLNode subtree, int val, String data) throws IllegalArgumentException
    {
        if (subtree == null)    //if you have reached the end of the tree (leave node)
        {
            AVLNode newNode = new AVLNode(val, data);   //create new node
            return newNode;                       //return it to create the new leaf node
        }
        else if (subtree.val == val)   //if value already exists then throw an illegalArguementException
            throw new IllegalArgumentException();
        else if (val > subtree.val)      //if value is > than current node's value
        {
            subtree.right = insert(subtree.right, val, data);     //recurse right
            int max = getHeight(subtree.right);             // set int max to right node height
            if (max < getHeight(subtree.left))              //if max < left node value
                max = getHeight(subtree.left);                 // set max to left values height
            subtree.height = max+1;              //set subtree.height equal to max +1
            subtree = rebalance(subtree);        //call rebalance to rebalance the tree
            return subtree;        //return node
        }
        else
        {
            subtree.left = insert(subtree.left, val, data);       //recurse left
            int max = getHeight(subtree.right);             // set int max to right node height
            if (max < getHeight(subtree.left))              //if max < left node value
                max = getHeight(subtree.left);                 // set max to left values height
            subtree.height = max+1;              //set subtree.height equal to max +1
            subtree = rebalance(subtree);        //call rebalance to rebalance the tree
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
    
    public static AVLNode delete(AVLNode tree, int val) throws IllegalArgumentException
    {
        if (tree == null)                         //if we reached a null value (reached end of tree) throw IllegalArguementException
            throw new IllegalArgumentException();
        else if (tree.val == val)                 //if we found the value
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
                tree.string = deleteHelper2(tree.left);
                tree.left = delete(tree.left, newValue);   //delete the predecessor from the tree since we swapped it to current spot
                tree = rebalance(tree);                    //rebalanceTree(tree)
                return tree;
            }
        }
        else if (val > tree.val)      //if value is > than the current node's value                 
        {
            tree.right = delete(tree.right, val);   //recurse right and set right node equal to the result
            int max = getHeight(tree.right);        //int max is equalright node's height
            if (max < getHeight(tree.left))         //if left nodes height is greater
                max = getHeight(tree.left);         //max is equal to the left node's height
            tree.height = max+1;                    //set node's height to max + 1 
            tree = rebalance(tree);      //rebalance tree
            return tree;  //return node
        }
        else
        {
            tree.left = delete(tree.left, val);     //recurse left and set left node equal to the result
            int max = getHeight(tree.right);        //int max is equalright node's height
            if (max < getHeight(tree.left))         //if left nodes height is greater
                max = getHeight(tree.left);         //max is equal to the left node's height
            tree.height = max+1;                    //set node's height to max + 1
            tree = rebalance(tree);      //rebalance tree
            return tree;  //return node
        }
    }
    public static int deleteHelper(AVLNode tree)
    {
        if (tree.right == null)  //if node has no right node then return it (it is the predecessor)
            return tree.val;
        else                     //recurse right
            return deleteHelper(tree.right);
    }
    public static String deleteHelper2(AVLNode tree)
    {
        if (tree.right == null)  //if node has no right node then return it (it is the predecessor)
            return tree.string;
        else                     //recurse right
            return deleteHelper2(tree.right);
    }
    
    public String search(int key)
    {
        if (root == null)  //if root is null, then tree is empty. Return null
            return null;
        AVLNode newNode = search(root, key);  //setnewNode equal to the search result
        if (newNode != null)                  //as long as node is not null 
            return newNode.string;            //return node's string
        return null;
    }
    
    /* search(tree, int)
     *
     * Searches the tree for a given key.  Returns the node where the tree
     * exists, or null if the key does not exist.  May be implemented
     * recursively (with a helper method), or as a loop.
     */    
    public AVLNode search(AVLNode tree, int val)
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
        Integer[] array = new Integer[count()];             //create new Integer[]
        ArrayList<Integer> list = new ArrayList<Integer>(); //create new ArrayList
        
        list = getKeysRecurse(root, list);     //set list equal to all of the values in the tree
        int size = list.size();                
        for (int i = 0; i < size; i++)         //place all of the values into the Integer[] 
        {
            array[i] = list.get(i);
        }
        return array;  //return the array
    }
    
    public ArrayList<Integer> getKeysRecurse(AVLNode node, ArrayList<Integer> list )
    {
        if (node == null)    //if node == null
            return list;          //return list, unmodified
        else
        {
            list = getKeysRecurse(node.left, list);     //add left subtree's values
            list.add(node.val);                         //add current node's value
            list = getKeysRecurse(node.right, list);    //add right subtree's values
        }
        return list;  //return list
    }
    public int count()
    {
        int num = countRecurse(root);   //num is equal to the #of nodes in the tree
        return num;   //return num
    }
    
    public int countRecurse(AVLNode node)
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
            AVLNode successor = getSuccessorRec(root, key);   //successor node will hold the node containing successor info (or it will be null)
            if (successor == null)                            // if the node is null
                throw new IllegalArgumentException();              //then there was no successor, throws IAE
            else
                return successor.val;                         //else return successor 
        } catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException();            //if key was not found, throw IAE 
        }
    }
    
    public AVLNode getSuccessorRec(AVLNode node, int key)
    {
        if (node == null)     //if we reach leaf node, key doesn't exist. Throw IAE
        {
            throw new IllegalArgumentException();
        }
        else if (node.val == key)    //if key is found 
        {
            if (node.right != null)  //if node has right child
            {
                AVLNode successor = node.right;   //set successor node equal to right child
                while (successor.left != null)    //recurse to most left child to get successor
                {
                    successor = successor.left;
                }
                return successor;      //return successor node
            }
            else
                return null;          //if no right child, then successor will be at higher node. Return null to signal this
        }
        else
        {
            AVLNode temp;       //holds result of below operations
            if (key > node.val)
                temp = getSuccessorRec(node.right, key);    //if key is larger than current node, recursive call right 
            else
                temp = getSuccessorRec(node.left, key);     //if key is smaller, recurse left
            
            if (temp == null)          //if temp equals null, then successor is above
            {
                if (node.val > key)    //if current node is greater than key, then return as successor
                    return node;
                else
                    return null;       //else return null to signal that it hasn't been found yet
            }
            else
                return temp;           //if temp has value, successor has been found. Just return it
        }
    }
    
    public static AVLNode rebalance(AVLNode node)
    {
        while ((getHeight(node.left) - getHeight(node.right)) > 1 || (getHeight(node.right) - getHeight(node.left)) > 1)  //while one of the branches is at least a height of 2 taller
        {
            if ((getHeight(node.left) - getHeight(node.right)) > 1)   //if the left side is too large
            {
                if (getHeight(node.left.right) > getHeight(node.left.left))  //if the node's right node is > then it's left
                {
                    node.left = rotateLeft(node.left);    //left rotate on the left node
                    node = rotateRight(node);             //right rotate on node
                    continue;
                }
                else  //if left node's left tree's height > left node's right tree's height
                {
                    node = rotateRight(node);        //right rotate of node
                    continue;
                }
            }
            else   //if the right side is too large
            {
                if (getHeight(node.right.left) > getHeight(node.right.right))  //if right node's left subtree > then right subtree
                {
                    node.right = rotateRight(node.right);   //right rotate node's right child
                    node = rotateLeft(node);                //left rotate on node
                    continue;
                }
                else     //if right node's right subtree > then left subtree
                {
                    node = rotateLeft(node);  //left rotate on node
                    continue;   
                }
            }
        }
        return node;  //return node once balanced
    }
    
    
    /* static getHeight(AVLNode)
     *
     * Helper method that returns the height of a subtree.  The parameter
     * may be null.
     *
     * Since a single node has height=0, this should return -1 if passed
     * a null parameter.
     *
     * This method is NON-RECURSIVE.  It runs in O(1) time.  As such, it
     * may only check the height field in the current node; it does not
     * look at any other nodes.
     */
    public static int getHeight(AVLNode subtree)
    {
        if (subtree == null)    //if node is null then return -1
            return -1;
        return subtree.height;  //else return node's height
    }
    
    /* static rotateRight(AVLNode)
     *
     * Performs a right-rotation at the current node.  This method assumes
     * that both the parameter, and its left child, are non-null; if
     * either is null, then a NullPointerException will result.
     *
     * Returns the root of the new tree, which is always the node which
     * (used to be) the left child of the old root.
     *
     * This method works by changing REFERENCES NOT VALUES.  That is, it
     * does not inspect (or change) the .val field of any node; instead,
     * it changes the left and right pointers.
     *
     * This method updates the height of all appropriate nodes after
     * rearranging the references.
     *
     * This method is NON-RECURSIVE.  It runs in O(1) time.  As such, it
     * may only check the fields in a fixed number of nodes.
     */
    public static AVLNode rotateRight(AVLNode subtree)
    {
        AVLNode brokenBranch = subtree.left.right;   //hold on the subtree's left child's right subtree 
        AVLNode newTop = subtree.left;  //hold the left child which will become the new parent
        subtree.left.right = subtree;   //set the current node's left childs right node equal to the subtree node
        subtree.left = brokenBranch;    //set subtree's left node to the broken branch (subtrees's old left child's right subtree)
        subtree = rotateHelper(newTop); //use rotate helper to fix height's
        return subtree;  //return new subtree
    }
    /* static rotateLeft(AVLNode)
     *
     * See the documentation for rotateRight() above.  This is simply its
     * mirror image.
     */
    public static AVLNode rotateLeft(AVLNode subtree)
    {
        AVLNode brokenBranch = subtree.right.left;  //hold on to subtree's right child's left subtree
        AVLNode newTop = subtree.right;             //hold on to subtree's right child
        subtree.right.left = subtree;               //set subtree's right child's left node to subtree
        subtree.right = brokenBranch;               //set subtree's right child to the broken branch (subtree's old right child's left subtree)
        subtree = rotateHelper(newTop);    //fix subtree's and all decendents heights
        return subtree;   //return new subtree
    }
    public static AVLNode rotateHelper(AVLNode subtree)
    {
        if (subtree == null)  //if node is null then return null
            return null;
        else               
        { 
            subtree.right = rotateHelper(subtree.right);  //recursive call right and set subtree.right equal to the return node
            subtree.left = rotateHelper(subtree.left);    //recursive call left and set subtree.left equal to the return node  
            int max = getHeight(subtree.left);            //set int max equal to the height of the left subtree
            if (getHeight(subtree.right) > max)           //if right subtree's height > max
                max = getHeight(subtree.right);              //then set max = right subtree
            subtree.height = max+1;                       //subtree's height = max + 1
            return subtree;             //return subtree
        }
    }
}

class AVLNode
{
    public AVLNode left,right;  //holds the children pointers 
    public int     height;      //holds the height
    
    public int val;             //holds the data value
    public String string;       //holds the string value
    
    public AVLNode(int val, String str)
    {
        this.val = val;
        string = str;
        
        // Java defaults all fields to zero/false/null, so the next
        // two lines aren't actually necessary.  But personally, I
        // think it's better to explicitly say "this field should be
        // zero" instead of use the default - otherwise, people who
        // read the code later won't know if you intended the values
        // to be zero, or simply just forgot to initialize them.
        // - Russ
        left = right = null;
        height = 0;
    }
}

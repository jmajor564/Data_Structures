/* File: AVLTree.java
 * Class: Computer Science 345
 * Author: John Major
 * Purpose: This program holds an avl tree. From this class you are able to call several
 *          methods that allows you to modify the tree by either adding or deleteing nodes. 
 *          the tree also has the ability to rebalance itself and contanins methods used to 
 *          rebalance the tree by rotating it to the left or the right. There is also the option
 *          print both the pre and post order traversals and create a dot file of the tree.
 */

import java.io.BufferedWriter; 
import java.io.FileWriter;


/* class AVLTree
 *
 * Models a complete AVLTree.  Individual nodes are modeled by the AVLNode
 * class.
 *
 * The constructor creates an empty tree (root=null).
 *
 * This class has many methods which perform basic operations.  Most of these
 * (non-static) methods have (static) recursive helper methods, which take an
 * AVLNode as the first paramter; these are static because it is valid for the
 * parameter to be null.  Thus, the base case for each of these methods is an
 * empty tree.  (See the method documentation below for details; there are a
 * few exceptions to this rule.)
 *
 * Methods that perform modifications on the tree all use the x=change(x)
 * style.  These include highly visible methods (such as insert and delete),
 * and also utility methods (such as rotateRight).
 */
public class AVLTree
{
 // ALERT
 //
 // You *must not* add any fields other than this one to this class!
 // This field is sufficient for everything that you need to do.
 //
 // Also, remember that you can't change the AVLNode class *at all* -
 // and so you cannot add fields there, either!
 //
 // However, you can (and should!) add some static helper methods to
 // this class.
 private AVLNode root;


 /* default constructor
  *
  * Initializes this object to represent an empty tree.
  */
 public AVLTree()
 {
  // See my comment in the constructor for AVLNode.  I like
  // to explicitly set things to null, instead of simply
  // using the default values.
  // - Russ
  root = null;
 }



 /* debug(String)
  *
  * Opens a file with the given filename, and then fills that file with
  * .dot file source code, to generate a picture for this tree.  This
  * wrapper opens (and closes) the file, and adds the necessary
  * prefix/suffix text inside it.  However, the actual body of the file
  * should be printed by a recursive helper method.
  *
  *   - Note from Russ: I have not yet defined the helper method, since
  *     I assume that different students might use different Java classes
  *     for printing.  One option is to pass a FileWriter as a parameter;
  *     another is to *return* a String.
  */
 public void debug(String filename)
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
 public static String debugGetVals(AVLNode node)
 {
     if (node == null)
         return "";
     return node.val + "[label=\""+node.val+"\\nh=" +getHeight(node)+"\"];\n" + debugGetVals(node.left) + debugGetVals(node.right); 
 }
 /* Method: debugGetRel
  * Purpose:
  * This method returns the relations for the dot graph. It iterates through the tree and returns the relationships
  * in the form of a string.
  * 
  */ 
 private static String debugGetRel(AVLNode node)
 {
     if (node == null)  //if null return "" (empty string);
         return "";
     else if (node.left == null && node.right == null)  //if a leaf then no relations, therfore return empty string
         return "";
     else if (node.left != null && node.right == null)  //if node has left child then return node.data + arrow + left child's data + label left and newline, then recursive call on left child
     {
         return node.val+  " -> " + node.left.val + "[label=\"left\"];\n" + 
             debugGetRel(node.left);
     }
     else if (node.left == null && node.right != null) //if node has right child then return node.data + arrow + right child's data + label right and newline, then recursive call on right child
     {
         return node.val+  " -> " + node.right.val + "[label=\"right\"];\n" + 
             debugGetRel(node.right);
     }
     else  //if node has two children create the realtions for both of them then add the recursive call for both left and right children
     {
         return node.val+  " -> " + node.left.val + "[label=\"left\"];\n" + node.val + " -> " + node.right.val + "[label=\"right\"];\n" +
             debugGetRel(node.left)  + debugGetRel(node.right);
     }
 }



 /* print_inOrder()
  *
  * Prints out an in-order traversal of the tree to stdout (followed by
  * a newline).  The keys are separated by spaces, and the list (if
  * non-empty) has a trailing space.  If the tree is empty, this prints
  * out nothing except for the newline.  Uses a static recursive helper
  * method.
  *
  * static print_inOrder(AVLNode)
  *
  * Helper method for the public method.  Is static; takes an AVLNode
  * parameter, which might be null.  If the parameter is null, it does
  * nothing.  Works recursively.
  */
 public void print_inOrder()
 {
     print_inOrder(root);  //calls the recursive method print_inOrder method which takes the root
 }
 private void print_inOrder(AVLNode tree)
 {
     if (tree == null)     //if node is null then do nothing and return 
         return;
     print_inOrder(tree.left);          //recursive call on left.node
     System.out.print(tree.val + " ");  //print node value
     print_inOrder(tree.right);         //recursive call on right node
 }


 /* print_preOrder()
  *
  * See print_inOrder() above - works in exactly the same way, except
  * produces a pre-order traversal instead of an in-order traversal.
  */
 public void print_preOrder()
 {
     print_preOrder(root);
 }
 private void print_preOrder(AVLNode tree)
 {
     if (tree == null)                //if node is null then do nothing and return
         return;
     System.out.print(tree.val + " ");  //print node's value
     print_preOrder(tree.left);         //recursive call on left node
     print_preOrder(tree.right);        //recursive call on right node
 }


 /* search(int)
  *
  * Searches the tree for a given key.  Returns the node where the tree
  * exists, or null if the key does not exist.  May be implemented
  * recursively (with a helper method), or as a loop.
  */
 public AVLNode search(int val)
 {
      return search(root, val);      //call the recusive search method and input root as starting point
 }
 public AVLNode search(AVLNode tree, int val)
 {
      if (tree == null)            //base case 1 : if node is null then return null
          return null;
      else if (tree.val == val)    //base case 2 : if value is found return the node  
          return tree;
      else if (val > tree.val)            //if value is > than current node recurse right
          return search(tree.right, val);
      else                                //if value is < than current node recurse left
          return search(tree.left, val);
 }


 /* insert(int)
  *
  * Inserts a new key into the tree; throws IllegalArgumentException if
  * the key is a duplicate.
  *
  * Uses the x=change(x) style to modify the tree.  Uses a static,
  * recursive helper method to implement the modification.  The helper
  * method is responsible for *all* of the changes associated with
  * inserting a new value, including all rebalances.
  *
  * static insert(AVLNode,int)
  *
  * Static, recursive helper method for insert(int).  Takes an AVLNode
  * parameter, which might be null; if the parameter is null, then it
  * creates a new node and returns it.
  *
  * Always returns the root of the new tree; does all of the
  * rebalancing work associated with the insert.
  */
 public void insert(int val) throws IllegalArgumentException
 {
     try {
      root = insert(root, val);  //call the recursive method insert and input the root of this tree
     } catch (IllegalArgumentException e)
     {
         System.err.println("ERROR: Could not insert "+val+" because it is already in the tree.");
     }
 }
 private static AVLNode insert(AVLNode subtree, int val) throws IllegalArgumentException
 {
         if (subtree == null)    //if you have reached the end of the tree (leave node)
         {
             AVLNode newNode = new AVLNode(val);   //create new node
             return newNode;                       //return it to create the new leaf node
         }
         else if (subtree.val == val)   //if value already exists then throw an illegalArguementException
             throw new IllegalArgumentException();
         else if (val > subtree.val)      //if value is > than current node's value
         {
             subtree.right = insert(subtree.right, val);     //recurse right
             int max = getHeight(subtree.right);             // set int max to right node height
             if (max < getHeight(subtree.left))              //if max < left node value
                 max = getHeight(subtree.left);                 // set max to left values height
             subtree.height = max+1;              //set subtree.height equal to max +1
             subtree = rebalance(subtree);        //call rebalance to rebalance the tree
             return subtree;        //return node
         }
         else
         {
             subtree.left = insert(subtree.left, val);       //recurse left
             int max = getHeight(subtree.right);             // set int max to right node height
             if (max < getHeight(subtree.left))              //if max < left node value
                 max = getHeight(subtree.left);                 // set max to left values height
             subtree.height = max+1;              //set subtree.height equal to max +1
             subtree = rebalance(subtree);        //call rebalance to rebalance the tree
             return subtree;        //return node          
         }
 }



 /* delete(int)
  *
  * Deletes a key from the tree; throws IllegalArgumentException if
  * the key does not exist.
  *
  * Uses the x=change(x) style to modify the tree.  Uses a static,
  * recursive helper method to implement the modification.  The helper
  * method is responsible for *all* of the changes associated with
  * deleting the value, including all rebalances.
  *
  * static delete(AVLNode,int)
  *
  * Static, recursive helper method for delete(int).  Takes an AVLNode
  * parameter, which might be null; if the parameter is null, then we
  * are attempting to delete from an empty tree, and thus the delete
  * fails.  On the other hand, this method can *return* null quite
  * normally; this happens when we delete the last node from the tree.
  *
  * Always returns the root of the new tree (which might be null); does
  * all of the rebalancing work associated with the delete.  (But see
  * note in the spec about possibly skipping rebalance-on-delete.)
  */
 public void delete(int val) throws IllegalArgumentException
 {
     try{
         root = delete(root, val);    //calls the recursive delete method
     }catch (IllegalArgumentException e)  //catches an illegalArguement if the value was not found in the tree
     {
         System.err.println("ERROR: Could not delete "+val+" because it was not in the tree.");
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

 /* static rebalance(AVLNode)
  *
  * Performs a rebalance (if required) on the current node.  This
  * method assumes that the parameter is non-null; if it is passed a
  * null parameter, it will hit NullPointerException.  However, it
  * is valid for either (or both) of the children of this node to
  * be null; to be clear, it is OK to call this on a leaf node.
  *
  * This method assumes that the height is correct, in the current
  * node and all its descendants; thus, after an insert or delete,
  * the *CALLER* must update the height before calling this method.
  *
  * This method returns the root of the new tree.  If no rotations
  * were required, then this is simply the parameter; if rotations
  * were required, then this returns the root after the rotation.
  *
  * This method is NON-RECURSIVE.  It runs in O(1) time.  As such, it
  * only checks for imbalances at THIS PARTICULAR NODE, never at any
  * descendants.
  */
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


/* Program: HashTable
 * Author: John Major
 * Date: 4/14/2017
 * Purpose: The purpose of this program is to create a Hashtable that can perform the following
 *          operations: Insert, Delete, Search, getKeys, getSuccessor. It does so by using 
 *          an array of Hashtable objects to store the data, and then stroing the objects in
 *          the array in a location generated using a hash function.
 */         



public class HashTable implements Proj04Dictionary
{
    
    private HashTableNode[] list;
    public int listSize;         //# of slots in the list
    private int numElements;      //number of elements that have been added
    
    /* constructor: Initializes the list[] to an array with 4000 slots, list size to a size of 4000, and
     *              sets the number of elements to 0.
     */ 
    public HashTable()
    {
        list = new HashTableNode[4000];   //initalizes the array to size of 5000
        listSize = 4000;                  //sets the list size int to 5000
        numElements = 0;                  //sets numElements to 0;
    }
    
    /* getHashValue: This methods generates a hasvalue for the inserted key based on the size of the list
     *               that was also inputted. It runs a bunch of random (not really) math operations on the
     *               key and then returns the modulo of size, which in turn returns a value between 0 and 
     *               size-1.
     */ 
    public int getHashValue(int key, int size)
    {
        int result = (((((key + 10) * 10000) / 3) + 47) * 23) % size;  //performs random operation to generate hash value
        if (result < 0)
            result = result * -1;  //if value is negitive, this sets it to become positive
        return result;  //returns result
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
        if (data == null)                         //if sttring data is null, return IAE
            throw new IllegalArgumentException();
        int hashValue = getHashValue(key, listSize);           //generate the has value
        HashTableNode newNode = new HashTableNode(key, data);  //create new node to be added wth inputted nformation
        HashTableNode tempNode = list[hashValue];              //get head node at list location hashValue
        if (tempNode == null)                                  //if list location is null then just add newNode as head
        {
            list[hashValue] = newNode;
            numElements++;
        }
        else                                                   //If there are already nodes in slot
        {
            while (tempNode.nextNode != null)                  //iterate to the end of the list
            {
                if (tempNode.key == key)                       //if key already exists throw IAE
                    throw new IllegalArgumentException();
                tempNode = tempNode.nextNode;
            }
            if (tempNode.key == key)                           //if key already exists throw IAE
                throw new IllegalArgumentException();
            tempNode.nextNode = newNode;                       //add newNode to the end of the list
            numElements++;                                     //increasement elements count
        }
        if (((double)numElements)/((double)listSize) > .75)    //if list size > 75% then resize the array
        {
            extendList();  //call extend list 
        }
    }
    
    /*Method ExtendList: This methods is used to extend the size of the current hashtable list. This will
     *                   double the size of the list and then transfer all of the old nodes into the new list. 
     *                   it will regenerate new hashvalues for each of the nodes and place them in their appropriate
     *                   locations.
     */ 
    
    public void extendList()
    {
        int newSize = listSize * 2; //new size of the list
        int count = 0;
        HashTableNode[] newList = new HashTableNode[newSize];   //create new list of size newSize
        for (int i = 0; i < listSize; i++)                      //iterates through the old list
        {
            HashTableNode oldListPointer = list[i];                    //pointer to head of each linked list in slot list[i]
            if (oldListPointer == null)                                //if null continue
                continue;
            while (oldListPointer != null)                              //else iterate through whole list
            {
                int hashValue = getHashValue(oldListPointer.key, newSize);  //generate the new hasvalue
                HashTableNode newListTemp = newList[hashValue];       //pointer to newList head of linked list
                if (newListTemp == null)
                {
                    HashTableNode temp = new HashTableNode(oldListPointer.key, oldListPointer.string);
                    temp.nextNode = null;
                    newList[hashValue] = temp;                    //if empty just add the current node to the new list
                    count++;
                }
                else
                {
                    while (newListTemp.nextNode != null)              //otherwise add to the end of the list
                    {
                        newListTemp = newListTemp.nextNode;
                    }
                    HashTableNode temp = new HashTableNode(oldListPointer.key, oldListPointer.string);
                    newListTemp.nextNode = temp;   
                    count++;
                }
                oldListPointer = oldListPointer.nextNode;                          //most to next node in the old list
            } 
        }
        list = newList;      //set list to the new list
        listSize = newSize;  //update the current list size
    }
    
    // delete(int)
    //
    // This function deletes the key from the dictionary.
    //
    // This function throws IllegalArgumentException if:
    //     - the key does not exist in the data structure    
    public void delete(int key) throws IllegalArgumentException
    {
        int hashValue = getHashValue(key, listSize);   //generate the hashvalue of the node that you are looking to delete
        HashTableNode tempNode = list[hashValue];      //points the the head of the lisnked list at that location generated
        if (tempNode == null)                          //if null then return IAE because node does not exist
            throw new IllegalArgumentException();
        else                                           //if node is not null
        {
            if (tempNode.key == key)                   //if first node is equal to key, set node.next as new head
            {
                list[hashValue] = tempNode.nextNode;
                numElements--;                         //update the count
                return;
            }
            else
            {
                HashTableNode prev = tempNode;
                while (tempNode != null)                     //otherwise iterate through the list 
                {
                    if (tempNode.key == key)                 //if you find the key (in the next node)
                    {
                        prev.nextNode = tempNode.nextNode;   //set current node equal to the key's nextnode (either a node or null)
                        numElements--;                                    //decrement numElements count
                        return;
                    }
                    else                                    //else just move to next node
                    {
                        prev = tempNode;                    //set prev as tempNode
                        tempNode = tempNode.nextNode;       //increment tempNode to it's next node
                    }
                }
            }
        }
        throw new IllegalArgumentException();
    }
    
    // String search(int)
    //
    // This function searches the data structure for the key.  If the key
    // is found, then this returns the String associated with it; if the
    // key is not found, it returns null.
    
    public String search(int key)
    {
        int hashValue = getHashValue(key, listSize);   //generate the hash value
        HashTableNode tempNode = list[hashValue];      //pointer to slots linked list
        while (tempNode != null)                       //iterate through list
        {
            if (tempNode.key == key)             //if you find the key, return the keys string
                return tempNode.string;
            tempNode = tempNode.nextNode;        //else move to next node
        }
        return null;   //if key not found, return null
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
        Integer[] array = new Integer[numElements];   //create new Integer[]
        int count = 0;  //tracks the next location in the array to add value
        for (int i = 0; i < listSize; i++)
        {
            HashTableNode node = list[i]; //points to head in slot list[i]
            while (node != null)
            {
                array[count] = node.key;   //add value into array[count]
                count++;                   //increments count
                node = node.nextNode;      //interates to next node in linked list
            }
        }
        return array;   //return array
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
        boolean foundKey = false;            //signals if the key was found
        int successor = key;                 //initialize the successor to the key
        for (int i = 0; i < listSize; i++)   //loops through the hashtable
        {
            HashTableNode node = list[i];    
            while (node != null)             //loops through each of the hashtables linked lists
            {
                if (node.key == key)         //if key is found
                {
                    foundKey = true;         //set foundKey to true
                    node = node.nextNode;    //move to next node
                }
                else if (node.key > key)     //if the current node is greater than key
                {
                    if (node.key < successor)  //if key less than successor
                    {
                        successor = node.key;  //set as new successor
                        node = node.nextNode;  // iterate to the next node
                        continue;                        
                    }
                    else if (successor == key)  //if successor has not yet been set
                    {
                        successor = node.key;   //set key as the new successor
                        node = node.nextNode;   //move to next node
                        continue;
                    } 
                    else
                        node = node.nextNode;   //move to next node
                }
                else
                {
                    node = node.nextNode;  //move to next node
                }
            }
        }
        if (successor != key && foundKey)  //if successor has been set and the key has been found
            return successor;              //return the successor
        else
            throw new IllegalArgumentException();  //else throw IAE
    }
}


/* class HashTable: This is used as a node template. It holds the link list node's key, string and
 *                  pointer to the next node. It contains a constructor that sets the values based
 *                  on the desired inputs.
 */ 
class HashTableNode
{
    int key;                 //holds key
    String string;           //holds string
    HashTableNode nextNode;  //holds nextNode
    
    public HashTableNode(int val, String str)
    {
        key = val;
        string = str;
        nextNode = null; 
    }
}

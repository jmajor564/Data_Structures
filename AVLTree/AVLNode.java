public class AVLNode
{
	public AVLNode left,right;
	public int     height;

	public int val;

	public AVLNode(int val)
	{
		this.val = val;

		// Java defaults all fields to zero/false/null, so the next
		// two lines aren't actually necessary.  But personally, I
		// think it's better to explicitly say "this field should be
		// zero" instead of use the default - otherwise, people who
		// read the code later won't know if you intended the values
		// to be zero, or simply just forgot to initialize them.
		//	- Russ
		left = right = null;
		height = 0;
	}
}


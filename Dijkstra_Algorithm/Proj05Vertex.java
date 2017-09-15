/* class Proj05Vertex
 *
 * Part of the Project 5 standard code.  This models a single vertex in a
 * digraph.  We use an edge list to represent the edges; each edge "list"
 * is an ArrayList for flexibility.
 *
 * Each Vertex has a String, which is its name; every Edge has an int,
 * which is its weight.  In addition, both types have three accessory
 * fields, with now defined purpose - the students can use these fields
 * (or not) as desired.
 */

import java.util.ArrayList;

public class Proj05Vertex
{
	// the instructor's code will fill out these two fields
	public String name;
	public ArrayList<Proj05Edge> outEdges;


	// students may use any of these fields for any purpose - there's
	// no defined meaning for them.
	public boolean      accBool;
	public int          accInt;
	public Object       accObj;


	public Proj05Vertex(String name)
	{
		this.name     = name;
		this.outEdges = new ArrayList<Proj05Edge>();
	}


	/* utility method, for saerching through the edges. */
	public boolean containsEdgeTo(Proj05Vertex other)
	{
		for (Proj05Edge e: outEdges)
			if (e.toVrt == other)
				return true;
		return false;
	}
}


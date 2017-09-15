/* class Proj05Edge
 *
 * Part of the Project 5 standard code.  This models a single edge in a
 * digraph.  Each edge includes two redundant fields: a reference to the
 * Vertex on the other end of this edge, and also the index (into the
 * graph's global array of vertices) of that same vertex.
 *
 * Each Vertex has a String, which is its name; every Edge has an int,
 * which is its weight.  In addition, both types have three accessory
 * fields, with now defined purpose - the students can use these fields
 * (or not) as desired.
 */

import java.util.ArrayList;

public class Proj05Edge
{
	// THESE FIELDS ARE REDUNTANT.
	// Both are set by the insructor's code
	Proj05Vertex toVrt;
	int          toIndx;


	// this is the edge-specific info
	int weight;


	// students may use any of these fields for any purpose - there's
	// no defined meaning for them.
	public boolean      accBool;
	public int          accInt;
	public Object       accObj;


	public Proj05Edge(Proj05Vertex vrt, int indx, int weight)
	{
		this.toVrt  = vrt;
		this.toIndx = indx;
		this.weight = weight;
	}
}


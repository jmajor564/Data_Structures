/* class Proj05Main
 *
 * This class:
 *    - Reads the input file
 *    - Creates the graph model
 *    - Calls the student code to implement various functions
 *
 * Key Data Structures:
 *    - An ArrayList of Proj05Vertex objects - the graph.
 *      We use the indices of the various vertices for various
 *      simplifications; most importantly, we expect that
 *      students will probably use these indices as the "indices"
 *      into the IndexMinPQ data structure.
 *
 *    - A HashMap: String->int, which we use to map String node
 *      names (as used in the input file) to indices into the
 *      ArrayList above.
 */


import java.io  .*;
import java.util.*;


public class Proj05Main
{
	public static void main(String[] args)
		throws IOException
	{
		if (args.length != 1)
		{
			System.err.printf("SYNTAX: java %s <filename>\n",
			                  Class.class.getSimpleName());
			System.exit(1);
		}

		if (args[0].substring(args[0].length()-3).equals(".in") == false)
		{
			System.err.println("ERROR: All input files must have the .in suffix.");
			System.exit(1);
		}
		String basename = args[0].substring(0, args[0].length()-3);

		Scanner in = null;
		try {
			in = new Scanner(new File(args[0]));
		} catch (Exception e) {
			System.err.printf("Could not open the input file '%s': %s\n", args[0], e);
			System.exit(1);
		}


		ArrayList<Proj05Vertex> verts = new ArrayList<Proj05Vertex>();
		HashMap<String,Integer> names = new HashMap<String,Integer>();


		while (in.hasNext())
		{
			String cmd = in.next();


			switch(cmd)
			{
			case "vertex":    handleVertex   (in, verts,names);  break;
			case "edge":      handleEdge     (in, verts,names);  break;
			case "reachable": handleReachable(in, verts,names);  break;
			case "dijkstra":  handleDijkstra (in, verts,names);  break;

			default:
				throw new IllegalArgumentException("Unrecognized command: "+cmd);
			}
		}


		/* after the input file has been consumed, we print out the
		 * .dot file.
		 */
		Proj05Vertex[] arr = new Proj05Vertex[verts.size()];
		Proj05StudentCode.printDotFile(verts.toArray(arr),
		                               basename+"-student.dot");

		/* We do a final printout, to convirm that the program didn't
		 * crash.
		 */
		System.out.println("Testcase completed.");
	}


	public static void handleVertex(Scanner                 in,
	                                ArrayList<Proj05Vertex> verts,
	                                HashMap<String,Integer> names)
	{
		String name = in.next();
		if (names.containsKey(name))
			throw new IllegalArgumentException("Duplicate vertex name: "+name);

		Proj05Vertex vert = new Proj05Vertex(name);
		int          indx = verts.size();

		verts.add(vert);
		names.put(name, indx);
	}



	public static void handleEdge(Scanner                 in,
	                              ArrayList<Proj05Vertex> verts,
	                              HashMap<String,Integer> names)
	{
		String from_name = in.next();
		String   to_name = in.next();
		int       weight = in.nextInt();

		if (weight < 0)
			throw new IllegalArgumentException("Negative edge weight "+weight);

		Integer from_I = names.get(from_name);
		Integer   to_I = names.get(  to_name);

		if (from_I == null || to_I == null)
			throw new IllegalArgumentException(String.format("Either the from or two of an edge didn't exist.  from='%s' to='%s'\n", from_name, to_name));

		Proj05Vertex from = verts.get(from_I);
		Proj05Vertex   to = verts.get(  to_I);

		if (from.containsEdgeTo(to))
			throw new IllegalArgumentException(String.format("The 'from' vertex already has an edge to the 'to' vertex.  from='%s' to='%s'\n", from_name, to_name));

		Proj05Edge edge = new Proj05Edge(to,to_I, weight);
		from.outEdges.add(edge);
	}



	public static void handleReachable(Scanner                 in,
	                                   ArrayList<Proj05Vertex> verts,
	                                   HashMap<String,Integer> names)
	{
		String from_name = in.next();
		String   to_name = in.next();

		Integer from_I = names.get(from_name);
		Integer   to_I = names.get(  to_name);

		if (from_I == null || to_I == null)
			throw new IllegalArgumentException(String.format("dijkstra: Either the from or two of an edge didn't exist.  from='%s' to='%s'\n", from_name, to_name));

		Proj05Vertex[] arr = new Proj05Vertex[verts.size()];

		Proj05StudentCode.reachable(verts.toArray(arr), from_I,to_I);
	}



	public static void handleDijkstra(Scanner                 in,
	                                  ArrayList<Proj05Vertex> verts,
	                                  HashMap<String,Integer> names)
	{
		String from_name = in.next();
		String   to_name = in.next();

		Integer from_I = names.get(from_name);
		Integer   to_I = names.get(  to_name);

		if (from_I == null || to_I == null)
			throw new IllegalArgumentException(String.format("dijkstra: Either the from or two of an edge didn't exist.  from='%s' to='%s'\n", from_name, to_name));

		Proj05Vertex[] arr  = new Proj05Vertex[verts.size()];

		Proj05StudentCode.dijkstra(verts.toArray(arr), from_I,to_I);
	}
}


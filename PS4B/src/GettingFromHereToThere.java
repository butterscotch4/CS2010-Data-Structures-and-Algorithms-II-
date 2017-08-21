// Copy paste this Java Template and save it as "GettingFromHereToThere.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0147919N
// write your name here: Sneha Santha Prabakar
// write list of collaborators here: Baghabrah Dana Mohammad A
// year 2017 hash code: x4gxK7xzMSlNvFsMEUVn (do NOT delete this line)

class GettingFromHereToThere {

	private int V; // number of vertices in the graph (number of rooms in the building)

	private ArrayList < ArrayList < IntegerPair > > AdjList; // the weighted graph (the building), effort rating of each corridor is stored here too
	private int[]taken;
	private static PriorityQueue < IntegerPair > pq;
	private int destinationV;
	private ArrayList<Integer> edgesVisited;

	public GettingFromHereToThere() {
	}

	void process(int vtx){
		taken[vtx]=1;
		for(int j=0; j<AdjList.get(vtx).size(); j++){
			IntegerPair v = AdjList.get(vtx).get(j);
			if(v.first().equals(destinationV)){
				edgesVisited.add(v.second());
				//pq.clear();
				//break;
			}
			//else{
				if(taken[v.first()]==0){
					pq.offer(new IntegerPair(v.second(), v.first()));  // we sort by weight then by adjacent vertex
				//}
			}
		}

	}

	int Query(int source, int destination) {
		int ans = 0;int maxE = 0;
		destinationV = destination;
		taken = new int[V]; 
		for(int i=0; i<V; i++){
			taken[i]=0;
		}
		pq = new PriorityQueue < IntegerPair > ();
		//System.out.println("Source= " + source);
		edgesVisited = new ArrayList<Integer>();
		if(source!=destination){
		process(source);
		}
		
		int max =0;
		
		while (!pq.isEmpty()) {
			IntegerPair front = pq.poll();
			//System.out.println("Adding edge: (" + front.first() + ", " + front.second() + "), MST cost now = ");
			if (taken[front.second()]==0) {
//				edgesVisited.add(front.first());
//				System.out.println("Edge: " + front.first());
				if(front.first()>max){
					max = front.first();
				}
				
				if(front.second() == destination)
					break;
				process(front.second());
			}
		}

		

		for(int i=0; i<edgesVisited.size(); i++){
			//System.out.println("Edge visited: " + edgesVisited.get(i));
			if(edgesVisited.get(i)>maxE){
				maxE = edgesVisited.get(i);
			}
		}

		ans = max;


		return ans;
	}

	void PreProcess(){
	}

	void run() throws Exception {
		// do not alter this method
		IntegerScanner sc = new IntegerScanner(System.in);
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int TC = sc.nextInt(); // there will be several test cases
		while (TC-- > 0) {
			V = sc.nextInt();

			// clear the graph and read in a new graph as Adjacency List
			AdjList = new ArrayList < ArrayList < IntegerPair > >();
			for (int i = 0; i < V; i++) {
				AdjList.add(new ArrayList < IntegerPair >());

				int k = sc.nextInt();
				while (k-- > 0) {
					int j = sc.nextInt(), w = sc.nextInt();
					AdjList.get(i).add(new IntegerPair(j, w)); // edge (corridor) weight (effort rating) is stored here
				}
			}

			PreProcess(); // you may want to use this function or leave it empty if you do not need it

			int Q = sc.nextInt();
			while (Q-- > 0)
				pr.println(Query(sc.nextInt(), sc.nextInt()));
			pr.println(); // separate the answer between two different graphs
		}

		pr.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method
		GettingFromHereToThere ps4 = new GettingFromHereToThere();
		ps4.run();
	}
}



class IntegerScanner { // coded by Ian Leow, using any other I/O method is not recommended
	BufferedInputStream bis;
	IntegerScanner(InputStream is) {
		bis = new BufferedInputStream(is, 1000000);
	}

	public int nextInt() {
		int result = 0;
		try {
			int cur = bis.read();
			if (cur == -1)
				return -1;

			while ((cur < 48 || cur > 57) && cur != 45) {
				cur = bis.read();
			}

			boolean negate = false;
			if (cur == 45) {
				negate = true;
				cur = bis.read();
			}

			while (cur >= 48 && cur <= 57) {
				result = result * 10 + (cur - 48);
				cur = bis.read();
			}

			if (negate) {
				return -result;
			}
			return result;
		}
		catch (IOException ioe) {
			return -1;
		}
	}
}



class IntegerPair implements Comparable < IntegerPair > {
	Integer _first, _second;

	public IntegerPair(Integer f, Integer s) {
		_first = f;
		_second = s;
	}

	public int compareTo(IntegerPair o) {
		if (!this.first().equals(o.first()))
			return this.first() - o.first();
		else
			return this.second() - o.second();
	}

	Integer first() { return _first; }
	Integer second() { return _second; }
}



class IntegerTriple implements Comparable < IntegerTriple > {
	Integer _first, _second, _third;

	public IntegerTriple(Integer f, Integer s, Integer t) {
		_first = f;
		_second = s;
		_third = t;
	}

	public int compareTo(IntegerTriple o) {
		if (!this.first().equals(o.first()))
			return this.first() - o.first();
		else if (!this.second().equals(o.second()))
			return this.second() - o.second();
		else
			return this.third() - o.third();
	}

	Integer first() { return _first; }
	Integer second() { return _second; }
	Integer third() { return _third; }
}
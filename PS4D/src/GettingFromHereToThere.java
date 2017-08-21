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
	private HashSet<Integer> taken;
	private HashSet<Integer> visited;
	private TreeSet<IntegerTriple> pq;
	private ArrayList<ArrayList<IntegerPair>> MstList;
	private int[][] qAns;
	private int currSource;


	public GettingFromHereToThere() {
	}

	void process(int vtx){
		taken.add(vtx);
		for(int j=0; j<AdjList.get(vtx).size(); j++){
			IntegerPair v = AdjList.get(vtx).get(j);
				if(!taken.contains(v.first())){
					pq.add(new IntegerTriple(v.second(), v.first(), vtx));  // we sort by weight then by adjacent vertex
			}
		}

	}
	
	void MstProcess(int vtx, int maxW){
		visited.add(vtx);
		for(int i=0; i<MstList.get(vtx).size(); i++){
			IntegerPair v = MstList.get(vtx).get(i);
			if(!visited.contains(v.second())){
				int w = Math.max(maxW, v.first());
				qAns[currSource][v.second()] = w;
				MstProcess(v.second(), w);
			}
		}
	}
	void PreProcess(){
		taken = new HashSet<Integer>();
		pq = new TreeSet<IntegerTriple>();
		MstList = new ArrayList<ArrayList<IntegerPair>>();
		for(int i=0; i<V; i++){
			MstList.add(new ArrayList<IntegerPair>());
		}
		process(0);

		while (!pq.isEmpty()) {
			IntegerTriple front = pq.pollFirst();
			if (!taken.contains(front.second())) {
				process(front.second());
				int vtx = front.third();
				MstList.get(vtx).add(new IntegerPair(front.first(), front.second()));
				MstList.get(front.second()).add(new IntegerPair(front.first(), vtx));
			}
		}
		
		int num = (V>10) ? 10 : V;
		qAns = new int[num][V];
		for(int k=0; k<num; k++){
			visited = new HashSet<Integer>();
			currSource = k;
			MstProcess(k, 0);
		}
	}

	int Query(int source, int destination) {
		return qAns[source][destination];
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
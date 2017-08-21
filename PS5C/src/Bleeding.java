// Copy paste this Java Template and save it as "Bleeding.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0147919N
// write your name here: Sneha Santha Prabakar
// write list of collaborators here: Baghabrah Dana Mohammad A
// year 2017 hash code: x8DYWsALaAzykZ8dYPZP (do NOT delete this line)

class Bleeding {
	private int V; // number of vertices in the graph (number of junctions in Singapore map)
	private int Q; // number of queries
	private ArrayList < ArrayList < IntegerPair > > AdjList; // the weighted graph (the Singapore map), the length of each edge (road) is stored here too, as the weight of edge
	private int[][] D;
	private int INF=10000000;
	private PriorityQueue < IntegerTriple > queue;
	int s, t, k;


	public Bleeding() {

	}

	void initSSSP(){
		for(int v =0; v<V; v++){
			for(int i=0; i<k+1; i++){
				D[v][i] = INF;	
			}
		}
		D[s][1]=0;
		queue.add(new IntegerTriple(0, 1, s));
	}

	void PreProcess() { 

	}

	int DJK(){
		D = new int[V][k+1];
		queue = new PriorityQueue < IntegerTriple > ();
		initSSSP();

		while(!queue.isEmpty()){
			IntegerTriple u = queue.poll();
			int weight = u.first();
			int count = u.second();
			int vtx = u.third();
			if((vtx==t) && (count<=k)){
				return D[vtx][count];
			}
			else if(count==k){
				continue;
			}

			for(int i=0; i<AdjList.get(vtx).size(); i++){

				int v = AdjList.get(vtx).get(i).first();
				int w = AdjList.get(vtx).get(i).second();
				
				if((D[vtx][count] + w)< (D[v][count+1])){
					D[v][count+1] = D[vtx][count] + w;
					queue.add(new IntegerTriple(D[v][count+1], count+1, v));
			}
		}
	}
		return -1;
}

int Query(int source, int destination, int kLimit) {
	 s = source;
	 t = destination;
	 k = kLimit;
	int ans = DJK();
	return ans;
}



void run() throws Exception {
	// you can alter this method if you need to do so
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
				AdjList.get(i).add(new IntegerPair(j, w)); // edge (road) weight (in minutes) is stored here
			}
		}

		PreProcess(); // optional

		Q = sc.nextInt();
		while (Q-- > 0)
			pr.println(Query(sc.nextInt(), sc.nextInt(), sc.nextInt()));

		if (TC > 0)
			pr.println();
	}

	pr.close();
}

public static void main(String[] args) throws Exception {
	// do not alter this method
	Bleeding ps5 = new Bleeding();
	ps5.run();
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
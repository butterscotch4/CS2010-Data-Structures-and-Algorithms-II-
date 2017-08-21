// Copy paste this Java Template and save it as "HospitalRenovation.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0147919N
// write your name here: Sneha Santha Prabakar
// write list of collaborators here: Dana Baghabarah
// year 2017 hash code: AlaYnzmQ65P4x2Uc559u (do NOT delete this line)

class HospitalRenovation {

	private int V; // number of vertices in the graph (number of rooms in the hospital)
	private int[][] AdjMatrix; // the graph (the hospital)
	private int[][] copy;
	private int[] RatingScore; // the weight of each vertex (rating score of each room)
	private int k=0;
	private int[] sortedRank;

	public HospitalRenovation() {

	}

	boolean BSF(){
		int[] visited = new int[V];
		int count = 0;

		for(int v = 0; v<V; v++){
			visited[v] = 0;
		}

		Queue<Integer> queue = new LinkedList<Integer>();

		if((k==0) && (V>1)){
			queue.add(1);
			visited[1] = 1;
		}
		else{
			queue.add(0);
			visited[0] = 1;
		}
		while(!queue.isEmpty()){
			int u = queue.poll();
			for(int i=0; i<V; i++){
				if(AdjMatrix[u][i] == 1){
					if(visited[i]==0){
						visited[i] = 1;
						//p[i] = u;
						queue.add(i);
					}
				}
			}
		}

		for(int j=0; j<visited.length; j++){
			if(visited[j]==1){
				count++;
			}
		}

		if(count==V){
			return false;
		}
		
		return true;
	}

	void sortRank(){
		for (int i = 1; i < sortedRank.length; i++) {
			for (int j = 0; j < sortedRank.length - i; j++) {
				if (sortedRank[j] > sortedRank[j+1]) { 
					int temp = sortedRank[j]; 
					sortedRank[j] = sortedRank[j+1]; 
					sortedRank[j+1] = temp; 
				}
			}
		}
	}

	int Query() {
		int ans = -1;

		sortRank();

		int j;

		int lowest = 0;
		int lowRank = 0;
		int count = 0;

		int[] alreadyVisited = new int[V];
		for(int i=0; i<V; i++)
			alreadyVisited[i]=0;

		copy = new int[V][V];

		boolean found = false;

		while((found==false) && (count<V)){
			lowest = sortedRank[lowRank];
			lowRank++;
			for(k=0; k<V; k++){
				if((RatingScore[k]==lowest) && (alreadyVisited[k]==0)){
					alreadyVisited[k]=1;
					break;
				}
			}
			for( j=0; j<V; j++){
				if(AdjMatrix[k][j]==1){
					copy[k][j]=1;
					AdjMatrix[k][j] = 0;
				}
			}


			if(BSF()==true){
				ans = RatingScore[k];
				found = true;
			}

			for(j=0; j<V; j++){
				if(copy[k][j]==1){
					AdjMatrix[k][j] = 1;
					copy[k][j] = 0;
				}
			}

			count++;
			k=0;

		}
		return ans;
	}


	void run() throws Exception {
		// for this PS3, you can alter this method as you see fit

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int TC = Integer.parseInt(br.readLine()); // there will be several test cases
		while (TC-- > 0) {
			br.readLine(); // ignore dummy blank line
			V = Integer.parseInt(br.readLine());

			StringTokenizer st = new StringTokenizer(br.readLine());
			// read rating scores, A (index 0), B (index 1), C (index 2), ..., until the V-th index
			RatingScore = new int[V];
			sortedRank = new int[V];
			for (int i = 0; i < V; i++){
				RatingScore[i] = Integer.parseInt(st.nextToken());
				sortedRank[i] = RatingScore[i];
			}

			// clear the graph and read in a new graph as Adjacency Matrix
			AdjMatrix = new int[V][V];
			for (int i = 0; i < V; i++) {
				st = new StringTokenizer(br.readLine());
				int k = Integer.parseInt(st.nextToken());
				while (k-- > 0) {
					int j = Integer.parseInt(st.nextToken());
					AdjMatrix[i][j] = 1; // edge weight is always 1 (the weight is on vertices now)
				}
			}

			pr.println(Query());
		}
		pr.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method
		HospitalRenovation ps3 = new HospitalRenovation();
		ps3.run();
	}
}

// Copy paste this Java Template and save it as "EmergencyRoom.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0147919N
// write your name here: Sneha Santha Prabakar
// write list of collaborators here:
// year 2017 hash code: oIxT79fEI2IQdQqvg1rx (do NOT delete this line)
public class EmergencyRoom {
class BinaryHeap {
	private ArrayList<Integer> A;
	private int BinaryHeapSize;

	BinaryHeap() {
		A = new ArrayList<Integer>();
		A.add(0); // dummy
		BinaryHeapSize = 0;
	}

	BinaryHeap(int[] array) {
		CreateHeap(array);
	}

	int parent(int i) { return i>>1; } // shortcut for i/2, round down

	int left(int i) { return i<<1; } // shortcut for 2*i

	int right(int i) { return (i<<1) + 1; } // shortcut for 2*i + 1

	void shiftUp(int i) {
		while (i > 1 && A.get(parent(i)) < A.get(i)) {
			int temp = A.get(i);
			A.set(i, A.get(parent(i)));
			A.set(parent(i), temp);
			i = parent(i);
		}
	}

	void Insert(int key) {
		BinaryHeapSize++;
		if (BinaryHeapSize >= A.size())
			A.add(key);
		else
			A.set(BinaryHeapSize, key);
		shiftUp(BinaryHeapSize);
	}

	void shiftDown(int i) {
		while (i <= BinaryHeapSize) {
			int maxV = A.get(i), max_id = i;
			if (left(i) <= BinaryHeapSize && maxV < A.get(left(i))) { // compare value of this node with its left subtree, if possible
				maxV = A.get(left(i));
				max_id = left(i);
			}
			if (right(i) <= BinaryHeapSize && maxV < A.get(right(i))) { // now compare with its right subtree, if possible
				maxV = A.get(right(i));
				max_id = right(i);
			}

			if (max_id != i) {
				int temp = A.get(i);
				A.set(i, A.get(max_id));
				A.set(max_id, temp);
				i = max_id;
			}
			else
				break;
		}
	}

	int ExtractMax() {
		int maxV = A.get(1);    
		A.set(1, A.get(BinaryHeapSize));
		BinaryHeapSize--; // virtual decrease
		shiftDown(1);  
		return maxV;
	}

	void CreateHeapSlow(int[] arr) { // the O(N log N) version, array arr is 0-based
		A = new ArrayList<Integer>();
		A.add(0); // dummy, this BinaryHeap is 1-based
		for (int i = 1; i <= arr.length; i++)
			Insert(arr[i-1]);
	}

	void CreateHeap(int[] arr) { // the O(N) version, array arr is 0-based
		BinaryHeapSize = arr.length;
		A = new ArrayList<Integer>();
		A.add(0); // dummy, this BinaryHeap is 1-based
		for (int i = 1; i <= BinaryHeapSize; i++) // copy the content
			A.add(arr[i-1]);
		for (int i = parent(BinaryHeapSize); i >= 1; i--)
			shiftDown(i);
	}

	ArrayList<Integer> HeapSort(int[] arr) { // array arr is 0-based
		CreateHeap(arr);
		int N = arr.length;
		for (int i = 1; i <= N; i++)
			A.set(N-i+1, ExtractMax());
		return A; // ignore the first index 0
	}

	int size() { return BinaryHeapSize; }

	boolean isEmpty() { return BinaryHeapSize == 0; }
}



	private int[] priorityArr;
	private BinaryHeap priority;
	private int index;
	private String[][] patients = new String [10][2];
	public EmergencyRoom() {
		priorityArr = new int[10];
		for(int j=0; j<10; j++){
			priorityArr[j]=0;
		}
		priority = new BinaryHeap(priorityArr);
		index = 0;
		for(int i=0; i<10; i++){
			patients[i][0] = "";
			patients[i][1] = "";
		}
	}

	void ArriveAtHospital(String patientName, int emergencyLvl) {
		if(index<10){
		patients[index][0] = patientName.toUpperCase();
		patients[index][1] = String.valueOf(emergencyLvl);
		priority.Insert(emergencyLvl);
		index++;
		}
	}

	void UpdateEmergencyLvl(String patientName, int incEmergencyLvl) {
		int count = 0;
		for(int i=0; i<patients.length; i++){

			if(patientName.equals(patients[i][0])){
				int oldEmergencyLvl = Integer.parseInt(patients[i][1]);
				int newEmergencyLvl = oldEmergencyLvl + incEmergencyLvl;
				patients[i][1]=""+newEmergencyLvl;
				BinaryHeap priority2 = new BinaryHeap(priorityArr);
				while(!priority.isEmpty()){
					int num = priority.ExtractMax();
					if(num==oldEmergencyLvl){
						count++;
						if(count>1){
							priority2.Insert(num);
						}
					}
					else{
						priority2.Insert(num);
					}
				}
				while(!priority2.isEmpty()){
					int num2 = priority2.ExtractMax();
					priority.Insert(num2);
				}
				priority.Insert(newEmergencyLvl);
			}
		}


	}


	void Treat(String patientName) {
		int count = 0;
		for(int i=0; i<patients.length; i++){
			if(patientName.equals(patients[i][0])){
				int oldPrio = Integer.parseInt(patients[i][1]);
				int j = i;
				while(j<(patients.length-1)){

						patients[j][0]=patients[j+1][0];
						patients[j][1]=patients[j+1][1];
						j++;
					
				}
				BinaryHeap priority3 = new BinaryHeap(priorityArr);
				while(!priority.isEmpty()){
					int num = priority.ExtractMax();
					if(num==oldPrio){
						count++;
						if(count>1){
							priority3.Insert(num);
						}
					}
					else{
						priority3.Insert(num);
					}
				}
				while(!priority3.isEmpty()){
					int num = priority3.ExtractMax();
					priority.Insert(num);
				}
				index--;
			}
		}
	}



	String Query() {
		String ans = "The emergency room is empty";
		int maxPriority = priority.ExtractMax();
		priority.Insert(maxPriority);
		int priority;
		for(int i=0; i<patients.length; i++){
			if(!patients[i][1].isEmpty()){
			
				priority = Integer.parseInt(patients[i][1]);
				if(maxPriority==priority){
					
					return patients[i][0];
				}
			}
		}
		return ans;
	}


	void run() throws Exception {
		// do not alter this method

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int numCMD = Integer.parseInt(br.readLine()); // note that numCMD is >= N
		while (numCMD-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int command = Integer.parseInt(st.nextToken());
			switch (command) {
			case 0: ArriveAtHospital(st.nextToken(), Integer.parseInt(st.nextToken())); break;
			case 1: UpdateEmergencyLvl(st.nextToken(), Integer.parseInt(st.nextToken())); break;
			case 2: Treat(st.nextToken()); break;
			case 3: pr.println(Query()); break;
			}
		}
		pr.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method
		EmergencyRoom ps1 = new EmergencyRoom();
		ps1.run();
	}
}

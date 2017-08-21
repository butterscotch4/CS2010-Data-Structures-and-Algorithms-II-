// Copy paste this Java Template and save it as "EmergencyRoom.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0147919N
// write your name here: Sneha Santha Prabakar
// write list of collaborators here: Dana Baghabrah, Anniya Baskaran, Rahumath Marini  
// year 2017 hash code: oIxT79fEI2IQdQqvg1rx (do NOT delete this line)
public class EmergencyRoom {
class Patient implements Comparable <Patient>{
	private String name = "";
	private int emergencyLvl = 0;
	private int arrNum = 0;

	public Patient(String name, int emergencyLvl, int arrNum){
		this.name = name;
		this.emergencyLvl = emergencyLvl;
		this.arrNum = arrNum;
	}

	public Patient(Patient otherPatient){
		this.name = otherPatient.getName();
		this.emergencyLvl = otherPatient.getEmergencyLvl();
		this.arrNum = otherPatient.getArrNum();
	}

	String getName(){
		return name;
	}

	int getEmergencyLvl(){
		return emergencyLvl;
	}

	int getArrNum(){
		return arrNum;
	}
	
	void setEmergencyLvl(int incrFactor){
		emergencyLvl = emergencyLvl + incrFactor;
	}
	
	public int compareTo (Patient otherPatient){
		int otherLvl = otherPatient.getEmergencyLvl();
		int otherArrNum = otherPatient.getArrNum();

		if(emergencyLvl==otherLvl){
			if(arrNum<otherArrNum){
				return 1; 
			}
			else{
				return -1;
			}
		}
		else if(emergencyLvl<otherLvl){
			return -1;
		}
		else if(emergencyLvl>otherLvl){
			return 1;
		}
		else
			return 0;


	}


}

	// if needed, declare a private data structure here that
	// is accessible to all methods in this class
	private static final int MAX_PATIENTS = 202000;
	private Patient[] heap;
	private int heapSize;
	private int index;
	private TreeMap<String, Integer> map;
	
	public EmergencyRoom() {
		heap = new Patient[MAX_PATIENTS];
		map = new TreeMap<String, Integer>();
		heapSize = 0;
		index = 0;
	}

	void ArriveAtHospital(String patientName, int emergencyLvl) {
		index++;
		Patient newPatient = new Patient(patientName, emergencyLvl, index);
		Insert(newPatient);
		map.put(patientName, heapSize);
	}

	public static int parent(int i){
		return i/2;
	}
	
	void shiftUp(int i) {
		while(i>1){
			if((heap[i].compareTo(heap[parent(i)])==1)){
				Patient temp = new Patient(heap[i]);
				heap[i] = heap[parent(i)];
				heap[parent(i)] = temp;
			//	i = parent(i);
				
				map.put(heap[parent(i)].getName(), parent(i));
				map.put(heap[i].getName(), i);
			}
			else if(heap[i].getEmergencyLvl() == heap[parent(i)].getEmergencyLvl()){
				if(heap[i].getArrNum() < heap[parent(i)].getArrNum()){
					Patient temp = new Patient(heap[i]);
					heap[i] = heap[parent(i)];
					heap[parent(i)] = temp;
					
					map.put(heap[parent(i)].getName(), parent(i));
					map.put(heap[i].getName(), i);
				}
			}
			else{
				break;
			}
			i = parent(i);

		}
	}
	
	private static int left(int i){
		return i<<1;
	}
	
	private static int right (int i){
		return (i<<1) + 1;
	}
	
	void shiftDown(int i){
		while(i<=heapSize){
			int maxPriority = heap[i].getEmergencyLvl();
			int maxPosition = i;
			int maxArrival = heap[i].getArrNum();
			if((left(i)<=heapSize) && (maxPriority<heap[left(i)].getEmergencyLvl())){
				maxPriority = heap[left(i)].getEmergencyLvl();
				maxPosition = left(i);
				maxArrival = heap[left(i)].getArrNum();
			}
			if((left(i)<=heapSize) && (maxPriority==heap[left(i)].getEmergencyLvl())){
				if((heap[left(i)].getArrNum()) < maxArrival){
					maxPriority = heap[left(i)].getEmergencyLvl();
					maxPosition = left(i);
					maxArrival = heap[left(i)].getArrNum();
				}
			}
			if((right(i)<=heapSize) && (maxPriority<heap[right(i)].getEmergencyLvl())){
				maxPriority = heap[right(i)].getEmergencyLvl();
				maxPosition = right(i);
				maxArrival = heap[right(i)].getArrNum();
			}
			if((right(i)<=heapSize) && (maxPriority == heap[right(i)].getEmergencyLvl())){
				if(heap[right(i)].getArrNum()<maxArrival){
					maxPriority = heap[right(i)].getEmergencyLvl();
					maxPosition = right(i);
					maxArrival = heap[right(i)].getArrNum();
				}
			}
			if(maxPosition != i){
				Patient temp = new Patient(heap[maxPosition]);
				heap[maxPosition] = heap[i];
				heap[i] = temp;
				map.put(heap[maxPosition].getName(), maxPosition);
				map.put(heap[i].getName(), i);
				i = maxPosition;
			}
			else{
				break;
			}
		}
	}
		

	void Insert(Patient key) {
		heapSize++;
		heap[heapSize] = key;
		shiftUp(heapSize);
	}

	
	void UpdateEmergencyLvl(String patientName, int incEmergencyLvl) {
		int index = map.get(patientName);
		int oldEmergencyLvl = heap[index].getEmergencyLvl();
		heap[index].setEmergencyLvl(incEmergencyLvl);
		
		shiftUp(index);
	}

	void Treat(String patientName) {
		int treatedPatientIndex = map.get(patientName);
		heap[treatedPatientIndex] = heap[heapSize];
		heapSize--;
		map.remove(patientName);
		map.put(heap[treatedPatientIndex].getName(), treatedPatientIndex);
		
		if(treatedPatientIndex>1 && heap[treatedPatientIndex].getEmergencyLvl()>heap[parent(treatedPatientIndex)].getEmergencyLvl()){
			shiftUp(treatedPatientIndex);
		}
		else{
			shiftDown(treatedPatientIndex);
		}
		
	}

	String Query() {
		String ans = "The emergency room is empty";

		if(heapSize>0){
			return heap[1].getName();
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
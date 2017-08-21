// Copy paste this Java Template and save it as "EmergencyRoom.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0147919N
// write your name here: Sneha Santha Prabakar
// write list of collaborators here:
// year 2017 hash code: oIxT79fEI2IQdQqvg1rx (do NOT delete this line)

class EmergencyRoom {

	private int[] priorityArr;
	private BinaryHeap priority;
	//private ArrayList<String> names = new ArrayList<String>();
	private int index;
	private String[][] patients = new String [10][2];
	public EmergencyRoom() {
		priorityArr = new int[10];
		priority = new BinaryHeap(priorityArr);
		index = 0;
		for(int i=0; i<patients.length; i++){
			patients[i][0] = "";
			patients[i][1] = "";
		}
	}

	void ArriveAtHospital(String patientName, int emergencyLvl) {
		/*	String prioq = String.valueOf(emergencyLvl);
		String patient = patientName + "," + prioq;
		names.add(patient);
		priorityArr[index] = emergencyLvl;
		index++;
		priority.Insert(emergencyLvl);
		 */
		patients[index][0] = patientName;
		patients[index][1] = String.valueOf(emergencyLvl);
		priority.Insert(emergencyLvl);
		index++;
	}

	void UpdateEmergencyLvl(String patientName, int incEmergencyLvl) {

		for(int i=0; i<patients.length; i++){

			if(patientName.equals(patients[i][0])){
				int oldEmergencyLvl = Integer.parseInt(patients[i][1]);
				patients[i][1]=patients[i][1]+incEmergencyLvl;
				int newEmergencyLvl = Integer.parseInt(patients[i][1]);
				BinaryHeap priority2 = new BinaryHeap(priorityArr);
				while(!priority.isEmpty()){
					int num = priority.ExtractMax();
					if(num !=  oldEmergencyLvl){
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
		for(int i=0; i<patients.length; i++){
			if(patientName.equals(patients[i][0])){
				int oldPrio = Integer.parseInt(patients[i][1]);
				int j = i;
				while(j<(patients.length-2)){

					if(patients[j+1][0].isEmpty()){
						patients[j][0]="";
						patients[j][1]="";
					}
					else{
						patients[j][0]=patients[j+1][0];
						patients[j][1]=patients[j+1][1];
						j++;
					}
				}
					BinaryHeap priority3 = new BinaryHeap(priorityArr);
					while(!priority.isEmpty()){
						int num = priority.ExtractMax();
						if(num!=oldPrio){
							priority3.Insert(num);
						}
					}
					while(!priority3.isEmpty()){
						int num = priority3.ExtractMax();
						priority.Insert(num);
					}
				}
			}
		}
	


		String Query() {
			String ans = "The emergency room is empty";
			int maxPriority = priority.ExtractMax();
			int priority;
			for(int i=0; i<patients.length; i++){
				//String priorityStr = patients[i][1];
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

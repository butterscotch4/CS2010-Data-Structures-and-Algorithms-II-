// Copy paste this Java Template and save it as "PatientNames.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0147919N	
// write your name here: Sneha Santha Prabakar
// write list of collaborators here: Baghabrah Dana Mohammad A
// year 2017 hash code: DZAjKugdE9QiOQKGFbut (do NOT delete this line)

class PatientNames {
   
	TreeSet<String> malePatients;
	TreeSet<String> femalePatients;
	int count;
	

  public PatientNames() {
   malePatients = new TreeSet<String>();
   femalePatients = new TreeSet<String>();
   count =0;
  }

  void AddPatient(String patientName, int gender) {
    if(gender == 1){
    	malePatients.add(patientName);
    }
    else if(gender == 2){
    	femalePatients.add(patientName);
    }
  }

  void RemovePatient(String patientName) {
	
    malePatients.remove(patientName);
    femalePatients.remove(patientName);
  }

  int Query(String START, String END, int gender) {
   // int ans = 0;

    if(gender == 1){
    	return malePatients.subSet(START, END).size();
    }
    else if(gender == 2){
    	return femalePatients.subSet(START, END).size();
    }
    else{
    	count = (malePatients.subSet(START, END).size()) + (femalePatients.subSet(START, END).size());
    	return count;
    }

   // return ans;
  }

  void run() throws Exception {
    // do not alter this method to avoid unnecessary errors with the automated judging
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    while (true) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int command = Integer.parseInt(st.nextToken());
      if (command == 0) // end of input
        break;
      else if (command == 1) // AddPatient
        AddPatient(st.nextToken(), Integer.parseInt(st.nextToken()));
      else if (command == 2) // RemovePatient
        RemovePatient(st.nextToken());
      else // if (command == 3) // Query
        pr.println(Query(st.nextToken(), // START
                         st.nextToken(), // END
                         Integer.parseInt(st.nextToken()))); // GENDER
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method to avoid unnecessary errors with the automated judging
    PatientNames ps2 = new PatientNames();
    ps2.run();
  }
}
// Copy paste this Java Template and save it as "PatientNames.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0147919N	
// write your name here: Sneha Santha Prabakar
// write list of collaborators here: Baghabrah Dana Mohammad A
// year 2017 hash code: DZAjKugdE9QiOQKGFbut (do NOT delete this line)

class PatientNames {

/*...................................BSTVeretex Class begins here.................................*/
	
	// Every vertex in this BST is a Java Class
	
	class BSTVertex {
		// all these attributes remain public to slightly simplify the code
		public BSTVertex parent, left, right;
		public String key;
		public int height;
		public int size; // will be used in AVL lecture

		BSTVertex(String v) { 
			key = v; 
			parent = left = right = null; 
			height = 0; 
			size = 1; 
		}
		public void setSize(int size){
			this.size = size;
		}
		public int getHeight(){
				return height;
		}
		public void setHeight(int height){
			this.height = height;
		}
	}


/* .....................................BST Class begins here.....................................*/
	class BST {
		protected BSTVertex root;

		public BST() { root = null; }

		// method called to search for a value v 
		public String search(String v) {
			BSTVertex res = search(root, v);
			return res == null ? "Not found" : res.key;
		}

		// overloaded recursive method to perform search
		protected BSTVertex search(BSTVertex T, String v) {
			if (T == null)  return null;                     // not found
			else if (T.key.equals(v)) return T;                        // found
			else if (T.key.compareTo(v) < 0)  return search(T.right, v);       // search to the right
			else                 return search(T.left, v);        // search to the left
		}

		// public method called to find Minimum key value in BST
		public String findMin() { return findMin(root); }

		// overloadded recursive method to perform findMin
		protected String findMin(BSTVertex T) {
			if (T == null)      throw new NoSuchElementException("BST is empty, no minimum");
			else if (T.left == null) return T.key;                    // this is the min
			else                     return findMin(T.left);           // go to the left
		}

		// public method called to find Maximum key value in BST
		public String findMax() { return findMax(root); }

		// overloadded recursive method to perform findMax
		protected String findMax(BSTVertex T) {
			if (T == null)       throw new NoSuchElementException("BST is empty, no maximum");
			else if (T.right == null) return T.key;                   // this is the max
			else                      return findMax(T.right);        // go to the right
		}

		// public method to find successor to given value v in BST
		public String successor(String v) { 
			BSTVertex vPos = search(root, v);
			return vPos == null ? "Successor not found" : successor(vPos);
		}

		// overloaded recursive method to find successor to for a given vertex T in BST
		protected String successor(BSTVertex T) {
			if (T.right != null)                       // this subtree has right subtree
				return findMin(T.right);  // the successor is the minimum of right subtree
			else {
				BSTVertex par = T.parent;
				BSTVertex cur = T;
				// if par(ent) is not root and cur(rent) is its right children
				while ((par != null) && (cur==par.right)) {
					cur = par;                                         // continue moving up
					par = cur.parent;
				}
				
				return par == null ? "Successor not found" : par.key;           // this is the successor of T
			}
		}

		// public method to find predecessor to given value v in BST
		public String predecessor(String v) { 
			BSTVertex vPos = search(root, v);
			return vPos == null ? "Predecessor not found" : predecessor(vPos);
		}

		// overloaded recursive method to find predecessor to for a given vertex T in BST
		protected String predecessor(BSTVertex T) {
			if (T.left != null)                         // this subtree has left subtree
				return findMax(T.left);  // the predecessor is the maximum of left subtree
			else {
				BSTVertex par = T.parent;
				BSTVertex cur = T;
				// if par(ent) is not root and cur(rent) is its left children
				while ((par != null) && (cur==par.left)) { 
					cur = par;                                         // continue moving up
					par = cur.parent;
				}
				return par == null ? "Predecessor not found" : par.key;           // this is the successor of T
			}
		}

		// public method called to perform inorder traversal
		public void inorder() { 
			inorder(root);
			System.out.println();
		}

		// overloaded method to perform inorder traversal
		protected void inorder(BSTVertex T) {
			if (T == null) return;
			inorder(T.left);                               // recursively go to the left
			System.out.printf(" %s", T.key);                      // visit this BST node
			inorder(T.right);                             // recursively go to the right
		}

		// method called to insert a new key with value v into BST
		public void insert(String v) { root = insert(root, v); }

		// overloaded recursive method to perform insertion of new vertex into BST
		protected BSTVertex insert(BSTVertex T, String v) {
			if (T == null) 
				return new BSTVertex(v);          // insertion point is found

			if (T.key.compareTo(v)<0) {                                      // search to the right
				T.right = insert(T.right, v);
				T.right.parent = T;
			}
			else {                                                 // search to the left
				T.left = insert(T.left, v);
				T.left.parent = T;
			}

			return T;                                          // return the updated BST
		}  

		// public method to delete a vertex containing key with value v from BST
		public void delete(String v) { root = delete(root, v); }

		// overloaded recursive method to perform deletion 
		protected BSTVertex delete(BSTVertex T, String v) {
			if (T == null)  return T;              // cannot find the item to be deleted

			if (T.key.compareTo(v)<0)                                    // search to the right
				T.right = delete(T.right, v);
			else if (T.key.compareTo(v)>0)                               // search to the left
				T.left = delete(T.left, v);
			else {                                            // this is the node to be deleted
				if (T.left == null && T.right == null)                   // this is a leaf
					T = null;                                      // simply erase this node
				else if (T.left == null && T.right != null) {   // only one child at right        
					T.right.parent = T.parent;
					T = T.right;                                                 // bypass T        
				}
				else if (T.left != null && T.right == null) {    // only one child at left        
					T.left.parent = T.parent;
					T = T.left;                                                  // bypass T        
				}
				else {                                 // has two children, find successor
					String successorV = successor(v);
					T.key = successorV;         // replace this key with the successor's key
					T.right = delete(T.right, successorV);      // delete the old successorV
				}
			}

			return T;                                          // return the updated BST
		}

		// will be used in AVL lecture
		protected int getHeight(BSTVertex T) {
			if (T == null) return -1;
			//else return Math.max(getHeight(T.left), getHeight(T.right)) + 1;
			else return T.height;
		}

		public int getHeight() { return getHeight(root); }
		
		protected int getSize(BSTVertex T){
			if(T==null)
				return 0;
			else
				return T.size;
		}
		
		public int getSize(){
			return getSize(root);
		}
	}
/*................................AVLTree Class begins here...................................... */	
	class AVLTree extends BST{
		
		public AVLTree(){
//			root = null;
		}
		
		//Override
		protected BSTVertex insert(BSTVertex vertex, String patientName){
			if(vertex == null){
				return new BSTVertex(patientName);
			}
			if(vertex.key.compareTo(patientName)<0){
				vertex.right = insert(vertex.right, patientName);
				vertex.right.parent = vertex;
				vertex.size++;
			}
			else{
				vertex.left = insert(vertex.left, patientName);
				vertex.left.parent = vertex;
				vertex.size++;
			}
			heightUpdate(vertex);
			
		//	int currBF = getBalanceFactor(vertex);
//			int leftBF = getBalanceFactor(vertex.left);
//			int rightBF = getBalanceFactor(vertex.right);
			
			//case 1
			if(getBalanceFactor(vertex) == 2){
				//case 1.1
				if(getBalanceFactor(vertex.left)==1){
					vertex = rotateRight(vertex);
					return vertex;
				}
				
				//case 1.2
				
				if(getBalanceFactor(vertex.left)==-1){
					vertex.left = rotateLeft(vertex.left);
					vertex = rotateRight(vertex);
					return vertex;
				}
			}
			
			//case 2
			if(getBalanceFactor(vertex) == -2){
				//case 2.1
				if(getBalanceFactor(vertex.right) == -1){
					vertex = rotateLeft(vertex);
					return vertex;
				}
				//case 2.2
				
				if(getBalanceFactor(vertex.right) == 1){
					vertex.right = rotateRight(vertex.right);
					vertex = rotateLeft(vertex);
					return vertex;
				}
			}
			return vertex;
		}
		
		protected BSTVertex delete(BSTVertex vertex, String patientName) {
	        if (vertex == null) return vertex;
	        if (vertex.key.equals(patientName)) {
	            if (vertex.left == null && vertex.right == null)
	                vertex = null;
	            else if (vertex.left == null && vertex.right != null) {
	                BSTVertex temp = vertex;
	                vertex.right.parent = vertex.parent;
	                vertex = vertex.right;
	                temp = null;
	                vertex = updateAfterDelete(vertex);

	            } else if (vertex.left != null && vertex.right == null) {
	                BSTVertex temp = vertex;
	                vertex.left.parent = vertex.parent;
	                vertex = vertex.left;
	                temp = null;
	                vertex = updateAfterDelete(vertex);
	            } else {
	                String successorV = successor(patientName);
	                vertex.key = successorV;
	                vertex.right = delete(vertex.right, successorV);
	                vertex = updateAfterDelete(vertex);
	            }
	        } else if (vertex.key.compareTo(patientName) < 0) {
	            vertex.right = delete(vertex.right, patientName);
	            vertex = updateAfterDelete(vertex);
	        } else {
	            vertex.left = delete(vertex.left, patientName);
	            vertex = updateAfterDelete(vertex);
	        }

	        return vertex;
	    }
		
		private BSTVertex updateAfterDelete(BSTVertex vertex) {
	        heightUpdate(vertex);
	        sizeUpdate(vertex);
	        if (getBalanceFactor(vertex) == 2) {
	            if (getBalanceFactor(vertex.left) == 1) {
	                vertex = rotateRight(vertex);
	            }
	            if (getBalanceFactor(vertex.left) == -1) {
	                vertex.left = rotateLeft(vertex.left);
	                vertex = rotateRight(vertex);
	            }
	        }
	        if (getBalanceFactor(vertex) == -2) {
	            if (getBalanceFactor(vertex.right) == -1) {
	                vertex = rotateLeft(vertex);
	            }
	            if (getBalanceFactor(vertex.right) == 1) {
	                vertex.right = rotateRight(vertex.right);
	                vertex = rotateLeft(vertex);
	            }
	        }
	        return vertex;
	    }
		
	    public void delete(String patientName) {
	        root = this.delete(root, patientName);
	    }
		
		private void heightUpdate(BSTVertex vertex){
			int defaultHeight = -1;
			int leftHeight = 0;
			int rightHeight=0;
			
			if(vertex.left!=null){
				leftHeight = vertex.left.getHeight();
			}
			else{
				leftHeight = defaultHeight;
			}
			if(vertex.right!=null){
				rightHeight = vertex.right.getHeight();
			}
			else{
				leftHeight = defaultHeight;
			}
			
			vertex.setHeight(Math.max(leftHeight, rightHeight) + 1);
		}
		
		private void sizeUpdate(BSTVertex vertex){
			if(vertex == null){
				vertex.setSize(0);
			}
			//int defaultSize = 0;
			int leftSize = 0;
			int rightSize = 0;
			if(vertex.left != null){
				leftSize = vertex.left.size;
			}
			
			if(vertex.right != null){
				rightSize = vertex.right.size;
			}

			
			vertex.setSize(leftSize + rightSize + 1);
		}
		
		BSTVertex rotateLeft (BSTVertex vertex){
			BSTVertex w = vertex.right;
			w.parent = vertex.parent;
			vertex.parent = w;
			vertex.right = w.left;
			if(w.left!=null){
				w.left.parent = vertex;
			}
			w.left = vertex;
			
			heightUpdate(vertex);
			heightUpdate(w);
			
			sizeUpdate(vertex);
			sizeUpdate(w);
			
			return w;
		}
		
		BSTVertex rotateRight (BSTVertex vertex){
			BSTVertex w = vertex.left;
			w.parent = vertex.parent;
			vertex.parent = w;
			vertex.left = w.right;
			if(w.right != null){
				w.right.parent = vertex;
			}
			
			w.right = vertex;
			
			heightUpdate(vertex);
			heightUpdate(w);
			
			sizeUpdate(vertex);
			sizeUpdate(w);
			
			return w;
		}
		
		protected int getBalanceFactor(BSTVertex vertex){
			int leftHeight;
			int rightHeight ;
			int defaultHeight = -1;
			
			if(vertex.left != null){
				leftHeight = vertex.left.getHeight();
			}
			else{
				leftHeight = defaultHeight;
			}
			if(vertex.right != null){
				rightHeight = vertex.right.getHeight();
			}
			else{
				rightHeight = defaultHeight;
			}
			
			int BF = leftHeight - rightHeight;
			
			return BF;
		}
		protected int getSize(BSTVertex vertex){
			int leftSize;
			int rightSize;
			int defaultSize = 0;
			
			if(vertex.left != null){
				leftSize = vertex.left.size;
			}
			else
				leftSize = defaultSize;
			if(vertex.right != null){
				rightSize = vertex.right.size;
			}
			else
				rightSize = defaultSize;
			
			int vSize = leftSize+rightSize+1;
			
			return vSize;
		}
		
		protected int rank(BSTVertex vertex, String START){
			if(vertex==null){
				return 0;
			}
			if(START.compareTo(vertex.key)<=0){
				return rank(vertex.left, START);
			}
			else{
				if(vertex.left==null)
					return rank(vertex.right, START) + 1;
				else
					return (vertex.left.size) + rank(vertex.right, START) + 1;
			}
		}
		
		protected int rank(String START){
			return rank(root, START);
		}
		
	}

	
/*............................PatientName Class begins here.......................................*/
	private AVLTree malePatients;
	private AVLTree femalePatients;
	


	public PatientNames() {
		malePatients = new AVLTree();
		femalePatients = new AVLTree();
	}


	void AddPatient(String patientName, int gender) {
		if(gender==1){
			malePatients.insert(patientName);
		}
		
		else{
			femalePatients.insert(patientName);
		}
	}

	void RemovePatient(String patientName) {
		malePatients.delete(patientName);
		femalePatients.delete(patientName);
	}

	int Query(String START, String END, int gender) {
		// int ans = 0;
		int count = 0;

		if(gender == 1){
			return subSetSize(START, END, 1);
		}
		else if(gender == 2){
			return subSetSize(START, END, 2);
		}
		else{
			count = Math.abs(subSetSize(START, END, 1) + subSetSize(START, END, 2));
			return count;
		}

		// return ans;
	}
	
	int subSetSize(String START, String END, int gender){
		int ans = 0;
		if(gender==1)
			ans = malePatients.rank(END)  - malePatients.rank(START);
		
		else 
			ans = femalePatients.rank(END) - femalePatients.rank(START);
		
		ans = Math.abs(ans);
		
		return ans;
		
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
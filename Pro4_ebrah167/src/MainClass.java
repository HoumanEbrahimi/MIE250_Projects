import java.util.ArrayList;
import java.io.*;

public class MainClass {
	public static BufferedReader inpt;
	

	public static void main(String[] args) throws IOException,NumberFormatException {
		// initialize variables 
		boolean play=true,valEntered=false,polyEntered=false,paramChanged=false;
		String choice;
		int nums=0,degree=0,nIter=100;double [][]coefficients=new double[nums][degree];double coef;
		double get_points=0;
		int tracker=0;
		double starting_point= 1.0;
		double epsilon=0.001,stepSize=0.05;
		// create objects of classes here in order to modify them later on
		Polynomial p= new Polynomial(nums,degree,coefficients);
		SteepestDescent sd = new SteepestDescent(epsilon,nIter,stepSize,starting_point);
		ArrayList <Polynomial> polynomial=new ArrayList<Polynomial>();
		inpt= new BufferedReader (new InputStreamReader(System.in));

		while (play) { // while the user can input values 
			
				 displayMenu(); // display the menu
				 choice=inpt.readLine();
				 System.out.println("");
				 if (choice.equals("Q") || choice.equalsIgnoreCase("q")) { // player quits 
		     		 System.out.println("Fin.");
					 play=false;
				 } else if (choice.equals("L") || choice.equalsIgnoreCase("l")) { // input polynomial
					 loadPolynomialFile(polynomial);
					 
					 polyEntered=true;

					 
				 }
				 else if (choice.equals("C") || choice.equalsIgnoreCase("c")) {
					 clearPolynomials(polynomial); // delet all polynomials 
					 System.out.println("All polynomials cleared.");
					 System.out.println("");
				 }
				  else if (choice.equals("F") || choice.equalsIgnoreCase("f")) { // display polynomials 
					  printPolynomials(polynomial); // print polynmial
					  //System.out.println("");
				  }
				  else if (choice.equals("S")|| choice.equalsIgnoreCase("s")) { // input algorthm parameters

						  getAlgorithmParams(sd,p.getN()); // call the function to get parameters 
						  sd.init(polynomial);
						  valEntered=false;

					//  }
					  
				 } else if (choice.equals("P")|| choice.equalsIgnoreCase("p")) {

					 sd.print(); // prints the algo parameters
				 } else if (choice.equals("R")|| choice.equalsIgnoreCase("r")) { // calls run
					 if(polynomial.size()==0 ) { // check if there is a polynomial 
						 System.out.println("ERROR: No polynomial functions are loaded!");
						 System.out.println("");

					 }
					 else { // inilization 
						 sd.init(polynomial);
						 tracker=0;
						 // for each polynomial, apply the run function 
						 for(Polynomial P:polynomial) {
							 sd.run(tracker,P);
							 tracker++;
						 }
						valEntered=true;
						System.out.println("");
						System.out.println("All polynomials done.");
						System.out.println("");
					 }
				 }
					 else if (choice.equals("D")|| choice.equalsIgnoreCase("d")) { // print best results if a result exits 
						if(valEntered==true && tracker==polynomial.size()) {
							System.out.println("Detailed results:");
							System.out.println("-------------------------------------------------------------------------");
							System.out.println("Poly no.         f(x)   norm(grad)   # iter   Comp time (ms)   Best point   ");
							System.out.println("-------------------------------------------------------------------------");
							 int index2=0;
							 sd.printAll(); // print the detailed result 
							 //for(Polynomial P:polynomial) {
							//	 sd.printSingleResult(index2,true);
							//	 index2++;

							 //}
							System.out.println("");
							System.out.println("Statistical summary:");
							System.out.println("---------------------------------------------------");
							System.out.println("          norm(grad)       # iter    Comp time (ms)");
							System.out.println("---------------------------------------------------");
							// print the statistical summary 

							sd.printStats();

						}
						else {
							System.out.println("ERROR: No results exist!");
						}

						
						 System.out.println("");
					 }
					 
		     	 else if (choice.equals("Q")|| choice.equalsIgnoreCase("q")) {
		     		 System.out.println("Fin.");
					 play=false;
					 
				 }
				 else {
					 System.out.println("ERROR: Invalid menu choice!");
					 System.out.println("");
					}

			}
		}
	// function that displays the menu 
	public static void displayMenu() {
		 System.out.println("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)");
		 System.out.println("L - Load polynomials from file");
		 System.out.println("F - View polynomial functions");
		 System.out.println("C - Clear polynomial functions");
		 System.out.println("S - Set steepest descent parameters");
		 System.out.println("P - View steepest descent parameters");
		 System.out.println("R - Run steepest descent algorithm");		
		 System.out.println("D - Display algorithm performance");		
		 System.out.println("Q - Quit\n");
		 System.out.print("Enter choice: ");
	} // function that gets polynomial detals 
	public static boolean getPolynomialDetails (Polynomial P) throws IOException{
		int nums=0,degree=0;double [][]coefficients=new double[nums][degree];double coef; // details of a polynomial 

		 nums=getInteger("Enter number of variables (0 to cancel): ",0,Integer.MAX_VALUE); // get user parameters 
		 if(nums==0) { // if input is zero, then the user does not want to enter a poylnomail
			 System.out.println("");
			 System.out.println("Process canceled. No changes made to polynomial function.");
			 System.out.println("");

			 return false;

		 }
		 else {
			 degree=getInteger("Enter polynomial degree (0 to cancel): ",0,Integer.MAX_VALUE); // get user degree

		 }

		 if(nums!=0 && degree==0) { // if degree is zero
			 System.out.println("");
			 System.out.println("Process canceled. No changes made to polynomial function.");
			 System.out.println("");

			 return false;
		 }
		 else {
			 coefficients=new double [nums][degree+1]; // get coefficients for the polynomial
			 //  set variables of the polynomial 
			P.setN(nums);
			P.setDegree(degree);
			P.init();
		 	for(int i=0;i<nums;i++) { // enter coefficients into an array and store them 
			 	System.out.println("Enter coefficients for variable x"+(i+1)+": ");
			 	for(int j=0;j<degree+1;j++) {
				 	coef=getDouble("   Coefficient "+(j+1)+": ",-Double.MAX_VALUE,Double.MAX_VALUE); // use the get double function
				 	P.setCoef(i,j,coef); // set the coefficient inside the class
				 	coefficients[i][j]=coef;
				 	
			 	}
		 	}
		 	System.out.println("");
		 	System.out.println("Polynomial complete!");
		 	System.out.println("");


		 }	
		return true;
	}
	// function where it gets user parameter for the algorithm
	public static boolean getAlgorithmParams(SteepestDescent SD, int n) throws NumberFormatException, IOException {
		double starting_point= 1.0;
        // default parameters for the algorithm function 
		int nIter=100;
		double get_points=0;
		double epsilon=0.001,stepSize=0.05;
		// get epsilon values from the user 
		epsilon=getDouble("Enter tolerance epsilon (0 to cancel): ",0.00,Double.MAX_VALUE);
		if(epsilon==0) {
			 System.out.println("");

			 System.out.println("Process canceled. No changes made to algorithm parameters.");
			 System.out.println("");

			 return false;
		}
		else {
			nIter=getInteger("Enter maximum number of iterations (0 to cancel): ",0,10000); // get iterations
		}

		if(nIter==0) { // check if user inputs zero
			 System.out.println("");
			 System.out.println("Process canceled. No changes made to algorithm parameters.");
			 System.out.println("");

			 return false;
		}
		else {
			stepSize=getDouble("Enter step size alpha (0 to cancel): ",0.00,Double.MAX_VALUE);
		}
		if(stepSize==0) {
			 System.out.println("");

			 System.out.println("Process canceled. No changes made to algorithm parameters.");
			 System.out.println("");
			 return false;
		}
		else { // enter values for the starting point 
			//System.out.println("Enter values for starting point: ");
		  		// set parameters to the steepestDescent class 
			get_points=getDouble("Enter value for starting point (0 to cancel): ",-Double.MAX_VALUE,Double.MAX_VALUE);
			
			if(get_points==0) {
				 System.out.println("");
				 System.out.println("Process canceled. No changes made to algorithm parameters.");
				 System.out.println("");

				 return false;
			}
			else {
				SD.setEps(epsilon);
				SD.setMaxIter(nIter);
				SD.setStepSize(stepSize);

				starting_point=get_points;
				 SD.originalResults(get_points);
				 SD.getParamsUser();
				 SD.setX0(get_points);
				 System.out.println("");
				 System.out.println("Algorithm parameters set!");
				 System.out.println("");
			}
				
		}


		return true;
		
	}
	
	public static void clearPolynomials(ArrayList<Polynomial> P) {
		P.clear();
	}
	public static void printPolynomials(ArrayList<Polynomial> P) {
		if(P.size()==0) {
			System.out.println("ERROR: No polynomial functions are loaded!\n");
		}
		else {
			int numofPoly=1;
			
			System.out.println("---------------------------------------------------------");
			System.out.println("Poly No.  Degree   # vars   Function");
			System.out.println("---------------------------------------------------------");
			for(int i=0;i<P.size();i++) {
				System.out.printf("%8d",numofPoly);
				numofPoly++;
				P.get(i).print();
				
			}
			System.out.println("");	

		}
	  }
	
	
	public static boolean loadPolynomialFile(ArrayList<Polynomial> P) throws IOException {
		Polynomial p;
		boolean checkVar=true;
		boolean loaded=true;
		int n=0,degree=0,numofPoly=0,index=0,index2=0;
		int numberofObject=0;
		
		System.out.print("Enter file name (0 to cancel): ");
		String file=inpt.readLine(); // user enters the file
		int value=0;
		File tempFile=new File(file);

		String line;
		String [] elements = null;
		double [][] coefficients=new double [n][degree];
		boolean proceed=true;
		// create arraylists to hold the coefficients and the degrees 
		ArrayList<Double> elementNum=new ArrayList<Double>();
		ArrayList<Integer> degreeNum=new ArrayList<Integer>();
		if(file.equals("0")) { // if the user cancels polynomial loading by entering 0
			System.out.println("");
			System.out.println("File loading process canceled.");
			System.out.println("");
			return false; // cancel the process
		}
		else if(tempFile.exists()==false) { // if a file is not found
			System.out.println("");
			System.out.println("ERROR: File not found!");
			
		}
		else if(file.length()==0) { // if the file is empty or has no polynomial
			System.out.println("");
			System.out.println("0 polynomials added!");
			System.out.println("");
		}
		else {
			BufferedReader file_loading=new BufferedReader(new FileReader(file)); // load the file

			do {
				line=file_loading.readLine(); // read the lines from the file
				
				if(line!=null) {// check if line is not equal to line
					elements=line.split(",");  // make the line into a list 
					// create a for loop that iterates through the line and gets the degree of each line 
					for(int i=0;i<elements.length && line.contains("*")==false;i++) {
						if (i==elements.length-1){
							n++;
						}
						// get the coefficients from each line 
						elementNum.add(Double.parseDouble(elements[i]));
						degree=elements.length-1;
						index2++;
					}
					if(line.contains("*")==false) { // if we hit *, add the degree 
						degreeNum.add(elements.length);

					}
					if(line.contains("*") && line.charAt(0)=='*') { // if line contains * as its first element
						for(Integer a:degreeNum) {  // check for matching degrees otherwise print the inconsistent message 
							if(!a.equals(degreeNum.get(0))) {
								proceed=false;
							}
						}
						value++;
					 // if polynomial is valid 
						if(proceed==true) {
							double [][] coef=new double [n][degree+1];
							for(int i=0;i<n;i++) {
								for(int j=0;j<degree+1;j++) {
									coef[i][j]=elementNum.get(index);
									
									index++;
								}
							}
							p=new Polynomial(n,degree,coef); // create the polynomial
							//  set degree and number of variable 
							p.setN(n);
							p.setDegree(degree);
							// add the polynomial into the arrayList 
							P.add(p);
							index=0;
							index2=0;
							degree=0;
							n=0;	
							numberofObject++;
							// reset the arraylists 
							elementNum=new ArrayList<Double>();
							degreeNum= new ArrayList<Integer>();
						}
						else { // the polynomial is inconsistent 
							elementNum=new ArrayList<Double>();
							degreeNum= new ArrayList<Integer>();

							n=0;
							proceed=true;
							System.out.println("");
							System.out.println("ERROR: Inconsistent dimensions in polynomial "+value+"!");
						}
					}

				}
				else { // if we reach the last polynomial in the file 
					if(checkVar==true) {
						if(n==0) { // check if n is zero 
							checkVar=false;
						}
						else { // check if the polynomial is inconsistent 
							for(Integer a:degreeNum) {
								if(!a.equals(degreeNum.get(0))) {
									proceed=false;
								}
							}
							value++;
							if(proceed==true) { // if polynomial is valid 
								// enter the coefficients of the polynomial 
								double [][] coef=new double [n][degree+1];
								for(int i=0;i<n;i++) {
									for(int j=0;j<degree+1;j++) {
										coef[i][j]=elementNum.get(index);
										index++;
									}
								}
								// create the polynomial and set the degree and number of variables 
								p=new Polynomial(n,degree,coef);
								p.setN(n);
								p.setDegree(degree);
								P.add(p);
								index=0;
								index2=0;
								degree=0;
								n=0;	
								numberofObject++;
								// re initialize the arraylist 
								elementNum=new ArrayList<Double>();
								degreeNum=new ArrayList<Integer>();
							}

							else { // give the error message is degrees don't match 
								System.out.println("");
								System.out.println("ERROR: Inconsistent dimensions in polynomial "+value+"!");
							}

						}
						proceed=true;
						if(line==null) {
							break;
						}
						}
						
					}

			}while(line!=null);


		}
		// as the final message, let the user know how many polynomials are loaded 
		System.out.println("");
		if(value>1) {
			System.out.println(numberofObject+" polynomials loaded!");	
			System.out.println("");
		}

		return loaded;
	}

	// get integer inputs 
	public static int getInteger(String prompt, int LB, int UB){
		int val=0;
		while(true) {
			try {
				System.out.print(prompt);
				val = Integer.parseInt(inpt.readLine()); // gets user input 
				if (val >= LB && val <= UB) { //  check the bounday 
					break;
				}
				else { // appropriate message if the integer is not in the bound 
					if(UB==Integer.MAX_VALUE && LB==-Integer.MAX_VALUE) {
						System.out.println("ERROR: Input must be an integer in [-infinity, infinity]!");

					}
					else if (UB==Integer.MAX_VALUE && LB!=-Integer.MAX_VALUE) {
						System.out.println("ERROR: Input must be an integer in [" + LB + ", infinity]!");

					}
					else {
						System.out.println("ERROR: Input must be an integer in [" + LB + ", " + UB + "]!");
					}

					System.out.println("");
					
				}
			}
			catch(NumberFormatException | IOException e){
				if(UB==Integer.MAX_VALUE && LB==-Integer.MAX_VALUE) {
					System.out.println("ERROR: Input must be an integer in [-infinity, infinity]!");

				}
				else if (UB==Integer.MAX_VALUE && LB!=-Integer.MAX_VALUE) {
					System.out.println("ERROR: Input must be an integer in [" + LB + ", infinity]!");

				}
				else {
					System.out.println("ERROR: Input must be an integer in [" + LB + ", " + UB + "]!");
				}

				System.out.println("");

			}
		}
		return val;
	}
	// gets double input 
	public static double getDouble(String prompt, double LB, double UB) throws NumberFormatException, IOException {
		double val=0;
		while(true) {
			try {
				System.out.print(prompt);
				val = Double.parseDouble(inpt.readLine()); // gets user input 
				if (val >= LB && val <= UB || Double.isNaN(val)) { //  check the bounday 
					break;
				}
				else { // appropriate message if the integer is not in the bound 
					if(UB==Double.MAX_VALUE && LB==-Double.MAX_VALUE) {
						System.out.println("ERROR: Input must be a real number in [-infinity, infinity]!");

					}
					else if (UB==Double.MAX_VALUE && LB!=-Double.MAX_VALUE) {
						System.out.println("ERROR: Input must be a real number in [" + String.format("%.2f", LB)+ ", infinity]!");

					}
					else {
						System.out.println("ERROR: Input must be a real number in [" +String.format("%.2f", LB)+ ", " + String.format("%.2f", UB) + "]!");
					}

					System.out.println("");
					
				}
			}
			catch(NumberFormatException | IOException e){ // when a user inputs string or characters?
				if(UB==Double.MAX_VALUE && LB==-Double.MAX_VALUE) {
					System.out.println("ERROR: Input must be a real number in [-infinity, infinity]!");

				}
				else if (UB==Double.MAX_VALUE && LB!=-Double.MAX_VALUE) {
					System.out.println("ERROR: Input must be a real number in [" + String.format("%.2f", LB) + ", infinity]!");

				}
				else {
					System.out.println("ERROR: Input must be a real number in [" + String.format("%.2f", LB) + ", " + String.format("%.2f", UB) + "]!");
				}

				System.out.println("");
				
			}
		}
		return val;
		
	}

}

//C:\Users\houma\eclipse-workspace\Pro4_ebrah167\input_file.txt
//C:\Users\houma\eclipse-workspace\Pro4_ebrah167\input_file2.txt
//C:\Users\houma\eclipse-workspace\Pro4_ebrah167\qweqweqw.txt
//C:\USERS\HOUMA\ECLIPSE-WORKSPACE\PRO4_EBRAH167\File4.txt
//C:\Users\houma\eclipse-workspace\Pro4_ebrah167\test.txt

//C:\Users\houma\eclipse-workspace\Pro4_ebrah167\goodman.txt


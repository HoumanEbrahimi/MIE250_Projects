import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
//////////////////////////////
//// Houman Ebrahimi
/// Project 3
//////////////////////////////
public class Pro3_ebrah167 {
	public static BufferedReader inpt;
	public static void main(String[] args){
		// initialize variables 
		boolean play=true,valEntered=false,polyEntered=false,paramEntered=false;
		String choice;
		int nums=0,degree=0,nIter=100;double [][]coefficients=new double[nums][degree];double coef;
		double get_points=0;
		double [] starting_point= {1.0};
		double epsilon=0.001,stepSize=0.05;
		// create objects of classes here in order to modify them later on
		Polynomial p= new Polynomial(nums,degree,coefficients);
		SteepestDescent sd = new SteepestDescent(epsilon,nIter,stepSize,starting_point);
		inpt= new BufferedReader (new InputStreamReader(System.in));
		//System.out.println("");

		while (play) { // while the user can input values 
			 try {
				 displayMenu(); // display the menu
				 choice=inpt.readLine();
				 System.out.println("");
				 if (choice.equals("Q") || choice.equalsIgnoreCase("q")) { // player quits 
		     		 System.out.println("The end.");
					 play=false;
				 } else if (choice.equals("E") || choice.equalsIgnoreCase("e")) { // input polynomial variables 

					 getPolynomialDetails(p); // call the function 
					 polyEntered=true;

					 
				 }
				  else if (choice.equals("F") || choice.equalsIgnoreCase("f")) { // display polynomials 
					 if (p.isSet()) { // check if there is a polynomial to start with
						 p.print(); 
					 }
					 else {
						 System.out.println("ERROR: Polynomial function has not been entered!\n");
						 //System.out.println("");

					 }
				  }
				  else if (choice.equals("S")|| choice.equalsIgnoreCase("s")) { // input algorthm parameters
					  if(p.getN()==0) {
						  System.out.println("ERROR: Polynomial function has not been entered!");
						  System.out.println("");

					  }
					  else {
						  getAlgorithmParams(sd,p.getN()); // call the function to get parameters 

					  }
					  
				 } else if (choice.equals("P")|| choice.equalsIgnoreCase("p")) {

					 sd.print(); // prints the algo parameters
				 } else if (choice.equals("R")|| choice.equalsIgnoreCase("r")) { // calls run
					 if(p.isSet()==false) { // check if there is a polynomial 
						 System.out.println("ERROR: Polynomial function has not been entered!");
						 System.out.println("");

					 }
					 else {
	
						sd.run(p);
						//System.out.println("");
						valEntered=true;
					 }
				 }
					 else if (choice.equals("D")|| choice.equalsIgnoreCase("d")) { // print best results if a result exits 
						if(valEntered==true) {
							System.out.println("--------------------------------------------------------------");
							System.out.println("      f(x)   norm(grad)   # iter   Comp time (ms)   Best point   ");
							System.out.println("--------------------------------------------------------------");
						}
						sd.printResults(valEntered);
						
						 System.out.println("");
					 }
					 
		     	 else if (choice.equals("Q")|| choice.equalsIgnoreCase("q")) {
		     		 System.out.println("The end.");
					 play=false;
					 
				 }
				 else {
					 System.out.println("ERROR: Invalid menu choice!");
					 System.out.println("");
					}
				} // if none of what user inputs matches, then no menu option is selected 
				catch (IOException e) {
				 System.out.println(""); 
				 System.out.println("ERROR: Invalid menu choice!");
				 System.out.println("");
				 
				}
			}
		}
	// function that displays the menu 
	public static void displayMenu() {
		 System.out.println("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)");
		 System.out.println("E - Enter polynomial function");
		 System.out.println("F - View polynomial function");
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
		double [] starting_point= {1.0};
        // default parameters for the algorithm function 
		int nIter=100;
		double get_points=0;
		double epsilon=0.001,stepSize=0.05;
		starting_point=new double[n];
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
			 System.out.println("Process canceled. No changes made to algorithm parameters.");
			 System.out.println("");
			 return false;
		}
		else { // enter values for the starting point 
			System.out.println("Enter values for starting point: ");
			SD.setEps(epsilon);
			SD.setMaxIter(nIter);
			SD.setStepSize(stepSize);
			SD.init(n);
		  	for(int i=0;i<n;i++) {
		  		// set parameters to the steepestDescent class 
			  get_points=getDouble("   x"+(i+1)+": ",-Double.MAX_VALUE,Double.MAX_VALUE);
			  starting_point[i]=get_points;
			  SD.originalResults(i,get_points);
			  SD.setX0(i,get_points);
			  SD.getParamsUser(n);
			 
		  	}		}
		System.out.println("");
		System.out.println("Algorithm parameters set!");
		System.out.println("");

		return true;
		
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

	 


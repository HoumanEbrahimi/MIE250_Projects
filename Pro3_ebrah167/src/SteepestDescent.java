import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class SteepestDescent {
	private double eps ; // tolerance
	private int maxIter ; // maximum number of iterations
	private double stepSize ; // step size alpha
	private double [] x0 ; // starting point
	private double [] bestPoint ; // best point found
	private double bestObjVal ; // best obj fn value found
	private double bestGradNorm ; // best gradient norm found
	private double [] resultPoints;
	private double []originalResults= {1.0}; // original starting points 
	private double [] copy_x0=x0;
	private long compTime ; // computation time needed
	private int nIter ; // no. of iterations needed
	private boolean resultsExist ; // whether or not results exist

	
	public SteepestDescent () {
		this.eps=0;this.maxIter=0;this.stepSize=0;
		this.bestObjVal=0;
		this.bestGradNorm=0;
		this.compTime=0;
		this.nIter=0;
		this.resultsExist=false;
		this.nIter=0;
		this.bestObjVal=0;
		this.bestGradNorm=0;
		//this.bestPoint=this.x0;
		//this.originalResults=this.x0;
		
		
	}
	
	public SteepestDescent ( double eps , int maxIter , double stepSize , double [] x0 ) {
		this.eps=eps;
		this.maxIter=maxIter;
		this.stepSize=stepSize;
		this.x0=x0;
		//this.bestPoint=x0;
		this.resultPoints=x0;

		
	}

	// getters 
	public double getEps () {
		return this.eps;
	}
	public int getMaxIter () {
		return this.maxIter;
	}
	public double getStepSize () {
		return this.stepSize;
	}
	public double [] getX0 () {
		return this.x0;
	}
	public double getBestObjVal () {
		return this.bestObjVal;
	}
	public double getBestGradNorm () {
		return this.bestGradNorm;
	}
	public double [] getBestPoint () {
		return this.bestPoint;
	}
	public int getNIter () {
		return this.nIter;
	}
	public long getCompTime () {
		return this.compTime;
	}

	public boolean hasResults () {
		return this.resultsExist;
	}
	
	// setters
	public void setEps ( double a ) {
		this.eps=a;
	}
	public void setMaxIter ( int a ) {
		this.maxIter=a;
	}
	public void setStepSize ( double a ) {
		this.stepSize=a;
	}
	public void setX0 ( int j , double a ) {
		this.x0[j]=a;
		
	}
	public void originalResults(int j, double a) { // sets original starting points 
		this.originalResults[j]=a;

	}
	public void setBestObjVal ( double a ) {
		this.bestObjVal=a;
	}
	public void setBestGradNorm ( double a ) {
		this.bestGradNorm=a;
	}
	public void setBestPoint ( double [] a ) {
		this.bestPoint=a;
	}
	public void setCompTime ( long a ) {
		this.compTime=a;
	}
	public void setNIter ( int a ) {
		this.nIter=a;
	}
	public void setHasResults ( boolean a ) {
		this.resultsExist=a;
	}
	
	// other methods
	public void init ( int n ) {// initialize member arrays to correct size
		this.x0=new double [n];
		this.bestPoint= new double [n];
		this.originalResults=new double[n];
	} 

	
	  public void run ( Polynomial P ) {// run the steepest descent algorithm
		  long start = System . currentTimeMillis () ; // Get current time
// make a copy of the best point array
		  this.bestPoint=Arrays.copyOf(this.x0,this.x0.length);
		  if((this.bestPoint.length==1 && this.bestPoint[0]==1) || this.bestPoint.length!=P.getN()) { // check original length of best points

			  if(this.bestPoint.length!=P.getN()) {  // set warning if dimensions don't match 
				  System.out.println("WARNING: Dimensions of polynomial and x0 do not match! Using x0 = 1-vector of appropriate dimension."); 
				  System.out.println("");
			  } // adjust the dimensions based on the new number of variables 
			  this.bestPoint=new double[P.getN()]; this.x0=new double[P.getN()];
			  // initialize each point to 1 as the default value
			  for(int i=0;i<P.getN();i++) { 
				  this.bestPoint[i]=1.0; this.x0[i]=1.0; 
			  } 
		  }
		  // find the value of gradient using best points
		  double [] gradient_vals=P.gradient(this.bestPoint); boolean run2=true; 
		  System.out.println("--------------------------------------------------------------");
		  System.out.println("      f(x)   norm(grad)   # iter   Comp time (ms)   Best point   ");
		  System.out.println("--------------------------------------------------------------"); 
		  for(int i=0;i<this.maxIter && run2==true;i++) { // main loop for iteraitons 
			  this.compTime=System . currentTimeMillis () -start ; // Get elapsed time in ms

			  //System.out.print(" "+String.format("%.6f",P.f(this.bestPoint))+"    "+String.format("%.6f",P.gradientNorm(this.bestPoint))+"        "+i+"         "+this.compTime+"     "); 
			  System.out.printf("%10.6f%13.6f%9d%17d   ",P.f(this.bestPoint),P.gradientNorm(this.bestPoint),i,this.compTime);
			  gradient_vals=P.gradient(this.bestPoint); // get the gradient values 

			  for(int j=0;j<x0.length;j++) { // calculate best points 

				  System.out.print(String.format("%.4f",this.bestPoint[j]));  // print
				  if(j!=x0.length-1) { 
					  System.out.print(", "); 
				  }
				  // calculation for best points 
				  this.bestPoint[j]=this.bestPoint[j]+this.stepSize*-1*gradient_vals[j];

			  } 
			  // check if the user has reached the stopping criterias and save the best results 
			  if(P.gradientNorm(this.bestPoint)<this.eps || i==this.maxIter-1 || Double.isNaN(P.gradientNorm(this.bestPoint))) {
				  this.bestObjVal=P.f(this.bestPoint);
				  this.bestGradNorm=P.gradientNorm(this.bestPoint);
				  this.resultPoints=this.bestPoint;
				  // result exists 
				  this.nIter=i+1; this.resultsExist=true; System.out.println("");
				  //printResults(this.resultsExist); //his.x0=this.originalResults;
				  if(this.bestGradNorm==0) {
					  break;
				  }
				  else {
					  printResults(this.resultsExist); //his.x0=this.originalResults;
 
				  }
				  //his.bestPoint=this.originalResults;

				  run2=false;
			  } else { 
				  run2=true;

			  }

			  System.out.println("");

		  }

		 // System.out.println("");

	  }
	 
	// returns the step size 
	public double lineSearch () {// find the next step size
		return this.stepSize;
	}
	// negative of the gradient 
	public double [] direction ( Polynomial P , double [] x ) {// find the next direction
		double [] gradient= P.gradient(x);
		
		for(int i=0;i<gradient.length;i++) {
			gradient[i]*=-1;
		}
		return gradient;
	} 
	public void getParamsUser ( int n ) {// get params from user for n- dim polynomial
		setEps(this.eps);
		setMaxIter(this.maxIter);
		setStepSize(this.stepSize);
		
		this.bestPoint=this.x0;
		
		
	} 
	public void print () {
		// print algorithm parameters
		System.out.println("Tolerance (epsilon): "+this.eps);
		System.out.println("Maximum iterations: "+this.maxIter);
		System.out.println("Step size (alpha): "+this.stepSize);
		System.out.print("Starting point (x0): ( ");
		for(int i=0;i<this.originalResults.length;i++) {
			System.out.print(String.format("%.2f",this.originalResults[i]));
			if(i!=this.originalResults.length-1) {
				System.out.print(", ");

			}
		}
		System.out.print(" )");
		
		System.out.println("\n");
		
	}
	public void printResults ( boolean rowOnly ) {
		// print iteration results , column
		if(rowOnly==true) { // if the program has run, print the best results 
			//System.out.print(" "+String.format("%.6f",this.bestObjVal)+"    "+String.format("%.6f",this.bestGradNorm)+"        "+this.nIter+"         "+this.compTime+"     ");
			System.out.printf("%10.6f%13.6f%9d%17d   ",this.bestObjVal,this.bestGradNorm,this.nIter,this.compTime);
			for(int i=0;i<this.resultPoints.length;i++) {
			    System.out.print(String.format("%.4f",this.resultPoints[i]));
				if (i!=this.resultPoints.length-1) {
					System.out.print(", ");
				}
			}
			System.out.println("");
		}
		else {
			System.out.println("ERROR: No results exist!"); //  if no result exists 
			
		}
		//System.out.println("");
		
	}
	//header optional

}


//
//f(x) = ( 0.60x1^5 + -5.70x1^4 + 1.50x1^3 + 6.70x1^2 + -2.80x1^1 + -7.60 ) + ( -9.80x2^5 + -10.20x2^4 + 5.40x2^3 + 7.80x2^2 + 1.20x2^1 + 8.90 ) + ( 1.20x3^5 + 6.70x3^4 + 1.00x3^3 + 3.00x3^2 + 2.00x3^1 + 5.00 ) + ( -0.70x4^5 + -0.45x4^4 + -0.76x4^3 + 1.00x4^2 + 4.00x4^1 + 5.00 ) + ( 3.00x5^5 + -5.60x5^4 + 1.00x5^3 + 5.00x5^2 + 6.70x5^1 + 4.00 ) + ( 3.00x6^5 + 1.00x6^4 + 2.00x6^3 + 6.00x6^2 + 5.00x6^1 + 3.00 )
//Tolerance (epsilon): 0.5674
//Maximum iterations: 150
//Step size (alpha): 4.56
//Starting point (x0): ( -3.40, -0.90, 5.40, 2.00, 3.00, -9.80 )

//
//
//
//
//
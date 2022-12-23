import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class SteepestDescent {
	private double eps ; // tolerance
	private int maxIter ; // maximum number of iterations
	private double stepSize ; // step size alpha
	private double  x0 ; // starting point
	Stats s=new Stats();
	
	private ArrayList<double[]> bestPoint;
	private double [] bestObjVal; // best obj fn value found
	private double [] bestGradNorm ; // best gradient norm found
	private ArrayList<double[]> resultPoints=new ArrayList<double[]>();
	private double originalResults= 1.0; // original starting points 
	private long []compTime ; // computation time needed
	private int [] nIter ; // no. of iterations needed
	private boolean resultsExist ; // whether or not results exist

	
	public SteepestDescent () {
		// set components to zero 
		this.eps=0;this.maxIter=0;
		this.stepSize=0;
		this.resultsExist=false;

		
		
	}
	
	public SteepestDescent ( double eps , int maxIter , double stepSize , double  x0 ) {
		this.eps=eps;
		this.maxIter=maxIter;
		this.stepSize=stepSize;
		this.x0=x0;
		this.originalResults=x0;
		
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
	public double  getX0 () {
		return this.x0;
	}
	public double [] getBestObjVal () {
		return this.bestObjVal;
	}
	public double [] getBestGradNorm () {
		return this.bestGradNorm;
	}
	public double [] getBestPoint (int i) {
		return this.bestPoint.get(i);
	}
	public int  [] getNIter () {
		return this.nIter;
	}
	public long [] getCompTime () {
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
	public void setX0 (double a ) {
		this.x0=a;
		
	}
	public void originalResults(double a) { // sets original starting points 
		this.originalResults=a;

	}
	public void setBestObjVal (int i, double a ) {
		this.bestObjVal[i]=a;
	}
	public void setBestGradNorm (int i, double a ) {
		this.bestGradNorm[i]=a;
	}
	public void setBestPoint (int i, double [] a ) {

		this.bestPoint.set(i,a);
	}
	public void setCompTime (int i, long a ) {
		this.compTime[i]=a;
	}
	public void setNIter (int i,int a ) {
		this.nIter[i]=a;
	}
	public void setHasResults ( boolean a ) {
		this.resultsExist=a;
	}
	
	// other methods
	public void init (ArrayList < Polynomial > P) {// initialize member arrays to correct size
		int index=0;
		int index2=0;
		double [] j;
		// set up the components for compTime, bestObj val and bestGradNorm
		this.compTime=new long [P.size()];
		this.bestObjVal=new double [P.size()];
		this.bestGradNorm=new double [P.size()];
		this.nIter=new int [P.size()];
		// create the arrays inside the arraylist 
		this.bestPoint=new ArrayList<double[]>();
		for(int a=0;a<P.size();a++) {
			this.bestPoint.add(a,new double[P.get(a).getN()]);
		

		}
		// populate the ararylist with the appropriate number of bestPoints 
		for(int i=0;i<P.size();i++) {
				for(int b=0;b<P.get(i).getN();b++) {
			
					(this.bestPoint.get(i))[b]=x0;				
				}
		}
		
	} 


	  public void run ( int i, Polynomial P ) {// run the steepest descent algorithm
		  long start = System . currentTimeMillis () ; // Get current time
//		  // find the value of gradient using best points
//
		  double [] gradient_vals=P.gradient(this.bestPoint.get(i)); 
		  boolean run2=true; 
		  for(int b=0;b<this.maxIter && run2==true;b++) { // main loop for iteraitons 
			  this.compTime[i]=System.currentTimeMillis () -start ; // Get elapsed time in ms
			  
			  gradient_vals=P.gradient(this.bestPoint.get(i)); // get the gradient values 

			  if(P.gradientNorm(this.bestPoint.get(i))<this.eps) { // if norm is smaller than epsilon 
				  run2=false;
				  this.bestObjVal[i]=P.f(this.bestPoint.get(i));
				  this.bestGradNorm[i]=P.gradientNorm(this.bestPoint.get(i));
				  this.resultPoints=this.bestPoint;
//				  // result exists 
				  this.nIter[i]=b; 
				  this.resultsExist=true; 
				  if(this.bestGradNorm[i]==0) {
					  break;
				  }
//
			  } else if (Double.isNaN(P.gradientNorm(this.bestPoint.get(i)))) { 
				  run2=false;
				  this.bestObjVal[i]=P.f(this.bestPoint.get(i));
				  this.bestGradNorm[i]=P.gradientNorm(this.bestPoint.get(i));
				  this.resultPoints=this.bestPoint;
//				  // result exists 
				  this.nIter[i]=b; 
				  this.resultsExist=true; 
//
				  if(this.bestGradNorm[i]==0) {
					  break;
				  }
//			  
			  }
			  else {
//				  
				  gradient_vals=P.gradient(this.bestPoint.get(i)); // get the gradient values 
// 
				  run2=true;
				  for(int j=0;j<this.bestPoint.get(i).length;j++) { // calculate best points 
//					  // calculation for best points 
					  (this.bestPoint.get(i))[j]=(this.bestPoint.get(i))[j]+this.stepSize*-1*gradient_vals[j];
//
				  } 
				  // print the bestpoints, best gradnorm etc
				  this.bestObjVal[i]=P.f(this.bestPoint.get(i));
				  this.bestGradNorm[i]=P.gradientNorm(this.bestPoint.get(i));
				  this.resultPoints=this.bestPoint;
//				  // result exists 
				  this.nIter[i]=b+1; 
				  this.resultsExist=true;
			  }

		  }
		  System.out.println("Polynomial "+(i+1)+" done in "+ this.compTime[i]+"ms.");

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
	public void getParamsUser () {// get params from user for n- dim polynomial
		setEps(this.eps);
		setMaxIter(this.maxIter);
		setStepSize(this.stepSize);
		
		//this.bestPoint=this.x0;
		
		
	} 
	public void print () {
		// print algorithm parameters
		System.out.println("Tolerance (epsilon): "+this.eps);
		System.out.println("Maximum iterations: "+this.maxIter);
		System.out.println("Step size (alpha): "+this.stepSize);
		System.out.print("Starting point (x0):"+this.originalResults);

		System.out.println("\n");
	}
	
	public void printStats () {
		// create a double for iterations and compTime
		double [] iterations= new double [this.nIter.length];
		double [] computation= new double [this.compTime.length];
		for(int i=0;i<this.nIter.length;i++) {
			iterations[i]=this.nIter[i];
			
		}
		for(int i=0;i<this.compTime.length;i++) {
			computation[i]=this.compTime[i];
		}
		// print the statistical summary 
		System.out.printf("Average %12.3f%13.3f%18.3f\n",s.average(this.bestGradNorm),s.average(iterations),s.average(computation));
		System.out.printf("St Dev %13.3f%13.3f%18.3f\n",s.StandardDev(this.bestGradNorm),s.StandardDev(iterations),s.StandardDev(computation));
		System.out.printf("Min %16.3f%13d%18d\n",s.min(this.bestGradNorm),s.min(this.nIter),s.min(this.compTime));
		System.out.printf("Max %16.3f%13d%18d\n",s.max(this.bestGradNorm),s.max(this.nIter),s.max(this.compTime));

		
		// print statistical summary of results
	}
	public void printAll () {
		//System.out.println("Detailed results:");
		//System.out.println("");
		for(int i=0;i<this.bestPoint.size();i++) { // print each detailed result
			printSingleResult(i,true);
		}
	}
	public void printSingleResult ( int i , boolean rowOnly ) {
		
		if(rowOnly==true) { // if the program has run, print the best results 
			System.out.printf("%8d%13.6f%13.6f%9d%17d   ",(i+1),this.bestObjVal[i],this.bestGradNorm[i],this.nIter[i],this.compTime[i]);
			for(int j=0;j<this.resultPoints.get(i).length;j++) {
				  System.out.print(String.format("%.4f",(this.resultPoints.get(i)[j])));  // print
				if (j!=this.resultPoints.get(i).length-1) {
					System.out.print(", ");
				}
			}
			System.out.println("");
		}
		else {
			System.out.println("ERROR: No results exist!"); //  if no result exists 
			
		}

	}

	//header optional
}



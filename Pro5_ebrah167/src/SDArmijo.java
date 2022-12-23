import java.io.IOException;
import java.math.*;

public class SDArmijo extends SteepestDescent{
	private double maxStep ; // Armijo max step size
	private double beta ; // Armijo beta parameter
	private double tau ; 
	//private double alpha;
	double epsilon, x0;
	int maxIter;

	private int K ; // Armijo max no. of iterations
	
	 // constructors
	public SDArmijo () {
		this.maxStep=1.0;
		this.beta=1.0E-4;
		this.tau=0.5;
		this.K=10;
		//this.alpha=this.maxStep;
		this.epsilon=0.001;
		this.x0=1;
		this.maxIter=100;
	}
	public SDArmijo ( double maxStep , double beta , double tau , int K ) {
		this.maxStep=maxStep;
		this.beta=beta;
		this.tau=tau;
		this.K=K;
		//this.alpha=maxStep;
		this.epsilon=0.001;
		this.x0=1;
		this.maxIter=10;
	}
	
	 // getters
	public double getMaxStep () {
		return this.maxStep;
	}
	public double getBeta () {
		return this.beta;
	}
	public double getTau () {
		return this.tau;
	}
	public int getK () {
		return this.K;
	}
	
	 // setters
	public void setMaxStep ( double a ) {
		this.maxStep=a;
	}
	public void setBeta ( double a ) {
		this.beta=a;
	}
	public void setTau ( double a ) {
		this.tau=a;
	}
	public void setK ( int a ) {
		this.K=a;
	}
	
	 // other methods

	public double lineSearch ( Polynomial P , double [] x ) {
		double alpha=this.maxStep;
		int kIter=0; // use to check for the convergence error
		alpha=this.maxStep;
		double [] result= new double [x.length];
		double [] diffX= new double [x.length];
		diffX=direction(P,x); // direction of each iteration
		// calculate the left side of the equation
		for(int i=0;i<x.length;i++) {
			result[i]=x[i]+alpha*diffX[i];
		}
		// for loop that iterates and multiplies alpha by tau until the two conditions are meet
		for(int j=0;j<this.K && P.f(result)>P.f(x)-alpha*this.beta*Math.pow(P.gradientNorm(x),2) && alpha<=this.maxStep;j++) {
			alpha*=this.tau;
			for(int i=0;i<x.length;i++) { // again update the 
				result[i]=x[i]+alpha*diffX[i];
			}
			kIter++;
		}
		if(kIter==this.K) { // check if armijo converges 
			alpha=-1.0;
			System.out.println("   Armijo line search did not converge!");
		}
		return alpha;
	}
	// gets user parameters 
	public boolean getParamsUser () throws IOException{
		double a=0;double b=0;double c=0;int d=0;double e=0;double f=0;int g=0;double h=0;
		// get algorithm parameters from user
		System.out.println("Set parameters for SD with an Armijo line search:");
		a=Pro5_ebrah167.getDouble("Enter Armijo max step size (0 to cancel): ",0.00,Double.MAX_VALUE);
		if(a==0) {
			 System.out.println("");
			 System.out.println("Process canceled. No changes made to algorithm parameters.");
			 return false;
		}
		else {
			b=Pro5_ebrah167.getDouble("Enter Armijo beta (0 to cancel): ",0.0,1.0);
		}
		
		if(b==0) {
			 System.out.println("");
			 System.out.println("Process canceled. No changes made to algorithm parameters.");

			return false;
		}
		else {
			c=Pro5_ebrah167.getDouble("Enter Armijo tau (0 to cancel): ",0.0,1.0);
		}
		
		if(c==0) {
			 System.out.println("");
			 System.out.println("Process canceled. No changes made to algorithm parameters.");

			 return false;
		}
		else {
			d=Pro5_ebrah167.getInteger("Enter Armijo K (0 to cancel): ",0,Integer.MAX_VALUE);
		}
		
		if(d==0) {
			 System.out.println("");
			 System.out.println("Process canceled. No changes made to algorithm parameters.");

			 return false;
		}
		else {
			f=Pro5_ebrah167.getDouble("Enter tolerance epsilon (0 to cancel): ",0.00,Double.MAX_VALUE); // get iterations

		}

		if(f==0) { // check if user inputs zero
			 System.out.println("");
			 System.out.println("Process canceled. No changes made to algorithm parameters.");

			 return false;
		}
		else {
			g=Pro5_ebrah167.getInteger("Enter maximum number of iterations (0 to cancel): ",0,10000);

		}
		
		if(g==0) {
			System.out.println("");
			 System.out.println("Process canceled. No changes made to algorithm parameters.");

			 return false;
		}
		else {
			h=Pro5_ebrah167.getDouble("Enter value for starting point (0 to cancel): ",-Double.MAX_VALUE,Double.MAX_VALUE);
	
		}			
		if(h==0) {
			System.out.println("");
			System.out.println("Process canceled. No changes made to algorithm parameters.");

			return false;
		}
		else {
			super.setEps(f);
			super.setMaxIter(g);
			super.setX0(h);
			super.originalResults(h);
			setMaxStep(a);
			setBeta(b);
			setTau(c);
			setK(d);
			System.out.println("");
			System.out.println("Algorithm parameters set!");
			return true;
		}
	}
	// print user parameters 
	public void print () {
		System.out.println("SD with an Armijo line search:");
		System.out.println("Tolerance (epsilon): "+super.getEps());
		System.out.println("Maximum iterations: "+super.getMaxIter());
		System.out.println("Starting point (x0): "+super.getX0());
		System.out.println("Armijo maximum step size: "+getMaxStep());
		System.out.println("Armijo beta: "+getBeta());
		System.out.println("Armijo tau: "+getTau());
		System.out.println("Armijo maximum iterations: "+getK());
	}
}

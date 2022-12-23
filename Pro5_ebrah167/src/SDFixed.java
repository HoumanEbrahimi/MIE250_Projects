import java.io.IOException;

public class SDFixed extends SteepestDescent{
	 private double alpha ; // fixed step size
	  double epsilon=0, x0=0;
	  int maxIter=0;

	 public SDFixed () {
		 this.alpha=0.01;
	 }
	 public SDFixed ( double alpha ) {
		 this.alpha=alpha;
	 }
	 
	  // getters
	  public double getAlpha () {
		  return this.alpha;
	  }
	 
	  // setters
	  public void setAlpha ( double a ) {
		  this.alpha=a;
	  }
	 
	  // other methods
	  public double lineSearch ( Polynomial P , double [] x ) {
		  	
			return alpha;
	  }
	  public boolean getParamsUser () throws IOException{
		  double a=0;double b=0;int c=0;double d=0;
		  System.out.println("Set parameters for SD with a fixed line search:");
		  a=Pro5_ebrah167.getDouble("Enter fixed step size (0 to cancel): ",0.00,Double.MAX_VALUE);
			if(a==0) {
				 System.out.println("");
				 System.out.println("Process canceled. No changes made to algorithm parameters.");
				// System.out.println("");
				 return false;
			}
			else {
				b=Pro5_ebrah167.getDouble("Enter tolerance epsilon (0 to cancel): ",0.00,Double.MAX_VALUE); // get iterations
	
			}
			if(b==0) { // check if user inputs zero
				 System.out.println("");
				 System.out.println("Process canceled. No changes made to algorithm parameters.");
				// System.out.println("");
				 return false;
			}
			c=Pro5_ebrah167.getInteger("Enter maximum number of iterations (0 to cancel): ",0,10000);
			
			if(c==0) {
				System.out.println("");
				 System.out.println("Process canceled. No changes made to algorithm parameters.");
				 //System.out.println("");
				 return false;
			}
			else {
				d=Pro5_ebrah167.getDouble("Enter value for starting point (0 to cancel): ",-Double.MAX_VALUE,Double.MAX_VALUE);

			}
				
			if(d==0) {
				System.out.println("");
				System.out.println("Process canceled. No changes made to algorithm parameters.");
				//System.out.println("");
				return false;
			}
		  super.setEps(b);
		  super.setMaxIter(c);
		  super.setX0(d);
		  super.originalResults(d);
		  setAlpha(a);
		  System.out.println("");
		  System.out.println("Algorithm parameters set!");
		  //System.out.println("");
		  return true;

	  }
	  public void print () {
		  System.out.println("SD with a fixed line search:");
		  System.out.println("Tolerance (epsilon): "+super.getEps());
		  System.out.println("Maximum iterations: "+super.getMaxIter());
		  System.out.println("Starting point (x0): "+super.getX0());
		  System.out.println("Fixed step size (alpha): "+this.alpha);
		  
	  }
}

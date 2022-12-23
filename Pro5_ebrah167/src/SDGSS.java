import java.io.IOException;

public class SDGSS extends SteepestDescent{
	private final double _PHI_ = (1. + Math . sqrt (5) ) /2.;
	private double maxStep ; // Armijo max step size
	private double minStep ; // Armijo beta parameter
	private double delta ; // Armijo delta parameter
	double epsilon=0, x0=0;
	int maxIter=0;
	 // constructors
	public SDGSS () {
		this.maxStep=1;
		this.minStep=0.001;
		this.delta=0.001;
	}
	public SDGSS ( double maxStep , double minStep , double delta ) {
		this.maxStep=maxStep;
		this.minStep=minStep;
		this.delta=delta;
	}
	public void originalParams() {
		this.maxStep=1;
		this.minStep=0.001;
		this.delta=0.001;
		this.epsilon=0.001;
		this.x0=1;
		this.maxIter=100;
	}
	 // getters
	public double getMaxStep () {
		return this.maxStep;
	}
	public double getMinStep () {
		return this.minStep;
	}
	public double getDelta () {
		return this.delta;
	}
	
	 // setters
	public void setMaxStep ( double a ) {
		this.maxStep=a;
	}
	public void setMinStep ( double a ) {
		this.minStep=a;
	}
	public void setDelta ( double a ) {
		this.delta=a;
	}
	
	 // other methods
	public double lineSearch ( Polynomial P , double [] x ) {
		// call the gss method 
		double a=this.minStep, b=this.maxStep,c=a+((b-a)/this._PHI_);
		return this.GSS(a,b,c,x,direction(P,x),P);// step size from GSS
	}
	// gets user parameters from the user 
	public boolean getParamsUser () throws NumberFormatException, IOException {
		double a=0;double b=0;double c=0;double d=0;int e=0;double f=0;int g=0;double h=0;

		System.out.println("Set parameters for SD with a golden section line search:");
		a=Pro5_ebrah167.getDouble("Enter GSS maximum step size (0 to cancel): ",0.00,Double.MAX_VALUE);
		if(a==0) {
			 System.out.println("");
			 System.out.println("Process canceled. No changes made to algorithm parameters.");
			System.out.println("");
			return false;
		}else {
			b=Pro5_ebrah167.getDouble("Enter GSS minimum step size (0 to cancel): ",0.00,a);
			
		}
		if(b==0) {
			 System.out.println("");
			 System.out.println("Process canceled. No changes made to algorithm parameters.");
			 System.out.println("");
			 return false;
		}else {
			c=Pro5_ebrah167.getDouble("Enter GSS delta (0 to cancel): ",0.00,Double.MAX_VALUE);

		}					
		if(c==0) {
			 System.out.println("");
			 System.out.println("Process canceled. No changes made to algorithm parameters.");
			 System.out.println("");
			 return false;
		}
		else {
			d=Pro5_ebrah167.getDouble("Enter tolerance epsilon (0 to cancel): ",0.00,Double.MAX_VALUE); // get iterations
			
		}
		if(d==0) { // check if user inputs zero
			 System.out.println("");
			 System.out.println("Process canceled. No changes made to algorithm parameters.");
			 System.out.println("");
			 return false;
		}
		else {
			e=Pro5_ebrah167.getInteger("Enter maximum number of iterations (0 to cancel): ",0,10000);
	
		}		
		if(e==0) {
			System.out.println("");
			 System.out.println("Process canceled. No changes made to algorithm parameters.");
			System.out.println("");
			 return false;
		}
		else {
			f=Pro5_ebrah167.getDouble("Enter value for starting point (0 to cancel): ",-Double.MAX_VALUE,Double.MAX_VALUE);
	
		}			
		if(f==0) {
			System.out.println("");
			System.out.println("Process canceled. No changes made to algorithm parameters.");
			System.out.println("");
			return false;
		}
		else {
			  super.setEps(d);
			  super.setMaxIter(e);
			  super.setX0(f);
				
			  setMaxStep(a);
			  setMinStep(b);
			  setDelta(c);
			  System.out.println("");
			  System.out.println("Algorithm parameters set!");
			  System.out.println("");
			  return true;// get algorithm parameters from user	
		}
	}
	// prints user parameters 
	public void print () {
		// print parameters
		System.out.println("SD with a golden section line search:");
		System.out.println("Tolerance (epsilon): "+super.getEps());
		System.out.println("Maximum iterations: "+super.getMaxIter());
		System.out.println("Starting point (x0): "+super.getX0());
		System.out.println("GSS maximum step size: "+getMaxStep());
		System.out.println("GSS minimum step size: "+getMinStep());
		System.out.println("GSS delta: "+getDelta());
	}
	private double GSS( double a , double b , double c , double [] x , double [] dir ,Polynomial P ) {
		//c=a+((b-a)/this._PHI_);
		double y=0,y2=0;
		double [] result= new double [dir.length];
		double [] result2= new double [dir.length];
		double [] result3= new double [dir.length];
		
		double [] checkOut=new double [dir.length];
		double [] checkOut2=new double[dir.length];
		double [] checkOut3= new double[dir.length];
		
		double s=0;
		// use these arrays to create the equations for a,b,c
		for(int i=0;i<x.length;i++) {
			checkOut[i]=x[i]+a*dir[i];
			checkOut2[i]=x[i]+b*dir[i];
			checkOut3[i]=x[i]+c*dir[i];
		}
		
		////////////////////
		// special consideration case
		// check if c is greater than both b and a 
		if(P.f(checkOut3)>P.f(checkOut) || P.f(checkOut3)>P.f(checkOut2)) {
			if(P.f(checkOut)>=P.f(checkOut2)) { // if a is greater return b
				return b;
			}
			else{
				return a;
			}
		}
		// return midpoint if the interval [a,b] is smaller than delta 
		if(Math.abs(b-a)<this.delta) {
			return (a+b)/2;
		}
		else {
			if(Math.abs(c-a)>Math.abs(b-c)) { // left interval if this is the case 
				y=a+((c-a)/_PHI_); // calculate y 
					for(int i=0;i<x.length;i++) { // calculate a , c ,y 
						result[i]=x[i]+y*dir[i];
						result2[i]=x[i]+a*dir[i];
						result3[i]=x[i]+c*dir[i];
					}
					if (P.f(result)<P.f(result2) && P.f(result)<P.f(result3)) { // is y is the smallest 
						return this.GSS(a,c,y,x,dir,P); // cut to the left 
					}
					else {
						return this.GSS(y,b, c, x, dir, P); // cut to the right 
					}
						
			}
			else { // if the right interval is bigger 
				y=b-((b-c)/_PHI_); // calculate y 
					for(int i=0;i<x.length;i++) { // calculate y,b,c
						result[i]=x[i]+y*dir[i];
						result2[i]=x[i]+b*dir[i];
						result3[i]=x[i]+c*dir[i];
					}
					if (P.f(result)<P.f(result2) && P.f(result)<P.f(result3)) { // if y is the smallest 
						return this.GSS(c,b,y,x,dir,P); // cut to the left of y
					}

					else {
						return this.GSS(a,y,c, x, dir, P); // cut to the right of y 
					}

			}	
		}
		//return s;
	}

}

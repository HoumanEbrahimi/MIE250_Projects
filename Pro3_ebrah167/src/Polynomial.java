public class Polynomial {
	private int n ; // no. of variables
	private int degree ; // degree of polynomial
	private double [][] coefs ; // coefficients	
	
	// constructors
	public Polynomial () {
		this.n=0; this.degree=0;
	}
	public Polynomial ( int n , int degree , double [][] coefs ) {
		this.n=n;
		this.degree=degree;
		this.coefs=coefs;
	}
	 // getters
	public int getN () {
		return this.n;
	}
	
	public int getDegree () {
		return this.degree;
	}
	
	public double [][] getCoefs (){
		return this.coefs;
	}
	 // setters
	public void setN ( int a ) {
		this.n=a;
	}
	public void setDegree ( int a ) {
		this.degree=a;
	}
	
	public void setCoef ( int j , int d , double a ) {
		this.coefs[j][d]=a;
	}
	 // other methods
	public void init () {
		// init member arrays to correct sized t
		this.coefs=new double[this.n][this.degree+1];
		
	}
	public double f ( double [] x ) {
		// calculate function value at point x
		int index=0;
		double val=0;
		int deg=this.degree;
		for(int i=0;i<this.coefs.length;i++) {
			for(int j=0;j<this.coefs[i].length;j++) {
				val+=coefs[i][j]*Math.pow(x[index],deg);
				deg--;
			}
			deg=this.degree;
			index++;
		}
		return val;
	}
	public double [] gradient ( double [] x ) { // gradient function 
		int index=0,deg=this.degree;
		double derivative=0,derivative_at_x=0;
		// take the partial derivative of the vector 
		double [][] gradient= new double[this.n][this.degree];
		double [] gradient_at_x=new double [this.n];
		for(int i=0;i<this.n;i++) { // for loop that is used for partial derivatives 
			for(int j=0;j<this.degree;j++) {
				derivative=coefs[i][j]*deg*Math.pow(x[index],deg-1); // take the derivative 
				gradient[i][j]=derivative;
				deg--;
			}
			index++;

			deg=this.degree;
		}
		 // get the gradient values for each component of the vector 
	      for(int i=0;i<gradient.length;i++){
	          for (int j=0;j<gradient[i].length;j++){
	              derivative_at_x+=gradient[i][j];
	           }
	           gradient_at_x[i]=derivative_at_x;
	           derivative_at_x=0;
	        }
	      
	      // return gradient 
		return gradient_at_x;
	} // calculate gradient at point x
	public double gradientNorm ( double [] x ) {
  	    int index=0;
  	    double [] gradient_array=new double [this.n];
  	    double sum=0,result=0;
  	    
  	    gradient_array=gradient(x);
  	    // iterate through the gradient array and find its norm
  	    while(index<=gradient_array.length-1){
  	    	// sum of all the points squared 
  	        sum+=Math.pow(gradient_array[index],2);
  	        index++;
  	    }
  	    // result is the gradient norm
  	    result=Math.sqrt(sum);
		return result;
	} // calculate norm of gradient at point x
	public boolean isSet () {
	
		return this.n!=0 && this.degree!=0;
	} // indicate whether polynomial is set
	public void print () {
		// this function prints the polynomial 
		int n_copy=this.n,deg_copy=this.degree,index=1; 
		System.out.print("f(x) = (");
		
		for(int i=0;i<this.coefs.length;i++) {
			for(int j=0;j<this.coefs[i].length;j++) {
				if(j!=this.coefs[i].length-1) {
					System.out.print(" "+String.format("%.2f", coefs[i][j])+"x"+index+"^"+deg_copy+" +");
				}
				else {
					System.out.print(" "+String.format("%.2f", coefs[i][j]));
					
				}
				deg_copy--;
				
			}
			index++;
			deg_copy=this.degree;
			if(i!=this.coefs.length-1) {
				System.out.print(" ) + (");
			}
			else {
				System.out.print(" )");
				System.out.println("\n");
			}
		}
	} // print out the polynomial
	
	
}

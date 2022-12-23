import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.math.*;

public class Stats {
//create a stats class 
	static double val;
	private static  double  average;
	private static double  stdDev;

	
	public Stats () {
		average=0;
		stdDev=0;

	}
// takes the average of each parameters and returns a double 
	public static double average(double [] num) {
		double sum=0;
		
		for(int i=0;i<num.length;i++) {
			sum+=num[i];
		}
		average=sum/num.length;
		return average;
	}
	
	// finds the min of a double array
	public double min(double [] num) {
		double localMin=num[0];
		for(int i=0;i<num.length;i++) {
			if(localMin>num[i]) {
				localMin=num[i];
			}
		}
		return localMin;
	}
	// finds the min of a integer array
	public int min(int [] num) {
		int localMin=num[0];
		for(int i=0;i<num.length;i++) {
			if(localMin>num[i]) {
				localMin=num[i];
			}
		}
		return localMin;
	}
	// finds the min of a long array
	public long min(long [] num) {
		long localMin=num[0];
		for(int i=0;i<num.length;i++) {
			if(localMin>num[i]) {
				localMin=num[i];
			}
		}
		return localMin;
	}
	
	
	// finds the max of a double array
	public double max(double []num) {
		double localMax=num[0];
		for(int i=0;i<num.length;i++) {
			if(localMax<num[i]) {
				localMax=num[i];
			}
		}
		return localMax;
	}
	// finds the max of a integer array
	public int max(int []num) {
		int localMax=num[0];
		for(int i=0;i<num.length;i++) {
			if(localMax<num[i]) {
				localMax=num[i];
			}
		}
		return localMax;
	}
	// finds the max of a long array
	public long max(long []num) {
		long localMax=num[0];
		for(int i=0;i<num.length;i++) {
			if(localMax<num[i]) {
				localMax=num[i];
			}
		}
		return localMax;
	}
	// finds the standard deviation of an array and returns a double 
	public static double StandardDev(double [] num) {
		double local_std=0;
		for(int i=0;i<num.length;i++) {
			local_std+=Math.pow((num[i]-average(num)), 2);
		}
		local_std=local_std/(num.length-1);
		local_std=Math.sqrt(local_std);
		return local_std;
	}
	
}

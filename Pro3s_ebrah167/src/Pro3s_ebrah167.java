import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Pro3s_ebrah167 {
	int choice;
	boolean play=true;
	inpt= new BufferedReader (new InputStreamReader(System.in));

	 while (play) {
		 try {
			 System.out.println("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)");
			 System.out.println("E - Enter polynomial function");
			 System.out.println("F - View polynomial function");
			 System.out.println("S - Set steepest descent parameters");
			 System.out.println("P - View steepest descent parameters");
			 System.out.println("R - Run steepest descent algorithm");
			 System.out.println("D - Display algorithm performance");
			 System.out.println("Q - Quit\n");
			 System.out.print("Enter choice: ");
			 choice = Integer.parseInt(inpt.readLine());
			 System.out.println("");

			 if (choice == 0) {
				 System.out.println("Au revoir!");
				 play = false;
			 } else if (choice == 1) {
			 } else if (choice == 2) {
			 } else if (choice == 3) {
			 } else if (choice == 4) {
			 } else if (choice == 5) {
			 } else {
				 System.out.println("ERROR: Invalid menu choice!");
				 System.out.println("");

			 }
		 }
		 catch (NumberFormatException | IOException e) {
			 System.out.println("");
			 System.out.println("ERROR: Invalid menu choice!");
			 System.out.println("");

		 }
		}
}

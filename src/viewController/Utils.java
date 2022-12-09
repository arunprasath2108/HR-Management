package viewController;

import java.util.Scanner;

import dbController.DB_Utils;

public class Utils
{
	
	public static Scanner scanner = new Scanner(System.in);
	

     public static String getStringInput()
    {
		String input = scanner.nextLine();
		printSpace();
		return input;
    }
	
	 public static int getIntInput()
	 {
		int input = scanner.nextInt();
		scanner.nextLine();
		printSpace();
		return input;
	 }
	 
	 public static void printSpace()
	 {
		 System.out.print("\n");
	 }
	 
	 public static void printLine()
	 {
		 System.out.println(" ----------------------------------------");
	 }
	 
	 public static void printEnterOption()
	 {
		 printSpace();
		 System.out.print(" Enter an option : ");
		 printSpace();
	 }
	 
	 public static void printInvalidInputMessage()
	 {
		 	printSpace();
			System.out.println(" Please Enter a Valid Input.");
			printSpace();
	 }
	 
	 public static void printLoginFailMessage()
	 {
		 printSpace();
		 System.out.println("  Incorrect User ID.");
		 printSpace();
		 printLine();
	 }
	 
	 public static void printWelcomeMessage(int userID)
	 {
		 
			System.out.println("       Welcome "+DB_Utils.getEmployeeName(userID)+" !!");
			Utils.printLine();
	 }
	 
	 public static void printTryAgainMessage()
	 {
		 printSpace();
		 System.out.println("  Please, Try Again with Unique Name...");
		 printSpace();
	 }

	 

}

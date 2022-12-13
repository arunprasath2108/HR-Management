package utils;


import java.util.*;
import dbController.*;


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
		 
			System.out.println("       Welcome "+EmployeeDBController.getEmployeeName(userID)+" !!");
			Utils.printLine();
	 }
	 
	 public static void printTryAgainMessage()
	 {
		 printSpace();
		 System.out.println("  Please, Try Again with Unique Name...");
		 printSpace();
	 }
	 
//    public static LocalDate getTodayDate()
//	{
//    	
//    	
//    	LocalDate todayDate=LocalDate.now();  //y-m-d
//    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM"); 
//    	String str=todayDate.format(formatter);
//    	LocalDate dateTime = LocalDate.parse(str,formatter);
//    	System.out.println(str+" "+dateTime+" "+todayDate);
//		return todayDate;
    	
    	
//			Date date = new Date();
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//			String todayDate = simpleDateFormat.format(date);
//			
			//return todayDate;
//	}
    
    
//    public static LocalDate getDateFormat(String str) 
//    {
//    	
//	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
//	    LocalDate dateTime = LocalDate.parse(str, formatter);
//		return dateTime;
//		
//    }

    
    
	 
	 
	 
//	 public static void printHeader()
//	 {
//		 	Utils.printSpace();
//			System.out.println(" FEATURES :");
//	 }
//
//
//	 public static void printLogOutMessage()
//	 {
//	     printSpace();
//		 System.out.println("	~ Logged Out ~	");	
//		 printSpace();
//	 }
//	
//	
//	 public static void printTeamAddedSuccessful()
//	 {
//	 	 printSpace();
//	  	 System.out.println(" Successfully added a Team. ");
//		 printSpace();
//	 }
//	 
//	
//	public static int printRequestCount(Employee employee) 
//	{
//		
//		return employee.getRequests().size();
//		
//	}
//	
//	
//	public static String getTeamName(int id)
//	{
//		
//		for( Entry<Integer, String> entries : Resource.teamMap.entrySet())
//		{
//			if(entries.getKey() == (Integer)id)
//			{
//				
//				return (String) entries.getValue();
//			}
//		}
//		
//		return null;
//		
//	}
//	
//	
//	public static Employee getEmployeeObject( int id)
//	{
//		
//		for(Employee employee : Resource.employees)
//		{
//			if(employee.getemployeeID() == id)
//			{
//				return employee;
//			}
//		}
//		return null;
//		
//	}
//	
//	
//	public static String getEmployeeName(int id)
//	{
//		
//		for(Employee employee : Resource.employees)
//		{
//			if(employee.getemployeeID() == id)
//			{
//				return employee.getemployeeName();
//			}
//		}
//		
//		return null;
//		
//	}
//
//	
//	public static String getCurrentDateTime()
//	{
//		Date date = new Date();
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy   HH:mm");
//		String todayDate = simpleDateFormat.format(date);
//		return  todayDate;
//	}
//


	 

}
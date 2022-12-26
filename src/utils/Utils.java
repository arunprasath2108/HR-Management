package utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import dbController.*;
import model.Employee;


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
	 
	 public static void printMessage(String message)
	 {
	     printSpace();
		 System.out.println(message);	
		 printSpace();
	 }
	 
	 public static void printSpace()
	 {
		 System.out.print("\n");
	 }
	 
	 public static void printLine()
	 {
		 System.out.println(" ------------------------------------------");
	 }
	 
	 public static void printWelcomeMessage(int userID)
	 {
		 
			System.out.println("       Welcome "+EmployeeDBController.getEmployeeName(userID)+" !!");
			Utils.printLine();
	 }
	
	 public static Date getTodayDateObject()
	 {
		 
	     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		 Date todayDate;
		try 
		{
			todayDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
			return todayDate;
		} 
		catch (ParseException e)
		{
			System.out.println("  Error in Today date getting method");
		}
		return null;
	 }
	 
	 //for joining date display
	 public static String getTodayDate()
	 {
		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
		   LocalDate now = LocalDate.now();   
		   return dtf.format(now);
	 }
	 
	 public static Date getCurrentDateTime()
	 {
		 return new Date();
	 }
	 
//	 public static String getCurrentDateAndTime()
//	 {
//		 LocalDateTime now = LocalDateTime.now();  
//	     DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
//	     String formatDateTime = now.format(format);  
//	     return formatDateTime;
//	     
//	 }

	 public static void printErrorMessageInAdd()
	 {
	     printSpace();
		 System.out.println("   * Please, Enter a valid Name to Add \n");	
		 System.out.println("   * Name should be minimum of 2 characters & start with alphabet ");
		 printSpace();
		 
	 }
	 public static void NoHigherRoleAvailable()
	 {
		 System.out.println("  You have prefered Higher Role...\n");
		 System.out.println("  So, this Role has automatically  set default Reporting to -> CEO\n");
	 }
	 
	public static Employee getEmployee(int userID)
	{

		//get basic Information in employee table
		Employee employeeBasicData = EmployeeDBController.getEmployee(userID);
		
		//get personal Information in personal Information Table
		Employee employeePersonalInfo = PersonalDBController.getEmployee(userID, employeeBasicData);

		Employee employeeWorkExperience = WorkExperienceDBController.getEmployee(userID, employeePersonalInfo);

		return employeeWorkExperience;
	}
	 
	
	 
	 

    
    
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
//	 public static void printHeader()
//	 {
//		 	Utils.printSpace();
//			System.out.println(" FEATURES :");
//	 }
//
//

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

package utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
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
		 System.out.println(" ----------------------------------------------------");
	 }
	 
	 public static void printWelcomeMessage(int userID)
	 {
		 
			System.out.println("       Welcome "+EmployeeDBController.getEmployeeName(userID)+" !!");
			Utils.printLine();
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
	 
	 
	public static long getDifferenceBetweenTwoDates(Date date1, Date date2)
	{
		
		long diff = date1.getTime() - date2.getTime();

        long diffDays = diff / (24 * 60 * 60 * 1000);
        
		return diffDays;
		
	}
	
	public static Date convertStringIntoDate(String dateString)
	{
		
		try 
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			return dateFormat.parse(dateString);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
			Utils.printMessage("  Can't convert String into Date format");
		}
		return null;
	}
	
	public static Date convertStringToDate(String dateString)
	{
		
		try 
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return dateFormat.parse(dateString);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
			Utils.printMessage("  Can't convert String to Date format");
		}
		return null;
	}
	 
	public static String convertDateIntoAnotherDateFormat(Date date)
	{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    return dateFormat.format(date);  
	}
	 
	 //in adding new Role and Team
	 public static void printErrorMessageInAdd()
	 {
	     printSpace();
		 System.out.println("   * Please, Enter a valid Name to Add \n");	
		 System.out.println("   * Name should be minimum of 2 characters & start with alphabet ");
		 printSpace();
		 
	 }
	 public static void printNoHigherRoleAvailable()
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

	 

}

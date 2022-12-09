package loginPage;

import java.util.*;
import dbController.*;
import viewController.*;

public class Main 
{
	
	private static final int LOGIN = 1;
	private static final int EXIT = 2;
	
	
	public static void main(String[] args) 
	{
		
		createTables();
		loginPage();
		
	} //main method
	
	
	//method for creating all tables
	public static void createTables()
	{
		//db.createDatabase();
		//db.createRoleTable();
		//db.createTeamsTable();
		//db.createWorkLocationTable();
		//db.createEmployeeTable();
		//db.insertLoctions();
		//db.createPersonalInfo();
		//db.createWorkExperience();
	}
	
	public static void loginPage()
	{
		
		while(true)
		{
			
			 Utils.printEnterOption();
			 System.out.println(" 1. Login");
			 System.out.println(" 2. Exit ");
			 Utils.printSpace();
			 
			 try
			 {
				 
				 int userInput = Utils.getIntInput();
			 
				 switch(userInput)
				 {
				 
					 	case LOGIN :
					 		employeeLogin();
				 			break;
				 			
				 		case EXIT :
				 			System.out.println("  ~ Thanks for your Patience ~ ");
				 			return;
				 			
				 		default :
				 			Utils.printInvalidInputMessage();
				}
			 }
			 
			 catch(InputMismatchException e)
			 {
		 			Utils.printInvalidInputMessage();
		 			Utils.scanner.nextLine();
		 			//loginPage();
			 }
			 
		 } 
	}
	
	private static void employeeLogin() 
	{
	
		Utils.printSpace();
		
		try
		{
			System.out.println(" Enter User ID :");
			int userID = Utils.getIntInput();
	
			if (EmployeeValidation.isEmployeePresent(userID))
			{
				loginAsUser(userID);
			}
			
			else 
			{
				Utils.printLoginFailMessage();
				return;
			}
		}
		
		catch(InputMismatchException e)
		{
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			employeeLogin();
		}
	
	}

	private static void loginAsUser(int userID) 
	{
	
		Utils.printWelcomeMessage(userID);

		int roleID = DB_Utils.getRoleID(userID);
		String employeeRole = DB_Utils.getRoleName(roleID);
		
		if(employeeRole.equalsIgnoreCase("HR"))
		{
			HR_View hr = new HR_View();
			hr.listEmployeeMenu();
		}
		
		else if(employeeRole.equalsIgnoreCase("PT"))
		{
//			Employee_View employee = new Employee_View();
//			System.out.println(" pt login");
		}
		
		else
		{
//			SeniorEmployee_View seniorEmployee = new SeniorEmployee_View();
//			System.out.println(" senior employee login");
		}
	}

}

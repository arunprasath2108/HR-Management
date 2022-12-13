package loginPage;

import java.util.*;
import dbController.*;
import utils.EmployeeValidation;
import utils.Utils;
import viewController.*;

public class Main 
{
	
	private static final int LOGIN = 1;
	private static final int EXIT = 2;
	
	
	public static void main(String[] args) 
	{
		
		//create tables in Database
		DBUtils.createTables();
		
		//main page
		loginPage();
		
		//closes the connection object when not in use
		DBConnector.closeConnection();
		
	} //main method
	

	
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

		int roleID = RoleDBController.getRoleID(userID);
		String employeeRole = RoleDBController.getRoleName(roleID);
		
		if(employeeRole.equalsIgnoreCase("HR"))
		{
			HRViewController hr = new HRViewController();
			hr.listEmployeeMenu();
		}
		
		else if(employeeRole.equalsIgnoreCase("PT"))
		{
//			Employee_View employee = new Employee_View();
			System.out.println(" pt login");
		}
		
		else
		{
//			SeniorEmployee_View seniorEmployee = new SeniorEmployee_View();
			System.out.println(" senior employee login");
		}
	}

}

package controller;

import java.util.*;
import dbController.*;
import utils.*;
import viewController.*;

public class LoginController 
{
	
	private static final int LOGIN = 1;
	private static final int EXIT = 2;
	
	//stops the method while wrong inputs exceeds 3 times
	static int inputLimit = 0;
	
	
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
			 }
			 
		 } 
	}
	
	private static void employeeLogin() 
	{	
		try
		{
			Utils.printDefaultHRId();
			Utils.printEnterUserID();
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
		}
	
	}

	private static void loginAsUser(int userID) 
	{
	
		Utils.printWelcomeMessage(userID);
		
		int roleID = RoleDBController.getRoleID(userID);
		int leastRoleID = RoleDBController.getLeastRoleID();

		if(userID == 2)       //default HR Login ID
		{
			HRController hr = new HRController();
			hr.listEmployeeMenu();
		}
		
		else if(leastRoleID == roleID && RoleDBController.getRoleName(roleID).equalsIgnoreCase("PT"))
		{
//			EmployeeController employee = new EmployeeController();
//			employee.listPTMenu(userID);
		}
		
		else
		{
//			SeniorEmployeeController seniorEmployee = new SeniorEmployeeController();
//			seniorEmployee.listEmployeeMenu(userID);
		}
		
		Utils.printLogOutMessage();
	}

}

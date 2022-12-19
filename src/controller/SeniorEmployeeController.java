package controller;

import java.sql.ResultSet;
import java.util.*;

import dbController.EmployeeDBController;
import dbController.TeamDBController;
import model.Employee;
import utils.*;
import viewController.*;

public class SeniorEmployeeController extends EmployeeController
{

	
	private static final int VIEW_REPORTEE = 5;
	private static final int APPROVE_LEAVE = 6;
	private static final int REQUEST_TEAM_CHANGE = 7;
	private static final int APPROVE_REJECT_TEAM_CHANGE = 8;
	private static final int NOTIFICATION = 9;
	private static final int LOG_OUT = 10;
	
	
	SeniorEmployeeViewController seniorEmployeeViewController = new SeniorEmployeeViewController();
	
//	HRController hrController = new HRController();
//	EmployeeController employeeController = new EmployeeController();


	
	public void viewTeam()
	{
		
		int teamID = getTeamID();
		
		seniorEmployeeViewController.viewTeam(teamID);
		
		
	}
	
	public int getTeamID()
	{
		
		employeeView.listTeam(TeamDBController.listTeam());
		int userInput = seniorEmployeeViewController.getTeamID();
		
		if(EmployeeValidation.isTeamIdPresent(userInput))
		{
			return userInput;
		}
		else
		{
			inputLimit++;
			if(inputLimit == 3)
			{
				inputLimit = 0;
				return 0;
			}
			Utils.printInvalidInputMessage();
			return getTeamID();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//list  senior employee menu
//	public void listEmployeeMenu(int employeeID)
//	{
//		
//		EmployeeValidation.checkProfileCompleted(employeeID);
//
//		employeeView.listEmployeeMenu(employeeID);
//		
//		seniorEmployee.listSeniorEmployeeMenu(employeeID);
//		
//		chooseOptionFromList(employeeID);
//	}
//			
//	public void requestMessages()
//	{
//		
//	}
//	
//	
//	private void chooseOptionFromList(int employeeID)
//	{
//		
//		Employee employee = EmployeeDBController.getEmployee(employeeID);
//		
//		try
//		{
//			Utils.printEnterOption();
//			int userInput = Utils.getIntInput();
//			
//			switch(userInput)
//			{
//				
//				case VIEW_REPORTEE : 
////					viewReportees(employee);
////					listEmployeeMenu(employee);
//					break;
//				
//				case APPROVE_LEAVE :  
////					System.out.println("apply leave");
////					listEmployeeMenu(employee);
//					break;
//					
//				case REQUEST_TEAM_CHANGE :	
////					teamChangeRequest(employee);
////					listEmployeeMenu(employee);
//					break;
//					
//				case APPROVE_REJECT_TEAM_CHANGE :
////					requestMessages(employee);
////					listEmployeeMenu(employee);
//					break;
//				
//				case NOTIFICATION :
////					printNotification(employee);
////					listEmployeeMenu(employee);
//					break;
//					
//				case LOG_OUT :
//					return;
//					
//				default :
//				 	employeeController.getInputFromEmployee(userInput, employee);
////				 	listEmployeeMenu(employee);
//				 	break;
//			}
//			
//		}
//		catch(InputMismatchException e)
//		{
//				Utils.printInvalidInputMessage();
//				Utils.scanner.nextLine();
//		}
//		
//		listEmployeeMenu(employeeID);
//		
//	}
	
//	public 
	
	
	
}

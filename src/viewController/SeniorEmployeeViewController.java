package viewController;

import java.sql.*;
import java.util.*;

import controller.SeniorEmployeeController;
import dbController.*;
import utils.*;

public class SeniorEmployeeViewController
{
	
//	SeniorEmployeeController seniorEmployeeController = new SeniorEmployeeController();
	
	//stop the method for wrong input more than 3 times
	int inputLimit = 0;
	
	
	public int getTeamID()
	{
		System.out.println(" Select Team ID to proceed : \n");
		
		try
		{
			int userInput = Utils.getIntInput();
			return userInput;
		}
		catch(InputMismatchException e)
		{
			System.out.println("   Enter Team ID only");
			Utils.scanner.nextLine();
			return 0;
		}
	}
	
	
	public void viewTeam(int teamID)
	{
		
		if(teamID == 0)
		{
			System.out.println("  Invalid Team ID\n");
		}
		else
		{
			ResultSet result = EmployeeDBController.getTeamDetails(teamID);
			printTeamDetails(result);
		}
	}
	
	public void printTeamDetails(ResultSet result)
	{
		
		try 
		{
				
			if(!result.next())
			{
				System.out.println("   No Employees in the Team\n");
				return;
			}
			result.previous();
			
			Utils.printLine();
			System.out.println("    ID        NAME           ROLE");
			Utils.printLine();
			
			while(result.next())
			{
				System.out.printf("   %3s        %-12s    %-5s  \n",
									result.getInt(DBConstant.ID),
									result.getString(DBConstant.NAME),
									result.getString(DBConstant.ROLE_NAME));
			}
				
		}
		catch(SQLException e)
		{
			System.out.println("   Error occured in printing Team Details\n");
			return;
		}
		
		Utils.printLine();
		Utils.printSpace();
		return;
	}

	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//	public void listSeniorEmployeeMenu(int userID)
//	{
//		
//		System.out.println(" 5. View Reportees");
//		System.out.println(" 6. Approve Leave Request ");
//		System.out.println(" 7. Request Team Change");
//		System.out.println(" 8. Manage Team Change Request ");
//
//		System.out.println(" 9. Notification ");
//		
//		System.out.println(" 10. Logout");
//		Utils.printLine();
//		
//		
//	}
	
	
//	private void viewReportees(int employeeID)
//	{
//		
//		System.out.println(" Reportee List : ");
//		Utils.printSpace();
//		System.out.println(" Employee ID	  Name	     Role");
//		Utils.printLine();
		
//		for( Employee employeee : Resource.employees)
//		{
//			
//			if(employeee.getEmployeeTeamName().equals(employee.getEmployeeTeamName()) && employeee.getemployeeRole().getValue() > employee.getemployeeRole().getValue() && employeee.getReportingTo().equalsIgnoreCase(employee.getemployeeName()))
//			{
//				isReporteePresent = true;
//				System.out.printf("       %-10s %-10s %-10s\n",employeee.getemployeeID(),employeee.getemployeeName(),employeee.getemployeeRole());
//				
//			}
//			
//		}
//		
//		if( isReporteePresent == false)
//		{
//			Utils.printSpace();
//			System.out.println(" 	  No Reportees !!!");
//			Utils.printSpace();
//		}
		
//	}
	
}

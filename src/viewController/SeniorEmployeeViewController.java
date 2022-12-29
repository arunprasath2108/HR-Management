 package viewController;

import java.sql.*;
import java.util.*;
import java.util.Date;

import dbController.*;
import model.*;
import utils.*;

public class SeniorEmployeeViewController
{
	
	//stop the method for wrong input more than 3 times
	int inputLimit = 0;
	
	
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

	
	public void listSeniorEmployeeMenu(int userID, int notificationCount, int requestCount)
	{
		
		System.out.println(" 5. View Reportees");
		System.out.println(" 6. Approve Leave Request ");
		System.out.println(" 7. Request Team Change");
		System.out.print(" 8. Manage Team Change Request ");
		
		if(requestCount > 0)
		{
			System.out.println(" ~ [" + requestCount + "] unread ");
		}
		else
		{
			Utils.printSpace();
		}
		
		System.out.print(" 9. Notification");
		
		if(notificationCount > 0)
		{
			System.out.println(" ~ [" + notificationCount + "] unread ");
		}
		else
		{
			Utils.printSpace();
		}
		
		System.out.println(" 10. Logout");
		Utils.printLine();
		
	}
	
	public void viewReportees(ArrayList<Employee> employees)
	{
		
		System.out.println(" Reportee List : ");
		Utils.printSpace();
		System.out.println("    ID	     Name	     Role");
		Utils.printLine();

		for( Employee employee : employees)
		{
			
			String role = RoleDBController.getRoleName(employee.getemployeeRoleID());
			
			System.out.printf("   %3s       %-12s     %-5s  \n",employee.getemployeeID(),
																		employee.getemployeeName(),role);
		}
		Utils.printLine();
		Utils.printSpace();
		
	}
	
	public int employeeChoice()
	{
		System.out.println(" 1. Choose Team.");
    	System.out.println(" 2. Back");
    	
    	return getInputFromEmployee();
	}
	
	public int getInputFromEmployee()
	{
		
		try
		{
			return Utils.getIntInput();
		}
		catch(InputMismatchException e)
		{
			Utils.scanner.nextLine();
    		return 0;
		}
	}
	
	
	public int getTeamID()
	{
		
		System.out.println(" Select Team ID to proceed : \n");
		return getInputFromEmployee();
	}

	public void displayRequests(ArrayList<Request> requests)
	{
		
		Utils.printLine();
		System.out.println("  Requests ");
		Utils.printLine();
		
		for(Request request : requests)
		{
			if(request.getStatus().contains("pending"))
			{
				printRequest(request);
			}
		}
		
		Utils.printSpace();
	}
	
	public void printRequest(Request request)
	{
		
		String requestBy = EmployeeDBController.getEmployeeName(request.getRequestBy());
		String teamName = TeamDBController.getTeamName(request.getTeamID());
		
		
		//split date & time into two parts			
		String[] dateFormat = request.getRequestOn().split(" ");
		
		//convert string into date format
		Date requestDateintoDateObject = Utils.convertStringToDate(dateFormat[0]);

		//convert date to readable format
		String requestDate = Utils.convertDateIntoAnotherDateFormat(requestDateintoDateObject);
		
		
	
		Utils.printSpace();
		System.out.println("  # " + request.getRequestID() + "                         " + requestDate + "  " + dateFormat[1] );
		Utils.printSpace();
		System.out.println("  FROM : " + request.getRequestBy() + " - " + requestBy);
		Utils.printSpace();
		System.out.println("  Requested for Team Change  ->   " + request.getTeamID() + " / " +teamName);
		Utils.printLine();
		
	}
	
	
	public void displayNotification(ArrayList<Notification> notifications)
	{
		
		Utils.printSpace();
		System.out.println(" NOTIFICATIONS :");
		Utils.printLine();
		Utils.printSpace();
		
		for(Notification notification : notifications)
		{
			//split date & time into two parts			
			String[] dateFormat = notification.getNotificationTime().split(" ");
			
			//convert string into date format
			Date date = Utils.convertStringToDate(dateFormat[0]);

			//convert date to readable format
			String notificationDate = Utils.convertDateIntoAnotherDateFormat(date);
			
			System.out.println(" " + notificationDate + "                                " + dateFormat[1]);
			Utils.printSpace();
			System.out.println("  " + notification.getNotification());
			Utils.printLine();
			Utils.printSpace();
		}
		Utils.printSpace();
	}
	
	public void printLeaveRequest(ArrayList<LeaveManagement> leaveReport)
	{
		
		Utils.printLine();
		Utils.printSpace();
		
		for(LeaveManagement leave : leaveReport)
		{
			
			String name = EmployeeDBController.getEmployeeName(leave.getRequestBy());
			
			String fromDate = Utils.convertDateIntoAnotherDateFormat(leave.getfromDate());
			String toDate = Utils.convertDateIntoAnotherDateFormat(leave.getToDate());

			
			System.out.printf(" # %-3s             %-20s \n", leave.getleaveID(), "From : " + fromDate + " To : " + toDate);
			Utils.printSpace();
			System.out.println("   Employee Name : " + name);
			Utils.printSpace();
			System.out.println("   Reason : " + leave.getReasonForLeave());
			Utils.printSpace();
			Utils.printLine();
		}
		
		Utils.printSpace();
	}
	
	public int getRequestInput()
	{
		System.out.println(" 1. Process Requests");
		System.out.println(" 2. Back");
		return getInputFromEmployee();
	}
	
	
	public int getRequestID()
	{
		System.out.println("  Enter Request ID for Team Change : ");
		return getInputFromEmployee();
	}
	
	public int getRequestDecisionInput()
	{
		System.out.println(" 1. Accept");
		System.out.println(" 2. Reject");
		System.out.println(" 3. Back to Menu ");
		
		return getInputFromEmployee();

	}
	
	public int getLeaveID()
	{
		System.out.println("  Enter leave ID for process Leave request : ");
		return getInputFromEmployee();
	}
	
	public String getReasonForReject()
	{
		System.out.println("  Enter a Valid Reason for Rejecting Leave Request : ");
		return Utils.getStringInput();
	}
	
	
}

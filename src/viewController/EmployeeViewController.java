package viewController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;

import dbController.*;
import model.*;
import utils.*;

public class EmployeeViewController
{
	
	
	public void listEmployeeMenu(int userID)
	{
		
		System.out.println(" Features :");
		Utils.printLine();
		System.out.println(" 1. My Profile");
		System.out.println(" 2. Edit Personal Info");
		System.out.println(" 3. Apply Leave");
		System.out.println(" 4. View Leave Request Report");
		System.out.println(" 5. Notification");
		Utils.printSpace();
		
	}
	
	public void displayProfile(Employee employee) 
	{
		
		Utils.printSpace();
		System.out.println(" EMPLOYEE DETAILS");
		Utils.printLine();
		System.out.println("  Team Name	  : " + TeamDBController.getTeamName(employee.getEmployeeTeamID()));
		System.out.println("  Employee ID     : " + EmployeeDBController.getEmployeeID(employee.getemployeeName()));
		System.out.println("  Name		  : " + employee.getemployeeName());
		System.out.println("  Role		  : " + RoleDBController.getRoleName(employee.getemployeeRoleID()));
		System.out.println("  Reporting to	  : " + employee.getReportingToID()+" - "+EmployeeDBController.getEmployeeName(employee.getReportingToID()));
		System.out.println("  Official Mail   : " + employee.getCompanyMailId());
		System.out.println("  Date of Joining : " + employee.getDateOfJoining());
		System.out.println("  Work Location	  : " + WorkLocationDBController.getLocationName(employee.getWorkLocationID()));
		Utils.printLine();
		Utils.printSpace();
		
	}
	
	public void displayPersonalInfo(Employee employee)
	{
		
		System.out.println(" PERSONAL INFORMATION :");
		Utils.printLine();
		Utils.printSpace();
		System.out.println(" Mobile		  : "+employee.getMobileNum());
		Utils.printSpace();
		System.out.println(" Email ID	  : "+employee.getEmailID());
		Utils.printSpace();
		System.out.println(" Address	  : "+employee.getAddress());
		Utils.printSpace();
		System.out.println(" Qualification	  : "+employee.getHighestDegree());
		Utils.printSpace();
		System.out.println(" Passed Out	  : "+employee.getPassedOutYear());
		Utils.printSpace();

		if(!employee.getWorkExperience().isEmpty())
		{
			listWorkExperience(employee.getWorkExperience());
		}
		Utils.printLine();
		Utils.printSpace();
		
	}
	
	public void listWorkExperience(ArrayList<WorkExperience> experiences)
	{
		int count = 1;
		Utils.printSpace();
		System.out.println(" WORK EXPERIENCE  :");
		
		for(WorkExperience work : experiences)
		{
			Utils.printSpace();
			System.out.println(" " +(count++) + " - Company Name : " + work.getCompanyName());
			System.out.println("     Company Role : " + work.getRoleInCompany());
			System.out.println("     Experience   : " + work.getExperience());
			Utils.printSpace();
		}
	}
	
	public void editPersonalInfo(Employee employee)
	{
		
		Utils.printSpace();
		displayPersonalInfo(employee);
		
		System.out.println(" 1.Add Mobile.");
		System.out.println(" 2.Change Email ID.");
		System.out.println(" 3.Edit Address.");
		System.out.println(" 4.Add Work Experience.");
		System.out.println(" 5.Educational Qualification.");
		Utils.printSpace();
		System.out.println(" 6. Back to Menu");
		Utils.printSpace();
		
	}
	
	public void listTeam(ArrayList<Team> teams)
	{
		Utils.printLine();
		System.out.println("  TEAM ID      TEAM NAME ");
		Utils.printLine();
		
		for(Team team : teams)
		{
			System.out.printf("   %-2s     -     %-10s \n",team.getTeamID(),team.getTeamName());
		}
		
		Utils.printLine();
		Utils.printSpace();
	}
	
	public String getMobileNum()
	{
		
		Utils.printSpace();	
		System.out.println(" Enter 10 Digit Number.");
		return Utils.getStringInput();
		
	}
	
	public String getPersonalMail()
	{
		
		Utils.printSpace();
		System.out.println(" Enter E-Mail ID");
		return Utils.getStringInput();
	}
	
	public String getAddress()
	{
		
		System.out.println(" Enter your Address in the below format");
		Utils.printSpace();
		System.out.println(" Home Address, Street, City");
		Utils.printSpace();
		System.out.println(" sample address ->  1/12, North Street, Coimbatore");
		Utils.printSpace();
		
		return Utils.getStringInput();
	}
	
	public int getQualification()
	{
		System.out.println(" Enter your Higher Qualification");
		Utils.printSpace();
		System.out.println(" 1. B.E / B.Tech ");
		System.out.println(" 2. M.E / M.Tech ");
		System.out.println(" 3. Arts Stream (Bsc / Msc / BA)");
		Utils.printSpace();
		System.out.println(" Choose a option.");

		return getInputFromEmployee();
	}
	
	public String getPassedOutYear()
	{
		
		System.out.println(" Passed Out year     Format  -> [ yyyy ]");
		return Utils.getStringInput();
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
	
	public String getCompanyName()
	{
		
		System.out.println(" Enter Company Name : ");
		return Utils.getStringInput();
	}
	
	public String getCompanyRole()
	{
		
		System.out.println(" Enter Role in the Compane : ");
		return Utils.getStringInput();
	}
	
	public int getYearsOfExperience()
	{
		
		System.out.println("  * Number of year you have worked for that company: ");
		System.out.println("  * Enter years in number : \n");
		System.out.println("  NOTE : If Experience is less than 1 year  ->  enter as [0] ");

		
		return Utils.getIntInput();
	}
	
	public int getMonthsOfExperience()
	{
		
		System.out.println("  * Number of Months : ");
		System.out.println("  * NOTE : Enter above [3] months only, if below 3  ->  enter as [0] :");
		
		return Utils.getIntInput();
	}
	
	public int confirmBeforeAddExperience()
	{
		
		System.out.println(" 1. Confirm before add Experience ");
		System.out.println(" 2. Back");
		Utils.printSpace();
		System.out.println(" NOTE : YOU CAN'T EDIT THIS WORK EXPERIENCE ONCE ADDED !!!");
		
		return getInputFromEmployee();
	}
	
	public int getInputForApplyLeave()
	{
		
		System.out.println(" 1. Apply leave");
		System.out.println(" 2. Back");
		
		return getInputFromEmployee();
	}
	
	public void displayAvailableLeave(ResultSet result)
	{
		
		try 
		{
			
			System.out.println("  # ID");
			Utils.printLine();
			
			while(result.next())
			{
				Utils.printSpace();
				System.out.println("  # " + result.getInt(DBConstant.LEAVE_TYPE_ID) + " - " + result.getString(DBConstant.LEAVE_NAME));
				System.out.println("                       Available " + result.getInt(DBConstant.UNUSED_LEAVE) + " Day(s) ");
				Utils.printSpace();
			}
			Utils.printLine();
		} 
		catch (SQLException e) 
		{
			System.out.println(" Can't able to display Available Leave ");
		}
	}
	
	public int getLeaveID()
	{
		
		System.out.println(" Choose Leave ID");
		return getInputFromEmployee();
	}
	
	public String getDateForApplyLeave()
	{
		System.out.println(" Enter Date in this format ->  dd/mm/yyyy \n");
		return Utils.getStringInput();
	}
	
	public String getReasonForLeave()
	{
		System.out.println("  Reason for your leave apply : \n");
		return Utils.getStringInput();
	}
	
	public void printLeaveReport(ArrayList<LeaveManagement> leaveReport)
	{
		
		
		Utils.printLine();
		Utils.printSpace();
		
		for(LeaveManagement leave : leaveReport)
		{
			
			String fromDate = Utils.convertDateIntoAnotherDateFormat(leave.getfromDate());
			String toDate = Utils.convertDateIntoAnotherDateFormat(leave.getToDate());
			
			System.out.println(" # ID "+leave.getleaveID() + "           From : " + fromDate + " to " + toDate );
			Utils.printSpace();
			System.out.printf("  Leave : %-20s    Status : %-10s", LeaveTypeDBController.getLeaveName(leave.getLeaveTypeID()),leave.getStatus());
			Utils.printSpace();
			
			if(leave.getRejectedReason() != null)
			{
				System.out.println("  Reason : " + leave.getRejectedReason());
				Utils.printSpace();
			}
			
			Utils.printLine();
			Utils.printSpace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	


	

}

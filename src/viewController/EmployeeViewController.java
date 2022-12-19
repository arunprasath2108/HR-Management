package viewController;

import java.util.ArrayList;
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
		Utils.printSpace();
		
	}
	
	public void displayProfile(Employee employee) 
	{
		
		Utils.printSpace();
		System.out.println(" EMPLOYEE DETAILS");
		Utils.printLine();
		System.out.println("  Team Name	  : " + TeamDBController.getTeamName(employee.getEmployeeTeamID()));
		System.out.println("  Employee ID     : " + EmployeeDBController.getEmployeeID(employee.getemployeeName()));
		System.out.println("  Name		  : " + EmployeeDBController.getEmployeeName(EmployeeDBController.getEmployeeID(employee.getemployeeName())));
		System.out.println("  Role		  : " + RoleDBController.getRoleName(employee.getemployeeRoleID()));
		System.out.println("  Reporting to	  : " + employee.getReportingToID()+" - "+EmployeeDBController.getEmployeeName(employee.getReportingToID()));
		System.out.println("  Official Mail   : " + employee.getCompanyMailId());
		System.out.println("  Date of Joining : " + employee.getDateOfJoining());
		System.out.println("  Work Location	  : " + WorkLocationDBController.getLocationName(employee.getWorkLocationID()));
		Utils.printLine();
		Utils.printSpace();
		
	}
	
	public void displayPersonalInfo(Employee employeee)
	{
		
		System.out.println(" PERSONAL INFORMATION :");
		Utils.printLine();
		System.out.println(" Mobile		  : "+employeee.getMobileNum());
		System.out.println(" Email ID	  : "+employeee.getEmailID());
		System.out.println(" Address	  : "+employeee.getAddress());
		System.out.println(" Work Experience  : "+employeee.getWorkExperience());
		System.out.println(" Education	  : "+employeee.getEducation());
		Utils.printLine();
		Utils.printSpace();
		
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public String getMobileNum()
//	{
//		
//		Utils.printSpace();	
//		System.out.println(" Enter 10 Digit Number.");
//		String number = Utils.getStringInput();
//		return number;
//	}
//	
//	public String getPersonalMail()
//	{
//		
//		Utils.printSpace();
//		System.out.println(" Enter Mail ID.");
//		String mailID = Utils.getStringInput();
//		return mailID;
//	}
	

}

package viewController;

import java.sql.*;
import java.util.*;
import dbController.*;
import model.*;
import utils.*;


public class HRViewController 
{
	
	
	//creating object for senior employee ViewController
	SeniorEmployeeViewController seniorEmployeeView = new SeniorEmployeeViewController();
		
		
	//checks the input, only 3 wrong inputs should be allowed
	int inputLimit = 0;

	
	public void displayHrMenu(int requestCount)
	{

		 System.out.println(" Features : ");
		 Utils.printLine();
		 System.out.println(" 1. Add Team");  
		 System.out.println(" 2. Add Employee");  
		 System.out.println(" 3. Add Role"); 
		 System.out.println(" 4. Add Work Location"); 
		 System.out.println(" 5. Add Leave Type"); 
		 System.out.println(" 6. View Team Info ");  
		 System.out.println(" 7. Edit Employee Info");
		 System.out.print(" 8. Employee Requests ");
		 if(requestCount > 0)
		 {
			 System.out.println(" ~ [" + requestCount + "] unread");
		 }
		 else
		 {
			 Utils.printSpace();
		 }
		 System.out.println(" 9. Logout.");
		 Utils.printLine();
		 Utils.printSpace();

	}
	
	public int getInputFromHR()
	{
		try
		{
			int input = Utils.getIntInput();
			return input;
		}
		catch(InputMismatchException e)
		{
			Utils.scanner.nextLine();
			return 0;
		}
	}
	
	public String getTeamName()
	{
		System.out.println(" Enter Team Name ");
		return Utils.getStringInput();	
	}
	
	public int getTeamID()
	{
		
		System.out.println(" Select Team ID to proceed : \n");
		return getInputFromHR();
	}

	public String getRoleName()
	{
		
		System.out.println(" Enter Role Name ");
		String name = Utils.getStringInput().toUpperCase();
		
		if(EmployeeValidation.isInputNameValid(name))
		{
			return name;
		} 
		else
		{
			inputLimit++;
			
			if(inputLimit == 3)
			{
				inputLimit = 0;
				return null;
			}
			
			System.out.println("   * Role length should be minimum of 2 characters and starts with alphabet\n");
			return getRoleName();
		}
	}
	
	public void displayRolePriority(ArrayList<Role> roles)
	{
		
		Utils.printLine();
		System.out.println("  PRIORITY ID     ROLE NAME ");
		Utils.printLine();
		
		for(Role role : roles)
		{
			System.out.printf("  %5s   -     %-10s   \n",role.getRolePriority(),role.getRoleName());
		}
		
		Utils.printLine();
		Utils.printSpace();
	}
	
	public int getRolePriority()
	{
		
		System.out.println("  NOTE : Select Priority ID to add Next to the selected Role priority \n");
		return getInputFromHR();	

	}
	
	public String getLocationName()
	{
		System.out.println(" Enter Location Name to add :");
		return Utils.getStringInput().toUpperCase();
	}
	
	public void isEmployeeAddedSuccessful(boolean isAdded)
	{
		
		if(isAdded)
		{
			Utils.printMessage(StringConstant.EMPLOYEE_ADDED_SUCCESSFUL);
		}
		else
		{
			Utils.printMessage(StringConstant.ADD_EMPLOYEE_FAILED);
		}
		
	}
	
	public void displayRoles(ArrayList<Role> roles)
	{
		
		if(RoleDBController.isRoleAvailable() >= 2)   //check if atleast two role is present to display
		{
			Utils.printLine();
			System.out.println("  ROLE ID    ROLE NAME ");
			Utils.printLine();
			
			for(Role role : roles)
			{
					System.out.printf("  %5s   -   %-5s\n",role.getRoleID(),role.getRoleName());
			}
			
			Utils.printLine();
			Utils.printSpace();
		}
		else
		{
			System.out.println("  No Role is Available to display!!\n");
		}
			
	}
	
	public int getRoleID()
	{
		System.out.println(" Select Role ID to proceed : \n");
		return getInputFromHR();
	}
	
	public String getName()
	{
		Utils.printSpace();
		System.out.println(" Enter User Name : ");
		Utils.printSpace();
		System.out.println("   * Name should be minimum of 3 characters.");
		Utils.printSpace();
		System.out.println("   * It should not have Numberic values and does not end with [.] period ");
		Utils.printSpace();
		
		return Utils.getStringInput();
	}
	
	public int getGenderInput()
	{
		
		System.out.println(" Choose Gender :");
		Utils.printSpace();
		System.out.println(" 1. MALE");
		System.out.println(" 2. FEMALE");
		System.out.println(" 3. Others");
		
		return getInputFromHR();
	}
	
	public void printReportingID(ResultSet result)
	{
		
		try 
		{
			if(!result.next())  //return true if resultSet is Empty
			{
				System.out.println("  You have prefered Higher Role...\n");
				System.out.println("  So, this Role has automatically  set default Reporting to -> CEO\n");
			}
			else
			{
				result.previous();
				
				Utils.printLine();
				System.out.println("    ID        NAME           ROLE");
				Utils.printLine();
			
				while(result.next())
				{
					
					int id = result.getInt(DBConstant.ID);
					String name = result.getString(DBConstant.NAME);
					String role = result.getString(DBConstant.ROLE_NAME);
					System.out.printf("   %3s        %-12s    %-5s  \n",id, name, role);
				}
				Utils.printLine();
				Utils.printSpace();
			}
		}
			catch (SQLException e) 
			{
					System.out.println("  Error in printing Reporting ID");
			}
	}
	
	public int getReportingID()
	{
		System.out.println(" Select Reporting ID : \n");
		return getInputFromHR();
	}
	
	public String getJoiningDate()
	{
		
		System.out.println("  Enter Employee Date of Joining : format -> [ dd/mm/yyyy ] ");
		Utils.printSpace();
		System.out.println("  * you can Choose Date Of Joining from a week before "+Utils.getTodayDate());
		Utils.printSpace();
		
		return Utils.getStringInput();
	}
	
	public int getLocationID()
	{
		System.out.println(" Choose Location ID: \n");
		return getInputFromHR();
	}
	
	public void printworkLocation(ArrayList<WorkLocation> locations)
	{

		Utils.printLine();
		System.out.println("     ID          NAME ");
		Utils.printLine();
		
		for(WorkLocation location : locations)
		{
			System.out.printf("   %3s    -      %-10s  \n",location.getLocationID(), location.getLocationName());
		}
		
		Utils.printLine();
		Utils.printSpace();
	}
	
	public String getEmailID()
	{
		System.out.println("  * Add some Characters [a-z] or Digits [0-9] or Special Characters [-.&$_] ");
		Utils.printSpace();
		System.out.println(" Please enter USERNAME only. Domain Name will be automatically generated.");
		Utils.printSpace();
		
		return Utils.getStringInput()+"@zoho.in";
		
	}
	
	public int getEmployeeID()
	{
		System.out.println("Enter User ID : ");
		return getInputFromHR();
	}

	public void printConfirmUserBeforeEdit()
	{
		System.out.println(" 1. Confirm User profile before Edit");
		System.out.println(" 2. Back");
		Utils.printSpace();
	}
	
	public void displayEditMenu()
	{
		
		Utils.printSpace();
		System.out.println(" Choose an Option : ");
		Utils.printLine();
		System.out.println(" 1. Change Team");
		System.out.println(" 2. Change Role");
		System.out.println(" 3. Edit Reporting to");
		System.out.println(" 4. Edit Work Location");
		System.out.println(" 5. Back to Menu");
		Utils.printSpace();
	}
	
	public int viewRequest()
	{
		System.out.println(" 1. View Requests");
		System.out.println(" 2. Back");
		
		return getInputFromHR();
	}
	
	public int getRequestID()
	{
		System.out.println("  Enter Request ID for Team Change : ");
		return getInputFromHR();
	}
	
	public void displayRequests(ArrayList<Request> requests)
	{
		
		Utils.printLine();
		System.out.println("  Requests ");
		Utils.printLine();
		
		for(Request request : requests)
		{
				seniorEmployeeView.printRequest(request);
		}
		
		Utils.printSpace();
	}
	
	public int confirmBeforeChangeTeam()
	{
		System.out.println(" 1. Confirm before Team change");
		System.out.println(" 2. Back");
		
		return getInputFromHR();
	}
	
	public String getLeaveName()
	{
		
		System.out.println(" Enter Leave Name to add :");
		return Utils.getStringInput();
	}
	
	public int getLeaveCount()
	{
		
		//jack
		System.out.println(" Enter Leave count for :");
		return getInputFromHR();
	}
	
	public int getGenderForAddLeave()
	{
		
		System.out.println(" Choose Gender :");
		Utils.printSpace();
		System.out.println(" 1. MALE");
		System.out.println(" 2. FEMALE");
		System.out.println(" 3. EVERYONE ");
		
		return getInputFromHR();
	}

	
}

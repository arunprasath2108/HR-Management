package viewController;

import java.util.*;
import java.util.Map.Entry;
import dbController.*;


public class HR_UI 
{
	
	//creating object for HR_Controller class
	 HR_Controller hrController = new HR_Controller();
	
	 private static final int ADD_TEAM = 1;
	 private static final int ADD_EMPLOYEE = 2;
	 private static final int ADD_ROLE = 3;
	 private static final int ADD_LOCATIONS = 4;
	 private static final int VIEW_TEAM_INFO = 5;
	 private static final int EDIT_EMPLOYEE_INFO = 6;
	 private static final int REQUESTS = 7;
	 private static final int LOG_OUT = 8;

	 
	 //Stop the method for wrong input more than 3 times
	 int inputLimit = 0;
	
	 
	public void listEmployeeMenu()
	{
			displayHrMenu();
			getInputFromHR();
	}
	
	private void displayHrMenu()
	{

		 System.out.println(" Features : ");
		 Utils.printLine();
		 System.out.println(" 1. Add Team");  
		 System.out.println(" 2. Add Employee");  
		 System.out.println(" 3. Add Role"); 
		 System.out.println(" 4. Add Work Location"); 
		 System.out.println(" 5. View Team Info ");  
		 System.out.println(" 6. Edit Employee Info");
		 System.out.print(" 7. Employee Requests ");
		 Utils.printSpace();
		 System.out.println(" 8. Logout.");
		 Utils.printLine();
		 Utils.printSpace();

	}
	
	private void getInputFromHR()
	{
		
		 Utils.printEnterOption();

		 try
		 {
			 int userInput = Utils.getIntInput();
			 
			 switch(userInput)
			 {
			 
				 case ADD_TEAM :
					 addTeam();
					 break;
					 
				 case ADD_EMPLOYEE :
					 addEmployee();
					 break;
					 
				 case ADD_ROLE :
					 addRole();
					 break;
					 
				 case ADD_LOCATIONS :
					 addWorkLocations();
					 break;
					 
				 case VIEW_TEAM_INFO :
					 break;
					 
				 case EDIT_EMPLOYEE_INFO :
					 break;
					 
				 case REQUESTS :
					 break;
					 
				 case LOG_OUT :
					 break;
					 
				 default :

				 				 	
			 }
		 }
		 catch(InputMismatchException e)
		 {
				 Utils.printInvalidInputMessage();
		 }
		 
	}
	
	private void addTeam()
	{
		
		System.out.println(" Enter Team Name ");
		String teamName = Utils.getStringInput();
		
		if(hrController.addTeam(teamName))
		{
			System.out.println("    ~ Successfully Added a Team -> ["+teamName+"]");
			Utils.printSpace();
		}
		
		else
		{
			System.out.println("  Team Name ["+teamName+"] already exists!!");
			Utils.printTryAgainMessage();
		}
		
	}
	
	private void addRole()
	{
		
		System.out.println(" Enter Role Name ");
		String roleName = Utils.getStringInput();
		
		if(hrController.addRole(roleName))
		{
			System.out.println("    ~ Successfully Added a Role -> ["+roleName+"]\n");
		}
		
		else
		{
			System.out.println("  Failed to add new Role!");
			Utils.printTryAgainMessage();
		}
	}
	
	private void addWorkLocations()
	{
		
		System.out.println(" Enter New Location Name ");
		String locationName = Utils.getStringInput();
		
		if(hrController.addWorkLocation(locationName))
		{
			System.out.println("    ~ Successfully Added a new Work Location -> ["+locationName+"]\n");
		}
		
		else
		{
			System.out.println("  Failed to add new Location!");
			Utils.printTryAgainMessage();
		}
	}
	
	private void addEmployee()
	{
		
		if(EmployeeValidation.isTeamsAvailable() == 0)
		{
			System.out.println("  No Team is Available!\n");
			return;
		}

		int teamID = getTeamID();
		if(teamID == 0) { return; };  //exit the method when the Team ID is null
		
		int roleID = getEmployeeRole();
		if(roleID == 0) { return; }; 
		
		
//		getReportingID();
//		getTeamID();
//		getMailID();
//		getDateOfJoining();
//		getWorkLocation();
//		getGender();
	}
	
	private void listTeam()
	{
		
		Utils.printLine();
		System.out.println("  TEAM ID     TEAM NAME ");
		Utils.printLine();
		
		HashMap<Integer,String> teams = hrController.getTeamsName();
		
		for(Entry<Integer, String> teamName : teams.entrySet())
		{
			int teamID = teamName.getKey();
			String name = teamName.getValue();
			System.out.printf("    %2s     -   %-5s \n",teamID, name);
		}
		
		Utils.printLine();
		Utils.printSpace();
		
	}
	
	private int getTeamID()
	{
		
		if(inputLimit == 3)
		{
			inputLimit = 0;
			return 0;
		}
		
		listTeam();
		System.out.println(" Select TEAM ID to proceed : \n");
		
		try
		{
			int userInput = Utils.getIntInput();
			
			if(EmployeeValidation.isTeamIDPresent(userInput))
			{
				return userInput;
			}
			else
			{
				inputLimit++;
				return getTeamID();
			}
			
		}
		
		catch(InputMismatchException e)
		{
			inputLimit++;
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			return getTeamID();
		}
		
	}

	private int getEmployeeRole()
	{
		
		if(inputLimit == 3)
		{
			inputLimit = 0;
			return 0;
		}
		
		displayRoles();
		System.out.println(" Select ROLE ID to proceed : \n");
		
		try
		{
			int userInput = Utils.getIntInput();
			
			if(EmployeeValidation.isRoleIdPresent(userInput))
			{
				return userInput;
			}
			else
			{
				inputLimit++;
				return getEmployeeRole();
			}
			
		}
		
		catch(InputMismatchException e)
		{
			inputLimit++;
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			return getEmployeeRole();
		}
		
	}
	
	private void displayRoles()
	{
		
		Utils.printLine();
		System.out.println("  ROLE ID     ROLE NAME ");
		Utils.printLine();
		
		HashMap<Integer, String> roles = hrController.getRoleNames();
		
		for(Entry<Integer, String> role : roles.entrySet())
		{
			int roleID = role.getKey();
			String roleName = role.getValue();
			
			System.out.printf("    %2s     -   %-5s \n",roleID, roleName);

		}
		
		Utils.printLine();
		Utils.printSpace();
		
	}


}

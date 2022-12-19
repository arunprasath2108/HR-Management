package viewController;

import java.sql.*;
import java.util.*;

import controller.HRController;
import dbController.*;
import model.*;
import utils.*;


public class HRViewController 
{
	
	//checks the input, only 3 wrong inputs should be allowed
	int inputLimit = 0;

	
	public void displayHrMenu()
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
		String name = Utils.getStringInput().toUpperCase();
		return name;		
	}
	
	
	public void isTeamAddedSuccessful(int value, String teamName)
	{
		
		if(value == 1)
		{
			System.out.println("    ~ Successfully Added a Team -> ["+teamName+"]");
			Utils.printSpace();
		}
		
		else if(value == 2)
		{
			System.out.println("  Team ["+teamName+"] already exists!!");
			Utils.printTryAgainMessage();
		}
		else
		{
			System.out.println("   * Please, Enter a valid Team Name to Add \n");
			System.out.println("   * Name should be minimum of 2 characters & start with alphabet.\n");
		}
		
	
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
	
	
	public void isRoleAddedSuccessful(int value, String roleName)
	{
		
		if(value == 1)
		{
			System.out.println("    ~ Successfully Added a Role -> ["+roleName+"]");
			Utils.printSpace();
		}
		
		else if(value == 2)
		{
			System.out.println("  Role ["+roleName+"] already exists!!");
			Utils.printTryAgainMessage();
		}
		else if(value == 3)
		{
			System.out.println("   * Please, Enter a valid Role Name to Add \n");
			System.out.println("   * Name should be minimum of 2 characters & start with alphabet.\n");
		}
		else
		{
			System.out.println("  Failed to Add Role\n");
		}
		
	
	}
	
	public void isLocationAddedSuccessful(int value, String locationName)
	{
		
		if(value == 1)
		{
			System.out.println("    ~ Successfully Added a Location -> ["+locationName+"]");
			Utils.printSpace();
		}
		
		else if(value == 2)
		{
			System.out.println("  Location ["+locationName+"] already exists!!");
			Utils.printTryAgainMessage();
		}
		else if(value == 3)
		{
			System.out.println("   * Please, Enter a valid Location Name to Add \n");
			System.out.println("   * Name should contains a minimal characters & start with alphabet.\n");
		}
		else
		{
			System.out.println("  Failed to add new Location\n");
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
		
		try
		{
			int userInput = Utils.getIntInput();
			return userInput;
		}
		catch(InputMismatchException e)
		{
			inputLimit++;
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			
			if(inputLimit == 3)
			{
				inputLimit = 0;
				return 0;
			}
			return getRolePriority();
		}
	}
	
	
	public String getLocationName()
	{
		
		System.out.println(" Enter Location Name to add :");
		String locationName = Utils.getStringInput().toUpperCase();
		return locationName;
	}
	
	
	public void isEmployeeAddedSuccessful(boolean isAdded)
	{
		
		if(isAdded)
		{
			System.out.println("    ~ Employee Added Successfully\n");
		}
		else
		{
			Utils.printFailedToAddEmployee();
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
		
		try
		{
			int userInput = Utils.getIntInput();
			return userInput;
		}
		catch(InputMismatchException e)
		{
			Utils.scanner.nextLine();
			return 0;
		}
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
		
		String name = Utils.getStringInput();
		return name;
	}
	
	public int getGenderInput()
	{
		
		System.out.println(" Choose Gender :");
		Utils.printSpace();
		System.out.println(" 1. MALE.");
		System.out.println(" 2. FEMALE.");
		System.out.println(" 3. Others.");
		
		try
		{
			int userInput = Utils.getIntInput();
			return userInput;
		}
		catch(InputMismatchException e)
		{
			Utils.scanner.nextLine();
			return 0;
		}

	}
	
	public boolean printReportingIdIfExists(ResultSet result)
	{
		
		try 
		{
			if(!result.next())  //return false if resultSet is Empty
			{
				System.out.println("  You have prefered Higher Role...\n");
				System.out.println("  So, this Role has automatically  set default Reporting to -> CEO\n");
				return false;
			}
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
		} 
		catch (SQLException e)
		{
			System.out.println("  Error !");
		}
		Utils.printLine();
		Utils.printSpace();
		return true;
	}
	
	public int getReportingID()
	{
		System.out.println(" Select Reporting ID : \n");
		try
		{
			int userInput = Utils.getIntInput();
			return userInput;
		}
		catch(InputMismatchException e)
		{
			Utils.scanner.nextLine();
			return 0;
		}
		
	}
	
	public String getJoiningDate()
	{
		
		System.out.println("  Enter Employee Date of Joining : format -> [ dd/mm/yyyy ] ");
		Utils.printSpace();
		System.out.println("  * you can Choose Date Of Joining from a week before "+Utils.getTodayDate());
		Utils.printSpace();
		
		String userInput = Utils.getStringInput();
		return userInput;
	}
	
	public int getLocationID()
	{
		System.out.println(" Choose Location ID: \n");
		
		try
		{
			int userInput = Utils.getIntInput();
			return userInput;
		}
		catch(InputMismatchException e)
		{
			Utils.scanner.nextLine();
			return 0;
		}
		
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
		String mail = Utils.getStringInput()+"@zoho.in";
		
		return mail;
	}
	
	
	public int getEmployeeID()
	{
		
		System.out.println("Enter User ID : ");
		
		try
		{
			int userInput = Utils.getIntInput();
			return userInput;
		}
		catch(InputMismatchException e)
		{
			Utils.scanner.nextLine();
			return 0;
		}
		
	}
	
	public void canEditEmployee(int value)
	{
		if(value == -1)
		{
			System.out.println("  ~ No Employee Available to Edit. \n");
		}
		else if(value == 0)
		{
			System.out.println("  Invalid Employee ID\n");
		}
		else if(value == 1)
		{
			System.out.println("   ~ Can't edit CEO Details.\n");
		}
		else if(value == 2)
		{
			System.out.println("   ~ You can't edit your details without permissions.\n");
		}
	}

	public void processEdit()
	{
		System.out.println(" 1. Confirm User profile before Edit");
		System.out.println(" 2. Back");
		Utils.printSpace();
	}
	
	public int getConfirmForEdit()
	{
		
		try
		{
			int userInput = Utils.getIntInput();
			return userInput;
		}
		catch(InputMismatchException e)
		{
			Utils.scanner.nextLine();
			return 0;
		}
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
	
	public int getInputForEdit()
	{
		
		try
		{
			int userInput = Utils.getIntInput();
			return userInput;
		}
		catch(InputMismatchException e)
		{
			Utils.scanner.nextLine();
			return 0;
		}
		
	}
	
	public void isWorkLocationChanged(boolean isChanged)
	{
		if(isChanged)
		{
			System.out.println("   ~ Work Location changed successful \n");
		}
		else
		{
			System.out.println("  Invalid Location ID\n");
		}
	}
	
	public void isRoleChanged(boolean isChanged)
	{
		if(isChanged)
		{
			System.out.println("   ~ Role changed successful \n");
		}
		else
		{
			System.out.println("  Invalid Role ID\n");
		}
	}
	
	public void isReportingIDChanged(boolean isChanged)
	{
		if(isChanged)
		{
			System.out.println("   ~ Reporting To changed successful \n");
		}
		else
		{
			System.out.println("  Invalid Reporting ID\n");
		}
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	//method for display higher role than the previous role
//	public boolean displayHigherRoles(int rolePriority)
//	{
//		
//		ArrayList<Role> roles = RoleDBController.listRole();
//		
//		if(RoleDBController.higherRoleCount(rolePriority) >= 1)   //check if atleast two role is present to display
//		{
//			Utils.printLine();
//			System.out.println("  ROLE ID    ROLE NAME ");
//			Utils.printLine();
//			
//			for(Role role : roles)
//			{
//				if(role.getRolePriority() < rolePriority &&  !role.getRoleName().equalsIgnoreCase("CEO"))
//				{
//					System.out.printf("  %5s   -   %-5s\n",role.getRoleID(),role.getRoleName());
//				}
//			}
//			
//			Utils.printLine();
//			Utils.printSpace();
//			return true;
//		}
//		else
//		{
//			System.out.println("  No Role is Available to display!!\n");
//			return false;
//		}
//		
//	}

	
//-----------------------------------


//	private  void processInbox(Employee employee)
//	{
//		int requestedID, checker = 0, senderID;
//		System.out.println(" Choose Index number.");
//		Utils.printSpace();
//		
//		try
//		{
//			
//			int userInput = Utils.getIntInput();
//			userInput--;
//			Utils.printSpace();
//			
//			for( String messages : employee.getRequests())
//			{
//				
//				if(messages.indexOf(messages) == userInput)
//				{
//					checker = 1;
//					String[] splitMessage = messages.split("-");
//					senderID = Integer.parseInt(splitMessage[0]); 
//					requestedID = Integer.parseInt(splitMessage[2]);
//					 
//					proceedMessage(employee, userInput, requestedID, senderID, messages );
//					break;
//				}
//			}
//			
//			if(checker == 0)
//			{
//				System.out.println(" Please, Enter a Valid Index Number. ");
//				Utils.printSpace();
//				processInbox(employee);
//				return;
//				
//			}
//			
//		}
//		catch(InputMismatchException e)
//		{
//			
//			Utils.printInvalidInputMessage();
//			Utils.scanner.nextLine();
//			processInbox(employee);
//			return;
//			
//		}
//	}
//
//
//	private  void proceedMessage(Employee employee, int indexOfMessage, int requestID, int senderId, String message) 
//	{
//		
//		try
//		{
//			System.out.println(" Choose a option :");
//			Utils.printSpace();
//			System.out.println(" 1. Change Team");
//			System.out.println(" 2. Back.");
//			Utils.printSpace();
//			int userInput = Utils.getIntInput();
//			Utils.printSpace();
//			switch(userInput)
//			{
//				case PROCESS_REQUEST :
//					processRequest(employee, message, requestID, senderId, indexOfMessage );
//					break;
//					
//				case IGNORE_MESSAGE :
//					break;
//					
//					default :
//						Utils.printInvalidInputMessage();
//						requestMessages(employee);
//						return;
//			}
//			
//		}
//		catch(InputMismatchException e)
//		{
//			Utils.printInvalidInputMessage();
//			Utils.scanner.nextLine();
//			requestMessages(employee);
//			return;
//		}	
//		
//	}
//
//	
//	private void processRequest(Employee employee, String message, int requestID, int senderId , int indexOfMessage )
//	{
//		
//		String[] getTeamName = message.split("-");
//		String teamName = getTeamName[3];
//		editTeamName(requestID, teamName, employee);
//		employee.getRequests().remove(indexOfMessage);
//		
//		//get sender employee object
//		Employee sender = Utils.getEmployeeObject(senderId);
//		//get requester employee object
//		Employee requestBy = Utils.getEmployeeObject(requestID);
//		String requesterName = Utils.getEmployeeName(requestID);
//		
//		sender.setNotificationSeen(false);
//		String messageSender = " ~ Team changed Successfully for \""+requesterName.toUpperCase()+"\"    "+Utils.getCurrentDateTime();
//		sender.getNotification().replace(requestBy.getemployeeID(), messageSender );
//		
//		requestBy.setNotificationSeen(false);
//		String messageRequester = " ~ Your Team changed Successfully to ["+teamName+"]       "+Utils.getCurrentDateTime();
//		requestBy.getNotification().replace(requestBy.getemployeeID(), messageRequester);
//		requestBy.setTeamChanged(false);
//		
//	}
	

//
//public void requestMessages(Employee employee) 
//{
//	
//	if(employee.getRequests().isEmpty() == true)
//	{
//		Utils.printSpace();
//		System.out.println("  ~ No Requests.");
//		Utils.printSpace();
//		return;
//	}
//	
//	Utils.printSpace();
//	System.out.println("  EMPLOYEE REQUESTS : ");
//	Utils.printLine();
//	
//	printRequestMessages(employee);
//	Utils.printSpace();
//	
//	processInbox(employee);
//	
//}
//
////printing message received from others
//private void printRequestMessages(Employee employee)
//{
//	
//	int senderID = 0, requestedID = 0;
//	String msg = "";
//	String requestByName;
//	
//	for( int messageCount = 0; messageCount<employee.getRequests().size(); messageCount++)
//	{
//		
//		String[] splitMessage = employee.getRequests().get(messageCount).split("-");
//		 senderID = Integer.parseInt(splitMessage[0]); 
//		 requestedID = Integer.parseInt(splitMessage[2]);
//		String senderName = Utils.getEmployeeName(senderID);  
//		requestByName = Utils.getEmployeeName(requestedID);  
//		msg = splitMessage[1];		
//		String newTeam = splitMessage[3];
//		String dateTime = splitMessage[4];
//	
//		messageCount++;
//		System.out.println("  "+messageCount+" - "+senderName+"           "+dateTime);
//		messageCount--;
//		
//		Utils.printSpace();
//		System.out.println("      ~ "+msg+"  Employee ID : "+requestByName+"   TEAM : "+newTeam);
//		Utils.printSpace();
//		
//	}
//	
//}
//
//

	
}

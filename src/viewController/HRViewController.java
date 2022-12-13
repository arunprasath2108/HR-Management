package viewController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;
import dbController.*;
import model.*;
import utils.*;


public class HRViewController 
{
	
	//creating object for HR_Controller class
	 HRDBController hrController = new HRDBController();
	
	 private static final int ADD_TEAM = 1;
	 private static final int ADD_EMPLOYEE = 2;
	 private static final int ADD_ROLE = 3;
	 private static final int ADD_LOCATIONS = 4;
	 private static final int VIEW_TEAM_INFO = 5;
	 private static final int EDIT_EMPLOYEE_INFO = 6;
	 private static final int REQUESTS = 7;
	 private static final int LOG_OUT = 8;
	 
	 //Gender Input
	 private static final int MALE = 1;
	 private static final int FEMALE = 3;
	 private static final int OTHERS = 2;


	 
	 //stop the method for wrong input more than 3 times
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
		
		if(inputLimit == 3)
		{
			inputLimit = 0;
			return;
		}
		
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
					 return;
					 
				 default :
					 inputLimit++;
					 Utils.printInvalidInputMessage();
					 listEmployeeMenu();
					 return;
			 }
		 }
		 catch(InputMismatchException e)
		 {
			 inputLimit++;
			 Utils.printInvalidInputMessage();
			 Utils.scanner.nextLine();
			 listEmployeeMenu();
			 return;
		 }
	}

	private void addTeam()
	{
		
		System.out.println(" Enter Team Name ");
		String teamName = Utils.getStringInput();
		Team team = new Team(teamName);
		
		if(TeamDBController.addTeam(team))
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
		
		//check if atleast one role is present in Table
		if(RoleDBController.isRoleAvailable() == 0)
		{
			return;
		}
		
		System.out.println(" Enter Role Name ");
		String roleName = Utils.getStringInput();
		
		int previousID = getRolePriority();
		if(previousID == 0) { return; };
		
		if(RoleDBController.changeRolePriority(previousID))
		{
			
			previousID++;
			Role role = new Role(roleName, previousID);
			
			if(RoleDBController.addRole(role))
			{
				System.out.println("    ~ Successfully Added a Role -> ["+roleName+"]\n");
			}
			
			else
			{
				System.out.println("  Failed to add new Role!");
				Utils.printTryAgainMessage();
			}
		}
		else
		{
			System.out.println( "  Can't Add Role, Error occured in changing Priority \n");
		}
		
	}

	private void displayRolePriority()
	{
		
		ArrayList<Role> roles = RoleDBController.listRole();
		Utils.printLine();
		System.out.println("  PRIORITY ID     ROLE NAME ");
		Utils.printLine();
		
		for(Role role : roles)
		{
			System.out.printf("  %5s   -     %-10s   \n ",role.getRolePriority(),role.getRoleName());
		}
		
		Utils.printLine();
		Utils.printSpace();
	}
	
	private int getRolePriority()
	{
		
		if(inputLimit == 3)
		{
			inputLimit = 0;
			return 0;
		}
		
		displayRolePriority();
		System.out.println("  NOTE : Select Priority ID to add Next to the selected Role priority \n");
		
		try
		{
			int userInput = Utils.getIntInput();
			
			if(EmployeeValidation.isRolePriorityPresent(userInput))
			{
				return userInput;
			}
			else
			{
				inputLimit++;
				Utils.printInvalidInputMessage();
				return getRolePriority();
			}
			
		}
		
		catch(InputMismatchException e)
		{
			inputLimit++;
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			return getRolePriority();
		}
		
		
	}
	
	
	private void addWorkLocations()
	{
		
		System.out.println(" Enter New Location Name ");
		String locationName = Utils.getStringInput();
		
		if(HRDBController.addWorkLocation(locationName))
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
		
		if(!EmployeeValidation.isTeamsAvailable())
		{
			System.out.println("  No Team is Available!\n");
			return;
		}

		int teamID = getTeamID();
		if(teamID == 0) { return; };  //exit the method when the Team ID is null
		
		int roleID = getEmployeeRole();
		if(roleID == 0) { return; }; 
		
		String name = getNameInput();
		if(name.isEmpty()) { return; };
		
		String gender = getGenderInput();
		if(gender.isEmpty()) { return; };
		
		int rolePriority = RoleDBController.getRolePriority(roleID);
		
		int reportingID = getReportingID(teamID, rolePriority);
		if(reportingID == 0) { return; };
		
		//doj
//		String doj = getDateInput();
//		System.out.println(doj);
//		if(doj.isEmpty()) { return; };
		
		int workLocation = getWorkLocationID();
		if(workLocation == 0) { return; };
		
		//trim the name for generating official mail id
		String nameAfterTrim = getNameWithoutSpace(name);
		
		String newMail = nameAfterTrim.toLowerCase()+"@zoho.in";
		
		if(EmployeeValidation.isOfficialMailExists(newMail))
		{
			newMail = "";
			System.out.println("  Mail Id Already Exists  -->  " +name+"@zoho.in \n");
			newMail = getEmailID();
			
			if(newMail.isEmpty())
			{ return ; };
		}
		
		//Employee employee = new Employee( id, name, roleID, reportingID, teamID, newMail, doj, workLocation, gender);

	}
	
	
	private String getNameInput()
	{
		
		if(inputLimit == 3 )
		{
			inputLimit = 0;
			return "";
		}
		
		Utils.printSpace();
		System.out.println(" Enter User Name : ");
		Utils.printSpace();
		System.out.println("   * Name should be minimum of 3 characters.");
		Utils.printSpace();
		System.out.println("   * It should not have Numberic values and does not end with [.] period ");
		Utils.printSpace();
		
		
		String name = Utils.getStringInput();
		Utils.printSpace();
		
		if(EmployeeValidation.isNameValid(name))
		{
			return name;
		}
		else
		{
			inputLimit++;
			Utils.printInvalidInputMessage();
			return getNameInput();
		}
		
	}

	private void listTeamName()
	{
		
		ArrayList<Team> teams = TeamDBController.listTeam();
		Utils.printLine();
		System.out.println("  TEAM ID      TEAM NAME ");
		Utils.printLine();
		
		for(Team team : teams)
		{
			System.out.printf("   %-2s     -     %-10s \n ",team.getTeamID(),team.getTeamName());
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
		
		listTeamName();
		System.out.println(" Select TEAM ID to proceed : \n");
		
		try
		{
			int userInput = Utils.getIntInput();
			
			if(EmployeeValidation.isTeamIdPresent(userInput))
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
		
		ArrayList<Role> roles = RoleDBController.listRole();
		Utils.printLine();
		System.out.println("  ROLE ID    ROLE NAME ");
		Utils.printLine();
		
		for(Role role : roles)
		{
			System.out.printf("  %5s   -   %-5s\n ",role.getRoleID(),role.getRoleName());
		}
		
		Utils.printLine();
		Utils.printSpace();
		
	}
	

	private int getReportingID(int teamId, int rolePriority)
	{
		
		if(inputLimit == 3)
		{
			inputLimit = 0;
			return 0;
		}
		
		printReportingID(teamId, rolePriority);
		System.out.println(" Select Reporting ID : \n");
		
		try
		{
			int userInput = Utils.getIntInput();
			
			if(EmployeeValidation.isEmployeePresent(userInput) && EmployeeValidation.isEmployeeInTeam(teamId, userInput))
			{
				return userInput;
			}
			else
			{
				inputLimit++;
				return getReportingID(teamId, rolePriority);
			}
			
		}
		catch(InputMismatchException e)
		{
			inputLimit++;
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			return getReportingID(teamId, rolePriority);
		}
		
	}
		
	
	private boolean printReportingID(int teamId, int rolePriority)
	{
		
		ResultSet result = EmployeeDBController.getReportingID(teamId,rolePriority);
		
		try 
		{
			
			if(!result.next())  //return false if resultSet is Empty
			{
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
			e.printStackTrace();
			System.out.println("  Error !");
			return false;
		}
		
		Utils.printLine();
		Utils.printSpace();
		return true;
	}
	
	
	private int getWorkLocationID()
	{
		
		if(inputLimit == 3)
		{
			inputLimit = 0;
			return 0;
		}
		
		printworkLocation();
		System.out.println(" Choose Location : \n");
		
		try
		{
			int userInput = Utils.getIntInput();
			
			if(EmployeeValidation.isWorkLocationPresent(userInput))
			{
				return userInput;
			}
			else
			{
				inputLimit++;
				Utils.printInvalidInputMessage();
			}
		}
		
		catch(InputMismatchException e)
		{
			inputLimit++;
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			return getWorkLocationID();
		}
		
		return 0;
		
	}
	
	
	private void printworkLocation()
	{
		
		HashMap<Integer, String> workLocations = new HashMap<>();
		workLocations = EmployeeDBController.getWorkLocation();
		
		Utils.printLine();
		System.out.println("     ID          NAME ");
		Utils.printLine();
		
		for(Entry<Integer, String> locations : workLocations.entrySet())
		{
			int id = locations.getKey();
			String name = locations.getValue();
			System.out.printf("   %3s    -      %-10s  \n",id, name);
		}
		
		Utils.printLine();
		Utils.printSpace();
	}
	
	

//	private String getDateInput()
//	{
//		
//		LocalDate todayDate = Utils.getTodayDate();  
//		System.out.println(" Enter Employee Date of Joining :  [ yyyy-mm-dd ] ");
//		Utils.printSpace();
//		System.out.println("  * you can Choose Date Of Joining from a week before "+todayDate);
//		Utils.printSpace();
//		
//		String joiningDate = Utils.getStringInput();
//		LocalDate inputDateFormat = Utils.getDateFormat(joiningDate);
//		System.out.println("inputDateFormat   "+inputDateFormat);
//		
//		if(EmployeeValidation.isDateValid(todayDate, inputDateFormat))
//		{
//			return joiningDate;
//		}
//		
//		return "";
//		
////		
////		if( EmployeeValidation.isDateValid(inputDateFormat) == true)
////		{
////			return joiningDate;
////		}
////		else
////		{
////			Utils.printSpace();
////			System.out.println(" Please, Enter a Valid Date. ");
////			Utils.printSpace();
////			return getDateInput();
////		}	
//	}
		

	
	private String getGenderInput() 
	{
		
		if(inputLimit == 3)
		{
			inputLimit = 0;
			return "";
		}
		
		String gender;
		Utils.printSpace();
		System.out.println(" Choose Gender :");
		Utils.printSpace();
		System.out.println(" 1. MALE.");
		System.out.println(" 2. FEMALE.");
		System.out.println(" 3. Others.");
		
		try
		{
			int userInput = Utils.getIntInput();
			Utils.printSpace();
			
			switch(userInput)
			{
				case MALE :
					gender = "MALE";
					break;
					
				case FEMALE :
					gender = "FEMALE";
					break;
					
				case OTHERS :
					gender = "OTHERS";
					break;
					
				default :
					inputLimit++;
					Utils.printInvalidInputMessage();
					return getGenderInput();
			}
			
		}
		catch(InputMismatchException e)
		{
			inputLimit++;
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			gender = getGenderInput();
		}
		return gender;
		
	}
	
	private String getEmailID()
	{
		
		if(inputLimit == 3)
		{
			inputLimit = 0;
			return "";
		}
		
		System.out.println("  * Add some Characters [a-z] or Digits [0-9] or Special Characters [-.&$_] ");
		Utils.printSpace();
		System.out.println(" Please enter USERNAME only. Domain Name will be automatically generated.");
		Utils.printSpace();
		String mail = Utils.getStringInput()+"@zoho.in";
		Utils.printSpace();
		
		if( EmployeeValidation.isEmailValid(mail)) 
		{
			if( !EmployeeValidation.isOfficialMailExists(mail))
			{
				return mail;
			}
		}
		else
		{
			inputLimit++;
			Utils.printSpace();
			System.out.println(" Invalid User Name.");
			Utils.printSpace();
			return getEmailID();
		}
		return mail;
		
	}
	
	private String getNameWithoutSpace(String name) 
	{
		
		String[] names = name.split("\s");
		
		StringBuilder userName = new StringBuilder();
		
		for( String nameSplit : names)
		{
			userName.append(nameSplit);
		}
		
		char[] trimName = userName.toString().toCharArray();
		int lastIndex = trimName.length-1;
		
		if(trimName[lastIndex] == '.')
		{
			userName.deleteCharAt(lastIndex);
		}
		
		return userName.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	private void getEmployeeDetails(String teamName) 
//	{
//		
//		
//		Role role = displayEmployeeRoles();
//		Utils.printSpace();
//		
//		String name = getNameInput();
//		Utils.printSpace();
//		
//		String gender = getGenderInput();
//		Utils.printSpace();
//		
//		String reportingToName = getReportingToEmployees(teamName, role);
//		
//		int reportingToID = displayReportingToID(teamName, reportingToName);
//		Utils.printSpace();
//		
//		String doj = getDateInput();
//		Utils.printSpace();
//		
//		String location = displayPreferedLocation();
//		Utils.printSpace();
//		
//		Employee latestEmployee = Resource.employees.get(Resource.employees.size()-1);
//		int employeeID = latestEmployee.getemployeeID();
//		
//		boolean isPresent = EmployeeValidation.isEmployeePresent(++employeeID);
//		
//		String nameAfterTrim = getNameWithoutSpace(name);
//		
//		String newMail = nameAfterTrim.toLowerCase()+"@zoho.in";
//		
//		boolean isMailExist = EmployeeValidation.isOfficialMailExists(newMail);
//		Utils.printSpace();
//		
//		if(isMailExist == true)
//		{
//			newMail = "";
//			System.out.println("  Mail Id Already Exists  -->  " +name+"@zoho.in");
//			Utils.printSpace();
//
//			newMail = getEmailID();
//		}
//		
//		if (isPresent == true)
//		{
//			System.out.println(" User ID " + employeeID + " Already Exists.");
//			Utils.printSpace();
//		} 
//		else 
//		{
//			Employee employee = new Employee(employeeID, name, role, teamName, reportingToName, reportingToID, location, doj, newMail, gender);
//			Resource.employees.add(employee);
//			
//			Resource.officialMail.add(newMail);
		//Employee employee = new Employee(employeeID, name, role, teamName, reportingToName, reportingToID, location, doj, newMail, gender);
//			Utils.printSpace();
//			System.out.println("   ~ Employee added Successful ~ ");
//			Utils.printSpace();
//			EmployeeManager.displayProfile(employee);
//		} 
//		
//	}




//	private Role changeEmployeeRole(Role r) 
//	{
//		
//		Role[] roles = Role.values();
//		System.out.println(" Select role : ");
//		Utils.printLine();
//		int toCheck = 0, noAboveRole = 0;
//
//		for (Role role : roles)
//		{
//			if(Role.CEO != role && Role.HR != role && r.getValue() > role.getValue())
//			{
//				noAboveRole = 1;
//				System.out.println(role.getValue() + " - " + role);
//			}
//		}
//
//		Utils.printSpace();
//		Utils.printSpace();
//		
//		if(noAboveRole == 0)
//		{
//			System.out.println("   ~ \"MANAGER\" - role is the highest position.");
//			Utils.printSpace();
//			return r;
//		}
//		
//		System.out.println(" Choose the Role :");
//		
//		try 
//		{
//			int userInput = Utils.getIntInput();
//			Utils.printSpace();
//			
//			for (Role role : roles)
//			{
//				if (userInput == role.getValue() && r.getValue() > role.getValue() && userInput != Role.HR.getValue() && userInput != Role.HR.getValue())   //&& r.getValue() > role.getValue()
//				{
//					toCheck = 1;
//					return role;
//				}
//			} 
//		} 
//		catch (InputMismatchException e)
//		{
//			Utils.printInvalidInputMessage();
//			Utils.scanner.nextLine();
//			return changeEmployeeRole(r);
//		}
//		
//		if (toCheck == 0) 
//		{
//			System.out.println(" Choose in this Options.");
//			Utils.printSpace();
//			return changeEmployeeRole(r);
//		}
//		return null;
//
//	}
//	
//	
//	private String displayPreferedLocation()
//	{
//		
//		int checkLocationChanged = 0;
//		System.out.println(" Enter User Work location : ");
//		Utils.printSpace();
//
//		PreferedLocation[]  location = PreferedLocation.values();
//		
//		for( PreferedLocation  places : location )
//		{
//			System.out.println(" "+places.getValue()+" - "+places);
//			
//		}
//		
//		try
//		{
//			int userInput = Utils.getIntInput();
//			Utils.printSpace();
//			for( PreferedLocation  places : location )
//			{
//				checkLocationChanged = 1;
//				if( places.getValue() == userInput)
//				{
//					
//					return places.toString();
//				}
//			}
//		}
//		catch(InputMismatchException e)
//		{
//			
//			Utils.printInvalidInputMessage();
//			Utils.scanner.nextLine();
//			return displayPreferedLocation();
//		}
//		
//		if( checkLocationChanged == 1)
//		{
//			Utils.printInvalidInputMessage();
//			return displayPreferedLocation();
//		}
//		return null;
//	}
//	
//	

//	private String getReportingToEmployees(String teamName, Role role)
//	{
//		
//		int ReportingTochecker = 0;
//		boolean noEmployeeInTeam = false;
//		
//		
//		if(role.name().equalsIgnoreCase(Role.MANAGER.name()))
//		{
//			return Resource.employees.get(0).getemployeeName();
//		}
//		
//		System.out.println("Choose Reporting to : ");
//		Utils.printSpace();
//		System.out.println(" Employee ID	Name	  Role	");
//		Utils.printLine();
//		
//		for(Employee employee : Resource.employees)
//		{
//			if(employee.getEmployeeTeamName().equals(teamName))
//			{
//
//				if(employee.getemployeeRole().getValue() < role.getValue())
//				{
//					noEmployeeInTeam = true;
//					System.out.println("      "+employee.getemployeeID()+"  	"+employee.getemployeeName()+"       "+employee.getemployeeRole());
//					
//				}
//				
//			}
//		}
//		
//		if(noEmployeeInTeam == false)
//		{
//			Utils.printSpace();
//			System.out.println("    *  No Employee above the Role prefered are not available * ");
//			Utils.printSpace();
//			System.out.println( "  * Temporary Reporting to, set as ~ CEO");
//			Utils.printSpace();
//			Utils.printSpace();
//			return Resource.employees.get(0).getemployeeName();
//		}
//		
//		Utils.printSpace();
//		System.out.println("Enter Employee ID : ");
//		Utils.printSpace();
//		
//		try
//		{
//			
//			int userID = Utils.getIntInput();
//			Utils.printSpace();
//			
//			for(Employee employee : Resource.employees)
//			{
//				if(employee.getemployeeID() == userID && employee.getEmployeeTeamName().equals(teamName) && employee.getemployeeRole().getValue() < role.getValue())
//				{
//					ReportingTochecker = 1;
//					return employee.getemployeeName();
//				}
//			}
//			
//		}
//		catch(InputMismatchException e)
//		{
//			Utils.printInvalidInputMessage();
//			Utils.printSpace();
//			Utils.scanner.nextLine();
//			return getReportingToEmployees(teamName, role);
//		}
//		
//		if( ReportingTochecker == 0)
//		{
//			Utils.printSpace();
//			String reportName = getReportingToEmployees(teamName, role);
//			return reportName;
//			
//		}
//		
//		return null;
//	}
//	
//	
//	private int displayReportingToID(String teamName, String reportingTo)
//	{
//		
//		if(reportingTo.equalsIgnoreCase(Resource.employees.get(0).getemployeeName()))
//		{
//			return Resource.employees.get(0).getemployeeID();
//		}
//		
//		for(Employee employee : Resource.employees)
//		{
//			if(employee.getemployeeName().equalsIgnoreCase(reportingTo) && employee.getEmployeeTeamName().equalsIgnoreCase(teamName))
//			{
//				return employee.getemployeeID();
//			}
//		}
//		return 0;
//	}
	
	
	

//
//
//	private void displayHrMenu(Employee employee)
//	{
//
//		 System.out.println(" Features : ");
//		 Utils.printLine();
//		 System.out.println(" 1. Add Employee");  
//		 System.out.println(" 2. Edit Employee Info");  
//		 System.out.println(" 3. Add Team ");  
//		 System.out.println(" 4. View Team Info");
//		 System.out.print(" 5. Employee Requests. ");
//		 
//		 if(EmployeeValidation.isRequestsEmpty(employee) == false)
//		 {
//			 int messageCount = Utils.printRequestCount(employee);
//			 System.out.print(" ~ ["+messageCount+"] Unread Messages");
//		 }
//		 
//		 Utils.printSpace();
//		 Utils.printSpace();
//		 System.out.println(" 6. Logout.");
//		 Utils.printLine();
//		 Utils.printSpace();
//
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	private int getEmployeeID( String name)
//	{
//		
//		for(Employee employee : Resource.employees)
//		{
//			if(employee.getemployeeName().equalsIgnoreCase(name))
//			{
//				return employee.getemployeeID();
//			}
//		}
//		
//		return 0;
//		
//	}

//	private void editEmployeeDetails()
//	{
//		
//		Employee getLastIndex = Resource.employees.get(Resource.employees.size()-1);
//		
//		if(getLastIndex.getemployeeID() <= 2)
//		{
//			System.out.println("  ~ No Employee Available to Edit.");
//			Utils.printSpace();
//			return;
//		}
//		
//		System.out.println("Enter User ID : ");
//		
//		try
//		{
//			int userInput = Utils.getIntInput();
//			Utils.printSpace();
//			
//			if(userInput == 1)
//			{
//				System.out.println(" ~ Can't edit CEO Details.");
//				Utils.printSpace();
//				return;
//			}
//			
//			if(userInput == 2)
//			{
//				System.out.println(" ~ You can't edit your details without permissions.");
//				Utils.printSpace();
//				return;
//			}
//	
//				
//			if(EmployeeValidation.isEmployeePresent(userInput) == true)
//			{
//					for(Employee employee : Resource.employees)
//					{
//						if(employee.getemployeeID() == userInput)
//						{
//							processEdit(employee);
//							break;
//						}
//					
//					}
//				
//			}
//			else
//			{
//				Utils.printSpace();
//				System.out.println(" Invalid Employee ID");
//				return;
//			}
//		}
//		catch(InputMismatchException e)
//		{
//			
//			Utils.printInvalidInputMessage();
//			Utils.printSpace();
//			Utils.scanner.nextLine();
//			return;
//			
//		}
//		
//		
//	}
//
//	
//	private void processEdit(Employee employee)
//	{
//		
//		EmployeeManager.displayProfile(employee);
//		System.out.println(" 1. Confirm User profile before Edit");
//		System.out.println(" 2. Back");
//		Utils.printSpace();
//		
//		try
//		{
//			int userInput = Utils.getIntInput();
//			Utils.printSpace();
//			
//			switch(userInput)
//			{
//			
//				case CONFIRM :
//					displayEditOption(employee);
//					break;
//					
//				case BACK :
//					break;
//					
//				default :
//					Utils.printInvalidInputMessage();
//					Utils.printSpace();
//					processEdit(employee);
//					return;
//			}
//		}
//		catch(InputMismatchException e)
//		{
//			
//			Utils.printInvalidInputMessage();
//			Utils.printSpace();
//			Utils.scanner.nextLine();
//			processEdit(employee);
//			return;
//			
//		}
//		
//		
//	}
//
//
//	private void displayEditOption(Employee employee) 
//	{
//		
//		Utils.printSpace();
//		System.out.println(" Choose an Option : ");
//		Utils.printLine();
//		System.out.println(" 1. Change Team.");
//		System.out.println(" 2. Change Role.");
//		System.out.println(" 3. Edit Reporting to");
//		System.out.println(" 4. Edit Work Location");
//		System.out.println(" 5. Back to Menu");
//		Utils.printSpace();
//		
//		try
//		{
//			int userInput = Utils.getIntInput();
//			Utils.printSpace();
//			
//			switch(userInput)
//			{
//			
//			case EDIT_TEAM_NAME :
//				editTeamName(employee);		
//				break;
//				
//			case EDIT_ROLE :
//				editRole(employee);			
//				break;
//				
//			case EDIT_REPORTING_ID :
//				editReportingID(employee);	
//				EmployeeManager.displayProfile(employee);
//				displayEditOption(employee);
//				break;
//				
//			case EDIT_LOCATION :
//				editLocation(employee);     
//				break;
//				
//			case BACK_MENU :
//				break;
//				
//			}
//		}
//		catch(InputMismatchException e)
//		{
//			
//			Utils.printInvalidInputMessage();
//			Utils.printSpace();
//			Utils.scanner.nextLine();
//			displayEditOption(employee);
//			return;
//			
//		}
//
//		
//	}
//
//
//	private void editReportingID(Employee employee)
//	{
//		
//		String reportingTo = getReportingToEmployees(employee.getEmployeeTeamName(), employee.getemployeeRole());
//		Utils.printSpace();
//		
//		if(reportingTo.equalsIgnoreCase(null))
//		{
//			
//				Utils.printLine();
//				System.out.println(" Currently No Employee above your role are Available.");
//		}
//		else
//		{
//			employee.setReportingTo(reportingTo);
//			employee.setReportingToID(getEmployeeID(reportingTo));
//			
//			if(reportingTo.equals(Resource.employees.get(0).getemployeeName()))
//			{
//				System.out.println("  ~ \"Manager\" Reporting to automatically set as - CEO. ");
//	
//			}
//			else
//			{
//				System.out.println("  ~ Reporting to changed Successful. ");
//			}
//		}
//		Utils.printSpace();
//		
//	}
//
//
//	private void editLocation(Employee employee) 
//	{
//		
//		String location = getWorkLocationForPlaceTransfer(employee.getEmployeeWorkLocation());
//		Utils.printSpace();
//		employee.setEmployeeWorkLocation(location);
//		System.out.println("  ~ Work Location Changed Successful.");
//		Utils.printSpace();
//		EmployeeManager.displayProfile(employee);
//		displayEditOption(employee);
//		return;
//		
//	}
//
//
//	private void editRole(Employee employee)
//	{
//		
//		
//		Role role = changeEmployeeRole(employee.getemployeeRole());
//
//		if( role.equals(Role.MANAGER))
//		{
//			employee.setemployeeRole(role);
//			employee.setReportingTo(Resource.employees.get(0).getemployeeName());
//			employee.setReportingToID(Resource.employees.get(0).getemployeeID()); 
//			EmployeeManager.displayProfile(employee);
//			displayEditOption(employee);
//			return;
//		}
//		
//		if( role.getValue() == employee.getemployeeRole().getValue())
//		{
//			displayEditOption(employee);
//			return;
//		}
//		
//		if( employee.getemployeeRole().getValue() > role.getValue())
//		{
//			employee.setemployeeRole(role); 
//			editReportingID(employee);
//			System.out.println("   ~ Role changed Successful. ");
//			Utils.printSpace();
//			EmployeeManager.displayProfile(employee);
//			displayEditOption(employee);
//			return;
//		}
//		else
//		{
//			System.out.println(" please, Choose Role above the previous position");
//			Utils.printSpace();
//			changeEmployeeRole(employee.getemployeeRole());
//			return;
//		}
//		
//		
//	}
//
//
//	private void editTeamName(Employee employee)
//	{
//		int teamID = 0;
//		if(employee.getemployeeRole().name().equals(Role.MANAGER.name()))
//		{
//			
//			Utils.printSpace();
//			System.out.println(" Without CEO Permissions, you can't change Team ..!");
//			Utils.printSpace();
//			Utils.printSpace();
//			displayEditOption(employee);
//			return;
//		}
//		try
//		{
//			if(EmployeeManager.listTeamName(employee.getEmployeeTeamName()) == true)
//			{
//				System.out.println(" Enter Team ID from the List : ");
//				teamID = Utils.getIntInput();
//				Utils.printSpace();
//				
//				if( EmployeeValidation.isTeamIDAlreadyExists(teamID))
//				{
//					String teamName = Utils.getTeamName(teamID);
//					
//					if(teamName.equalsIgnoreCase(employee.getEmployeeTeamName()))
//					{
//						Utils.printSpace();
//						System.out.println(" You are already in "+teamName+" team.");
//						Utils.printSpace();
//						editTeamName(employee);
//						return;
//					}
//					else
//					{
//						employee.setEmployeeTeamName(teamName);
//						editReportingID(employee);
//						System.out.println("  ~ Team Name changed Successful");
//						Utils.printSpace();
//						EmployeeManager.displayProfile(employee);
//						displayEditOption(employee);
//						return;
//					}
//				}
//				else
//				{
//					Utils.printSpace();
//					System.out.println(" ~ No such team is Available");
//					Utils.printSpace();
//					displayEditOption(employee);
//					return;
//				}
//			}
//		}
//		catch(InputMismatchException e)
//		{
//				Utils.printSpace();
//				System.out.println("  Enter Team ID only..!");
//				Utils.printSpace();
//				Utils.scanner.nextLine();
//				editTeamName(employee);
//		}
//		
//		
//	}
//	
//	
//	//overloaded method for editing team name from request.
//	private void editTeamName(int senderID, String newTeam, Employee hr)
//	{
//		
//		Utils.printSpace();
//		Employee employee = Utils.getEmployeeObject(senderID);
//		employee.setEmployeeTeamName(newTeam);
//		Utils.printSpace();
//		System.out.println("   	 Reporting to ~ must be in the same team ");
//		Utils.printSpace();
//		editReportingID(employee);
//		Utils.printSpace();
//	}
	
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
	
	//overloaded method for changing the work location
//private String getWorkLocationForPlaceTransfer(String currentLocation)
//{
//	int checkLocationChanged = 0;
//	System.out.println(" Enter User Work locationnn : ");
//	Utils.printSpace();
//	
//	PreferedLocation[]  location = PreferedLocation.values();
//
//	for( PreferedLocation  places : location )
//	{
//		
//		if( places.name().equalsIgnoreCase(currentLocation))
//		{
//			continue;
//		}
//		
//		else
//		{
//			System.out.println(" "+places.getValue()+" - "+places);
//		}
//		
//	}
//	
//	try
//	{
//		int userInput = Utils.getIntInput();
//		Utils.printSpace();
//		for( PreferedLocation  places : location )
//		{
//			checkLocationChanged = 1;
//			if( places.getValue() == userInput && places.name().equalsIgnoreCase(currentLocation) == false)
//			{
//				
//				return places.toString();
//			}
//		}
//	}
//	catch(InputMismatchException e)
//	{
//		
//		Utils.printInvalidInputMessage();
//		Utils.scanner.nextLine();
//		return getWorkLocationForPlaceTransfer(currentLocation);
//	}
//	
//	if( checkLocationChanged == 1)
//	{
//		Utils.printInvalidInputMessage();
//		return getWorkLocationForPlaceTransfer(currentLocation);
//	}
//	return null;
//}
//
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

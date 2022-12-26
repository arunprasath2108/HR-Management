package controller;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import dbController.*;
import model.*;
import utils.*;
import viewController.*;


public class HRController
{
	
	 //creating object for HR viewController
	 HRViewController hrView = new HRViewController();
	
	 //creating object for Employee viewController
	 EmployeeViewController employeeView = new EmployeeViewController();
	
	 //creating object for Senior Employee Controller
	 SeniorEmployeeController seniorEmployeeController = new SeniorEmployeeController();

	
	 // getInputFromHR method 
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
	 private static final int FEMALE = 2;
	 private static final int OTHERS = 3;
	 
	 //Edit Employee Details
	 private static final int EDIT_TEAM_NAME = 1;
	 private static final int EDIT_ROLE = 2;
	 private static final int EDIT_REPORTING_ID = 3;
	 private static final int EDIT_LOCATION = 4;
	 private static final int BACK_MENU = 5;
	 
	 //process Edit
	 private static final int CONFIRM = 1;
	 private static final int BACK = 2;
	 
	 private static final int VIEW_REQUEST = 1;
	 
	 
	 //stop the method for wrong input more than 3 times
	 int inputLimit = 0;
	 
	 
	 
	public void listEmployeeMenu()
	{
		int requestCount = requestCountForHR();
		hrView.displayHrMenu(requestCount);
		getInputFromHR();
	}
	
	
	public void getInputFromHR()
	{
			 
		 int userInput = hrView.getInputFromHR();
		 
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
				 seniorEmployeeController.viewTeam();
				 break;
				 
			 case EDIT_EMPLOYEE_INFO :
				 editEmployeeDetails();
				 break;
				 
			 case REQUESTS :
				 requests();
				 break;
				 
			 case LOG_OUT :
				 return;
				 
			 default :
				 inputLimit++;
				 if(inputLimit == 3)
				 {
					 inputLimit = 0;
					 return;
				 }
				Utils.printMessage(StringConstant.INVALID_INPUT);
		 }
		 listEmployeeMenu();	 
	}
	
	public int requestCountForHR()
	{
		
		ArrayList<Request>  requests = RequestDBController.getRequestsForHR();

		return requests.size();
	}
	
	public void addTeam()
	{
		
		String teamName = hrView.getTeamName();
		
		if(EmployeeValidation.isInputNameValid(teamName))
		{
			Team team = new Team(teamName);
			
			if(TeamDBController.addTeam(team))
			{
				Utils.printMessage(StringConstant.TEAM_ADDED_SUCCESS + teamName);
			}
			else
			{
				Utils.printMessage("  "+teamName+StringConstant.ALREADY_EXIST);
				Utils.printMessage(StringConstant.TRY_AGAIN);
			}
		}
		else
		{
			Utils.printErrorMessageInAdd();
		}
	}
	
	private void addRole()
	{
		
		//check if atleast two role is present in Table
		if(RoleDBController.isRoleAvailable() < 2 )
		{
			Utils.printMessage(StringConstant.ADD_ROLE_FAILED);
			return;
		}
		
		String roleName = hrView.getRoleName();
		
		if(roleName == null)
		{
			Utils.printErrorMessageInAdd();
			return;
		}
		
		if(RoleDBController.isRolePresent(roleName))
		{
			Utils.printMessage("  "+roleName+StringConstant.ALREADY_EXIST);
			Utils.printMessage(StringConstant.TRY_AGAIN);
			return;
		}
		
		//get already exist role id to insert new role in between
		int previousID = getPreviousRolePriority();  

		if(previousID == 0)
		{ 
			Utils.printMessage(StringConstant.ADD_ROLE_FAILED);
			return;
		}
		   
		//if priority ID is least Role ID, then add at last
		if(previousID == RoleDBController.getRolePriority(RoleDBController.getLeastRoleID()))
		{
			previousID++;
			Role role = new Role(roleName, previousID);
			if(RoleDBController.addRole(role))
			{
				Utils.printMessage(StringConstant.ROLE_ADDED_SUCCESS + roleName);
				return;
			}
		}
		if(RoleDBController.changeRolePriority(previousID))
		{
			
			previousID++;  //shifting all role & ( rolePriority ID + 1) to next position
			Role role = new Role(roleName, previousID);

			if(RoleDBController.addRole(role))
			{
				Utils.printMessage(StringConstant.ROLE_ADDED_SUCCESS + roleName);
				return;
			}
		}
			Utils.printMessage(StringConstant.ADD_ROLE_FAILED);
	}
	
	private int getPreviousRolePriority()
	{
		
		ArrayList<Role> roles = RoleDBController.listRole();
		hrView.displayRolePriority(roles);
		int userInput = hrView.getRolePriority();

		if(EmployeeValidation.isRolePriorityPresent(userInput))  
		{
			return userInput;
		}

		if( !inputLimitReached(StringConstant.INVALID_INPUT))
		{
			return getPreviousRolePriority();
		}
		return 0;

	}
	
	
	private void addWorkLocations()
	{
		
		String locationName = hrView.getLocationName();
		
		if(EmployeeValidation.isInputNameValid(locationName))
		{
			WorkLocation location = new WorkLocation(locationName);
			
			if(WorkLocationDBController.addWorkLocation(location))
			{
				Utils.printMessage(StringConstant.LOCATION_ADDED_SUCCESS);
			}
			else
			{
				Utils.printMessage("  "+locationName+StringConstant.ALREADY_EXIST);
				Utils.printMessage(StringConstant.TRY_AGAIN);
			}
		}
		else
		{
			Utils.printMessage(StringConstant.ADD_LOCATION_FAILED);
		}
	}
	
	
	private void addEmployee()
	{
		
		if(!EmployeeValidation.isTeamsAvailable())
		{
			System.out.println("  No Team is Available to add!\n");
			return;
		}

		int teamID = seniorEmployeeController.getTeamID();
		if(teamID == 0) 
		{
			Utils.printMessage(StringConstant.ADD_EMPLOYEE_FAILED);
			return;
		} //exit the method when the Team ID is null

		int roleID = getEmployeeRole();
		if(roleID == 0) { return; }; 
		
		String name = getNameInput();
		if(name == null) { return; };
		
		String gender = getGenderInput();
		if(gender == null) { return; };
		
		int rolePriority = RoleDBController.getRolePriority(roleID);
		
		int reportingID = getReportingID(teamID, rolePriority);
		if(reportingID == 0) 
		{
			Utils.printMessage(StringConstant.ADD_EMPLOYEE_FAILED);
			return;
		}
		
		Date doj = getJoiningDate();
		if(doj == null) { return; };
		 
		int workLocation = getWorkLocationID();
		if(workLocation == 0) { return; };
		
//		trim the name for generating official mail id
		String nameAfterTrim = getNameWithoutSpace(name);

		String newMail = nameAfterTrim.toLowerCase()+"@zoho.in";

		if(EmployeeValidation.isOfficialMailExists(newMail))
		{
			newMail = "";
			System.out.println("  Mail Id Already Exists  -->  " +name+"@zoho.in \n");
			newMail = getEmailID();
			
			if(newMail == null)
			{ return ; };
		}
		
		Employee employee = new Employee(name, roleID, reportingID, teamID, newMail, doj, workLocation, gender);
		
		if(EmployeeDBController.addEmployee(employee))
		{
			hrView.isEmployeeAddedSuccessful(true);
			createRowInDB(name);
			employeeView.displayProfile(employee);
		}
		else
		{
			hrView.isEmployeeAddedSuccessful(false);
		}
	}

	void createRowInDB(String name)
	{
		//creates a Row in Personal Info table for this specific employee
		PersonalDBController.createRowInPersonalInfoTable(EmployeeDBController.getEmployeeID(name));
		
	}
	
	private int getEmployeeRole()
	{
		
		hrView.displayRoles(RoleDBController.listRole());
		int userInput = hrView.getRoleID();
		
		if(EmployeeValidation.isRoleIdPresent(userInput))
		{
			return userInput;
		}
		else
		{
			inputLimit++;
			if(inputLimit == 3)
			{
				inputLimit = 0;
				Utils.printMessage(StringConstant.ADD_EMPLOYEE_FAILED);
				return 0;
			}
			Utils.printMessage(StringConstant.INVALID_INPUT);
			return getEmployeeRole();
		}
	}
	
	
	private String getNameInput()
	{
		
		String name = hrView.getName();
		
		if(EmployeeValidation.isNameValid(name))
		{
			return name;
		}
		else
		{
			inputLimit++;
			if(inputLimit == 3 )
			{
				inputLimit = 0;
				Utils.printMessage(StringConstant.ADD_EMPLOYEE_FAILED);
				return null;
			}
			Utils.printMessage(StringConstant.INVALID_INPUT);
			return getNameInput();
		}
		
	}
	
	
	private String getGenderInput() 
	{

		String gender;
		int genderInput = hrView.getGenderInput();
	
		switch(genderInput)
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
				if(inputLimit == 3)
				{
					inputLimit = 0;
					Utils.printMessage(StringConstant.ADD_EMPLOYEE_FAILED);
					return null;
				}
				Utils.printMessage(StringConstant.INVALID_INPUT);
				return getGenderInput();
		}
		return gender;
	}
	
	
	private int getReportingID(int teamId, int rolePriority)
	{
		
		ResultSet result = EmployeeDBController.getReportingID(teamId,rolePriority);
		
		try {
			if(!result.next())
			{
				Utils.NoHigherRoleAvailable();
				return 1;
			}
		} 
		catch (SQLException e)
		{
			System.out.println(StringConstant.INVALID_REPORTING_ID);
			Utils.printSpace();
		}
		hrView.printReportingID(result);
		
		int userInput = hrView.getReportingID();
		
		if(EmployeeValidation.isEmployeePresent(userInput) && EmployeeValidation.isEmployeeInTeam(teamId, userInput))
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
			Utils.printMessage(StringConstant.INVALID_INPUT);
			return getReportingID(teamId, rolePriority);
		}
	}
	
	
	private Date getJoiningDate()
	{
		
		if(inputLimit == 3)
		{
			inputLimit = 0;
			Utils.printMessage(StringConstant.ADD_EMPLOYEE_FAILED);
			return null;
		}
		
		Date date ;
		Date todayDate = Utils.getTodayDateObject();
		
		String joiningDate = hrView.getJoiningDate();
		
		if( !EmployeeValidation.isDateFormatValid(joiningDate))
		{
			inputLimit++;
			Utils.printSpace();
			System.out.println("  Invalid Date Format !");
			Utils.printSpace();
			return getJoiningDate();
		}
		
		Utils.printSpace();
		
		if( EmployeeValidation.isDateValid(joiningDate, todayDate))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try 
			{
				return sdf.parse(joiningDate);
			} 
			catch (ParseException e)
			{
				System.out.println("  Error occured in converting date into date object\n");
			}
		}
		else
		{
			inputLimit++;
			Utils.printSpace();
			System.out.println(" Please, Enter a Valid Date On or Before "+Utils.getTodayDate());
			Utils.printSpace();
			return getJoiningDate();
		}
		
		return null;	
	}
	 

	private int getWorkLocationID()
	{
		 
		hrView.printworkLocation(WorkLocationDBController.getWorkLocation());
		int userInput = hrView.getLocationID();
		
		if(EmployeeValidation.isWorkLocationPresent(userInput))
		{
			return userInput;
		}
		else
		{
			inputLimit++;
			if(inputLimit == 3)
			{
				inputLimit = 0;
				Utils.printMessage(StringConstant.ADD_EMPLOYEE_FAILED);
				return 0;
			}
			
			Utils.printMessage(StringConstant.INVALID_INPUT);
			return getWorkLocationID();	
		}
		
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
	
	
	private String getEmailID()
	{
		
		String mail = hrView.getEmailID();
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
			if(inputLimit == 3)
			{
				inputLimit = 0;
				Utils.printMessage(StringConstant.ADD_EMPLOYEE_FAILED);
				return null;
			}
			
			Utils.printMessage(StringConstant.INVALID_INPUT);
			return getEmailID();
		}
		
		return null;
	}


	private void editEmployeeDetails()
	{
		
		if( !EmployeeDBController.isMinimumEmployeesPresent())
		{
			Utils.printMessage(" No Employees Available");  
			return;
		}
		
		int userInput = hrView.getEmployeeID();
		
		if(userInput == 1 ||userInput == 2 )			//CEO ID - 1 , HR ID - 2
		{
			Utils.printMessage(StringConstant.CANT_EDIT);   //can't edit CEO or HR
			return;
		}
		else if(EmployeeValidation.isEmployeePresent(userInput))
		{
			Employee employee = EmployeeDBController.getEmployee(userInput);
			processEdit(employee);
			return;
		}
		else
		{
			if( !inputLimitReached(StringConstant.EDIT_EMPLOYEE_FAILED))
			{
				Utils.printMessage(StringConstant.INVALID_ID);
				editEmployeeDetails();
			}
		}
	}
	
	
	private void processEdit(Employee employee)
	{

		employeeView.displayProfile(employee);
		hrView.printConfirmUserBeforeEdit();
		
		int userInput = hrView.getInputFromHR();
		
		switch(userInput)
		{
		
			case CONFIRM :
				displayEditOption(employee);
				break;
				
			case BACK :
				return;
				
			default :
				if( !inputLimitReached(StringConstant.EDIT_EMPLOYEE_FAILED))
				{
					Utils.printMessage(StringConstant.INVALID_INPUT);
					processEdit(employee);
				}
		}
	}
	

	private void displayEditOption(Employee employee) 
	{
		
		hrView.displayEditMenu();
		int userInput = hrView.getInputFromHR();
		
		switch(userInput)
		{
		
			case EDIT_TEAM_NAME :
				editTeam(employee);
				break;
				
			case EDIT_ROLE :
				editRole(employee);	
				break;
				
			case EDIT_REPORTING_ID :
				editReportingID(employee.getemployeeID());	
				break;
				
			case EDIT_LOCATION :
				editLocation(employee);     
				break;
				
			case BACK_MENU :
				return;
				
			default :
				if( !inputLimitReached(StringConstant.EDIT_EMPLOYEE_FAILED))
				{
					Utils.printMessage(StringConstant.INVALID_INPUT);
					displayEditOption(employee);
				}
				return;
		}
		
		employeeView.displayProfile(EmployeeDBController.getEmployee(employee.getemployeeID()));
		displayEditOption(employee);
	}
	
	
	boolean inputLimitReached(String message)
	 {
		inputLimit++;
		
		if(inputLimit == 3)
		{
			inputLimit = 0;
			Utils.printMessage(message);
			return true;
		}
		return false;
	}
	
	
	private void editRole(Employee employee)
	{
		
		int rolePriority = RoleDBController.getRolePriority(employee.getemployeeRoleID());
		ArrayList<Role> roles = RoleDBController.listRoleExceptPreviousRole(rolePriority);
		
		if(roles.size() == 0)
		{
			Utils.printMessage(StringConstant.NO_ROLE_AVAILABLE);
			return;
		}
		
		hrView.displayRoles(roles);
		int userInput = hrView.getRoleID();

		if(userInput != employee.getemployeeRoleID() && userInput != 1 && userInput!= 0 && RoleDBController.isRoleIdPresent(userInput))
		{
			
			RoleDBController.setRoleID(userInput, employee.getemployeeID());
			editReportingID(employee.getemployeeID());
			
			Utils.printMessage(StringConstant.ROLE_CHANGED);
			return;
		}
		else
		{
			if( !inputLimitReached(StringConstant.EDIT_EMPLOYEE_FAILED))
			{
				Utils.printMessage(StringConstant.INVALID_ROLE_ID);
				editRole(employee);
			}
			return;
		}
	}

	
	private void editReportingID(int employeeID)
	{
		
		Employee employee = EmployeeDBController.getEmployee(employeeID);
		
		//get the employee (Role) priority id
		int rolePriorty = RoleDBController.getRolePriority(employee.getemployeeRoleID());

		//get the reporting id
		int reportingID = getNewReportingID(employee.getEmployeeTeamID(), rolePriorty, employee.getReportingToID());
		Utils.printSpace();
		if(reportingID == 0)
		{
			return;
		}
		
		if(reportingID == 1)
		{
			EmployeeDBController.setReportingID(reportingID, employee.getemployeeID());
			return;
		}
		else if( reportingID != employee.getReportingToID() && EmployeeValidation.isEmployeeInTeam(employee.getEmployeeTeamID(), reportingID))
		{
			if(EmployeeDBController.setReportingID(reportingID, employee.getemployeeID()))
			{
				Utils.printMessage(StringConstant.REPORTING_ID_CHANGED);
				return;
			}
		}
		else
		{
			if( !inputLimitReached(StringConstant.EDIT_EMPLOYEE_FAILED))
			{
				Utils.printMessage(StringConstant.INVALID_REPORTING_ID);
				editReportingID(employeeID);
			}
			return;
		}
	}
	
	private int getNewReportingID(int teamID, int rolePriority, int employeeID)
	{
		
		ResultSet result = EmployeeDBController.getNewReportingID(teamID,rolePriority, employeeID);
		
		try 
		{
			if(!result.next()) 	 //return true if resultSet is Empty
			{
				Utils.NoHigherRoleAvailable();
				return 1;
			}
			else
			{
				result.previous();
				hrView.printReportingID(result);
				
				int userInput = hrView.getReportingID();
				if(userInput == 0)
				{
					if( !inputLimitReached(StringConstant.EDIT_EMPLOYEE_FAILED))
					{
						Utils.printMessage(StringConstant.INVALID_REPORTING_ID);
						getNewReportingID(teamID, rolePriority, employeeID);
					}
					return 0;
				}
				return userInput;
			}
		} 
		catch (SQLException e)
		{
			System.out.println(StringConstant.INVALID_REPORTING_ID);
			Utils.printSpace();
		}
		return 0;
	}



	private void editLocation(Employee employee) 
	{
		ArrayList<WorkLocation> locations = WorkLocationDBController.getLocationExceptPreviousLocation(employee.getWorkLocationID());
		hrView.printworkLocation(locations);
		
		int userInput = hrView.getLocationID();
		
		if(userInput != employee.getWorkLocationID() && WorkLocationDBController.isWorkLocationPresent(userInput))
		{
			if(WorkLocationDBController.setLocationID(userInput, employee.getemployeeID()))
			{
				Utils.printMessage(StringConstant.LOCATION_CHANGED);
				return;
			}
		}
		else
		{
			Utils.printMessage(StringConstant.INVALID_LOCATION_ID);			
		}
		
		if(!inputLimitReached(StringConstant.EDIT_EMPLOYEE_FAILED))
		{
			editLocation(employee);
		}
	}
	

	private void editTeam(Employee employee)
	{
		
		int employeeID = employee.getemployeeID();
		int teamID = employee.getEmployeeTeamID();
		
		ArrayList<Team> teams = TeamDBController.listTeamExceptCurrentTeam(teamID);
		employeeView.listTeam(teams);
		
		int userInput = hrView.getTeamID();
		
		if(EmployeeValidation.isTeamIdPresent(userInput) && userInput !=teamID)
		{
			if(TeamDBController.setTeamID(userInput, employee.getemployeeID()))
				{
					editReportingID(employeeID);
					Utils.printMessage(StringConstant.TEAM_CHANGED);
					return;
				}
		}
		else
		{
			if( !inputLimitReached(StringConstant.EDIT_EMPLOYEE_FAILED))
			{
				Utils.printMessage(StringConstant.INVALID_TEAM_ID);
				editTeam(employee);
			}
			return;
		}
	}

	
	private void requests()
	{
		
		ArrayList<Request>  requests = RequestDBController.getRequestsForHR();  

		if(requests.isEmpty())
		{
			Utils.printMessage(StringConstant.NO_REQUESTS);
			return;
		}
		
		int userInput = hrView.viewRequest();
		
		switch(userInput)
		{
			case VIEW_REQUEST :
				viewRequests(requests);
				break;
				
			case BACK :
				return;
				
			default :
				if( !inputLimitReached(StringConstant.INVALID_INPUT))
				{
					requests();
				}
		}
	}
	
	
	private void viewRequests(ArrayList<Request> requests)
	{
		
		hrView.displayRequests(requests);
		replyRequest();
	}
	
	private void replyRequest()
	{

		int requestID = hrView.getRequestID();
		
		if(RequestDBController.isRequestWaitingForHRApproval(requestID, "HR"))
		{
			
			//get the specific message for confirmation
			Request request = RequestDBController.getSpecificRequestForHR(requestID);
			
			ArrayList<Request> requests = new ArrayList<>();
			requests.add(request);
			
			hrView.displayRequests(requests);
			handleRequest(request);
		}
		else
		{
			if( !inputLimitReached(StringConstant.CANT_PROCESS_TEAM_CHANGE))
			{
				Utils.printMessage(" Enter valid Request ID ");
				replyRequest();
			}
		}
	}
	
	
	private void handleRequest(Request request)
	{
		
		int userInput = hrView.confirmBeforeChangeTeam();
		
		switch(userInput)
		{
		
			case CONFIRM :
				manageTeamChange(request);
				break;
				
			case BACK :
				return;
				
			default :
				if( !inputLimitReached(StringConstant.CANT_PROCESS_TEAM_CHANGE))
				{
					Utils.printMessage(StringConstant.INVALID_INPUT);
					handleRequest(request);
				}
		}
	}
	
	private void manageTeamChange(Request request)
	{
		
		Employee employee = EmployeeDBController.getEmployee(request.getRequestBy());
		
		int notificationID = NotificationDBController.getNotificationID(request.getRequestID(), request.getRequestBy());

		if(isTeamchanged(employee, request.getTeamID()))
		{
			String notification = " TC"+request.getRequestID()+ " - your team changed successful";
			changeNotification(notificationID, notification, false);
			RequestDBController.deleteRequest(request.getRequestID());
		}
		else
		{
			String notification = " TC"+request.getRequestID()+ " - Can't change your Team, contact your Team Head. ";
			changeNotification(notificationID, notification, false);
		}
	}
	
	private boolean isTeamchanged(Employee employee, int newTeamID)
	{
		
		if(TeamDBController.setTeamID(newTeamID, employee.getemployeeID()))
		{
			editReportingID(employee.getemployeeID());
			Utils.printMessage(StringConstant.TEAM_CHANGED);
			return true;
		}
		
		return false;
	}
	
	
	
	private void changeNotification(int notificationID, String notification, boolean isMessageSeen)
	{
		
		if( !NotificationDBController.changeNotification(notificationID, notification, isMessageSeen))
		{
			Utils.printMessage(" Can't change Notification ! ");
		}
	}
	
	
}

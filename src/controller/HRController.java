package controller;


import java.sql.ResultSet;
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
	 
	 
	 //stop the method for wrong input more than 3 times
	 int inputLimit = 0;
	 
	 
	 
	public void listEmployeeMenu()
	{
		
		hrView.displayHrMenu();
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
				 Utils.printInvalidInputMessage();
		 }
		 listEmployeeMenu();	 
	}
	
	public void addTeam()
	{
		String teamName = hrView.getTeamName();
		
		if(EmployeeValidation.isInputNameValid(teamName))
		{
			Team team = new Team(teamName);
			
			if(TeamDBController.addTeam(team))
			{
				hrView.isTeamAddedSuccessful(1, teamName);
			}
			else
			{
				hrView.isTeamAddedSuccessful(2, teamName);
			}
		}
		else
		{
			hrView.isTeamAddedSuccessful(3, teamName);
		}
	}
	
	private void addRole()
	{
		
		//check if atleast two role is present in Table
		if(RoleDBController.isRoleAvailable() < 2 )
		{
			hrView.isRoleAddedSuccessful(4, "");
			return;
		}
		
		String roleName = hrView.getRoleName();
		
		if(roleName == null)
		{
			hrView.isRoleAddedSuccessful(3, roleName);
			return;
		}
		
		if(RoleDBController.isRolePresent(roleName))
		{
			hrView.isRoleAddedSuccessful(2, roleName);
			return;
		}
		
		int previousID = getPreviousRolePriority();
		if(previousID == 0)
		{ 
			hrView.isRoleAddedSuccessful(4, roleName);
		}
		
		if(RoleDBController.changeRolePriority(previousID))
		{
			previousID++;
			Role role = new Role(roleName, previousID);
			
			if(RoleDBController.addRole(role))
			{
				hrView.isRoleAddedSuccessful(1, roleName);
			}
			else
			{
				hrView.isRoleAddedSuccessful(4, roleName);
			}
		}
		else
		{
			hrView.isRoleAddedSuccessful(4, roleName);
		}
	}
	
	private int getPreviousRolePriority()
	{
		
		if(inputLimit == 3)
		{
			inputLimit = 0;
			return 0;
		}
		
		ArrayList<Role> roles = RoleDBController.listRole();
		hrView.displayRolePriority(roles);
		int userInput = hrView.getRolePriority();
		
		try
		{
			
			if(EmployeeValidation.isRolePriorityPresent(userInput))
			{
				return userInput;
			}
			else
			{
				inputLimit++;
				Utils.printInvalidInputMessage();
				return getPreviousRolePriority();
			}
			
		}
		
		catch(InputMismatchException e)
		{
			inputLimit++;
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			return getPreviousRolePriority();
		}
		
		
	}
	
	private void addWorkLocations()
	{
		
		String locationName = hrView.getLocationName();
		
		if(EmployeeValidation.isInputNameValid(locationName))
		{
			WorkLocation location = new WorkLocation(locationName);
			
			if(WorkLocationDBController.addWorkLocation(location))
			{
				hrView.isLocationAddedSuccessful(1, locationName);
			}
			else
			{
				hrView.isLocationAddedSuccessful(2, locationName);
			}
		}
		else
		{
			hrView.isLocationAddedSuccessful(3, locationName);
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
			Utils.printFailedToAddEmployee();
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
			Utils.printFailedToAddEmployee();
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
			
			//creates a Row in Personal Info table for this specific employee
			PersonalDBController.createRow(EmployeeDBController.getEmployeeID(name));
			employeeView.displayProfile(employee);
		}
		else
		{
			hrView.isEmployeeAddedSuccessful(false);
		}
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
				Utils.printFailedToAddEmployee();
				return 0;
			}
			Utils.printInvalidInputMessage();
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
				Utils.printFailedToAddEmployee();
				return null;
			}
			Utils.printInvalidInputMessage();
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
					Utils.printFailedToAddEmployee();
					return null;
				}
				Utils.printInvalidInputMessage();
				return getGenderInput();
		}
		return gender;
	}
	
	
	private int getReportingID(int teamId, int rolePriority)
	{
		
		ResultSet result = EmployeeDBController.getReportingID(teamId,rolePriority);
		
		boolean isReportingIdPresent = hrView.printReportingIdIfExists(result);
		if( !isReportingIdPresent)
		{
			return 1;   //1 is CEO's ID
		}
		
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
			Utils.printInvalidInputMessage();
			return getReportingID(teamId, rolePriority);
		}
		
	}
	
	
	private Date getJoiningDate()
	{
		
		if(inputLimit == 3)
		{
			inputLimit = 0;
			Utils.printFailedToAddEmployee();
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
				date = sdf.parse(joiningDate);
				return date;
			} 
			catch (ParseException e)
			{
				e.printStackTrace();
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
				Utils.printFailedToAddEmployee();
				return 0;
			}
			
			Utils.printInvalidInputMessage();
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
				Utils.printFailedToAddEmployee();
				return null;
			}
			
			Utils.printInvalidInputMessage();
			return getEmailID();
		}
		
		return null;
		
	}


	private void editEmployeeDetails()
	{
		
		if( !EmployeeDBController.employeesCount())
		{
			hrView.canEditEmployee(-1);  //no employees available
			return;
		}
		
		int userInput = hrView.getEmployeeID();
		if(userInput == 1)			//CEO ID - 1
		{
			hrView.canEditEmployee(userInput);  //can't edit ceo 
			return;
		}
		else if(userInput == 2)   //Default HR - 2
		{
			hrView.canEditEmployee(userInput);  //can't edit HR
			return;
		}
		else if(EmployeeValidation.isEmployeePresent(userInput))
		{
			Employee employee = EmployeeDBController.getEmployee(userInput);
			processEdit(employee);
			return;
			
		}
		else if(userInput == 0)
		{
			hrView.canEditEmployee(userInput);  //Invalid Input.
			inputLimit++;
			
		}
		else
		{
			hrView.canEditEmployee(0);
			inputLimit++;
		}
		
		if(inputLimit == 3)
		{
			inputLimit = 0;
			Utils.printFailedToEditEmployee();
			return;
		}
		editEmployeeDetails();
		return;
		
		
	}
	
	
	private void processEdit(Employee employee)
	{
		
		
		employeeView.displayProfile(employee);

		hrView.processEdit();
		int userInput = hrView.getConfirmForEdit();
		
		switch(userInput)
		{
		
			case CONFIRM :
				displayEditOption(employee);
				break;
				
			case BACK :
				return;
				
			default :
				inputLimit++;
				if(inputLimit == 3)
				{
					inputLimit = 0;
					Utils.printFailedToEditEmployee();
					return;
				}
				Utils.printInvalidInputMessage();
				processEdit(employee);
				return;
		}
		
	}


	private void displayEditOption(Employee employee) 
	{
		
		
		hrView.displayEditMenu();
		int userInput = hrView.getInputForEdit();
		if(userInput == 0)
		{
			inputLimit++;
			if(inputLimit == 3)
			{
				inputLimit = 0;
				Utils.printFailedToEditEmployee();
				return;
			}
			displayEditOption(employee);
			return;
		}
		
		switch(userInput)
		{
		
			case EDIT_TEAM_NAME :
//					editTeamName(employee);
				break;
				
			case EDIT_ROLE :
					editRole(employee);	
				break;
				
			case EDIT_REPORTING_ID :
					editReportingID(employee);	
				break;
				
			case EDIT_LOCATION :
					editLocation(employee);     
				break;
				
			case BACK_MENU :
				return;
				
				default :
					inputLimit++;
					Utils.printInvalidInputMessage();
		}
		
		
		employeeView.displayProfile(EmployeeDBController.getEmployee(employee.getemployeeID()));
		displayEditOption(employee);
		return;

		
	}
	
	
	private void editRole(Employee employee)
	{
		
		int rolePriority = RoleDBController.getRolePriority(employee.getemployeeRoleID());
		
		ArrayList<Role> roles = RoleDBController.listRoleExceptPreviousRole(rolePriority);
		if(roles.size() == 0)
		{
			Utils.printFailedToEditRole();
			return;
		}
		hrView.displayRoles(roles);
		int userInput = hrView.getRoleID();

		if(userInput != employee.getemployeeRoleID() && userInput != 1 && userInput!= 0 && RoleDBController.isRoleIdPresent(userInput))
		{
			RoleDBController.setRoleID(userInput, employee.getemployeeID());
			editReportingID(employee);
			hrView.isRoleChanged(true);
			editReportingID(employee);
			return;
		}
		else
		{
			inputLimit++;
			hrView.isRoleChanged(false);
			if(inputLimit == 3)
			{
				inputLimit = 0;
				Utils.printFailedToEditEmployee();
				return;
			}
		}
		editRole(employee);
		return;
	}

	
	private void editReportingID(Employee employee)
	{
		
		//get the employee (Role) priority id
		int rolePriorty = RoleDBController.getRolePriority(employee.getemployeeRoleID());
		
		//get the reporting id
		int reportingID = getNewReportingID(employee.getEmployeeTeamID(), rolePriorty, employee.getemployeeID());
		
		if(reportingID == 0) 
		{
			//-----------
			return;
		}
		if(reportingID == 1)
		{
			return;
		}
		Utils.printSpace();
		
		if( reportingID != employee.getReportingToID() && EmployeeValidation.isEmployeeInTeam(employee.getEmployeeTeamID(), reportingID))
		{
			if(EmployeeDBController.setReportingID(reportingID, employee.getemployeeID()))
			{
				hrView.isReportingIDChanged(true);
				return;
			}
		}
		else
		{
			inputLimit++;
			if(inputLimit == 3)
			{
				inputLimit = 0;
				Utils.printFailedToEditEmployee();
				return;
			}
			hrView.isReportingIDChanged(false);
			editReportingID(employee);
			return;
			
		}
	}
	
	private int getNewReportingID(int teamID, int rolePriority, int employeeID)
	{
		
		ResultSet result = EmployeeDBController.getNewReportingID(teamID,rolePriority, employeeID);
		
		boolean isReportingIdPresent = hrView.printReportingIdIfExists(result);
		if( !isReportingIdPresent)
		{
			return 1;   //1 is CEO's ID
		}
		
		int userInput = hrView.getReportingID();
		if(userInput == 0)
		{
			return 0;
		}
		else
		{
			return userInput;
		}
		
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
				hrView.isWorkLocationChanged(true);
				return;
			}
		}
		else
		{
			inputLimit++;
			hrView.isWorkLocationChanged(false);
			if(inputLimit == 3)
			{
				inputLimit = 0;
				Utils.printFailedToEditEmployee();
				return;
			}
		}
		
		editLocation(employee);
		return;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	private void editTeamName(Employee employee)
//	{
//		
//		if(inputLimit == 3)
//		{
//			inputLimit = 0;
//			return;
//		}
//		
//		int teamID = employee.getEmployeeTeamID();
//		
//		if(employeeView.listTeamName(teamID))
//		{
//			try
//			{
//				int userInput = Utils.getIntInput();
//				
//				if(EmployeeValidation.isTeamIdPresent(userInput) && userInput != employee.getEmployeeTeamID())
//				{
//					if(TeamDBController.setTeamID(userInput, employee.getemployeeID()))
//					{
//						editReportingID(employee);
//						System.out.println("  ~ Team Changed successful\n");
//						return;
//					}
//					
//				}
//				else
//				{
//					inputLimit++;
//					System.out.println("  Enter Valid Team ID\n");
//				}
//				
//			}
//			catch(InputMismatchException e)
//			{
//				inputLimit++;
//				Utils.printSpace();
//				System.out.println("  Enter Team ID only..!!!\n");
//				Utils.scanner.nextLine();
//			}
//			
//			editTeamName(employee);
//		}
//	}
	

}

package controller;

import java.util.*;
import dbController.*;
import model.Employee;
import utils.*;
import viewController.*;


public class EmployeeController
{
	
	
	EmployeeViewController employeeView = new EmployeeViewController();
	
	
	private static final int VIEW_PROFILE = 1;
	private static final int EDIT_PERSONAL_INFO = 2;
	private static final int APPLY_LEAVE = 3;
	private static final int VIEW_LEAVE_REQUEST = 4;
	private static final int LOG_OUT = 5;
	
	
	private static final int EDIT_MOBILE_NUM = 1;
	private static final int EDIT_EMAIL = 2;
	private static final int EDIT_ADDRESS = 3;
	private static final int EDIT_WORK = 4;
	private static final int EDIT_STUDIES = 5;
	private static final int BACK_MENU = 6;
	
	
	//stops the method while wrong inputs exceeds 3 times
	int inputLimit = 0;
	
	
	
	public void listPTMenu(int userID)
	{
		
		if(inputLimit == 3)
		{
			inputLimit = 0;
			return;
		}
		
		EmployeeValidation.checkProfileCompleted(userID);
		
		employeeView.listEmployeeMenu(userID);
		System.out.println(" 5. LogOut.");
		Utils.printSpace();
		Utils.printEnterOption();
		
		try 
		{
			int userInput = Utils.getIntInput();
			
			Employee employee = EmployeeDBController.getEmployee(userID);
			
			if(getInputFromEmployee(userInput, employee))
			{
				return;
			}
			
		}
		catch(InputMismatchException e)
		{
			inputLimit++;
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
		}
		
		listPTMenu(userID);
	}
	
	public boolean getInputFromEmployee(int userInput, Employee employee)
	{
		
		switch (userInput)
		{
		
			case VIEW_PROFILE:
				employeeView.displayProfile(employee);
				employeeView.displayPersonalInfo(employee);   
				return false;
				
			case EDIT_PERSONAL_INFO:
				employeeView.editPersonalInfo(employee);   
				getInputForEdit(employee);
				return false;
				
			case APPLY_LEAVE:
				//for apply leave
				return false;
				
			case VIEW_LEAVE_REQUEST:
				//view leave request
				return false;
				
			case LOG_OUT :
				return true;
				
			default:
				inputLimit++;
				Utils.printInvalidInputMessage();
				return false;
				
		}
		
	} 
	
	private void getInputForEdit(Employee employee)
	{
		int employeeID = employee.getemployeeID();
		
		Utils.printEnterOption();
		
		try
		{
			int userInput = Utils.getIntInput();
			
			switch(userInput)
			{
					case EDIT_MOBILE_NUM :
//						editMobileNum(employeeID); 
						break;
						
					case EDIT_EMAIL :
//						editMailID(employeeID);   
						break;
						
					case EDIT_ADDRESS :    
//						editAddress(employee);
						break;
						
					case EDIT_WORK :   
//						editWorkExperience(employee);
						break;
						
					case EDIT_STUDIES :   
//						editHighestQualification(employee);
						break;
						
					case BACK_MENU :
						break;
						
					default :
							Utils.printInvalidInputMessage();
							break;
							
			}
		
		}
		catch(InputMismatchException e)
		{
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();	
		}
		
		System.out.println(employee.getemployeeID());
		employeeView.displayPersonalInfo(EmployeeDBController.getEmployee(employee.getemployeeID()));

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	private void editMobileNum(int employeeID)
//	{
//		
//		String number = employeeView.getMobileNum();
//		
//		if( !EmployeeValidation.isMobileNumberValid(number) )
//		{
//				Utils.printSpace();
//				System.out.println("  * INVALID MOBILE NUMBER * \n");
//		}
//		else
//		{
//			if(PersonalDBController.setMobileNumber(number, employeeID))
//			{
//				System.out.println("   ~ Mobile Number added successful \n");
//			}
//		}
//		
//	}
//
//	
//	private void editMailID(int employeeID)
//	{
//		
//		
//		String mailID = employeeView.getPersonalMail();
//		
//		if(EmployeeValidation.isEmailValid(mailID))
//		{
//			
//			if(PersonalDBController.setPersonalMail(mailID, employeeID))
//			{
//				System.out.println("   ~ Mail ID added successful");
//			}
////			editPersonalInfo(employee);
//		}
//		else
//		{
//			Utils.printSpace();
//			System.out.println(" * Invalid Mail ID *");
////			editPersonalInfo(employee);
//		}
//	}
		
//	private static void editAddress(Employee employee)
//	{
//		
//		System.out.println(" Enter your Address in the below format.");
//		Utils.printSpace();
//		System.out.println(" Home Address, Street, City");
//		Utils.printSpace();
//		System.out.println(" sample address ->  1/12, Northcut Road, Coimbatore");
//		Utils.printSpace();
//		
//		String address = Utils.getStringInput();
//		Utils.printSpace();
//		employee.setAddress(address);
//		
//		System.out.println("   ~ Address added successful");
//		editPersonalInfo(employee);
//		
//	}
//	
//	
//	private static void editWorkExperience(Employee employee)
//	{
//		
//		System.out.println(" Enter Company Name : ");
//		String companyName = Utils.getStringInput();
//		Utils.printSpace();
//		
//		System.out.println(" Name of the Role : ");
//		String role = Utils.getStringInput();
//		Utils.printSpace();
//		
//		String timePeriod = getYearsOfExperience();
//		Utils.printSpace();
//		
//		String WorkExperience = companyName+" - "+role+" - "+timePeriod+" years";
//		employee.setWorkExperience(WorkExperience);
//		
//		System.out.println("   ~ Work Experience added Successful");
//		editPersonalInfo(employee);
//		
//
//	}
//	
//	
//	private static String getYearsOfExperience()
//	{
//		
//		Utils.printSpace();
//		System.out.println("  * Number of years you have worked : ");
//		System.out.println("  * Minimum ~ 1 year || Maximum ~ 20 year ");
//		Utils.printSpace();
//		System.out.println("  NOTE : Experience less than 1 Year, Enter as -> 0");
//		Utils.printSpace();
//		
//		String userInput = Utils.getStringInput();
//		Utils.printSpace();
//		
//		if( EmployeeValidation.isExperienceYearValid(userInput))
//		{
//			if( userInput.equalsIgnoreCase("0"))
//			{
//				return "less than 1";
//			}
//			return userInput;
//		}
//		else
//		{
//			return getYearsOfExperience();
//		}
//		
//	}
//	
//	private static void editHighestQualification(Employee employee)
//	{
//		
//		System.out.println(" Enter your Qualification");
//		Utils.printSpace();
//		System.out.println(" 1. B.E / B.Tech ");
//		System.out.println(" 2. M.E / M.Tech ");
//		System.out.println(" 3. Arts Stream (Bsc / Msc / BA)");
//		
//		try
//		{
//			Utils.printSpace();
//			System.out.println(" Choose a option.");
//			Utils.printSpace();
//			String degree = getDegreeInput();
//			Utils.printSpace();
//			
//			if( degree == null)
//			{
//				EmployeeManager.editPersonalInfo(employee);
//				return;
//			}
//			
//			String passedOut = getPassedOutYear();
//			Utils.printSpace();
//
//			String studiesInfo = degree+" - "+passedOut+" passed out";
//			employee.setEducation(studiesInfo);
//			System.out.println("   ~ Educational Qualifications added successful");
//			EmployeeManager.editPersonalInfo(employee);
//			return;
//				
//		}
//			
//		catch(InputMismatchException e)
//		{
//			Utils.printInvalidInputMessage();
//			Utils.scanner.nextLine();	
//			editHighestQualification(employee);
//			return;
//			
//		}
//		
//	}
//	
//	public static String getDegreeInput()
//	{
//		
//		String degree = null;
//		int userInput = Utils.getIntInput();
//		Utils.printSpace();
//		
//		switch(userInput)
//		{
//		
//			case BE_BTECH :
//				degree = "B.E/B.Tech";
//				return degree;
//				
//			case ME_MTECH :
//				degree = "M.E/M.Tech";
//				return degree;
//				
//			case ARTS :
//				degree = "Arts&Science";
//				return degree;
//				
//			default :
//				Utils.printInvalidInputMessage();
//				return degree = getDegreeInput();
//			
//		}
//		
//	}
//	
//	public static String getPassedOutYear( )
//	{
//		
//		System.out.println(" Passed Out year     Format  -> [ yyyy ]");
//		String passedOut = Utils.getStringInput();
//		Utils.printSpace();
//		
//		if(EmployeeValidation.isPassedOutYearValid(passedOut))
//		{
//			return passedOut;
//		}
//		else
//		{
//			
//			System.out.println(" Year must be greater than 1985 or equals to Present Year.");
//			Utils.printSpace();
//			return getPassedOutYear();
//		}
//		
//	}
	
	
	
	
	
	
	

}

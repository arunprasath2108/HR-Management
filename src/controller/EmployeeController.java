package controller;

import java.util.*;
import dbController.*;
import model.*;
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
	
	
	private static final int BE_BTECH = 1;
	private static final int ME_MTECH = 2;
	private static final int ARTS = 3;
	
	private static final int CONFIRM = 1;
	private static final int BACK = 2;

	
	
	//stops the method while wrong inputs exceeds 3 times
	int inputLimit = 0;
	
	
	
	public void listPTMenu(int userID)
	{
		
		if(inputLimit == 3)
		{
			inputLimit = 0;
			return;
		}
		
		EmployeeValidation.isProfileIncomplete(userID);
		
		employeeView.listEmployeeMenu(userID);
		System.out.println(" 5. LogOut.");
		Utils.printSpace();
		Utils.printMessage(StringConstant.ENTER_OPTION);
		
		try 
		{
			int userInput = Utils.getIntInput();
			
			Employee employee = Utils.getEmployee(userID);
			
			if(getInputFromEmployee(userInput, employee))  //true - logout
			{
				return;
			}
		}
		catch(InputMismatchException e)
		{
			inputLimit++;
			Utils.printMessage(StringConstant.INVALID_INPUT);
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
				if( !inputLimitChecker(StringConstant.INVALID_INPUT))
				{
					Utils.printMessage(StringConstant.INVALID_INPUT);
					return false;
				}
				return true;
				
		}
		
	} 
	
	
	
	private void getInputForEdit(Employee employee)
	{
		
		int employeeID = employee.getemployeeID();
		Utils.printMessage(StringConstant.ENTER_OPTION);
		int userInput = employeeView.getInputFromEmployee();
		
		switch(userInput)
		{
			case EDIT_MOBILE_NUM :
				editMobileNumber(employeeID); 
				break;
				
			case EDIT_EMAIL :
				editMailID(employeeID);   
				break;
				
			case EDIT_ADDRESS :    
				editAddress(employeeID);
				break;
				
			case EDIT_WORK :   
				editWorkExperience(employeeID);
				break;
				
			case EDIT_STUDIES :   
				editQualification(employeeID);
				break;
				
			case BACK_MENU :
				return;
				
			default :
				if(! inputLimitChecker(StringConstant.EDIT_EMPLOYEE_FAILED))
				{
					Utils.printMessage(StringConstant.INVALID_INPUT);
					getInputForEdit(employee);
				}
				return;
		}
		employeeView.displayPersonalInfo(Utils.getEmployee(employeeID));
	}
	
	
	private void editMobileNumber(int employeeID)
	{
		
		String mobileNumber = employeeView.getMobileNum();
		
		if( !EmployeeValidation.isMobileNumberValid(mobileNumber) )
		{
				Utils.printMessage(StringConstant.INVALID_MOBILE_NUMBER);
		}
		else
		{
			if(PersonalDBController.setMobileNumber(mobileNumber, employeeID))
			{
				Utils.printMessage(StringConstant.MOBILE_NUMBER_ADDED_SUCCESSFUL);
				return;
			}
		}
		
		if( !inputLimitChecker(StringConstant.EDIT_PERSONAL_INFO_FAILED))
		{
			Utils.printMessage(StringConstant.INVALID_INPUT);
			editMobileNumber(employeeID);
		}
	}
	
	private void editMailID(int employeeID)
	{
		
		String mailID = employeeView.getPersonalMail();
		
		if(EmployeeValidation.isEmailValid(mailID))
		{
			if( !EmployeeValidation.isOfficialMailExists(mailID) && !EmployeeValidation.isPersonalMailExists(mailID))
			{
				if(PersonalDBController.setPersonalMail(mailID, employeeID))
				{
					Utils.printMessage(StringConstant.MAIL_ADDED_SUCCESSFUL);
				}
				else
				{
					Utils.printMessage(StringConstant.EDIT_PERSONAL_INFO_FAILED);
				}
			}
			else
			{
				Utils.printMessage(StringConstant.DUPLICATE_PERSONAL_MAIL);
			}
		}
		else
		{
			if( !inputLimitChecker(StringConstant.EDIT_PERSONAL_INFO_FAILED))
			{
				Utils.printMessage(StringConstant.INVALID_MAIL_ID);
				editMailID(employeeID);
			}
		}
	}
	
	boolean inputLimitChecker(String message)
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


	private void editAddress(int employeeID)
	{
		
		String address = employeeView.getAddress();
		
		if(PersonalDBController.setAddress(address, employeeID))
		{
			Utils.printMessage(StringConstant.ADDRESS_ADDED_SUCCESSFUL);
			return;
		}
		else
		{
			Utils.printMessage(StringConstant.EDIT_PERSONAL_INFO_FAILED);
		}
	}
	
	
	
	private void editQualification(int employeeID)
	{
			
		String degree = getDegreeInput();
		if( degree == null) { return; }
		
		String passedOut = getPassedOutYear(); 
		if(passedOut == null) { return; }
		
		if(PersonalDBController.setHigherQualification(degree, passedOut, employeeID))
		{
			Utils.printMessage(StringConstant.EDUCATION_ADDED_SUCCESSFUL);
		}
		else
		{
			Utils.printMessage(StringConstant.INVALID_QUALIFICATION);
		}
	}
	
	
	
	public String getDegreeInput()
	{
		
		String degree = null;
		int userInput = employeeView.getQualification();
		
		switch(userInput)
		{
		
			case BE_BTECH :
				return "B.E/B.Tech";
				
			case ME_MTECH :
				return "M.E/M.Tech";
				
			case ARTS :
				return "Arts&Science";
				
			default :
				if( !inputLimitChecker(StringConstant.EDIT_PERSONAL_INFO_FAILED))
				{
					Utils.printMessage(StringConstant.INVALID_INPUT);
					return getDegreeInput();
				}
		}
		return degree;
	}
	
	public String getPassedOutYear()
	{
		
		String passedOut = employeeView.passedOutYear();
		
		if(EmployeeValidation.isPassedOutYearValid(passedOut))
		{
			return passedOut;
		}
		else
		{
			if( !inputLimitChecker(StringConstant.EDIT_PERSONAL_INFO_FAILED))
			{
				Utils.printMessage(StringConstant.INVALID_INPUT);
				getPassedOutYear();
			}
		}
		return null;
	}
	
	public void editWorkExperience(int employeeID)
	{
		
		String companyName = employeeView.getCompanyName();
		String roleInCompany = employeeView.getCompanyRole();
		int experienceYears = getYearsOfExperience();
		int experienceMonths = getMonthsOfExperience();
		
		if(experienceYears == 0 && experienceMonths == 0)
		{
			return;
		}
		
		String experience = convertExperienceIntoStringFormat(experienceYears, experienceMonths);
		
		WorkExperience work = new WorkExperience(companyName, roleInCompany, experience);
		
		if(confirmBeforeAddExperience(work) == 0)
		{
			Utils.printMessage(StringConstant.EDIT_PERSONAL_INFO_FAILED);
			return;
		}
		
		if(WorkExperienceDBController.addWorkExperience(employeeID, work))
		{
			Utils.printMessage(StringConstant.WORK_ADDED_SUCCESSFUL);
		}
		else
		{
			Utils.printMessage(StringConstant.EDIT_PERSONAL_INFO_FAILED);
		}
	}
	
	private int confirmBeforeAddExperience(WorkExperience work)
	{
		ArrayList<WorkExperience> works = new ArrayList<>();
		works.add(work);
		employeeView.listWorkExperience(works);
		
		int userInput = employeeView.confirmBeforeAddExperience();
		
		switch(userInput)
		{
			case CONFIRM :
				return 1;
				
			case BACK :
				return 0;
				
			default :
				if(! inputLimitChecker(StringConstant.EDIT_PERSONAL_INFO_FAILED))
				{
					Utils.printMessage(StringConstant.INVALID_INPUT);
					confirmBeforeAddExperience(work);
				}
				return 0;
		}
	}
	

	private int getYearsOfExperience()
	{

		int years = employeeView.getYearsOfExperience();
		
		if( EmployeeValidation.isExperienceYearValid(years))
		{
			return years;
		}
		else
		{
			
			if( !inputLimitChecker(StringConstant.EDIT_PERSONAL_INFO_FAILED))
			{
				Utils.printMessage(StringConstant.INVALID_INPUT);
				getYearsOfExperience();
			}
			else
			{
				return 0;
			}
		}
		return 0;
		
	}
	
	private int getMonthsOfExperience()
	{
		
		int months = employeeView.getMonthsOfExperience();
		
		if( EmployeeValidation.isExperienceMonthValid(months))
		{
			return months;
		}
		else
		{
			
			if( !inputLimitChecker(StringConstant.EDIT_PERSONAL_INFO_FAILED))
			{
				Utils.printMessage(StringConstant.INVALID_INPUT);
				getMonthsOfExperience();
			}
			else
			{
				return 0;
			}
		}
		return 0;
	}
	
	private String convertExperienceIntoStringFormat(int years, int months)
	{
		
		String year = Integer.toString(years);
		String month = Integer.toString(months);
		
		String experience = year + " years and " + month + " months";
		return experience;

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


		

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


	
	
	
	
	
	

}

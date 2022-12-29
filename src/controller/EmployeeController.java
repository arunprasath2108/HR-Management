package controller;

import java.sql.ResultSet;
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
	private static final int NOTIFICATION = 5;
	private static final int LOG_OUT = 6;
	
	
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
	
	private static final int APPLY = 1;


	
	
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
		System.out.println(" 6. LogOut.");
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
				applyLeave(employee);
				return false;
				
			case VIEW_LEAVE_REQUEST:
				displayLeaveReport(employee.getemployeeID());
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
		
		String passedOut = employeeView.getPassedOutYear();
		
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
	
	private void applyLeave(Employee employee)
	{
		
		int userInput = employeeView.getInputForApplyLeave();
		
		switch(userInput)
		{
			case BACK :
				return;
				
			case APPLY :
				break;
				
			default :
				if( !inputLimitChecker(StringConstant.CANT_APPLY_LEAVE))
				{
					Utils.printMessage(StringConstant.INVALID_INPUT);
					applyLeave(employee);
				}
				return;
		}
		
		displayLeave(employee.getemployeeID());
		
		int leaveTypeID = employeeView.getLeaveID();
		
		LeaveBalance leaveBalance = LeaveBalanceDBController.getLeaveBalance(leaveTypeID, employee.getemployeeID());
		if(leaveBalance == null)
		{
			if( !inputLimitChecker(StringConstant.CANT_APPLY_LEAVE))
			{
				Utils.printMessage(StringConstant.INVALID_LEAVE_ID);
				applyLeave(employee);
			}
			return;
		}
		
		if(leaveBalance.getUnusedLeave() == 0)
		{
			if( !inputLimitChecker(StringConstant.CANT_APPLY_LEAVE))
			{
				Utils.printMessage("  you have utilized all days in this leave category\n");
				applyLeave(employee);
			}
			return;
		}
		
		Utils.printMessage(" From Date : ");
		Date fromDate = getDateForApplyLeave();
		if(fromDate == null) { return; }
		
		Utils.printMessage(" To Date : ");
		Date toDate = getDateForApplyLeave();
		if(toDate == null) { return; }
		
		int numberOfDaysApplied = (int) Utils.getDifferenceBetweenTwoDates(toDate, fromDate);
		
		//for one day leave, it return 0 -> so, numberOfDaysApplied++;
		numberOfDaysApplied++;

		if(numberOfDaysApplied < 0)
		{
			Utils.printMessage("  Invalid Date Input");
			return;
		}

		//difference b/w unused leave & appliedDays
		if((leaveBalance.getUnusedLeave() - (numberOfDaysApplied)) >= 0)
		{
			 
			String reasonForLeave = employeeView.getReasonForLeave();
			LeaveManagement leaveManagement = new LeaveManagement(employee.getemployeeID(), employee.getReportingToID(),leaveTypeID,
																			 fromDate, toDate, reasonForLeave, "pending");
			
			autoApproveLeave(leaveManagement);
			sendLeaveRequest(leaveManagement, leaveBalance, numberOfDaysApplied);
		}
		else
		{
			Utils.printMessage("  The Days you have applied are more than your Available leave count");
		}
	}
	
	private void sendLeaveRequest(LeaveManagement leave, LeaveBalance leaveBalance, int numberOfDaysApplied)
	{
		
		if(LeaveManagementDBController.setLeaveRequest(leave))
		{
			LeaveBalanceDBController.changeLeaveBalance(leaveBalance.getLeaveTypeID(), leave.getRequestBy(), (leaveBalance.getUnusedLeave()-numberOfDaysApplied));
			setNotification(leave.getRequestBy(), " Your leave applied successful", false);
			Utils.printMessage("    Leave Applied Successful");
		}
		else
		{
			Utils.printMessage("  Can't apply for leave");
		}
	}
	
	private void autoApproveLeave(LeaveManagement leaveManagement)
	{
		
		Date toDate = leaveManagement.getToDate();
		Date todayDate = Utils.getCurrentDateTime();
		
		if(toDate.compareTo(todayDate) < 0)     //return < 0, today's date comes after than toDate
		{
			leaveManagement.setStatus("approved");
			setNotification(leaveManagement.getRequestBy(), " Your leave approved successful", false);
		}
	}
	
	private Date getDateForApplyLeave()
	{
		
		String date = employeeView.getDateForApplyLeave();
		
		if( !EmployeeValidation.isDateFormatValid(date))
		{
			
			if( !inputLimitChecker(StringConstant.CANT_APPLY_LEAVE))
			{
				Utils.printMessage("  Invalid Date format");
				getDateForApplyLeave();
			}
			return null;
		}
		else
		{
			return Utils.convertStringIntoDate(date);
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
	
	
	private void displayLeave(int employeeID)
	{
		
		ResultSet result = LeaveBalanceDBController.getAvailableLeave(employeeID);
		
		//jack
		employeeView.displayAvailableLeave(result);
	}
	
	
	
	private void displayLeaveReport(int employeeID)
	{
		
		ArrayList<LeaveManagement> leaveReport = LeaveManagementDBController.getLeaveReport(employeeID);
		
		if(leaveReport.size() == 0)
		{
			Utils.printMessage("   No Leave has been applied");
			return;
		}
		
		employeeView.printLeaveReport(leaveReport);
	}

	
	public void setNotification(int employeeID, String message, boolean isMessageSeen)
	{
		if( !NotificationDBController.setNotification(employeeID, message, isMessageSeen))
		{
			Utils.printMessage("  Error in sending Notification !!!");
		}
	}
	
	
	public void changeNotification(int notificationID, String notification, boolean setMessageNotSeen)
	{
		
		if( !NotificationDBController.changeNotification(notificationID, notification, setMessageNotSeen))
		{
			Utils.printMessage(" Can't change Notification ! ");
		}
		
	}
}

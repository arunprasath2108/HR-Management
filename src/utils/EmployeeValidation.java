package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import dbController.*;

public class EmployeeValidation
{
	
	public static boolean isEmployeePresent(int userID)
	{
		
		if(EmployeeDBController.isEmployeePresent(userID))
		{
			return true;
		}
		
		return false;
	} 
	
	public static boolean isTeamsAvailable()
	{
		
		if(TeamDBController.isTeamsAvailable() == 1)
		{
			return true;
		}
		
		return false;
	} 
	
	public static boolean isTeamIdPresent(int id)
	{
		
		if(TeamDBController.isTeamPresent(id))
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean isEmployeeInTeam(int teamID, int userInput)
	{
		
		if(TeamDBController.isEmployeeInTeam(teamID, userInput) == userInput)
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean isRoleIdPresent(int roleID)
	{
		
		if(RoleDBController.isRoleIdPresent(roleID))
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean isWorkLocationPresent(int input)
	{
		
		if(EmployeeDBController.isWorkLocationPresent(input))
		{
			return true;
		}
		
		return false;
	}

	public static boolean isRolePriorityPresent(int input)
	{
		
		if(RoleDBController.isRolePriorityPresent(input))
		{
			return true;
		}
		
		return false;
	}
	
//	public static boolean isDateValid(LocalDate todayDate, LocalDate inputDateFormat )
//	{
//		
//		if((todayDate.compareTo(inputDateFormat))>=1 &&  todayDate.compareTo(inputDateFormat)<7)
//		{
//			return true;
//		}
//		
//		return false;
//	}
	
	public static boolean isNameValid(String name) 
	{
		
		 String input = name;
		 Pattern pattern = Pattern.compile("^[A-Za-z\\s]{1,}[\\.]{0,1}[a-zA-Z\\s]{1,}[\\.]?$");
		 Matcher matcher = pattern.matcher(input);
		 
		 while(matcher.find())
		 {
			 return true;
		 }
		
		return false;
		
	}
	
	public static boolean isEmailValid(String mailID)
	{
		
		String mail = mailID;
		Pattern pattern = Pattern.compile("(^[a-z]{1,})(\\.?)([a-z0-9]{2,64})@{1}([a-z0-9]{2,10})\\.{1}[a-z0-9]{2,5}(\\.?)([a-z]{2,10})?");
		Matcher matcher = pattern.matcher(mail);
		
		while(matcher.find())
		{
			return true;
		}
		
		return false;
		
	}


	public static boolean isOfficialMailExists(String mail)
	{
		
		if(EmployeeDBController.isOfficialMailExist(mail))
		{
			return true;
		}
		
		return false;
		
	}
		
      
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//      		
//      return false;
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("D");
//		String todaysCount = sdf.format(date);
//		int today_Date_Count = Integer.parseInt(todaysCount);
//		
//		
//		String[] currentDate = todayDate.split("/");
//		int currentYear = Integer.parseInt(currentDate[2]);
//		
//	
//		try
//		{
//			
//			String[] userInputDateSplit = joiningDate.split("/");
//			
//			int datee = Integer.parseInt(userInputDateSplit[0]);
//			int month = Integer.parseInt(userInputDateSplit[1]);
//			int year = Integer.parseInt(userInputDateSplit[2]);
//	
//				
//			if(datee >= 01 && datee <=31 && month >= 01 && month <=12 && year == currentYear)
//			{
//				
//				Calendar c = Calendar.getInstance();
//				
//				c.set(year, month, datee );
//				
//				int joinDateCount = c.get(Calendar.DAY_OF_YEAR)-30;
//				
//
//				if(today_Date_Count >= joinDateCount &&  (today_Date_Count)-(joinDateCount) <= 7)
//				{
//					return true;
//				}
//				else
//				{
//					return false;
//				}
//				
//				
//			}
//			else
//			{
//				return false;
//			}
//				
//
//		}
//		catch(Exception e)
//		{
//			Utils.printSpace();
//			System.out.println(" Format -> DD/MM/YYYY ");
//			Utils.printSpace();
//			System.out.println(" Please, enter in this Correct Format.");
//			Utils.printSpace();
//			return false;
//		}
//		
	
	

//
//	public static boolean isPersonalDetailUpdated(Employee employeee)
//	{
//		
//		if(employeee.getEmailID() == null || employeee.getEducation() == null || employeee.getWorkExperience() == null)
//		{
//			return false;
//		}
//		
//		Utils.printSpace();
//		return true;
//		
//	}
//	
//
//	public static boolean isEmailValid(String mailID)
//	{
//		
//		String mail = mailID;
//		Pattern pattern = Pattern.compile("(^[a-z]{1,})(\\.?)([a-z0-9]{2,64})@{1}([a-z0-9]{2,10})\\.{1}[a-z0-9]{2,5}(\\.?)([a-z]{2,10})?");
//		Matcher matcher = pattern.matcher(mail);
//		
//		while(matcher.find())
//		{
//			return true;
//		}
//		
//		return false;
//		
//	}
//	
//	
//	public static boolean isMobileNumberValid(String mobileNum)
//	{
//		
//		 String number = mobileNum;
//		 Pattern pattern = Pattern.compile("^[6-9]{1}[0-9]{9}$");
//		 Matcher matcher = pattern.matcher(number);
//		 
//		 while(matcher.find())
//		 {
//			 return true;
//		 }
//		return false;
//		
//	}
//	
//	
//	public static boolean isPassedOutYearValid(String year)
//	{
//		try
//		{
//			int passOut = Integer.parseInt(year);
//			
//			if( passOut >= 1985 && passOut <=2024)
//			{
//				return true;
//			}
//		}
//		catch(NumberFormatException e)
//		{
//			Utils.printInvalidInputMessage();
//			return false;
//		}
//		
//		return false;
//
//		
//	}
//	
//	
//	public static boolean isTeamAlreadyExists(String name)
//	{
//		
//		if(Resource.teamMap.containsValue(name.toUpperCase()))
//		{
//			return true;
//		}
//		
//		return false;
//		
//	}
//	
//	public static boolean isTeamIDAlreadyExists(int id)
//	{
//		
//		if(Resource.teamMap.containsKey(id))
//		{
//			return true;
//		}
//		
//		return false;
//	}
//	
//
//	public static boolean isExperienceYearValid(String userInput)
//	{
//		
//		
//			try
//			{
//				 int year = Integer.parseInt(userInput);
//				
//				 if(year <= 20 && year >= 0)
//				 {
//					 return true;
//				 }
//			}
//			catch(NumberFormatException e)
//			{
//				return false;
//			}
//			
//			 return false;
//		
//	}
//	
//	public static boolean isOfficialMailExists(String mail)
//	{
//		
//		for( String mails : Resource.officialMail)
//		{
//			if( mails.equalsIgnoreCase(mail))
//			{
//				return true;
//			}
//			
//		}
//
//		return false;
//		
//	}
//	
//	
//	public static boolean isRequestsEmpty(Employee employee)
//	{
//		
//		if(employee.getRequests().isEmpty())
//		{
//			return true;
//		}
//		
//		return false;
//	}
//	
//	public static boolean isNotificationEmpty(Employee employee)
//	{
//		
//		if(employee.getNotification().isEmpty())
//		{
//			return true;
//		}
//		
//		return false;
//	}
//
//

//
//	public static boolean isDateValid(String joiningDate, String todayDate)
//	{
//		
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("D");
//		String todaysCount = sdf.format(date);
//		int today_Date_Count = Integer.parseInt(todaysCount);
//		
//		
//		String[] currentDate = todayDate.split("/");
//		int currentYear = Integer.parseInt(currentDate[2]);
//		
//	
//		try
//		{
//			
//			String[] userInputDateSplit = joiningDate.split("/");
//			
//			int datee = Integer.parseInt(userInputDateSplit[0]);
//			int month = Integer.parseInt(userInputDateSplit[1]);
//			int year = Integer.parseInt(userInputDateSplit[2]);
//	
//				
//			if(datee >= 01 && datee <=31 && month >= 01 && month <=12 && year == currentYear)
//			{
//				
//				Calendar c = Calendar.getInstance();
//				
//				c.set(year, month, datee );
//				
//				int joinDateCount = c.get(Calendar.DAY_OF_YEAR)-30;
//				
//
//				if(today_Date_Count >= joinDateCount &&  (today_Date_Count)-(joinDateCount) <= 7)
//				{
//					return true;
//				}
//				else
//				{
//					return false;
//				}
//				
//				
//			}
//			else
//			{
//				return false;
//			}
//				
//
//		}
//		catch(Exception e)
//		{
//			Utils.printSpace();
//			System.out.println(" Format -> DD/MM/YYYY ");
//			Utils.printSpace();
//			System.out.println(" Please, enter in this Correct Format.");
//			Utils.printSpace();
//			return false;
//		}
//		
//		
//		
//	}
//	
	
	
}

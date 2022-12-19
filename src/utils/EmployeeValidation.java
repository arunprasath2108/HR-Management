package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import dbController.*;
import model.Employee;

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
		
		if(WorkLocationDBController.isWorkLocationPresent(input))
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
	
	public static boolean isDateValid(String date1, Date date2)
	{
		
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/yyyy");
		Date joiningDate = null;
        Date todayDate = date2;
        
		try
		{
			
			joiningDate = simpleDateformat.parse(date1);

            long diff = todayDate.getTime() - joiningDate.getTime();

            long diffDays = diff / (24 * 60 * 60 * 1000);

            if(diffDays >=0 && diffDays <7) 
            {
            	return true;
            }
        } 
		catch (ParseException e) 
		{
            e.printStackTrace();
        }
		return false;
	}
	
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
	
	public static boolean isInputNameValid(String name) 
	{
		
		 String input = name;
		 Pattern pattern = Pattern.compile("^[A-Za-z]{2,}");
		 Matcher matcher = pattern.matcher(input);
		 
		 while(matcher.find())
		 {
			 return true;
		 }
		
		return false;
		
	}
	
	public static boolean isDateFormatValid(String date) 
	{
		
		 String input = date;
		 Pattern pattern = Pattern.compile("^[0-9]{1,2}/{1}[0-9]{1,2}/[0-9]{4}$");
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
	
	public static void checkProfileCompleted(int employeeID) 
	 {
		 		
	 	if( !EmployeeValidation.isPersonalDetailUpdated(employeeID))
	 	{
	 		Utils.printSpace();
	 		System.out.println("   * PROFILE IS INCOMPLETE * ");
	 		Utils.printSpace();
	 	}		
	 }
	

	public static boolean isPersonalDetailUpdated(int employeeID)
	{
		
		if(PersonalDBController.isPersonalInfoUpdated(employeeID))
		{
			return false;
		}

		return true;
		
	}
		
	
	public static boolean isMobileNumberValid(String mobileNum)
	{
		
		 String number = mobileNum;
		 Pattern pattern = Pattern.compile("^[6-9]{1}[0-9]{9}$");
		 Matcher matcher = pattern.matcher(number);
		 
		 while(matcher.find())
		 {
			 return true;
		 }
		return false;
		
	}
      
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	


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


	
	
}

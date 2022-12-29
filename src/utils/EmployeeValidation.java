package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import dbController.*;

public class EmployeeValidation
{
	
	public static boolean isEmployeePresent(int userID)
	{
		
		return EmployeeDBController.isEmployeePresent(userID);
	} 
	
	public static boolean isTeamsAvailable()
	{
		
		return (TeamDBController.isTeamsAvailable() == 1);
	} 
	

	public static boolean isTeamIdPresent(int id)
	{
		
		return TeamDBController.isTeamPresent(id);
	}
	
	public static boolean isEmployeeInTeam(int teamID, int employeeID)
	{
		
		return (EmployeeDBController.isEmployeeInTeam(teamID, employeeID) == employeeID );
	}
	
	public static boolean isRoleIdPresent(int roleID)
	{
		
		return RoleDBController.isRoleIdPresent(roleID);
	}
	
	public static boolean isWorkLocationPresent(int input)
	{
		
		return WorkLocationDBController.isWorkLocationPresent(input);
	}

	public static boolean isRolePriorityPresent(int input)
	{
		
		return RoleDBController.isRolePriorityPresent(input);
	}
	
	public static boolean isJoiningDateValid(String date1, Date date2)
	{
		
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/yyyy");
		Date joiningDate = null;
        Date todayDate = date2;
        
		try
		{
			joiningDate = simpleDateformat.parse(date1);
			
			long differenceInDays = Utils.getDifferenceBetweenTwoDates(todayDate, joiningDate);
			
            return (differenceInDays >=0 && differenceInDays <7);
        } 
		catch (ParseException e) 
		{
            System.out.println(" Error in is Date Valid or not method\n");
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
		
		 Pattern pattern = Pattern.compile("^[A-Za-z]{1,}[\\s]{0,1}[A-Za-z\\s]{1,20}");
		 Matcher matcher = pattern.matcher(name);
		 
		 while(matcher.find())
		 {
			 return true;
		 }
		
		return false;
		
	}
	
	public static boolean isDateFormatValid(String date) 
	{
		
		 Pattern pattern = Pattern.compile("^[0-9]{1,2}/{1}[0-9]{1,2}/[0-9]{4}$");
		 Matcher matcher = pattern.matcher(date);
		 
		 while(matcher.find())
		 {
			 return true;
		 }
		
		return false;
		
	}
	
	public static boolean isEmailValid(String mailID)
	{
		
		Pattern pattern = Pattern.compile("(^[a-z]{1,})(\\.?)([a-z0-9]{1,64})@{1}([a-z0-9]{2,10})\\.{1}[a-z0-9]{2,5}(\\.?)([a-z]{2,10})?");
		Matcher matcher = pattern.matcher(mailID);
		
		while(matcher.find())
		{
			return true;
		}
		
		return false;
		
	}

	public static boolean isOfficialMailExists(String mail)
	{
		
		return EmployeeDBController.isOfficialMailExist(mail);
	}
	
	public static boolean isPersonalMailExists(String mail)
	{
		
		return PersonalDBController.isPersonalMailExist(mail);
	}
	
	
	
	 public static void isProfileIncomplete(int userID)
	 {
		 
		 if( PersonalDBController.isProfileIncomplete(userID))    
		 {														  
			 Utils.printSpace();
			 System.out.println("    * PROFILE IS INCOMPLETE * ");
			 Utils.printSpace();
		 }
	 }
		
	
	public static boolean isMobileNumberValid(String mobileNumber)
	{
		
		 Pattern pattern = Pattern.compile("^[6-9]{1}[0-9]{9}$");
		 Matcher matcher = pattern.matcher(mobileNumber);
		 
		 while(matcher.find())
		 {
			 return true;
		 }
		return false;
		
	}
	
	
	public static boolean isPassedOutYearValid(String year)
	{
		
		try
		{
			int passOut = Integer.parseInt(year);       //year is valid only for current year or +2 year
			return ( passOut >= 1985 && passOut <= Calendar.getInstance().get(Calendar.YEAR)+2);
		}
		catch(NumberFormatException e)
		{
			return false;
		}

	}
	
	public static boolean isExperienceYearValid(int years)
	{
		
		if(years >= 0 && years <=20)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean isExperienceMonthValid(int month)
	{
		
		if(month >= 3 && month <=11)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
      
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	







	
	
}

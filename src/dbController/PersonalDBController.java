package dbController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonalDBController 
{
	
	static PreparedStatement statement;
	
	public static boolean setMobileNumber(String number, int employeeID)
	{
		
		String query = DBConstant.UPDATE + DBConstant.PERSONAL_INFORMATION_TABLE +" "+ DBConstant.SET 
						+ DBConstant.MOBILE +" = '"+ number +"' " + DBConstant.WHERE + DBConstant.EMPLOYEE_ID + " = "+employeeID;
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			int result = statement.executeUpdate();
			
			if(result == 1)
			{
				return true;
			}
			
		} 
		
		catch (SQLException e) 
		{
			System.out.println(" Error occured in setting Mobile number !");
		}
		return false;
	}
	
	
	public static boolean setPersonalMail(String mail, int employeeID)
	{
		
		String query = DBConstant.UPDATE + DBConstant.PERSONAL_INFORMATION_TABLE +" "+ DBConstant.SET 
						+ DBConstant.EMAIL_ID +" = '"+ mail +"' " + DBConstant.WHERE + DBConstant.EMPLOYEE_ID + " = "+employeeID;
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			int result = statement.executeUpdate();
			
			if(result == 1)
			{
				return true;
			}
			
		} 
		
		catch (SQLException e) 
		{
			System.out.println(" Error occured in setting Personal Mail !");
		}
		return false;
	}
	
	public static boolean createRow(int employeeID)
	{
		
		String query = DBConstant.INSERT_INTO + DBConstant.PERSONAL_INFORMATION_TABLE +" ("
						+DBConstant.EMPLOYEE_ID + ") values (" + employeeID +")"; 

		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			int result = statement.executeUpdate();
			
			if(result == 1)
			{
				return true;
			}
			
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println(" Error occured in creating Row in personal Info Table !");
		}
		return false;
	}
	
	
	public static boolean isPersonalInfoUpdated(int employeeID)
	{
		
		String query = DBConstant.SELECT + DBConstant.EMAIL_ID +", "+ DBConstant.DEGREE + " " + DBConstant.FROM
						+  DBConstant.PERSONAL_INFORMATION_TABLE + DBConstant.WHERE + DBConstant.EMPLOYEE_ID + " = " + employeeID;
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				String mail = result.getString(DBConstant.EMAIL_ID);    //  1 - email_id column in personal info
				String degree = result.getString(DBConstant.DEGREE);  //  2 - Degree column in personal info
				
				if(mail == null || degree == null)
				{
					return true;
				}
			}
			
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println(" Error occured in checking personal info updated or not !");
		}
		
		return false;
	}
	

}

package dbController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Employee;


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
			return (statement.executeUpdate() != 0);
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
			return (statement.executeUpdate() != 0);
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in setting Personal Mail !");
		}
		return false;
	}
	
	public static boolean isPersonalMailExist(String mail)
	{
		
		String query = DBConstant.SELECT + DBConstant.EMAIL_ID +" " + DBConstant.FROM 
						+ DBConstant.PERSONAL_INFORMATION_TABLE +" "+ DBConstant.WHERE 
						+ DBConstant.EMAIL_ID + " = '" + mail +"'";
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				if(mail.equalsIgnoreCase(result.getString(DBConstant.EMAIL_ID)))
				{
					return true;
				}
			}
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in Mail Verification !");
		}
		return false;
	}
	
	public static boolean setAddress(String address, int employeeID)
	{
		
		String query = DBConstant.UPDATE + DBConstant.PERSONAL_INFORMATION_TABLE +" "+ DBConstant.SET 
						+ DBConstant.ADDRESS +" = '"+ address +"' " + DBConstant.WHERE + DBConstant.EMPLOYEE_ID + " = "+employeeID;
		
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			return (statement.executeUpdate() != 0);
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in setting current address !");
		}
		return false;
	}
	
	public static boolean setHigherQualification(String degree,String passedOut, int employeeID)
	{
		
		String query = DBConstant.UPDATE + DBConstant.PERSONAL_INFORMATION_TABLE +" "+ DBConstant.SET 
						+ DBConstant.DEGREE +" = '"+ degree +"' , "+DBConstant.PASSED_OUT_YEAR + " = '" + passedOut +"' " + DBConstant.WHERE + DBConstant.EMPLOYEE_ID + " = "+employeeID;
		
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			return (statement.executeUpdate() != 0);
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in setting Qualification !");
		}
		return false;
	}
	
	
	public static boolean createRowInPersonalInfoTable(int employeeID)
	{
		
		String query = DBConstant.INSERT_INTO + DBConstant.PERSONAL_INFORMATION_TABLE +" ("
						+DBConstant.EMPLOYEE_ID + ") values (" + employeeID +")"; 

		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			return (statement.executeUpdate() != 0);
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in creating Row in personal Info Table !");
		}
		return false;
	}
	
	
	public static boolean isProfileIncomplete(int employeeID)
	{
		
		String query = DBConstant.SELECT + DBConstant.EMAIL_ID +", "+ DBConstant.DEGREE + " " + DBConstant.FROM
						+  DBConstant.PERSONAL_INFORMATION_TABLE + DBConstant.WHERE + DBConstant.EMPLOYEE_ID + " = " + employeeID;
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				if(result.getString(DBConstant.EMAIL_ID) == null || result.getString(DBConstant.DEGREE) == null)
				{
					return true;
				}
			}
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in checking personal info updated or not !");
		}
		return false;
	}
	
	public static Employee getEmployee(int employeeID, Employee employee)
	{

		String query = DBConstant.SELECT + " * "+ DBConstant.FROM 
				+ DBConstant.PERSONAL_INFORMATION_TABLE +" "+ DBConstant.WHERE + DBConstant.EMPLOYEE_ID + " = " + employeeID;

		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{

					employee.setMobileNum(result.getString(DBConstant.MOBILE));
					employee.setEmailID(result.getString(DBConstant.EMAIL_ID));
					employee.setAddress(result.getString(DBConstant.ADDRESS));
					employee.setHighestDegree(result.getString(DBConstant.DEGREE));
					employee.setPassedOutYear(result.getString(DBConstant.PASSED_OUT_YEAR));
					
					return employee;
			}
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting Employee Instance !!");
		}
		
		return null;
	}
	

}

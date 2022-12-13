package dbController;

import java.sql.*;
import java.util.HashMap;


public class EmployeeDBController 
{

	static PreparedStatement statement;
	
	
	public static String getEmployeeName(int employeeID)
	{
		
		String query = DBConstant.SELECT + DBConstant.NAME +" "+ DBConstant.FROM 
						+ DBConstant.EMPLOYEE_TABLE +" "+ DBConstant.WHERE + DBConstant.ID + " = " + employeeID;
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			String name = result.getString(DBConstant.NAME);
			return name;
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting Employee Name !");
		}
		
		return null;
		
	}
	
	public static boolean isEmployeePresent(int userID)
	{
		
		String query = DBConstant.SELECT + " * "+ DBConstant.FROM 
				+ DBConstant.EMPLOYEE_TABLE +" "+ DBConstant.WHERE + DBConstant.ID + " = " + userID;

		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				int id = result.getInt(DBConstant.ID);
				
				if(id == userID)
				{
					return true;
				}
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println(" Error occured in getting Employee Name !");
		}

		return false;
	}
	
	public static  ResultSet getReportingID(int userTeamID, int rolePriority)
	{
		
		ResultSet result = null;
		
		String query = DBConstant.SELECT + DBConstant.ID +", "+ DBConstant.NAME +", "+DBConstant.ROLE_TABLE +"." + DBConstant.ROLE_NAME 
						+ " "+ DBConstant.FROM + DBConstant.EMPLOYEE_TABLE +" "+ DBConstant.INNER_JOIN 
						+" "+ DBConstant.ROLE_TABLE +" "+ DBConstant.ON +" "+ DBConstant.ROLE_TABLE +"."+DBConstant.ROLE_ID +" = " 
						+ DBConstant.EMPLOYEE_TABLE +"." + DBConstant.ROLE_ID
						+ DBConstant.WHERE + DBConstant.TEAM_ID + " = " + userTeamID 
						+ DBConstant.AND +" "+ DBConstant.ROLE_TABLE +"."+ DBConstant.ROLE_PRIORITY +" < " + rolePriority;

		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			result = statement.executeQuery();
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println(" Error occured in getting Employee Name !");
		}
		
		return result;
		
	}

	public static HashMap<Integer, String> getWorkLocation()
	{
		
		HashMap<Integer, String> workLocations = new HashMap<>();
		String query = DBConstant.SELECT + " * "+ DBConstant.FROM 
						+ DBConstant.WORK_LOCATION_TABLE;

		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				int locationId = result.getInt(DBConstant.LOCATION_ID);
				String locationName = result.getString(DBConstant.LOCATION_NAME);
				workLocations.put(locationId, locationName);
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println(" Error occured in getting Employee Name !");
		}

		return workLocations;
		
	}
	
	public static boolean isWorkLocationPresent(int locationID)
	{
		
		String query = DBConstant.SELECT + DBConstant.LOCATION_ID +" "+ DBConstant.FROM 
				+ DBConstant.WORK_LOCATION_TABLE +" "+ DBConstant.WHERE + DBConstant.LOCATION_ID + " = " + locationID;

		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				
				int id = result.getInt(DBConstant.LOCATION_ID);
				
				if(id == locationID)
				{
					return true;
				}
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println(" Error occured in validating Work Location ID !");
		}

		return false;
		
	}

	public static boolean isOfficialMailExist(String mail)
	{
		
		String query = DBConstant.SELECT + DBConstant.MAIL_ID +" " + DBConstant.FROM 
						+ DBConstant.EMPLOYEE_TABLE +" "+ DBConstant.WHERE 
						+ DBConstant.MAIL_ID + " = " + mail;

		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				String mailID = result.getString(DBConstant.MAIL_ID);
				
				if(mail.equalsIgnoreCase(mailID))
				{
					return true;
				}
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println(" Error occured in Mail Validation !");
		}

		return false;
	}
	

}

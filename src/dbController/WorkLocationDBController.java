package dbController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.WorkLocation;

public class WorkLocationDBController
{
	
	static PreparedStatement statement;


	public static boolean addWorkLocation(WorkLocation location)
	{
		
		String query = DBConstant.INSERT_INTO + DBConstant.WORK_LOCATION_TABLE + " (" 
						+ DBConstant.LOCATION_NAME + " )" + " values ('" + location.getLocationName() + "')";
		
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			return (statement.executeUpdate() == 1);
		} 
		
		catch (SQLException e) 
		{
			return false;
		}
	}
	
	public static ArrayList<WorkLocation> getWorkLocation()
	{
		
		ArrayList<WorkLocation> locations = new ArrayList<>();
		WorkLocation location;
		
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
				location = new WorkLocation(locationId, locationName);
				locations.add(location);
			}
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting Employee Name !");
		}

		return locations;
	}
	
	public static ArrayList<WorkLocation> getLocationExceptPreviousLocation(int locationID)
	{
		
		ArrayList<WorkLocation> locations = new ArrayList<>();
		WorkLocation location;
		String query = DBConstant.SELECT + " * "+ DBConstant.FROM 
						+ DBConstant.WORK_LOCATION_TABLE + DBConstant.WHERE + DBConstant.LOCATION_ID + "!=" + locationID;

		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				int locationId = result.getInt(DBConstant.LOCATION_ID);
				String locationName = result.getString(DBConstant.LOCATION_NAME);
				location = new WorkLocation(locationId, locationName);
				locations.add(location);
			}
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting Employee Name !");
		}

		return locations;
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
				if(result.getInt(DBConstant.LOCATION_ID) == locationID)
				{
					return true;
				}
			}
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in validating Work Location ID !");
		}

		return false;
	}
	
	public static String getLocationName(int locationID)
	{
		
		String query = DBConstant.SELECT + DBConstant.LOCATION_NAME +" "+ DBConstant.FROM 
						+ DBConstant.WORK_LOCATION_TABLE +" "+ DBConstant.WHERE + DBConstant.LOCATION_ID + " = " + locationID;
		
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			return result.getString(DBConstant.LOCATION_NAME);
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting Location Name !");
		}
		return null;
	}

	
	public static boolean setLocationID(int locationID, int employeeID)
	{
		
		String query = DBConstant.UPDATE + DBConstant.EMPLOYEE_TABLE +" "+ DBConstant.SET 
						+ DBConstant.WORK_LOCATION +" = "+locationID + DBConstant.WHERE + DBConstant.ID + " = "+employeeID;
		
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);			
			return (statement.executeUpdate() != 0);
		} 
		
		catch (SQLException e) 
		{
			System.out.println(" Error occured in setting location!");
		}
		return false;
	}

}

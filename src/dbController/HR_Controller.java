package dbController;

import java.sql.*;
import java.util.*;

public class HR_Controller 
{
	
	private static final String INSERT_INTO = "Insert into ";
	private static final String SELECT = "Select ";
	private static final String FROM = "From ";
	private static final String TEAM_TABLE = " Teams";
	private static final String ROLE_TABLE = " Role";
	private static final String WORK_LOCATION_TABLE = " Work_Location";
	private static final String COLUMN_TEAM_NAME = " Team_Name";
	private static final String COLUMN_ROLE_NAME = " Role_Name";
	private static final String COLUMN_LOCATION = " Locations";

	PreparedStatement statement;
	Connection connection;

	public boolean addTeam(String teamName)
	{
		
		String query = INSERT_INTO + TEAM_TABLE + "(" + COLUMN_TEAM_NAME + ")" + " values (?)";
		
		try 
		{
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, teamName);
			statement.executeUpdate();
			statement.close();
			return true;
		} 
		
		catch (SQLException e) 
		{
			return false;
		}
		
	}
	
	public boolean addRole(String roleName)
	{
		
		String query = INSERT_INTO + ROLE_TABLE + "(" + COLUMN_ROLE_NAME + ")" + " values (?)";
		
		try 
		{
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, roleName);
			statement.executeUpdate();
			statement.close();
			return true;
		} 
		
		catch (SQLException e) 
		{
			return false;
		}
		
	}
	
	public boolean addWorkLocation(String locationName)
	{
		
		String query = INSERT_INTO + WORK_LOCATION_TABLE + "(" + COLUMN_LOCATION + ")" + " values (?)";
		
		try 
		{
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, locationName);
			statement.executeUpdate();
			statement.close();
			return true;
		} 
		
		catch (SQLException e) 
		{
			return false;
		}
		
	}
	
	public HashMap<Integer, String> getTeamsName()
	{
		
		String query = SELECT + " * " + FROM + TEAM_TABLE;
		
		HashMap<Integer, String> teams = new HashMap<>();
		
		try 
		{
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				int teamID = resultSet.getInt(1);
				String teamName = resultSet.getString(2);
				teams.put(teamID, teamName);
			}
			return teams;
		}
		
		catch (SQLException e)
		{
			System.out.println("  Error occured in getting Team Name!\n");
		}
		return null;
		
	}
	
	public HashMap<Integer, String> getRoleNames()
	{
		
		HashMap<Integer, String> roles = new HashMap<>();
		
		String query = SELECT + " * " + FROM + ROLE_TABLE;
		
		try 
		{
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				int roleID = resultSet.getInt(1);
				String roleName = resultSet.getString(2);
				roles.put(roleID, roleName);
			}
			return roles;
		}
		
		catch (SQLException e)
		{
			System.out.println("  Error occured in getting Role Name!\n");
		}
		return null;
		
	}
	
	

}

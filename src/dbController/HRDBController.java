package dbController;

import java.sql.*;

public class HRDBController 
{

	static PreparedStatement statement;

	

	public static boolean addWorkLocation(String locationName)
	{
		
		String query = DBConstant.INSERT_INTO + DBConstant.WORK_LOCATION_TABLE + " (" 
						+ DBConstant.LOCATION_NAME + " )" + " values ('" + locationName + "')";
		
		try 
		{

			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
			statement.close();
			return true;
		} 
		
		catch (SQLException e) 
		{
			return false;
		}
		
	}
//	
//	public HashMap<Integer, String> getTeamsName()
//	{
//		
//		String query = DBConstants.SELECT + " * " + DBConstants.FROM + DBConstants.TEAMS_TABLE;
//		
//		HashMap<Integer, String> teams = new HashMap<>();
//		
//		try 
//		{
//			connection = DBConnector.getConnection();
//			statement = connection.prepareStatement(query);
//			ResultSet resultSet = statement.executeQuery();
//			
//			while(resultSet.next())
//			{
//				int teamID = resultSet.getInt(1);
//				String teamName = resultSet.getString(2);
//				teams.put(teamID, teamName);
//			}
//			return teams;
//		}
//		
//		catch (SQLException e)
//		{
//			System.out.println("  Error occured in getting Team Name!\n");
//		}
//		return null;
//		
//	}
//	
//	public HashMap<Integer, String> getRoleNames()
//	{
//		
//		HashMap<Integer, String> roles = new HashMap<>();
//		
//		String query = DBConstants.SELECT + " * " + DBConstants.FROM + DBConstants.ROLE_NAME;
//		
//		try 
//		{
//			connection = DBConnector.getConnection();
//			statement = connection.prepareStatement(query);
//			ResultSet resultSet = statement.executeQuery();
//			
//			while(resultSet.next())
//			{
//				int roleID = resultSet.getInt(1);
//				String roleName = resultSet.getString(2);
//				roles.put(roleID, roleName);
//			}
//			return roles;
//		}
//		
//		catch (SQLException e)
//		{
//			System.out.println("  Error occured in getting Role Name!\n");
//		}
//		return null;
//		
//	}
//	
	

}

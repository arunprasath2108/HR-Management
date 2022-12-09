package dbController;

import java.sql.*;

public class EmployeeValidation
{

	private static final String SELECT = "Select ";
	private static final String FROM = "from ";
	private static final String WHERE = "Where ";
	private static final String EMPLOYEE_TABLE = "Employee ";
	private static final String TEAMS_TABLE = "Teams ";
	private static final String ROLE_TABLE = "Role ";

	
	static Connection connection;
	static PreparedStatement statement;
	
	
	public static boolean isEmployeePresent(int userID)
	{
		
		String query = SELECT + " ID " + FROM + EMPLOYEE_TABLE + WHERE + " ID = ?";
		
		try 
		{
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, userID);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				int employeeID = result.getInt("ID");
				
				if(employeeID == userID)
				{
					return true;
				}
			}
			
		} 
		
		catch (SQLException e) 
		{
			System.out.println(" Error occured in isEmployeepresent method  !");
		}
		
		return false;
	}
	
	public static int isTeamsAvailable()
	{
		
		int teamsCount = 0;
		String query = SELECT + " count(*) " + FROM + "(" + SELECT + " 1 " + FROM + TEAMS_TABLE + " limit 1 ) as TeamCount";
		
		try 
		{
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			teamsCount = result.getInt(1);
		} 
		
		catch (SQLException e) 
		{
			System.out.println(" Error occured in isTeamsAvailable method  !");
		}
		
		return teamsCount;
	}
	
	public static boolean isTeamIDPresent(int userInput)
	{
		
		String query = SELECT + " team_id " + FROM + TEAMS_TABLE;
		
		try 
		{
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				int id = result.getInt(1);
				
				if(id == userInput)
				{
					return true;
				}
			}
		} 
		
		catch (SQLException e) 
		{
			System.out.println(" Error occured in isTeamsPresent method  !\n");
		}
		return false;
		
	}

	public static boolean isRoleIdPresent(int roleID)
	{
		
		String query = SELECT + " role_id " + FROM + ROLE_TABLE;
		
		try 
		{
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				int id = result.getInt(1);
				
				if(id == roleID)
				{
					return true;
				}
			}
		} 
		
		catch (SQLException e) 
		{
			System.out.println(" Error occured in isTeamsPresent method  !\n");
		}
		
		return false;
	}
	
	
}

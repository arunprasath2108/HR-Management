package dbController;

import java.sql.*;
import java.util.ArrayList;
import model.Team;


public class TeamDBController
{
	
	static PreparedStatement statement;
	
	

	public static boolean isTeamPresent(int userInput)
	{
		
		String query = DBConstant.SELECT + DBConstant.TEAM_ID +" "+ DBConstant.FROM + DBConstant.TEAMS_TABLE;
		
		try 
		{
		
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				int id = result.getInt(DBConstant.TEAM_ID);
				
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
	
	public static int isTeamsAvailable()
	{
		
		int teamsCount = 0;
		String query = DBConstant.SELECT + "count(*) " + DBConstant.FROM + "(" 
						+ DBConstant.SELECT + 1 + DBConstant.FROM + DBConstant.TEAMS_TABLE + " limit 1 ) as TeamCount";
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			
			teamsCount = result.getInt(1); 	//if minimum one team is present in teams table, it returns value 1
			 								//1 - column Index (count) 
			
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println(" Error occured in isTeamsAvailable method  !");
		}
		
		return teamsCount;
	}
	
	public static int getLastAddedTeamID()
	{
		int teamID = 0;
		String query = DBConstant.SELECT + DBConstant.TEAM_ID +" "
						+DBConstant.FROM + DBConstant.TEAMS_TABLE +" "
						+ DBConstant.ORDER_BY + DBConstant.TEAM_ID +" "+DBConstant.DESC + DBConstant.LIMIT + 1;
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			teamID = result.getInt(DBConstant.TEAM_ID);
		} 
		
		catch (SQLException e) 
		{
			System.out.println(" Error occured in isTeamsAvailable method  !");
		}
		
		return teamID;
		
	}
	
	public static boolean addTeam(Team team)
	{
		
		String query = DBConstant.INSERT_INTO + DBConstant.TEAMS_TABLE +" ("
						+DBConstant.TEAM_NAME + ") values ('"
						+team.getTeamName() + "')";
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
			return true;
		} 
		catch (SQLException e) 
		{
			return false;
		}
		
	}
	
	public static ArrayList<Team> listTeam()
	{
		
		ArrayList<Team> teams = new ArrayList<>();
		Team team = null;
		String query = DBConstant.SELECT + " * "+ DBConstant.FROM + DBConstant.TEAMS_TABLE;
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				int teamID = result.getInt(DBConstant.TEAM_ID);
				String teamName = result.getString(DBConstant.TEAM_NAME);
				team = new Team(teamID, teamName);
				teams.add(team);
			
			}
			return teams;
		} 
		catch (SQLException e) 
		{
			return null;
		}
		
	}
	
	public static int isEmployeeInTeam(int teamID, int userInput)
	{
		int id = 0;
		String query = DBConstant.SELECT + DBConstant.ID +" "
						+ DBConstant.FROM + DBConstant.EMPLOYEE_TABLE  + " "
						+ DBConstant.WHERE + DBConstant.TEAM_ID + " = " + teamID + DBConstant.AND +" " + DBConstant.ID +" = " + userInput ;
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				id = result.getInt(DBConstant.ID);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("  Error in isEmployeeInTeam method ");
			
		}
		return id;
		
	}
	
	
//	public static String getTeamName(int teamID)
//	{
//		
//		String query = DBConstant.SELECT + DBConstant.TEAM_NAME +" "+ DBConstant.FROM 
//						+ DBConstant.TEAMS_TABLE +" "+ DBConstant.WHERE + DBConstant.TEAM_ID + " = " + teamID;
//		
//		try 
//		{
//			
//			connection = DBConnector.getConnection();
//			statement = connection.prepareStatement(query);
//			ResultSet result = statement.executeQuery();
//			result.next();
//			String teamName = result.getString(DBConstant.TEAM_NAME);
//			return teamName;
//			
//		} 
//		
//		catch (SQLException e) 
//		{
//			System.out.println(" Error occured in getting Team Name !");
//		}
//		
//		return null;
//	}
	
	
	
	
}
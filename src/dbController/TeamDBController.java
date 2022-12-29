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
				if(result.getInt(DBConstant.TEAM_ID) == userInput)
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

			return result.getInt(1); 	//if minimum one team is present in teams table, it returns value 1 
		} 								//1 - column Index (count)
		catch (SQLException e) 
		{
			System.out.println(" Error occured in isTeamsAvailable method  !");
		}
		return teamsCount;
	}
	
	public static boolean addTeam(Team team)
	{
		
		String query = DBConstant.INSERT_INTO + DBConstant.TEAMS_TABLE +" ("
						+DBConstant.TEAM_NAME + ") values ('"
						+team.getTeamName() + "')";
		
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
	
	public static ArrayList<Team> listTeam()
	{
		
		ArrayList<Team> teams = new ArrayList<>();
		Team team;
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
	
	public static ArrayList<Team> listTeamExceptCurrentTeam(int teamId)
	{
		
		ArrayList<Team> teams = new ArrayList<>();
		Team team;
		String query = DBConstant.SELECT + " * "+ DBConstant.FROM + DBConstant.TEAMS_TABLE 
						+ DBConstant.WHERE + DBConstant.TEAM_ID +" != "+ teamId;
		
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

	
	public static String getTeamName(int teamID)
	{
		
		String query = DBConstant.SELECT + DBConstant.TEAM_NAME +" "+ DBConstant.FROM 
						+ DBConstant.TEAMS_TABLE +" "+ DBConstant.WHERE + DBConstant.TEAM_ID + " = " + teamID;
		
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				return result.getString(DBConstant.TEAM_NAME);
			}
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting Team Name !");
		}
		return null;
	}	
	

	
}

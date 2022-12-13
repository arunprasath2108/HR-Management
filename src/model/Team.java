package model;

public class Team 
{
	
	private int teamID;
	private String teamName;
	
	
	//constructor
	public Team(String teamName)
	{
		this.teamName = teamName;
	}
	
	public Team(int teamID, String teamName)
	{
		this.teamID = teamID;
		this.teamName = teamName;
	}
	
	

	public int getTeamID()
	{
		return teamID;
	}
	
	public String getTeamName()
	{
		return teamName;
	}

//	public void setTeamID(int teamId)
//	{
//		this.teamID = teamId;
//	}
	
	
//	public void setTeamName(String teamName)
//	{
//		this.teamName = teamName;
//	}
	
}

package model;

import java.util.Date;

public class Request 
{
	private int requestID;
	private int requestBy;
	private int receiverID;
	private int teamID;
	private String requestedOn;
	private String status;
	
	
	//constructor without requestID
	public Request(int requestBy, int receiverID, int teamID, String status)
	{
		this.requestBy = requestBy;
		this.receiverID = receiverID;
		this.requestedOn = requestedOn;
		this.teamID = teamID;
		this.status = status;
	}
	
	
	//constructor with all parameters
	public Request(int requestID, int requestBy, int receiverID, String requestedOn, int teamID, String status)
	{
		this.requestID = requestID;
		this.requestBy = requestBy;
		this.receiverID = receiverID;
		this.requestedOn = requestedOn;
		this.teamID = teamID;
		this.status = status;
	}
	
	
	
	
	
	//getters
	
	public int getRequestID()
	{
		return requestID;
	}
	
	public int getReceiverID()
	{
		return receiverID;
	}
	
	public int getRequestBy()
	{
		return requestBy;
	}
	
	public int getTeamID()
	{
		return teamID;
	}
	
	public String getRequestOn()
	{
		return requestedOn;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	
	//setters
	
	public void setRequestID(int requestID)
	{
		this.requestID = requestID;
	}
	public void setReceiverID(int receiver)
	{
		receiverID = receiver;
	}

	public void setRequestBy(int sender)
	{
		requestBy = sender;
	}

	public void setTeamID(int teamID)
	{
		this.teamID = teamID;
	}
	
	public void setStatus(String message)
	{
		status = message;
	}
	
	public void setRequestOn(String date)
	{
		requestedOn = date;
	}
	
	
	
	

	

}

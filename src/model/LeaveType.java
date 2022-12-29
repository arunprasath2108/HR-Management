package model;

public class LeaveType 
{
	
	private int leaveID;
	private String leaveName;
	private int leaveCount;
	private String genderForLeaveType;
	
	
	public LeaveType(String leaveName, int leaveCount, String gender)
	{
		this.leaveName = leaveName;
		this.leaveCount = leaveCount;
		this.genderForLeaveType = gender;
	}
	
	public LeaveType(int leaveID, String leaveName, int leaveCount, String gender)
	{
		this.leaveID = leaveID;
		this.leaveName = leaveName;
		this.leaveCount = leaveCount;
		this.genderForLeaveType = gender;
	}
	
	
	
	
	//getters
	
	public int getLeaveID()
	{
		return leaveID;
	}
	
	public String getLeaveName()
	{
		return leaveName;
	}
	
	public int getLeaveCount()
	{
		return leaveCount;
	}
	
	public String getGender()
	{
		return genderForLeaveType;
	}

	public void setLeaveTypeID(int leaveID)
	{
		this.leaveID = leaveID;
	}
	
	

}

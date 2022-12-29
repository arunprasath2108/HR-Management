package model;

public class LeaveBalance 
{
	
	private int takenLeaveID;
	private int employeeID;
	private int unusedLeave;
	private int totalLeave;
	private int leaveTypeID;
	
	
	public LeaveBalance(int employeeID, int totalLeave, int unusedLeave, int leaveTypeID)
	{
		this.employeeID = employeeID;
		this.totalLeave = totalLeave;
		this.unusedLeave = unusedLeave;
		this.leaveTypeID = leaveTypeID;
	}
	
	public LeaveBalance(int takenLeaveID, int employeeID, int totalLeave, int unusedLeave, int leaveTypeID)
	{
		this.takenLeaveID = takenLeaveID;
		this.employeeID = employeeID;
		this.totalLeave = totalLeave;
		this.unusedLeave = unusedLeave;
		this.leaveTypeID = leaveTypeID;
	}
	
	
	//getters
	
	public int getEmployeeID()
	{
		return employeeID;
	}
	
	public int getUnusedLeave()
	{
		return unusedLeave;
	}
	
	public int getTotalLeave()
	{
		return totalLeave;
	}
	
	public int getLeaveTypeID()
	{
		return leaveTypeID;
	}
	


}

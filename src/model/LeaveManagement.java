package model;

import java.util.Date;

public class LeaveManagement 
{

	private int leaveID;
	private int requestBy;
	private int reportingID;
	private int leaveTypeID;
	private Date fromDate;
	private Date toDate;
	private String reasonForLeave;
	private String status;
	private String rejectedReason;
	
	public LeaveManagement(int requestBy, int reportingID, int leaveTypeID, Date fromDate, Date toDate, String reasonForLeave, String status)
	{
		this.requestBy = requestBy;
		this.reportingID = reportingID;
		this.leaveTypeID = leaveTypeID;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.reasonForLeave = reasonForLeave;
		this.status = status;
	}
	
	public LeaveManagement(int leaveID, int requestBy, int reportingID, int leaveTypeID, Date fromDate, Date toDate, String reasonForLeave, String status, String rejectedReason)
	{
		this.leaveID = leaveID;
		this.requestBy = requestBy;
		this.reportingID = reportingID;
		this.leaveTypeID = leaveTypeID;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.reasonForLeave = reasonForLeave;
		this.status = status;
		this.rejectedReason = rejectedReason;
	}
	
	

	public int getleaveID()
	{
		return leaveID;
	}
	
	public int getRequestBy()
	{
		return requestBy;
	}

	public int getReportingID()
	{
		return reportingID;
	}
	
	public int getLeaveTypeID()
	{
		return leaveTypeID;
	}

	public Date getfromDate()
	{
		return fromDate;
	}
	
	public Date getToDate()
	{
		return toDate;
	}
	
	public String getReasonForLeave()
	{
		return reasonForLeave;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public String getRejectedReason()
	{
		return rejectedReason;
	}


	public void setStatus(String status)
	{
		this.status = status;
	}



}

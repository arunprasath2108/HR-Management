package model;

import java.util.Date;

public class Notification 
{
	
	private int notificationID;
	private int employeeID;
	private String notification;
	private boolean notificationSeen;
	private String notificationTime;
	
	
	public Notification(int notificationID, int employeeID, String notification, boolean notificationSeen, String notificationTime)
	{
		this.notificationID = notificationID;
		this.employeeID = employeeID;
		this.notification = notification;
		this.notificationSeen = notificationSeen;
		this.notificationTime = notificationTime;
	}
	
	//getters
	
	public int getnotificationID()
	{
		return notificationID;
	}
	
	public int getemployeeID()
	{
		return employeeID;
	}
	
	public String getNotification()
	{
		return notification;
	}
	
	public boolean getNotificationSeen()
	{
		return notificationSeen;
	}
	
	public String getNotificationTime()
	{
		return notificationTime;
	}

}

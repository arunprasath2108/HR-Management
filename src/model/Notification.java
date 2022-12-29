package model;


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
	
	public String getNotification()
	{
		return notification;
	}

	public String getNotificationTime()
	{
		return notificationTime;
	}

}

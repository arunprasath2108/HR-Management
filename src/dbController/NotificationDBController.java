package dbController;

import java.sql.*;
import java.util.*;
import model.Notification;
import utils.Utils;

public class NotificationDBController
{
	
	static PreparedStatement statement;

	
	public static boolean setNotification(int employeeID, String message, boolean isMessageSeen)
	{
		
		String query = DBConstant.INSERT_INTO + " " + DBConstant.NOTIFICATION_TABLE + " ( " 
						+ DBConstant.EMPLOYEE_ID +", " + DBConstant.NOTIFICATION +", "
						+ DBConstant.NOTIFICATION_SEEN +","+ DBConstant.NOTIFICATION_TIME + " ) values (" + employeeID +", '"
						+ message + "' ," + isMessageSeen +", '" + Utils.getCurrentDateTime() + "' )";

		try
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			return (statement.executeUpdate() != 0);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("  Can't set Notification !");
		}
		return false;
		
	}
	
	
	//to change notification status
	public static int getNotificationID(int requestID, int requestedBy)
	{
		
		String query = DBConstant.SELECT + " * " + DBConstant.FROM + DBConstant.NOTIFICATION_TABLE + " "
						+ DBConstant.WHERE + DBConstant.EMPLOYEE_ID + " = " + requestedBy;
		try
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				String requestId = Integer.toString(requestID);
				
				if(result.getString(DBConstant.NOTIFICATION).contains(requestId))
				{
					return result.getInt(DBConstant.NOTIFICATION_ID);
				}
			}
		}
		catch(SQLException e)
		{
			System.out.println("  Can't get Notification ID!");
		}
		return 0;
		
	}
	
	
	public static boolean changeNotification(int notificationID, String notification, boolean setMessageSeen)
	{
		
		String query = DBConstant.UPDATE + DBConstant.NOTIFICATION_TABLE + " " + DBConstant.SET
						+ DBConstant.NOTIFICATION + " = '" + notification + "' , " + DBConstant.NOTIFICATION_TIME
						+ " = '" + Utils.getCurrentDateTime() + "' , " + DBConstant.NOTIFICATION_SEEN + " = '"
						+ setMessageSeen + "' " + DBConstant.WHERE + DBConstant.NOTIFICATION_ID + " = " + notificationID;
						
		try
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			return (statement.executeUpdate() != 0);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("  Can't change Notification !!");
		}
		return false;
		
	}
	
	
	public static int getNotificationCount(int employeeID)
	{
		
		int count = 0;
		String query = DBConstant.SELECT + DBConstant.EMPLOYEE_ID + " " + DBConstant.FROM + DBConstant.NOTIFICATION_TABLE + " "
						+ DBConstant.WHERE + DBConstant.EMPLOYEE_ID + " = " + employeeID + " " + DBConstant.AND
						+ DBConstant.NOTIFICATION_SEEN + " = ' false ' ";
		
		try
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				if(result.getInt(DBConstant.EMPLOYEE_ID) == employeeID)
				{
					count++;
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("  Can't get Notification count!");
		}
		
		return count;
	}
	
	
	public static ArrayList<Notification>  getNotification(int employeeID)
	{
		
		ArrayList<Notification> notifications = new ArrayList<>();
		
		String query = DBConstant.SELECT + " * " + DBConstant.FROM + DBConstant.NOTIFICATION_TABLE + " "
						+ DBConstant.WHERE + DBConstant.EMPLOYEE_ID + " = " + employeeID + " " + DBConstant.ORDER_BY + DBConstant.NOTIFICATION_ID + " " + DBConstant.DESC;
		
		try
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				
				Notification notification = new Notification(result.getInt(DBConstant.NOTIFICATION_ID),
									result.getInt(DBConstant.EMPLOYEE_ID),
									result.getString(DBConstant.NOTIFICATION),
									result.getBoolean(DBConstant.NOTIFICATION_SEEN),
									result.getString(DBConstant.NOTIFICATION_TIME));
				
				notifications.add(notification);
			}
			return notifications;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("  Can't get Notification count!");
		}
		
		return null;
	}
	
	public static boolean changeNotificationSeen(int employeeID)
	{
		
		String query = DBConstant.UPDATE + DBConstant.NOTIFICATION_TABLE + " " + DBConstant.SET
						+ DBConstant.NOTIFICATION_SEEN + " = " + DBConstant.TRUE + DBConstant.WHERE
						+ DBConstant.EMPLOYEE_ID + " = " + employeeID;
		
		try
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			return (statement.executeUpdate() != 0);
			
		}
		catch(SQLException e)
		{
			System.out.println("  Can't change Notification seen!");
		}

		return false;
	}

}

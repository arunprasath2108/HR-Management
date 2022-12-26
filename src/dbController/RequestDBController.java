package dbController;

import java.sql.*;
import java.util.ArrayList;

import model.Request;
import utils.Utils;

public class RequestDBController
{
	static PreparedStatement statement;
	
	
	public static int setRequestAndReturnRequestID(Request request)
	{
		
		String query = DBConstant.INSERT_INTO + " " + DBConstant.TEAM_CHANGE_REQUEST_TABLE + " ( " 
						+ DBConstant.REQUEST_BY +", "+ DBConstant.RECEIVER_ID +", " 
						+ DBConstant.REQUESTED_ON + ", "+ DBConstant.STATUS +", " + DBConstant.TEAM_ID + ")  values ("
						+ request.getRequestBy() + "," + request.getReceiverID() + ", '" + Utils.getCurrentDateTime() + "', '" + request.getStatus() + "', '" 
						+ request.getTeamID() + "') returning " + DBConstant.REQUEST_ID;

		try
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			return result.getInt(DBConstant.REQUEST_ID);
			
		}
		catch(SQLException e)
		{
			System.out.println("  Can't send Message !");
		}
		return 0;
		
	}
		
	public static ArrayList<Request> getAllRequests(int employeeID)
	{
		
		ArrayList<Request> requests = new ArrayList<>();

		String query = DBConstant.SELECT +" * " + DBConstant.FROM + " " + DBConstant.TEAM_CHANGE_REQUEST_TABLE
						+ DBConstant.WHERE + "  " + DBConstant.RECEIVER_ID + " = " + employeeID;
		
		try
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				
				Request request  = new Request (result.getInt(DBConstant.REQUEST_ID),
												result.getInt(DBConstant.REQUEST_BY),
												result.getInt(DBConstant.RECEIVER_ID),
												result.getString(DBConstant.REQUESTED_ON),
												result.getInt(DBConstant.TEAM_ID),
												result.getString(DBConstant.STATUS));
				
					requests.add(request);
			}
			
			return requests;
		}
		catch(SQLException e)
		{
			System.out.println("  Can't get Request messages !");
		}
		return null;
		
	}

	public static boolean isRequestPresent(int requestID, int employeeID)
	{
		
		String query = DBConstant.SELECT + " " + DBConstant.REQUEST_ID + " " + DBConstant.FROM + " "
						+ DBConstant.TEAM_CHANGE_REQUEST_TABLE + " " + DBConstant.WHERE 
						+ DBConstant.REQUEST_ID + " = " + requestID + " " + DBConstant.AND
						+ DBConstant.RECEIVER_ID + " = " + employeeID;
		
		try
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			
			return (result.getInt(DBConstant.REQUEST_ID)== requestID);
		}
		
		catch(SQLException e)
		{
			return false;
		}
	}
	
	//reply as HR
	public static boolean isRequestWaitingForHRApproval(int requestID, String status)
	{
		
		String query = DBConstant.SELECT + " " + DBConstant.REQUEST_ID + ", "
				+ DBConstant.STATUS + DBConstant.FROM + " "
						+ DBConstant.TEAM_CHANGE_REQUEST_TABLE + " " + DBConstant.WHERE 
						+ DBConstant.REQUEST_ID + " = " + requestID;
		
		try
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			
			return (result.getInt(DBConstant.REQUEST_ID) == requestID && 
						result.getString(DBConstant.STATUS).contains(status));  //waiting for HR Approval
		}
		
		catch(SQLException e)
		{
			return false;
		}
	}
	
	public static boolean acceptRequest(int requestID, String message)
	{
		
		String query = DBConstant.UPDATE + " " + DBConstant.TEAM_CHANGE_REQUEST_TABLE + " "
						+ DBConstant.SET + " " + DBConstant.STATUS + " = '" + message + "' "
						+ DBConstant.WHERE + " " + DBConstant.REQUEST_ID + " = " + requestID;
		
		try
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			return (statement.executeUpdate() != 0);
		}
		catch(SQLException e)
		{
			System.out.println(" Can't change Request status");
		}
		return false;
	}
	
	public static boolean rejectRequest(int requestID)
	{
		
		String query = DBConstant.DELETE + DBConstant.FROM + DBConstant.TEAM_CHANGE_REQUEST_TABLE + " "
						+ DBConstant.WHERE + DBConstant.REQUEST_ID + " = " + requestID;
		
		try
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			return (statement.executeUpdate() != 0);
		}
		catch(SQLException e)
		{
			System.out.println(" Can't reject request");
		}
		return false;
		
	}
	
	public static Request getSpecificRequest(int requestID, int employeeID)
	{

		String query = DBConstant.SELECT +" * " + DBConstant.FROM + " " + DBConstant.TEAM_CHANGE_REQUEST_TABLE
						+ DBConstant.WHERE + "  " + " " + DBConstant.REQUEST_ID + " = " + requestID + " "
						+ DBConstant.AND + " " + DBConstant.RECEIVER_ID + " = " + employeeID;
		
		try
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{

				return new Request(result.getInt(DBConstant.REQUEST_ID),
									  result.getInt(DBConstant.REQUEST_BY),
									  result.getInt(DBConstant.RECEIVER_ID),
									  result.getString(DBConstant.REQUESTED_ON),
									  result.getInt(DBConstant.TEAM_ID),
									  result.getString(DBConstant.STATUS));
			}
		}
		catch(SQLException e)
		{
			
			System.out.println("  Can't get Request message !");
		}
		return null;
		
	}


	public static ArrayList<Request> getRequestsForHR()
	{
		
		ArrayList<Request> requests = new ArrayList<>();

		String query = DBConstant.SELECT +" * " + DBConstant.FROM + " " + DBConstant.TEAM_CHANGE_REQUEST_TABLE;
		
		try
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				
				if(result.getString(DBConstant.STATUS).contains("HR"))
				{

					Request request  = new Request (result.getInt(DBConstant.REQUEST_ID),
												result.getInt(DBConstant.REQUEST_BY),
												result.getInt(DBConstant.RECEIVER_ID),
												result.getString(DBConstant.REQUESTED_ON),
												result.getInt(DBConstant.TEAM_ID),
												result.getString(DBConstant.STATUS));
				
					requests.add(request);
				}
			}
			
			return requests;
		}
		catch(SQLException e)
		{
			System.out.println("  Can't get Request messages !");
		}
		return null;
		
	}


	public static Request getSpecificRequestForHR(int requestID)
	{

		String query = DBConstant.SELECT +" * " + DBConstant.FROM + " " + DBConstant.TEAM_CHANGE_REQUEST_TABLE
						+ DBConstant.WHERE + "  " + " " + DBConstant.REQUEST_ID + " = " + requestID;
		
		try
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{

				return new Request(result.getInt(DBConstant.REQUEST_ID),
									  result.getInt(DBConstant.REQUEST_BY),
									  result.getInt(DBConstant.RECEIVER_ID),
									  result.getString(DBConstant.REQUESTED_ON),
									  result.getInt(DBConstant.TEAM_ID),
									  result.getString(DBConstant.STATUS));
			}
		}
		catch(SQLException e)
		{
			
			System.out.println("  Can't get Request message !");
		}
		return null;
		
	}


	public static boolean deleteRequest(int requestID)
	{
		
		String query = DBConstant.DELETE + DBConstant.FROM 
						+ DBConstant.TEAM_CHANGE_REQUEST_TABLE + " " + DBConstant.WHERE
						+ DBConstant.REQUEST_ID + " = " + requestID;
		
		try
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			return (statement.executeUpdate() != 0);
		}
		catch(SQLException e)
		{
			System.out.println(" Can't delete Request");
		}
		return false;
	}

	public static boolean isAlreadyRequestSent(int employeeID)
	{
		
		String query = DBConstant.SELECT + " " + DBConstant.REQUEST_BY + " " + DBConstant.FROM 
						+ DBConstant.TEAM_CHANGE_REQUEST_TABLE + " " + DBConstant.WHERE + DBConstant.REQUEST_BY
						+ " = " + employeeID;
		
		try
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			
			return (result.getInt(DBConstant.REQUEST_BY)== employeeID);
		}
		
		catch(SQLException e)
		{
			return false;
		}
	}


}

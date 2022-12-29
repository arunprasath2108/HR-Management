package dbController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.LeaveType;

public class LeaveTypeDBController
{
	
	static PreparedStatement statement;
	
	
	public static int addLeave(LeaveType leave)
	{
		
		String query = DBConstant.INSERT_INTO + DBConstant.LEAVE_TYPE_TABLE + " (" + DBConstant.LEAVE_NAME + ", "
						+ DBConstant.LEAVE_COUNT +"," + DBConstant.GENDER + ") values ('" + leave.getLeaveName() + "', " + leave.getLeaveCount() + ",'" 
						+ leave.getGender() + "') returning " + DBConstant.LEAVE_TYPE_ID;
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			return result.getInt(DBConstant.LEAVE_TYPE_ID);
		} 
		catch (SQLException e) 
		{
			System.out.println(" Can't add new Leave type !.....");
		}
		return 0;
	}
	
	//get leave types except the opposite gender
	public static ArrayList<LeaveType> getLeaveTypes(String gender)
	{
		ArrayList<LeaveType> leaveTypes = new ArrayList<>();
		LeaveType leave;
		String query = DBConstant.SELECT + " * " + DBConstant.FROM + DBConstant.LEAVE_TYPE_TABLE + " " 
						+ DBConstant.WHERE + DBConstant.GENDER + " != '" + gender + "'";
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				
				leave = new LeaveType(result.getInt(DBConstant.LEAVE_TYPE_ID),
										result.getString(DBConstant.LEAVE_NAME),
										result.getInt(DBConstant.LEAVE_COUNT),
										result.getString(DBConstant.GENDER));
				
				leaveTypes.add(leave);
			}
			return leaveTypes;
		} 
		catch (SQLException e) 
		{
			System.out.println(" Can't add new Leave type !.....");
		}
		return null;
	}
	
	
	public static String getLeaveName(int leaveID)
	{
		
		String query = DBConstant.SELECT + DBConstant.LEAVE_NAME +" " + DBConstant.FROM + DBConstant.LEAVE_TYPE_TABLE + " "
						+ DBConstant.WHERE + DBConstant.LEAVE_TYPE_ID + " = " + leaveID;
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			
			return result.getString(DBConstant.LEAVE_NAME);
		} 
		catch (SQLException e) 
		{
			System.out.println(" Can't get Leave Name !.....");
		}
		return null;
	}

}

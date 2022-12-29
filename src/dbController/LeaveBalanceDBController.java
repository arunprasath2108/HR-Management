package dbController;

import java.sql.*;
import model.LeaveBalance;

public class LeaveBalanceDBController 
{
	
	
	static PreparedStatement statement;
	
	
	//adding leave types for new joining
	public static boolean addLeaveForEmployee(LeaveBalance leave)
	{
		
		String query = DBConstant.INSERT_INTO + DBConstant.LEAVE_BAlANCE_TABLE + " (" + DBConstant.EMPLOYEE_ID + ","
						+ DBConstant.TOTAL_LEAVE + ", " + DBConstant.UNUSED_LEAVE + "," + DBConstant.LEAVE_TYPE_ID +") values ("
						+ leave.getEmployeeID() +"," + leave.getTotalLeave() +","+ leave.getUnusedLeave() +"," + leave.getLeaveTypeID()+")";
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			return (statement.executeUpdate() == 1);
		} 
		catch (SQLException e) 
		{
			System.out.println(" Can't add Leave type for Employee !.....");
		}
		return false;
	}
	
	
	//get a single leave type
	public static LeaveBalance getLeaveBalance(int leaveID, int employeeID)
	{
		
		String query = DBConstant.SELECT + " * " + DBConstant.FROM + DBConstant.LEAVE_BAlANCE_TABLE + " "
						+ DBConstant.WHERE + DBConstant.LEAVE_TYPE_ID + " = " + leaveID + " " + DBConstant.AND + " "
						+ DBConstant.EMPLOYEE_ID + " = " + employeeID;
		
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				
				if(result.getInt(DBConstant.LEAVE_TYPE_ID) == leaveID && result.getInt(DBConstant.EMPLOYEE_ID) == employeeID)
				{
					
					return new LeaveBalance(result.getInt(DBConstant.TAKEN_LEAVE_ID),
											result.getInt(DBConstant.EMPLOYEE_ID),
											result.getInt(DBConstant.TOTAL_LEAVE),
											result.getInt(DBConstant.UNUSED_LEAVE),
											result.getInt(DBConstant.LEAVE_TYPE_ID));
				}
							
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println(" Can't get taken_leave_id !.....");
		}
		return null;
	}
	
	
	//get all Unused leave type for employee
	public static ResultSet getAvailableLeave(int employeeID)
	{
		
		String query = DBConstant.SELECT + DBConstant.LEAVE_BAlANCE_TABLE + "." + DBConstant.LEAVE_TYPE_ID + ", "
						+ DBConstant.LEAVE_TYPE_TABLE +"." + DBConstant.LEAVE_NAME + ", " + DBConstant.LEAVE_BAlANCE_TABLE + "." 
						+ DBConstant.UNUSED_LEAVE + " " + DBConstant.FROM + DBConstant.LEAVE_BAlANCE_TABLE +" " + DBConstant.INNER_JOIN + " "
						+ DBConstant.LEAVE_TYPE_TABLE + " " + DBConstant.ON + " " + DBConstant.LEAVE_TYPE_TABLE + "."
						+ DBConstant.LEAVE_TYPE_ID + " = " + DBConstant.LEAVE_BAlANCE_TABLE + "." + DBConstant.LEAVE_TYPE_ID + " "
						+ DBConstant.AND + " " + DBConstant.LEAVE_BAlANCE_TABLE + "." + DBConstant.EMPLOYEE_ID + " = " + employeeID
						+ DBConstant.ORDER_BY + DBConstant.LEAVE_BAlANCE_TABLE + "." + DBConstant.LEAVE_TYPE_ID;
		

		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			ResultSet result = statement.executeQuery();
			
			return result;
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting Available leave !.....");
		}
		return null;
	}

	
	public static boolean changeLeaveBalance(int leaveID, int employeeID, int remainingLeave)
	{
		
		String query = DBConstant.UPDATE + DBConstant.LEAVE_BAlANCE_TABLE + " " + DBConstant.SET 
						+ DBConstant.UNUSED_LEAVE + " = " + remainingLeave + " " + DBConstant.WHERE
						+ DBConstant.LEAVE_TYPE_ID + " = " + leaveID + " " + DBConstant.AND + DBConstant.EMPLOYEE_ID + " = " + employeeID;
		
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			return (statement.executeUpdate() == 1);
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Can't change leave balance !.....");
		}
		return false;
	}
}

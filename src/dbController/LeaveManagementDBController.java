package dbController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.LeaveManagement;



public class LeaveManagementDBController
{
	
	static PreparedStatement statement;
	
	
	public static boolean setLeaveRequest(LeaveManagement leave)
	{
		
		String query = DBConstant.INSERT_INTO + DBConstant.LEAVE_MANAGEMENT_TABLE + " (" + DBConstant.REQUEST_BY + ", "
						+ DBConstant.REPORTING_ID + ", " + DBConstant.LEAVE_TYPE_ID + ", " + DBConstant.FROM_DATE + ", "
						+ DBConstant.TO_DATE + ", " + DBConstant.REASON_FOR_LEAVE +", " +  DBConstant.STATUS + ") values (" + leave.getRequestBy() + ", "
						+ leave.getReportingID() + ", " + leave.getLeaveTypeID() + ", '" + leave.getfromDate() + "', '"
						+ leave.getToDate() + "', '" + leave.getReasonForLeave() + "', '" + leave.getStatus() + "')";
		

		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			return (statement.executeUpdate() == 1);
		} 
		catch (SQLException e) 
		{
			System.out.println(" Can't apply leave !.....");
		}
		return false;
	}
	
	
	//display self - requested leave Reports
	public static ArrayList<LeaveManagement> getLeaveReport(int employeeID)
	{
		
		ArrayList<LeaveManagement> leaveReport = new ArrayList<>();
		LeaveManagement leave;
		String query = DBConstant.SELECT + " * " + DBConstant.FROM + DBConstant.LEAVE_MANAGEMENT_TABLE + " "
						+ DBConstant.WHERE + DBConstant.REQUEST_BY + " = " + employeeID + " " + DBConstant.ORDER_BY + DBConstant.LEAVE_ID +" " + DBConstant.DESC;
				
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				leave = new LeaveManagement(result.getInt(DBConstant.LEAVE_ID),
						result.getInt(DBConstant.REQUEST_BY),
						result.getInt(DBConstant.REPORTING_ID),
						result.getInt(DBConstant.LEAVE_TYPE_ID),
						result.getDate(DBConstant.FROM_DATE),
						result.getDate(DBConstant.TO_DATE),
						result.getString(DBConstant.REASON_FOR_LEAVE),
						result.getString(DBConstant.STATUS),
						result.getString(DBConstant.REJECTED_REASON));
				
				leaveReport.add(leave);
			}
			return leaveReport;
		} 
		catch (SQLException e) 
		{
			System.out.println(" Can't get leave report!.....");
		}
		return null;
	}

	
	//display Requests for senior employee to accept or reject requests
	public static ArrayList<LeaveManagement> getLeaveRequests(int employeeID)
	{
		
		ArrayList<LeaveManagement> leaveReport = new ArrayList<>();
		LeaveManagement leave;
		
		String query = DBConstant.SELECT + " * " + DBConstant.FROM + DBConstant.LEAVE_MANAGEMENT_TABLE + " "
						+ DBConstant.WHERE + DBConstant.REPORTING_ID + " = " + employeeID + " " + DBConstant.AND + DBConstant.STATUS + " = 'pending' "
						+ DBConstant.ORDER_BY + DBConstant.LEAVE_ID +" " + DBConstant.DESC;
				
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				leave = new LeaveManagement(result.getInt(DBConstant.LEAVE_ID),
						result.getInt(DBConstant.REQUEST_BY),
						result.getInt(DBConstant.REPORTING_ID),
						result.getInt(DBConstant.LEAVE_TYPE_ID),
						result.getDate(DBConstant.FROM_DATE),
						result.getDate(DBConstant.TO_DATE),
						result.getString(DBConstant.REASON_FOR_LEAVE),
						result.getString(DBConstant.STATUS),
						result.getString(DBConstant.REJECTED_REASON));
				
				leaveReport.add(leave);
			}
			return leaveReport;
		} 
		catch (SQLException e) 
		{
			System.out.println(" Can't get leave requests!.....");
		}
		return null;
	}
	
	public static LeaveManagement returnLeaveReportIfLeaveIdPresent(int leaveID, int reportingID)
	{
		
		String query = DBConstant.SELECT + " * " + DBConstant.FROM + DBConstant.LEAVE_MANAGEMENT_TABLE + " "
						+ DBConstant.WHERE + DBConstant.LEAVE_ID + " = " + leaveID + " " + DBConstant.AND
						+ DBConstant.REPORTING_ID + " = " + reportingID;
						
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				if(result.getInt(DBConstant.LEAVE_ID) == leaveID)
				{
					 return new LeaveManagement(result.getInt(DBConstant.LEAVE_ID),
												result.getInt(DBConstant.REQUEST_BY),
												result.getInt(DBConstant.REPORTING_ID),
												result.getInt(DBConstant.LEAVE_TYPE_ID),
												result.getDate(DBConstant.FROM_DATE),
												result.getDate(DBConstant.TO_DATE),
												result.getString(DBConstant.REASON_FOR_LEAVE),
												result.getString(DBConstant.STATUS),
												result.getString(DBConstant.REJECTED_REASON));
				}
			}
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Can't get specific leave report !.....");
		}
		return null;
	}

	
	public static boolean approveOrRejectLeave(int leaveID, String status, String rejectedReason)
	{
		
		String query = DBConstant.UPDATE + DBConstant.LEAVE_MANAGEMENT_TABLE + " " + DBConstant.SET 
						+ DBConstant.STATUS + " = '" + status + "' , " + DBConstant.REJECTED_REASON + " = '"
						+ rejectedReason + "' " + DBConstant.WHERE + DBConstant.LEAVE_ID + " = " + leaveID;

		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			return (statement.executeUpdate() == 1);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println(" Can't approve / reject leave !.....");
		}
		return false;
	}
	
}

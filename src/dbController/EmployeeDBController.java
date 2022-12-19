package dbController;

import java.sql.*;
import model.*;


public class EmployeeDBController 
{

	static PreparedStatement statement;
	
	
	public static String getEmployeeName(int employeeID)
	{
		
		String query = DBConstant.SELECT + DBConstant.NAME +" "+ DBConstant.FROM 
						+ DBConstant.EMPLOYEE_TABLE +" "+ DBConstant.WHERE + DBConstant.ID + " = " + employeeID;
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while(result.next())
			{
				String name = result.getString(DBConstant.NAME);
				return name;
			}
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting Employee Name !.....");
		}
		
		return null;
		
	}
	
	public static int getEmployeeID(String employeeName)
	{
		
		String query = DBConstant.SELECT + DBConstant.ID +" "+ DBConstant.FROM 
						+ DBConstant.EMPLOYEE_TABLE +" "+ DBConstant.WHERE + DBConstant.NAME + " = '" + employeeName+"'";
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			int id = result.getInt(DBConstant.ID);
			return id;
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting Employee ID !");
		}
		
		return 0;
		
	}
	
	public static boolean isEmployeePresent(int userID)
	{
		
		String query = DBConstant.SELECT + " * "+ DBConstant.FROM 
				+ DBConstant.EMPLOYEE_TABLE +" "+ DBConstant.WHERE + DBConstant.ID + " = " + userID;

		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				int id = result.getInt(DBConstant.ID);
				
				if(id == userID)
				{
					return true;
				}
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println(" Error occured in verifying Employee Present or not !");
		}

		return false;
	}
	
	public static  ResultSet getReportingID(int userTeamID, int rolePriority)
	{
		
		ResultSet result = null;
		
		String query = DBConstant.SELECT + DBConstant.ID +", "+ DBConstant.NAME +", "+DBConstant.ROLE_TABLE +"." + DBConstant.ROLE_NAME 
						+ " "+ DBConstant.FROM + DBConstant.EMPLOYEE_TABLE +" "+ DBConstant.INNER_JOIN 
						+" "+ DBConstant.ROLE_TABLE +" "+ DBConstant.ON +" "+ DBConstant.ROLE_TABLE +"."+DBConstant.ROLE_ID +" = " 
						+ DBConstant.EMPLOYEE_TABLE +"." + DBConstant.ROLE_ID
						+ DBConstant.WHERE + DBConstant.TEAM_ID + " = " + userTeamID 
						+ DBConstant.AND +" "+ DBConstant.ROLE_TABLE +"."+ DBConstant.ROLE_PRIORITY +" < " + rolePriority;

		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			result = statement.executeQuery();
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting Reporting ID !");
		}
		
		return result;
		
	}
	
	public static ResultSet getNewReportingID(int userTeamID, int rolePriority, int employeeID)
	{
		
		ResultSet result = null;
		
		String query = DBConstant.SELECT + DBConstant.ID +", "+ DBConstant.NAME +", "+DBConstant.ROLE_TABLE +"." + DBConstant.ROLE_NAME 
						+ " "+ DBConstant.FROM + DBConstant.EMPLOYEE_TABLE +" "+ DBConstant.INNER_JOIN 
						+" "+ DBConstant.ROLE_TABLE +" "+ DBConstant.ON +" "+ DBConstant.ROLE_TABLE +"."+DBConstant.ROLE_ID +" = " 
						+ DBConstant.EMPLOYEE_TABLE +"." + DBConstant.ROLE_ID
						+ DBConstant.AND +" " + DBConstant.TEAM_ID + " = " + userTeamID 
						+ DBConstant.AND +" " + DBConstant.ROLE_TABLE +"."+ DBConstant.ROLE_PRIORITY +" < " + rolePriority +" "
						+ DBConstant.AND +" " + DBConstant.EMPLOYEE_TABLE +"."+ DBConstant.ID +" != "+employeeID;
		
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			result = statement.executeQuery();
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting new Reporting ID !");
		}
		
		return result;
		
	}
	
	

	public static boolean isOfficialMailExist(String mail)
	{
		
		String query = DBConstant.SELECT + DBConstant.COMPANY_MAIL +" " + DBConstant.FROM 
						+ DBConstant.EMPLOYEE_TABLE +" "+ DBConstant.WHERE 
						+ DBConstant.COMPANY_MAIL + " = '" + mail +"'";
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				
				String mailID = result.getString(DBConstant.COMPANY_MAIL);

				if(mail.equalsIgnoreCase(mailID))
				{
					System.out.println(mail);

					return true;
				}
			}
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in Mail Verification !");
		}

		return false;
	}
	
	public static boolean addEmployee(Employee employee)
	{
		
		String query = DBConstant.INSERT_INTO + DBConstant.EMPLOYEE_TABLE + " (" 
						+ DBConstant.NAME+","+DBConstant.ROLE_ID+","+DBConstant.REPORTING_ID+","
						+DBConstant.TEAM_ID+","+DBConstant.COMPANY_MAIL+","+DBConstant.DOJ+","
						+DBConstant.WORK_LOCATION+","+DBConstant.GENDER+") values ('"
						+employee.getemployeeName()+"',"+employee.getemployeeRoleID()+","
						+employee.getReportingToID()+","+employee.getEmployeeTeamID()+", '"
						+employee.getCompanyMailId()+"', '"+employee.getDateOfJoining()+"', "
						+employee.getWorkLocationID()+", '"+employee.getGender()+"' )";
		
		try 
		{
		
			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
			return true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return false;
		}
				
	}

	public static boolean employeesCount()
	{
		
		String query = DBConstant.SELECT + "count(*) " + DBConstant.FROM + "(" 
				+ DBConstant.SELECT + 1 + DBConstant.FROM + DBConstant.EMPLOYEE_TABLE + " limit 3 ) as EmployeeCount";
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			int count = result.getInt(1); //if minimum three employees present, returns true
			
			if(count == 3)
			{
				return true;
			}
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting Employee count !");
		}
		
		return false;
		
	}
	
	public static Employee getEmployee(int employeeID)
	{
		
		String query = DBConstant.SELECT + " * "+ DBConstant.FROM 
				+ DBConstant.EMPLOYEE_TABLE +" "+ DBConstant.WHERE + DBConstant.ID + " = " + employeeID;

		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				int id = result.getInt(DBConstant.ID);
				String name = result.getString(DBConstant.NAME);
				int roleID = result.getInt(DBConstant.ROLE_ID);
				int reportingID = result.getInt(DBConstant.REPORTING_ID);
				int teamID = result.getInt(DBConstant.TEAM_ID);
				String companyMail = result.getString(DBConstant.COMPANY_MAIL);
				Date doj = result.getDate(DBConstant.DOJ);
				int locationID = result.getInt(DBConstant.WORK_LOCATION);
				String gender = result.getString(DBConstant.GENDER);

				Employee employee = new Employee(id, name, roleID, reportingID, teamID, companyMail, doj, locationID, gender);
				return employee;
			}
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting Employee Instance !");
		}
		
		return null;

	}
	
	
	public static  ResultSet getTeamDetails(int teamID)
	{
		
		ResultSet result = null;
		
		String query = DBConstant.SELECT + DBConstant.ID +", "+ DBConstant.NAME +", "+DBConstant.ROLE_TABLE +"." + DBConstant.ROLE_NAME 
						+ " "+ DBConstant.FROM + DBConstant.EMPLOYEE_TABLE +" "+ DBConstant.INNER_JOIN 
						+" "+ DBConstant.ROLE_TABLE +" "+ DBConstant.ON +" "+ DBConstant.ROLE_TABLE +"."+DBConstant.ROLE_ID +" = " 
						+ DBConstant.EMPLOYEE_TABLE +"." + DBConstant.ROLE_ID
						+ DBConstant.WHERE + DBConstant.TEAM_ID + " = " + teamID ;
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			result = statement.executeQuery();
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting Team details accessing method !\n");
		}
		
		return result;
		
	}
	
	
	public static boolean setReportingID(int reportingID, int employeeID)
	{
		
		String query = DBConstant.UPDATE + DBConstant.EMPLOYEE_TABLE +" "+ DBConstant.SET 
						+ DBConstant.REPORTING_ID +" = "+reportingID + DBConstant.WHERE + DBConstant.ID + " = "+employeeID;
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			int result = statement.executeUpdate();
			
			if(result == 1)
			{
				return true;
			}
			
		} 
		
		catch (SQLException e) 
		{
			System.out.println(" Error occured in setting Reporting ID !");
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public static Employee getEmployeePersonalInfo(int employeeID)
//	{
//		
//		String query = DBConstant.SELECT + DBConstant.WORK_EXPERIENCE_TABLE +"."+ DBConstant.EMPLOYEE_ID +","+
//		 DBConstant.MOBILE+","+DBConstant.EMAIL_ID+","+DBConstant.DEGREE+","+DBConstant.PASSED_OUT_YEAR+","+
//		DBConstant.ADDRESS+","+DBConstant.COMPANY_NAME+","+DBConstant.COMPANY_ROLE+","
//		+DBConstant.YEARS_OF_EXPERIENCE+" "+DBConstant.FROM+DBConstant.PERSONAL_INFORMATION_TABLE+" "+
//		DBConstant.INNER_JOIN+" "+DBConstant.WORK_EXPERIENCE_TABLE+" "
//		+ DBConstant.ON+ DBConstant.PERSONAL_INFORMATION_TABLE +"."+ DBConstant.EMPLOYEE_ID +" = "
//		+ DBConstant.WORK_EXPERIENCE_TABLE+"."+DBConstant.EMPLOYEE_ID +" "+DBConstant.AND+
//		DBConstant.EMPLOYEE_ID+" = "+employeeID;
//
//		System.out.println(query);
//		try 
//		{
//			
//			statement = DBConnector.getConnection().prepareStatement(query);
//			ResultSet result = statement.executeQuery();
//			while(result.next())
//			{
//				String mobile = result.getString(DBConstant.MOBILE);
//				String emailID = result.getString(DBConstant.EMAIL_ID);
//				String address = result.getString(DBConstant.ADDRESS);
//				String degree = result.getString(DBConstant.DEGREE);
//				int passedYear = result.getInt(DBConstant.PASSED_OUT_YEAR);
//				String comapanyName = result.getString(DBConstant.COMPANY_NAME);
//				String companyRole = result.getString(DBConstant.COMPANY_ROLE);
//				int experience = result.getInt(DBConstant.YEARS_OF_EXPERIENCE);
//
//
//
////				Employee employee = new Employee(id, name, roleID, reportingID, teamID, companyMail, doj, locationID, gender);
////				return employee;
//			}
//			
//		} 
//		catch (SQLException e) 
//		{
//			System.out.println(" Error occured in getting Employee Instance !");
//		}
//		
//		return null;
//
//	}
	
}

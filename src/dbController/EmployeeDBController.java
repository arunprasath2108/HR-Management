package dbController;

import java.sql.*;
import java.util.ArrayList;

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
			result.next();
			return result.getString(DBConstant.NAME);
			
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
			return result.getInt(DBConstant.ID);
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
				if(result.getInt(DBConstant.ID) == userID)
				{
					return true;
				}
			}
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in verifying Employee Present or not !");
		}

		return false;
	}
	
	public static int isEmployeeInTeam(int teamID, int employeeID)
	{
		int id = 0;
		String query = DBConstant.SELECT + DBConstant.ID +" "
						+ DBConstant.FROM + DBConstant.EMPLOYEE_TABLE  + " "
						+ DBConstant.WHERE + DBConstant.TEAM_ID + " = " + teamID + DBConstant.AND +" " + DBConstant.ID +" = " + employeeID ;
		
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				return result.getInt(DBConstant.ID);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("  Error in verify Employee is Present in team or not ");
			
		}
		return id;
	}
	
	
	public static ResultSet getReportingID(int userTeamID, int rolePriority)
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
			return statement.executeQuery();
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
			return statement.executeQuery();
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
				if(mail.equalsIgnoreCase(result.getString(DBConstant.COMPANY_MAIL)))
				{
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
			return (statement.executeUpdate() == 1);
		} 
		catch (SQLException e) 
		{
			return false;
		}
				
	}

	public static boolean isMinimumEmployeesPresent()
	{
		
		String query = DBConstant.SELECT + "count(*) " + DBConstant.FROM + "(" 
				+ DBConstant.SELECT + 1 + DBConstant.FROM + DBConstant.EMPLOYEE_TABLE + " limit 3) as EmployeeCount";
																						//subquery in FROM must have an alias
		try 																	
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			
			return (result.getInt(1) == 3);   //if minimum three employees present, returns true
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

				return new Employee(
						result.getInt(DBConstant.ID), result.getString(DBConstant.NAME),
						result.getInt(DBConstant.ROLE_ID), result.getInt(DBConstant.REPORTING_ID),
						result.getInt(DBConstant.TEAM_ID), result.getString(DBConstant.COMPANY_MAIL),
						result.getDate(DBConstant.DOJ), result.getInt(DBConstant.WORK_LOCATION),
						result.getString(DBConstant.GENDER));
				
			}
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting Employee Instance !");
		}
		
		return null;
	}
	
	public static ArrayList<Employee> getAllEmployee()
	{
		
		ArrayList<Employee>  employees = new ArrayList<Employee>();
		Employee employee;
		String query = DBConstant.SELECT + " * "+ DBConstant.FROM + DBConstant.EMPLOYEE_TABLE ;

		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{

				employee = new Employee(result.getInt(DBConstant.ID),result.getString(DBConstant.NAME),
										result.getInt(DBConstant.ROLE_ID), result.getInt(DBConstant.REPORTING_ID),
										result.getInt(DBConstant.TEAM_ID), result.getString(DBConstant.COMPANY_MAIL),
										result.getDate(DBConstant.DOJ), result.getInt(DBConstant.WORK_LOCATION),
										result.getString(DBConstant.GENDER));
				
				employees.add(employee);
				
			}
			return employees;
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting all Employees !");
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
			return statement.executeQuery();
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
			return (statement.executeUpdate() == 1);
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in setting Reporting ID !");
		}
		return false;
	}
	
	public static boolean changeEmployeeTeam(int teamID, int employeeID)
	{
		
		String query = DBConstant.UPDATE + DBConstant.EMPLOYEE_TABLE +" "+ DBConstant.SET 
						+ DBConstant.TEAM_ID +" = "+teamID + DBConstant.WHERE + DBConstant.ID + " = "+employeeID;
		
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			return (statement.executeUpdate() == 1);
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in setting Team ID !");
		}
		return false;
	}
	
	public static boolean changeEmployeeWorkLocation(int locationID, int employeeID)
	{
		
		String query = DBConstant.UPDATE + DBConstant.EMPLOYEE_TABLE +" "+ DBConstant.SET 
						+ DBConstant.WORK_LOCATION +" = "+locationID + DBConstant.WHERE + DBConstant.ID + " = "+employeeID;
		
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);			
			return (statement.executeUpdate() != 0);
		} 
		
		catch (SQLException e) 
		{
			System.out.println(" Error occured in setting location!");
		}
		return false;
	}
	
	
	public static ArrayList<Employee> getReportee(int employeeID)
	{
		
		ArrayList<Employee> reportees = new ArrayList<>();
		Employee employee;
		String query = DBConstant.SELECT + " * "+ DBConstant.FROM 
				+ DBConstant.EMPLOYEE_TABLE +" "+ DBConstant.WHERE + DBConstant.REPORTING_ID + " = " + employeeID;

		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			 	while(result.next())
			 	{
					employee = new Employee(result.getInt(DBConstant.ID), result.getString(DBConstant.NAME),
					result.getInt(DBConstant.ROLE_ID), result.getInt(DBConstant.REPORTING_ID),
					result.getInt(DBConstant.TEAM_ID), result.getString(DBConstant.COMPANY_MAIL),
					result.getDate(DBConstant.DOJ), result.getInt(DBConstant.WORK_LOCATION),
					result.getString(DBConstant.GENDER));
					
					reportees.add(employee);
			 	}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println(" Error occured in getting Employee Instance !");
		}
		
		return reportees;
	}
	
	

}

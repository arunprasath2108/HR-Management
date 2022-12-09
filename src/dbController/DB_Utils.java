package dbController;

import java.sql.*;

public class DB_Utils 
{
	
	private static final String DATABASE_NAME = "HR_Management ";
	private static final String EMPLOYEE_TABLE = "Employee ";
	private static final String TEAMS_TABLE = "Teams ";
	private static final String ROLE_TABLE = "Role ";
	private static final String WORK_LOCATION_TABLE = "Work_Location ";
	private static final String PERSONAL_INFORMATION_TABLE = "Personal_Information ";
	private static final String WORK_EXPERIENCE_TABLE = "Work_Experience ";

	
	private static final String CREATE_TABLE_IF_NOT_EXISTS = "create table if not exists ";
	private static final String CREATE_DATABASE = "create database ";
	private static final String SELECT = "Select ";
	private static final String FROM = "from ";

	 static PreparedStatement statement;
	 static Connection connection;
	 
	
	public void createDatabase()
	{
		
		boolean isPresent = true;
		String query = SELECT + " datname " + FROM + " pg_database";

		try
		{
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				String existingDB = resultSet.getString(1);
				
				if(existingDB.equals(DATABASE_NAME))
				{
					isPresent = false;
					System.out.println("  Database Already Exists..!");
					break;
				}
			}
			
			if(isPresent)
			{
				String query2 = CREATE_DATABASE + DATABASE_NAME;
				statement = connection.prepareStatement(query2);
				statement.executeUpdate();
				
			}
			statement.close();
			connection.close();
				
		}
		catch (SQLException e) 
		{
			System.out.println("  Can't create Database..!");
		}
		
	}

	public void createEmployeeTable()
	{
		
		String query = CREATE_TABLE_IF_NOT_EXISTS + EMPLOYEE_TABLE + 
				" (ID serial not null primary key,"
				+ " Name varchar(50) not null, "
				+ "role_ID int, Team_ID int, "
				+ "Reporting_ID int, "
				+ "Work_Location int, "
				+ "DOJ date not null, "
				+ "mail_ID varchar(100) unique not null, "
				+ "gender varchar(10) not null, "
				+ "Foreign key(role_ID) "
				    + "references role(role_id), "
			    + "Foreign key(team_ID) "
			        + "references teams(team_id), "
			    + "Foreign key(work_location) "
			        + "references work_location(location_id))";
		
		try 
		{
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			statement.executeUpdate();
		} 
		
		catch (SQLException e) 
		{
			System.out.println("  can't create Employee Table ");
		}

	}

	public void createRoleTable()
	{
		
		String query = CREATE_TABLE_IF_NOT_EXISTS + ROLE_TABLE + " (Role_ID serial primary key, Role_Name varchar(30) unique not null) ";
		
		try 
		{
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			statement.executeUpdate();
		} 
		
		catch (SQLException e) 
		{
			System.out.println("  Can't create Role table ");
		}
	}
	
	public void createTeamsTable()
	{
		
		String query = CREATE_TABLE_IF_NOT_EXISTS + TEAMS_TABLE + " ( Team_ID serial primary key, Team_Name varchar(50) unique not null) ";
		
		try 
		{
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			statement.executeUpdate();
		} 
		
		catch (SQLException e) 
		{
			System.out.println("  Can't create Team table ");
		}
	}
	
	public void createWorkLocationTable()
	{
		
		String query = CREATE_TABLE_IF_NOT_EXISTS + WORK_LOCATION_TABLE + " ( Location_ID serial primary key, Locations varchar(50) unique not null)";
		
		try 
		{
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			statement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			System.out.println("  Can't create Work Location table ");
		}
	}

	public void insertLoctions()
	{
		
		String query = " insert into " + WORK_LOCATION_TABLE + "(locations) values ('Chennai'),('Coimbatore'), ('Madurai')";
		
		try 
		{
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			statement.executeUpdate();
			statement.close();
			connection.close();
		}
		
		catch (SQLException e)
		{
			System.out.println(" Can't add values into table ");
		}
	}

	public void createPersonalInfo()
	{
		
		String query = CREATE_TABLE_IF_NOT_EXISTS + PERSONAL_INFORMATION_TABLE + 
				" (Employee_ID int primary key, "
				+ "mobile varchar(15) unique, "
				+ "Email_ID varchar(100) unique, "
				+ "Address varchar(150), "
				+ "Degree varchar(50), "
				+ "PassedOutYear int, "
				+ " foreign key(Employee_ID) "
				     + "references Employee (ID) )";
		
		connection = DB_Connection.getConnection();
		
		try
		{
			statement = connection.prepareStatement(query);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} 
		
		catch (SQLException e) 
		{
			System.out.println("  Can't create personal info table ");
		}
		
	}
	
	public void createWorkExperience()
	{
		
		String query = CREATE_TABLE_IF_NOT_EXISTS + WORK_EXPERIENCE_TABLE + 
				" (Employee_ID int primary key, "
				+ "Company_Name varchar(50), "
				+ "Role varchar(30), "
				+ "Years_of_Experience int, "
				+ "Foreign key (employee_id) "
				    + "references Employee (id))";
		
		connection = DB_Connection.getConnection();
		
		try
		{
			statement = connection.prepareStatement(query);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} 
		
		catch (SQLException e) 
		{
			System.out.println("  Can't create work experience table ");
			
		}
	}

	public static String getRoleName(int roleID)
	{
		
		String query = SELECT + "role_name " + FROM + ROLE_TABLE + " where role_id = ?";
		
		try 
		{
			
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, roleID);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				
				String role = result.getString("Role_Name");
				return role;
				
			}
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getRoleName method  !");
		}
		
		return null;
	}
	
	public static int getRoleID(int employeeID)
	{
		
		String query = SELECT + "role_ID " + FROM + EMPLOYEE_TABLE + " where id = ?";
		
		try 
		{
			
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, employeeID);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				int roleID = result.getInt("Role_id");
				return roleID;
			}
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getRoleName method  !");
		}
		
		return 0;
	}

	public static String getEmployeeName(int employeeID)
	{
		
		String query = SELECT + " name " + FROM + EMPLOYEE_TABLE + "where id = ?";
		
		try 
		{
			
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, employeeID);
			ResultSet result = statement.executeQuery();
			result.next();
			String name = result.getString("name");
			return name;
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting Employee Name !");
		}
		
		return null;
		
	}
	
	public static String getTeamName(int teamID)
	{
		
		String query = SELECT + "team_name " + FROM + TEAMS_TABLE + "where team_id = ?";
		
		try 
		{
			
			connection = DB_Connection.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, teamID);
			ResultSet result = statement.executeQuery();
			result.next();
			String teamName = result.getString("team_name");
			return teamName;
			
		} 
		
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting Team Name !");
		}
		
		return null;
	}

	
}

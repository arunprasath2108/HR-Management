package dbController;

import java.sql.*;

public class DBUtils 
{

	 static PreparedStatement statement;
	 
	
	//method for creating all tables
	public static void createTables()
	{
		createRoleTable();
		createTeamsTable();
		createWorkLocationTable();
		createEmployeeTable();
		//insertLoctions();
		createPersonalInfo();
		createWorkExperience();
	}
	
	
	public static void createDatabase()
	{
		
		boolean isPresent = true;
		String query = DBConstant.SELECT + " datname " + DBConstant.FROM + " pg_database";

		try
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				String existingDB = resultSet.getString(1);
				
				if(existingDB.equals(DBConstant.DATABASE_NAME))
				{
					isPresent = false;
					System.out.println("  Database Already Exists..!");
					break;
				}
			}
			
			if(isPresent)
			{
				String query2 = DBConstant.CREATE_DATABASE + DBConstant.DATABASE_NAME;
				statement = DBConnector.getConnection().prepareStatement(query2);
				statement.executeUpdate();
				
			}

				
		}
		catch (SQLException e) 
		{
			System.out.println("  Can't create Database..!");
		}
		
	}
	
	public static void createRoleTable()
	{
		
		String query = DBConstant.CREATE_TABLE_IF_NOT_EXISTS +" "
						+ DBConstant.ROLE_TABLE + " (" 
						+ DBConstant.ROLE_ID + " serial primary key, "
						+ DBConstant.ROLE_NAME +" varchar(30) unique not null, "
						+ DBConstant.ROLE_PRIORITY +" int not null) ";
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
			statement.close();
		} 
		
		catch (SQLException e) 
		{
			System.out.println("  Can't create Role table ");
		}
		
	}
	
	public static void createTeamsTable()
	{
		
		String query = DBConstant.CREATE_TABLE_IF_NOT_EXISTS + " " + DBConstant.TEAMS_TABLE + " ("
						+ DBConstant.TEAM_ID + " serial primary key, "
						+ DBConstant.TEAM_NAME + " varchar(50) unique not null) ";
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
			statement.close();
		} 
		
		catch (SQLException e) 
		{
			System.out.println("  Can't create Team table ");
		}
	}
	
	public static void createWorkLocationTable()
	{
		
		String query = DBConstant.CREATE_TABLE_IF_NOT_EXISTS + " " + DBConstant.WORK_LOCATION_TABLE + " ("
						+ DBConstant.LOCATION_ID + " serial primary key, "
						+ DBConstant.LOCATION_NAME + " varchar(50) unique not null)";
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
			statement.close();
		} 
		catch (SQLException e) 
		{
			System.out.println("  Can't create Work Location table ");
		}
	}
	
	public static void createEmployeeTable()
	{
		
		String query = DBConstant.CREATE_TABLE_IF_NOT_EXISTS + " " + DBConstant.EMPLOYEE_TABLE + " (" 
				        + DBConstant.ID + " serial not null primary key, "
						+ DBConstant.NAME + " varchar(50) not null, "
						+ DBConstant.ROLE_ID + " int, "
						+ DBConstant.TEAM_ID + " int, "
						+ DBConstant.REPORTING_ID + " int, "
						+ DBConstant.WORK_LOCATION + " int, "
						+ DBConstant.DOJ + " date not null, "
						+ DBConstant.COMPANY_MAIL + " varchar(100) unique not null, "
						+ DBConstant.GENDER + " varchar(10) not null, "
						+ DBConstant.FOREIGN_KEY + " (role_ID) "
						    + DBConstant.REFERENCES + " role(role_id), "
					    + DBConstant.FOREIGN_KEY + " (team_ID) "
					        + DBConstant.REFERENCES + " teams(team_id), "
					    + DBConstant.FOREIGN_KEY + " (work_location) "
					        + DBConstant.REFERENCES + " work_location(location_id))";
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
			statement.close();
		} 
		
		catch (SQLException e) 
		{
			System.out.println("  can't create Employee Table ");
		}

	}

	public static void insertLoctions()
	{
		
		String query = DBConstant.INSERT_INTO + " " + DBConstant.WORK_LOCATION_TABLE + " ("
						+ DBConstant.LOCATION_NAME + ") values ('Chennai'),('Coimbatore'), ('Madurai')";
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
			statement.close();
		}
		
		catch (SQLException e)
		{
			System.out.println(" Can't add values into table ");
		}
	}

	public static void createPersonalInfo()
	{
		
		String query = DBConstant.CREATE_TABLE_IF_NOT_EXISTS + " " + DBConstant.PERSONAL_INFORMATION_TABLE + " ("
						+ DBConstant.EMPLOYEE_ID + " int primary key, "
						+ DBConstant.MOBILE + " varchar(15) unique, "
						+ DBConstant.EMAIL_ID + " varchar(100) unique, "
						+ DBConstant.ADDRESS + " varchar(150), "
						+ DBConstant.DEGREE + " varchar(50), "
						+ DBConstant.PASSED_OUT_YEAR + " int, "
						+ DBConstant.FOREIGN_KEY +" (" + DBConstant.EMPLOYEE_ID +") "
						     + DBConstant.REFERENCES +" " + DBConstant.EMPLOYEE_TABLE +" (" + DBConstant.ID +") )";
		
		try
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
			statement.close();
		} 
		
		catch (SQLException e) 
		{
			System.out.println("  Can't create personal info table ");
		}
		
	}
	
	public static void createWorkExperience()
	{
		
		String query = DBConstant.CREATE_TABLE_IF_NOT_EXISTS + " " + DBConstant.WORK_EXPERIENCE_TABLE + "("
						+ DBConstant.EMPLOYEE_ID +" int primary key, "
						+ DBConstant.COMPANY_NAME +" varchar(50), "
						+ DBConstant.COMPANY_ROLE + " varchar(30), "
						+ DBConstant.YEARS_OF_EXPERIENCE +" int, "
						+ DBConstant.FOREIGN_KEY +" (" + DBConstant.EMPLOYEE_ID +") "
						    + DBConstant.REFERENCES + DBConstant.EMPLOYEE_TABLE + " (" + DBConstant.ID +") )";
		
		try
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
			statement.close();
		} 
		
		catch (SQLException e) 
		{
			System.out.println("  Can't create work experience table ");
			
		}
	}

	
}

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
//		insertDefaultLoctions();
		createPersonalInfoTable();
		createWorkExperienceTable();
		createRequestsTable();
		createNotificationTable();
		createLeaveTypeTable();
		createLeaveManagementTable();
		createLeaveBalanceTable();
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
					System.out.println("  Database Already Exists..!\n");
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
						+ DBConstant.FOREIGN_KEY + " (" + DBConstant.ROLE_ID + ") "
						    + DBConstant.REFERENCES + DBConstant.ROLE_TABLE + "(" + DBConstant.ROLE_ID  + "), "
					    + DBConstant.FOREIGN_KEY + " (" + DBConstant.TEAM_ID  +") "
					        + DBConstant.REFERENCES + DBConstant.TEAMS_TABLE + "(" + DBConstant.TEAM_ID + "), "
					    + DBConstant.FOREIGN_KEY + " (" + DBConstant.WORK_LOCATION  +") "
					        + DBConstant.REFERENCES + DBConstant.WORK_LOCATION +  "(" + DBConstant.LOCATION_ID + "))";
		
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			System.out.println("  can't create Employee Table ");
		}
	}

	public static void insertDefaultLoctions()
	{
		
		String query = DBConstant.INSERT_INTO + " " + DBConstant.WORK_LOCATION_TABLE + " ("
						+ DBConstant.LOCATION_NAME + ") values ('Chennai'),('Coimbatore'), ('Madurai')";
		
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
		}
		catch (SQLException e)
		{
			System.out.println(" Can't add values into table ");
		}
	}

	public static void createPersonalInfoTable()
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
		} 
		catch (SQLException e) 
		{
			System.out.println("  Can't create personal info table ");
		}
	}
	
	public static void createWorkExperienceTable()
	{
		
		String query = DBConstant.CREATE_TABLE_IF_NOT_EXISTS + " " + DBConstant.WORK_EXPERIENCE_TABLE + "("
						+ DBConstant.WORK_EXPERIENCE_ID + " serial primary key, "
						+ DBConstant.EMPLOYEE_ID +" int , "
						+ DBConstant.COMPANY_NAME +" varchar(50), "
						+ DBConstant.COMPANY_ROLE + " varchar(30), "
						+ DBConstant.YEARS_OF_EXPERIENCE +" varchar(20), "
						+ DBConstant.FOREIGN_KEY +" (" + DBConstant.EMPLOYEE_ID +") "
						    + DBConstant.REFERENCES + DBConstant.EMPLOYEE_TABLE + " (" + DBConstant.ID +") )";
		
		try
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			System.out.println("  Can't create work experience table ");
		}
	}
	
	public static void createRequestsTable()
	{
		
		String query = DBConstant.CREATE_TABLE_IF_NOT_EXISTS +" "+DBConstant.TEAM_CHANGE_REQUEST_TABLE +" ("
				        +DBConstant.REQUEST_ID +" int not null generated always as identity (increment 2 start 101) "+DBConstant.PRIMARY_KEY + ", "
						+ DBConstant.REQUEST_BY +" int not null,"+DBConstant.RECEIVER_ID +" int not null, "
						+ DBConstant.REQUESTED_ON +" timestamp not null, "
						+ DBConstant.TEAM_ID + " int not null, "
						+ DBConstant.STATUS + " varchar(255), "
						+ DBConstant.FOREIGN_KEY +" (" + DBConstant.RECEIVER_ID +") "
						    + DBConstant.REFERENCES + DBConstant.EMPLOYEE_TABLE + " (" + DBConstant.ID +") )";
		
		
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			System.out.println("  Can't create Request table ");
		}
	}
	
	public static void createNotificationTable()
	{
		
		String query = DBConstant.CREATE_TABLE_IF_NOT_EXISTS +" "+DBConstant.NOTIFICATION_TABLE +" ("
						+  DBConstant.NOTIFICATION_ID + " serial , " + DBConstant.EMPLOYEE_ID +" int , " + DBConstant.NOTIFICATION + " varchar(255), "
						+ DBConstant.NOTIFICATION_SEEN +" boolean not null , "
						+ DBConstant.NOTIFICATION_TIME + " timestamp not null, "
						+ DBConstant.FOREIGN_KEY +" (" + DBConstant.EMPLOYEE_ID +") "
						    + DBConstant.REFERENCES + DBConstant.EMPLOYEE_TABLE + " (" + DBConstant.ID +") )";
		
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			System.out.println("  Can't create Notification table ");
		}
	}
	
	public static void createLeaveTypeTable()
	{
		
		String query = DBConstant.CREATE_TABLE_IF_NOT_EXISTS +" "+DBConstant.LEAVE_TYPE_TABLE + " (" 
						+ DBConstant.LEAVE_TYPE_ID + " serial primary key, "
						+ DBConstant.LEAVE_NAME + " varchar(100) not null, " + DBConstant.LEAVE_COUNT + " int not null," +  DBConstant.GENDER + " varchar(20) not null ) ";  
						
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			System.out.println("  Can't create Leave Type table ");
		}
	}
	
	
	public static void createLeaveManagementTable()
	{
		
		String query = DBConstant.CREATE_TABLE_IF_NOT_EXISTS +" "+DBConstant.LEAVE_MANAGEMENT_TABLE + " (" 
						+ DBConstant.LEAVE_ID + " int not null generated always as identity (increment 2 start 1001) " +  DBConstant.PRIMARY_KEY  +", "
						+ DBConstant.REQUEST_BY + " int, " + DBConstant.REPORTING_ID + " int, " + DBConstant.LEAVE_TYPE_ID + " int, "  
						+ DBConstant.FROM_DATE + " date not null, " + DBConstant.TO_DATE + " date not null, "
						+ DBConstant.REASON_FOR_LEAVE + " varchar(255), " + DBConstant.STATUS + " varchar(50) not null, "
						+ DBConstant.REJECTED_REASON + " varchar(255), " 
						+ DBConstant.FOREIGN_KEY +" (" + DBConstant.LEAVE_TYPE_ID +") "
					    	+ DBConstant.REFERENCES + DBConstant.LEAVE_TYPE_TABLE + " (" + DBConstant.LEAVE_TYPE_ID +") )";
						
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			System.out.println("  Can't create Leave Management table ");
		}
	}
	
	public static void createLeaveBalanceTable()
	{
		
		String query = DBConstant.CREATE_TABLE_IF_NOT_EXISTS +" "+DBConstant.LEAVE_BAlANCE_TABLE + " (" 
						+ DBConstant.TAKEN_LEAVE_ID + " serial primary key, "
						+ DBConstant.EMPLOYEE_ID + " int , " + DBConstant.TOTAL_LEAVE + " int not null, "
						+ DBConstant.UNUSED_LEAVE + " int not null, " + DBConstant.LEAVE_TYPE_ID + " int not null, " 
						+ DBConstant.FOREIGN_KEY +" (" + DBConstant.LEAVE_TYPE_ID +") "
				    		+ DBConstant.REFERENCES + DBConstant.LEAVE_TYPE_TABLE + " (" + DBConstant.LEAVE_TYPE_ID +"))";
			
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			System.out.println("  Can't create Leave Balance table ");
		}
	}
	
	
}

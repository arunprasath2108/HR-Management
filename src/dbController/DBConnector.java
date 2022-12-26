package dbController;

import java.sql.*;

public class DBConnector 
{
	
	 public static DBConnector connector = null;

	 private Connection connection;

	//constructor
	private DBConnector()
	{
		
		String url = "jdbc:postgresql://localHost:5432/"+DBConstant.DATABASE_NAME;
		String userName = "postgres";
		String pass = "12345";
		
		try 
		{
			try
			{
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, userName, pass);

			}
			catch (ClassNotFoundException e) 
			{
				System.out.println("  Can't Register Driver");
			}
		} 
		catch(SQLException e) 
		{
			System.out.println("  Can't establish connection");
		}
	}
	
	
	public static Connection getConnection()
	{
		if(connector == null)
		{
			connector = new DBConnector();
		}
		
		return connector.connection;
	}

	
	public static void closeConnection() 
	{
		try
		{
			connector.connection.close();
		} 
		catch (SQLException e)
		{
			System.out.println("  Can't Close the Connection");
		}
	}
	
}

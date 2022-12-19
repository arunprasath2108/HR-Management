package dbController;

import java.sql.*;
import java.util.*;
import model.*;

public class RoleDBController 
{

	static PreparedStatement statement;
	
	
	public static int getRoleID(int employeeID)
	{
		
		String query = DBConstant.SELECT + DBConstant.ROLE_ID +" "
						+ DBConstant.FROM + DBConstant.EMPLOYEE_TABLE +" "
						+ DBConstant.WHERE + DBConstant.ID + " = " + employeeID;

		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			int roleID = result.getInt(DBConstant.ROLE_ID);
			return roleID;
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getRoleName method  !");
		}
		
		return 0;
	}
	
	public static String getRoleName(int roleID)
	{
		
		String query = DBConstant.SELECT + DBConstant.ROLE_NAME +" "
						+ DBConstant.FROM + DBConstant.ROLE_TABLE +" "
						+ DBConstant.WHERE + DBConstant.ROLE_ID + " = " + roleID;
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			String role = result.getString(DBConstant.ROLE_NAME);
			return role;
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getRoleName method  !");
		}
		
		return null;
	}
	
	public static boolean isRoleIdPresent(int roleID)
	{
		
		String query = DBConstant.SELECT + DBConstant.ROLE_ID +" "+ DBConstant.FROM + DBConstant.ROLE_TABLE;
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				int id = result.getInt(DBConstant.ROLE_ID);
				
				if(id == roleID)
				{
					return true;
				}
			}
		} 
		
		catch (SQLException e) 
		{
			System.out.println(" Error occured in isTeamsPresent method  !\n");
		}
		
		return false;
	}
	
	public static boolean addRole(Role role)
	{
		
		String query = DBConstant.INSERT_INTO + DBConstant.ROLE_TABLE 
						+ " ( " + DBConstant.ROLE_NAME +", "+DBConstant.ROLE_PRIORITY +") "
						+ "values ('"+ role.getRoleName()+"', "+role.getRolePriority() + ")";

		try 
		{

			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
			return true;
		} 
		catch (SQLException e) 
		{
			return false;
		}
		
	}
	
	public static boolean isRolePresent(String roleName)
	{
		
		String query = DBConstant.SELECT + DBConstant.ROLE_NAME + " "
						+ DBConstant.FROM + DBConstant.ROLE_TABLE +" "
						+ DBConstant.WHERE + DBConstant.ROLE_NAME + " = '" + roleName +"'";
		
		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while(result.next())
			{
				String role = result.getString(DBConstant.ROLE_NAME);
				if(role.equalsIgnoreCase(roleName))
				{
					return true;
				}
			} 
			
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getRoleName method  !");
		}
		
		return false;
	}
	
	public static ArrayList<Role> listRole()
	{
		
		ArrayList<Role> roles = new ArrayList<>();
		Role role = null;
		String query = DBConstant.SELECT + " * "+ DBConstant.FROM + DBConstant.ROLE_TABLE 
						+ DBConstant.WHERE + DBConstant.ROLE_ID +"!= 1" + DBConstant.ORDER_BY + DBConstant.ROLE_PRIORITY;
		
		try 
		{
		
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				int roleID = result.getInt(DBConstant.ROLE_ID);
				String roleName = result.getString(DBConstant.ROLE_NAME);
				int priorityID = result.getInt(DBConstant.ROLE_PRIORITY);
				role = new Role(roleID, roleName, priorityID);
				roles.add(role);
			
			}
			return roles;
		} 
		catch (SQLException e) 
		{
			return null;
		}
	}
	
	public static ArrayList<Role> listRoleExceptPreviousRole(int previousID)
	{
		
		ArrayList<Role> roles = new ArrayList<>();
		Role role = null;
		String query = DBConstant.SELECT + " * "+ DBConstant.FROM + DBConstant.ROLE_TABLE 
						+ DBConstant.WHERE + DBConstant.ROLE_PRIORITY +"<"+previousID + DBConstant.AND + DBConstant.ROLE_ID + "!= 1" + DBConstant.ORDER_BY + DBConstant.ROLE_PRIORITY;
		
		try 
		{
		
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				int roleID = result.getInt(DBConstant.ROLE_ID);
				String roleName = result.getString(DBConstant.ROLE_NAME);
				int priorityID = result.getInt(DBConstant.ROLE_PRIORITY);
				role = new Role(roleID, roleName, priorityID);
				roles.add(role);
			
			}
			return roles;
		} 
		catch (SQLException e) 
		{
			return null;
		}
	}
	
	public static boolean isRolePriorityPresent(int roleID)
	{
		
		String query = DBConstant.SELECT + DBConstant.ROLE_PRIORITY +" "+ DBConstant.FROM + DBConstant.ROLE_TABLE;
		
		try 
		{
		
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				int id = result.getInt(DBConstant.ROLE_PRIORITY);
				
				if(id == roleID)
				{
					return true;
				}
			}
		} 
		
		catch (SQLException e) 
		{
			System.out.println(" Error occured in checking role priority method  !\n");
		}
		
		return false;
	}
	
	public static boolean changeRolePriority(int priorityID)
	{
		
		String query = DBConstant.UPDATE + DBConstant.ROLE_TABLE + DBConstant.SET 
						+ DBConstant.ROLE_PRIORITY + " = " + DBConstant.ROLE_PRIORITY+ "+1 " 
						+ DBConstant.WHERE + DBConstant.ROLE_PRIORITY + ">" + priorityID;

		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			statement.executeUpdate();
			return true;
		} 
		catch (SQLException e) 
		{
			return false;
		}
		
	}
	
	public static int isRoleAvailable()
	{
		
		int roleCount = 0;
		String query = DBConstant.SELECT + "count(*) " + DBConstant.FROM + "(" 
						+ DBConstant.SELECT + 1 + DBConstant.FROM + DBConstant.ROLE_TABLE + " limit 2 ) as RoleCount";
		
		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			
			roleCount = result.getInt(1); 	//if minimum one role is present in role table, it returns value 1
			 								//1 - column Index (count) 
		} 
		
		catch (SQLException e) 
		{
			System.out.println(" Error occured in isRoleAvailable method  !");
		}
		
		return roleCount;
		
	}
	
	public static int getRolePriority(int roleID)
	{
		
		String query = DBConstant.SELECT + DBConstant.ROLE_PRIORITY +" "
						+ DBConstant.FROM + DBConstant.ROLE_TABLE +" "
						+ DBConstant.WHERE + DBConstant.ROLE_ID + " = " + roleID;

		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			int priorityID = result.getInt(DBConstant.ROLE_PRIORITY);
			return priorityID;
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting priority id method  !");
		}
		
		return 0;
	}
	
	public static int higherRoleCount(int rolePriority)
	{
		
		String query = DBConstant.SELECT + " count(*) "
						+ DBConstant.FROM + DBConstant.ROLE_TABLE +" "
						+ DBConstant.WHERE + DBConstant.ROLE_PRIORITY + " < " + rolePriority + DBConstant.AND + DBConstant.ROLE_PRIORITY + " != 1";

		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			int priorityID = result.getInt(1);
			return priorityID;
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting higherRole count method  !");
		}
		
		return 0;
	}
	
	public static int getLeastRoleID()
	{
		
		String query = DBConstant.SELECT + DBConstant.ROLE_ID +" "
						+ DBConstant.FROM + DBConstant.ROLE_TABLE +" "
						+ DBConstant.ORDER_BY + DBConstant.ROLE_PRIORITY 
						+ DBConstant.DESC + DBConstant.LIMIT + " 1";

		try 
		{
			
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			int roleID = result.getInt(DBConstant.ROLE_ID);
			return roleID;
			
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getRoleName method  !");
		}
		
		return 0;
	}
	
	
	public static boolean setRoleID(int roleID, int employeeID)
	{
		
		String query = DBConstant.UPDATE + DBConstant.EMPLOYEE_TABLE +" "+ DBConstant.SET 
						+ DBConstant.ROLE_ID +" = "+roleID + DBConstant.WHERE + DBConstant.ID + " = "+employeeID;
		
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
			e.printStackTrace();
			System.out.println(" Error occured in setting Role ID !");
		}
		return false;
	}
	
	

}

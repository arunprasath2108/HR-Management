package dbController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Employee;
import model.WorkExperience;

public class WorkExperienceDBController 
{
	
	static PreparedStatement statement;

	
	public static boolean addWorkExperience(int employeeID, WorkExperience work)
	{
		
		String query = DBConstant.INSERT_INTO + DBConstant.WORK_EXPERIENCE_TABLE +" ("
						+DBConstant.EMPLOYEE_ID +", " + DBConstant.COMPANY_NAME + ", " + DBConstant.COMPANY_ROLE + ", " 
						+ DBConstant.YEARS_OF_EXPERIENCE + ") values (" + employeeID +", '" + work.getCompanyName() + "', '" 
						+ work.getRoleInCompany() + "' , '" + work.getExperience() + "' )"; 

		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			return (statement.executeUpdate() != 0);
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in adding in Work Experience!\n");
		}
		return false;
	}
	
	public static Employee getEmployee(int employeeID, Employee employee)
	{
		
		ArrayList<WorkExperience> workExperience = new ArrayList<>();
		
		String query = DBConstant.SELECT + " * "+ DBConstant.FROM 
				+ DBConstant.WORK_EXPERIENCE_TABLE +" "+ DBConstant.WHERE + DBConstant.EMPLOYEE_ID + " = " + employeeID;

		try 
		{
			statement = DBConnector.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				
				WorkExperience previousWork = new WorkExperience (result.getString(DBConstant.COMPANY_NAME),
																  result.getString(DBConstant.COMPANY_ROLE),
																  result.getString(DBConstant.YEARS_OF_EXPERIENCE));
				
				workExperience.add(previousWork);
			}
			employee.setWorkExperience(workExperience);
			return employee;
		} 
		catch (SQLException e) 
		{
			System.out.println(" Error occured in getting Employee Instance !");
		}
		return null;
	}
	

}

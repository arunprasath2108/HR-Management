package dbController;

public class DBConstant 
{
	
	public static final String DATABASE_NAME = "HR Management";

	
	//Employee Table Name, Column Name Constants
	public static final String EMPLOYEE_TABLE = "Employee";
	public static final String ID = "Id";
	public static final String NAME = "Name";
	public static final String REPORTING_ID = "Reporting_ID";
	public static final String WORK_LOCATION = "Work_Location";
	public static final String DOJ = "DOJ";
	public static final String MAIL_ID = "Company_Mail";
	public static final String GENDER = "Gender";

	
	//Teams Table Constants
	public static final String TEAMS_TABLE = "Teams";
	public static final String TEAM_ID = "Team_Id";
	public static final String TEAM_NAME = "Team_Name";
	
	
	//Role Table Constants
	public static final String ROLE_TABLE = "Role";
	public static final String ROLE_ID = "Role_ID";
	public static final String ROLE_NAME = "Role_Name";
	public static final String ROLE_PRIORITY = "Priority";
	
	
	//Work Location Constants
	public static final String WORK_LOCATION_TABLE = "Work_Location";
	public static final String LOCATION_ID = "Location_ID";
	public static final String LOCATION_NAME = "Location_Name";
	
	
	//Personal Information Table Constants
	public static final String PERSONAL_INFORMATION_TABLE = "Personal_Information";
	public static final String EMPLOYEE_ID = "Employee_ID";
	public static final String MOBILE = "Mobile";
	public static final String EMAIL_ID = "Email_ID";
	public static final String ADDRESS = "Address";
	public static final String DEGREE = "Degree";
	public static final String PASSED_OUT_YEAR = "Passed_Out_Year";
	
	
	//Work Experience Table Constants
	public static final String WORK_EXPERIENCE_TABLE = "Work_Experience";
	public static final String COMPANY_NAME = "Company_Name";
	public static final String COMPANY_ROLE = "Company_Role";
	public static final String YEARS_OF_EXPERIENCE = "Years_Of_Experience";
	
	
	

	//Query constants
	public static final String CREATE_TABLE_IF_NOT_EXISTS = "Create table if not exists ";
	public static final String CREATE_DATABASE = "Create database ";
	public static final String SELECT = "Select ";
	public static final String INSERT_INTO = "Insert into ";
	public static final String FROM = " From ";
	public static final String WHERE = " Where ";
	public static final String REFERENCES = " References ";
	public static final String LIMIT = " Limit ";
	public static final String UPDATE = " Update ";
	public static final String SET = " Set ";
	public static final String AND = " And ";
	public static final String DESC = " Desc ";
	public static final String ORDER_BY = " Order by ";
	public static final String ON = "On";
	
	
	
	
	//Query keys 
	public static final String PRIMARY_KEY = "Primary key";
	public static final String FOREIGN_KEY = "Foreign key";
	public static final String UNIQUE_KEY = "Unique key";
	
	//Joins
	public static final String INNER_JOIN = "Inner Join";
	

}
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
	public static final String COMPANY_MAIL = "Company_Mail";
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
	public static final String WORK_EXPERIENCE_ID = "Work_experience_id";
	public static final String COMPANY_NAME = "Company_Name";
	public static final String COMPANY_ROLE = "Company_Role";
	public static final String YEARS_OF_EXPERIENCE = "Years_Of_Experience";
	
	
	
	//Requests Table Constants
	public static final String TEAM_CHANGE_REQUEST_TABLE = "Team_Change_Request";
	public static final String REQUEST_ID = "Request_ID";
	public static final String REQUEST_BY = "Request_By";
	public static final String RECEIVER_ID = "Receiver_ID";
	public static final String REQUESTED_ON = "Requested_On";
	public static final String STATUS = "Status";
	
	
	
	//Notification Table Constants
	public static final String NOTIFICATION_TABLE = "Notification";
	public static final String NOTIFICATION_ID = "Notification_ID";
	public static final String NOTIFICATION = "Notification";
	public static final String NOTIFICATION_SEEN = "Notification_seen";
	public static final String NOTIFICATION_TIME = "Notification_Time";
	public static final String TRUE = "True";
	public static final String FALSE = "False";
	
	
	//Leave Management Table Constants
	public static final String LEAVE_MANAGEMENT_TABLE = "Leave_Management";
	public static final String LEAVE_ID = "Leave_ID";
	public static final String FROM_DATE = "From_date";
	public static final String TO_DATE = "To_date";
	public static final String REASON_FOR_LEAVE = "Reason_for_leave";
	public static final String REJECTED_REASON = "Rejected_Reason";
	//leave_type_id, 
	
	
	//Taken Leave Table Constants
	public static final String LEAVE_BAlANCE_TABLE = "Leave_Balance";
	public static final String TAKEN_LEAVE_ID = "Taken_Leave_ID";
	public static final String TOTAL_LEAVE = "Total_Leave";
	public static final String UNUSED_LEAVE = "Unused_Leave";
	
	
	//Leave Types Table Constants
	public static final String LEAVE_TYPE_TABLE = "Leave_Type";
	public static final String LEAVE_TYPE_ID = "Leave_type_ID";
	public static final String LEAVE_NAME = "Leave_Name";
	public static final String LEAVE_COUNT = "Leave_Count";

	
	
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
	public static final String DELETE = "Delete ";
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

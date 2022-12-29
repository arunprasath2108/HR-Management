package model;

import java.util.*;


public class Employee 
{
	
		//basic info fields
		private int employeeID;
		private String employeeName;
		private int roleID;
		private int reportingToID;
		private int employeeTeamID;
		private String companyMailId;
		private Date dateOfJoining;
		private int WorkLocationID;
		private String gender;
		
		//personal info fields
		private String emailID;
		private String mobileNumber;
		private String highestDegree;
		private String passedOutYear;
		private String address;
		private ArrayList<WorkExperience> workExperience;
		
		
		//constructor accepting all fields
		public Employee(int id, String name, int roleID, int reportingToID , int employeeTeamID, String companyMailId, Date doj, int locationID, String gender)
		{
			
			employeeID = id;
			employeeName = name;
			this.roleID = roleID;
			this.reportingToID = reportingToID;
			this.employeeTeamID = employeeTeamID;
			this.companyMailId = companyMailId;
			dateOfJoining = doj;
			WorkLocationID = locationID;
			this.gender = gender;
			
		}
		
		
		//constructor without employeeID parameter for initially adding employee
		public Employee(String name, int roleID, int reportingToID , int employeeTeamID, String companyMailId, Date doj, int locationID, String gender)
		{
			employeeName = name;
			this.roleID = roleID;
			this.reportingToID = reportingToID;
			this.employeeTeamID = employeeTeamID;
			this.companyMailId = companyMailId;
			dateOfJoining = doj;
			WorkLocationID = locationID;
			this.gender = gender;
		}
		
		
		
		
		
		//getters 
		
		public int getemployeeID()
		{
			return employeeID;
		}

		public String getemployeeName()
		{
			return employeeName;
		}
	
		public int getemployeeRoleID()
		{
			return roleID;
		}

		public int getEmployeeTeamID()
		{
			return employeeTeamID;
		}
				
		public int getReportingToID()
		{
			return reportingToID;
		}
		
		public int getWorkLocationID()
		{
			return WorkLocationID;
		}
		
		public Date getDateOfJoining()
		{
			return dateOfJoining;
		}
		
		public String getCompanyMailId()
		{
			return companyMailId;
		}
		
		public String getGender()
		{
			return gender;
		}
		
		public String getMobileNum()
		{
			return mobileNumber;
		}
		
		public String getEmailID()
		{
			return emailID;
		}
		
		public String getAddress()
		{
			return address;
		}
		
		public ArrayList<WorkExperience> getWorkExperience()
		{
			return workExperience;
		}
		
		public String getHighestDegree()
		{
			return highestDegree;
		}
		
		public String getPassedOutYear()
		{
			return passedOutYear;
		}
		
		
//----------------------------------------------------------------
		
		
		//setters
		
		public void setReportingToID(int id)
		{
			reportingToID = id;
		}
		
		public void setRoleID(int roleID)
		{
			this.roleID = roleID;
		}
		
		public int setWorkLocationID(int locationID)
		{
			return WorkLocationID;
		}
		
		public void setTeamID(int employeeTeamID)
		{
			this.employeeTeamID = employeeTeamID;
		}
		
		public void setMobileNum(String number)
		{
			mobileNumber = number;
		}
		
		public void setEmailID(String mail)
		{
			emailID = mail;
		}
		
		public void setAddress(String address)
		{
			this.address = address;
		}
		
		public void setWorkExperience(ArrayList<WorkExperience> work)
		{
			workExperience = work;
		}
		
		public void setHighestDegree(String education)
		{
			this.highestDegree = education;
		}
		
		public void setPassedOutYear(String year)
		{
			this.passedOutYear = year;
		}
		
		public void setTemporaryDateOfJoining(Date date)
		{
			this.dateOfJoining = date;
		}

}

package model;

import java.util.Date;

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
		private String education;
		private String workExperience;
		private String address;
		
		
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
		
		public String getWorkExperience()
		{
			return workExperience;
		}
		
		public String getEducation()
		{
			return education;
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
		
		public void setWorkExperience(String work)
		{
			workExperience = work;
		}
		
		public void setEducation(String education)
		{
			this.education = education;
		}
		
		
		
		
		
		
//		public void setemployeeID(int id)
//		{
//			employeeID = id;
//		}
		
		
//		public void setemployeeName(String name)
//		{
//			employeeName = name;
//		}
		
//		public void setemployeeRole(String role)
//		{
//			this.role = role;
//		}
		
//		public void setEmployeeTeamName(int teamID)
//		{
//			employeeTeamID = teamID;
//		}
		

		
//		public void setEmployeeWorkLocation(String location)
//		{
//			employeeWorkLocation = location;
//		}
		
//		public void setDateOfJoining(String doj)
//		{
//			dateOfJoining = doj;
//		}
		
//		public void setCompanyMailId(String mailID)
//		{
//			companyMailId = mailID;
//		}
		
//		public void setGender(String gender)
//		{
//			this.gender = gender;
//		}
		
		
		
		


}

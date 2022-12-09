package model;

public class Employee 
{
	
		//basic info fields
		private int employeeID;
		private String employeeName;
		private String role;
		private int reportingToID;
		private int employeeTeamID;
		private String companyMailId;
		private String dateOfJoining;
		private String employeeWorkLocation;
		private String gender;
		
		//personal info fields
		private String emailID;
		private String mobileNumber;
		private String education;
		private String workExperience;
		private String address;
		
		
		
		public Employee(int id, String name, String role, int reportingToID , int employeeTeamID, String companyMailId, String doj, String location, String gender)
		{
			
			employeeID = id;
			employeeName = name;
			this.role = role;
			this.reportingToID = reportingToID;
			this.employeeTeamID = employeeTeamID;
			this.companyMailId = companyMailId;
			dateOfJoining = doj;
			employeeWorkLocation = location;
			this.gender = gender;
			
		}
		
		
		
		
		public void setemployeeID(int id)
		{
			employeeID = id;
		}
		
		public int getemployeeID()
		{
			return employeeID;
		}
		
		public void setemployeeName(String name)
		{
			employeeName = name;
		}
		
		public String getemployeeName()
		{
			return employeeName;
		}
		
		public void setemployeeRole(String role)
		{
			this.role = role;
		}
		
		public String getemployeeRole()
		{
			return role;
		}
		
		public void setEmployeeTeamName(int teamID)
		{
			employeeTeamID = teamID;
		}
		
		public int getEmployeeTeamName()
		{
			return employeeTeamID;
		}
		
		public void setReportingToID(int id)
		{
			reportingToID = id;
		}
		
		public int getReportingToID()
		{
			return reportingToID;
		}
		
		public void setEmployeeWorkLocation(String location)
		{
			employeeWorkLocation = location;
		}
		
		public String getEmployeeWorkLocation()
		{
			return employeeWorkLocation;
		}
		
		public void setDateOfJoining(String doj)
		{
			dateOfJoining = doj;
		}
		
		public String getDateOfJoining()
		{
			return dateOfJoining;
		}
		
		public void setCompanyMailId(String mailID)
		{
			companyMailId = mailID;
		}
		
		public String getCompanyMailId()
		{
			return companyMailId;
		}
		
		public void setGender(String gender)
		{
			this.gender = gender;
		}
		
		public String getGender()
		{
			return gender;
		}
		
		public void setMobileNum(String number)
		{
			mobileNumber = number;
		}
		
		public String getMobileNum()
		{
			return mobileNumber;
		}
		
		public void setEmailID(String mail)
		{
			emailID = mail;
		}
		
		public String getEmailID()
		{
			return emailID;
		}
		
		public void setAddress(String address)
		{
			this.address = address;
		}
		
		public String getAddress()
		{
			return address;
		}
		
		public void setWorkExperience(String work)
		{
			workExperience = work;
		}
		
		public String getWorkExperience()
		{
			return workExperience;
		}
		
		public void setEducation(String education)
		{
			this.education = education;
		}
		
		public String getEducation()
		{
			return education;
		}


}
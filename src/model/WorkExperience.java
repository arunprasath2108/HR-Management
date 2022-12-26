package model;

public class WorkExperience 
{
	
	private String companyName;
	private String roleInCompany;
	private String yearsOfExperience;
	
	public WorkExperience(String companyName, String roleInCompany, String experience)
	{
		this.companyName = companyName;
		this.roleInCompany = roleInCompany;
		yearsOfExperience = experience;
	}

	
	public String getCompanyName()
	{
		return companyName;
	}
	
	public String getRoleInCompany()
	{
		return roleInCompany;
	}
	
	public String getExperience()
	{
		return yearsOfExperience;
	}
}

package model;

public class Role 
{
	
	private int roleID;
	private String roleName;
	private int rolePriority;
	
	
	//default constructor 
	public Role(int roleID, String roleName, int rolePriority)
	{
		this.roleID = roleID;
		this.roleName = roleName;
		this.rolePriority = rolePriority;
	}
	
	//constructor with only two parameters
	public Role(String roleName, int rolePriority)
	{
		
		this.roleName = roleName;
		this.rolePriority = rolePriority;
	}
	
	

	//getters
	
	public int getRoleID()
	{
		return roleID;
	}
	
	public String getRoleName()
	{
		return roleName;
	}
	
	public int getRolePriority()
	{
		return rolePriority;
	}
	

}

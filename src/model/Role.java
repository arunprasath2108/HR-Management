package model;

public class Role 
{
	
	private int roleID;
	private String roleName;
	private int rolePriority;
	
	
	public Role(int roleID, String roleName, int rolePriority)
	{
		this.roleID = roleID;
		this.roleName = roleName;
		this.rolePriority = rolePriority;
	}
	
	public Role(String roleName, int rolePriority)
	{
		
		this.roleName = roleName;
		this.rolePriority = rolePriority;
	}
	
	

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
	
	
	
//	public void setRoleID(int roleId)
//	{
//		roleID = roleId;
//	}
	
//	public void setRoleName(String roleName)
//	{
//		this.roleName = roleName;
//	}
	
//	public void setRolePriority(int priorityID)
//	{
//		rolePriority = priorityID;
//	}

}

package model;

public class WorkLocation 
{
	
	private int locationID;
	private String locationName;
	
	
	//default constructor
	public WorkLocation(int locationID, String locationName)
	{
		this.locationID = locationID;
		this.locationName = locationName;
	}
	
	
	//constructor with location name only
	public WorkLocation(String locationName)
	{
		this.locationName = locationName;
	}
	
	
	
	
	
	//getters
	
	public int getLocationID()
	{
		return locationID;
	}
	
	public String getLocationName()
	{
		return locationName;
	}
	

	
	

}

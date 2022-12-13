package utils;

public class EnumGender 
{
	
	 //enum for gender input
	 public enum Gender 
	 {
		 
	    	MALE(1), FEMALE(2), OTHERS(3);
		
		 	private int value;
			 
			private Gender(int value)
			{
			    this.value = value;
			}	
		 
			public int getValue() 
			{
			    return value;
		    }
			 
	 }

}

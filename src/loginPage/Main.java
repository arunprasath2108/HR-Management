package loginPage;


import controller.LoginController;
import dbController.*;


public class Main 
{
	
	
	public static void main(String[] args) 
	{
		
		//create tables in Database
		DBUtils.createTables();
		
		//main page
		LoginController.loginPage();
		
		//closes the connection object when not in use
		DBConnector.closeConnection();
		
	} //main method
	

}

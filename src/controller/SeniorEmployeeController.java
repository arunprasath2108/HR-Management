package controller;

import java.util.*;
import dbController.*;
import model.*;
import utils.*;
import viewController.*;

public class SeniorEmployeeController extends EmployeeController
{

	
	private static final int VIEW_REPORTEE = 5;
	private static final int APPROVE_LEAVE = 6;
	private static final int REQUEST_TEAM_CHANGE = 7;
	private static final int APPROVE_REJECT_TEAM_CHANGE = 8;
	private static final int NOTIFICATION = 9;
	private static final int LOG_OUT = 10;
	
	
	private static final int CHANGE_TEAM = 1;;
	private static final int BACK = 2;
	
	private static final int PROCESS_REQUESTS = 1;
	
	private static final int ACCEPT = 1;
	private static final int REJECT = 2;
	private static final int BACK_MENU = 3;


	
	SeniorEmployeeViewController seniorEmployeeView = new SeniorEmployeeViewController();
	


	
	//list  senior employee menu
	public void listEmployeeMenu(int employeeID)
	{
		
		EmployeeValidation.isProfileIncomplete(employeeID);

		employeeView.listEmployeeMenu(employeeID);
		
		int notificationCount = isNotificationAvailable(employeeID);
		int requestCount = requestCountForEmployee(employeeID);
		
		seniorEmployeeView.listSeniorEmployeeMenu(employeeID, notificationCount, requestCount);
		
		chooseOptionFromList(employeeID);
	}
	
	private int isNotificationAvailable(int employeeID)
	{
		return NotificationDBController.notificationCount(employeeID);
	}
	
	public int requestCountForEmployee(int employeeID)
	{
		
		int count = 0;
		ArrayList<Request>  requests = RequestDBController.getAllRequests(employeeID);
		
		for(Request request : requests)
		{
			if(request.getStatus().contains("pending"))
			{
				count++;
			}
		}
		
		return count;
	}
			
	
	public void viewTeam()
	{
		
		int teamID = getTeamID();
		
		seniorEmployeeView.viewTeam(teamID);
		
		
	}
	
	public int getTeamID()
	{
		
		employeeView.listTeam(TeamDBController.listTeam());
		int userInput = seniorEmployeeView.getTeamID();
		
		if(EmployeeValidation.isTeamIdPresent(userInput))
		{
			return userInput;
		}
		else
		{
			inputLimit++;
			if(inputLimit == 3)
			{
				inputLimit = 0;
				return 0;
			}
			Utils.printMessage(StringConstant.INVALID_INPUT);
			return getTeamID();
		}
	}
	
	
	
	
	private void chooseOptionFromList(int employeeID)
	{
		
		Employee employee = Utils.getEmployee(employeeID);
		
		Utils.printMessage(StringConstant.ENTER_OPTION);
		int userInput = seniorEmployeeView.getInputFromEmployee();
		
		switch(userInput)
		{
			
			case VIEW_REPORTEE : 
				viewReportee(employeeID);
				break;
			
			case APPROVE_LEAVE :  
//					System.out.println("apply leave");
				break;
				
			case REQUEST_TEAM_CHANGE :	
				teamChangeRequest(employee);
				break;
				
			case APPROVE_REJECT_TEAM_CHANGE :
				requestMessages(employeeID);
				break;
			
			case NOTIFICATION :
				notification(employeeID);
				break;
				
			case LOG_OUT :
				return;
				
			default :
				if(getInputFromEmployee(userInput, employee))
				{
					return;
				}
		}
	  listEmployeeMenu(employeeID);		
	}
	
	public void viewReportee(int employeeID)
	{
	
		ArrayList<Employee> reportees = EmployeeDBController.getReportee(employeeID);
		
		if(reportees.size() == 0)
		{
			Utils.printMessage("     No Reportees !!");
			Utils.printSpace();
		}
		else
		{
			seniorEmployeeView.viewReportees(reportees);
		}		
	}
	
	
	public void teamChangeRequest(Employee employee)
	{
		
		if(RequestDBController.isAlreadyRequestSent(employee.getemployeeID()))
		{
			Utils.printMessage("   ~ Your previous request is still pending....");
			Utils.printSpace();
			return;
		}
		
		ArrayList<Team> teams = TeamDBController.listTeamExceptCurrentTeam(employee.getEmployeeTeamID());
		
		if(teams.size() == 1)
		{
			Utils.printMessage(StringConstant.ONE_TEAM_ONLY_PRESENT);
			return;
		}
		
		int userInput = seniorEmployeeView.employeeChoice();
		
		switch(userInput)
		{
    		case CHANGE_TEAM :
    			chooseTeam(employee, teams);
    			break;
    			
    		case BACK :
    			break;
    			
			default :
				Utils.printMessage(StringConstant.INVALID_INPUT);
				
				if( !inputLimitReached(StringConstant.CANT_CHANGE_TEAM))
				{
					teamChangeRequest(employee);
				}
		}
	}
	
	
	private void chooseTeam(Employee employee, ArrayList<Team> teams)
	{
		
		employeeView.listTeam(teams);
		int userInput = seniorEmployeeView.getTeamID();
		
		if(TeamDBController.isTeamPresent(userInput))
		{
			if(userInput != employee.getEmployeeTeamID())
			{
				sendRequest(employee, userInput);
			}
			else
			{
				Utils.printMessage("  ~ You are already in "+TeamDBController.getTeamName(employee.getEmployeeTeamID()));
			}
		}
		else
		{
			Utils.printMessage(StringConstant.INVALID_TEAM_ID);
			
			if( !inputLimitReached(StringConstant.CANT_CHANGE_TEAM))
			{
				chooseTeam(employee, teams);
			}
		}
   }
	
	
	private void sendRequest(Employee employee, int newTeamID)
	{
		
		Request request = new Request(employee.getemployeeID(), employee.getReportingToID(), newTeamID , "pending");
		
		int requestID = RequestDBController.setRequestAndReturnRequestID(request);
		if(requestID != 0 )
		{
			setNotification(employee.getemployeeID(), "TC"+requestID+" - Request sent to your Reporting person ", false);
			Utils.printMessage("     ~ Request sent ");
		}
		else
		{
			Utils.printMessage("    ~ Request sent Failed... ");
		}
	}
	
	private void setNotification(int employeeID, String message, boolean isMessageSeen)
	{
		if( !NotificationDBController.setNotification(employeeID, message, isMessageSeen))
		{
			Utils.printMessage("  Error in sending Notification !!!");
		}
	}
	
	
	private void requestMessages(int employeeID)
	{

		//write condition to check lower employee, not to have access to approve/reject
		
		ArrayList<Request>  requests = RequestDBController.getAllRequests(employeeID);
		
		if(requests.isEmpty())
		{
			Utils.printMessage(StringConstant.NO_REQUESTS);
			return;
		}
		
		seniorEmployeeView.displayRequests(requests);
		replyRequest(employeeID);
	
	}
	
	
	private void replyRequest(int employeeID)
	{
		
		int userInput = seniorEmployeeView.getRequestInput();
		
		switch(userInput)
		{
			case PROCESS_REQUESTS :
				processRequest(employeeID);
				break;
				
			case BACK :
				return;
				
			default :
				if( !inputLimitReached(StringConstant.CANT_PROCESS_TEAM_CHANGE))
				{
					Utils.printMessage(StringConstant.INVALID_INPUT);
					replyRequest(employeeID);
				}
		}
	}
	
	private void processRequest(int employeeID)
	{
		
		int requestID = seniorEmployeeView.getRequestID();
		
		if(RequestDBController.isRequestPresent(requestID, employeeID))
		{
			//get the specific message for confirmation
			Request request = RequestDBController.getSpecificRequest(requestID, employeeID);
			
			ArrayList<Request> requests = new ArrayList<>();
			requests.add(request);
			
			seniorEmployeeView.displayRequests(requests);
			handleRequest(requestID, request.getRequestBy());
		}
		else
		{
			if( !inputLimitReached(StringConstant.CANT_PROCESS_TEAM_CHANGE))
			{
				Utils.printMessage(" Enter valid Request ID ");
				processRequest(employeeID);
			}
		}
		
	}
	
	private void handleRequest(int requestID, int requestedBy)
	{
		
		int decision = seniorEmployeeView.getRequestDecisionInput();
		
		switch(decision)
		{
			case ACCEPT :
				acceptRequest(requestID, requestedBy);
				break;
				
			case REJECT :
				rejectRequest(requestID, requestedBy);
				break;
				
			case BACK_MENU :
				return;
				
			default :
				if( !inputLimitReached(StringConstant.CANT_PROCESS_TEAM_CHANGE))
				{
					Utils.printMessage(StringConstant.INVALID_INPUT);
					handleRequest(requestID, requestedBy);
				}
		}
	}
	
	private void acceptRequest(int requestID, int requestedBy)
	{
		
		if(RequestDBController.acceptRequest(requestID, "Waiting for HR Approval"))
		{
			
			int notificationID = NotificationDBController.getNotificationID(requestID, requestedBy);
			String notification = " TC"+requestID+ " - " + StringConstant.REQUEST_FORWARDED_TO_HR;
			changeNotification(notificationID, notification, false);
			Utils.printMessage(StringConstant.REQUEST_FORWARDED_TO_HR);
		}
		else
		{
			Utils.printMessage(" Can't process Request ");
		}
		
	}
	
	private void changeNotification(int notificationID, String notification, boolean setMessageNotSeen)
	{
		
		if( !NotificationDBController.changeNotification(notificationID, notification, setMessageNotSeen))
		{
			Utils.printMessage(" Can't change Notification ! ");
		}
		
	}
	
	
	private void rejectRequest(int requestID, int requestedBy)
	{
		
		if(RequestDBController.rejectRequest(requestID))
		{
			int notificationID = NotificationDBController.getNotificationID(requestID, requestedBy);
			String notification = " TC"+requestID+ " - " + StringConstant.REQUEST_REJECTED;
			changeNotification(notificationID, notification, false);
			RequestDBController.deleteRequest(requestID);
			Utils.printMessage("   TC" + requestID + " - " + StringConstant.REQUEST_REJECTED + " !\n");
		}
		else
		{
			Utils.printMessage(" Can't reject this Request\n");
		}
	}
	
	
	private void notification(int employeeID)
	{
		
		ArrayList<Notification> notifications = NotificationDBController.getNotification(employeeID);

		if(notifications.isEmpty())
		{
			Utils.printMessage("  ~ Notification Box is Empty");
			return;
		}
		
		seniorEmployeeView.displayNotification(notifications);
		
		//change notification as seen
		NotificationDBController.changeNotificationSeen(employeeID);
	}
	
	
	boolean inputLimitReached(String message)
	{
		
		inputLimit++;
		
		if(inputLimit == 3)
		{
			inputLimit = 0;
			Utils.printMessage(message);
			return true;
		}
		return false;
	}

	
//	private  void processInbox(Employee employee)
//	{
//		int requestedID, checker = 0, senderID;
//		System.out.println(" Choose Index number.");
//		Utils.printSpace();
//		
//		try
//		{
//			
//			int userInput = Utils.getIntInput();
//			userInput--;
//			Utils.printSpace();
//			
//			for( String messages : employee.getRequests())
//			{
//				
//				if(messages.indexOf(messages) == userInput)
//				{
//					checker = 1;
//					String[] splitMessage = messages.split("-");
//					senderID = Integer.parseInt(splitMessage[0]); 
//					requestedID = Integer.parseInt(splitMessage[2]);
//					 
//					proceedMessage(employee, userInput, requestedID, senderID, messages );
//					break;
//				}
//			}
//			
//			if(checker == 0)
//			{
//				System.out.println(" Please, Enter a Valid Index Number. ");
//				Utils.printSpace();
//				processInbox(employee);
//				return;
//				
//			}
//			
//		}
//		catch(InputMismatchException e)
//		{
//			
//			Utils.printInvalidInputMessage();
//			Utils.scanner.nextLine();
//			processInbox(employee);
//			return;
//			
//		}
//	}
//
//
//	private  void proceedMessage(Employee employee, int indexOfMessage, int requestID, int senderId, String message) 
//	{
//		
//		try
//		{
//			System.out.println(" Choose a option :");
//			Utils.printSpace();
//			System.out.println(" 1. Change Team");
//			System.out.println(" 2. Back.");
//			Utils.printSpace();
//			int userInput = Utils.getIntInput();
//			Utils.printSpace();
//			switch(userInput)
//			{
//				case PROCESS_REQUEST :
//					processRequest(employee, message, requestID, senderId, indexOfMessage );
//					break;
//					
//				case IGNORE_MESSAGE :
//					break;
//					
//					default :
//						Utils.printInvalidInputMessage();
//						requestMessages(employee);
//						return;
//			}
//			
//		}
//		catch(InputMismatchException e)
//		{
//			Utils.printInvalidInputMessage();
//			Utils.scanner.nextLine();
//			requestMessages(employee);
//			return;
//		}	
//		
//	}
//
//	
//	private void processRequest(Employee employee, String message, int requestID, int senderId , int indexOfMessage )
//	{
//		
//		String[] getTeamName = message.split("-");
//		String teamName = getTeamName[3];
//		editTeamName(requestID, teamName, employee);
//		employee.getRequests().remove(indexOfMessage);
//		
//		//get sender employee object
//		Employee sender = Utils.getEmployeeObject(senderId);
//		//get requester employee object
//		Employee requestBy = Utils.getEmployeeObject(requestID);
//		String requesterName = Utils.getEmployeeName(requestID);
//		
//		sender.setNotificationSeen(false);
//		String messageSender = " ~ Team changed Successfully for \""+requesterName.toUpperCase()+"\"    "+Utils.getCurrentDateTime();
//		sender.getNotification().replace(requestBy.getemployeeID(), messageSender );
//		
//		requestBy.setNotificationSeen(false);
//		String messageRequester = " ~ Your Team changed Successfully to ["+teamName+"]       "+Utils.getCurrentDateTime();
//		requestBy.getNotification().replace(requestBy.getemployeeID(), messageRequester);
//		requestBy.setTeamChanged(false);
//		
//	}
//	private  void replyRequestMessages(Employee employee) 
//	{
//		
//		Utils.printSpace();
//		
//		try
//		{
//			
//			System.out.println(" 1. Reply.");
//			System.out.println(" 2. Back.");
//			Utils.printSpace();
//			System.out.println(" Choose a option.");
//			Utils.printSpace();
//			int userInput = Utils.getIntInput();
//			Utils.printSpace();
//			
//			switch(userInput)
//			{
//				
//				case REPLY :
//					processMessage(employee);
//					break;
//					
//				case BACK :
//					break;
//					
//				default :
//					Utils.printInvalidInputMessage();
//					replyRequestMessages(employee);
//					 return;
//			}
//			
//		}
//		catch(InputMismatchException e)
//		{
//			Utils.printInvalidInputMessage();
//			Utils.scanner.nextLine();
//			replyRequestMessages(employee);
//			return;
//		}
//		
//		
//	}
//	
//	
//	private  void processMessage(Employee employee)
//	{
//		
//		Utils.printSpace();
//		System.out.println(" Choose Index number to Reply.");
//		Utils.printSpace();
//		
//		try
//		{
//			int userInput = Utils.getIntInput();
//			userInput--;     //matches userInput with index of ArrayList index
//			
//			if(userInput < 0)
//			{
//				processMessage(employee);
//				return;
//			}
//			Utils.printSpace();
//			
//			for ( String message : employee.getRequests())
//			{
//					if( message.indexOf(message) == userInput)
//					{
//						confirmMessageBeforeReply(message, employee, userInput);	
//						break;
//					}
//					else
//					{
//						Utils.printInvalidInputMessage();
//						processMessage(employee);
//						return;
//					}
//				
//			}
//			
//		}
//		catch(InputMismatchException e)
//		{
//			Utils.printInvalidInputMessage();
//			Utils.scanner.nextLine();
//			processMessage(employee);
//			return;
//		}
//		
//		
//	}
//
//
//	private  void confirmMessageBeforeReply( String message, Employee employee, int userInput)
//	{
//		
//		String[] splitMessage = message.split("-");
//		int senderID = Integer.parseInt(splitMessage[0]);  
//		String senderName = Utils.getEmployeeName(senderID);  
//		String requestMessage = splitMessage[1];	
//		String teamName = splitMessage[3];
//		
//		
//		Utils.printLine();
//		System.out.println("  From : "+senderName);
//		Utils.printSpace();
//		System.out.println("      "+requestMessage+" ["+teamName+"] ");
//		
//		Utils.printSpace();
//		Utils.printLine();
//		Utils.printSpace();
//		Utils.printSpace();
//		
//		System.out.println(" 1. Proceed to HR.");
//		System.out.println(" 2. Reject.");
//		Utils.printSpace();
//		
//		try
//		{
//			int input = Utils.getIntInput();
//			
//			switch( input )
//			{
//				
//				case PROCEED_TO_HR :
//					forwardRequestToHR(message, employee);
//					employee.getRequests().remove(userInput);
//					break;
//					
//				case REJECT :
//					rejectRequest(employee, message);
//					employee.getRequests().remove(userInput);
//					break;
//					
//				default :
//					Utils.printInvalidInputMessage();
//					processMessage(employee);
//					return;
//					
//			}
//			
//			
//		}
//		catch(InputMismatchException e)
//		{
//			Utils.printInvalidInputMessage();
//			Utils.scanner.nextLine();
//			processMessage(employee);
//			return;
//		}
//		
//	}
//
//	
//	private  void rejectRequest(Employee employee, String message) 
//	{
//		
//		String[] splitMessage = message.split("-");
//		int receiverID = Integer.parseInt(splitMessage[0]);  
//		
//		for( Employee employeee : Resource.employees)
//		{
//			
//			if(employeee.getemployeeID() == receiverID)
//			{
//				String notification = " ~ Contact your Team Head for Further Reference about Team Change    "+Utils.getCurrentDateTime();
//				employeee.getNotification().replace(receiverID, notification);
//				Utils.printSpace();
//				employeee.setTeamChanged(false);
//				employeee.setNotificationSeen(false);
//				System.out.println("      ~ Message Sent !!");
//				Utils.printSpace();
//				break;
//			}
//			
//		}
//
//		
//	}
//
//
//	private void forwardRequestToHR(String message, Employee  sender) 
//	{
//		
//		int senderID = sender.getemployeeID();
//		String[] splitMessage = message.split("-");
//		int requestedPerson = Integer.parseInt(splitMessage[2]);
//		String requestPersonName = Utils.getEmployeeName(requestedPerson);
//		
//		//changing sender ID. 
//		splitMessage[0] = Integer.toString(senderID);
//		
//		StringBuilder requestMessage = new StringBuilder();
//		
//		for( String msg : splitMessage)
//		{
//			requestMessage.append(msg);
//			requestMessage.append("-");
//		}
//		requestMessage.append(Utils.getCurrentDateTime());
//		
//		//forward message to HR
//		for( Employee employee : Resource.employees)
//		{
//			if(employee.getemployeeRole() == Role.HR)
//			{
//				employee.getRequests().add(requestMessage.toString());
//				Utils.printSpace();
//				System.out.println("     ~ Request sent to HR");
//				Utils.printSpace();
//				
//				//notify the sender about this request.
//				String notification = " ~ Waiting for HR Acceptance for \""+requestPersonName.toUpperCase()+"\" Team Change      "+Utils.getCurrentDateTime();
//				sender.getNotification().put(requestedPerson, notification);
//				sender.setNotificationSeen(false);
//				break;
//			}
//		}
//		
//		//send notification to requested employee about the process.
//		sendNotificationToRequestPerson(requestedPerson);
//
//	}
//	
//	private void sendNotificationToRequestPerson(int requestedPerson)
//	{
//		
//		for( Employee senderEmployee : Resource.employees)
//		{
//			if(senderEmployee.getemployeeID() == requestedPerson)
//			{
//				String notification = "  ~ Your Team Change Request waiting for HR Approval      "+Utils.getCurrentDateTime();
//				senderEmployee.getNotification().replace(requestedPerson, notification);
//				senderEmployee.setNotificationSeen(false);
//				Utils.printSpace();
//				break;
//			}
//		}
//	}
//	
//	public  void requestMessages(Employee employee)
//	{
//		
//		//condition to check MTS cannot Accept/reject team change request.
//		if(employee.getemployeeRole().getValue() == Role.MTS.getValue())
//		{
//			Utils.printSpace();
//			System.out.println(" You don't have access to Approve / Reject Team Change.");
//			Utils.printSpace();
//			return;
//		}
//		
//		if(employee.getRequests().isEmpty())
//		{
//			Utils.printSpace();
//			System.out.println("  ~ No Requests !");
//			Utils.printSpace();
//			return;
//		}
//		
//		Utils.printSpace();
//		printRequestMessages(employee);
//		
//		Utils.printSpace();
//		replyRequestMessages(employee);
//		
//	}

//	private void printNotification(Employee employee)
//	{
//		
//		if(employee.getNotification().isEmpty())
//		{
//			System.out.println("    No Messages..!");
//			Utils.printSpace();
//		}
//		
//		employee.setNotificationSeen(true);
//		for( Entry<Integer, String> messages : employee.getNotification().entrySet())
//		{
//			System.out.println(" "+messages.getValue());
//			Utils.printSpace();
//		}
//		
//	}

	
	
	
	
	
	
	
	
	
	
}

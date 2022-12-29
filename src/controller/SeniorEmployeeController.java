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
		return NotificationDBController.getNotificationCount(employeeID);
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
				processLeave(employeeID);
				break;
				
			case REQUEST_TEAM_CHANGE :	
				teamChangeRequest(employee);
				break;
				
			case APPROVE_REJECT_TEAM_CHANGE :
				requestMessages(employeeID);
				break;
			
			case NOTIFICATION :
				viewNotification(employeeID);
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
	
	
	private void requestMessages(int employeeID)
	{

		//write condition to check lower employee, not to have access to approve/reject
		
		ArrayList<Request>  requests = RequestDBController.getAllRequests(employeeID);
		
		if(requests.isEmpty())
		{
			Utils.printMessage(StringConstant.NO_REQUESTS);
			return;
		}
		
		int checker = 0;
		for(Request request : requests)
		{
			if(request.getStatus().equalsIgnoreCase(StringConstant.PENDING))
			{
				checker = 1;
				seniorEmployeeView.displayRequests(requests);
				break;
			}
		}
		
		if(checker == 1)
		{
			replyRequest(employeeID);
		}
		else
		{
			Utils.printMessage(StringConstant.NO_REQUESTS);
		}
	
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
			handleRequest(requestID, request.getRequestBy(), employeeID);
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
	
	private void handleRequest(int requestID, int requestedBy, int employeeID)
	{
		
		int decision = seniorEmployeeView.getRequestDecisionInput();
		
		switch(decision)
		{
			case ACCEPT :
				acceptRequest(requestID, requestedBy, employeeID);
				break;
				
			case REJECT :
				rejectRequest(requestID, requestedBy, employeeID);
				break;
				
			case BACK_MENU :
				return;
				
			default :
				if( !inputLimitReached(StringConstant.CANT_PROCESS_TEAM_CHANGE))
				{
					Utils.printMessage(StringConstant.INVALID_INPUT);
					handleRequest(requestID, requestedBy, employeeID);
				}
		}
	}
	
	private void acceptRequest(int requestID, int requestedBy, int employeeID)
	{
		
		if(RequestDBController.acceptRequest(requestID, "Waiting for HR Approval"))
		{
			
			int notificationID = NotificationDBController.getNotificationID(requestID, requestedBy);
			String notification = " TC"+requestID+ " - " + StringConstant.REQUEST_FORWARDED_TO_HR;
			
			//change notification for request by employee
			changeNotification(notificationID, notification, false);
			
			//add notification for employee who process request
			setNotification(employeeID, notification, false);
			
			Utils.printMessage(StringConstant.REQUEST_FORWARDED_TO_HR);
		}
		else
		{
			Utils.printMessage(" Can't process Request ");
		}
		
	}
	
	
	
	private void rejectRequest(int requestID, int requestedBy, int employeeID)
	{
		
		if(RequestDBController.rejectRequest(requestID))
		{
			int notificationID = NotificationDBController.getNotificationID(requestID, requestedBy);
			String notification = " TC"+requestID+ " - " + StringConstant.REQUEST_REJECTED;
			
			//change notification for request by employee
			changeNotification(notificationID, notification, false);
			
			//add notification for employee who process request
			setNotification(employeeID, notification, false);
			
			RequestDBController.deleteRequest(requestID);
			Utils.printMessage("   TC" + requestID + " - " + StringConstant.REQUEST_REJECTED + " !\n");
		}
		else
		{
			Utils.printMessage(" Can't reject this Request\n");
		}
	}
	
	
	private void viewNotification(int employeeID)
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
	
	
	private void processLeave(int employeeID)
	{
		
		ArrayList<LeaveManagement> leaveReport = LeaveManagementDBController.getLeaveRequests(employeeID);
		
		if(leaveReport.size() == 0)
		{
			Utils.printMessage("  No Leave requests !!");
			return;
		}
		
		seniorEmployeeView.printLeaveRequest(leaveReport);
		
		int leaveID = seniorEmployeeView.getLeaveID();
		
		//jack
//		for(LeaveManagement leave : leaveReport)
//		{
//			if( leave.getleaveID() == leaveID)
//			{
//				if( !inputLimitReached(StringConstant.ENTER_VALID_LEAVE_ID))
//				{
//					Utils.printMessage(StringConstant.INVALID_LEAVE_ID);
//					processLeave(employeeID);
//				}
//				return;
//			}
//		}
		
		LeaveManagement leaveRequest = LeaveManagementDBController.returnLeaveReportIfLeaveIdPresent(leaveID, employeeID);
		
		if(leaveRequest == null)
		{
			if( !inputLimitReached(StringConstant.ENTER_VALID_LEAVE_ID))
			{
				Utils.printMessage(StringConstant.INVALID_LEAVE_ID);
				processLeave(employeeID);
			}
			return;
		}
		
		leaveReport.removeAll(leaveReport);
		leaveReport.add(leaveRequest);
		seniorEmployeeView.printLeaveRequest(leaveReport);
		proceedLeaveRequest(leaveRequest);

	}
	
	private void proceedLeaveRequest(LeaveManagement leaveRequest)
	{
		
		int decision = seniorEmployeeView.getRequestDecisionInput();
		
		switch(decision)
		{
			case ACCEPT :
				approveLeave(leaveRequest);
				break;
				
			case REJECT :
				rejectLeave(leaveRequest);
				break;
				
			case BACK_MENU :
				return;
				
			default :
				if( !inputLimitReached("  Can't approve / reject leave request"))
				{
					Utils.printMessage(StringConstant.INVALID_INPUT);
					proceedLeaveRequest(leaveRequest);
				}
		}
	}
	
	private void approveLeave(LeaveManagement leaveRequest)
	{
		
		if(LeaveManagementDBController.approveOrRejectLeave(leaveRequest.getleaveID(), "approved", null))
		{
			
			Utils.printMessage("   Leave request accepted");
		}
		else
		{
			Utils.printMessage("   Can't accept leave request");
		}
	}
	
	private void rejectLeave(LeaveManagement leaveRequest)
	{
		
		String reasonForReject = seniorEmployeeView.getReasonForReject();
		if(LeaveManagementDBController.approveOrRejectLeave(leaveRequest.getleaveID(), "rejected", reasonForReject))
		{
			changeLeaveBalanceForRejectedLeave(leaveRequest);
			Utils.printMessage("   Rejected leave request");
		}
		else
		{
			Utils.printMessage("   Can't reject leave request");
		}
	}
	
	
	
	
	private void changeLeaveBalanceForRejectedLeave(LeaveManagement leaveRequest)
	{
		
		Date fromDate = leaveRequest.getfromDate();
		Date toDate = leaveRequest.getToDate();
		
		int numberOfDaysApplied = (int) Utils.getDifferenceBetweenTwoDates(toDate, fromDate);
		
		//for one day leave, it return 0 -> so, numberOfDaysApplied++;
		numberOfDaysApplied++;
		
		LeaveBalance leave = LeaveBalanceDBController.getLeaveBalance(leaveRequest.getLeaveTypeID(), leaveRequest.getRequestBy());
		
		int addRejectedDaysToUnusedLeave = leave.getUnusedLeave() + numberOfDaysApplied;
		
		if( !LeaveBalanceDBController.changeLeaveBalance(leaveRequest.getLeaveTypeID(), leaveRequest.getRequestBy(), addRejectedDaysToUnusedLeave))
		{
			Utils.printMessage("  can't add back leave count for rejected leave ");
		}
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
	
}

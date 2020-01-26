package simpleLib;

import java.util.Calendar;

public class LoanTransaction {
	private User userObj;
	private Document docObj;
	private String dueDate;
	
	//constructor
	public LoanTransaction(User userObj, Document docObj) throws Exception{
		this.userObj = userObj;
		this.docObj = docObj;
		
		if(docObj.getCopies() < 1)
			throw new Exception("Book Not Available!!");
		
		if(userObj.getItemsBorrowed() == userObj.getItemsBorrowable())
			throw new Exception("Borrowing Limit Reached!!");
		
		if(userObj == null || docObj == null)
			throw new Exception("Transaction Not Allowed!!");
		
		Calendar curDate = Calendar.getInstance();
		curDate.add(Calendar.DAY_OF_MONTH, docObj.getLoanTerm(userObj.getUserType()));
		String currentDate = curDate.getTime().toString();
		
		dueDate = "";
		switch(currentDate.substring(4, 7)){
		case "Jan":
			dueDate += "01/";
			break;
		case "Feb":
			dueDate += "02/";
			break;
		case "Mar":
			dueDate += "03/";
			break;
		case "Apr":
			dueDate += "04/";
			break;
		case "May":
			dueDate += "05/";
			break;
		case "Jun":
			dueDate += "06/";
			break;
		case "Jul":
			dueDate += "07/";
			break;
		case "Aug":
			dueDate += "08/";
			break;
		case "Sep":
			dueDate += "09/";
			break;
		case "Oct":
			dueDate += "10/";
			break;
		case "Nov":
			dueDate += "11/";
			break;
		case "Dec":
			dueDate += "12/";
			break;
		default:
			break;
		}
		
		dueDate += currentDate.substring(8, 10) + "/" + currentDate.substring(24);
		
		System.out.println("Transaction Successful!!\nDue Date: " + dueDate);
	}
	
	//helper
	public String getRegisterString(){
		return userObj.getID() + "\t" + docObj.getTitle() + "\t" + dueDate;
	}
	
}

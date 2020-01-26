package simpleLib;

public class User {
	private String name;
	private long id;
	private String userType;
	private int numberOfItemsBorrowed;
	private int maxItemsBorrowable;
	
	//constructor
	public User(String name, long id, String userType){
		this.name = name;
		this.id = id;
		this.userType = userType;
		switch(userType.trim()){
		case "Student":
		case "student":
			maxItemsBorrowable = 6;
			break;
		case "Librarian":
		case "librarian":
		case "Faculty":
		case "faculty":
		case "Staff":
		case "staff":
			maxItemsBorrowable = 12;
			break;
		default:
			System.out.println("Unknown User Type... Can't borrow books!!");
			maxItemsBorrowable = 0;
			break;
		}
	}
	
	//accessors
	public String getName(){
		return name;
	}
	
	public long getID(){
		return id;
	}
	
	public String getUserType(){
		return userType;
	}
	
	public int getItemsBorrowed(){
		return numberOfItemsBorrowed;
	}
	
	public int getItemsBorrowable(){
		return maxItemsBorrowable;
	}
	
	//mutators
	public void setName(String name){
		this.name = name;
	}
	
	public void setID(long id){
		this.id = id;
	}
	
	public void setUserType(String userType){
		this.userType = userType;
		switch(userType.trim()){
		case "Student":
		case "student":
			maxItemsBorrowable = 6;
			break;
		case "Faculty":
		case "faculty":
		case "Staff":
		case "staff":
			maxItemsBorrowable = 12;
			break;
		default:
			System.out.println("Unknown User Type... Can't borrow books!!");
			maxItemsBorrowable = 0;
			break;
		}
	}
	
	public void setItemsBorrowed(int items){
		this.numberOfItemsBorrowed = items;
	}

	//helper
	@Override
	public String toString(){
		return name + "\t" + id + "\t" + userType + "\t" + numberOfItemsBorrowed;
	}

}

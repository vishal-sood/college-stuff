package simpleLib;

public class Document {
	private String title;
	private String publisher;
	private int numberOfCopies;
	
	private final int loanTermForStudent = 180;
	private final int loanTermForFaculty = 365;
	
	//constructor
	public Document(String title, String publisher){
		this.title = title;
		this.publisher = publisher;
		this.numberOfCopies = 1;
	}
	
	public Document(String title, String publisher, int copies){
		this.title = title;
		this.publisher = publisher;
		this.numberOfCopies = copies;
	}
	
	//accessors	
	public String getTitle(){
		return title;
	}
	
	public String getPublisher(){
		return publisher;
	}
	
	public int getLoanTerm(String userType){
		int term = (userType.equals("Student") || userType.equals("student"))? loanTermForStudent : loanTermForFaculty;
		return term;
	}
	
	public int getCopies(){
		return this.numberOfCopies;
	}
	
	//mutators
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setPublisher(String publisher){
		this.publisher = publisher;
	}
	
	public void setCopies(int copies){
		this.numberOfCopies = copies;
	}
	
}

package simpleLib;

import java.util.ArrayList;

public class ConferenceProceeding extends Book {
	private String location;
	private String conferenceDate;
	
	//constructor
	public ConferenceProceeding(String title, String publisher, String date, long isbn, int copies, String authors, String location, String conferenceDate){
		super(title, publisher, date, isbn, copies, authors);
		this.location = location;
		this.conferenceDate = conferenceDate;
	}
	
	//accessors
	public String getLocation(){
		return location;
	}
	
	public String getConferenceDate(){
		return conferenceDate;
	}
	
	//mutators
	public void setLocation(String location){
		this.location = location;
	}
	
	public void setConferenceDate(String conferenceDate){
		this.conferenceDate = conferenceDate;
	}
	
	//helper
	@Override
	public String toString(){
		String authorList = "";
		ArrayList<String> temp = getAuthors();
		
		if(temp.size() > 0){
			authorList = temp.get(0);
			
			for(int i=1; i<temp.size(); i++){
				authorList += "," + temp.get(i);
			}
		}
		
		return getTitle() + "\t" + getPublisher() + "\t" + getDate() + "\t" + getISBN() + "\t" + getCopies() + " \t" + authorList + "\t" + location + "\t" + conferenceDate;
		
	}

}

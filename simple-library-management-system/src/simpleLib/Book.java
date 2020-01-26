package simpleLib;

import java.util.ArrayList;

public class Book extends Document {
	private String date;
	private long isbn;
	private ArrayList<String> authors;
	
	//constructor
	public Book(String title, String publisher, String date, long isbn, int copies, String authors){
		super(title, publisher, copies);
		
		this.date = date;
		this.isbn = isbn;
		
		this.authors = new ArrayList<String>(); 
		String[] authorList = authors.split(",");
		for(String author: authorList)
			this.authors.add(author.trim());
	}
	
	//accessors
	public String getDate(){
		return date;
	}
	
	public long getISBN(){
		return isbn;
	}
	
	public ArrayList<String> getAuthors(){
		return authors;
	}
	
	//mutators
	public void setDate(String date){
		this.date = date;
	}
	
	public void setISBN(long isbn){
		this.isbn = isbn;
	}
	
	public void setAuthors(String authors){
		String[] authorList = authors.split(",");
		for(String author: authorList)
			this.authors.add(author.trim());
	}
	
	//helper
	@Override
	public String toString(){
		String authorList = "";
		
		if(authors.size() > 0){
			authorList = authors.get(0);
			
			for(int i=1; i<authors.size(); i++){
				authorList += "," + authors.get(i);
			}
		}
		
		return getTitle() + "\t" + getPublisher() + "\t" + date + "\t" + isbn + "\t" + getCopies() + " \t" + authorList;
	}
	
}

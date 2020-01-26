package simpleLib;

import java.io.*;
import java.util.ArrayList;

public class LibDirectory {
	private ArrayList<User> users;
	private ArrayList<Book> books;
	private ArrayList<Journal> journals;
	private ArrayList<ConferenceProceeding> conferences;
	
	public LibDirectory(){
		this.users = new ArrayList<User>();
		this.books = new ArrayList<Book>();
		this.journals = new ArrayList<Journal>();
		this.conferences = new ArrayList<ConferenceProceeding>();
	}
	
	public LibDirectory(ArrayList<User> users, ArrayList<Book> books, ArrayList<Journal> journals, ArrayList<ConferenceProceeding> conferences){
		this.users = users;
		this.books = books;
		this.journals = journals;
		this.conferences = conferences;
	}
	
	//helpers
	public boolean readLibDirectory(String filename){
		BufferedReader reader = null;
		String line;
		try {
			reader = new BufferedReader(new FileReader(filename));
		
			while(!(line = reader.readLine()).equals("Users:"))
				continue;
			
			while(!(line = reader.readLine()).equals("Books:")){
				String userDetails[] = line.split("\t");
				if(userDetails.length == 4){
					User u = new User(userDetails[0].trim(), Long.parseLong(userDetails[1].trim()), userDetails[2].trim());
					u.setItemsBorrowed(Integer.parseInt(userDetails[3].trim()));
					users.add(u);
				}
			}
			
			while(!(line = reader.readLine()).equals("Journals:")){
				String bookDetails[] = line.split("\t");
				if(bookDetails.length == 6)
					books.add(new Book(bookDetails[0].trim(), bookDetails[1].trim(), bookDetails[2].trim(), Long.parseLong(bookDetails[3].trim()), Integer.parseInt(bookDetails[4].trim()), bookDetails[5].trim()));
			}
			
			while(!(line = reader.readLine()).equals("Conferences:")){
				String journalDetails[] = line.split("\t");
				if(journalDetails.length == 6)
					journals.add(new Journal(journalDetails[0].trim(), journalDetails[1].trim(), journalDetails[2].trim(), Integer.parseInt(journalDetails[3].trim()), Integer.parseInt(journalDetails[4].trim()), journalDetails[5].trim()));
			}
			
			while((line = reader.readLine()) != null){
				String conferenceDetails[] = line.split("\t");
				if(conferenceDetails.length == 8)
					conferences.add(new ConferenceProceeding(conferenceDetails[0].trim(), conferenceDetails[1].trim(), conferenceDetails[2].trim(), Long.parseLong(conferenceDetails[3].trim()), Integer.parseInt(conferenceDetails[4].trim()), conferenceDetails[5].trim(), conferenceDetails[6].trim(), conferenceDetails[7].trim()));
			}
			
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	public void writeLibDirectory(){
		FileWriter writer = null;
		
		try {
			writer = new FileWriter(Console.filename);
			writer.flush();
			
			//write users
			writer.write("Users:\n");
			
			for(User u: users){
				writer.write(u.toString() + "\n");
				writer.flush();
			}
			
			//write books
			writer.write("Books:\n");
			
			for(Book b: books){
				writer.write(b.toString() + "\n");
				writer.flush();
			}
			
			//write journals
			writer.write("Journals:\n");
			
			for(Journal j: journals){
				writer.write(j.toString() + "\n");
				writer.flush();
			}
			
			//write conferences
			writer.write("Conferences:\n");
			
			for(ConferenceProceeding c: conferences){
				writer.write(c.toString() + "\n");
				writer.flush();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<String> search(String query, String type){
		ArrayList<String> searchResult = new ArrayList<String>();
		
		switch(type){
		case "Author":
			searchResult = searchByAuthor(query);
			break;
		case "Publisher":
			searchResult = searchByPublisher(query);
			break;
		case "Title":
			searchResult = searchByTitle(query);
			break;
		default:
			searchResult.add("Unknown Query Type!!");
		}
		
		return searchResult;
	}

	private ArrayList<String> searchByTitle(String query) {
		ArrayList<String> searchResult = new ArrayList<String>();
		
		searchResult.add("Books By Title:");
		searchResult.add("Tilte\tPublisher\tDate\tISBN\tCopies\tAuthor");
		for(Book b: books){
			if(b.getTitle().equals(query))
				searchResult.add(b.toString());
		}
		searchResult.add("");
		
		searchResult.add("Journals By Title:");
		searchResult.add("Tilte\tPublisherDate\tVolume\tIssue\tPublisher\tArticles");
		for(Journal j: journals){
			if(j.getTitle().equals(query))
				searchResult.add(j.toString());
		}
		searchResult.add("");
		
		searchResult.add("Conference Proceedings By Title:");
		searchResult.add("Tilte\tPublisher\tDate\tISBN\tCopies\tAuthor\tLocation\tConferenceDate");
		for(ConferenceProceeding c: conferences){
			if(c.getTitle().equals(query))
				searchResult.add(c.toString());
		}
		searchResult.add("");
		
		
		return searchResult;
	}

	private ArrayList<String> searchByPublisher(String query) {
		ArrayList<String> searchResult = new ArrayList<String>();
		
		searchResult.add("Books By Publisher:");
		searchResult.add("Tilte\tPublisher\tDate\tISBN\tCopies\tAuthor");
		for(Book b: books){
			if(b.getPublisher().equals(query))
				searchResult.add(b.toString());
		}
		searchResult.add("");
		
		searchResult.add("Journals By Publisher:");
		searchResult.add("Tilte\tPublisherDate\tVolume\tIssue\tPublisher\tArticles");
		for(Journal j: journals){
			if(j.getPublisher().equals(query))
				searchResult.add(j.toString());
		}
		searchResult.add("");
		
		searchResult.add("Conference Proceedings By Publisher:");
		searchResult.add("Tilte\tPublisher\tDate\tISBN\tCopies\tAuthor\tLocation\tConferenceDate");
		for(ConferenceProceeding c: conferences){
			if(c.getPublisher().equals(query))
				searchResult.add(c.toString());
		}
		searchResult.add("");


		return searchResult;
	}

	private ArrayList<String> searchByAuthor(String query) {
		ArrayList<String> searchResult = new ArrayList<String>();

		searchResult.add("Books By Author:");
		searchResult.add("Tilte\tPublisher\tDate\tISBN\tCopies\tAuthor");
		for(Book b: books){
			for(String a: b.getAuthors()) 
				if(a.equals(query))
					searchResult.add(b.toString());
		}
		searchResult.add("");

		searchResult.add("Conference Proceedings By Author:");
		searchResult.add("Tilte\tPublisher\tDate\tISBN\tCopies\tAuthor\tLocation\tConferenceDate");
		for(ConferenceProceeding c: conferences){
			for(String a: c.getAuthors()) 
				if(a.equals(query))
					searchResult.add(c.toString());
		}
		searchResult.add("");


		return searchResult;
	}
	
	public boolean loanTransaction(long userId, String title, String docType){
		User userObj = null;
		for(User u: users){
			if(u.getID() == userId)
				userObj = u;
		}
		
		Document docObj = null;
		switch(docType){
		case "Book":
			for(Book b: books){
				if(b.getTitle().equals(title)){
					docObj = b;
					break;
				}
			}
			break;
		case "Journal":
			for(Journal j: journals)
				if(j.getTitle().equals(title)){
					docObj = j;
					break;
				}
			break;
		case "Conference Proceeding":
			for(ConferenceProceeding c: conferences)
				if(c.getTitle().equals(title)){
					docObj = c;
					break;
				}
			break;			
		}
		
		try {
			LoanTransaction newTransaction = new LoanTransaction(userObj, docObj);
			String transaction = newTransaction.getRegisterString();
			FileWriter writer = new FileWriter("Transactions.txt", true);
			writer.write(transaction + "\n");
			writer.flush();
			userObj.setItemsBorrowed(userObj.getItemsBorrowed() + 1);
			docObj.setCopies(docObj.getCopies() - 1);
			return true;
			
		} catch (Exception e) {
			return false;
		}
		
	}

	public void returnTransaction(long userId, String title, String docType, String dueDate){
		User userObj = null;
		for(User u: users){
			if(u.getID() == userId)
				userObj = u;
		}
		
		Document docObj = null;
		switch(docType){
		case "Book":
			for(Book b: books){
				if(b.getTitle().equals(title)){
					docObj = b;
					break;
				}
			}
			break;
		case "Journal":
			for(Journal j: journals)
				if(j.getTitle().equals(title)){
					docObj = j;
					break;
				}
			break;
		case "Conference Proceeding":
			for(ConferenceProceeding c: conferences)
				if(c.getTitle().equals(title)){
					docObj = c;
					break;
				}
			break;			
		}
		
		if(userObj == null || docObj == null){
			System.out.println("Return Not Allowed!! Incorrect Information Provided..");
			return;
		}
		
		String transaction = userId + "\t" + title + "\t" + dueDate; 
		try {
			BufferedReader reader = new BufferedReader(new FileReader("Transactions.txt"));
			String line;
			ArrayList<String> fileData = new ArrayList<String>();
			while((line = reader.readLine()) != null){
				if(!line.equals(transaction)){
					fileData.add(line);
				}
			}
			
			FileWriter writer = new FileWriter("Transactions.txt");
			for(String s: fileData){
				writer.write(s + "\n");
				writer.flush();
			}
			
			userObj.setItemsBorrowed(userObj.getItemsBorrowed() - 1);
			docObj.setCopies(docObj.getCopies() + 1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

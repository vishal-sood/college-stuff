package simpleLib;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Console {
	
	public static final String filename = "LibDirectory.txt";
	
	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		
		LibDirectory currentLibDirectory = new LibDirectory();
		System.out.println("\nDo you want to read existing Library Directory? (Yes/No)");
		String choice = in.nextLine();
		switch(choice){
		case "Yes":
		case "yes":
			currentLibDirectory.readLibDirectory(filename);
			break;
		case "No":
		case "no":
			ArrayList<User> users = new ArrayList<User>();
			ArrayList<Book> books = new ArrayList<Book>();
			ArrayList<Journal> journals = new ArrayList<Journal>();
			ArrayList<ConferenceProceeding> conferences = new ArrayList<ConferenceProceeding>();
			
			BufferedReader reader = null;
			String file, line;
			System.out.println("Enter the name of file containing user data:\t");
			file = in.nextLine();
			try {
				reader = new BufferedReader(new FileReader(file));
				reader.readLine();// to skip the header
				while((line = reader.readLine()) != null){
					String[] userDetails = line.split(" *\t+| {3,}");
					if(userDetails.length == 3)
						users.add(new User(userDetails[0].trim(), Long.parseLong(userDetails[1].trim()), userDetails[2].trim()));
					else if(userDetails.length != 0)
						throw new Exception("Bad Input Format");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("Enter the name of file containing books data:\t");
			file = in.nextLine();
			try {
				reader = new BufferedReader(new FileReader(file));
				reader.readLine();// to skip the header
				while((line = reader.readLine()) != null){
					String[] bookDetails = line.split(" *\t+| {3,}");
					if(bookDetails.length == 6)
						books.add(new Book(bookDetails[0].trim(), bookDetails[1].trim(), bookDetails[2].trim(), Long.parseLong(bookDetails[3].trim()), Integer.parseInt(bookDetails[4].trim()), bookDetails[5].trim()));
					else if(bookDetails.length != 0)
						throw new Exception("Bad Input Format");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("Enter the name of file containing journals data:\t");
			file = in.nextLine();
			try {
				reader = new BufferedReader(new FileReader(file));
				reader.readLine();// to skip the header
				while((line = reader.readLine()) != null){
					String[] journalDetails = line.split(" *\t+| {3,}");
					if(journalDetails.length == 6)
						journals.add(new Journal(journalDetails[0].trim(), journalDetails[4].trim(), journalDetails[1].trim(), Integer.parseInt(journalDetails[2].trim()), Integer.parseInt(journalDetails[3].trim()), journalDetails[5].trim()));
					else if(journalDetails.length != 0)
						throw new Exception("Bad Input Format");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("Enter the name of file containing conference proceedings data:\t");
			file = in.nextLine();
			try {
				reader = new BufferedReader(new FileReader(file));
				reader.readLine();// to skip the header
				while((line = reader.readLine()) != null){
					String conferenceDetails[] = line.split(" *\t+| {3,}");
					if(conferenceDetails.length == 8)
						conferences.add(new ConferenceProceeding(conferenceDetails[0].trim(), conferenceDetails[1].trim(), conferenceDetails[2].trim(), Long.parseLong(conferenceDetails[3].trim()), Integer.parseInt(conferenceDetails[4].trim()), conferenceDetails[5].trim(), conferenceDetails[6].trim(), conferenceDetails[7].trim()));
					else if(conferenceDetails.length != 0)
						throw new Exception("Bad Input Format");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			currentLibDirectory = new LibDirectory(users, books, journals, conferences);
			
			break;
		default:
			System.out.println("\nWrong Choice Entered!! Exiting..");
			in.nextLine();
			return;
		}
		
		System.out.println("\nWelcome to University Library");
		System.out.println("--------------------------------\n");
		while(true){			
			System.out.println("\n1. Search\n2. Loan\n3. Return\nPress Q to quit. Enter Your Choice (1-3):\t");
			char c = in.nextLine().charAt(0);
			switch(c){
			case '1':
				System.out.println("\nChoose an option:\n1. Search by Title\n2. Search by Author\n3. Search by Publisher\nEnter Your Choice (1-3):\t");
				char ch = in.nextLine().charAt(0);
				String query = "";
				String type = "";
				switch (ch){
				case '1':
					type = "Title";
					System.out.println("\nEnter the Title:\t");
					query = in.nextLine();
					break;
				case '2':
					type = "Author";
					System.out.println("\nEnter the Author:\t");
					query = in.nextLine();
					break;
				case '3':
					type = "Publisher";
					System.out.println("\nEnter the Publisher:\t");
					query = in.nextLine();
					break;
				default:
					System.out.println("\nWrong Choice Entered!! Exiting..");
					in.nextLine();
					return;
				}
				ArrayList<String> searchResult = currentLibDirectory.search(query, type);
				System.out.println("\nSearch Result");
				System.out.println("----------------\n");
				for(String s: searchResult)
					System.out.println(s);
				break;
			case '2':
				System.out.println("\nEnter the User ID:\t");
				long uid = Long.parseLong(in.nextLine());
				System.out.println("Enter the Document Type:\t");
				String docType = in.nextLine();
				System.out.println("Enter the Title:\t");
				String title = in.nextLine();
				
				if(!currentLibDirectory.loanTransaction(uid, title, docType))
					System.out.println("Transaction Unsuccessful!! Try Again..");
				
				break;
			case '3':
				System.out.println("\nEnter the User ID:\t");
				uid = Long.parseLong(in.nextLine());
				System.out.println("Enter the Document Type:\t");
				docType = in.nextLine();
				System.out.println("Enter the Title:\t");
				title = in.nextLine();
				System.out.println("Enter the Due Date (mm/dd/yyyy):\t");
				String dueDate = in.nextLine();
				currentLibDirectory.returnTransaction(uid, title, docType, dueDate);
				break;
			case 'q':
			case 'Q':
				currentLibDirectory.writeLibDirectory();
				return;
			default:
				System.out.println("Incorrect Choice Entered!!");
				break;
			}
			
		}
		
	}
	
}

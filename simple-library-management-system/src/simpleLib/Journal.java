package simpleLib;

import java.util.ArrayList;

public class Journal extends Document {
	private String publisherDate;
	private int volume;
	private int issue;
	private ArrayList<String> articles;
	
	private final int loanTermForStudent = 3;
	private final int loanTermForFaculty = 3;
	
	//constructor
	public Journal(String title, String publisher, String publisherDate, int volume, int issue, String articles){
		super(title, publisher);
		
		this.publisherDate = publisherDate;
		this.volume = volume;
		this.issue = issue;
		
		this.articles = new ArrayList<String>(); 
		String[] articleList = articles.split(",");
		for(String article: articleList)
			this.articles.add(article.trim());
	}
	
	//accessors
	public String getPublisherDate(){
		return publisherDate;
	}
	
	public int getVolume(){
		return volume;
	}
	
	public ArrayList<String> getArticles(){
		return articles;
	}
	
	public int getIssue(){
		return this.issue;
	}
	
	@Override
	public int getLoanTerm(String userType){
		int term = (userType.equals("Student") || userType.equals("student"))? loanTermForStudent : loanTermForFaculty;
		return term;
	}
	
	//mutators
	public void setPublisherDate(String publisherDate){
		this.publisherDate = publisherDate;
	}
	
	public void setVolume(int volume){
		this.volume = volume;
	}
	
	public void setIssue(int issue){
		this.issue = issue;
	}
	
	public void setArticles(String articles){
		String[] articleList = articles.split(",");
		for(String article: articleList)
			this.articles.add(article.trim());
	}
	
	//helper
	@Override
	public String toString(){
		String articleList = "";
		ArrayList<String> temp = getArticles();

		if(temp.size() > 0){
			articleList = temp.get(0);

			for(int i=1; i<temp.size(); i++){
				articleList += "," + temp.get(i);
			}
		}

		return getTitle() + "\t" + getPublisher() + "\t" + publisherDate + "\t" + volume + "\t" + issue + " \t" + articleList;

	}

}

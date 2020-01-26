package memory_matching_game;

import java.util.Scanner;

public class PlayMemoryGame {
	public static Scanner sc = new Scanner(System.in);
	public static MemoryGameBoard game = new MemoryGameBoard();
	
	public static void main(String[] args) {
		int bestScore = 1000;
		int turnCount;
		int pairsLeft;
		boolean startGame = true;
		
		System.out.println("Welcome to the Memory Game.\n"
				+ "The goal is to find all the matching pairs in as few turns as possible.\n"
				+ "At each turn select two different positions on the board to see if they match.\n");
		
		while(startGame) {
			System.out.println("Press any key to start the game.");
			sc.next(); // wait for a key to be pressed
			
			game.shuffleCards();
			
			turnCount = 0;
			pairsLeft = (MemoryGameBoard.BOARD_SIZE * MemoryGameBoard.BOARD_SIZE) / 2;
			
			while(pairsLeft > 0) {
				turnCount++;
				game.showBoard();
				
				System.out.println("Where is the first card you want to see?");
				int row1 = getValidInt("Row: ", 1, MemoryGameBoard.BOARD_SIZE);
				int col1 = getValidInt("Col: ", 1, MemoryGameBoard.BOARD_SIZE);
				while(game.isFaceUp(row1, col1)) {
					System.err.println("ERROR: The chosen card is already face up, choose another card!!");
					row1 = getValidInt("Row: ", 1, MemoryGameBoard.BOARD_SIZE);
					col1 = getValidInt("Col: ", 1, MemoryGameBoard.BOARD_SIZE);
				}
				game.flipCard(row1, col1);
				/*game.showBoard(); //Ideally the board should be displayed here so that the player can choose the second card after seeing the first card opened.*/
				
				System.out.println("Where is the second card you want to see?");
				int row2 = getValidInt("Row: ", 1, MemoryGameBoard.BOARD_SIZE);
				int col2 = getValidInt("Col: ", 1, MemoryGameBoard.BOARD_SIZE);
				while(game.isFaceUp(row2, col2)) {
					System.err.println("ERROR: The chosen card is already face up, choose another card!!");
					System.out.println();
					row2 = getValidInt("Row: ", 1, MemoryGameBoard.BOARD_SIZE);
					col2 = getValidInt("Col: ", 1, MemoryGameBoard.BOARD_SIZE);
				}
				game.flipCard(row2, col2);
				
				game.showBoard();
				if(game.cardsMatch(row1, col1, row2, col2)) {
					System.out.println("You found a match.");
					pairsLeft--;
				}
				else {
					game.flipCard(row1, col1);
					game.flipCard(row2, col2);
					System.out.println("Sorry, No match.");
				}
				
				System.out.println("Press any key to continue.");
				sc.next(); // wait for a key to be pressed
				
			}
			
			System.out.println("CONGRATULATIONS! You found all the matching pairs!\n");
			
			System.out.println("You did it in " + turnCount + " turns.");
			
			if(turnCount < bestScore)
				bestScore = turnCount;
			if(turnCount == bestScore)
				System.out.println("That is your best score so far!");
			else
				System.out.println("Your best score so far is: " + bestScore);
			System.out.println();
			
			System.out.println("Do you want to start another game?");
			String choice = sc.next();
			if(choice.equalsIgnoreCase("Yes") || choice.equalsIgnoreCase("Y"))
				startGame = true;
			else
				startGame = false;
			
		}
		
		System.out.println("Your Best Score for the session was: " + bestScore);
		System.out.println("Goodbye! See you again..");
		
	}
	
	public static int getValidInt(String prompt, int min, int max) {
		boolean valid = false;
		int num = 0;
		while(!valid) {
			System.out.print(prompt);
			if(sc.hasNextInt()) {
				num = sc.nextInt();
				if(num >= min && num <= max)
					valid = true;
				else
					System.err.println("ERROR: " + num + " is not in the valid range ("+ min + "..." + max +")");
			}
			else {
				System.err.println("ERROR: " + sc.next() + " is not a valid integer ("+ min + "..." + max +")");
			}
		}
		
		return num;
	}
}

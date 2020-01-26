package tictactoe;

import java.util.Scanner;

public class TicTacToe{
	public Player takePlayerInput() {
		System.out.println("Enter player name");
		Scanner s = new Scanner(System.in);
		String name = s.nextLine();
		System.out.println("Enter player symbol");
		char symbol = s.next().charAt(0);
		return new Player(name, symbol);
	}

	public void start() {
		Player player1 = takePlayerInput();
		Player player2 = takePlayerInput();
		while (player2.getSymbol() == player1.getSymbol()) {
			System.out.println("Player2 take someother symbol");
			player2 = takePlayerInput();
		}

		Board b = new Board(player1.getSymbol(), player2.getSymbol());
		boolean isPlayer1Turn = true;
		Scanner s = new Scanner(System.in);
		while (b.getGameStatus() == Board.INCOMPLETE) {
			Player currentPlayer;
			if (isPlayer1Turn)
				currentPlayer = player1;
			else 
				currentPlayer = player2;

			boolean done = false;
			while (!done) {
				b.print();
				System.out.println(currentPlayer.getName() + " your turn!");
				System.out.println("Enter x");
				int x = s.nextInt();
				System.out.println("Enter y");
				int y = s.nextInt();
				try {
					b.move(currentPlayer.getSymbol(), x, y);
					done = true;
				} catch (WrongSymbolException e) {
					System.out.println("Something went wrong!!!");
					return;
				} catch(WrongInputException e) {
					System.out.println("Invalid input! Try again");
				}
			}
			isPlayer1Turn = !isPlayer1Turn;
		}

		b.print();
		int status = b.getGameStatus();
		if (status == Board.DRAW) {
			System.out.println("Draw!");
		} else if (status == Board.PLAYER1WON) {
			System.out.println(player1.getName() + " you won!");
		} else {
			System.out.println(player2.getName() + " you won!");
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TicTacToe t= new TicTacToe();
		t.start();
	}
}

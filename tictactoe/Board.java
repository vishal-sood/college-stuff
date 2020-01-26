package tictactoe;

public class Board {
	char[][] board;
	char player1Symbol;
	char player2Symbol;
	public static final int PLAYER1WON = 1;
	public static final int PLAYER2WON = 2;
	public static final int INCOMPLETE = 3;
	public static final int DRAW = 0;
	
	
	public Board(char player1S, char player2S) {
		this.player1Symbol = player1S;
		this.player2Symbol = player2S;
		board = new char[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = ' ';
			}
		}
		
	}

	public void print() {
		System.out.println("------");
		for (int i = 0; i < 3; i++) {
			System.out.print("|");
			for (int j = 0; j < 3; j++) {
				System.out.print(board[i][j] + "|");
			}
			System.out.println();
			System.out.println("------");
		}
	}
	
	public void move(char symbol, int x, int y) throws WrongSymbolException, WrongInputException {
		if (symbol != player1Symbol && symbol != player2Symbol) {
			throw new WrongSymbolException();
		}
		
		if (x < 0 || x > 2 || y < 0 || y > 2 || board[x][y] != ' ') {
			throw new WrongInputException();
		}
		
		board[x][y] = symbol;
	}

	public int getGameStatus() {
		for (int i = 0; i < 3; i++) {
			if (board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
				if (board[i][0] == player1Symbol)
					return PLAYER1WON;
				else if (board[i][0] == player2Symbol)
					return PLAYER2WON;
			}
		}
		
		for (int i = 0; i < 3; i++) {
			if (board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
				if (board[0][i] == player1Symbol)
					return PLAYER1WON;
				else if (board[0][i] == player2Symbol)
					return PLAYER2WON;
			}
		}
		
		if (board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
			if (board[0][0] == player1Symbol)
				return PLAYER1WON;
			else if (board[0][0] == player2Symbol)
				return PLAYER2WON;
		}
		
		if (board[2][0] == board[1][1] && board[2][0] == board[0][2]) {
			if (board[2][0] == player1Symbol)
				return PLAYER1WON;
			else if (board[2][0] == player2Symbol)
				return PLAYER2WON;
		}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == ' ') {
					return INCOMPLETE;
				}
			}
		}
		return DRAW;
	}
}

package memory_matching_game;

public class MemoryGameBoard {
	
	public static final int BOARD_SIZE = 4;
	private static final int NUM_SWAPS = 1000;
	
	private Card[][] gameBoard;
	
	public MemoryGameBoard() {
		this.gameBoard =  new Card[BOARD_SIZE + 1][BOARD_SIZE + 1];
		initalizeCards();
	}
	
	public void shuffleCards() {
		turnCardsFaceDown();
		for(int i=0; i<NUM_SWAPS; i++){
			int row1 = 1 + (int)(Math.random() * BOARD_SIZE);
			int col1 = 1 + (int)(Math.random() * BOARD_SIZE);
			int row2 = 1 + (int)(Math.random() * BOARD_SIZE);
			int col2 = 1 + (int)(Math.random() * BOARD_SIZE);
			
			Card temp = this.gameBoard[row1][col1];
			this.gameBoard[row1][col1] = this.gameBoard[row2][col2];
			this.gameBoard[row2][col2] = temp;
		}
	}
	
	public void showBoard() {
		hideBoard();
		System.out.println(this.toString());
	}
	
	public boolean cardsMatch(int row1, int col1, int row2, int col2) {
		return this.gameBoard[row1][col1].equals(this.gameBoard[row2][col2]);
	}
	
	public boolean isFaceUp(int row, int col) {
		return this.gameBoard[row][col].isFaceUp();
	}
	
	public void flipCard(int row, int col) {
		this.gameBoard[row][col].flipCard();
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("    1 2 3 4 \n");
		buf.append("   =========\n");
		for(int i=1; i<=BOARD_SIZE; i++){
			buf.append(i + " | ");
			for(int j=1; j<=BOARD_SIZE; j++){
				buf.append(this.gameBoard[i][j].toString() + " ");
			}
			buf.append("|\n");
		}
		buf.append("   =========");
		
		return buf.toString();
	}
	
	private void initalizeCards() {
		for(int i=1; i<=BOARD_SIZE; i++)
			for(int j=1; j<=BOARD_SIZE; j++)
				this.gameBoard[i][j] = new Card(((i+1)%2)*4 + j);
	}
	
	private void hideBoard() {
		int linesToScroll = 15;
		while(linesToScroll > 0){
			System.out.println();
			linesToScroll--;
		}
	}
	
	private void turnCardsFaceDown() {
		for(int i=1; i<=BOARD_SIZE; i++)
			for(int j=1; j<=BOARD_SIZE; j++)
				this.gameBoard[i][j].setFaceDown();
	}
	
	public void displayGameBoardValues() {
		System.out.print("    1 2 3 4 \n");
		System.out.print("   =========\n");
		for(int i=1; i<=BOARD_SIZE; i++){
			System.out.print(i + " | ");
			for(int j=1; j<=BOARD_SIZE; j++){
				System.out.print(this.gameBoard[i][j].getValue() + " ");
			}
			System.out.print("|\n");
		}
		System.out.print("   =========\n");
	}
	
}

package game_2048;

import java.awt.Color;
import java.util.HashMap;

public class Board {
	private int[][] board;
	private Color[][] colors;
	private boolean isGame;
	private int numberOfTilesOnBoard;
	private int score;
	private HashMap<Integer, Color> colorValues;
	
	public Board(){
		this.board = new int[4][4];
		this.colors = new Color[4][4];
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				this.colors[i][j] = Color.LIGHT_GRAY;
		this.colorValues = new HashMap<Integer, Color>();
		this.colorValues.put(0, Color.LIGHT_GRAY);
		this.colorValues.put(2, new Color(248, 248, 255));
		this.colorValues.put(4, new Color(255, 248, 220));
		this.colorValues.put(8, new Color(255, 228, 181));
		this.colorValues.put(16, new Color(255, 165, 79));
		this.colorValues.put(32, new Color(255, 130, 71));
		this.colorValues.put(64, new Color(255, 69, 0));
		this.colorValues.put(128, new Color(255, 236, 139));
		this.colorValues.put(256, new Color(255, 193, 37));
		this.colorValues.put(512, new Color(255, 189, 26));
		this.colorValues.put(1024, new Color(255, 185, 15));
		this.colorValues.put(2048, new Color(255, 215, 0));
		this.colorValues.put(4096, new Color(38, 38, 38));
		this.colorValues.put(8192, new Color(77, 77, 77));
		this.colorValues.put(16384, new Color(100, 100, 100));
		this.isGame = true;
		newTile();
		newTile();
		this.numberOfTilesOnBoard = 2;
		this.score = 0;
	}
	
	public boolean isGame(){
		return this.isGame;
	}
	
	public void newTile(){
		if(!this.isGame || this.numberOfTilesOnBoard == 16)
			return;
		
		int tileNumber;
		int tileValue;
		boolean done = false;
		
		int valueIsTwoOrFour = (int) (Math.random() * 35);
		if(valueIsTwoOrFour % 10 == 0 || valueIsTwoOrFour % 11 == 0)
			tileValue = 4;
		else
			tileValue = 2;
		
		while(!done){
			float boundaryTileOrNot = (float) (Math.random() * 5);
			if(boundaryTileOrNot > 4.0){
				int[] choice = {5,6,9,10};
				tileNumber = choice[(int) (Math.random() * 4)];
			}
			else{
				int[] choice = {0,1,2,3,4,7,8,11,12,13,14,15};
				tileNumber = choice[(int) (Math.random() * 12)];
			}
			
			if(board[tileNumber / 4][tileNumber % 4] == 0){
				board[tileNumber / 4][tileNumber % 4] = tileValue;
				this.numberOfTilesOnBoard++;
				done = true;
			}
		}
		setColors();
	}
	
	private void setColors() {
		int[][] temp = getBoard();
		
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
					this.colors[i][j] = colorValues.get(temp[i][j]);
	}

	private void isGameEnded(){
		if(numberOfTilesOnBoard < 16)
			return;

		boolean ok = true;

		int[] x = {5,6,9,10};
		for(int k: x){
			int i = k / 4;
			int j = k % 4;

			if(board[i][j] == board[i - 1][j] || board[i][j] == board[i + 1][j] || board[i][j] == board[i][j - 1] || board[i][j] == board[i][j + 1])
				ok = false;
		}

		int[] y = {1,2,13,14};
		for(int k: y){
			int i = k / 4;
			int j = k % 4;

			if(board[i][j] == board[i][j - 1] || board[i][j] == board[i][j + 1])
				ok = false;
		}

		int[] z = {4,7,8,11};
		for(int k: z){
			int i = k / 4;
			int j = k % 4;

			if(board[i][j] == board[i - 1][j] || board[i][j] == board[i + 1][j])
				ok = false;
		}

		if(ok)
			this.isGame = false;
	}

	public void moveRight(){
		boolean hasMoved = false;
		for(int i = 0; i < 4; i++){
			int j = 3, k = 3;
			while(k >= 0){
				boolean flag = false;
				while(k >= 0){
					if(board[i][k] != 0){
						flag = true;
						break;
					}
					k--;
				}

				if(k != j){
					if(flag){
						board[i][j] = board[i][k];
						hasMoved = true;
						board[i][k] = 0;
						j--;
					}
				}
				else{
					k--;
					j--;
				}
			}
			for(j = 3; j > 0; j--){
				if(board[i][j] != 0){
					if(board[i][j] == board[i][j - 1]){
						this.score += (2 * board[i][j]);
						board[i][j] += board[i][j - 1];
						this.numberOfTilesOnBoard--;
						hasMoved = true;
						for(k = j - 1; k > 0; k--)
							board[i][k] = board[i][k - 1];
						board[i][0] = 0;
					}
				}
			}
		}
		
		if(hasMoved){
			setColors();
			newTile();
		}
		
		isGameEnded();
	}
	
	public void moveLeft(){
		boolean hasMoved = false;
		for(int i = 0; i < 4; i++){
			int j = 0, k = 0;
			while(k <= 3){
				boolean flag = false;
				while(k <= 3){
					if(board[i][k] != 0){
						flag = true;
						break;
					}
					k++;
				}

				if(k != j){
					if(flag){
						board[i][j] = board[i][k];
						hasMoved = true;
						board[i][k] = 0;
						j++;
					}
				}
				else{
					k++;
					j++;
				}
			}
			for(j = 0; j < 3; j++){
				if(board[i][j] != 0){
					if(board[i][j] == board[i][j + 1]){
						this.score += (2 * board[i][j]);
						board[i][j] += board[i][j + 1];
						this.numberOfTilesOnBoard--;
						hasMoved = true;
						for(k = j + 1; k < 3; k++)
							board[i][k] = board[i][k + 1];
						board[i][3] = 0;
					}
				}
			}
		}
		
		
		if(hasMoved){
			setColors();
			newTile();
		}
		
		isGameEnded();
	}
	
	public void moveDown(){
		boolean hasMoved = false;
		for(int j = 0; j < 4; j++){
			int i = 3, k = 3;
			while(k >= 0){
				boolean flag = false;
				while(k >= 0){
					if(board[k][j] != 0){
						flag = true;
						break;
					}
					k--;
				}

				if(k != i){
					if(flag){
						board[i][j] = board[k][j];
						hasMoved = true;
						board[k][j] = 0;
						i--;
					}
				}
				else{
					k--;
					i--;
				}
			}
			for(i = 3; i > 0; i--){
				if(board[i][j] != 0){
					if(board[i][j] == board[i - 1][j]){
						this.score += (2 * board[i][j]);
						board[i][j] += board[i - 1][j];
						this.numberOfTilesOnBoard--;
						hasMoved = true;
						for(k = i - 1; k > 0; k--)
							board[k][j] = board[k - 1][j];
						board[0][j] = 0;
					}
				}
			}
		}
		
		if(hasMoved){
			setColors();
			newTile();
		}
		
		isGameEnded();
	}
	
	public void moveUp(){
		boolean hasMoved = false;
		for(int j = 0; j < 4; j++){
			int i = 0, k = 0;
			while(k <= 3){
				boolean flag = false;
				while(k <= 3){
					if(board[k][j] != 0){
						flag = true;
						break;
					}
					k++;
				}

				if(k != i){
					if(flag){
						board[i][j] = board[k][j];
						hasMoved = true;
						board[k][j] = 0;
						i++;
					}
				}
				else{
					k++;
					i++;
				}
			}
			for(i = 0; i < 3; i++){
				if(board[i][j] != 0){
					if(board[i][j] == board[i + 1][j]){
						this.score += (2 * board[i][j]);
						board[i][j] += board[i + 1][j];
						this.numberOfTilesOnBoard--;
						hasMoved = true;
						for(k = i + 1; k < 3; k++)
							board[k][j] = board[k + 1][j];
						board[3][j] = 0;
					}
				}
			}
		}
		
		if(hasMoved){
			setColors();
			newTile();
		}
		
		isGameEnded();
	}
	
	public int[][] getBoard(){
		return this.board;
	}
	
	public Color[][] getColors(){
		return this.colors;
	}

	
	public int score() {
		return score;
	}
	
}

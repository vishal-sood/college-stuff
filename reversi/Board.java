package reversi;

import java.util.ArrayList;

public class Board {
	int[][] board;

	public Board(){
		board = new int[8][8];
		board[3][3] = 1;
		board[4][4] = 1;
		board[3][4] = 2;
		board[4][3] = 2;
	}

	public void move(int x, int y, int playerNumber) throws InvalidInput, InvalidMove{
		if(x < 0 || x > 7 || y < 0 || y > 7 || board[x][y] != 0)
			throw new InvalidInput();

		int currentXPosition = x;
		int currentYPosition = y;
		int otherPlayerNumber = playerNumber == 1 ? 2 : 1;
		int[] xDir = {0,0,1,1,1,-1,-1,-1};
		int[] yDir = {-1,1,0,1,-1,0,1,-1};
		int count = 0;

		for(int z = 0; z < 8; z++){
			ArrayList<Integer[]> toBeChanged = new ArrayList<Integer[]>();
			boolean didPlayerNumberOccur = false;
			boolean doWeHaveToChangeAnything = true;
			int i = currentXPosition + xDir[z];
			int j = currentYPosition + yDir[z];
			while(i >= 0 && j >= 0){
				if(board[i][j] == 0){
					doWeHaveToChangeAnything = false;
					break;
				}

				if(board[i][j] == otherPlayerNumber){
					Integer[] newPosition = {i,j};
					toBeChanged.add(newPosition);
				}

				if(board[i][j] == playerNumber){
					didPlayerNumberOccur = true;
					break;
				}
				i += xDir[z];
				j += yDir[z];
			}

			if(toBeChanged.isEmpty() || !didPlayerNumberOccur || !doWeHaveToChangeAnything)
				continue;
			else{
				for(int l = 0; l < toBeChanged.size(); l++){
					Integer[] pos = toBeChanged.get(l);
					board[pos[0]][pos[1]] = playerNumber;
				}
				count++;
			}
		}
		
		if(count == 0)
			throw new InvalidMove();
		else
			board[x][y] = playerNumber;
		
	}
	
	public void print(){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++)
				System.out.print(board[i][j]);
			System.out.println();
		}
	}
	
}

package reversi;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;

public class Reversi extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	JPanel[] row = new JPanel[9];
	JButton[] button = new JButton[64];
	JTextArea display = new JTextArea(1, 20);
	Font font = new Font("Times New Roman", Font.PLAIN, 30);
	int[] dimW = {516,60};
	int[] dimH = {40,60};
	Dimension displayDimension = new Dimension(dimW[0], dimH[0]);
	Dimension regularDimension = new Dimension(dimW[1], dimH[1]);
	int[][] board;
	int count;
	int playerOnepieces;
	int playerTwopieces;
	int currentPlayer = 1;
	boolean gameEnded = false;

	public void board(){
		board = new int[8][8];
		board[3][3] = 1;
		board[4][4] = 1;
		board[3][4] = 2;
		board[4][3] = 2;
		count = 4;
		playerOnepieces = 2;
		playerTwopieces = 2;
	}
	
	Reversi(){
		super("Reversi");
		board();
		setSize(600, 620);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridLayout grid = new GridLayout(9,8);
		setLayout(grid);
		
		for(int i = 0; i < 9; i++)
			row[i] = new JPanel();
		
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		for(int i = 0; i < 9; i++)
			row[i].setLayout(f1);
		
		for(int i = 0; i < 64; i++) {
			button[i] = new JButton();
			if(board[i % 8][i / 8] == 0)
				button[i].setBackground(Color.gray);
			else if(board[i % 8][i / 8] == 1){
				button[i].setBackground(Color.white);
				button[i].setEnabled(false);
			}
			else if(board[i % 8][i / 8] == 2){
				button[i].setBackground(Color.black);
				button[i].setEnabled(false);
			}
			button[i].setPreferredSize(regularDimension);
			button[i].addActionListener(this);
		}
		
		display.setFont(font);
		display.setEditable(false);
		display.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		display.setPreferredSize(displayDimension);
		display.setOpaque(false);
		
		row[0].add(display);
		row[0].setSize(displayDimension);
		add(row[0]);
		
		
		for(int k = 1; k < 9; k++){
			for(int i = 0; i < 8; i++)
				row[k].add(button[((k - 1) * 8) + i]);
		add(row[k]);
		}
		
		setVisible(true);
		display.setText("Player One, Your Turn");
	}
	
	public void gameEnded(){
		if(gameEnded){
			for(int i = 0; i < 64; i++)
				button[i].setEnabled(false);
		}
	}

	public void actionPerformed(ActionEvent ae){
		for(int i = 0; i < 64; i++)
			if(ae.getSource() == button[i])
				move(i % 8, i / 8, currentPlayer);
	}

	public void move(int x, int y, int playerNumber){
		if(gameEnded)
			return;
		
		display.setText("");
		
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
			while(i >= 0 && j >= 0 && i <= 7 && j <= 7){
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
					button[(pos[1] * 8) + pos[0]].setVisible(false);
					if(playerNumber == 1){
						button[(pos[1] * 8) + pos[0]].setBackground(Color.white);
						playerOnepieces++;
						playerTwopieces--;
					}
					else if(playerNumber == 2){
						button[(pos[1] * 8) + pos[0]].setBackground(Color.black);
						playerOnepieces--;
						playerTwopieces++;
					}
					button[(pos[1] * 8) + pos[0]].setEnabled(false);
					button[(pos[1] * 8) + pos[0]].setVisible(true);
				}
				count++;
			}
		}

		if(count == 0){
			String sText = currentPlayer == 1? "Player One, Your Turn": "Player Two, Your Turn";
			display.setText(sText);
			display.setText("Invalid Move! " + sText);
		}
		else{
			board[x][y] = playerNumber;

			button[(y * 8) + x].setVisible(false);
			if(playerNumber == 1){
				button[(y * 8) + x].setBackground(Color.white);
				playerOnepieces++;
			}
			else if(playerNumber == 2){
				button[(y * 8) + x].setBackground(Color.black);
				playerTwopieces++;
			}
			button[(y * 8) + x].setEnabled(false);
			button[(y * 8) + x].setVisible(true);
			
			this.count++;
			currentPlayer = currentPlayer == 1? 2 : 1;
			String sText = currentPlayer == 1? "Player One, Your Turn": "Player Two, Your Turn";
			display.setText(sText);
		}
		
		if(this.count == 64){
			gameEnded = true;
			if(playerOnepieces > playerTwopieces)
				display.setText("Player One Wins!!");
			else if(playerTwopieces > playerOnepieces)
				display.setText("Player Two Wins!!");
			else
				display.setText("It's a Draw!!");
		}
		
		if(playerOnepieces < 1){
			display.setText("Player Two Wins!!");
		}
		
		if(playerTwopieces < 1){
			display.setText("Player One Wins!!");
		}
		
		gameEnded();
	}

	public static void main(String[] args){
		Reversi r = new Reversi();
	}	
}

package game_2048;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Game2048 extends JFrame implements ActionListener, KeyListener{
	
	private static final long serialVersionUID = 1L;
	
	Board b;
	JPanel[] row = new JPanel[6];
	JButton[] button = new JButton[20];
	JTextArea display = new JTextArea(2, 1);
	final static Dimension tileDimension = new Dimension(80, 80);
	final static Dimension displayDimension = new Dimension(320, 80);
	
	Game2048(){
		super("2048");
		b = new Board();
		setSize(400, 560);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridLayout grid = new GridLayout(6, 4, 0, 0);
		setLayout(grid);
		
		for(int i = 0; i < 6; i++)
			row[i] = new JPanel();
		
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		for(int i = 0; i < 6; i++)
			row[i].setLayout(f1);
		
		Color[][] temp = b.getColors();
		int[][] temporary = b.getBoard();
			
		for(int i = 0; i < 16; i++) {
			button[i] = new JButton();
			button[i].setBackground(temp[i / 4][i % 4]);
			button[i].setEnabled(false);
			button[i].setPreferredSize(tileDimension);
			button[i].addActionListener(this);
			if(temporary[i / 4][i % 4] != 0)
				button[i].setText(temporary[i / 4][i % 4] + "");
		}
		
		for(int i = 16; i < 20; i++) {
			button[i] = new JButton();
			button[i].setBackground(Color.WHITE);
			button[i].setEnabled(true);
			button[i].setPreferredSize(tileDimension);
			button[i].addActionListener(this);
			if(i == 16)
				button[i].setText("Left");
			else if(i == 17)
				button[i].setText("Up");
			else if(i == 18)
				button[i].setText("Down");
			else if(i == 19)
				button[i].setText("Right");
			
		}
		
		display.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		display.setEditable(false);
		display.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		display.setPreferredSize(displayDimension);
		display.setOpaque(false);
		
		row[0].add(display);
		row[0].setSize(new Dimension(0, 0));
		add(row[0]);
		
		for(int k = 1; k < 6; k++){
			for(int i = 0; i < 4; i++)
				row[k].add(button[((k - 1) * 4) + i]);
		add(row[k]);
		}
		
		setVisible(true);
		display.setText("\nScore: " + b.score());
		
		addKeyListener(this);
		setFocusable(true);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == button[16])
			b.moveLeft();
		else if(ae.getSource() == button[17])
			b.moveUp();
		else if(ae.getSource() == button[18])
			b.moveDown();
		else if(ae.getSource() == button[19])
			b.moveRight();
		else
			return;
		
		display.setText("\nScore: " + b.score());
		
		Color[][] temp = b.getColors();
		for(int i = 0; i < 16; i++)
			button[i].setBackground(temp[i / 4][i % 4]);
		
		int[][] temporary = b.getBoard();
		for(int i = 0; i < 16; i++)
			if(temporary[i / 4][i % 4] != 0)
				button[i].setText(temporary[i / 4][i % 4] + "");
			else
				button[i].setText("");
		
		if(!b.isGame()){
			display.setText("Game Over!! Try Again" + "\nScore: " + b.score());
			for(int i = 16; i < 20; i++)
				button[i].setEnabled(false);
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			b.moveLeft();
		else if(e.getKeyCode() == KeyEvent.VK_UP)
			b.moveUp();
		else if(e.getKeyCode() == KeyEvent.VK_DOWN)
			b.moveDown();
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			b.moveRight();
		else
			return;
		
		display.setText("\nScore: " + b.score());
		
		Color[][] temp = b.getColors();
		for(int i = 0; i < 16; i++)
			button[i].setBackground(temp[i / 4][i % 4]);
		
		int[][] temporary = b.getBoard();
		for(int i = 0; i < 16; i++)
			if(temporary[i / 4][i % 4] != 0)
				button[i].setText(temporary[i / 4][i % 4] + "");
			else
				button[i].setText("");
		
		if(!b.isGame()){
			display.setText("Game Over!! Try Again" + "\nScore: " + b.score());
			for(int i = 16; i < 20; i++)
				button[i].setEnabled(false);
		}
	}

	public void keyReleased(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {}
	
	public static void main(String[] args){
		Game2048 g = new Game2048();
	}
	
}

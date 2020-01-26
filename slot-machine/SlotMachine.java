import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;


public class SlotMachine extends JFrame implements ActionListener, ChangeListener {
	
	private final int MINIMUM_ALLOWED_BET = 1;
	private final String[][] payoff = {	{"Bell", "Bell", "Bell", "10"},
										{"Grape", "Grape", "Grape", "7"},
										{"Cherry", "Cherry", "Cherry", "5"},
										{"Cherry", "Cherry", "Line", "3"},
										{"Cherry", "Line", "Cherry", "3"},
										{"Line", "Cherry", "Cherry", "3"},
										{"Cherry", "Line", "Line", "1"},
										{"Line", "Cherry", "Line", "1"},
										{"Line", "Line", "Cherry", "1"} };
	
	// game constants
	private int currentHoldings;
	private int currentBet;
	private boolean isRegular;
	
	// GUI will have 5 rows/panels
	private JPanel[] rows;
	
	// components of first row
	private JLabel holdingsLabel;
	private JLabel holdingsValue;
	private JLabel betLabel;
	private JSpinner betChooser;
	
	// components of second row
	private Cell[] cells;
	private JLabel[] drums;
	
	// components of third row
	private JButton spinButton;
	
	// components of fourth row
	private JLabel mode;
	private ButtonGroup regularOrNot;
	private JRadioButton regular;
	private JRadioButton test;
	
	// components of fifth row
	private JButton resetButton;
	
	public SlotMachine() {
		
		currentHoldings = 100;
		currentBet = 1;
		isRegular = true;
		
		rows = new JPanel[5];
		
		holdingsLabel = new JLabel("Balance: ", SwingConstants.RIGHT);
		holdingsValue = new JLabel(String.valueOf(currentHoldings), SwingConstants.RIGHT);
		betLabel = new JLabel("Bet: ", SwingConstants.RIGHT);
		betChooser = new JSpinner(new SpinnerNumberModel(currentBet, MINIMUM_ALLOWED_BET, currentHoldings, 1));
		betChooser.addChangeListener(this);
		
		cells = new Cell[3];
		drums = new JLabel[3];
		
		spinButton = new JButton("Spin");
		spinButton.addActionListener(this);
		
		mode = new JLabel("Mode: ", SwingConstants.RIGHT);
		regularOrNot = new ButtonGroup();
		regular = new JRadioButton("Regular", true);
		regular.addActionListener(this);
		test = new JRadioButton("Test", false);
		test.addActionListener(this);
		
		resetButton = new JButton("Reset");
		resetButton.addActionListener(this);
		
		setLayout(new GridBagLayout()); // set main layout
		
		for(int i=0; i<rows.length; i++){
			rows[i] = new JPanel();
		}
		
		// setting components in row 0
		rows[0].setLayout(new GridLayout(1, 4, 5, 5));
		rows[0].add(holdingsLabel);
		rows[0].add(holdingsValue);
		rows[0].add(betLabel);
		rows[0].add(betChooser);
		
		// setting components in row 1
		rows[1].setLayout(new GridLayout(1, 3, 5, 5));
		
		for(int i=0; i<cells.length; i++){
			cells[i] = new Cell();
			drums[i] = new JLabel();
			drums[i].setIcon(cells[i].getIcon());
			rows[1].add(drums[i]);
		}
		
		// setting components in row 2
		rows[2].setLayout(new GridLayout(1, 1, 5, 5));
		rows[2].add(spinButton);
		
		// setting components in row 3
		rows[3].setLayout(new GridLayout(1, 0, 5, 5));
		
		regularOrNot.add(regular);
		regularOrNot.add(test);
		rows[3].add(mode);
		rows[3].add(regular);
		rows[3].add(test);
		
		// setting components in row 4
		rows[4].setLayout(new GridLayout(1, 1, 5, 5));
		rows[4].add(resetButton);
		
		for(int i=0; i<rows.length; i++){
			if(i != 1){
				GridBagConstraints c = new GridBagConstraints();
				c.gridheight = 1;
				c.gridwidth = 3;
				c.gridx = 0;
				c.gridy = (i==0)? i : (i+1);
				c.insets = new Insets(5,5,5,5);
				add(rows[i], c);
			}
			else{
				GridBagConstraints c = new GridBagConstraints();
				c.gridheight = 2;
				c.gridwidth = 3;
				c.gridx = 0;
				c.gridy = i;
				c.insets = new Insets(5,5,5,5);
				add(rows[i], c);
			}
		}
		
		setMinimumSize(new Dimension(400, 320));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("One-Armed Bandit");
		setVisible(true);
	}
	
	public void setComponents(){
		holdingsValue.setText(String.valueOf(currentHoldings));
		betChooser.setModel(new SpinnerNumberModel((currentBet < currentHoldings) ? currentBet : currentHoldings, MINIMUM_ALLOWED_BET, currentHoldings, 1));
		for(int i=0; i<drums.length; i++){
			drums[i].setIcon(cells[i].getIcon());
		}
	}
	
	public int getPayoff(){
		boolean found = false;
		for(int i=0; i<payoff.length; i++){
			if(found)
				break;
			found = true;
			for(int j=0; j<payoff[0].length - 1; j++){
				if(!payoff[i][j].equals("Line")){
					if(!payoff[i][j].equals(cells[j].getName())){
						found = false;
						break;
					}
				}
			}
			
			if(found)
				return Integer.parseInt(payoff[i][payoff[0].length - 1]);
		}
		
		return 0;
	}
	
	public static void main(String[] args) {
		SlotMachine sm = new SlotMachine();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == spinButton){
			if(currentBet > currentHoldings || currentBet < 1){
				JOptionPane.showMessageDialog(this, "Illegal Bet Amount!!\nBet should be greater than zero(0) and less than your current holdings(" + currentHoldings + ")..", "Illegal Bet", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			currentHoldings -= currentBet;
			for(Cell c: cells){
				c.changeIcon(isRegular);
			}
			
			currentHoldings += (currentBet * getPayoff());
			
			if(currentHoldings < 1){
				int choice = JOptionPane.showConfirmDialog(this, "You ran out of money!!\nDo you want to reset the game?", "Out of Money", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				if(choice == JOptionPane.NO_OPTION)
					System.exit(0);
				
				currentHoldings = 100;
				currentBet = 1;
				isRegular = true;
			}
			
			setComponents();
		}
		else if(ae.getSource() == regular){
			if(regular.isSelected())
				isRegular = true;
			else if (test.isSelected())
				isRegular = false;
		}
		else if(ae.getSource() == test){
			if(regular.isSelected())
				isRegular = true;
			else if (test.isSelected())
				isRegular = false;
		}
		else if(ae.getSource() == resetButton){
			int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to reset the game?", "Reset Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(choice == JOptionPane.NO_OPTION)
				return;
			
			currentHoldings = 100;
			currentBet = 1;
			isRegular = true;
			
			setComponents();
		}
	}

	@Override
	public void stateChanged(ChangeEvent ce) {
		if(ce.getSource() == betChooser){
			currentBet = Integer.parseInt(betChooser.getValue().toString());
		}
	}
	
}

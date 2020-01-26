package memory_matching_game;

public class Card {
	
	private int value;
	private boolean faceUp;
	
	public Card(int initValue) {
		this.value = initValue;
		this.faceUp = false;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public boolean isFaceUp() {
		return this.faceUp;
	}
	
	public void flipCard() {
		if(this.faceUp)
			this.faceUp = false;
		else
			this.faceUp = true;
	}
	
	public void setFaceUp() {
		this.faceUp = true;
	}
	
	public void setFaceDown() {
		this.faceUp = false;
	}
	
	public boolean equals(Card otherCard) {
		return (this.value == otherCard.getValue());
	}
	
	@Override
	public String toString() {
		if(this.faceUp)
			return Integer.toString(this.value);
		else
			return "*";
	}
	
}

package cards;

import roma.Game;

public class Kat extends Card {
	private final static int PRICE = 5;
	private final static int DEFENCE = 1;
	private final static String TITLE = "KAT";
	private final static CardType TYPE = CardType.CHARACTER;
	private final int NUM_LIVES = 9;
	private int remainingLives;
	
	public Kat() {
		super(PRICE,DEFENCE,TITLE,TYPE);
		remainingLives = NUM_LIVES;
	}

	@Override
	public void activate(Game g) {
		System.out.println("MIAOW");
	}
	
	public int getLives() {
		return remainingLives;
	}
	
	public void deductLife() {
		remainingLives--;
	}
	
	public void reanimate() {
		remainingLives = NUM_LIVES;
	}

}

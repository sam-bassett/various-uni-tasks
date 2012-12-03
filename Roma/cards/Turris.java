package cards;

import roma.*;

public class Turris extends Card {
	private final static int PRICE = 6;
	private final static int DEFENCE = 6;
	private final static String TITLE = "TURRIS";
	private final static CardType TYPE = CardType.BUILDING;

	public Turris() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	public void activate(Game g) {
		// DOES NOTHING
		System.out.println("Idiot.");
	}

}
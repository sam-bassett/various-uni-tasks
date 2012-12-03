package cards;

import roma.Game;

public class Templum extends Card {
	private final static int PRICE = 2;
	private final static int DEFENCE = 2;
	private final static String TITLE = "TEMPLUM";
	private final static CardType TYPE = CardType.BUILDING;
	
	
	public Templum() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	@Override
	public void activate(Game g) {
		//does nothing
	}

}

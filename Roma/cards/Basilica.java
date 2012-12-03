package cards;

import roma.Game;
import roma.Player;
import roma.UserUI;

public class Basilica extends Card {
	private final static int PRICE = 6;
	private final static int DEFENCE = 5;
	private final static String TITLE = "BASILICA";
	private final static CardType TYPE = CardType.BUILDING;
	private boolean activatable;

	public Basilica() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	// TODO: do this after tests
	@Override
	public void activate(Game g) {
		
	}
}

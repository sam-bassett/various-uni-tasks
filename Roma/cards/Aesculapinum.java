package cards;

import roma.*;

public class Aesculapinum extends Card {
	private final static int PRICE = 5;
	private final static int DEFENCE = 2;
	private final static String TITLE = "AESCULAPINUM";
	private final static CardType TYPE = CardType.BUILDING;

	public Aesculapinum() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	public void activate(Game g) {
		super.setUI(g.getUI());
		Player current = g.getCurrentPlayer();
		current.drawAnyCard("discard");
	}

}

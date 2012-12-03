package cards;

import roma.*;

public class Haruspex extends Card {
	private final static int PRICE = 4;
	private final static int DEFENCE = 3;
	private final static String TITLE = "HARUSPEX";
	private final static CardType TYPE = CardType.CHARACTER;

	public Haruspex() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	public void activate(Game g) {
		super.setUI(g.getUI());
		Player current = g.getCurrentPlayer();
		current.drawAnyCard("deck");
	}

}

package cards;

import roma.Game;
import roma.UserUI;

public class Onager extends Card {

	private final static int PRICE = 5;
	private final static int DEFENCE = 4;
	private final static String TITLE = "ONAGER";
	private final static CardType TYPE = CardType.BUILDING;

	public Onager() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	public void activate(Game g) {
		super.setUI(g.getUI());
		String cardName = "";
		boolean finished = false;
		while (!finished) {
			UI.promptForCard();
			cardName = UI.getString();
			cardName = cardName.toUpperCase();
			if (super.isBuilding(cardName)) {
				battle(g, cardName);
				finished = true;
			} else {
				System.out.println("Please enter a valid building card");
			}
		}
	}
}

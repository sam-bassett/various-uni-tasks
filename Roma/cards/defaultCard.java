package cards;

import roma.Game;

public class defaultCard extends Card {

	public defaultCard(int price, int defence, String title, CardType cardType) {
		super(price, defence, title, cardType);
	}

	public void activate(Game g) {
		// does nothing
	}
}

package cards;

import roma.Game;
import roma.UserUI;

public class Velites extends Card {
	private final static int PRICE = 5;
	private final static int DEFENCE = 3;
	private final static String TITLE = "VELITES";
	private final static CardType TYPE = CardType.CHARACTER;

	public Velites() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	@Override
	public void activate(Game g) {
		super.setUI(g.getUI());

		boolean finished = false;
		while (!finished) {
			String cardName = UI.getString();
			cardName = cardName.toUpperCase();
			if (super.isCharacter(cardName)) {
				finished = true;
				battle(g, cardName);
			} else {
				System.out.println("Please enter a valid character card");
			}
		}

	}

}

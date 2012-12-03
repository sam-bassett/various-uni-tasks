package cards;

import roma.Game;
import roma.Player;
import roma.UserUI;

public class Gladiator extends Card {
	private final static int PRICE = 6;
	private final static int DEFENCE = 5;
	private final static String TITLE = "GLADIATOR";
	private final static CardType TYPE = CardType.CHARACTER;

	public Gladiator() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	@Override
	public void activate(Game g) {
		// returns a face up character card to the opponent's hand
		super.setUI(g.getUI());
		Player opponent = g.getOpposingPlayer();
		boolean finished = false;
		while (!finished) {
			UI.promptForCard();
			String cardName = UI.getString();
			cardName = cardName.toUpperCase();
			if (super.isCharacter(cardName)) {
				// perform function
				opponent.returnToHand(cardName);
				finished = true;
			} else {
				System.out.println("Please enter a valid character card:");
			}
		}

	}

}

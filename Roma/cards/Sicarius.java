package cards;

import roma.Game;
import roma.Player;
import roma.UserUI;

public class Sicarius extends Card {

	private final static int PRICE = 9;
	private final static int DEFENCE = 2;
	private final static String TITLE = "SICARIUS";
	private final static CardType TYPE = CardType.CHARACTER;

	public Sicarius() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	// Sicarius destroys one of the opponent's face-up character cards,
	// then discards itself.

	public void activate(Game g) {
		super.setUI(g.getUI());
		Player currentPlayer = g.getCurrentPlayer();
		Player opponent = g.getOpposingPlayer();
		boolean isFinished = false;

		while (!isFinished) {
			UI.promptForCard();
			String cardName = UI.getString();
			cardName = cardName.toUpperCase();
			if (super.isCharacter(cardName)) {
				int cardIndex = opponent.getCardIndex(cardName);
				if (cardIndex != -1) {
					opponent.discard(cardIndex);
					cardIndex = currentPlayer.getCardIndex(TITLE);
					currentPlayer.discard(cardIndex);
					isFinished = true;
				}
			} else {
				System.out.println(cardName + " is not a character! please try again");
			}
		}
	}

}

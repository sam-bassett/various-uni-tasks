package cards;

import roma.Game;
import roma.Player;
import roma.UserUI;

public class Nero extends Card {
	private final static int PRICE = 8;
	private final static int DEFENCE = 9;
	private final static String TITLE = "NERO";
	private final static CardType TYPE = CardType.CHARACTER;

	public Nero() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	@Override
	public void activate(Game g) {
		// Same as Sicarius, but on a building card
		super.setUI(g.getUI());
		Player currentPlayer = g.getCurrentPlayer();
		Player opponent = g.getOpposingPlayer();
		boolean isFinished = false;

		while (!isFinished) {
			UI.promptForCard();
			String cardName = UI.getString();
			cardName = cardName.toUpperCase();
			if (super.isBuilding(cardName)) {
				int cardIndex = opponent.getCardIndex(cardName);
				if (cardIndex != -1) {
					opponent.discard(cardIndex);
					cardIndex = currentPlayer.getCardIndex(TITLE);
					currentPlayer.discard(cardIndex);
					isFinished = true;
				}
			} else {
				System.out.println("card is not a building! please try again");
			}
		}
	}

}

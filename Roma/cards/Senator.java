package cards;

import roma.Game;
import roma.Player;
import roma.UserUI;

public class Senator extends Card {
	private final static int PRICE = 3;
	private final static int DEFENCE = 3;
	private final static String TITLE = "SENATOR";
	private final static CardType TYPE = CardType.CHARACTER;

	public Senator() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	public void activate(Game g) {
		// Prompt user for cards until they enter "DONE"
		super.setUI(g.getUI());
		Player currentPlayer = g.getCurrentPlayer();
		boolean finished = false;
		while (finished == false) {
			UI.promptForCard();
			System.out.print("Or enter DONE to finish");
			String cardName = UI.getString();
			cardName = cardName.toUpperCase();
			if (cardName.compareTo("DONE") == 0) {
				finished = true;
			} else if (super.isCharacter(cardName)) {
				currentPlayer.playCard(cardName);
			} else {
				System.out.println("Not a character card!");
			}
		}
	}
}
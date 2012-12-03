package cards;

import roma.Game;
import roma.UI;
import roma.Player;

public class Centurio extends Card {
	private final static int PRICE = 9;
	private final static int DEFENCE = 5;
	private final static String TITLE = "CENTURIO";
	private final static CardType TYPE = CardType.CHARACTER;

	public Centurio() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	public void activate(Game g) {
		// attack the card directly opposite, can add an unused action die to
		// battle die
		// how will we access the unused action dice??? store them?
		super.setUI(g.getUI());
		Player current = g.getCurrentPlayer();
		int battleDice = super.rollBattleDice();
		Player opponent = g.getOpposingPlayer();
		int ownIndex = super.getDiscNum();

		System.out.println("You rolled a " + battleDice + "!");
		System.out
				.println("Would you like to add the value of an action dice?");
		if (UI.getBoolean()) {
			System.out.println("Dice: " + current.getDice());
			int diceNum = UI.getDiceNumber();
			battleDice += current.useDieNumber(diceNum);
		}
		opponent.defend(ownIndex, battleDice);
	}

}

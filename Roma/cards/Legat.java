package cards;

import roma.Game;
import roma.Player;

public class Legat extends Card {
	private final static int PRICE = 5;
	private final static int DEFENCE = 2;
	private final static String TITLE = "LEGAT";
	private final static CardType TYPE = CardType.CHARACTER;

	public Legat() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	public void activate(Game g) {
		// get 1 vic point for every unoccupied dice disc the opponent has

		Player current = g.getCurrentPlayer();
		Player opponent = g.getOpposingPlayer();

		int blanks = opponent.subtractBlanks();
		opponent.collectVictoryPoints(blanks);
		current.collectVictoryPoints(blanks);
	}

}

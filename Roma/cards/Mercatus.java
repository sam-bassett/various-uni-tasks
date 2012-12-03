package cards;

import roma.Game;
import roma.Player;

public class Mercatus extends Card {

	private final static int PRICE = 6;
	private final static int DEFENCE = 5;
	private final static String TITLE = "MERCATUS";
	private final static CardType TYPE = CardType.BUILDING;

	public Mercatus() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	public void activate(Game g) {
		// get 1 vic point for every FORUM the opponent has on their board
		super.setUI(g.getUI());
		Player currentPlayer = g.getCurrentPlayer();
		Player opponent = g.getOpposingPlayer();
		int numForums = opponent.getNumberCards("FORUM");
		opponent.subtractVictoryPoints(numForums);
		currentPlayer.collectVictoryPoints(numForums);
	}
}

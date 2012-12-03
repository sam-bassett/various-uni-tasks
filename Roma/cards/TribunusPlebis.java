package cards;

import roma.Game;
import roma.Player;

public class TribunusPlebis extends Card {
	private final static int PRICE = 5;
	private final static int DEFENCE = 5;
	private final static String TITLE = "TRIBUNUSPLEBIS";
	private final static CardType TYPE = CardType.CHARACTER;

	public TribunusPlebis() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	public void activate(Game g) {
		// steal 1 victory point from you opponent
		Player owner = g.getCurrentPlayer();
		Player opponent = g.getOpposingPlayer();
		opponent.subtractVictoryPoints(1);
		owner.collectVictoryPoints(1);
	}

}

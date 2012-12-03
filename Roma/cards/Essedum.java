package cards;

import roma.Game;
import roma.Player;

public class Essedum extends Card {
	private final static int PRICE = 6;
	private final static int DEFENCE = 3;
	private final static String TITLE = "ESSEDUM";
	private final static CardType TYPE = CardType.CHARACTER;
	private final int DEFENCE_MODIFIER = -2;

	public Essedum() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	@Override
	public void activate(Game g) {
		Player opponent = g.getOpposingPlayer();
		for (int i = 0; i < 7; i++) {
			opponent.setDefenceModifier(i, DEFENCE_MODIFIER);
		}
	}

}

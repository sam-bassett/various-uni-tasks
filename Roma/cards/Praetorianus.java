package cards;

import roma.Game;
import roma.Player;
import roma.UserUI;

public class Praetorianus extends Card {

	private final static int PRICE = 4;
	private final static int DEFENCE = 4;
	private final static String TITLE = "PRAETORIANUS";
	private final static CardType TYPE = CardType.CHARACTER;

	public Praetorianus() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	public void activate(Game g) {
		// block dice disc for one go
		// THAT'S SO DAMN ANNOYING
		// TODO: Do this when it's not so annoying
		super.setUI(g.getUI());
		Player opponent = g.getOpposingPlayer();
		int discNum = UI.getInt(0, 6);
		opponent.blockDisc(discNum);
		System.out.println("disc blocked!");
	}

}
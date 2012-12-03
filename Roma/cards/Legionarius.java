package cards;

import roma.Game;
import roma.Player;

public class Legionarius extends Card {

	private final static int PRICE = 4;
	private final static int DEFENCE = 5;
	private final static String TITLE = "LEGIONARIUS";
	private final static CardType TYPE = CardType.CHARACTER;

	public Legionarius() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	public void activate(Game g) {
		battle(g);
	}

	public void battle(Game g) {
		super.setUI(g.getUI());
		Player current = g.getCurrentPlayer();
		Player opponent = g.getOpposingPlayer();
		int battleDice = super.rollBattleDice();
		int ownIndex = super.getDiscNum();

		System.out.println("You rolled a " + battleDice + "!");
		System.out.println("attacking at " + ownIndex);
		System.out.println("attacking " + g.getOpposingPlayer().getBoard().getBoard()[ownIndex].getTitle());
		opponent.defend(ownIndex, battleDice);
	}

}

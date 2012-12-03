package cards;

import roma.Game;
import roma.Player;

public class Mercator extends Card {
	private final static int PRICE = 7;
	private final static int DEFENCE = 2;
	private final static String TITLE = "MERCATOR";
	private final static CardType TYPE = CardType.CHARACTER;

	public Mercator() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	@Override
	public void activate(Game g) {
		super.setUI(g.getUI());
		// for 2 sesterii each, player can buy 1 vic point from opp.
		Player current = g.getCurrentPlayer();
		Player opponent = g.getOpposingPlayer();
		int victoryPoints = UI.getInt(0, 36); // the amount f victory points being bought
		
		if (current.getMoney() >= 2 * victoryPoints) {
			// remaining money and vp don't change, just exchange p2p
			current.subtractMoney(2 * victoryPoints);
			opponent.collectMoney(2 * victoryPoints);
			opponent.subtractVictoryPoints(victoryPoints);
			current.collectVictoryPoints(victoryPoints);
		} else {
			System.out.println("Not enough sesterii to bargain with!");
		}
	}

}

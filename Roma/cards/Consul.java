package cards;

import roma.Game;
import roma.Player;
import roma.UserUI;

public class Consul extends Card {
	private final static int PRICE = 3;
	private final static int DEFENCE = 3;
	private final static String TITLE = "CONSUL";
	private final static CardType TYPE = CardType.CHARACTER;

	public Consul() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	@Override
	public void activate(Game g) {
		// Score on a dice can be increased or decreased by 1 point
		super.setUI(g.getUI());
		Player current = g.getCurrentPlayer();
		int diceNumber = UI.getDiceNumber();
		int diceVal = current.useDieNumber(diceNumber);
		System.out.println("Enter 1 to increase or -1 to decrease:");
		int offset = UI.getInt(-1, 1);
		current.giveDie(diceNumber, diceVal + offset);
	}

}

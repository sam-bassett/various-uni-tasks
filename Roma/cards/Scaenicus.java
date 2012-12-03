package cards;

import roma.Game;
import roma.Player;
import roma.UserUI;

public class Scaenicus extends Card {

	private final static int PRICE = 8;
	private final static int DEFENCE = 3;
	private final static String TITLE = "SCAENICUS";
	private final static CardType TYPE = CardType.CHARACTER;

	public Scaenicus() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	@Override
	public void activate(Game g) {
		super.setUI(g.getUI());
		Player current = g.getCurrentPlayer();
		Card toCopy = null;
		System.out.println("Enter a card to copy:");
		String cardName = UI.getString();
		// doesn't even need to be cloned! it can just be a reference.
		toCopy = current.cloneCard(cardName);
		int tempDiscNum = toCopy.getDiscNum();
		toCopy.setDiscNum(getDiscNum());
		System.out.println("You get to activate a " + toCopy.getTitle());
		toCopy.activate(g);
		toCopy.setDiscNum(tempDiscNum);
	}

}

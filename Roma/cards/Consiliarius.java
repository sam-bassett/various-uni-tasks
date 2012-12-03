package cards;

import roma.Game;
import roma.Player;
import roma.UserUI;

public class Consiliarius extends Card {
	private final static int PRICE = 4;
	private final static int DEFENCE = 4;
	private final static String TITLE = "CONSILIARIUS";
	private final static CardType TYPE = CardType.CHARACTER;

	public Consiliarius() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	@Override
	public void activate(Game g) {
		// TODO complete consiliarius
		// Player can pick up all his character cards and lay them again.
		// Dump board to an array...
		// Better idea. Create an array of 6 ints, this will hold the new
		// positions of each card. i.e. array[0] = 4 means that the card at
		// index 0 will be moved to index 4.

		// That seems to cause problems, dump board to an array,read off titles
		// of all those who aren't null (or no card). "Please enter new
		// location for x", stores new index at current index in array. Gives
		// this to player.board.
		super.setUI(g.getUI());
		Player current = g.getCurrentPlayer();
		String[] cardArray = current.dumpBoard();
		int[] newCardLocations = new int[cardArray.length];
		for (int i = 0; i < cardArray.length; i++) {
			if (cardArray[i] == null || super.isBuilding(cardArray[i])) {
				newCardLocations[i] = -1;
			} else {
				System.out.println("Please enter a new location for card "
						+ cardArray[i]);
				newCardLocations[i] = UI.getInt(0, 6);
			}
		}
		current.reshuffleBoard(newCardLocations);
	}

}

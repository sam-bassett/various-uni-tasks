package cards;

import roma.Game;
import roma.Player;

public class Forum extends Card {
	private final static int PRICE = 5;
	private final static int DEFENCE = 5;
	private final static String TITLE = "FORUM";
	private final static CardType TYPE = CardType.BUILDING;

	public Forum() {
		super(PRICE, DEFENCE, TITLE, TYPE);
	}

	@Override
	public void activate(Game g) {
		super.setUI(g.getUI());
		Player current = g.getCurrentPlayer();
		System.out.println(current.getDice());
		UI.promptForDice();
		int diceVal = -1;
		int diceNum = UI.getDiceNumber();
		diceVal = current.useDieNumber(diceNum);

		System.out.println("Adding " + diceVal + " VicPoints to player!");
		g.giveVicPoints(diceVal, current);
		activateOptions(g);
	}
	
	private void activateOptions(Game g) {
		Card[] board = g.getCurrentPlayer().getBoard().getBoard();
		int i = super.getDiscNum();
		super.setUI(g.getUI());
		Boolean activateTemplum = false;
		
		if (i - 1 >= 0 && board[i - 1] != null) {
			if (board[i - 1].getTitle().equals("BASILICA")) {
				System.out.println("You hava a Basilica on disc" + (i - 1) + " adding 2 victory points");
				g.giveVicPoints(2, g.getCurrentPlayer());
			} else if (board[i - 1].getTitle().equals("TEMPLUM")) {
				System.out.println("Would you like to activate your templum on disc" + (i - 1));
				activateTemplum = UI.getBoolean();
			}
		} if (i + 1 <= 6 && board[i + 1] != null) {
			if (board[i + 1].getTitle().equals("BASILICA")) {
				System.out.println("You hava a Basilica on disc" + (i + 1) + " adding 2 victory points");
				g.giveVicPoints(2, g.getCurrentPlayer());
			} else if (board[i + 1].getTitle().equals("TEMPLUM")) {
				System.out.println("Would you like to activate your templum on disc" + (i + 1));
				activateTemplum = UI.getBoolean();
			}
		}
		
		if (activateTemplum) {
			activateTemplum(g);
		}
	}
	
	private void activateTemplum(Game g) {
		super.setUI(g.getUI());
		Player current = g.getCurrentPlayer();
		System.out.println(current.getDice());
		UI.promptForDice();
		int diceVal = -1;
		int diceNum = UI.getDiceNumber();
		diceVal = current.useDieNumber(diceNum);

		System.out.println("Adding " + diceVal + " VicPoints to player!");
		g.giveVicPoints(diceVal, current);
	}

}

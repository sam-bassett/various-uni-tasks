package roma;

import cards.Card;

public class PlayerActions {
	public static final int LAY_CARD = 1;
	public static final int TAKE_MONEY = 2;
	public static final int DRAW_CARDS = 3;
	public static final int ACTIVATE_CARD = 4;
	public static final int BRIBE = 5;
	public static final int SHOW_HAND = 6;
	public static final int SHOW_BOARD = 7;
	public static final int END_TURN = 8;

	private  Game g;
	private  Player currentPlayer;
	private  UI input;
	
	public PlayerActions (Game g) {
		this.g = g;
		currentPlayer = g.getCurrentPlayer();
		input = g.getUI();
	}
	
	public void runAction(int action, Game g, UI input) {
		currentPlayer = g.getCurrentPlayer();
		if (action == LAY_CARD) {
			layCard(input.getCardName(), input.getInt(0, 6));
		} else if (action == TAKE_MONEY) {
			takeMoney(input.getDiceValue());
		} else if (action == DRAW_CARDS) {
			selectCard(activateCardsDisc(input.getDiceValue()),
					input.getCardName());
		} else if (action == ACTIVATE_CARD) {
			activateCard(input.getDiceDisc());
		} else if (action == BRIBE) {
			bribe(input.getDiceValue());
		} else if (action == SHOW_HAND) {
			currentPlayer.showHand();
		} else if (action == SHOW_BOARD) {
			Player opponent = g.getOpposingPlayer();
			opponent.printBoard();
			currentPlayer.printBoard();
		} else {
			endTurn();
		}
	}
	
	public Card[] activateCardsDisc(int diceToUse) {
		currentPlayer.useDie(diceToUse);
		Card[] drawnCards = new Card[diceToUse];
		for (int i = 0; i < diceToUse; i++) {
			drawnCards[i] = g.drawCard();
		}
		return drawnCards;
	}
	
	public void selectCard (Card[] from, String cardName) {
		for (int i = 0; i < from.length; i++) {
			if (from[i].getTitle().equals(cardName)) {
				currentPlayer.drawSpecificCard(from[i]);
			} else {
				g.discardCard(from[i]);
			}
		}
	}
	
	public void takeMoney (int diceToUse) {
		currentPlayer.useDie(diceToUse);
		g.giveMoney(diceToUse, currentPlayer);
	}
	
	public void layCard (String cardName, int indexTo) {
		// TODO make player UI-independent
		currentPlayer.playCard(cardName);
	}
	
	public void bribe (int diceToUse) {
		currentPlayer.useDie(diceToUse);
		g.giveMoney(-diceToUse, currentPlayer);
		currentPlayer.activateCard(g, diceToUse);
	}
	
	public void activateCard (int disc) {
		currentPlayer.activateCard(g, disc);
	}
	
	public void endTurn () {
		//TODO implement nextTurn in game
		g.nextTurn();
	}
}

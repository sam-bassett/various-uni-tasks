package roma;

import java.util.ArrayList;
import cards.Card;

public class Player {

	private final int USED_DICE = -1;

	private ArrayList<Card> hand;
	// maybe have a field here of 'active cards'?
	// or even a class - it has cards, it refreshes them periodically...

	private String playerName;
	private int victoryPoints;
	private int money;

	private Board board;
	private Deck deck;

	private int[] dice;
	private UI UI;

	public Player(String playerName, Deck deck, UI UI) {
		this.playerName = playerName;
		hand = new ArrayList<Card>();
		board = new Board(deck,UI,hand);
		this.deck = deck;
		dice = null;
		this.UI = UI;
	}

	public void drawCard() {
		Card c = deck.drawCard();
		hand.add(c);
		UI.promptDraw(c.getTitle());
	}

	public void drawSpecificCard(Card toAdd) {
		hand.add(toAdd);
		UI.promptDraw(toAdd.getTitle());
	}

	public void discard(int disc) {
		board.discard(disc);
	}

	public void playCard() {
		int indexFrom = UI.getCardIndex(hand);
		playCardFromIndex(indexFrom);
	}

	private void playCardFromIndex(int indexFrom) {
		Card toPlay = hand.get(indexFrom);
		if (money < toPlay.getPrice()) {
			System.out.println("Not enough sesterii!");
		} else {
			int indexTo = UI.getInt(0, 6);
			toPlay.setDiscNum(indexTo);
			board.playCard(toPlay, indexTo);
			hand.remove(indexFrom);
			money -= toPlay.getPrice();
		}
		System.out.println("played");
	}

	public void playCard(String card) {
		int indexFrom = UI.findCard(hand, card);
		playCardFromIndex(indexFrom);
	}

	public void showHand() {
		UI.showHand(hand);
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand (ArrayList<Card> hand) {
		this.hand = hand;
	}

	public void collectMoney(int amount) {
		money += amount;
	}

	public void subtractMoney(int amount) {
		money -= amount;
	}

	public void collectVictoryPoints(int amount) {
		victoryPoints += amount;
		if (victoryPoints < 0) {
			victoryPoints = 0;
		}
	}

	public void subtractVictoryPoints(int amount) {
		victoryPoints -= amount;
		if (victoryPoints < 0) {
			victoryPoints = 0;
		}
	}

	public int subtractBlanks() {
		int blanks = board.blankSpaces();
		if (victoryPoints < blanks) {
			blanks = victoryPoints;
		}
		victoryPoints -= blanks;
		return blanks;
	}

	public void returnToHand(String cardName) {
		Card toReturn = board.removeCard(cardName);
		hand.add(toReturn);
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}

	public void setVictoryPoints(int amount) {
		this.victoryPoints = amount;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int amount) {
		this.money = amount;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String name) {
		this.playerName = name;
	}

	public void defend(int defendingIndex, int battleDice) {
		board.defend(defendingIndex, battleDice);
	}

	public void activateCard(Game g, int disc) {
		board.activateCardAtDisc(disc, g);
	}

	public void blockDisc(int discNum) {
		board.blockDisc(discNum);
	}

	public void unblockDiscs() {
		board.unblockDiscs();
	}

	public int getNumberCards(String cardName) {
		return board.numberOfCards(cardName);
	}

	public int getCardIndex(String cardName) {
		return board.getCardIndex(cardName);
	}

	public String[] dumpBoard() {
		return board.dumpBoardTitles();
	}

	public void reshuffleBoard(int[] goalConfig) {
		board.reshuffle(goalConfig);
	}

	public void giveDice(int[] dice) {
		this.dice = dice;
	}

	public void giveDie(int diceNumber, int diceVal) {
		dice[diceNumber] = diceVal;
	}

	public int useDie (int diceVal) {
		int diceNumber = USED_DICE;
		for (int i = 0; i < 3; i++) {
			if (dice[i] == diceVal) {
				diceNumber = i;
			}
		}
		boolean finished = false;
		if (dice[0] == USED_DICE && dice[1] == USED_DICE
				&& dice[2] == USED_DICE) {
			System.out.println("All dice used!");
			finished = true;
			diceVal = 0;
		}
		while (!finished) {
			if (diceVal == USED_DICE) {
				System.out.println("Dice already used! "
						+ "Type 'exit' to not use any dice");
				diceNumber = UI.getDiceNumber();
			} else {
				dice[diceNumber] = USED_DICE;
				finished = true;
			}
		}
		return diceVal;

	}

	public int useDieNumber(int diceNumber) {
		int diceVal = dice[diceNumber];
		boolean finished = false;
		if (dice[0] == USED_DICE && dice[1] == USED_DICE
				&& dice[2] == USED_DICE) {
			System.out.println("All dice used!");
			finished = true;
			diceVal = 0;
		}
		while (!finished) {
			// TODO: Fix.
			diceVal = dice[diceNumber];
			if (diceVal == USED_DICE) {
				System.out.println("Dice already used! "
						+ "Type 'exit' to not use any dice");
				diceNumber = UI.getDiceNumber();
			} else {
				dice[diceNumber] = USED_DICE;
				finished = true;
			}
		}
		return diceVal;
	}

	public String getDice() {
		String dice = "";
		for (int i = 0; i < this.dice.length; i++) {
			if (this.dice[i] != -1) {
				dice += "Dice " + (i + 1) + ": ";
				dice += this.dice[i];
				dice += "\n";
			}
		}
		return dice;
	}

	public void printBoard() {
		System.out.println("\n\nPlayer " + playerName);
		board.printBoard();
	}

	public void layInitialCards() {
		printBoard();
		System.out.println(playerName + " lay your cards!");
		while (!hand.isEmpty()) {
			Card toLay = hand.get(0);
			System.out.println(toLay.getTitle() + ": where to lay this card?");
			int index = UI.getInt(0, 6);
			board.playCard(toLay, index);
			hand.remove(0);
		}
	}

	public Card[] swapCards() {
		System.out.println("Please enter two cards to swap:");

		int index1 = UI.getCardIndex(hand);
		System.out.println("And the other:");
		int index2 = UI.getCardIndex(hand);
		Card[] toSwap = new Card[2];
		toSwap[0] = hand.get(index1);
		toSwap[1] = hand.get(index2);
		hand.remove(index1);
		hand.remove(index2);
		return toSwap;
	}

	public boolean isOnBoard(String cardName) {
		return board.isOnBoard(cardName);
	}

	public void setDefenceModifier(int discNum, int modifier) {
		board.setDefenceModifier(discNum, modifier);
	}

	public Card cloneCard(String cardName) {
		return board.cloneCard(cardName);
	}

	// from must be either "deck" or "discard"
	public void drawAnyCard(String from) {
		deck.getCard(from, UI);
	}
	
	//from must be either "deck" or "discard"
	public String getTitleAtIndex(String from, int index) {
		return deck.getTitleAtIndex(from, index);
	}
	

	public void refreshDefenceModifiers() {
		board.refreshDefenceModifiers();
	}

	public Board getBoard() {
		return board;
	}

	public int[] getActionDice() {
		return dice;
	}

	public void setActionDice(int[] newDice) {
		dice = newDice;
	}
}

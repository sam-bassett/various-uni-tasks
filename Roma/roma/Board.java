package roma;

import cards.*;
import java.util.ArrayList;

public class Board {
	private static final int NUM_DISCS = 7;

	private Card[] board;

	private Deck deck;

	private boolean[] blockedDiscs;
	private UI UI;
	private ArrayList<Card> hand;

	public Board(Deck deck, UI ui, ArrayList<Card> hand) {
		board = new Card[NUM_DISCS];
		this.deck = deck;
		this.hand = hand;
		blockedDiscs = new boolean[NUM_DISCS];
		for (int i = 0; i < NUM_DISCS; i++) {
			blockedDiscs[i] = false;
		}
		UI = ui;
	}

	public void discard(int discNumber) {
		Card toDiscard = null;
		toDiscard = board[discNumber];
		if (isOnBoard("GRIMREAPER")
				&& !toDiscard.getTitle().equals("GRIMREAPER")) {
			// instead return to player's hand
			hand.add(toDiscard);
		} else {
			board[discNumber] = null;
			deck.discard(toDiscard);
		}
	}

	public Card removeCard(String cardName) {
		int index = getCardIndex(cardName);
		Card temp = board[index];
		board[index] = null;
		return temp;
	}

	public int getCardIndex(String cardName) {
		return UI.getCardIndex(board, cardName);
	}

	public boolean isOnBoard(String cardName) {
		boolean onBoard = false;
		for (int i = 0; i < NUM_DISCS; i++) {
			if (board[i] != null && board[i].getTitle().equals(cardName)) {
				onBoard = true;
			}
		}
		return onBoard;
	}

	public int blankSpaces() {
		int count;
		int blanks = 0;
		for (count = 0; count < NUM_DISCS; count++) {
			if (board[count] == null) {
				blanks++;
			}
		}

		return blanks;
	}

	public void playCard(Card c, int position) {
		deck.discard(board[position]);
		if (c.getTitle().equals("TURRIS")) {
			for (int i = 0; i < NUM_DISCS; i++) {
				setDefenceModifier(i, 1);
			}
		}
		board[position] = c;
	}

	public Card[] getBoard() {
		return board;
	}

	public void activateCardAtDisc(int disc, Game g) {
		if (board[disc] != null && !blockedDiscs[disc]) {
			board[disc].activate(g);
		} else {
			// TODO: If player makes a mistake, don't punish them so harshly
			System.out.println("No card found! Turn wasted!");
		}
	}

	public void defend(int defendingIndex, int battleDice) {
		Card defending = board[defendingIndex];
		if (defending != null && defending.isDestroyed(battleDice)) {
			if (defending.getTitle().equals("KAT")) {
				Kat k = (Kat) defending;
				k.deductLife();
				if (k.getLives() == 0) {
					k.reanimate();
					discard(defendingIndex);
				} else {
					System.out.println("Miaow!");
				}
			} else {
				discard(defendingIndex);
			}
			UI.notifySuccessfulAttack();
		} else {
			UI.notifyFailedAttack();
		}
	}

	public int numberOfCards(String cardName) {
		int count = 0;
		for (int i = 0; i < NUM_DISCS; i++) {
			if (board[i] != null && board[i].sameTitle(cardName)) {
				count++;
			}
		}
		return count;
	}

	public void reshuffle(int[] goalConfig) {
		assert (goalConfig.length == NUM_DISCS);
		Card[] newBoard = board.clone();
		for (int i = 0; i < goalConfig.length; i++) {
			if (goalConfig[i] != -1) {
				Card temp = board[i];
				newBoard[goalConfig[i]] = temp;
			}
		}
		board = newBoard;
	}

	public String[] dumpBoardTitles() {
		String[] titles = new String[NUM_DISCS];
		for (int i = 0; i < NUM_DISCS; i++) {
			if (titles[i] != null) {
				titles[i] = board[i].getTitle();
			} else {
				titles[i] = "No card";
			}
		}
		return titles;
	}

	public void printBoard() {
		UI.showBoard(board);
	}

	public void blockDisc(int discNum) {
		blockedDiscs[discNum] = true;
	}

	public void unblockDiscs() {
		for (int i = 0; i < NUM_DISCS; i++) {
			blockedDiscs[i] = false;
		}
	}

	public void setDefenceModifier(int discNum, int modifier) {
		if (board[discNum] != null) {
			board[discNum].setDefenceMod(modifier);
		}
	}

	public void refreshDefenceModifiers() {
		int numTurris = 0;
		for (int i = 0; i < NUM_DISCS; i++) {
			Card temp = board[i];
			if (temp != null) {
				temp.setDefenceMod(-temp.getDefenceMod());
				if (temp.getTitle().equals("TURRIS")) {
					numTurris++;
				}
			}
		}
		for (int i = 0; i < NUM_DISCS; i++) {
			if (board[i] != null) {
				if (board[i].getTitle().equals("TURRIS")) {
					board[i].setDefenceMod(numTurris - 1);
				} else {
					board[i].setDefenceMod(numTurris);
				}
			}
		}
	}

	public Card cloneCard(String cardName) {
		int index = getCardIndex(cardName);
		return board[index];
	}
	
	public Card[] getPlayedCards() {
		Card[] cards = new Card[NUM_DISCS];
		for (int i = 1; i < NUM_DISCS; i++) {
			cards[i-1] = board[i];
		}
		cards[NUM_DISCS - 1] = board[0];
		return cards;
	}
	
	public void setPlayedCards(Card[] played) {
		for (int i = 1; i < NUM_DISCS; i++) {
			board[i] = played[i-1];
		}
		board[0] = played[NUM_DISCS - 1];
	}
}

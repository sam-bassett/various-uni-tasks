package roma;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;

import cards.Card;

public class TestUI implements UI {
	private IntQue diceNumbers;
	private String cardNames;
	private int newInts;
	private int newDiceDiscs;
	private int diceValues;
	private boolean bools;
	
	public TestUI() {
		diceNumbers = new IntQue(10);
	}
	
	public void clearUI() {
		diceNumbers.clear();
	}
	
	public void setDiceNumber(int number) {
		diceNumbers.push(number);
	}

	@Override
	public int getDiceNumber() {
		return diceNumbers.pop();
	}

	public void setCardName(String cardName) {
		this.cardNames = cardName;
	}

	@Override
	public String getString() {
		return cardNames;
	}

	public String getCardName() {
		return cardNames;
	}

	public int getDiceDisc() {
		return newDiceDiscs;
	}

	public int getBattleDie() {
		return diceValues;
	}
	
	public void setBattleDie(int battleDie) {
		diceValues = battleDie;
	}
	
	public int getDiceValue() {
		return diceValues;
	}

	public void setDiceValue(int to) {
		diceValues = to;
	}

	public void setDiceDisc(int to) {
		newDiceDiscs = to;
	}

	public void setInt (int toSet) {
		newInts = toSet;
	}

	@Override
	public int getInt (int min, int max) {
		return newInts;
	}

	@Override
	public void showRules() {
		// TODO Auto-generated method stub

	}

	@Override
	public void printAttributes(Player p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showHand(List<Card> hand) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showBoard(Card[] board) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCardIndex(List<Card> cards) {
		promptForCard();
		int ret = -1;
		while (ret == -1) {
			String cardName = this.cardNames;
			cardName = cardName.toUpperCase();
			ret = findCard(cards, cardName);
			if (ret == -1) {
				System.out.println("card not found, please enter a valid card");
			}
		}

		return ret;
	}

	@Override
	public int getCardIndex(Card[] cards, String cardName) {
		ArrayList<Card> temp = new ArrayList<Card>();
		for (int i = 0; i < cards.length; i++) {
			temp.add(cards[i]);
		}
		int ret = -1;
		cardName = cardName.toUpperCase();
		ret = findCard(temp, cardName);
		if (ret == -1) {
			System.out.println("card not found, please enter a valid card");
		}

		return ret;
	}

	@Override
	public int findCard(List<Card> cards, String cardName) {
		// TODO this probably introduces horrible bugs. fix it.
		boolean cardFound = false;
		int index = -1;

		Iterator<Card> iterator = cards.iterator();

		for (int i = 0; iterator.hasNext() && i < cards.size() && !cardFound; i++) {
			Card current = cards.get(i);
			if (current != null && current.getTitle().contains(cardName)) {
				System.out.println("Card " + current.getTitle() + " found!");
				cardFound = true;
				index = i;
			}
		}
		return index;
	}

	@Override
	public boolean getBoolean() {
		// TODO Auto-generated method stub
		return bools;
	}

	public void setBoolean (boolean b) {
		this.bools = b;
	}

	@Override
	public void promptPhaseThree() {
		// TODO Auto-generated method stub

	}

	@Override
	public void promptForCard() {
		// TODO Auto-generated method stub

	}

	@Override
	public void promptRethrow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void promptForInt(int from, int to) {
		// TODO Auto-generated method stub

	}

	@Override
	public void promptForUsername() {
		// TODO Auto-generated method stub

	}

	@Override
	public void promptForDice() {
		// TODO Auto-generated method stub

	}

	@Override
	public void promptDiscToActivate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void promptDraw(String title) {
		// TODO Auto-generated method stub

	}

	@Override
	public void promptGameStart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifySuccessfulAttack() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyFailedAttack() {
		// TODO Auto-generated method stub

	}
}

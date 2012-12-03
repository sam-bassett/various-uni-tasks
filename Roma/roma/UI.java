package roma;

import java.util.List;

import cards.Card;

public interface UI {

	public void clearUI();
	
	public abstract void showRules();

	public abstract void printAttributes(Player p);

	public abstract void showHand(List<Card> hand);

	public abstract void showBoard(Card[] board);

	public abstract String getString();

	public abstract int getInt(int min, int max);

	public abstract int getBattleDie();
	
	public abstract void setBattleDie(int battleDie);
	
	public abstract int getDiceNumber();

	public abstract int getCardIndex(List<Card> cards);

	public abstract int getCardIndex(Card[] cards, String cardName);

	public abstract int findCard(List<Card> cards, String cardName);

	public abstract boolean getBoolean();

	public abstract void setBoolean(boolean newBool);
	
	public abstract void promptPhaseThree();

	public abstract void promptForCard();

	public abstract void promptRethrow();

	public abstract void promptForInt(int from, int to);

	public abstract void promptForUsername();

	public abstract void promptForDice();

	public abstract void promptDiscToActivate();

	public abstract void promptDraw(String title);

	public abstract void promptGameStart();

	public abstract void notifySuccessfulAttack();

	public abstract void notifyFailedAttack();
	
	public abstract void setDiceNumber(int dice);
	
	public abstract void setCardName(String cardName);
	
	public abstract int getDiceValue();
	
	public abstract void setDiceValue(int to);
	
	public abstract int getDiceDisc();
	
	public abstract void setDiceDisc(int to);
	
	public abstract void setInt(int to);
	
	public abstract String getCardName();

}
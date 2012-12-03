package testImplementation;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import roma.*;

import java.util.ArrayList;

import framework.cards.Card;
import framework.interfaces.GameState;

public class GameStateAdaptor implements GameState {

	private Game g;

	public GameStateAdaptor (Game g) {
		this.g = g;
	}

	@Override
	public int getWhoseTurn() {
		// TODO Auto-generated method stub
		return g.getPlayerTurn();
	}

	@Override
	public void setWhoseTurn(int player) {
		// TODO Auto-generated method stub
		g.setPlayerTurn(player);
	}

	@Override
	public List<Card> getDeck() {
		Deck deck = g.getDeck();
		return CardAdaptors.deckAdaptorTo(deck);
	}

	@Override
	public void setDeck(List<Card> deck) {
		Deck ourDeck = g.getDeck();
		ourDeck.setDeck(CardAdaptors.deckAdaptorFrom(deck));
	}


	@Override
	public List<Card> getDiscard() {
		Deck discard = g.getDeck();
		return CardAdaptors.discardAdaptorTo(discard);
	}

	@Override
	public void setDiscard(List<Card> discard) {
		Deck ourDiscard = g.getDeck();
		ourDiscard.setDiscard(CardAdaptors.deckAdaptorFrom(discard));
	}

	@Override
	public int getPlayerSestertii(int playerNum) {
		Player p = g.getPlayer(playerNum);
		return p.getMoney();
	}

	@Override
	public void setPlayerSestertii(int playerNum, int amount) {
		Player p = g.getPlayer(playerNum);
		p.setMoney(amount);
	}

	@Override
	public int getPlayerVictoryPoints(int playerNum) {
		Player p = g.getPlayer(playerNum);
		return p.getVictoryPoints();
	}

	@Override
	public void setPlayerVictoryPoints(int playerNum, int points) {
		Player p = g.getPlayer(playerNum);
		g.giveVicPoints(-p.getVictoryPoints(), p);
		g.giveVicPoints(points, p);
	}

	@Override
	public Collection<Card> getPlayerHand(int playerNum) {
		Player p = g.getPlayer(playerNum);
		Collection<Card> adapted = new ArrayList<Card>();
		cards.Card current = null;
		for (int i = 0; i < p.getHand().size(); i++) {
			current = p.getHand().get(i);
			adapted.add(CardAdaptors.cardAdaptorToThem(current));
		}
		return adapted;
	}

	@Override
	public void setPlayerHand(int playerNum, Collection<Card> hand) {
		// TODO Auto-generated method stub
		Player p = g.getPlayer(playerNum);
		ArrayList<cards.Card> ourHand = new ArrayList<cards.Card>();
		cards.Card current = null;
		Iterator<Card> theirHand = hand.iterator();
		while (theirHand.hasNext()) {
			current = CardAdaptors.cardAdaptorFromThem(theirHand.next());
			ourHand.add(current);
		}
		p.setHand(ourHand);
	}

	@Override
	public Card[] getPlayerCardsOnDiscs(int playerNum) {
		Player p = g.getPlayer(playerNum);
		Board board = p.getBoard();
		cards.Card cards[] = board.getPlayedCards();
		Card theirCards[] = new Card[7];
		
		int i;
		for (i = 0; i < 7; i++) {
			theirCards[i] = CardAdaptors.cardAdaptorToThem(cards[i]);
		}
		return theirCards;
	}

	@Override
	public void setPlayerCardsOnDiscs(int playerNum, Card[] discCards) {
		Player p = g.getPlayer(playerNum);
		Board board = p.getBoard();
		cards.Card[] ourCards = new cards.Card[7];
		
		int i;
		for (i = 0; i < 7; i++) {
			ourCards[i] = CardAdaptors.cardAdaptorFromThem(discCards[i]);
			if (ourCards[i] != null) {
				ourCards[i].setDiscNum(i + 1);
			}
		}
		board.setPlayedCards(ourCards);
	}

	@Override
	public int[] getActionDice() {
		Player current = g.getCurrentPlayer();
		int[] dice = current.getActionDice();
		return DiceAdaptor.removeUsedDice(dice);
	}

	@Override
	public void setActionDice(int[] dice) {
		Player current = g.getCurrentPlayer();
		current.setActionDice(dice);
	}

	@Override
	public int getPoolVictoryPoints() {
		return g.getRemainingVictoryPoints();
	}

	@Override
	public boolean isGameCompleted() {
		return g.isGameFinished();
	}
}

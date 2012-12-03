package roma;

import java.util.*;
import cards.*;

public class Deck {

	// private static final int NUM_CARDS = 52;
	private static final int[] CARDS_TO_ADD = { 1, // Sicarius
			2, // Architectus
			2, // Consili
			2, // Legat
			2, // Gladi
			1, // Merc
			2, // Consul
			3, // Legion
			1, // Nero
			2, // Prae
			2, // Scae
			2, // Haru
			2, // Senator
			2, // Velites
			2, // Essedum
			2, // Tribunus
			2, // Centurio ====END OF CHARACTER CARDS====
			2, 2, 2, 6, 2, 2, 2, 2,
			2, // Kat
			1}; //Grim

	private List<Card> deck;
	private List<Card> discard;

	public Deck() {
		deck = new Stack<Card>();
		discard = new ArrayList<Card>();
		initDeck();
	}

	private void initDeck() {
		// this needs to fill the deck with actual cards.
		// variable numbers of each card, listed below:

		// CHARACTERS:
		// Sicarius x1, Architectus x2, Consiliarius x2,
		// Legat x2, Gladiator x2, Mercator x1, Consul x2,
		// Legionarius x3, Nero x1, Praetorianus x2,
		// Scaenicus x2, Haruspex x2, Senator x2, Velites x2,
		// Essedum x2, Tribunus x2, Centurio x2
		// BUILDINGS:
		// Aesculapinum x2, Basilica x2, Machina x2, Forum x6,
		// Mercatus x2, Onager x2, Templum x2, Turris x2.
		for (int i = 0; i < CARDS_TO_ADD.length; i++) {
			// add each card CARDS_TO_ADD[i] times.
			for (int j = 0; j < CARDS_TO_ADD[i]; j++) {
				addCardToDeck(i);
			}
		}

		Collections.shuffle(deck);
	}

	private void addCardToDeck(int cardID) {
		// TODO: add rest of cards
		Card toAdd = null;
		if (cardID == 0) {
			toAdd = new Sicarius();
		} else if (cardID == 1) {
			toAdd = new Architectus();
		} else if (cardID == 2) {
			toAdd = new Consiliarius();
		} else if (cardID == 3) {
			toAdd = new Legat();
		} else if (cardID == 4) {
			toAdd = new Gladiator();
		} else if (cardID == 5) {
			toAdd = new Mercator();
		} else if (cardID == 6) {
			toAdd = new Consul();
		} else if (cardID == 7) {
			toAdd = new Legionarius();
		} else if (cardID == 8) {
			toAdd = new Nero();
		} else if (cardID == 9) {
			toAdd = new Praetorianus();
		} else if (cardID == 10) {
			toAdd = new Scaenicus();
		} else if (cardID == 11) {
			toAdd = new Haruspex();
		} else if (cardID == 12) {
			toAdd = new Senator();
		} else if (cardID == 13) {
			toAdd = new Velites();
		} else if (cardID == 14) {
			toAdd = new Essedum();
		} else if (cardID == 15) {
			toAdd = new TribunusPlebis();
		} else if (cardID == 16) {
			toAdd = new Centurio();

			// END CHARACTERS
		} else if (cardID == 17) {
			toAdd = new Aesculapinum();
		} else if (cardID == 18) {
			toAdd = new Basilica();
		} else if (cardID == 19) {
			toAdd = new Machina();
		} else if (cardID == 20) {
			toAdd = new Forum();
		} else if (cardID == 21) {
			toAdd = new Mercatus();
		} else if (cardID == 22) {
			toAdd = new Onager();
		} else if (cardID == 23) {
			toAdd = new Templum();
		} else if (cardID == 24) {
			toAdd = new Turris();
		} else if (cardID == 25) {
			toAdd = new Kat();
		} else if (cardID == 26) {
			toAdd = new GrimReaper();
		} else {
			System.out.println("PROBLEM");
			System.out.println(cardID);
			System.exit(0);
		}
		deck.add(toAdd);

	}

	public Card drawCard() {
		Card toReturn = deck.get(0);
		deck = deck.subList(1, deck.size());
		return toReturn;
	}

	public void shuffleDiscardToDeck() {
		while (!discard.isEmpty()) {
			deck.add(discard.get(0));
			discard = discard.subList(1, discard.size());
		}
		Collections.shuffle(deck);
	}

	public void discard(Card c) {
		if (c != null) {
			c.setDiscNum(-1);
			discard.add(c);
		}
	}

	public boolean isEmpty() {
		return deck.isEmpty();
	}

	public int getDeckSize() {
		return deck.size();
	}

	public int getDiscardSize() {
		return discard.size();
	}

	public Card getCard(String from, UI UI) {
		List<Card> cards = null;
		if (from.equals("deck")) {
			cards = deck;
		} else if (from.equals("discard")) {
			cards = discard;
		} else {
			System.out.println("please only enter \"deck\" or \"discard\"");
		}
		int index = UI.getCardIndex(cards);
		Card found = cards.get(index);
		cards.remove(index);
		return found;
	}
	
	public String getTitleAtIndex(String from, int index) {
		if (from.equals("deck")) {
			return deck.get(index).getTitle();
		} else if (from.equals("discard")) {
			return discard.get(index).getTitle();
		}
		return null;	//invalid
	}
	
	public void setDeck (List<Card> deck) {
		this.deck = deck;
	}
	
	public List<Card> getDeck() {
		return this.deck;
	}
	
	public void setDiscard (List<Card> discard) {
		this.discard = discard;
	}
	
	public List<Card> getDiscard() {
		return this.discard;
	}
}

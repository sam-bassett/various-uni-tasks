package testImplementation;

import java.util.List;
import java.util.Stack;

import cards.*;
import framework.cards.Card;
import roma.Deck;
import roma.Game;

public class CardAdaptors {
	
	public static Card cardAdaptorToThem(cards.Card ourCard){
		if (ourCard == null) {
			return Card.NOT_A_CARD;
		}
		
		for (Card theirCard : Card.values()) {
			if (theirCard.toString().toUpperCase().equals(ourCard.getTitle())) {
				return theirCard;
			}
		}
		System.out.println("error!!!!");
		System.exit(0);
		return null;
	}

	public static List<Card> deckAdaptorTo(Deck d) {
		int i = 0;
		cards.Card current = null;
		List<cards.Card> deck = d.getDeck();
		List<Card> newDeck = new Stack<Card>();
		while (i != deck.size()) {
			current = deck.get(i);
			newDeck.add(cardAdaptorToThem(current));	
			i++;
		}

		return newDeck;
	}
	
	public static List<cards.Card> deckAdaptorFrom (List<Card> theirDeck) {
		List<cards.Card> ourDeck = new Stack<cards.Card>();
		for (int i = 0; i < theirDeck.size(); i++) {
			ourDeck.add(cardAdaptorFromThem(theirDeck.get(i)));
		}
		
		return ourDeck;
	}

	public static List<Card> discardAdaptorTo(Deck d) {
		int i = 0;
		cards.Card current = null;
		List<cards.Card> discard = d.getDiscard();
		List<Card> newDiscard = new Stack<Card>();
		while (i != discard.size()) {
			current = discard.get(i);
			newDiscard.add(cardAdaptorToThem(current));		
			i++;
		}

		return newDiscard;
	}

	public static cards.Card cardAdaptorFromThem(Card theirCard) {

		cards.Card ourCard = null;
		if (theirCard.toString().equals("Sicarius")) {
			ourCard = new Sicarius();
		} else if (theirCard.toString().equals("Architectus")) {
			ourCard = new Architectus();
		} else if (theirCard.toString().equals("Consiliarius")) {
			ourCard = new Consiliarius();
		} else if (theirCard.toString().equals("Legat")) {
			ourCard = new Legat();
		} else if (theirCard.toString().equals("Gladiator")) {
			ourCard = new Gladiator();
		} else if (theirCard.toString().equals("Mercator")) {
			ourCard = new Mercator();
		} else if (theirCard.toString().equals("Consul")) {
			ourCard = new Consul();
		} else if (theirCard.toString().equals("Legionarius")) {
			ourCard = new Legionarius();
		} else if (theirCard.toString().equals("Nero")) {
			ourCard = new Nero();
		} else if (theirCard.toString().equals("Praetorianus")) {
			ourCard = new Praetorianus();
		} else if (theirCard.toString().equals("Scaenicus")) {
			ourCard = new Scaenicus();
		} else if (theirCard.toString().equals("Haruspex")) {
			ourCard = new Haruspex();
		} else if (theirCard.toString().equals("Senator")) {
			ourCard = new Senator();
		} else if (theirCard.toString().equals("Velites")) {
			ourCard = new Velites();
		} else if (theirCard.toString().equals("Essedum")) {
			ourCard = new Essedum();
		} else if (theirCard.toString().equals("Tribunusplebis")) {
			ourCard = new TribunusPlebis();
		} else if (theirCard.toString().equals("Centurio")) {
			ourCard = new Centurio();
		} else if (theirCard.toString().equals("Kat")) {
			ourCard = new Kat();
		} else if (theirCard.toString().equals("Grimreaper")) {
			ourCard = new GrimReaper();

			// END CHARACTERS
		} else if (theirCard.toString().equals("Aesculapinum")) {
			ourCard = new Aesculapinum();
		} else if (theirCard.toString().equals("Basilica")) {
			ourCard = new Basilica();
		} else if (theirCard.toString().equals("Machina")) {
			ourCard = new Machina();
		} else if (theirCard.toString().equals("Forum")) {
			ourCard = new Forum();
		} else if (theirCard.toString().equals("Mercatus")) {
			ourCard = new Mercatus();
		} else if (theirCard.toString().equals("Onager")) {
			ourCard = new Onager();
		} else if (theirCard.toString().equals("Templum")) {
			ourCard = new Templum();
		} else if (theirCard.toString().equals("Turris")) {
			ourCard = new Turris();
			
		} else if (theirCard.toString().equals("Not A Card")) {
			ourCard = null;
		} else {
			System.out.println("testImplementation.CardAdaprtors: unrecognised card...");
			System.out.println(theirCard.toString());
		}
		return ourCard;
	}
	
}
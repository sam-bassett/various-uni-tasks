package testImplementation;

import cards.Aesculapinum;
import cards.Architectus;
import cards.CardType;
import cards.Centurio;
import cards.Consiliarius;
import cards.Consul;
import cards.Essedum;
import cards.Forum;
import cards.Gladiator;
import cards.Haruspex;
import cards.Legat;
import cards.Legionarius;
import cards.Machina;
import cards.Mercator;
import cards.Mercatus;
import cards.Nero;
import cards.Onager;
import cards.Praetorianus;
import cards.Scaenicus;
import cards.Senator;
import cards.Sicarius;
import cards.TribunusPlebis;
import cards.Turris;
import cards.Velites;
import cards.defaultCard;
import framework.Rules;
import framework.cards.Card;
import framework.interfaces.MoveMaker;
import framework.interfaces.activators.*;
import roma.*;

public class MoveMakerAdaptor implements MoveMaker {
	
	private Game g;
	private UI testUI;
	// can assume g is always in testing mode , as that's how we're going to do it.
	public MoveMakerAdaptor (Game g) {
		this.g = g;
		testUI = g.getUI();
	}

	@Override
	public CardActivator chooseCardToActivate(int disc)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		// Get card at disc, convert to cardActivator (which should
		// hold a pointer to game, a pointer to card, a method called
		// 'complete' and anything else the specific card needs.
		Player p = g.getCurrentPlayer();
		cards.Card c = p.getBoard().getBoard()[disc];
		CardActivator ca = generateActivator(c, disc);
		return ca;
	}

	@Override
	public void activateCardsDisc(int diceToUse, Card chosen)
			throws UnsupportedOperationException {
		testUI.setDiceValue(diceToUse);
		testUI.setCardName(chosen.toString().toUpperCase());
		g.runAction(PlayerActions.DRAW_CARDS, testUI);
	}

	@Override
	public void activateMoneyDisc(int diceToUse)
			throws UnsupportedOperationException {
		testUI.setDiceValue(diceToUse);
		g.runAction(PlayerActions.TAKE_MONEY, testUI);
	}

	@Override
	public CardActivator activateBribeDisc(int diceToUse)
			throws UnsupportedOperationException {
		// TODO fix this
		Player p = g.getCurrentPlayer();
		cards.Card c = p.getBoard().getBoard()[0];
		p.subtractMoney(diceToUse);
		CardActivator ca = generateActivator(c, 0);
		return ca;
	}

	@Override
	public void endTurn() throws UnsupportedOperationException {
		g.runAction(PlayerActions.END_TURN, testUI);
		g.phaseOne(g.getCurrentPlayer());
	}

	@Override
	public void placeCard(Card toPlace, int discToPlaceOn)
			throws UnsupportedOperationException {
		if (discToPlaceOn == Rules.BRIBE_DISC) {
			discToPlaceOn = 0;
		}
		
		testUI.setCardName(toPlace.toString().toUpperCase());
		testUI.setInt(discToPlaceOn);
		g.runAction(PlayerActions.LAY_CARD, testUI);
	}
	
	private CardActivator generateActivator (cards.Card c, int position) {
		CardActivator ca;
		ca = CardActivatorFactory.getActivator(c.getTitle(), position, g);
		return ca;
	}

}

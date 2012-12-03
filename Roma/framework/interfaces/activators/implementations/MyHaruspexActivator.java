package framework.interfaces.activators.implementations;

import roma.Game;
import roma.Player;
import roma.UI;
import framework.interfaces.activators.HaruspexActivator;

public class MyHaruspexActivator implements HaruspexActivator {

	private int position;
	private Game state;
	private int cardIndex;
	
	public MyHaruspexActivator(int position, Game state) {
		this.position = position;
		this.state = state;
		this.cardIndex = -1;
	}

	@Override
	public void chooseCardFromPile(int indexOfCard) {
		this.cardIndex = indexOfCard;
	}

	@Override
	public void complete() {
		//use action die
		Player current = state.getCurrentPlayer();
		current.useDie(position);
		//get card to activate
		cards.Card me = current.getBoard().getBoard()[position];
		//get card to draw
		String toDraw = current.getTitleAtIndex("deck", cardIndex);
		
		//setup UI
		UI input = state.getUI();
		input.clearUI();
		input.setCardName(toDraw);
		
		//activate
		me.activate(state);
	}
		
}

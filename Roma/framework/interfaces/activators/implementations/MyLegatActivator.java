package framework.interfaces.activators.implementations;

import roma.*;
import framework.interfaces.activators.LegatActivator;

public class MyLegatActivator implements LegatActivator {

	private int position;
	private Game state;
	
	public MyLegatActivator(int position, Game state) {
		this.position = position;
		this.state = state;
	}

	@Override
	public void complete() {
		//use activation die
		Player current = state.getCurrentPlayer();
		current.useDie(position);
		//get card to activate
		cards.Card me = current.getBoard().getBoard()[position];
		//activate
		me.activate(state);
	}

}

package framework.interfaces.activators.implementations;

import roma.*;
import framework.interfaces.activators.MercatusActivator;

public class MyMercatusActivator implements MercatusActivator {

	private int position;
	private Game state;
	
	public MyMercatusActivator(int position, Game state) {
		this.position = position;
		this.state = state;
	}

	@Override
	public void complete() {
		//use activation die
		Player current = state.getCurrentPlayer();
		current.useDie(position);
		//get Card to activate
		cards.Card me = current.getBoard().getBoard()[position];
		//activate
		me.activate(state);
	}

}

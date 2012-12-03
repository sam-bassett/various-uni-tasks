package framework.interfaces.activators.implementations;

import roma.*;
import framework.interfaces.activators.TribunusPlebisActivator;

public class MyTribunusPlebisActivator implements TribunusPlebisActivator {

	private int position;
	private Game state;
	
	public MyTribunusPlebisActivator(int position, Game state) {
		this.position = position;
		this.state = state;
	}

	@Override
	public void complete() {
	//use activation die
	state.getCurrentPlayer().useDie(position);
	//find card to activate
	cards.Card me = state.getCurrentPlayer().getBoard().getBoard()[position];
	//activate
	me.activate(state);
	}

}

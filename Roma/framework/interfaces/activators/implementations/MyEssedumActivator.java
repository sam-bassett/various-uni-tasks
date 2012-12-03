package framework.interfaces.activators.implementations;

import roma.*;
import framework.interfaces.activators.EssedumActivator;

public class MyEssedumActivator implements EssedumActivator {

	private int position;
	private Game state;
	
	public MyEssedumActivator(int position, Game state) {
		this.position = position;
		this.state = state;
	}

	@Override
	public void complete() {
		//use action die
		Player current = state.getCurrentPlayer();
		current.useDie(position);
		//get card to activate
		cards.Card me = current.getBoard().getBoard()[position];
		//activate
		me.activate(state);
	}

}

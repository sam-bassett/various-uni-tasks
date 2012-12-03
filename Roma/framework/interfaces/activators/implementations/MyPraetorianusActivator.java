package framework.interfaces.activators.implementations;

import roma.*;
import framework.interfaces.activators.PraetorianusActivator;

public class MyPraetorianusActivator implements PraetorianusActivator {

	private int position;
	private Game state;
	private int discToBlock;
	
	public MyPraetorianusActivator(int position, Game state) {
		this.position = position;
		this.state = state;
		this.discToBlock = -1;
	}

	@Override
	public void chooseDiceDisc(int diceDisc) {
		discToBlock = diceDisc;
	}

	@Override
	public void complete() {
		//use activation die
		Player current = state.getCurrentPlayer();
		current.useDie(position);
		//get card to activate
		cards.Card me = current.getBoard().getBoard()[position];
		
		//setup input
		UI input = state.getUI();
		input.clearUI();
		input.setInt(discToBlock);
		//activate
		me.activate(state);
	}

}

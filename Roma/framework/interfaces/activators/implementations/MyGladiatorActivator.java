package framework.interfaces.activators.implementations;

import roma.*;
import framework.interfaces.activators.GladiatorActivator;

public class MyGladiatorActivator implements GladiatorActivator {

	private int position;
	private Game state;
	private int targetDisc;
	
	public MyGladiatorActivator(int position, Game state) {
		this.position = position;
		this.state = state;
		this.targetDisc = -1;
	}

	@Override
	public void chooseDiceDisc(int diceDisc) {
		targetDisc = diceDisc;
	}

	@Override
	public void complete() {
		//use activation die
		Player current = state.getCurrentPlayer();
		current.useDie(position);
		//get card to activate
		cards.Card me = current.getBoard().getBoard()[position];
		//get target card
		cards.Card target = state.getOpposingPlayer().getBoard().getBoard()[targetDisc];
		
		//setup UI
		UI input= state.getUI();
		input.clearUI();
		input.setCardName(target.getTitle());
		
		//activate
		me.activate(state);
	}

}

package framework.interfaces.activators.implementations;

import roma.*;
import framework.interfaces.activators.VelitesActivator;

public class MyVelitesActivator implements VelitesActivator {

	private int position;
	private Game state;
	private int targetDisc;
	private int battleDie;
	
	public MyVelitesActivator(int position,Game state) {
		this.position = position;
		this.state = state;
		targetDisc = -1;
		battleDie = -1;
	}

	@Override
	public void chooseDiceDisc(int diceDisc) {
		targetDisc = diceDisc;
	}

	@Override
	public void giveAttackDieRoll(int roll) {
		battleDie = roll;
	}

	@Override
	public void complete() {
		//use activation die
		Player current = state.getCurrentPlayer();
		current.useDie(position);
		//get card to activate
		cards.Card me = current.getBoard().getBoard()[position];
		//get targetCard
		cards.Card target = state.getOpposingPlayer().getBoard().getBoard()[targetDisc];
		//setup UI
		UI input = state.getUI();
		input.clearUI();
		input.setCardName(target.getTitle());
		//activate
		me.activate(state);		
	}

}

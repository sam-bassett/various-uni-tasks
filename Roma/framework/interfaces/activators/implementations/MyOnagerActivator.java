package framework.interfaces.activators.implementations;

import roma.*;
import framework.interfaces.activators.OnagerActivator;

public class MyOnagerActivator implements OnagerActivator {

	private int position;
	private Game state;
	private int targetDisc;
	private int attackDie;
	
	public MyOnagerActivator(int position, Game state) {
		this.position = position;
		this.state = state;
		this.targetDisc = -1;
		this.attackDie = 0;
	}

	@Override
	public void chooseDiceDisc(int diceDisc) {
		targetDisc = diceDisc;
	}

	@Override
	public void giveAttackDieRoll(int roll) {
		attackDie = roll;
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
		
		//setup input
		UI input = state.getUI();
		input.clearUI();
		input.setCardName(target.getTitle());
		input.setBattleDie(attackDie);
		
		//activate
		me.activate(state);

	}

}

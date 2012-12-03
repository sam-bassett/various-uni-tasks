package framework.interfaces.activators.implementations;

import roma.*;
import framework.interfaces.activators.LegionariusActivator;

public class MyLegionariusActivator implements LegionariusActivator {
	
	private int position;
	private Game state;
	private int battleDie;
	
	public MyLegionariusActivator(int position, Game state) {
		this.position = position;
		this.state = state;
		this.battleDie = 0;
	}

	@Override
	public void giveAttackDieRoll(int roll) {
		this.battleDie = roll;
	}

	@Override
	public void complete() {
		//use activation die
		Player current = state.getCurrentPlayer();
		current.useDie(position);
		//get card to activate
		cards.Card me = current.getBoard().getBoard()[position];
		
		//setup UI
		UI input = state.getUI();
		input.clearUI();
		input.setBattleDie(battleDie);
		
		//activate
		me.activate(state);
	}

}

package framework.interfaces.activators.implementations;

import roma.*;
import testImplementation.DiceAdaptor;
import framework.interfaces.activators.ConsulActivator;

public class MyConsulActivator implements ConsulActivator {

	private int position;
	private Game state;
	private int change; //how much to change the dice value by
	private int dieSelected; //by value not index
	
	
	public MyConsulActivator(int position, Game state) {
		this.state = state;
		this.position = position;
		this.change = 0;
		this.dieSelected = -1;
	}

	@Override
	public void complete() {
		//use activation die
		Player current = state.getCurrentPlayer();
		current.useDie(position);
		//get dice
		int[] dice = current.getActionDice();
		//get card to activate
		cards.Card me = current.getBoard().getBoard()[position];
		//setup UI
		UI input = state.getUI();
		input.clearUI();
		input.setDiceNumber(DiceAdaptor.getDieIndex(dice, dieSelected));
		input.setInt(change);
		//activate
		me.activate(state);
	}

	@Override
	public void chooseConsulChangeAmount(int amount) {
		change = amount;
	}

	@Override
	public void chooseWhichDiceChanges(int originalRoll) {
		dieSelected = originalRoll;
	}

}

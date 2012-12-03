package framework.interfaces.activators.implementations;

import roma.*;
import framework.interfaces.activators.CenturioActivator;

public class MyCenturioActivator implements CenturioActivator {

	private int position;
	private Game state;
	private boolean addActionDie;
	private int attackDie;
	private int actionDieToAdd;
	
	public MyCenturioActivator(int position, Game state) {
		this.position = position;
		this.state = state;
		this.addActionDie = false;
		this.attackDie = 0;
		this.actionDieToAdd = 0;
	}

	@Override
	public void giveAttackDieRoll(int roll) {
		attackDie = roll;
	}

	@Override
	public void chooseActionDice(int actionDiceValue) {
		actionDieToAdd = actionDiceValue;
	}

	@Override
	public void complete() {
		//use activation die
		Player current = state.getCurrentPlayer();
		current.useDie(position);
		//get action dice
		int[] dice = current.getActionDice();
		//get card to activate
		cards.Card me = current.getBoard().getBoard()[position];
		
		//setup UI
		UI input = state.getUI();
		input.clearUI();
		input.setBattleDie(attackDie);
		input.setBoolean(addActionDie);
		input.setDiceNumber(testImplementation.DiceAdaptor.getDieIndex(dice, actionDieToAdd));
		
		//activate
		me.activate(state);
	}

	@Override
	public void chooseCenturioAddActionDie(boolean attackAgain) {
		addActionDie = attackAgain;
	}

}

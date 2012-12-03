package framework.interfaces.activators.implementations;

import roma.Game;
import roma.Player;
import roma.TestUI;
import roma.UI;
import testImplementation.DiceAdaptor;

public class MyForumActivator implements framework.interfaces.activators.ForumActivator {

	private Game state;
	private int position;
	private int dieToUse;
	private boolean activateTemplum;
	private int activateTemplumWith;
	
	public MyForumActivator(int position, Game state) {
		dieToUse = 0;
		activateTemplum = false;
		activateTemplumWith = 0;
		this.position = position;
		this.state = state;
	}
	@Override
	public void chooseActionDice(int actionDiceValue) {
		dieToUse = actionDiceValue;
	}

	@Override
	public void complete() {
		Player current = state.getCurrentPlayer();
		int[] dice = current.getActionDice();	//fetch action dice
	    current.useDie(position);		//use die required to activate
		cards.Card me = current.getBoard().getBoard()[position];	//locate card to activate
		
		//set up input
		UI input = state.getUI();
		input.clearUI();
		input.setDiceNumber(DiceAdaptor.getDieIndex(dice, dieToUse));
		input.setDiceNumber(DiceAdaptor.getDieIndex(dice, activateTemplumWith));
		input.setBoolean(activateTemplum);
		state.setUI(input);
		//activate
		me.activate(state);
	}

	@Override
	public void chooseActivateTemplum(boolean activate) {
		activateTemplum = activate;
	}

	@Override
	public void chooseActivateTemplum(int diceValue) {
		activateTemplumWith = diceValue;
	}

}

package framework.interfaces.activators.implementations;

import roma.Game;
import roma.Player;
import roma.UI;
import framework.interfaces.activators.NeroActivator;

public class MyNeroActivator implements NeroActivator {

	int position;
	Game state;
	int targetDisc;
	
	public MyNeroActivator(int position, Game state) {
		this.position = position;
		this.state = state;
		this.targetDisc = -1;
	}

	@Override
	public void chooseDiceDisc(int diceDisc) {
		this.targetDisc = diceDisc;
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

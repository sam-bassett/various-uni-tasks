package framework.interfaces.activators.implementations;

import roma.*;
import testImplementation.CardActivatorFactory;
import framework.interfaces.activators.CardActivator;
import framework.interfaces.activators.ScaenicusActivator;

public class MyScaenicusActivator implements ScaenicusActivator {

	private int position;
	private Game state;
	private CardActivator toMimic;
	
	public MyScaenicusActivator(int position, Game state) {
		this.position = position;
		this.state = state;
		this.toMimic = null;
	}

	@Override
	public void complete() {
		// TODO Auto-generated method stub

	}

	@Override
	public CardActivator getScaenicusMimicTarget(int diceDisc) {
		cards.Card toCopy = state.getCurrentPlayer().getBoard().getBoard()[diceDisc];
		return CardActivatorFactory.getActivator(toCopy.getTitle(), diceDisc, state);
	}

}

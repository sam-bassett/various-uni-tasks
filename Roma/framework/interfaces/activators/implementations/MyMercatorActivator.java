
package framework.interfaces.activators.implementations;

import roma.*;
import framework.interfaces.activators.MercatorActivator;

public class MyMercatorActivator implements MercatorActivator {

	private int position;
	private Game state;
	int victoryPoints;
	
	public MyMercatorActivator(int position, Game state) {
		this.position = position;
		this.state = state;
		this.victoryPoints = 0;
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
		input.setInt(victoryPoints);
		
		//activate
		me.activate(state);
	}

	@Override
	public void chooseMercatorBuyNum(int VPToBuy) {
		victoryPoints = VPToBuy;
	}

}

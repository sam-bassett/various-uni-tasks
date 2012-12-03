package testImplementation;

import framework.interfaces.AcceptanceInterface;
import framework.interfaces.GameState;
import framework.interfaces.MoveMaker;
import roma.Game;

public class MyAcceptanceInterface implements AcceptanceInterface {
	
	private Game g;
	
	public MyAcceptanceInterface () {
		g = new Game(true);
	}

	@Override
	public MoveMaker getMover(GameState state) {
		// TODO Auto-generated method stub
		return new MoveMakerAdaptor(g);
	}

	@Override
	public GameState getInitialState() {
		// TODO Auto-generated method stub
		g = new Game(true);
		return new GameStateAdaptor(g);
	}

}

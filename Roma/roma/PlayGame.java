package roma;

import java.util.ArrayList;

public class PlayGame {

	public static void main(String[] args) {

		// very rudimentary SaveGame function here.
		// ArrayList created, Game cloned to it each turn
		// now they're indexed by turn, you can go forwards and back
		// as you please.

		ArrayList<Game> saveGame = new ArrayList<Game>();

		Game g = new Game(false);
		g.startGame();
		int winner = -1;
		int turns = 0;

		while (!g.isGameFinished()) {
			saveGame.add(g);
			turns++;
			winner = g.turn();
		}

		Player winningPlayer = g.getPlayer(winner);
		System.out.println("The winner is  " + winningPlayer.getPlayerName()
				+ " after " + turns + " turns!");
	}

}

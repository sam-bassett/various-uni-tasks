package roma;

import java.util.Random;
import java.util.ArrayList;
import cards.Card;

public class Game {

	private static final String NO_NAME = "NEMO";

	private int playerTurn;
	private int turnNumber;
	private Player player1;
	private Player player2;
	private int remainingVictoryPoints;
	private int remainingMoney;
	private Deck deck;
	private Random generator = new Random();
	private ArrayList<Game> saves;
	private UI UI;
	private PlayerActions actions;
	private boolean turnEnded;

	public Game(boolean isTest) {
		turnEnded = true;
		UI = UIFactory.makeUI(isTest);
		deck = new Deck();
		playerTurn = 0;
		player1 = new Player(NO_NAME, deck, UI);
		player2 = new Player(NO_NAME, deck, UI);
		actions = new PlayerActions(this);
		remainingVictoryPoints = 36;
		remainingMoney = 10000000;
		saves = new ArrayList<Game>();
	}

	public void startGame() {

		UI.promptGameStart();
		int startInput = -1;
		startInput = UI.getInt(1, 2);

		if (startInput == 1) {
			// start playing
		} else {
			// print rules and start
			UI.showRules();
		}

		System.out.print("Player 1, ");
		UI.promptForUsername();
		player1.setPlayerName(UI.getString());
		System.out.print("Player 2, ");
		UI.promptForUsername();
		player2.setPlayerName(UI.getString());

		initHands();

		player1.collectVictoryPoints(10);
		remainingVictoryPoints -= 10;
		player2.collectVictoryPoints(10);
		remainingVictoryPoints -= 10;

		swapCards();
		player1.layInitialCards();
		System.out.println("\n======other player======");
		player2.layInitialCards();
	}

	private void initHands() {
		int count;
		System.out.println("Player1 drawing cards!");
		for (count = 0; count < 4; count++) {
			player1.drawCard();
		}

		System.out.println("Player 2 drawing cards!");
		for (count = 0; count < 4; count++) {
			player2.drawCard();
		}

	}

	private void swapCards() {

		System.out.println("player 1s hand :");
		player1.showHand();
		System.out.println("Player 1:");
		Card[] player1Swap = player1.swapCards();

		System.out.println("player 2s hand :");
		player2.showHand();
		System.out.println("Player 2:");
		Card[] player2Swap = player2.swapCards();
		for (int i = 0; i < 2; i++) {
			player1.drawSpecificCard(player2Swap[i]);
			player2.drawSpecificCard(player1Swap[i]);
		}
	}
	
	// revamping turns: make it like microprocessors. get
	// initial conditions, start it running, allow it to do things
	// until the end turn function is called and play progresses.
	
	public int turn() {
		// try {
		// Game temp = (Game) this.clone();
		// saves.add(temp);
		// } catch (CloneNotSupportedException e) {
		// System.err.println("Clone not supported!");
		// e.printStackTrace();
		// }
		int winner = -1;

		Player current = getCurrentPlayer();
		Player opponent = getOpposingPlayer();
		System.out.println("It is player " + playerTurn + "'s turn!");
		opponent.unblockDiscs();
		phaseOne(current);
		current.giveDice(phaseTwo());
		phaseThree(current);
		opponent.refreshDefenceModifiers();

		winner = checkWinner();
//		playerTurn++;
//		playerTurn = playerTurn % 2;
//		turnNumber++;

		// TODO: refresh game modifiers
		return winner;
	}

	public void phaseOne(Player p) {
		int blanks = p.subtractBlanks();
		remainingVictoryPoints += blanks;
	}

	private int[] phaseTwo() {
		int dice[] = new int[3];
		int i;
		for (i = 0; i < 3; i++) {
			dice[i] = generator.nextInt(6) + 1;
			System.out.println("You rolled a " + dice[i] + "!");
		}
		if (dice[0] == dice[1] && dice[1] == dice[2]) {
			UI.promptRethrow();
			if (UI.getBoolean()) {
				dice = phaseTwo();
			}
		}

		return dice;
	}

	public void runAction(int action, UI UI) {
		actions.runAction(action, this, UI);
	}
	
	public void nextTurn() {
		turnEnded = true;
		playerTurn++;
		playerTurn%=2;
		turnNumber++;
	}
	
	private void phaseThree(Player p) {

		int selection = -1;
		turnEnded = false;
		while (!turnEnded) {
			UI.printAttributes(p);
			UI.promptPhaseThree();
			selection = UI.getInt(1, 8);
			runAction(selection, UI);
			// TODO: If a player wants to go back to selection menu
			// instead of making a selection, they should be able to.
			// maybe enter a 'wrong' input?

			// or just a 'b' or 'back'
		}

		if (deck.isEmpty()) {
			System.out.println("The deck is empty!");
			deck.shuffleDiscardToDeck();
		}
	}
	
	public boolean isGameFinished() {
		if (checkWinner() != -1) {
			return true;
		} else {
			return false;
		}
	}

	private int checkWinner() {
		int winner = -1;
		if (player1.getVictoryPoints() <= 0) {
			winner = 2;
		} else if (player2.getVictoryPoints() <= 0) {
			winner = 1;
		} else if (remainingVictoryPoints == 0) {
			if (player1.getVictoryPoints() > player2.getVictoryPoints()) {
				winner = 1;
			} else if (player2.getVictoryPoints() > player1.getVictoryPoints()) {
				winner = 2;
			} else {
				winner = 0;
			}
		}
		return winner;
	}

	public void discardCard(Card toDiscard) {
		deck.discard(toDiscard);
	}

	public Card drawCard() {
		return deck.drawCard();
	}

	public void giveMoney(int amount, Player p) {
		remainingMoney -= amount;
		p.collectMoney(amount);
	}

	public void giveVicPoints(int amount, Player p) {
		remainingVictoryPoints -= amount;
		p.collectVictoryPoints(amount);
	}

	// =========================================================================
	// ===================GETTERS AND SETTERS ONLY BEYOND HERE==================
	// =========================================================================

	public Player getPlayer(int playerNumber) {
		if (playerNumber == 0) {
			return player1;
		} else {
			return player2;
		}
	}

	public Player getCurrentPlayer() {
		if (playerTurn == 0) {
			return player1;
		} else {
			return player2;
		}
	}

	public Player getOpposingPlayer() {
		if (playerTurn == 0) {
			return player2;
		} else {
			return player1;
		}
	}

	public int getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(int playerTurn) {
		this.playerTurn = playerTurn;
	}

	public int getRemainingMoney() {
		return remainingMoney;
	}

	public void setRemainingMoney(int remainingMoney) {
		this.remainingMoney = remainingMoney;
	}

	public int getRemainingVictoryPoints() {
		return remainingVictoryPoints;
	}
	
	public void setRemainingVictoryPoints(int amount) {
		remainingVictoryPoints = amount;
	}

	public int getTurnNumber() {
		return this.turnNumber;
	}

	public Deck getDeck () {
		return this.deck;
	}
	
	public void setUI (UI newUI) {
		this.UI = newUI;
	}
	
	public UI getUI() {
		return this.UI;
	}
	
}

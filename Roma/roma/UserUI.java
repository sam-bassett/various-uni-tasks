package roma;

import java.util.*;

import cards.Card;

public class UserUI implements UI {
	private Input input;

	private final String cardTop 	= " ________________";
	private final String cardSide 	= "|                |";
	private final String cardBottom = "|________________|";
	private final int WHITESPACE 	= 16;

	public UserUI () {
		input = new UserInput();
	}
	
	public void clear(){
		
	}
	
	@Override
	public void showRules() {
		// TODO: implement rules
		System.out.println("Here be rules!");
	}

	@Override
	public void printAttributes(Player p) {
		System.out.println("\n\n\n");
		System.out.println("====Player " + p.getPlayerName()
				+ " attributes====");
		System.out.println("VicPoints: " + p.getVictoryPoints());
		System.out.println("Sesterii: " + p.getMoney());
		System.out.println("" + p.getDice());
		System.out.println("\n\n\n");
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#showHand(java.util.List)
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#showHand(java.util.List)
	 */
	@Override
	public void showHand(List<Card> hand) {
		System.out.println("Hand:");
		Iterator<Card> iterator = hand.iterator();
		while (iterator.hasNext()) {
			printCard(iterator.next());
		}
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#showBoard(cards.Card[])
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#showBoard(cards.Card[])
	 */
	@Override
	public void showBoard(Card[] board) {
		System.out.println("Board:");
		int i = 0;
		for (i = 0; i < board.length; i++) {
			System.out.print("   Disc num. " + i + "   \t");
		}
		System.out.println();
		for (i = 0; i < board.length; i++) {
			System.out.print(cardTop + "\t");
		}
		System.out.println();
		for (i = 0; i < 5; i++) {
			for (int j = 0; j < board.length; j++) {
				System.out.print(cardSide + "\t");
			}
			System.out.println();
		}
		Card current = null;
		for (i = 0; i < board.length; i++) {
			current = board[i];
			if (current != null) {
				formatString(current.getTitle());
			} else {
				formatString("No Card!");
			}
			System.out.print("\t");
		}
		System.out.println();
		for (i = 0; i < board.length; i++) {
			current = board[i];
			if (current != null) {
				String attributes = "CST: " + current.getPrice() + ",  DEF: "
						+ current.getDefence();
				formatString(attributes);
			} else {
				formatString("");
			}
			System.out.print("\t");
		}
		System.out.println();

		for (i = 0; i < 4; i++) {
			for (int j = 0; j < board.length; j++) {
				System.out.print(cardSide + "\t");
			}
			System.out.println();
		}
		for (i = 0; i < board.length; i++) {
			System.out.print(cardBottom + "\t");
		}
		System.out.println();
	}

	private void printCard(Card c) {
		System.out.println(cardTop);
		int i;
		for (i = 0; i < 5; i++) {
			System.out.println(cardSide);
		}
		if (c != null) {
			String title = c.getTitle();
			formatString(title);
			System.out.println();

			String attributes = "CST: " + c.getPrice() + ",  DEF: "
					+ c.getDefence();

			formatString(attributes);
			System.out.println();
		} else {
			formatString("No card!");
		}
		for (i = 0; i < 4; i++) {
			System.out.println(cardSide);
		}
		System.out.println(cardBottom);
	}

	private void formatString(String string) {
		System.out.print("|" + string);
		for (int j = 0; j < (WHITESPACE - string.length()); j++) {
			System.out.print(" ");
		}
		System.out.print("|");
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#getString()
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#getString()
	 */
	@Override
	public String getString() {
		return input.getString();
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#getInt(int, int)
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#getInt(int, int)
	 */
	@Override
	public int getInt(int min, int max) {
		boolean valid = false;
		int read = -1;
		promptForInt(min, max);
		while (valid == false) {
			read = input.getInt();
			if (read >= min && read <= max) {
				valid = true;
			} else {
				System.out.println("Wrong!");
				System.err.println("Please enter an int between " + min
						+ " and " + max);
			}
		}

		return read;
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#getDiceNumber()
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#getDiceNumber()
	 */
	@Override
	public int getDiceNumber() {
		promptForDice();
		int selectedDice = getInt(1, 3) - 1;
		return selectedDice;
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#getCardIndex(java.util.List)
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#getCardIndex(java.util.List)
	 */
	@Override
	public int getCardIndex(List<Card> cards) {
		promptForCard();
		int ret = -1;
		while (ret == -1) {
			String cardName = input.getString();
			cardName = cardName.toUpperCase();
			ret = findCard(cards, cardName);
			if (ret == -1) {
				System.out.println("card not found, please enter a valid card");
			}
		}

		return ret;
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#getCardIndex(cards.Card[], java.lang.String)
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#getCardIndex(cards.Card[], java.lang.String)
	 */
	@Override
	public int getCardIndex(Card[] cards, String cardName) {
		ArrayList<Card> temp = new ArrayList<Card>();
		for (int i = 0; i < cards.length; i++) {
			temp.add(cards[i]);
		}
		int ret = -1;
		cardName = cardName.toUpperCase();
		ret = findCard(temp, cardName);
		if (ret == -1) {
			System.out.println("card not found, please enter a valid card");
		}

		return ret;
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#findCard(java.util.List, java.lang.String)
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#findCard(java.util.List, java.lang.String)
	 */
	@Override
	public int findCard(List<Card> cards, String cardName) {
		boolean cardFound = false;
		int index = -1;
		int temp = -1;

		Iterator<Card> iterator = cards.iterator();

		for (int i = 0; iterator.hasNext() && i < cards.size() && !cardFound; i++) {
			Card current = cards.get(i);
			if (cardFound == true && (current.getTitle().contains(cardName))) {
				System.out.println("Duplicate card found");
				System.out.println("First instatnce: " + index);
				System.out.println("New instance:    " + i);
				System.out.println("Please select an index");

				try {
					temp = input.getInt();
					assert (temp == i || temp == index);
				} catch (AssertionError e) {
					System.err.println("Please enter either " + i + " or "
							+ index);
					temp = input.getInt();
				} catch (InputMismatchException ex) {
					System.err.println("Please enter either " + i + " or "
							+ index);
					temp = input.getInt();
				}

				index = temp;
			}

			if (current != null && current.getTitle().contains(cardName)) {
				System.out.println("Card " + current.getTitle() + " found!");
				cardFound = true;
				index = i;
			}

		}

		return index;
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#getBoolean()
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#getBoolean()
	 */
	@Override
	public boolean getBoolean() {
		String in = input.getString();
		boolean ret;
		if (in.charAt(0) == 'y' || in.charAt(0) == 'Y') {
			ret = true;
		} else if (in.charAt(0) == 'n' || in.charAt(0) == 'N') {
			ret = false;
		} else {
			System.err.println("Please enter yes or no");
			ret = getBoolean();
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#promptPhaseThree()
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#promptPhaseThree()
	 */
	@Override
	public void promptPhaseThree() {
		System.out.println("1. Lay cards");
		System.out.println("2. Take money");
		System.out.println("3. Take cards");
		System.out.println("4. Activate card");
		System.out.println("5. Bribe");
		System.out.println("6. Show hand");
		System.out.println("7. Show board");
		System.out.println("8. End turn");
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#promptForCard()
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#promptForCard()
	 */
	@Override
	public void promptForCard() {
		System.out.println("Please enter a card name!");
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#promptRethrow()
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#promptRethrow()
	 */
	@Override
	public void promptRethrow() {
		System.out
				.println("All three dice equal! Would you like to roll again?");
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#promptForInt(int, int)
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#promptForInt(int, int)
	 */
	@Override
	public void promptForInt(int from, int to) {
		System.out.println("Please enter a number between " + from + " and "
				+ to);
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#promptForUsername()
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#promptForUsername()
	 */
	@Override
	public void promptForUsername() {
		System.out.println("Please enter your name: ");
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#promptForDice()
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#promptForDice()
	 */
	@Override
	public void promptForDice() {
		System.out.println("Please select a dice: ");
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#promptDiscToActivate()
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#promptDiscToActivate()
	 */
	@Override
	public void promptDiscToActivate() {
		System.out
				.println("Enter the dice whose disc you'd like to activate: ");
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#promptDraw(java.lang.String)
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#promptDraw(java.lang.String)
	 */
	@Override
	public void promptDraw(String title) {
		System.out.println("You drew a " + title);
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#promptGameStart()
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#promptGameStart()
	 */
	@Override
	public void promptGameStart() {
		System.out.println("====Welcome to Roma!====");
		System.out.println("Please make a selection:");
		System.out.println("1. Start Game");
		System.out.println("2. Print Rules and start");
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#notifySuccessfulAttack()
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#notifySuccessfulAttack()
	 */
	@Override
	public void notifySuccessfulAttack() {
		System.out.println("Attack succeeded! Card destroyed!");
	}

	/* (non-Javadoc)
	 * @see roma.UInterface#notifyFailedAttack()
	 */
	
	/* (non-Javadoc)
	 * @see roma.UI#notifyFailedAttack()
	 */
	@Override
	public void notifyFailedAttack() {
		System.out.println("Attack failed!");
	}

	@Override
	public void setDiceNumber(int dice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCardName(String cardName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDiceValue() {
		// TODO Auto-generated method stub
		System.out.println("I'M NOT A TEST!");
		return input.getInt();
	}

	@Override
	public void setDiceValue(int to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDiceDisc() {
		// TODO Auto-generated method stub
		return input.getInt();
	}

	@Override
	public void setDiceDisc(int to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInt(int to) {
		// TODO Auto-generated method stub
		
	}
	
	public String getCardName() {
		return input.getString();
	}

	@Override
	public void setBoolean(boolean newBool) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearUI() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBattleDie() {
		Random generator = new Random();
		return generator.nextInt(6) + 1;
	}

	@Override
	public void setBattleDie(int battleDie) {
		// TODO Auto-generated method stub
		
	}

}
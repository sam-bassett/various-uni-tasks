package cards;

import java.util.Random;

import roma.Game;
import roma.Player;
import roma.UI;

public abstract class Card {

	private final static String[] BUILDINGS = { "AESCULAPINUM", "BASILICA",
			"MACHINA", "FORUM", "MERCATUS", "ONAGER", "TEMPLUM", "TURRIS" };

	private final static String[] CHARACTERS = { "ARCHITECTUS", "CONSILIARIUS",
			"LEGAT", "GLADIATOR", "MERCATOR", "CONSUL", "LEGIONARIUS", "NERO",
			"PRAETORIANUS", "SCAENICUS", "HARUSPEX", "SENATOR", "VELITES",
			"ESSEDUM", "TRIBIUNUS PLEBIS", "CENTURIO", "SICARIUS" };

	private int price;
	private int defence;
	private int defenceModifier;
	private String title;
	private CardType cardType;
	private int discNum;
	UI UI;

	public abstract void activate(Game g);

	public Card(int price, int defence, String title, CardType cardType) {
		this.price = price;
		this.defence = defence;
		this.title = title;
		this.cardType = cardType;
		defenceModifier = 0;
		discNum = -1;
	}

	public String getTitle() {
		return title;
	}

	public boolean sameTitle(String title) {
		return (this.title.compareTo(title) == 0);
	}

	public void battle(Game g, String cardName) {
		int battleDice = rollBattleDice();
		Player opponent = g.getOpposingPlayer();
		int defendingIndex = opponent.getCardIndex(cardName);
		System.out.println("You rolled a " + battleDice);

		opponent.defend(defendingIndex, battleDice);

	}

	public int getDefence() {
		return defence + defenceModifier;
	}

	public boolean isDestroyed(int battleDice) {
		return (battleDice >= (defence + defenceModifier));
	}

	public int getPrice() {
		return price;
	}

	public int rollBattleDice() {
		return UI.getBattleDie();
	}

	public boolean isCharacter(String cardName) {
		boolean isCharacter = false;
		for (int i = 0; i < CHARACTERS.length; i++) {
			if (CHARACTERS[i].contains(cardName)) {
				isCharacter = true;
			}
		}
		return isCharacter;
	}

	public boolean isBuilding(String cardName) {
		boolean isBuilding = false;
		for (int i = 0; i < BUILDINGS.length; i++) {
			if (BUILDINGS[i].compareTo(cardName) == 0) {
				isBuilding = true;
			}
		}
		return isBuilding;
	}

	public void setDefenceMod(int modifier) {
		defenceModifier += modifier;
	}

	public int getDefenceMod() {
		return defenceModifier;
	}

	public int getDiscNum() {
		return discNum;
	}

	public void setDiscNum(int num) {
		discNum = num;
	}
	
	public void setUI (UI UI) {
		this.UI = UI;
	}
}

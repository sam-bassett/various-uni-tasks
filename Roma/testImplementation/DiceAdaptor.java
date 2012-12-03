package testImplementation;

public class DiceAdaptor {

	public static int getDieIndex(int[] dice, int die) {
		int index = -1;
		for (int i = 0; i < dice.length; i++) {
			if(dice[i] == die) {
				index = i;
			}
		}
		return index;
	}
	
	public static int[] removeUsedDice(int[] dice) {
		int length = 0;
		for (int i = 0; i < dice.length; i++) {
			if (dice[i] != -1) {
				length++;
			}
		}
		int[] newDice = new int[length];
		
		int current = 0;
		for (int i = 0; i < dice.length; i++) {
			if (dice[i] != -1) {
				newDice[current] = dice[i];
				current++;
			}
		}
		return newDice;
	}
}

package Enigma;

public class RotorWiring {
	private int[] rotorWiring;
	private int[] backWiring;
	
	public RotorWiring() {
		rotorWiring = null;
		backWiring = null;
	}
	
	public int encryptForwards(int toEncrypt) {
		return rotorWiring[toEncrypt];
	}
	
	public int encryptBack(int toEncrypt) {
		return backWiring[toEncrypt];
	}
	
	public void setWiring (int[] wiring) {
		rotorWiring = wiring;
		backWiring = backWire(wiring);
	}
		
	private int[] backWire (int[] wiring) {
		// This must reverse the order of the initial wiring - 
		// use two 'for' loops
		int temp = -1;
		int[] backWired = new int[wiring.length];
		try {
			assert(wiring.length == 26);
		} catch (AssertionError e) {
			System.err.println("Unable to backWire the rotor, length incorrect");
			System.exit(0);
		}
		
		for (int i = 0; i < wiring.length; i++) {
			temp = wiring[i];
			backWired[temp] = i;
		}
		return backWired;
	}
	
	public void stepRotorWiring() {
		rotorWiring = stepWiring(rotorWiring);
		backWiring = stepWiring(backWiring);
	}
	
	private int[] stepWiring(int[] rotorWiring) {
		// step rotor
		// moves all the values in wiring down by one, with [0] moved to [25]
		int temp = rotorWiring[0] - 1;
		if (temp < 0) {
			temp = 25;
		}
		for (int i = 0; i < 25; i++) {
			rotorWiring[i] = rotorWiring[i+1] - 1;
			if (rotorWiring[i] < 0) {
				rotorWiring[i] = 25;
			}
		}
		rotorWiring[25] = temp;
		
		return rotorWiring;
	}
}

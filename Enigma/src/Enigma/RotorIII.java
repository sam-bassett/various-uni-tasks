package Enigma;

public class RotorIII extends Rotor {
//	private int[] rotorWiring = {1, 3, 5, 7, 9, 11, 2, 15, 17, 19, 23, 21, 25,
//			13, 24, 4, 8, 22, 6, 0, 10, 12, 20, 18, 16, 14};
//	
//	private int notchPosition = 21;
	
	private String wiring = "BDFHJLCPRTXVZNYEIWGAKMUSQO";
	private char notch = 'V';
	
	public RotorIII () {
		super.setWiring(wiring);
		super.setNotch(notch);
	}
	
}

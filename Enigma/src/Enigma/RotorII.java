package Enigma;

public class RotorII extends Rotor {
//	private int[] rotorWiring = {0, 9, 3, 10, 18, 8, 17, 20, 23, 1, 11, 7, 22,
//			19, 12, 2, 16, 6, 25, 13, 15, 24, 5, 21, 14, 4};
//	
//	private int notchPosition = 4;
	
	private String wiring = "AJDKSIRUXBLHWTMCQGZNPYFVOE";
	private char notch = 'E';
	
	public RotorII () {
		super.setWiring(wiring);
		super.setNotch(notch);
	}
	
}

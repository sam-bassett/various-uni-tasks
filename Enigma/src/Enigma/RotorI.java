package Enigma;

public class RotorI extends Rotor {
//	private int[] rotorWiring = {4,10,12,5,11,6,3,16,21,25,13,19,14,22,24,7,23,
//			20,18,15,0,8,1,17,2,9};
	
	private String wiring = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
	
	private char notch = 'Q';
	
	public RotorI () {
		super.setWiring(wiring);
		super.setNotch(notch);
	}
	
}

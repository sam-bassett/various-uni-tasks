package Enigma;

public class RotorIV extends Rotor {
//	private int[] rotorWiring = {4,18,14,21,15,25,9,0,24,16,20,8,17,7,23,11,
//			13,5,19,6,10,3,2,12,22,1};
//	
//	private int notchPosition = 9; 
	
	private String wiring = "ESOVPZJAYQUIRHXLNFTGKDCMWB";
	private char notch = 'J';
	
	public RotorIV () {
		super.setWiring(wiring);
		super.setNotch(notch);
	}
	
}

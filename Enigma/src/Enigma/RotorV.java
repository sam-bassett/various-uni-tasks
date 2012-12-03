package Enigma;

public class RotorV extends Rotor {
//	private int[] rotorWiring = {21,25,1,17,6,8,19,24,20,15,18,3,13,7,11,23,0,
//			22,12,9,16,14,5,4,2,10};
//	
//	private int notchPosition = 25;
	
	private String wiring = "VZBRGITYUPSDNHLXAWMJQOFECK";
	private char notch = 'Z';
	
	public RotorV () {
		super.setWiring(wiring);
		super.setNotch(notch);
	}
	
}

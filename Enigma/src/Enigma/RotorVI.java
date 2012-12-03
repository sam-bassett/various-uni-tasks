package Enigma;

public class RotorVI extends Rotor {
//	private int[] rotorWiring = {9,15,6,21,14,20,12,5,24,16,1,4,13,7,25,17,3,
//			10,0,18,23,11,8,2,19,22};
//	
//	private int notchPosition = -1; //??
	
	private String wiring = "JPGVOUMFYQBENHZRDKASXLICTW";
	private char notch = 'Z';
	private char secondNotch = 'M';
	
	public RotorVI () {
		super.setWiring(wiring);
		super.setNotch(notch);
		super.setSecondNotch(secondNotch);
	}
	
}

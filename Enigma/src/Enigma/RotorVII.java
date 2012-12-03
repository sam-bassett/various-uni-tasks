package Enigma;

public class RotorVII extends Rotor {
//	private int[] rotorWiring = {13,25,9,7,6,17,2,23,12,24,18,22,1,14,20,5,0,
//			8,21,11,15,4,10,16,3,19};
//	
//	private int notchPosition = -1; //??
	private String wiring = "NZJHGRCXMYSWBOUFAIVLPEKQDT";
	private char notch = 'Z';
	private char secondNotch = 'M';
	
	public RotorVII () {
		super.setWiring(wiring);
		super.setNotch(notch);
		super.setSecondNotch(secondNotch);
	}

}

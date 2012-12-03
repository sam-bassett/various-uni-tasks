package Enigma;

public class RotorVIII extends Rotor {
//	private int[] rotorWiring = {5,10,16,7,19,11,23,14,2,1,9,18,15,3,25,17,0,
//			12,4,22,13,8,20,24,6,21};
//	
//	
//	private int notchPosition = -1; //??
	
	private String wiring = "FKQHTLXOCBJSPDZRAMEWNIUYGV";
	private char notch = 'Z';
	private char secondNotch = 'M';
	
	public RotorVIII () {
		super.setWiring(wiring);
		super.setNotch(notch);
		super.setSecondNotch(secondNotch);
	}
	
}

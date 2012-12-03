package Enigma;

public class ReflectorC extends Rotor {
//	private int[] rotorWiring = {5,21,15,9,8,0,14,24,4,3,17,25,23,22,6,2,19,10,
//			20,16,18,1,13,12,7,11};
	
	private String wiring = "FVPJIAOYEDRZXWGCTKUQSBNMHL";
	
	public ReflectorC () {
		super.setWiring(wiring);
		super.setReflector(true);
	}
	
}

package Enigma;

public class ReflectorA extends Rotor {
//	private int[] rotorWiring = {4,9,12,25,0,11,24,23,21,1,22,5,2,17,16,20,14,
//			13,19,18,15,8,10,7,6,3};
	
	private String wiring = "EJMZALYXVBWFCRQUONTSPIKHGD";
	
	public ReflectorA () {
		super.setWiring(wiring);
		super.setReflector(true);
	}

}

package Enigma;

public class ReflectorB extends Rotor {
//	private int[] rotorWiring = {24,17,20,7,16,18,11,3,15,23,13,6,14,10,12,8,4,
//			1,5,25,2,22,21,9,0,19};
	
	private String wiring = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
	
	public ReflectorB () {
		super.setWiring(wiring);
		super.setReflector(true);
	}
	
}

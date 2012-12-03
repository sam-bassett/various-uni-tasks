package Enigma;

public class EnigmaBuilder {
	
	// It seems 'walzenlage' is the selection of wheels - you can put numbers
	// from 1 - 8 in for each wheel, and A-C for the reflector (despite what
	// the sample seems to say).
	// walzenlage[0] is slowest, walzenlage[2] is fastest wheel.
	public static EnigmaMachine constructM3 (String walzenlage) {
		EnigmaMachine newM3 = new EnigmaM3(walzenlage);
		return newM3;
	}
	
	public static EnigmaMachine constructM4 (String walzenlage) {
		EnigmaMachine newM4 = new EnigmaM4(walzenlage);
		return newM4;
	}
	
	public static EnigmaMachine constructM4_1 (String walzenlage, String ringstellung) {
		return null;
	}
	
}

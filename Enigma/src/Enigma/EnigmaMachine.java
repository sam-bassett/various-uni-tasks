package Enigma;

public interface EnigmaMachine {
	
	// Returns number of wheels the operator can set.
	// is equal to the length of the string from set/getIndicators.
	int numberOfSettableWheels();
	
	// positions == numberOfSettableWheels()
	void setIndicators (String positions);
	
	// returns what you could read through windows on actual machine
	String getCurrentIndicators();
	
	String encipher (String plaintext);
}

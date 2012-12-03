package Enigma;

import java.util.*;

public class Pawls {
	private LinkedList<Rotor> rotors;
	
	public Pawls (LinkedList<Rotor> rotors) {
		this.rotors = rotors;
	}
	
	public void stepRotors () {
		// do nothing yet
		// ok, this needs to do a few things. First, the fastest rotor steps
		// each time this is called. Then, if the first's notch is at 0, 
		// the second wheel can turn as well. If the first and second wheels'
		// notches are at 0, then the first, second and third wheels turn together.
		// Also, if the second wheel's notch is at 0, the second and third 
		// wheels turn together. This is a double step. 
		// All this can be accomplished recursively - the first will always
		// step, but before stepping it checks the next wheel. If prev's
		// notch is at 0, check next and so on 
		
		// initially, this is all that's needed
		rotors.getFirst().rotate();
	}

}

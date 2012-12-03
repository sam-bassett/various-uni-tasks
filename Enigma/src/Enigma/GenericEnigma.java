package Enigma;

import java.util.LinkedList;

public class GenericEnigma implements EnigmaMachine {
	
	private final int NUM_ROTORS = 4;
	private LinkedList<Rotor> rotors;
	private final int INT_OFFSET = 48;
	
	private Pawls pawls;
	
	// when initialised, all that's passed in to the machine is the 
	// walzenlage. That remains the same for both enigmas. Interactions
	// with the machine are also constant. The only thing that drastically
	// changes is how the machine treats reflectors - in the M4, they are
	// treated exactly as rotors (except they must be on the end).
	// maybe, therefore, you can just extract all the guts from this class
	// and put it in a GenericEnigma?
	
	// trying that now.
		
	public GenericEnigma (String walzenlage) {
		
		rotors = new LinkedList<Rotor>();
		pawls = new Pawls(rotors);
		
		// using abominable if..else blocks until I have a better idea
		parseWalzenlage(walzenlage);
	}
	
	private void parseWalzenlage (String walzenlage) {
		int i = 0;
		//Scanner read = new Scanner(walzenlage);
		Rotor toAdd = null;
		
		for (i = walzenlage.length()-1; i > 0; i--) {
			char tempChar = walzenlage.charAt(i-1);
			int temp = (int) tempChar - INT_OFFSET;
			assert((temp >= 0) && (temp < 10));
			toAdd = getRotor(temp);
			addToList(toAdd);
		}
		if (walzenlage.endsWith("A")) {
			toAdd = new ReflectorA();
		} else if (walzenlage.endsWith("B")) {
			toAdd = new ReflectorB();
		} else if (walzenlage.endsWith("C")) {
			toAdd = new ReflectorC();
		} else {
			System.err.println("Walzenlage must end with reflector");
			System.exit(0);
		}
		addToList(toAdd);
		
	}
	
	private void addToList (Rotor r) {
		if (rotors.isEmpty()) {
			r.connectNext(null);
			r.connectPrev(null);
			rotors.addLast(r);
		} else {
			Rotor temp = rotors.getLast();
			r.connectPrev(rotors.getLast());
			r.connectNext(null);
			temp.connectNext(r);
			rotors.addLast(r);
		}
	}
	
	private Rotor getRotor (int rotorNum) {
		Rotor temp = null;
		if (rotorNum == 1) {
			temp = new RotorI();
		} else if (rotorNum == 2) {
			temp = new RotorII();
		} else if (rotorNum == 3) {
			temp = new RotorIII();
		} else if (rotorNum == 4) {
			temp = new RotorIV();
		} else if (rotorNum == 5) {
			temp = new RotorV();
		} else if (rotorNum == 6) {
			temp = new RotorVI();
		} else if (rotorNum == 7) {
			temp = new RotorVII();
		} else if (rotorNum == 8) {
			temp = new RotorVIII();
		}
		return temp;
	}
	
	@Override
	public int numberOfSettableWheels() {
		return NUM_ROTORS-1;
	}

	@Override
	public void setIndicators(String positions) {
		// this should just pass the first element of the linked list a string,
		// which will pass it to the second, etc. Maybe each rotor could delete the
		// first element of positions each time, too?
		// easy to do, just positions.substring(1) each time
		for (int i = 0; i < positions.length(); i++) {
			int temp = positions.charAt(i);
			if (temp > 90 || temp < 65) {
				System.err.println("Indicator positions incorrect format");
				assert(false);
			}
		}
		
		rotors.getFirst().setIndicators(positions);
	}

	@Override
	public String getCurrentIndicators() {
		String indicators = rotors.getFirst().getIndicators();
		return indicators;
	}

	@Override
	public String encipher(String plaintext) {
		// maybe do this like microprocessor - have a step? while !completed, step rotors.
		String ciphertext = "";
		for (int i = 0; i < plaintext.length(); i++ ) {
			pawls.stepRotors();
			char current = plaintext.charAt(i);
			ciphertext += rotors.getFirst().encryptLetter(current);
		}
		return ciphertext;
	}
	
	public void setNoReflectors() {
		rotors.getLast().setReflector(false);
	}

}

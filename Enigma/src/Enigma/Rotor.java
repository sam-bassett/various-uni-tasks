package Enigma;

public class Rotor {
	private static final int CHAR_OFFSET = 65;
//	private int[] rotorWiring;
//	private int[] backWiring;
	private RotorWiring rotorWiring = new RotorWiring();

	private Rotor next = null;
	private Rotor prev = null;
	
	private int indicatorPosition = 0;
	private int notchPosition = -1;
	private int secondNotchPosition = -1;
	
	private boolean isReflector = false;
	
	public void connectNext(Rotor next) {
		// Connect this rotor to the next one
		this.next = next;
	}
	
	public void connectPrev (Rotor prev) {
		// connect to previous one
		this.prev = prev;
	}
	
	public char encryptLetter (char toEncrypt) {
		int encryptedInt = -1;
		char encryptedChar = '!';
		try {
			assert (toEncrypt < 91 && toEncrypt >= 65);
		} catch (AssertionError e) {
			System.err.println("Incorrect value passed to encryption,");
			System.err.println("System will now exit");
			System.exit(0);
		}
		
		int intToEncrypt = ((int) toEncrypt - CHAR_OFFSET);
		encryptedInt = encryptInt(intToEncrypt);
		encryptedChar = (char)(encryptedInt + CHAR_OFFSET);
		
		return encryptedChar;
	}

	public int encryptInt(int toEncrypt) {
		// do the encryption (based on own encryption string)
		// returns -1 if not finished, encrypted number otherwise
		
		int temp = -1;
		int nextThing = -1;
		
		if (next == null) {
			temp = rotorWiring.encryptForwards(toEncrypt);
		} else {
			nextThing = next.encryptInt(rotorWiring.encryptForwards(toEncrypt));
			temp = rotorWiring.encryptBack(nextThing);
		}
		return temp;
	}
	
	public void setIndicators (String indicators) {
		if (!isReflector) {
			if (next != null) {
				next.setIndicators(indicators.substring(0,indicators.length()-1));
			}
			
			int tempChar = indicators.charAt(indicators.length()-1);
			int temp = tempChar - CHAR_OFFSET;
			while (indicatorPosition != temp) {
				rotorWiring.stepRotorWiring();
				stepNotch();
				stepIndicators();
			}
		}
	}
	
	public String getIndicators () {
		String indicators = "";
		if (next != null && !isReflector) {
			indicators += next.getIndicators();
			indicators += (char) (indicatorPosition + CHAR_OFFSET);
		}
		return indicators;
	}
	
	public void setWiring (int[] wires) {
		rotorWiring.setWiring(wires);
	}
	
	public void setWiring (String wiring) {
		int[] connections = stringToInts(wiring);
		this.rotorWiring.setWiring(connections);
		
//		this.wiring = connections;
//		if (!isReflector) {
//			this.backWiring = backWire(connections);
//		}
	}
	
	public int[] stringToInts(String toConvert) {
		int[] converted = new int[toConvert.length()];
		
		for (int i = 0; i < toConvert.length(); i++) {
			converted[i] = ((int)toConvert.charAt(i) - CHAR_OFFSET);
		}
		return converted;
	}
	
	public void setNotch (char notchPosition) {
		this.notchPosition = (int) (notchPosition - CHAR_OFFSET);
		this.secondNotchPosition = this.notchPosition;
	}

	public void setSecondNotch (char notch) {
		this.secondNotchPosition = (int) (notch - CHAR_OFFSET);
	}
	
	private int getNotchPosition() {
		// returns the position of the closest notch
		// to 0 position
		int temp = -1;
		if (notchPosition <= secondNotchPosition) {
			temp = notchPosition;
		} else {
			temp = secondNotchPosition;
		}
		
		return temp;
	}

	public void rotate () {
		
		if (next != null && !next.isReflector()) {
			next.rotate();
		}
		if (willStep()) {
			rotorWiring.stepRotorWiring();
//			rotorWiring = wiring.stepWiring(rotorWiring);
//			backWiring = stepWiring(backWiring);

			stepIndicators();
			stepNotch();
		}
	}
	
	private void stepIndicators () {
		indicatorPosition++;
		indicatorPosition%=26;
	}
	
	private void stepNotch() {
		if (notchPosition == secondNotchPosition) {
			if (notchPosition == 0) {
				notchPosition = 25;
			} else {
				notchPosition--;
			}
			secondNotchPosition = notchPosition;
		} else {
			if (notchPosition == 0) {
				notchPosition = 25;
				secondNotchPosition--;
			} else if (secondNotchPosition == 0) {
				secondNotchPosition = 25;
				notchPosition--;
			} else {
				notchPosition--;
				secondNotchPosition--;
			}
		}
	}
	
	private boolean isReflector() {
		return isReflector;
	}
	
	public void setReflector (boolean isReflector) {
		this.isReflector = isReflector;
	}
		
	//TODO: Fix hack (if time)
	
	public boolean willStep() {
		return ((prev==null) || (prev.getNotchPosition() == 0) ||
				(prev != null && next != null && !next.isReflector() 
				&& getNotchPosition() == 0));
	}
}

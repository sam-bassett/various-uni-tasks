package Enigma;

public class EnigmaM3 extends GenericEnigma {
	private final int NUM_WHEELS = 4;

	public EnigmaM3(String walzenlage) {
		super(walzenlage);
		
		try {
			assert(walzenlage.endsWith("A")||walzenlage.endsWith("B")||
					walzenlage.endsWith("C"));
			assert(walzenlage.length() == NUM_WHEELS);
		} catch (AssertionError e) {
			System.out.println("Please enter a string of length 4 containing the" +
					" three rotor numbers and a reflector A..C");
			System.exit(0);
		}
	}

	@Override
	public int numberOfSettableWheels() {
		return NUM_WHEELS-1;
	}
}

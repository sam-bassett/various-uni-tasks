package Enigma;

public class EnigmaM4 extends GenericEnigma {
	private final int NUM_WHEELS = 5;

	public EnigmaM4 (String walzenlage) {
		super(walzenlage);
		
		try {
			assert(walzenlage.endsWith("A")||walzenlage.endsWith("B")||
					walzenlage.endsWith("C"));
			assert(walzenlage.length() == NUM_WHEELS);
		} catch (AssertionError e) {
			System.out.println("Please enter a string of length 5 containing the" +
					" four rotor numbers and a reflector A..C");
			System.exit(0);
		}
		super.setNoReflectors();
	}

	@Override
	public int numberOfSettableWheels() {
		return this.NUM_WHEELS;
	}
	
	@Override
	public void setIndicators(String positions) {	
		// for M4, mess around with positions until it works. 
		// basically you need to reorder the string, 
		// end -> start
		
		String positionsM4;
		positionsM4 = "";
		
		positionsM4 += ""+positions.charAt(NUM_WHEELS - 1);
		positionsM4 += positions;
		positionsM4 = positionsM4.substring(0, positionsM4.length() - 1);
		
		super.setIndicators(positionsM4);
	}
}

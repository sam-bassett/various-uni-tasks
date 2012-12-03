package Enigma;

public class TestEnigma {
	
	public static void test() {
		testEncryption();
		testIndicators();
		testTwoNotches();
	}
	
	public static void testTwoNotches() {
		System.out.println("testing rotors with two notches");
		EnigmaMachine m3 = EnigmaBuilder.constructM3("678B");
		m3.setIndicators("AAA");
		assert(m3.getCurrentIndicators().equals("AAA"));
		String result = m3.encipher("ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ");
		String intended = "GZSVJJCRMPUUPKQQSXPKZOBBWHZXWLMKAJDIIMLHFGGELZRJJPPK";
		assert(result.equals(intended));
		
		// now the real test, with changes to indicators
		m3 = EnigmaBuilder.constructM3("876C");
		m3.setIndicators("AZQ");
		assert(m3.getCurrentIndicators().equals("AZQ"));
		result = m3.encipher("ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ");
		intended = "DKJIZBFAUIPIALQBSKQOONQPTRKTEAWJMCLMBFUYYUMFMXVYBQXN";
		assert(result.equals(intended));
	}
	
	public static void testIndicators() {
		System.out.println("Testing indicators...");
		EnigmaMachine m3 = EnigmaBuilder.constructM3("123B");
		m3.setIndicators("AAA");
		assert(m3.getCurrentIndicators().equals("AAA"));
		m3.setIndicators("ABC");
		assert(m3.getCurrentIndicators().equals("ABC"));
		try {
			m3.setIndicators("123");
		} catch (AssertionError e) {
			System.out.println("crashed properly");
		}
		try {
			m3.setIndicators("ABCD");
		} catch (AssertionError e) {
			System.out.println("crashed properly");
		}
		System.out.println("done!");
	}
	
	public static void testEncryption() {
		// what's a few degrees between friends?
		EnigmaMachine enigma = EnigmaBuilder.constructM3("453B"); 
		enigma.setIndicators("AQM");
		
		// first test no stepping of second rotor
		String in  = "ABCDEFGHI";
		String out = "UVJMQEPWS";
		String result = enigma.encipher(in);
		assert(result.equals(out));
		
		// now test with stepping
		enigma.setIndicators("AQM");
		in  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		out = "UVJMQEPWSXCGSQKAJEIDIOVGDX";
		assert(enigma.encipher(in).equals(out));
		
		// and the whole thing
		enigma.setIndicators("JQM");
		in = "LOREMIPSUMDOLORSITAMETCONSECTETURADIPISICINGELITSEDDOEIUSMODTEMPORINCIDI" +
				"DUNTUTLABOREETDOLOREMAGNAALIQUAUTENIMADMINIMVENIAMQUISNOSTRUDEXERCITA" +
				"TIONULLAMCOLABORISNISIUTALIQUIPEXEACOMMODOCONSEQUATDUISAUTEIRUREDOLOR" +
				"INREPREHENDERITINVOLUPTATEVELITESSECILLUMDOLOREEUFUGIATNULLAPARIATURE" +
				"XCEPTEURSINTOCCAECATCUPIDATATNONPROIDENTSUNTINCULPAQUIOFFICIADESERUNT" +
				"MOLLITANIMIDESTLABORUM";

		out = "NRYPDOZYHZVKKWMMRVRVBGQCZJVHEJBCNNKBBBNMPTUOHOWPBFSCVFZBDQZRUURVYAQGPNP" +
				"UIGPJJJYJMZHLQWSFDEWNQRZARSHQRQSNWVTYSJACDZAYRFHWZOFQDIOCJEIDJDNZSTVP" +
				"WRUSUEPNWIHEDIIHGHOQPZUSDPBVVNWJGCVQDGZAYPZPDAUZNXSQCCJFZVAULDSBKNYYP" +
				"VVGSFIPDWRBURUCFBALQQEGWQXDOQZHDHGCJKRABMLXNBGFUQYCQEBFATSWZVMXIVJBOB" +
				"FCHOZFIZUEFQHSXDIXWTBPFDXYDSBJLHWVTUDYLYJUMBSMXVPCGUTMUFWOCWVDOBBLEPC" +
				"GORMCAJDOUHKGHXSBJMTGHA";
		assert(enigma.encipher(in).equals(out));
	}
}

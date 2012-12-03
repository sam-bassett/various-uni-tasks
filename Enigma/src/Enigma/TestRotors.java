package Enigma;

public class TestRotors {
	
	public static void test() {
		testRotors();
		testReflectors();
	}
	
	public static void testRotors() {
		System.out.println("Testing rotors...");
		rotorI();
		rotorII();
		rotorIII();
		rotorIV();
		rotorV();
		rotorVI();
		rotorVII();
		rotorVIII();
		System.out.println("Done!");
	}
	
	public static void testReflectors() {
		reflectorA();
		reflectorB();
		reflectorC();
	}
	
	private static void reflectorC() {
		Rotor rotor = new ReflectorC();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String encrypted = "FVPJIAOYEDRZXWGCTKUQSBNMHL";
		for (int i = 0; i < 26; i++) {
			assert(rotor.encryptLetter(alphabet.charAt(i)) == encrypted.charAt(i));
		}
	}

	private static void reflectorB() {
		Rotor rotor = new ReflectorB();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String encrypted = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
		for (int i = 0; i < 26; i++) {
			assert(rotor.encryptLetter(alphabet.charAt(i)) == encrypted.charAt(i));
		}	
	}

	private static void reflectorA() {
		Rotor rotor = new ReflectorA();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String encrypted = "EJMZALYXVBWFCRQUONTSPIKHGD";
		for (int i = 0; i < 26; i++) {
			assert(rotor.encryptLetter(alphabet.charAt(i)) == encrypted.charAt(i));
		}	
	}

	private static void rotorVIII() {
		Rotor rotor = new RotorVIII();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String encrypted = "FKQHTLXOCBJSPDZRAMEWNIUYGV";
		for (int i = 0; i < 26; i++) {
			assert(rotor.encryptLetter(alphabet.charAt(i)) == encrypted.charAt(i));
		}	
	}

	private static void rotorVII() {
		Rotor rotor = new RotorVII();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String encrypted = "NZJHGRCXMYSWBOUFAIVLPEKQDT";
		for (int i = 0; i < 26; i++) {
			assert(rotor.encryptLetter(alphabet.charAt(i)) == encrypted.charAt(i));
		}	
	}

	private static void rotorVI() {
		Rotor rotor = new RotorVI();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String encrypted = "JPGVOUMFYQBENHZRDKASXLICTW";
		for (int i = 0; i < 26; i++) {
			assert(rotor.encryptLetter(alphabet.charAt(i)) == encrypted.charAt(i));
		}
	}

	private static void rotorV() {
		Rotor rotor = new RotorV();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String encrypted = "VZBRGITYUPSDNHLXAWMJQOFECK";
		for (int i = 0; i < 26; i++) {
			assert(rotor.encryptLetter(alphabet.charAt(i)) == encrypted.charAt(i));
		}
	}

	private static void rotorIV() {
		Rotor rotor = new RotorIV();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String encrypted = "ESOVPZJAYQUIRHXLNFTGKDCMWB";
		for (int i = 0; i < 26; i++) {
			assert(rotor.encryptLetter(alphabet.charAt(i)) == encrypted.charAt(i));
		}
		
	}

	private static void rotorIII() {
		Rotor rotor = new RotorIII();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String encrypted = "BDFHJLCPRTXVZNYEIWGAKMUSQO";
		for (int i = 0; i < 26; i++) {
			assert(rotor.encryptLetter(alphabet.charAt(i)) == encrypted.charAt(i));
		}
	}

	private static void rotorII() {
		Rotor rotor = new RotorII();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String encrypted = "AJDKSIRUXBLHWTMCQGZNPYFVOE";
		for (int i = 0; i < 26; i++) {
			assert(rotor.encryptLetter(alphabet.charAt(i)) == encrypted.charAt(i));
		}
	}

	private static void rotorI() {
		Rotor rotor = new RotorI();
		assert (rotor.encryptLetter('A') == 'E');
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String encrypted = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
		for (int i = 0; i < 26; i++) {
			assert(rotor.encryptLetter(alphabet.charAt(i)) == encrypted.charAt(i));
		}
	}
}

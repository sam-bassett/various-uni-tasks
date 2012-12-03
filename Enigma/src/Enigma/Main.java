package Enigma;

import java.util.Scanner;

public class Main {
	public static void main (String[] args) {

		Scanner read = new Scanner(System.in);
		String rotorDetails;
		String plaintext;
		
		System.out.println("Enter rotor arrangement (slow, med, fast, reflector)");
		rotorDetails = read.nextLine();
		EnigmaMachine enigma = EnigmaBuilder.constructM4(rotorDetails);
		
		System.out.println("Enter indicators:");
		enigma.setIndicators(read.nextLine());
		
		System.out.println("Enter plaintext:");
		plaintext = read.nextLine();
				
		String cipertext = enigma.encipher(plaintext);
		System.out.println(cipertext);
	}
}

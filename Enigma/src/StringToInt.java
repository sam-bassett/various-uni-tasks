import java.util.Scanner;

public class StringToInt {
	public static void main (String[] args) {
		Scanner read = new Scanner(System.in);
		String toConvert = read.nextLine();
		
		int[] converted = stringToInts(toConvert);
		
		for (int i = 0; i < converted.length; i++) {
			System.out.print(converted[i] + ",");
		}
	}

	public static int[] stringToInts(String toConvert) {
		int[] converted = new int[toConvert.length()];
		
		for (int i = 0; i < toConvert.length(); i++) {
			converted[i] = ((int)toConvert.charAt(i) - 65);
		}
		return converted;
	}
}

package roma;

import java.util.Scanner;

public class UserInput extends Input {

	private Scanner input = new Scanner(System.in);
	
	@Override
	public String getString() {
		return input.next();
	}

	@Override
	public int getInt() {
		return input.nextInt();
	}

}

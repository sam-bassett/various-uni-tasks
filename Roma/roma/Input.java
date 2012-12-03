package roma;

public abstract class Input {
	// before each get call when testing, each set method has to be called first.
	// this is a hacky way to fix our UI. This means that we can give our game
	// logic, which seems to depend on the user's input, the values we want it
	// to have for testing purposes.
	public abstract String getString();
	
	public abstract int getInt();
}

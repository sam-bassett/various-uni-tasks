package roma;

public class TestingInput extends Input {
	
	private String nextString;
	private int    nextInt;

	@Override
	public String getString() {
		String ret = nextString;
		nextString = null;
		return ret;
	}

	@Override
	public int getInt() {
		int ret = nextInt;
		nextInt = -1;
		return ret;
	}
	
	public void setNextString (String value) {
		nextString = value;
	}
	
	public void setNextInt (int value) {
		nextInt = value;
	}

}

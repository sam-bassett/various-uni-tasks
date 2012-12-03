package roma;

public class UIFactory {
	
	public static UI makeUI(boolean isTest) {
		if (isTest) {
			return new TestUI();
		} else {
			return new UserUI();
		}
	}

}

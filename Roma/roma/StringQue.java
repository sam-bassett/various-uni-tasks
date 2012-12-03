package roma;

public class StringQue {
	private String[] que;
	int size;
	
	public StringQue(int size) {
		this.size = size;
		que = new String[size];
		for (int i = 0; i < size; i++) {
			que[i] = null;
		}
	}
	
	public void push(String pushing) {
		int i = 0;
		while (que[i] != null && i < size) {
			i++;
		}
		que[i] = pushing;
	}
	
	public String pop() {
		String popping = que[0];
		int i;
		for (i = 0; i < size - 1; i ++) {
			que[i] = que[i + 1];
		}
		que[i] = null;
		return popping;
	}
	
	public void clear() {
		for (int i = 0; i < size; i++) {
			que[i] = null;
		}
	}
}

package roma;

public class IntQue {

	private int[] que;
	int size;
	
	public IntQue(int size) {
		this.size = size;
		que = new int[size];
		for (int i = 0; i < size; i++) {
			que[i] = -1;
		}
	}
	
	public void push(int pushing) {
		int i = 0;
		while (que[i] != -1 && i < size) {
			i++;
		}
		que[i] = pushing;
	}
	
	public int pop() {
		int popping = que[0];
		int i;
		for (i = 0; i < size - 1; i ++) {
			que[i] = que[i + 1];
		}
		que[i] = -1;
		return popping;
	}
	
	public void clear() {
		for (int i = 0; i < size; i++) {
			que[i] = -1;
		}
	}
}

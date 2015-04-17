package Components;

public class MUX {
		
	public int select(int inp1, int inp2, boolean s) {
		return (s)?inp2:inp1;
	}
}

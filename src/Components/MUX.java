package Components;

public class MUX {
		
	public int select(int inp1, int inp2, int select) {
		return (select==1)?inp2:inp1;
	}
}

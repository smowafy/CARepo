package Components;

public class MUX {
		
	public int select(int inp1, int inp2, boolean s) {
		return (s)?inp2:inp1;
	}
	public int[] select2(int[] inp1, int[]inp2, int s){
		return(s==0)?inp1:inp2;
	}
}

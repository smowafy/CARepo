package Components;

public class Register {
	int[] bits;
	int pointer;
	
	public Register(int size) {
		bits = new int [size];
		pointer = 0;
	}
	public void insert(int[] input) {
		for(int i = pointer; i < pointer + input.length; i++) {
			bits[i] = input[i-pointer];
		}
		pointer  = (pointer+input.length)%bits.length;
	}
	
}

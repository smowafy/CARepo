package Components;

public class Register {
	int[] bits;
	int pointer = 0;
	
	public Register(int size) {
		bits = new int [size];
	}
	public void insert(int[] input) {
		for(int i = pointer; i < input.length + pointer; i++) {
			bits[i] = input[i-pointer];
		}
		pointer = (pointer + input.length) % bits.length;
	}
	public int [] getRegister()
	{
		return bits;
	}
}

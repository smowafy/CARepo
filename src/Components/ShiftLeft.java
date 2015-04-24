package Components;

public class ShiftLeft {
	public int[] shift(int[] input) {
		for(int i = 2; i < input.length; i++) {
			input[i-2] = input[i];
		}
		input[30] = input[31] = 0;
		return input;
	}
}

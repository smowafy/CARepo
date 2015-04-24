package Components;

public class SignExtend {
	
	public int[] extend(int[] input) {
		int[] output = new int[32];
		for(int i = 0; i < 16; i++) {
			output[i+16] = input[i];
		}
		for(int i = 0; i <16; i++) {
			output[i] = input[16];
		}
		return output;
	}
}

package Components;

public class BitsConverter {
	public int[] IntegerToBits(int inp) {
		int[] result = new int[32];
		for (int i = 0; i < 32; i++) {
			int bit = (inp & (1 << i));
			if (bit > 0)
				result[32 - 1 - i] = 1;
			else
				result[32 - 1 - i] = 0;
		}
		return result;
	}

	public int BitsToInterger(int[] inp, int size) {
		int out = 0;
		for (int i = size - 1; i >= 0; i--) {
			out += Math.pow(2, size - 1 - i) * inp[i];
		}
		return out;

	}

	public int BitsToInteger(int[] inp) {
		int out = 0;
		int size = inp.length;
		for (int i = size - 1; i >= 0; i--) {
			out += Math.pow(2, 31 - i) * inp[i];
		}
		return out;
	}

	public static void main(String[] args) {
		BitsConverter x = new BitsConverter();
		int[] arr = new int[32];
		arr[31] = 1;
		arr[30] = 1;
		arr[29] = 1;
		System.out.println(x.BitsToInteger(arr));
		int a = 7;
		arr = x.IntegerToBits(a);
		for (int i = 31; i > 26; i--) {
			System.out.print(arr[i]);
		}
	}
}

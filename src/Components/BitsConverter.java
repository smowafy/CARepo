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




	public int BitsToInteger(int[] inp) {
		int out = 0;
		boolean neg = false;
		if(inp[0] == 1)
		{
			neg =true;
			for(int i = 0; i<inp.length; i++)
			{
				inp[i] = inp[i]^1;
			}
			int x = this.BitsToIntegerHelper(inp);
			inp = this.IntegerToBits(++x);
		}
		for(int i = inp.length -1; i>= 0; i--) {
			out += Math.pow(2, inp.length-1-i)*inp[i];
		}
		return neg?(-1)*out:out;
	}
	
	public int BitsToIntegerHelper(int[] inp) {
		int out = 0;
		for (int i = inp.length-1; i >= 0; i--) {
			out += Math.pow(2, inp.length-1 - i) * inp[i];
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
		int a = -6;
		arr = x.IntegerToBits(a);
		for(int i = 0; i < 32; i++) {
		System.out.print(arr[i]);
		}
		System.out.println();
		int [] arr2 = {0,1,0,0};
		for(int i = 0; i <3; i++) {
		System.out.print(arr2[i]);
		}
		System.out.println();
		System.out.println(x.BitsToInteger(arr2));
	}
	
}

package Components;

public class Adder {
	BitsConverter converter;
	
	public Adder() {
		converter = new BitsConverter();
	}
	public int[] add(int[] a, int[] b){
		int aInt, bInt, cInt;
		aInt = converter.BitsToInteger(a);
		bInt = converter.BitsToInteger(b);
		cInt = aInt+bInt;
		return converter.IntegerToBits(cInt);
	}
	public int[] inc(int[] a) {
		int aInt = converter.BitsToInteger(a);
		aInt+=4;
		return converter.IntegerToBits(aInt);
	}
}

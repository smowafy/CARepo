package Memory;


import Components.BitsConverter;

public class RegisterFile {
	int[] file;
	BitsConverter converter;

	public RegisterFile() {
		file = new int [31];
		converter = new BitsConverter();
	}
	
	public void insertIntoRegister(int[] input, int[] register) {
		int regIdx = converter.BitsToInterger(register, 5);
		int inputVal = converter.BitsToInteger(input);
		file[regIdx] = inputVal;
	}
	
	public int[] getFromRegister(int[] register) {
		int regIdx = converter.BitsToInterger(register, 5);
		return converter.IntegerToBits(file[regIdx]);
	}
	
	public int[] getFromRegister(int register) {
		return converter.IntegerToBits(file[register]);
	}
}

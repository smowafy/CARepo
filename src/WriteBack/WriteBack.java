package WriteBack;

import Components.BitsConverter;

public class WriteBack {
	Hashtable<Integer, Integer> registers;
	BitsConverter converter;

	public WriteBack() {
		registers = new Hashtable<Integer, Integer>();
		converter = new BitsConverter();
	}

	public void writeToRegister(int[] register, int[] value) {
		registerAddress = converter.BitsToInteger(register);
		registerValue = converter.BitsToInteger(value);
		registers.put(registerAddress, registerValue);
	}

}

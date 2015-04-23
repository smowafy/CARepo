package Memory;

import java.util.Hashtable;

import Components.BitsConverter;

public class DataMemory {
	Hashtable<Integer, Integer> memory;
	BitsConverter converter;
	
	public DataMemory() {
		memory = new Hashtable<Integer, Integer>();
		converter = new BitsConverter();
	}
	
	public void insertIntoMemory(int[] input, int[] address) {
		int addressInt = converter.BitsToInteger(address);
		int inputInt = converter.BitsToInteger(input);
		memory.put(addressInt, inputInt);
	}
	
	public void insertIntoMemory(int[] input, int address) {
		int inputInt = converter.BitsToInteger(input);
		memory.put(address, inputInt);
	}
	
	public int[] getFromMemory(int[] address) {
		int addressInt = converter.BitsToInteger(address);
		int value = memory.get(addressInt);
		return converter.IntegerToBits(value);
	}
	public int[] getFromMemory(int address) {
		int value = memory.get(address);
		return converter.IntegerToBits(value);
	}
	
}

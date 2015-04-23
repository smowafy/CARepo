package Fetcher;

import Components.Adder;
import Components.BitsConverter;
import Components.Register;
import Decoder.Decoder;
import Memory.DataMemory;
import Memory.RegisterFile;

public class Fetcher {
	DataMemory instructionMemory;
	Adder adder;
	RegisterFile file;
	DataMemory dataMemory;
	Register fetch = new Register(64);
	RegisterFile regFile;
	BitsConverter converter;

	public Fetcher() {
		instructionMemory = Mips.Mips.instructionMemory;
		adder = new Adder();
		converter = new BitsConverter();
		// PC = counter;
		dataMemory = Mips.Mips.dataMemory;
		regFile = Mips.Mips.regFile;
		int check = fetchNext();
		System.out.println(check);
		if (check != 0) {
			System.out.println("IF/ID:");
			System.out.println("    "+fetch);
			new Decoder(fetch);
			new Fetcher();
		}
	}

	private int fetchNext() {
		int[] instruction = instructionMemory.getFromMemory(Mips.Mips.PC);
		int check = converter.BitsToInteger(instruction);
		if (check != 0) {
			int[] count = converter.IntegerToBits(Mips.Mips.PC, 32);
			count = adder.inc(count);
			Mips.Mips.PC = converter.BitsToInteger(count);
			fetch.insert(count);
			fetch.insert(instruction);
		}
		return check;
	}
}

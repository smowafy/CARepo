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
		int val = fetchNext();
		if (val != 0) {
			new Decoder(fetch);
			new Fetcher();
		}
	}

	private int fetchNext() {
		int[] instruction = instructionMemory.getFromMemory(Mips.Mips.PC);
		int val = converter.BitsToInteger(instruction);
		if (val != 0) {
			int[] pcReg = converter.IntegerToBits(Mips.Mips.PC, 32);
			pcReg = adder.inc(pcReg);
			Mips.Mips.PC = converter.BitsToInteger(pcReg);
			fetch.insert(pcReg);
			fetch.insert(instruction);
		}

		return val;
	}
}

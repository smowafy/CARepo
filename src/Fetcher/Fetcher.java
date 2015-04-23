package Fetcher;

import Components.Adder;
import Components.BitsConverter;
import Components.Register;
import Decoder.Decoder;
import Memory.DataMemory;
import Memory.RegisterFile;
import Mips.Mips;

public class Fetcher {
	DataMemory instructionMemory;
	Adder adder;
	RegisterFile file;
	DataMemory dataMemory;
	Register fetch = new Register(64);
	RegisterFile regFile;
	BitsConverter converter;

	public Fetcher() {
		instructionMemory = Mips.instructionMemory;
		regFile = Mips.regFile;
		adder = new Adder();
		converter = new BitsConverter();
		int[] instruction = fetchNext();
		int val = converter.BitsToInteger(instruction);
		if (val != 0) {
			// extract opcode
			int[] opcode = new int[6];
			for (int i = 0; i < 6; i++) {
				opcode[i] = instruction[i];
			}
			// if j then jump to address directly
			int opCodeVal = converter.BitsToInteger(opcode);

			if (opCodeVal == 0) {
				int[] func = new int[5];
				for (int j = 0; j < 5; j++) {
					func[j] = instruction[27 + j];
				}
				if (converter.BitsToInteger(func) == 8) {
					// set pc to ra
					Mips.PC = converter.BitsToInteger(regFile
							.getFromRegister(31));
				} else
					new Decoder(fetch);
			} else if (opCodeVal == 2 || opCodeVal == 3) {
				int[] jAddress = new int[26];
				for (int i = 0; i < 26; i++) {
					jAddress[i] = instruction[6 + i];
				}
				int[] ra = { 1, 1, 1, 1, 1 };
				if (opCodeVal == 3)
					regFile.insertIntoRegister(ra,
							converter.IntegerToBits(Mips.PC));
				Mips.PC = converter.BitsToInteger(jAddress);
			} else
				new Decoder(fetch);
			new Fetcher();
		}

	}

	private int[] fetchNext() {
		int[] instruction = instructionMemory.getFromMemory(Mips.PC);
		int val = converter.BitsToInteger(instruction);
		if (val != 0) {
			int[] pcReg = converter.IntegerToBits(Mips.PC, 32);
			pcReg = adder.inc(pcReg);
			Mips.PC = converter.BitsToInteger(pcReg);
			fetch.insert(pcReg);
			fetch.insert(instruction);
		}

		return instruction;
	}
}

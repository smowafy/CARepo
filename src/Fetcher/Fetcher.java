package Fetcher;

import java.util.Arrays;

import Components.Adder;
import Components.BitsConverter;
import Components.Register;
import Decoder.Decoder;
import Memory.DataMemory;
import Memory.RegisterFile;
import Parser.Parser;

@SuppressWarnings("unused")
public class Fetcher {
	DataMemory instructionMemory;
	Adder adder;
	RegisterFile file;
	DataMemory dataMemory;
	Register fetch = new Register(64);
	RegisterFile regFile;
	BitsConverter converter;
	Parser parser;

	public Fetcher() {
		instructionMemory = Mips.Mips.instructionMemory;
		adder = new Adder();
		converter = new BitsConverter();
		// PC = counter;
		dataMemory = Mips.Mips.dataMemory;
		parser = new Parser();
		regFile = Mips.Mips.regFile;
		int check = fetchNext();
		if (check != 0) {
			//new Decoder(fetch);
			System.out.println(Arrays.toString(fetch.getRegister()));
			new Fetcher();
		}
		
	}

	private int fetchNext() {
		int[] instruction = instructionMemory.getFromMemory(Mips.Mips.PC);
		int check = converter.BitsToInteger(instruction);
		int[] opcode = new int[6];
		int[] count = new int[32];
		String opc = "";
		if (check != 0) {
			for (int i = 0; i < 6; i++) {
				opcode[i] = instruction[i];
			}
			int op = converter.BitsToInteger(opcode);
			if (op != 0) {
				opc = parser.getInstructionOpcode().get(op);}
			
				if (opc.equals("j")) {
					int[] jump = new int[26];
					for (int i = 6; i < 32; i++) {
						jump[i - 6] = instruction[i];
					}
					int j = converter.BitsToInteger(jump);
					Mips.Mips.PC = j*4;
					count = converter.IntegerToBits(Mips.Mips.PC, 32);
				} else if (opc.equals("jr")) {
					int[] adr = Mips.Mips.regFile.getFromRegister(parser
							.getRegister().get("$ra"));
					count = adr;
					Mips.Mips.PC = converter.BitsToInteger(adr);
				} else if (opc.equals("jal")) {
					int[] jump = new int[26];
					for (int i = 6; i < 32; i++) {
						jump[i - 6] = instruction[i];
					}
					int[] incPC = converter.IntegerToBits(Mips.Mips.PC, 32);
					incPC = adder.inc(incPC);
					Mips.Mips.regFile.insertIntoRegister(
							incPC,
							converter.IntegerToBits(parser.getRegister().get(
									"$ra")));
					Mips.Mips.PC = converter.BitsToInteger(jump)*4;
					count = converter.IntegerToBits(Mips.Mips.PC, 32);
				}
			 else {
				count = converter.IntegerToBits(Mips.Mips.PC, 32);
				count = adder.inc(count);
				Mips.Mips.PC = converter.BitsToInteger(count);
			}

			fetch.insert(count);
			fetch.insert(instruction);
		}
		return check;
	}
}

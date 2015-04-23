package Mips;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


import Components.BitsConverter;
import Fetcher.Fetcher;
import Memory.DataMemory;
import Memory.RegisterFile;
import Parser.Parser;

public class Mips {
	public static int PC;
	public static DataMemory instructionMemory;
	public static Parser parser;
	public static BitsConverter converter;
	public static DataMemory dataMemory;
	public static RegisterFile regFile;
	public static int wbCalls = 4;
	
	public Mips(ArrayList<String> program, int startAddress) {
		PC = startAddress;
		instructionMemory = new DataMemory();
		dataMemory = new DataMemory();
		regFile = new RegisterFile();
		parser = new Parser();
		converter = new BitsConverter();
		int[] instrCode;
		int i = 0;
		for (i = 0; i < program.size(); i++) {
			String[] instr = program.get(i).split(" ");
			instrCode = getInstruction(instr);
			regFile = new RegisterFile(); // Missing method;
			dataMemory = new DataMemory();
			instructionMemory.insertIntoMemory(instrCode, PC+i*4);
			
		}
		instrCode = new int[32];
		instructionMemory.insertIntoMemory(instrCode, PC+i*4);
		System.out.println("Fetching...");
		new Fetcher();

	}

	private int[] getInstruction(String[] instr) {
		int op = parser.getInstruction().get(instr[0]);
		int[] opcode = converter.IntegerToBits(op, 6);
		int[] rs, rd, rt, shamt, func, constant, address;
		int[] instrOpcode = new int[32];
		
		/* R-format */ 
		if (op == 0) {
			if (instr[0].equals("jr")) {
				rs = converter.IntegerToBits(Integer.parseInt(instr[1]), 5);
				rd = converter.IntegerToBits(0, 5);
				rt = converter.IntegerToBits(0, 5);
				shamt = converter.IntegerToBits(0, 5);
			} else if (instr[0].equals("sll") || instr[0].equals("srl")) {
				rs = converter.IntegerToBits(0, 5);
				rd = converter.IntegerToBits(
						parser.getRegister().get(instr[1]), 5);
				rt = converter.IntegerToBits(
						parser.getRegister().get(instr[2]), 5);
				shamt = converter.IntegerToBits(Integer.parseInt(instr[3]), 5);
			} else {
				rd = converter.IntegerToBits(
						parser.getRegister().get(instr[1]), 5);
				rs = converter.IntegerToBits(
						parser.getRegister().get(instr[2]), 5);
				rt = converter.IntegerToBits(
						parser.getRegister().get(instr[3]), 5);
				shamt = converter.IntegerToBits(0, 5);
			}
			func = converter.IntegerToBits(parser.getFunction().get(instr[0]),
					6);
			System.arraycopy(opcode, 0, instrOpcode, 0, 6);
			System.arraycopy(rs, 0, instrOpcode, 6, 5);
			System.arraycopy(rt, 0, instrOpcode, 11, 5);
			System.arraycopy(rd, 0, instrOpcode, 16, 5);
			System.arraycopy(shamt, 0, instrOpcode, 21, 5);
			System.arraycopy(func, 0, instrOpcode, 26, 6);
		} else {
			
			/* J-format */ 
			if (instr[0].equals("j") || instr[0].equals("jal")) {
				address = converter.IntegerToBits(Integer.parseInt(instr[1]),
						26);
				System.arraycopy(opcode, 0, instrOpcode, 0, 6);
				System.arraycopy(address, 0, instrOpcode, 6, 26);

			} else {
				
				/* I-format */ 
				if (instr[0].equals("bne") || instr[0].equals("beq")) {
					rs = converter.IntegerToBits(
							parser.getRegister().get(instr[1]), 5);
					rt = converter.IntegerToBits(
							parser.getRegister().get(instr[2]), 5);
					constant = converter.IntegerToBits(
							Integer.parseInt(instr[3]), 16);
					System.arraycopy(opcode, 0, instrOpcode, 0, 6);
					System.arraycopy(rs, 0, instrOpcode, 6, 5);
					System.arraycopy(rt, 0, instrOpcode, 11, 5);
					System.arraycopy(constant, 0, instrOpcode, 16, 16);

				} else if (instr[0].equals("addi")) {
					rt = converter.IntegerToBits(
							parser.getRegister().get(instr[1]), 5);
					rs = converter.IntegerToBits(
							parser.getRegister().get(instr[2]), 5);
					constant = converter.IntegerToBits(
							Integer.parseInt(instr[3]), 16);
					System.arraycopy(opcode, 0, instrOpcode, 0, 6);
					System.arraycopy(rs, 0, instrOpcode, 6, 5);
					System.arraycopy(rt, 0, instrOpcode, 11, 5);
					System.arraycopy(constant, 0, instrOpcode, 16, 16);

				} else {
					rt = converter.IntegerToBits(
							parser.getRegister().get(instr[1]), 5);
					String[] split = instr[2].split("\\(");
					rs = converter.IntegerToBits(
							parser.getRegister()
									.get(split[1].substring(0,
											split[1].length() - 1)), 5);
					constant = converter.IntegerToBits(
							Integer.parseInt(split[0]), 16);
					System.arraycopy(opcode, 0, instrOpcode, 0, 6);
					System.arraycopy(rs, 0, instrOpcode, 6, 5);
					System.arraycopy(rt, 0, instrOpcode, 11, 5);
					System.arraycopy(constant, 0, instrOpcode, 16, 16);
				}

			}
		}
		return instrOpcode;

	}

	public static void main(String[] args) throws IOException {
		ArrayList<String> program = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		int address = Integer.parseInt(line);
		while(!(line = br.readLine()).equals(""))
		{
			program.add(line);
		}
		@SuppressWarnings("unused")
		Mips m = new Mips(program, address);
		
		System.out.println("Register contents: ");
		for(int i = 0; i < 32; i++) {
			System.out.println("    "+m.converter.BitsToInteger(m.regFile.getFromRegister(i)));
		}
		System.out.println("Memory contents: ");
		for(int i = 0; i < 32; i++) {
			System.out.println("    "+m.converter.BitsToInteger(m.dataMemory.getFromMemory(i)));
		}
		
	}
}

package Decoder;

import Components.BitsConverter;
import Instructions.IFormat;
import Instructions.Instruction;
import Instructions.JFormat;
import Instructions.RFormat;

public class Decoder {

	Instruction currentInstruction;
	BitsConverter converter;

	public Decoder() {
		converter = new BitsConverter();
	}

	public void setInstruction(int[] arr) {

		int opcode = converter.BitsToInterger(arr, 6);
		if (opcode == 0) {
			currentInstruction = new RFormat(arr);
		} else if (opcode == 2 || opcode == 3) {
			currentInstruction = new JFormat(arr);
		} else {
			currentInstruction = new IFormat(arr);
		}
	}

	/*
	 * Don't care signal is returned as -1
	 */

	public int getRegDst() {
		int opcode = converter
				.BitsToInterger(currentInstruction.getOpcode(), 6);
		if (currentInstruction instanceof JFormat)
			return 0;
		if (currentInstruction instanceof IFormat) {
			if (opcode == 0x2B || opcode == 0x4 || opcode == 0x5)
				return -1;
			return 0;
		}
		if (converter.BitsToInterger(((RFormat) currentInstruction).getFn(), 6) == 0x8)
			return -1;
		return 1;
	}

	public int getRegWrite() {
		int opcode = converter
				.BitsToInterger(currentInstruction.getOpcode(), 6);
		if (currentInstruction instanceof JFormat) {
			if (opcode == 0x3)
				return 1;
			return 0;
		}
		if (currentInstruction instanceof IFormat) {
			if (opcode == 0x2B || opcode == 0x28 || opcode == 0x4
					|| opcode == 0x5)
				return 0;
			return 1;
		}
		if (converter.BitsToInterger(((RFormat) currentInstruction).getFn(), 6) == 0x8)
			return 0;
		return 1;
	}

	public int getALUSrc() {
		int opcode = converter
				.BitsToInterger(currentInstruction.getOpcode(), 6);
		if (currentInstruction instanceof JFormat)
			return -1;
		if (currentInstruction instanceof IFormat)
			return 1;
		if (converter.BitsToInterger(((RFormat) currentInstruction).getFn(), 6) == 0x8)
			return -1;
		return 0;
	}

	/*
	 * The ALUOp signal for the J-format instructions should be rechecked
	 */
	public int[] getALUOp() {
		int opcode = converter
				.BitsToInterger(currentInstruction.getOpcode(), 6);
		int[] custom = { 1, 0 };
		int[] sub = { 0, 1 };
		int[] add = { 0, 0 };
		int[] invalid = { 1, 1 };
		if (currentInstruction instanceof RFormat)
			return custom;
		if (currentInstruction instanceof IFormat) {
			if (opcode == 0x4 || opcode == 0x5)
				return sub;
			return add;
		}
		return invalid;
	}

	public int getMemWrite() {
		int opcode = converter
				.BitsToInterger(currentInstruction.getOpcode(), 6);
		if (opcode == 0x20 || opcode == 0x23 || opcode == 0x24)
			return 1;
		return 0;
	}
	
	public int getMemRead() {
		int opcode = converter
				.BitsToInterger(currentInstruction.getOpcode(), 6);
		if (opcode == 0x2B || opcode == 0x28) return 1;
		return 0;
	}
	
	public int getMemToReg() {
		return getMemWrite();
	}

}

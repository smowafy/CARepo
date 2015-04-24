package Decoder;

import java.util.Arrays;

import Components.BitsConverter;
import Components.Register;
import Components.SignExtend;
import Instructions.IFormat;
import Instructions.Instruction;
import Instructions.JFormat;
import Instructions.RFormat;
import Memory.RegisterFile;

public class Decoder {

	Instruction currentInstruction;
	int[] PC;
	BitsConverter converter;
	RegisterFile regFile;
	SignExtend extender;

	public Decoder(Register IFIDreg, RegisterFile curr) {
		int[] IFID = IFIDreg.getRegister();
		PC = Mips.Mips.PC;
		converter = new BitsConverter();
		extender = new SignExtend();
		regFile = curr;
		int[] instructionPart = new int[32];
		System.arraycopy(IFID, 0, instructionPart, 0, 32);
		setInstruction(instructionPart);
		
	}

	public void setInstruction(int[] arr) {

		int opcode = converter.BitsToInterger(arr, 6);
		if (opcode == 0) {
			currentInstruction = new RFormat(arr);
		} else if (opcode == 0x2 || opcode == 0x3) {
			currentInstruction = new JFormat(arr);
		} else {
			currentInstruction = new IFormat(arr);
		}
	}

	public void getFromIFID(int[] instruction, int[] pc) {
		setInstruction(instruction);
		PC = pc;
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
		if (opcode == 0x2B || opcode == 0x28)
			return 1;
		return 0;
	}
	
	public int getBranch() {
		int opcode = converter
				.BitsToInterger(currentInstruction.getOpcode(), 6);
		if (opcode == 0x4 || opcode == 0x5) return 1;
		return 0;
	}

	public int getMemToReg() {
		return getMemWrite();
	}
	/*
	 * 
	 * 
	 * 
	 * 
	 * ID/EX register output is produced here in the method IDEXregister() below, listed in the order mentioned in Practice assignment 7 1st question
	 * Which is:
		32-bits incremented PC
	   	32-bits read register 1 value
		32-bits read register 2 value
		32-bits sign extended offset
		5-bits Rt field
		5-bits Rd field
		2-bits WB control signals
		3-bits MEM control signals
		4-bits EX control signals
		5-bits Shift amount
		
	 * 
	 * 
	 * 
	 * 
	 */

	public int[] IDEXregister() {
		int[][] components = new int [11][];
		components[0] = PC;
		//PC
		components[1] = converter.IntegerToBits(0);
		if (currentInstruction instanceof RFormat) {
			components[1] = ((RFormat)currentInstruction).getRs();
			components[1] = regFile.getFromRegister(components[1]);
			components[2] = ((RFormat)currentInstruction).getRt();
			components[2] = regFile.getFromRegister(components[2]);
		}
		//load the values in registers rs and rt
		else if (currentInstruction instanceof IFormat) {
			components[1] = ((IFormat)currentInstruction).getRs();
			components[1] = regFile.getFromRegister(components[1]);
			components[2] = ((IFormat)currentInstruction).getRt();
			components[2] = regFile.getFromRegister(components[2]);
		}
		//load the values in registers rs and rt
		components[3] = converter.IntegerToBits(0);
		if (currentInstruction instanceof IFormat) {
			components[3] = extender.extend(((IFormat)currentInstruction).getOffset());
		}
		//load the offset in case of I-format
		components[4] = components[5] = converter.IntegerToBits(0);
		if (currentInstruction instanceof RFormat) {
			components[4] = ((RFormat)currentInstruction).getRt();
			components[5] = ((RFormat)currentInstruction).getRd();
		} else if (currentInstruction instanceof IFormat) {
			components[4] = ((IFormat)currentInstruction).getRt();
		}
		//load addresses of rt and rd for R-format / only rt for I-format
		int[] WBSignals = new int[2];
		WBSignals[0] = this.getMemToReg();
		WBSignals[1] = this.getRegWrite();
		components[6] = WBSignals;
		//load Write-Back signals in this order: MemToReg, RegWrite
		int[] MEMSignals = new int [3];
		MEMSignals[0] = this.getBranch();
		MEMSignals[1] = this.getMemRead();
		MEMSignals[2] = this.getMemWrite();
		components[7] = MEMSignals;
		//load Memory signals in this order: branch, MemRead, MemWrite
		int[] EXSignals = new int[4];
		EXSignals[0] = this.getRegDst();
		EXSignals[1] = this.getALUSrc();
		EXSignals[2] = this.getALUOp()[0];
		EXSignals[3] = this.getALUOp()[1];
		components[8] = EXSignals;
		//load Execute signals in this order: RegDst, ALUSrc, 2 bits of ALUOp
		if (currentInstruction instanceof RFormat) {
			components[9] = ((RFormat)currentInstruction).getShamt();
		}
		int [] isShift = new int[1];
		if (currentInstruction instanceof RFormat) {
			RFormat tmp = ((RFormat)currentInstruction);
			if (converter.BitsToInteger(tmp.getOpCode(), 6) == 0 && 
				converter.BitsToInteger(tmp.getFn(), 6) == 0) {
					isShift[0] = 1;
			}
		}
		components[10] = isShift;
		return combineArrays(components);
		//The combineArrays method just concatenates all the arrays into a single array
	}
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Helping methods
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public static int[] combineArrays(int[][] arrays) {
		int len = 0;
		int acc = 0;
		for (int i = 0; i < arrays.length; i++) {
			len += arrays[i].length;
		}
		int[] result = new int[len];
		for (int i = 0; i < arrays.length; i++) {
			System.arraycopy(arrays[i], 0, result, acc, arrays[i].length);
			acc += arrays[i].length;
		}
		return result;
	}
	public static int[] combineArrays(int[]a, int[] b) {
		int length = a.length + b.length;
        int[] result = new int[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
	}
	
	public static void main(String[] args) {
		/*RegisterFile reg = new RegisterFile();
		int[] IFID = new int[32];
		Decoder dec = new Decoder(IFID, reg);
		int[] finalReg = dec.IDEXregister();
		System.out.println(Arrays.toString(finalReg));*/
	}

}

package WriteBack;

import Components.MUX;
import Components.BitsConverter;
import Components.Register;
import Memory.RegisterFile;

public class WriteBack {
	/* current register file */
	RegisterFile registerFile;
	BitsConverter converter;
	MUX inputSrc;
	/* number of clock cycles = 4 + wbCalls */
	

	public WriteBack(Register instruction) {
		registerFile = Mips.Mips.regFile;
		//registerFile = new RegisterFile();
		converter = new BitsConverter();
		inputSrc = new MUX();
		writeBack(instruction.getRegister());
	}

	/* takes pipeline register contents as input */
	public void writeBack(int[] instruction) {
		Mips.Mips.wbCalls++;
		int i = 68;
		/*
		 * reg[0] = regWrite, reg[1] = memToReg, reg[2 ~ 6] = register, reg[7 ~
		 * 38] = memory word, reg[39 ~ 71] = ALU input
		 */
		int[] targetRegister = new int[5];
		int[] registerValue = new int[32];
		boolean regWrite = (instruction[70] == 1) ? true : false;
		boolean memToReg = (instruction[69] == 1) ? true : false;
		/* if register write = 0 then nothing to do */
		if (regWrite) {
			for (int j = 4; j >= 0; j--, i--) {
				targetRegister[j] = instruction[i];
			}
			/* if ALU input, skip memory part */
			if (!memToReg)
				i -= 32;
			for (int j = 31; j >= 0; j--, i--) {
				registerValue[j] = instruction[i];
			}
			/* write into register file */
			registerFile.insertIntoRegister(registerValue, targetRegister);
		}

	}

	public static void main(String[] args) {
		
}
}

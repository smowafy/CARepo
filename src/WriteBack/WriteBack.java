package WriteBack;

import java.util.Arrays;

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
			System.out.println("ID/EX Register Components in order:");
			System.out.println("Register Value: "+Arrays.toString(registerValue));
			System.out.println("Target Register: "+Arrays.toString(targetRegister));
			System.out.println("Memory To Register: "+memToReg);
			System.out.println("Register Write: "+regWrite);
			/* write into register file */
			registerFile.insertIntoRegister(registerValue, targetRegister);
		}
		else
			System.out.println("Nothing to be written");

	}

	public static void main(String[] args) {
		int[] p = new int[71];
		p[70] = 1;
		p[69] = 0;
		p[64] = 1;
		for(int i = 65; i < 69; i++) {
			p[i] = 0;
		}
		for(int i = 32; i < 64; i++){
			p[i] = 1;
		}
		for(int i = 0; i < 32; i++) {
			p[i] = 0;
		}
		Register r = new Register(71);
		r.insert(p);
		new WriteBack(r);
	}
}

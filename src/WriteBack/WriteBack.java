package WriteBack;

import Components.MUX;
import Components.BitsConverter;
import Instructions.IFormat;
import Instructions.Instruction;
import Instructions.JFormat;
import Instructions.RFormat;
import Memory.RegisterFile;

public class WriteBack {
	/*current register file*/
	RegisterFile registerFile;
	BitsConverter converter;
	MUX inputSrc;
	int[] instruction;

	public WriteBack(RegisterFile regFile) {
		registerFile = regFile;
		converter = new BitsConverter();
		inputSrc = new MUX();
	}

	public int[] getInput(int[] memoryInput, 
		int[] aluInput, boolean memToReg) {
		return converter.IntegerToBits(inputSrc.select(aluInput,
		 memoryInput, memToReg));
	}
}

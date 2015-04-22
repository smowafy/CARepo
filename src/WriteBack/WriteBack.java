package WriteBack;

import Components.MUX;
import Components.BitsConverter;
import Memory.RegisterFile;

public class WriteBack {
	/*current register file*/
	RegisterFile registerFile;
	BitsConverter converter;
	MUX inputSrc;
	int[] targetRegister;
	int[] registerValue;

	public WriteBack(RegisterFile regFile) {
		registerFile = regFile;
		converter = new BitsConverter();
		inputSrc = new MUX();
	}
	/*select which line to get input from(memory or ALU)*/
	public int[] getInput(int[] memoryInput, 
		int[] aluInput, boolean memToReg) {
		return inputSrc.select(aluInput,
		 memoryInput, memToReg);
	}
	/*could not find how register is taken from pipeline
	 register so i assumed the register is passed directly*/
	public void setTargetRegisterAndValue (int[] register,
	 int[] value) {
		targetRegister = register;
		registerValue = value;
	}
	/*writes into register file*/
	public void writeIntoRegister() {
		registerFile.insertIntoRegister(targetRegister, registerValue);
	}
}

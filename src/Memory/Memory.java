package Memory;

import Memory.DataMemory;
import Components.BitsConverter;
import Components.MUX;
import Components.Register;
import Memory.RegisterFile;
import WriteBack.WriteBack;

public class Memory {
	DataMemory data, instructionMemory;
	int[] components;
	int memRead;
	int memWrite;
	int[] aluResult;
	int beqZero, bneZero;
	int[] register;
	BitsConverter converter;
	int[] branchAdd;
	int[] value2;
	int[] regDes;
	int memToReg, branch;
	int regWrite;
	int[] readData; // 32 if load

	public Memory(Register components) {
		this.components = components.getRegister();
		this.instructionMemory = Mips.Mips.instructionMemory;
		this.data = Mips.Mips.dataMemory;
		converter = new BitsConverter();
		this.memRead = memRead;
		this.memWrite = memWrite;
		this.aluResult = aluResult;
		this.beqZero = beqZero;
		initComponents();
		if (memWrite == 1) {
			store(aluResult, value2);
		} else if (memRead == 1) {
			load(aluResult);
		} else if (beqZero == 1) {
			int select = branch & beqZero;
			MUX mux = new MUX();
			Mips.Mips.PC = mux.select(Mips.Mips.PC,
					converter.BitsToInteger(branchAdd), select);
		} else {
			if (bneZero == 1) {
				int select = branch & bneZero;
				MUX mux = new MUX();
				Mips.Mips.PC = mux.select(Mips.Mips.PC,
						converter.BitsToInteger(branchAdd), select);
			}
		}
		new WriteBack(WBregister());

	}

	public void initComponents() {
		System.arraycopy(components, 0, branchAdd, 0, 32);
		beqZero = components[32];
		bneZero = components[33];
		// System.arraycopy(components, 32, zero, 0, 1);
		System.arraycopy(components, 34, aluResult, 0, 32);
		System.arraycopy(components, 66, value2, 0, 32);
		System.arraycopy(components, 98, regDes, 0, 32);
		// System.arraycopy(components, 102, memToReg, 0, 5);
		memToReg = components[103];
		regWrite = components[104];
		branch = components[105];
		memRead = components[106];
		memWrite = components[107];
	}

	public void store(int[] aluResult, int[] writeData) {
		int alu = converter.BitsToInteger(aluResult);
		int value = converter.BitsToInteger(writeData);
		data.memory.put(alu, value);
	}

	public int[] load(int[] aluResult) {

		return data.getFromMemory(aluResult);
	}

	public Register WBregister() {
		int[] regComponents = new int[71];
		System.arraycopy(aluResult, 0, regComponents, 0, 32);
		System.arraycopy(load(aluResult), 0, regComponents, 32, 32);
		System.arraycopy(regDes, 0, regComponents, 64, 5);
		regComponents[70] = regWrite;
		regComponents[69] = memToReg;
		Register r = new Register(71);
		r.insert(regComponents);
		return r;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

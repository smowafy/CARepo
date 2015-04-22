package Executer;
import Components.BitsConverter;
import Components.Adder;
import Components.ShiftLeft;
import Components.MUX;
import Memory.DataMemory;

public class Executer {
	DataMemory memory;// given from the main class when new executer is initialised
	BitsConverter converter;
	Adder adder;
	ShiftLeft addShift;
	MUX mux;
	Alu alu;
	AluControl aluControl;
	int[] components;// supposing inputs from pipeline registers is there
	int[] incrementedPc, value1, value2, signExtendedAdd, rd, rt; //pipeline register inputs
	int[] branchAdd;
	int[] aluResult;
	int[] aluSourceResult; // result from aluSrc mux
	int[] registerDstResult; //result from regdst mux
	int[] signExtendShiftResult; // result from shift left
	int[] aluControlResult; // alu control bits
	int[] aluControlInput; // 6-bits opcode input to alu control to output alu control bits
	int[] aluOp;// Ex control signal from pipeline register
	int regDst,aluSrc; //Ex control signals from pipeline register
	int zero; // zero-bit
	int regWrite,memToReg; //WB control signals
	int branch, memWrite, memRead; //Mem control signals
	
	public Executer(DataMemory memory, int[] components){
		this.memory = memory;
		this.components = components;
		converter = new BitsConverter();
		adder = new Adder();
		addShift = new ShiftLeft();
		mux = new MUX();
		alu = new Alu(this.memory);
		aluControl = new AluControl();
		aluOp = new int[2];
		incrementedPc = new int[32];
		branchAdd = new int[32];
		rd = new int[5];
		rt = new int[5];
		value1 = new int[32];
		value2 = new int[32];
		signExtendedAdd = new int[32];
		aluResult = new int[32];
		aluSourceResult = new int[6];
		registerDstResult = new int[5];
		signExtendShiftResult = new int[32];
		aluControlInput = new int[6];
		aluControlResult = new int[4];
		initComponents();
		
	}
	
	public void initComponents(){
		System.arraycopy(components, 0, incrementedPc, 0, 32);
		System.arraycopy(components, 32, value1, 0, 32);
		System.arraycopy(components, 64, value2, 0, 32);
		System.arraycopy(components, 96, signExtendedAdd, 0, 32);
		System.arraycopy(components, 128, rt, 0, 5);
		System.arraycopy(components, 133, rd, 0, 5);
		memToReg = components[138];
		regWrite = components[139];
		branch = components[140];
		memRead = components[141];
		memWrite = components[142];
		regDst = components[143];
		aluSrc = components[144];
		System.arraycopy(converter, 145, aluOp, 0, 2);
	}
	
	public void setAluOperation(){
		System.arraycopy(signExtendedAdd, 0, aluControlInput, 0, 6);
		aluControlResult=aluControl.selectOperation(aluOp, aluControlInput);
	}
	public void setDestinationReg(){
		registerDstResult = mux.select2(rt, rd, regDst);
	}
	public void setAluSourceResult(){
		aluSourceResult = mux.select2(value2, signExtendedAdd, aluSrc);
	}
	public void setShiftedAddress(){
		signExtendShiftResult = addShift.shift(signExtendedAdd);
	}
	
	public void setBranchAddress(){
		branchAdd = adder.add(signExtendShiftResult, incrementedPc);
	}
	public void callAlu(){
		setAluOperation();
		setAluSourceResult();
		aluResult = alu.doOperation(value1, aluSourceResult, aluControlResult);
		zero = alu.zero(value1, aluSourceResult);
	}
	public int[] EXMEMregister(){
		int[] regComponents = new int[107];
		System.arraycopy(branchAdd, 0, regComponents, 0, 32);
		regComponents[32]= zero;
		System.arraycopy(aluResult, 0, regComponents, 33, 32);
		System.arraycopy(value2, 0, regComponents, 65, 32);
		System.arraycopy(registerDstResult, 0, regComponents, 97, 5);
		regComponents[102] = memToReg;
		regComponents[103] = regWrite;
		regComponents[104] = branch;
		regComponents[105] = memRead;
		regComponents[106] = memWrite;
		return regComponents;
	}
	public int[] runExecuter(){
		callAlu();
		setDestinationReg();
		setBranchAddress();
		return EXMEMregister();
	}
	

}

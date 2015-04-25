package Executer;
import java.util.Arrays;

import Components.BitsConverter;
import Components.Register;
import Components.Adder;
import Components.Register;
import Components.ShiftLeft;
import Components.MUX;
import Memory.Memory;;

public class Executer {
	//DataMemory memory;// given from the main class when new executer is initialised
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
	int[] aluSourceResult1; // result from aluSrc mux
	int[] aluSourceResult;
	int[] registerDstResult; //result from regdst mux
	int[] signExtendShiftResult; // result from shift left
	int[] aluControlResult; // alu control bits
	int[] aluControlInput; // 6-bits opcode input to alu control to output alu control bits
	int[] aluOp;// Ex control signal from pipeline register
	int regDst,aluSrc; //Ex control signals from pipeline register
	int beqZero; // zero-bit for beq
	int bneZero; // zero-bit for bne
	int regWrite,memToReg; //WB control signals
	int branch, memWrite, memRead; //Mem control signals
	int shift; // 1 when the operation is shift
	int[] shamt; // shift amount
	int loadByte;
	int storeByte;
	int loadUpperImmediate;
	int loadByteu;
	
	public Executer(Register components){
		//this.memory = memory;
		this.components = components.getRegister();;
		converter = new BitsConverter();
		adder = new Adder();
		addShift = new ShiftLeft();
		mux = new MUX();
		alu = new Alu();
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
		aluSourceResult1 = new int[6];
		shamt = new int[6];
		registerDstResult = new int[5];
		signExtendShiftResult = new int[32];
		aluControlInput = new int[6];
		aluControlResult = new int[4];
		initComponents();
		System.out.println("ID/EX Register Components in order:");
		System.out.println("Incremented PC " + Arrays.toString(incrementedPc));
		System.out.println("RS Register value " + Arrays.toString(value1));
		System.out.println("RT Register value "+ Arrays.toString(value2));
		System.out.println("Sign Extended Address "+Arrays.toString(signExtendedAdd));
		System.out.println("RT Register number "+Arrays.toString(rt));
		System.out.println("RD Register number "+Arrays.toString(rd));
		System.out.println("memToReg: " + memToReg);
		System.out.println("regWrite: " + regWrite);
		System.out.println("branch: " + branch);
		System.out.println("memRead: " + memRead);
		System.out.println("memWrite: " +  memWrite);
		System.out.println("regDst: " + regDst);
		System.out.println("aluSrc: " +aluSrc);
		System.out.println("AluOp: "+ Arrays.toString(aluOp));
		System.out.println("Shift amount: "+ Arrays.toString(shamt));
		System.out.println("shift: " +  shift);
		System.out.println("loadByte: " +  loadByte);
		System.out.println("storeByte: " +  storeByte);
		System.out.println("loadUpperImmediate: " +  loadUpperImmediate);
		System.out.println("loadByte unsigned: " +  loadByteu);
		System.out.println("loadByte: " +  loadByte);
		System.out.println("Function Code "+Arrays.toString(aluControlInput));
		runExecuter();
		
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
		System.arraycopy(components, 145, aluOp, 0, 2);
		System.arraycopy(components, 147, shamt, 1, 5);
		shift = components[152];
		loadByte = components[153];
		storeByte = components[154];
		loadUpperImmediate = components[155];
		loadByteu = components[156];
		System.arraycopy(components,157 ,aluControlInput, 0, 6);
	}
	
	public void setAluOperation(){
		//System.arraycopy(signExtendedAdd, 26, aluControlInput, 0, 6);
		aluControlResult=aluControl.selectOperation(aluOp, aluControlInput);
	}
	public void setDestinationReg(){
		registerDstResult = mux.select2(rt, rd, regDst);
	}
	public void setAluSourceResult(){
		aluSourceResult1 = mux.select2(value2, signExtendedAdd, aluSrc);
		aluSourceResult = mux.select2(aluSourceResult1, shamt, shift);
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
		beqZero = alu.zero(value1, aluSourceResult);
		bneZero = 1-beqZero;
	}
	public Register EXMEMregister(){
		Register exmem = new Register(112);
		int[] regComponents = new int[112];
		System.arraycopy(branchAdd, 0, regComponents, 0, 32);
		regComponents[32]= beqZero;
		regComponents[33]= bneZero;
		System.arraycopy(aluResult, 0, regComponents, 34, 32);
		System.arraycopy(value2, 0, regComponents, 66, 32);
		System.arraycopy(registerDstResult, 0, regComponents, 98, 5);
		regComponents[103] = memToReg;
		regComponents[104] = regWrite;
		regComponents[105] = branch;
		regComponents[106] = memRead;
		regComponents[107] = memWrite;
		regComponents[108] = loadByte;
		regComponents[109] = storeByte;
		regComponents[110] = loadUpperImmediate;
		regComponents[111] = loadByteu;
		exmem.insert(regComponents);
		return exmem;
	}
	public void runExecuter(){
		callAlu();
		setDestinationReg();
		setBranchAddress();
		new Memory(EXMEMregister());
		 
	}
	public static void main(String[] args) {
		/*
		BitsConverter conv = new BitsConverter();
		int value1 = 6;
		int value2 = 3;
		int[] arr1 = conv.IntegerToBits(value1);
		int[] arr2 = conv.IntegerToBits(value2);
		int[] components = new int[153];
		System.arraycopy(new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},0,components, 0,  32);
		System.arraycopy( arr1, 0,components, 32, 32);
		System.arraycopy( arr2, 0,components, 64, 32);
		System.arraycopy(new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},0,components, 96,32);
		System.arraycopy(new int[]{0,0,1,0,0},0,components, 128,  5);
		System.arraycopy(new int[]{0,0,1,0,1},0,components, 133, 5);
		components[138] = 0; // memToReg
		components[139] = 1; //regWrite
		components[140] = 0; // branch
		components[141] = 0; // memRead
		components[142] = 0; // memWrite
		components[143] = 1; //regDst
		components[144] = 0; //aluSrc
		components[145] = 1;
		components[146] = 0;
		components[147] = 0;
		components[148] = 0;
		components[149] = 0;
		components[150] = 0;
		components[151] = 1;
		components[152] = 1;//shamt
		Register input = new Register(153);
		input.insert(components);
		Executer ex = new Executer(input);
		
		int[] result = ex.runExecuter();
		for (int i = 0; i < 32; i++) {
			System.out.print(result[i]+" ");
		}
		System.out.println();
		for (int i = 34; i < 66; i++) {
			System.out.print(result[i]+" ");
		}
		System.out.println();
		for (int i = 66; i < 98; i++) {
			System.out.print(result[i]+" ");
		}
		System.out.println();
		for (int i = 98; i < 103; i++) {
			System.out.print(result[i]+" ");
		}
		System.out.println();
		System.out.println(result[103]);
		System.out.println(result[104]);
		System.out.println(result[105]);
		System.out.println(result[106]);
		System.out.println(result[107]);
		*/
	}
	

}
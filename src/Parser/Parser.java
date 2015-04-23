package Parser;

import java.util.HashMap;

public class Parser {
	private HashMap<String, Integer> register;
	private HashMap<Integer, String> registerOpcode;
	private HashMap<String, Integer> instruction;
	private HashMap<Integer, String> instructionOpcode;
	private HashMap<String, Integer> function;
	private HashMap<Integer, String> functionOpcode;

	public Parser() {
		register = new HashMap<String, Integer>();
		registerOpcode = new HashMap<Integer, String>();
		instruction = new HashMap<String, Integer>();
		instructionOpcode = new HashMap<Integer, String>();
		function = new HashMap<String, Integer>();
		functionOpcode = new HashMap<Integer, String>();
		initRegister();
		initRegOp();
		initInstr();
		initInstrOp();
		initFunc();
		initFuncOp();
	}

	private void initFunc() {
		function.put("add", 32);
		function.put("sub", 34);
		function.put("sll", 0);
		function.put("srl", 2);
		function.put("nor", 39);
		function.put("slt", 42);
		function.put("sltu", 41);
		function.put("and", 36);
		function.put("jr", 8);
	}

	private void initFuncOp() {
		functionOpcode.put(32, "add");
		functionOpcode.put(34, "sub");
		functionOpcode.put(0, "sll");
		functionOpcode.put(2, "srl");
		functionOpcode.put(39, "nor");
		functionOpcode.put(42, "slt");
		functionOpcode.put(41, "sltu");
		functionOpcode.put(36, "and");
		functionOpcode.put(8, "jr");
	}

	private void initInstrOp() {
		instructionOpcode.put(8, "addi");
		instructionOpcode.put(35, "lw");
		instructionOpcode.put(32, "lb");
		instructionOpcode.put(36, "lbu");
		instructionOpcode.put(43, "sw");
		instructionOpcode.put(40, "sb");
		instructionOpcode.put(4, "beq");
		instructionOpcode.put(5, "bne");
		instructionOpcode.put(2, "j");
		instructionOpcode.put(3, "jal");
		instructionOpcode.put(15, "lui");

	}

	private void initInstr() {
		instruction.put("addi", 8);
		instruction.put("lw", 35);
		instruction.put("lb", 32);
		instruction.put("lbu", 36);
		instruction.put("sw", 43);
		instruction.put("sb", 40);
		instruction.put("beq", 4);
		instruction.put("bne", 5);
		instruction.put("j", 2);
		instruction.put("jal", 3);
		instruction.put("lui", 15);
		instruction.put("add", 0);
		instruction.put("sub", 0);
		instruction.put("sll", 0);
		instruction.put("srl", 0);
		instruction.put("nor", 0);
		instruction.put("slt", 0);
		instruction.put("sltu", 0);
		instruction.put("and", 0);
		instruction.put("jr", 0);
	}

	private void initRegOp() {
		registerOpcode.put(0, "$zero");
		registerOpcode.put(1, "$at");
		registerOpcode.put(2, "$v0");
		registerOpcode.put(3, "$v1");
		registerOpcode.put(4, "$a0");
		registerOpcode.put(5, "$a1");
		registerOpcode.put(6, "$a2");
		registerOpcode.put(7, "$a3");
		registerOpcode.put(8, "$t0");
		registerOpcode.put(9, "$t1");
		registerOpcode.put(10, "$t2");
		registerOpcode.put(11, "$t3");
		registerOpcode.put(12, "$t4");
		registerOpcode.put(13, "$t5");
		registerOpcode.put(14, "$t6");
		registerOpcode.put(15, "$t7");
		registerOpcode.put(16, "$s0");
		registerOpcode.put(17, "$s1");
		registerOpcode.put(18, "$s2");
		registerOpcode.put(19, "$s3");
		registerOpcode.put(20, "$s4");
		registerOpcode.put(21, "$s5");
		registerOpcode.put(22, "$s6");
		registerOpcode.put(23, "$s7");
		registerOpcode.put(24, "$t8");
		registerOpcode.put(25, "$t9");
		registerOpcode.put(26, "$k0");
		registerOpcode.put(27, "$k1");
		registerOpcode.put(28, "$gp");
		registerOpcode.put(29, "$fp");
		registerOpcode.put(30, "$sp");
		registerOpcode.put(31, "$ra");

	}

	private void initRegister() {
		register.put("$zero", 0);
		register.put("$at", 1);
		register.put("$v1", 2);
		register.put("$v2", 3);
		register.put("$a0", 4);
		register.put("$a1", 5);
		register.put("$a2", 6);
		register.put("$a3", 7);
		register.put("$t0", 8);
		register.put("$t1", 9);
		register.put("$t2", 10);
		register.put("$t3", 11);
		register.put("$t4", 12);
		register.put("$t5", 13);
		register.put("$t6", 14);
		register.put("$t7", 15);
		register.put("$s0", 16);
		register.put("$s1", 17);
		register.put("$s2", 18);
		register.put("$s3", 19);
		register.put("$s4", 20);
		register.put("$s5", 21);
		register.put("$s6", 22);
		register.put("$s7", 23);
		register.put("$t8", 24);
		register.put("$t9", 25);
		register.put("$k0", 26);
		register.put("$k1", 27);
		register.put("$gp", 28);
		register.put("$sp", 29);
		register.put("$fp", 30);
		register.put("$ra", 31);

	}

	public HashMap<String, Integer> getRegister() {
		return register;
	}

	public HashMap<Integer, String> getRegisterOpcode() {
		return registerOpcode;
	}

	public HashMap<String, Integer> getInstruction() {
		return instruction;
	}

	public HashMap<Integer, String> getInstructionOpcode() {
		return instructionOpcode;
	}

	public HashMap<String, Integer> getFunction() {
		return function;
	}

	public HashMap<Integer, String> getFunctionOpcode() {
		return functionOpcode;
	}

}

package Instructions;

import java.util.Arrays;

public class RFormat {
	int[] opcode, rs, rt, rd, shamt, fn;
	
	public RFormat(int[] instruction) {
		opcode = new int[6];
		rs = new int [5];
		rt = new int [5];
		rd = new int [5];
		shamt = new int [5];
		fn = new int [6];
		for(int i = 0; i < 32; i++) {
			if (i < 6) {
				opcode[i] = instruction[i];
			} else if (i < 11) {
				rs[i-6] = instruction[i];
			}
			else if(i < 16) {
				rt[i-11] = instruction[i];
			} else if (i < 21) {
				rd[i-16] = instruction[i];
			} else if (i < 26) {
				shamt[i - 21] = instruction[i];
			} else {
				fn[i - 26] = instruction[i];
			}
		}
	}

	public int[] getOpcode() {
		return opcode;
	}

	public int[] getRs() {
		return rs;
	}

	public int[] getRt() {
		return rt;
	}

	public int[] getRd() {
		return rd;
	}

	public int[] getShamt() {
		return shamt;
	}

	public int[] getFn() {
		return fn;
	}
	public static void main(String[] args) {
		int[]x = new int[32];
		x[6] = 1;
		RFormat inst = new RFormat(x);
		System.out.println(Arrays.toString(inst.getRs()));
	}
}

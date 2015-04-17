package Instructions;

public class IFormat {
int[] opcode, rs, rt, offset;
	
	public IFormat(int[] instruction) {
		opcode = new int[6];
		rs = new int [5];
		rt = new int [5];
		offset = new int [16];
		for(int i = 0; i < 32; i++) {
			if (i < 6) {
				opcode[i] = instruction[i];
			} else if (i < 11) {
				rs[i-6] = instruction[i];
			}
			else if(i < 16) {
				rt[i-11] = instruction[i];
			} else {
				offset[i - 16] = instruction[i];
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

	public int[] getOffset() {
		return offset;
	}
}

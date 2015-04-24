package Instructions;

public class JFormat extends Instruction {
int[] opcode, address;
	
	public JFormat(int[] instruction) {
		opcode = new int[6];
		address = new int [26];
		for(int i = 0; i < 32; i++) {
			if (i < 6) {
				opcode[i] = instruction[i];
			} else {
				address[i-6] = instruction[i];
			}
		}
	}

	public int[] getOpcode() {
		return opcode;
	}

	public int[] getAddress() {
		return address;
	}
}

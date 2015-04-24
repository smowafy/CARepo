package Executer;
import Components.BitsConverter;
import Memory.DataMemory;

public class Alu {
	DataMemory memory;
	BitsConverter conv;
	
	public Alu(DataMemory memory){
		this.memory = memory;
		conv = new BitsConverter();
	}
	public int zero(int[] value1, int[]value2){
		int value12 = conv.BitsToInteger(value1);
		int value22 = conv.BitsToInteger(value2);
		int[] result = execSub(value12,value22);
		boolean zero = conv.BitsToInteger(result)==0;
		return(zero)?1:0;
		
	}
	public int [] doOperation(int []value1, int[]value2, int[]aluControlInput){
		int op = conv.BitsToInteger(aluControlInput);
		int value21 = conv.BitsToInteger(value1);
		int value22 = conv.BitsToInteger(value2);
		int value1u = conv.BitsToIntegerHelper(value1);
		int value2u = conv.BitsToIntegerHelper(value2);
		switch(op){
		case 2: return execAdd(value21, value22);
		case 6: return execSub(value21, value22);
		case 12: return execNor(value1, value2);
		case 7: return execSlt(value21, value22);
		case 0: return execAnd(value1, value2);
		case 1: return execSll(value1, value22);
		case 3: return execSrl(value1, value22);
		case 5: return execSltu(value1u, value2u);
		default : return execjr(value21, value22);
		}
	}
	
	public int[] execAdd(int value1, int value2){
		int[] result = new int[32];
		int sum = value1+value2;
		result = conv.IntegerToBits(sum);
		return result;
	}
	public int[] execSub(int value1, int value2){
		int[] result = new int[32];
		int diff = value1-value2;
		result = conv.IntegerToBits(diff);
		return result;
	}
	public int[] execNor(int[] value1, int[] value2){
		int[] result = new int[32];
		for (int i = 0; i < result.length; i++) {
			result[i] = 1- (value1[i] | value2[i]);
		}
		return result;
	}
	public int[] execSlt(int value1, int value2){
		int[] result = new int[32];
		int output=0;
		if (value1<value2)
			output=1;
		result = conv.IntegerToBits(output);
		return result;
	}
	public int[] execAnd(int[] rs, int[] rt){
		int[] result = new int[32];
		for (int i = 0; i < result.length; i++) {
			result[i]= rs[i] & rt[i];
		}
		return result;
	}
	public int[] sll(int[] input){
		for(int i = 1; i < input.length; i++) {
			input[i-1] = input[i];
		}
		input[31] = 0;
		return input;
	}
	public int[] srl(int[] input){
		for(int i = 30; i > 0; i--) {
			input[i+1] = input[i];
		}
		input[0] = 0;
		return input;
	}
	public int[] execSll(int[] value1, int value2){
		int[] result = value1;
		for (int i = value2; i >0; i--) {
			result = sll(result);
		}
		return result;
	}
	public int[] execSrl(int[] value1, int value2){
		int[] result = value1;
		for (int i = value2; i >0; i--) {
			result = srl(result);
		}
		return result;
	}

	public int[] execSltu(int value1, int value2){
		int[] result = new int[32];
		int output=0;
		if (value1<value2)
			output=1;
		result = conv.IntegerToBits(output);
		return result;
	}

	public int[] execjr(int rs, int rt){
		// jr is not handles in execute phase
		return null;
	}
	

}

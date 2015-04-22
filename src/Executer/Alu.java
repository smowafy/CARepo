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
		int value12 = conv.BitsToInterger(value1, 32);
		int value22 = conv.BitsToInterger(value2, 32);
		int[] result = execSub(value12,value22);
		boolean zero = conv.BitsToInterger(result,32)==0;
		return(zero)?1:0;
		
	}
	public int [] doOperation(int []value1, int[]value2, int[]aluControlInput){
		int op = conv.BitsToInterger(aluControlInput, 4);
		int value21 = conv.BitsToInterger(value1, 32);
		int value22 = conv.BitsToInterger(value2, 32);
		
		switch(op){
		case 2: return execAdd(value21, value22);
		case 6: return execSub(value21, value22);
		case 12: return execNor(value1, value2);
		case 7: return execSlt(value21, value22);
		case 0: return execAnd(value1, value2);
		default : return null;
		//case "sll": return execSll(value21, value22);
		//case "srl": return execSrl(value21, value22);
		//case "sltu": return execSltu(value21, value22);
		//case "jr": return execjr(value21, value22);
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
	public int[] execNor(int[] rs, int[] rt){
		int[] result = new int[32];
		for (int i = 0; i < result.length; i++) {
			result[i] = 1- (rs[i] | rt[i]);
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
	public int[] execSll(int rs, int rt){
		int[] result = new int[32];
		return result;
	}
	public int[] execSrl(int rs, int rt){
		int[] result = new int[32];
		return result;
	}

	public int[] execSltu(int rs, int rt){
		int[] result = new int[32];
		return result;
	}

	public int[] execjr(int rs, int rt){
		int[] result = new int[32];
		return result;
	}
	

}

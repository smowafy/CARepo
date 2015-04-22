package Executer;
import Components.BitsConverter;
public class AluControl {
	
	public int[] selectOperation(int[] aluOp, int[] aluControlInput){
		
		BitsConverter converter = new BitsConverter();
		int[] result = new int[4];
		int op;
		if (aluOp[0]==0 && aluOp[1]==0){//Lw-Sw
			op = 2;
			result = converter.IntegerToBits(op);
		}
		else{
			if(aluOp[0]==1 && aluOp[1]==0){//R-Type
				int input = converter.BitsToInterger(aluControlInput, 6);
				switch(input){
				case 32: op = 2; break;
				case 34: op = 6; break;
				case 39: op = 12; break;
				case 42: op = 7; break;
				case 36: op = 0; break;
				default: op = 0; //temporarily
				//case 41: op= ?? sltu
				//case 8: op=?? jr
				//case 0: op=?? sll
				//case 2: op=?? srl
				}
				result = converter.IntegerToBits(op);
			}
			else{
				if(aluOp[0]==0 && aluOp[1]==1){//beq
					op = 6;
					result = converter.IntegerToBits(op);
				}
			}
		}
		return result;
		}

}

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
				int input = converter.BitsToInteger(aluControlInput);
				switch(input){
				case 32: op = 2; break;
				case 34: op = 6; break;
				case 39: op = 12; break;
				case 42: op = 7; break;
				case 36: op = 0; break;
				case 41: op = 5; break; // 5 chosen as control bits for sltu
				case 0: op = 1; break; // 1 chosen as control bits for sll
				case 2: op = 3; break; // 3 chosen as control bits for srl
				default: op = 25; // input for jr which is not handled so any number chosen is ok
				}
				result = converter.IntegerToBits(op);
			}
			else{
				if(aluOp[0]==0 && aluOp[1]==1){//beq
					op = 6;
					result = converter.IntegerToBits(op);
				}
				else{ //bne
					op = 20; // any number chosen for bne is ok since its not handled in execute
				}
			}
		}
		return result;
		}

}
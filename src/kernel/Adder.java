package kernel;

import util.ConstDefinition;
import util.Instr;
import util.Instr.OP;

public class Adder {
	private int time;
	private Instr.OP op;
	private FloatPointRegister fp;
	Adder(FloatPointRegister fp){
		this.fp = fp;
	}
	void run(Instr.OP op) {
		this.op = op;
		if (op == OP.ADD) {
			time = ConstDefinition.OP_TIME[0];
		}
		else if (op == OP.SUB) {
			time = ConstDefinition.OP_TIME[1];
		}
		else {
			System.out.println("操作符有错误！");
		}
	}
	void activate() {
		time--;
		if (time == 0) {
			if (op == OP.ADD) {
				ans = ;
			}
			else {
				ans = ;
			}
			fp.set(op., ans);
		}
	}
	
}

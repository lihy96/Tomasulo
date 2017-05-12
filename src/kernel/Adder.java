package kernel;

import util.ConstDefinition;
import util.Instr;
import util.Instr.OP;
import util.Instr.REG;

public class Adder {
	private int time;
	private Instr instr;
	private FloatPointRegister fp;
	Adder(FloatPointRegister fp){
		this.fp = fp;
	}
	void run(Instr instr) {
		this.instr = instr;
		if (instr.op == OP.ADD) {
			time = ConstDefinition.OP_TIME[0];
		}
		else if (instr.op == OP.SUB) {
			time = ConstDefinition.OP_TIME[1];
		}
		else {
			System.out.println("操作符有错误！");
		}
	}
	void activate() {
		time--;
		if (time == 0) {
			double ans;
			if (instr.op == OP.ADD) {
				ans = fp.get(instr.src1.ordinal()) + fp.get(instr.src2.ordinal());
			}
			else {
				ans = fp.get(instr.src1.ordinal()) - fp.get(instr.src2.ordinal());
			}
			fp.set(instr.des.ordinal(), ans);
		}
	}
	
}
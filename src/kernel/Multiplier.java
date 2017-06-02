package kernel;

import main.MainDriver;
import util.ConstDefinition;
import util.Instr;
import util.Instr.OP;

public class Multiplier {
	private int time;
	private Instr instr;
	private MainDriver main;
	public Multiplier(MainDriver main){
		this.main = main;
	}
	void run(Instr instr) {
		this.instr = instr;
		if (instr.op == OP.MUL) {
			time = ConstDefinition.OP_TIME[0];
		}
		else if (instr.op == OP.DIV) {
			time = ConstDefinition.OP_TIME[1];
		}
		else {
			System.out.println("操作符有错误！");
		}
	}
	void activate() {
		time--;
		if (time == 0) {
			float ans;
			if (instr.op == OP.MUL) {
				ans = main.fp.get(instr.src1.ordinal()) * main.fp.get(instr.src2.ordinal());
			}
			else {
				ans = main.fp.get(instr.src1.ordinal()) / main.fp.get(instr.src2.ordinal());
			}
			main.fp.set(instr.des.ordinal(), ans);
		}
	}
	
}

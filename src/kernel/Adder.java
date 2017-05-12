package kernel;

import util.ConstDefinition;
import util.Instr;
import util.Instr.OP;

public class Adder {
	private int time;
	private int op1, op2, d;//两个源寄存器，目的寄存器
	private Instr.OP op;
	void run(Instr.OP op, int d, int op1, int op2) {
		this.op = op;
		this.d = d;
		this.op1 = op1;
		this.op2 = op2;
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
	
}

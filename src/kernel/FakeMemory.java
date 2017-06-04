package kernel;

import java.util.ArrayList;

import main.Clock;
import util.ConstDefinition;
import util.Instr;
import util.Instr.OP;

public class FakeMemory {
	private double m[] = new double[ConstDefinition.MEM_NUM];
//	private int time;
//	private Instr instr;
//	public FakeMemory() {}
	public ArrayList<ArrayList<String>> get(int begin, int end) {
		ArrayList<ArrayList<String>> mem = new ArrayList<ArrayList<String>>();
		ArrayList<String> val = new ArrayList<String>();
		ArrayList<String> name = new ArrayList<String>();
		for (int i = begin; i < end; ++i) {
			name.add("" + i);
			val.add("" + get(i));
		}
		mem.add(name);
		mem.add(val);
		return mem;
	}
	public double get(int k) {
		return m[k];
	}
	public void set(int k, double value) {
		m[k] = value;
	}
	public FakeMemory() {
		for (int i = 0; i < ConstDefinition.MEM_NUM; ++i) {
			m[i] = 0.0;
		}
	}
	/*void run(Instr instr) {
		this.instr = instr;
		if (instr.op == OP.LOAD) {
			time = ConstDefinition.OP_TIME[4];
		}
		else if (instr.op == OP.STOR) {
			time = ConstDefinition.OP_TIME[5];
		}
		else {
			System.out.println("操作符有错误！");
		}
	}
	
	public void activate() {
		time--;
		if (time == 0) {
			FP fp = MainDriver.fp;
			if (instr.op == OP.LOAD) {
				fp.set(instr.src1, get(instr.imm));
			}
			else {
				set(instr.imm, fp.get(instr.src1));
			}
		}
	}*/
}

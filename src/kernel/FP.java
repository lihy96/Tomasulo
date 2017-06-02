package kernel;

import java.util.PriorityQueue;
import java.util.Queue;

import main.MainDriver;

public class FP {

	public enum REG {
		F0,
		F1,
		F2,
		F3,
		F4,
		F5,
		F6,
		F7,
		F8,
		F9
	};
	
	public static FP getInstance() {
		return FP.getInstance(null);
	}
	
	public static FP getInstance(MainDriver __main) {
		if (__fp == null) {
			__fp = new FP();
			__fp.init();
			__fp.main = __main;
		}
		return __fp;
	}
	
	public void set(Integer id, float num) {
		regs[id].num = num;
	}
	public void set(REG reg, float num) {
		regs[reg.ordinal()].num = num;
	}
	public float get(Integer id) {
		return regs[id].num;
	}
	public float get(REG reg) {
		return regs[reg.ordinal()].num;
	}
	
	public void setQ(REG reg, ReserveStackEntry rse) {
		regs[reg.ordinal()].state = rse;
	}
	public ReserveStackEntry getQ(REG reg) {
		return regs[reg.ordinal()].state;
	}
	
	public FloatEntry[] getState() {
		return regs;
	}
	
	public void print() {
		for (FloatEntry fEntry : regs) {
			System.out.println(fEntry.reg.name() + " : " + 
					fEntry.num + "\t" + 
					((fEntry.state == null) ? "null" : fEntry.state.getID()));
		}
	}
	
	private FP() {};
	private static FP __fp = null;
	private FloatEntry[] regs = null;
	private MainDriver main = null; 

	private static class FloatEntry {
		REG reg = null;
		float num = 0;
		ReserveStackEntry state = null;
		
		public FloatEntry(REG __reg) {
			reg = __reg;
		}
	}
	
	private void init() {
		REG[] enums = REG.values();
		regs = new FloatEntry[enums.length];
		for (int i = 0; i < enums.length; ++i) {
			regs[i] = new FloatEntry(enums[i]);
		}
	}
	
	public static void main(String[] args) {
		FP fp = FP.getInstance();
		REG reg = REG.F0;
		fp.set(reg, 10);
		reg = REG.F3;
		fp.set(reg, 6.7f);
		reg = REG.F9;
		fp.set(reg, 30.3f);
		fp.print();
	}
}

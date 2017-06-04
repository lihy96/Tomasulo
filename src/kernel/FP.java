package kernel;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

import main.Clock;

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
		F9,
		F10
	};
	
	public static FP getInstance() {
		if (__fp == null) {
			__fp = new FP();
			__fp.init();
		}
		return __fp;
	}
	public static void clear(FP fp) {
		for (FloatEntry fe : fp.regs) {
			fe.num = 0;
			fe.state = null;
		}
	}
	public static void listen(FP fp, ReserveStackEntry rse) {
		for (FloatEntry fe : fp.regs) {
			if (fe.state == rse) {
				fe.state = null;
				fe.num = Clock.CDB_DATA;
			}
		}
	}
	public static void print(FP fp) {
		for (FloatEntry fEntry : fp.regs) {
			System.out.println(fEntry.reg.name() + " : " + 
					fEntry.num + "\t" + 
					((fEntry.state == null) ? "null" : fEntry.state.getID()));
		}
	}
	
	public ArrayList<ArrayList<String>> get_fp() {
		ArrayList<ArrayList<String>> fp = new ArrayList<ArrayList<String>>();
		ArrayList<String> _regs = new ArrayList<>();
		ArrayList<String> _states = new ArrayList<>();
		for (int i = 0; i < regs.length; ++i) {
			_regs.add("" + regs[i].num);
			_states.add("" + (regs[i].state == null ? "" : regs[i].state.getID()));
		}
		fp.add(_states);
		fp.add(_regs);
		return fp;
	}
	
	public void set(Integer id, double num) {
		regs[id].num = num;
	}
	public void set(REG reg, double num) {
		regs[reg.ordinal()].num = num;
	}
	public double get(Integer id) {
		return regs[id].num;
	}
	public double get(REG reg) {
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
	
	private FP() {};
	private static FP __fp = null;
	private FloatEntry[] regs = null;
	private Clock main = null; 

	private static class FloatEntry {
		private REG reg = null;
		double num = 0;
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
		FP.print(fp);
	}
}

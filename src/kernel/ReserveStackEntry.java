package kernel;

import main.MainDriver;
import util.Instr;

/**
 * 保留站的结构
 */
public class ReserveStackEntry {
	private static int id = 0;
	
	private int __id;	// 本保留站独有的id
	public Instr.OP OP;	// 要对源操作数进行的操作
	public ReserveStackEntry Qj = null, Qk = null;	// 将产生源操作数的保留站号.
	// 源操作数的值，V和Q只有一个有效。对于load来说，Vk字段用于保存偏移量
	public Float Vj = null, Vk = null;	
	public boolean Busy;	// 为True表示本保留站或缓冲单元“忙”
	public Integer A;	// 仅load和store缓冲器有该字段。开始 是存放指令中的立即数字段，地址计算后存放有效地址
	
	public ReserveStackEntry() {
		__id = id ++;
	}
	
	public ReserveStackEntry(boolean flag) {
		if (flag) {
			__id = id ++;
		}
	}
	
	public int getID() {
		return __id;
	}
	
	public int getTotalEntryNum() {
		return id;
	}
	
	public static ReserveStackEntry[] initGroup(int size) {
		ReserveStackEntry[] group = null;
		group = new ReserveStackEntry[size];
		for (int i = 0; i < size; ++i) {
			group[i] = new ReserveStackEntry();
		}
		return group;
	}
	
	public static void setReserveEntry(ReserveStackEntry rse, Instr itr) {
		rse.OP = itr.op;
		rse.A = itr.imm;
		
		FP fp = MainDriver.fp;
		// instr中的src1必定不为空，无需判断
		rse.Qj = fp.getQ(itr.src1);
		if (rse.Qj == null) {
			rse.Vj = fp.get(itr.src1);
		}
		if (itr.src2 != null) {
			rse.Qk = fp.getQ(itr.src2);
			if (rse.Qk == null)
				rse.Vk = fp.get(itr.src2);
		}
		
		// 修改目的寄存器
		fp.setQ(itr.des, rse);
		// 修改保留站状态
		rse.Busy = true;
	}
	
	public static void freeReserveEntry(ReserveStackEntry rse) {
		rse.Busy = false;
	}
	
	public static ReserveStackEntry getFreeGroup(ReserveStackEntry[] stack) {
		ReserveStackEntry reserveStackEntry = null;
		for (ReserveStackEntry rse : stack) {
			if (rse.Busy) continue;
			reserveStackEntry = rse;
			break;
		}
		return reserveStackEntry;
	}
}

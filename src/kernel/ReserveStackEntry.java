package kernel;

import main.Clock;
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
	public Double Vj = null, Vk = null;	
	// 为True表示本保留站或缓冲单元“忙”,初始化为空闲
	public boolean Busy = false;	
	// 仅load和store缓冲器有该字段。开始 是存放指令中的立即数字段，地址计算后存放有效地址
	public Integer A = null;	
	
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
	public String toString() {
		return "" + __id + "\t" + OP + " : " + 
				"<" + (Qj == null ? "null" : Qj.getID()) + 
					", " + (Qk == null ? "null" : Qk.getID()) + ">---" +
				"<" + Vj + ", " + Vk + ", " + A + "> : " + 
				"Busy ?" + Busy;
	}
	
	public static void print(ReserveStackEntry[] group) {
		for (ReserveStackEntry rse : group) {
			System.out.println(rse.toString());
		}
	}
	
	public static ReserveStackEntry[] initGroup(int size) {
		assert(size <= 0);
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
		
		FP fp = Clock.fp;
		if (itr.src1 != null) {
			rse.Qj = fp.getQ(itr.src1);
			if (rse.Qj == null) {
				rse.Vj = fp.get(itr.src1);
			}
		}
		if (itr.src2 != null) {
			rse.Qk = fp.getQ(itr.src2);
			if (rse.Qk == null)
				rse.Vk = fp.get(itr.src2);
		}
		
		// 修改目的寄存器
		if (itr.des != null) 
			fp.setQ(itr.des, rse);
		// 修改保留站状态
		rse.Busy = true;
	}
	
	public static void freeReserveEntry(ReserveStackEntry rse) {
		rse.Busy = false;
	}
	
	public static ReserveStackEntry getFreeEntry(ReserveStackEntry[] group) {
		ReserveStackEntry reserveStackEntry = null;
		for (ReserveStackEntry rse : group) {
			if (rse.Busy) continue;
			reserveStackEntry = rse;
			break;
		}
		return reserveStackEntry;
	}
	
	public static ReserveStackEntry getRunnableEntry(ReserveStackEntry[] group, Instr.OP op) {
		ReserveStackEntry reserveStackEntry = null;
		for (ReserveStackEntry rse : group) {
			/* 如果保留站为空闲状态，跳过 */
			if (!rse.Busy) continue;
			
			boolean is_ok = false;
			switch(op) {
			case ADD:
			case SUB:
			case MUL:
			case DIV:
				is_ok = (rse.Vj != null && rse.Vk != null); break;
			case LOAD:
				is_ok = true; break;
			case STOR:
				is_ok = (rse.Vj != null && rse.A != null); break;
			}
			if (is_ok) {
				reserveStackEntry = rse;
				break;
			}
		}
		return reserveStackEntry;
	}
	
	public static void listen(ReserveStackEntry[] group, ReserveStackEntry rse) {
		for (ReserveStackEntry tmp : group) {
			if (!tmp.Busy) continue;
			if (tmp.Qj == rse) {
				tmp.Qj = null;
				tmp.Vj = Clock.CDB_DATA;
			}
			if (tmp.Qk == rse) {
				tmp.Qk = null;
				tmp.Vk = Clock.CDB_DATA;
			}
		}
	}
}

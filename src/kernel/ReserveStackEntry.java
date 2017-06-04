package kernel;

import java.util.ArrayList;

import main.Clock;
import main.MainDriver;
import util.Instr;

/**
 * 保留站的结构
 */
public class ReserveStackEntry {
	private static int id = 0;
	
	private int __id;	// 本保留站独有的id
	private String __name;
	private Instr instr;
	public Instr.OP OP;	// 要对源操作数进行的操作
	public ReserveStackEntry Qj = null, Qk = null;	// 将产生源操作数的保留站号.
	// 源操作数的值，V和Q只有一个有效。对于load来说，Vk字段用于保存偏移量
	public Double Vj = null, Vk = null;	
	// 为True表示本保留站或缓冲单元“忙”,初始化为空闲
	public boolean Busy = false;	
	// 仅load和store缓冲器有该字段。开始 是存放指令中的立即数字段，地址计算后存放有效地址
	public Integer A = null;	
	
	public ReserveStackEntry(String _name) {
		__id = id ++;
		__name = _name;
	}
	
	public ReserveStackEntry(boolean flag) {
		if (flag) {
			__id = id ++;
		}
	}
	public int getID() {
		return __id;
	}
	public String getName() {
		return __name;
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
	
	public static ArrayList<ArrayList<String>> get_reserved_entrys(ReserveStackEntry[] group, int time) {
		ArrayList<ArrayList<String>> reserve_entrys = new ArrayList<ArrayList<String>>();
		for (ReserveStackEntry rse : group) {
			ArrayList<String> entry = new ArrayList<String>();
			entry.add("" + time);
			entry.add("" + rse.getName());
			entry.add("" + rse.Busy);
			entry.add("" + ((rse.OP == null) ? "" : rse.OP.name()));
			entry.add("" + rse.Vj);
			entry.add("" + rse.Vk);
			entry.add("" + ((rse.Qj == null) ? "" : rse.Qj.getName()));
			entry.add("" + ((rse.Qk == null) ? "" : rse.Qk.getName()));
			reserve_entrys.add(entry);
		}
		return reserve_entrys;
	}
	
	public static ReserveStackEntry[] initGroup(int size, String prefix) {
		assert(size <= 0);
		ReserveStackEntry[] group = null;
		group = new ReserveStackEntry[size];
		for (int i = 0; i < size; ++i) {
			group[i] = new ReserveStackEntry(prefix + i);
		}
		return group;
	}
	
	public static void setReserveEntry(ReserveStackEntry rse, Instr itr) {
		// 添加进监控状态表
		Clock.running_state.add(itr);
		rse.instr = itr;
		rse.instr.state.flow = true;
		
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
	
	public static void freeReserveEntry(ReserveStackEntry[] group, ReserveStackEntry rse) {
		for (Instr instr : Clock.running_state) {
			if (instr == rse.instr) {
				Clock.running_state.remove(instr);
			}
		}
		rse.Busy = false;
		/* load和store缓冲区需要模仿队列操作 */
		if (rse.OP == Instr.OP.LOAD || rse.OP == Instr.OP.STOR) {
			adjustFakeQueue(group);
		}
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
	
	private static void adjustFakeQueue(ReserveStackEntry[] group) {
		/* 该函数只有在释放保留站的时候才调用，且释放的保留站的idx为0 */
		int idx = 0;
		for (int i = 1; i < group.length; ++i) {
			if (group[i].Busy) {
				if (i == idx) continue;
				ReserveStackEntry rse = group[i];
				group[i] = group[idx];
				group[idx] = rse;
				idx ++;
			}
		}
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
				is_ok = true;
				
				break;
			case STOR:
				is_ok = (rse.Vj != null && rse.A != null); break;
			}
			if (is_ok) {
				reserveStackEntry = rse;
				reserveStackEntry.instr.state.running = true;
				break;
			}
		}
		return reserveStackEntry;
	}
	
	public static void listen(ReserveStackEntry[] group, ReserveStackEntry rse) {
		rse.instr.state.write_back = true;
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

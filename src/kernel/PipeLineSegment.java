package kernel;

import util.Instr;

public class PipeLineSegment {
	public ReserveStackEntry rse = null;
	public Instr.OP OP = null;	// 要对源操作数进行的操作
	// 源操作数的值
	public Double Vj = null, Vk = null;	
	// 表示当前保留站是否有指令正在执行
	public boolean Busy = false;	
	// 仅load和store缓冲器有该字段。开始是存放指令中的立即数字段，地址计算后存放有效地址
	public Integer A = null;	
	
	private int segment = 0;
	private int time = 0;
	private int total_time = 0;
	private boolean need_schedule = false;
	private int[] pl_time = null;
	private Integer pl_num = null;

	public boolean load(ReserveStackEntry _rse, int[] _time) {
		if (_rse == null) return false;
		
		this.rse = _rse;
		// 如果是载入流水线过程，那么默认数据都已经准备好了，所以对于Qj, Qk不进行载入
		this.OP = _rse.OP;
		this.Vj = _rse.Vj; this.Vk = _rse.Vk;
		// 设置当前保留站正在执行指令
		this.Busy = true;
		this.A = _rse.A;
		
		segment = 0;
		total_time = 0;
		time = 0;
		need_schedule = false;
		
		// set time
		pl_time = _time;
		pl_num = pl_time.length;
//		pl_num = ConstDefinition.OP_PILELINE_NUM[OP.ordinal()];
//		pl_time = ConstDefinition.OP_PILELINE_TIME[OP.ordinal()];
		return true;
	}
	
	public boolean activate() {
		if (need_schedule) return need_schedule;
		
		total_time ++;
		if ((segment < pl_num) && (time < pl_time[segment])) {
			time ++;
		}
		
		if (time == pl_time[segment]) {
			segment ++; time = 0;
			need_schedule = true;
		}
		
		if (segment == pl_num) {
			need_schedule = true;
		}
		
		return need_schedule;
	}
	
	public String toString() {
		return (!this.Busy) ? "null" : 
				(this.rse.getName() + "\t" + 
				OP.name() + " : " +
				"<" + Vj + ", " + Vk + ", " + A + "> " + 
				"seg? " + segment + ", " + 
				"time? " + time + ", " + 
				"schedule? " + need_schedule);
	}
	public void schedule() {
		need_schedule = false;
	}
	public void free() {
		Busy = false;
	}
	public int getTime() {
		return total_time;
	}
	public void clear() {
		this.rse = null;
		
		this.OP = null;
		this.Vj = null;
		this.Vk = null;	
		this.Busy = false;	
		this.A = null;	
		
		this.segment = 0;
		this.total_time = 0;
		this.time = 0;
		this.need_schedule = false;
		this.pl_time = null;
		this.pl_num = null;
	}
}

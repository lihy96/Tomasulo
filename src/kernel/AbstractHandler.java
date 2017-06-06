package kernel;

import java.util.ArrayList;

import main.Clock;
import util.ConstDefinition;
import util.Instr.OP;

public abstract class AbstractHandler {
	// 4 or 5
	protected PipeLineSegment[] pipeline = new PipeLineSegment[0];
	protected ReserveStackEntry[] group;
	public AbstractHandler(ReserveStackEntry[] _group){
		for (int i = 0; i < pipeline.length; ++i) {
			// 各段流水线的模拟，简单保存保留站
			pipeline[i] = new PipeLineSegment();
		}
		this.group = _group;
	}

	public ArrayList<Integer> getTime() {
		ArrayList<Integer> times = new ArrayList<Integer>();
		for (int i = 0; i < pipeline.length; ++i) {
			times.add(pipeline[i].getTime());
		}
		return times;
	}
	
	public void print() {
		for (PipeLineSegment pls : pipeline) {
			System.out.println(pls.toString());
		}
	}
	
	public void clear() {
		for (int i = 0; i < pipeline.length; ++i) {
			pipeline[i].clear();
		}
	}
	
	public void activate() {
		/* 如果流水段空闲, 表示没有指令正在执行或者指令已经进入下一段流水，需要寻找一个可执行的保留站。 */
		if (!pipeline[0].Busy) {
			ReserveStackEntry crRse = ReserveStackEntry.getRunnableEntry(group);
			/* 如果有可执行保留站，加载至流水段 */
			if (crRse != null) {
				int[] time = ConstDefinition.OP_PILELINE_TIME[crRse.OP.ordinal()];
				pipeline[0].load(crRse, time);
			}
		}

		for (int i = pipeline.length; i > 0; --i) {
			PipeLineSegment pls = pipeline[i - 1];
			if (!pls.Busy) continue;

			System.out.println("Find pipeline to execute : " + (i-1));
			boolean need_schedule = pls.activate();
			System.out.println("Run instr : " + pls.toString());
			// 指令是否需要调度进入下一段流水线
			if (need_schedule) {
				if (i == pipeline.length) {
					calFunc(pls);
					
					System.out.println("End instr.");
					
					/* 保留站和流水段计算完需要释放 */
					pls.free();
					ReserveStackEntry.freeReserveEntry(group, pls.rse);
				}
				else {
					// 如果调度成功，进入下一段流水线
					if (schedule(i - 1)) pls.schedule();
				}
			}
		}
	}
	
	protected void calFunc(PipeLineSegment pls) {
		double ans = (pls.OP == OP.ADD) ? pls.Vj + pls.Vk : pls.Vj - pls.Vk;
		
		/** 
		 * 将计算结果放入总线，并唤醒其他等待该保留站计算结果的保留站
		 * 如果有寄存器的保留站状态为当前状态，则写入 
		 */
		Clock.wake_up(pls.rse, ans);
	}
	
	// 调度算法，交换当前流水段和下一段,返回是否调度成功
	protected boolean schedule(int idx) {
		if (idx + 1 == pipeline.length) {
			System.out.println("> Pipeline index error.");
			return false;
		}
		
		PipeLineSegment pls = pipeline[idx + 1];
		// 检查下一个流水段是否有指令正在执行，如果有，调度失败
		if (pls.Busy) return false;
		pipeline[idx + 1] = pipeline[idx];
		pipeline[idx] = pls;
		return true;
	}
}

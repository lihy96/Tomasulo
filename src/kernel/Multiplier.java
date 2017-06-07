package kernel;

import main.Clock;
import util.ConstDefinition;
import util.Instr.OP;

public class Multiplier extends AbstractHandler {
	// 2 or 3
	private PipeLineSegment[] pipeline = new PipeLineSegment[ConstDefinition.OP_PILELINE_TIME[2].length];
	public Multiplier(ReserveStackEntry[] _group) {
		super(_group);
		// 重新设置流水段
		super.pipeline = this.pipeline;
		for (int i = 0; i < pipeline.length; ++i) {
			// 各段流水线的模拟，简单保存保留站
			pipeline[i] = new PipeLineSegment();
		}
	}
	
	protected void calFunc(PipeLineSegment pls) {
		double ans = (pls.OP == OP.MULD) ? pls.Vj * pls.Vk : pls.Vj / pls.Vk;
		
		/** 
		 * 将计算结果放入总线，并唤醒其他等待该保留站计算结果的保留站
		 * 如果有寄存器的保留站状态为当前状态，则写入 
		 */
		Clock.wake_up(pls.rse, ans);
	}	
}

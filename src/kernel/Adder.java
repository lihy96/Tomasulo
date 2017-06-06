package kernel;

import main.Clock;
import util.ConstDefinition;
import util.Instr;
import util.Instr.OP;

public class Adder {
	private int time;
	private ReserveStackEntry crRse = null;
	public Adder(){}
	
	public int getTime() {
		return time;
	}
	
	public void clear() {
		time = 0;
		crRse = null;
	}
	
	public void activate() {
		assert(time < 0);
		
		/* 如果time为0, 表示当前运算部件没有执行操作，需要寻找一个可执行的保留站。 */
		if (time == 0) {
			crRse = ReserveStackEntry.getRunnableEntry(Clock.addGroup, Instr.OP.ADD);
			/* 如果没有可执行保留站，直接返回 */
			if (crRse == null) return ;
			System.out.println("Run instr : " + crRse.toString());
			setTime();
		}

		/* 默认时间减一 */
		time --;
		
		/* 如果time为0,表示当前运算部件将要执行完操作。 */
		if (time == 0) {
			double ans;
			if (crRse.OP == OP.ADD) {
				ans = crRse.Vj + crRse.Vk;
			}
			else {
				ans = crRse.Vj - crRse.Vk;
			}
			System.out.println("End instr : " + crRse.toString());
			
			/** 
			 * 将计算结果放入总线，并唤醒其他等待该保留站计算结果的保留站
			 * 如果有寄存器的保留站状态为当前状态，则写入 
			 */
			Clock.wake_up(crRse, ans);
			
			/* 保留站计算完需要释放 */
			ReserveStackEntry.freeReserveEntry(Clock.addGroup, crRse);
			crRse = null;
		}
	}
	
	private void setTime() {
		switch(crRse.OP) {
		case ADD:
			time = ConstDefinition.OP_TIME[0]; break;
		case SUB:
			time = ConstDefinition.OP_TIME[1]; break;
		default:
			System.out.println("Adder 操作符错误: " + crRse.toString());
			System.exit(2);
			break;
		}
	}
	
}

package kernel;

import main.MainDriver;
import util.ConstDefinition;
import util.Instr.OP;

public class Multiplier {
	private int time;
	private ReserveStackEntry crRse = null;
	public Multiplier(){}
	
	public void activate() {
		assert(time < 0);
		
		/* 如果time为0, 表示当前运算部件没有执行操作，需要寻找一个可执行的保留站。 */
		if (time == 0) {
			crRse = ReserveStackEntry.getRunnableEntry(MainDriver.mulGroup);
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
			if (crRse.OP == OP.MUL) {
				ans = crRse.Vj * crRse.Vk;
			}
			else {
				ans = crRse.Vj / crRse.Vk;
			}
			System.out.println("End instr : " + crRse.toString());
			
			/** 
			 * 将计算结果放入总线，并唤醒其他等待该保留站计算结果的保留站
			 * 如果有寄存器的保留站状态为当前状态，则写入 
			 */
			MainDriver.wake_up(crRse, ans);
			
			/* 保留站计算完需要释放 */
			ReserveStackEntry.freeReserveEntry(crRse);
			crRse = null;
		}
	}
	
	private void setTime() {
		switch(crRse.OP) {
		case MUL:
			time = ConstDefinition.OP_TIME[2]; break;
		case DIV:
			time = ConstDefinition.OP_TIME[3]; break;
		default:
			System.out.println("Multiplier 操作符错误: " + crRse.toString());
			System.exit(2);
			break;
		}
	}
	
}

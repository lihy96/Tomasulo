package kernel;

import main.Clock;
import util.ConstDefinition;
import util.Instr;

public class Storer {
	private int time;
	private ReserveStackEntry crRse = null;
	public Storer() {}
	
	public void activate() {
		assert(time < 0);
		
		/* 如果time为0, 表示当前运算部件没有执行操作，需要寻找一个可执行的保留站。 */
		if (time == 0) {
			crRse = ReserveStackEntry.getRunnableEntry(Clock.storeGroup, Instr.OP.STOR);
			/* 如果没有可执行保留站，直接返回 */
			if (crRse == null) return ;
			System.out.println("Run instr : " + crRse.toString());
			setTime();
		}
		
		/* 默认时间减一 */
		time --;
		
		/* 如果time为0,表示当前运算部件将要执行完操作。 */
		if (time == 0) {
			Clock.mem.set(crRse.A, crRse.Vj);
			System.out.println("End instr : " + crRse.toString());
			
			/* 保留站计算完需要释放 */
			ReserveStackEntry.freeReserveEntry(Clock.storeGroup, crRse);
			crRse = null;
		}
	}
	
	private void setTime() {
		switch(crRse.OP) {
		case STOR:
			time = ConstDefinition.OP_TIME[5]; break;
		default:
			System.out.println("Storer 操作符错误: " + crRse.toString());
			System.exit(2);
			break;
		}
	}
}

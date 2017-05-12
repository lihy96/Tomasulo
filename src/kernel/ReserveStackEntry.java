package kernel;

/*
 * 保留站的结构
 */
public class ReserveStackEntry {
	private static int id = 0;
	
	private int __id;
	public String OP;	// 要对源操作数进行的操作
	public ReserveStackEntry Qj, QK;	// 将产生源操作数的保留站号.
	public int Vj, Vk;	// 源操作数的值，V和Q只有一个有效。对于load来说，Vk字段用于保存偏移量
	public boolean Busy;	// 为True表示本保留站或缓冲单元“忙”
	public int A;	// 仅load和store缓冲器有该字段。开始是存放指令中的立即数字段，地址计算后存放有效地址
	
	public ReserveStackEntry() {
		__id = id ++;
	}
	
	public int getID() {
		return __id;
	}
	
	public int getTotalEntryNum() {
		return id;
	}
}

package util;

public final class ConstDefinition {
	// add, sub, mul, div, load, store
	public final static int[] OP_TIME = {
			2, 2, 10, 40, 2, 2
	};
	
	public final static int[][] OP_PILELINE_TIME = {
			{
				1, 1
			},
			{
				1, 1
			},
			{
				1, 2, 2, 1, 2, 2
			},
			{
				5, 5, 10, 6, 4, 10
			},
			{
				2
			},
			{
				2
			}
	};
//	static {
//		OP_PILELINE_NUM = new int[OP_PILELINE_TIME.length];
//		for (int i = 0; i < OP_PILELINE_TIME.length; ++i) {
//			OP_PILELINE_NUM[i] = OP_PILELINE_TIME[i].length;
//		}
//		if ((OP_PILELINE_NUM[0] != OP_PILELINE_NUM[1]) || 
//				(OP_PILELINE_NUM[2] != OP_PILELINE_NUM[3]) ||
//				(OP_PILELINE_NUM[4] != OP_PILELINE_NUM[5])) {
//			System.out.println("> Error at error pileline cycle.");
//			System.exit(2);;
//		}
//	}
	
	public final static int FP_NUM = 10;
	public final static int MEM_NUM = 5000;
	
	public final static int ADD_RESERVE_ENTRY_NUM = 3;
	public final static int MUL_RESERVE_ENTRY_NUM = 2;
	public final static int LOAD_RESERVE_ENTRY_NUM = 3;
	public final static int STORE_RESERVE_ENTRY_NUM = 3;
	public final static int MEM_ACCESS_RESERVE_ENTRY_NUM = 6;
}

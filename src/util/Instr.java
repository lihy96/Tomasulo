package util;

import kernel.ReserveStackEntry;

public class Instr {

	public enum OP {
		ADD,
		SUB,
		MUL,
		DIV,
		LOAD,
		STOR
	};
	
	public enum REG {
		F0,
		F1,
		F2,
		F3,
		F4,
		F5,
		F6,
		F7,
		F8,
		F9
	};
	
	public OP op;	// 要对源操作数进行的操作
	
	/* 源寄存器地址，
	 * Load，Store指令默认使用R1: LOAD des, imm(src1)
	 * 其他指令格式：ADD des, src1, src2
	 */
	public REG src1, src2;	
	public REG des;	// 目的地址寄存器
	public int imm;	// Load,Store指令中的立即数
	
	
}

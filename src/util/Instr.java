package util;

import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import kernel.FP;

//import kernel.ReserveStackEntry;

public class Instr {

	public enum OP {
		ADD,
		SUB,
		MUL,
		DIV,
		LOAD,
		STOR
	};
	
	public OP op;	// 要对源操作数进行的操作
	
	/* 源寄存器地址，
	 * Load，Store指令默认使用R1: LOAD des, imm(src1)
	 * 其他指令格式：ADD des, src1, src2
	 */
	public FP.REG src1 = null, src2 = null;	
	public FP.REG des = null;	// 目的地址寄存器
	public Integer imm = null;	// Load,Store指令中的立即数
	
	@Override
	public String toString() {
		return 	"OP : " + ((op == null) ? "null" : op.name()) + "\n" +
				"des : " + ((des == null) ? "null" : des.name()) + "\n" +
				"src : " + ((src1 == null) ? "null" : src1.name()) + 
						", " + ((src2 == null) ? "null" : src2.name()) + "\n" +
				"imm : " + imm;
	}
	
	public ArrayList<String> get_list() {
		ArrayList<String> arr = new ArrayList<>();
		arr.add(op.name());
		if (des != null) arr.add(des.name());
		if (src1 != null) arr.add(src1.name());
		if (src2 != null) arr.add(src2.name());
		if (imm != null) arr.add(imm.toString());
		return arr;
	}
	
}

package util;

import java.util.ArrayList;
import java.util.HashMap;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import kernel.FP;

//import kernel.ReserveStackEntry;

public class Instr {

	public enum OP {
		ADDD,
		SUBD,
		MULD,
		DIVD,
		LD,
		ST
	};
	
	public State state = new State();
	
	public OP op;	// 要对源操作数进行的操作
	
	/* 源寄存器地址，
	 * Load，Store指令默认使用R1: LOAD des, imm(src1)
	 * 其他指令格式：ADD des, src1, src2
	 */
	public FP.REG src1 = null, src2 = null;	
	public FP.REG des = null;	// 目的地址寄存器
	public Integer imm = null;	// Load,Store指令中的立即数
	private String instr = null;
	
	public Instr(String _instr) {
		instr = _instr;
	}
	
	@Override
	public String toString() {
		return instr;
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

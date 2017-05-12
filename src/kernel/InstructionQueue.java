package kernel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import util.ConstDefinition;
import util.FileReaderUtil;
import util.Instr;

/*
 * 指令队列
 */
public class InstructionQueue {
	Queue<ReserveStackEntry> itrsQue = new LinkedList<ReserveStackEntry>();
	
	public InstructionQueue(String fileName) {
	}
	
//	public void close() {
//		if (fru != null) fru.close();
//	}
	
	public boolean load(String fileName) {
		ArrayList<String> instrs = new ArrayList<String>();
		FileReaderUtil.readFileToList(fileName, instrs);
		
		for (String instr : instrs) {
			itrsQue.add(decodeInstr(instr));
		}
		return true;
	}
	
	public ReserveStackEntry decodeInstr(String instr) {
		String[] infos = instr.split("[ ,()]");
		System.out.println(infos);
		
		if (infos.length != 4) {
			System.out.println(">>> Error at decode instruction with wrong format.");
		}
		
		// 检查操作码
		Instr.OP op = Instr.OP.valueOf(infos[0]);
		switch (op) {
		case ADD : break;
		case SUB : break;
		case MUL : break;
		case DIV : break;
		case LOAD : break;
		case STOR : break;
		}
		
		ReserveStackEntry rse = new ReserveStackEntry(false);
		return rse;
	}
	
	public void mount(AbstractHandler ah) {
		
	}
}

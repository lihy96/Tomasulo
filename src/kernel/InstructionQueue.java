package kernel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import util.FileReaderUtil;

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
		// String 
		
		ReserveStackEntry rse = new ReserveStackEntry(false);
		return rse;
	}
	
	public void mount(AbstractHandler ah) {
		
	}
}

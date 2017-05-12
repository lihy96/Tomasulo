package kernel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import util.FileReaderUtil;
import util.Instr;

/*
 * 指令队列
 */
public class InstructionQueue {
	Queue<Instr> itrsQue = new LinkedList<Instr>();
	
	public InstructionQueue() {}
	
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
	
	public Instr decodeInstr(String instr) {
		String[] infos = instr.split("[ ,()]");
		System.out.println(infos);
		
//		if (infos.length != 4) {
//			System.out.println(">>> Error at decode instruction with wrong format.");
//		}
		
		Instr istr = new Instr();
		// 检查操作码
		try {
			Instr.OP op = Instr.OP.valueOf(infos[0]);
			switch (op) {
			case ADD : 
				
				break;
			case SUB : break;
			case MUL : break;
			case DIV : break;
			case LOAD : break;
			case STOR : break;
			default:
				// should not goto here.
				assert(true);
			}
		}
		catch (IllegalArgumentException e) {
			System.out.println(">>> Error at decode operator : " + infos[0]);
		}
		catch (NullPointerException e) {
			System.out.println(">>> Error at decode instruction : " + instr);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return istr;
	}
	
	public void mount(AbstractHandler ah) {
		
	}
}

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
		
		return load(instrs);
	}
	
	public boolean load(ArrayList<String> instrs) {
		for (String instr : instrs) {
			itrsQue.add(decodeInstr(instr));
		}
		return true;
	}
	
	public Instr decodeInstr(String instr) {
		String[] infos = instr.split("[\t ,()]");
		System.out.println(infos);

		String left = null, mid = null, right = null;
		try {
			int cc = 1;
			while ((left = infos[cc ++]).equals(""));
			while ((mid = infos[cc ++]).equals(""));
			while ((right = infos[cc ++]).equals(""));
			
			if (infos.length > cc) throw new Exception();
		}
		catch (Exception e) {
			System.out.println(">>> Error at decode instruction with wrong format.");
			return null;
		}

		Instr istr = new Instr();
		istr.des = Instr.REG.valueOf(left);
		// 检查操作码
		try {
			istr.op = Instr.OP.valueOf(infos[0]);
			switch (istr.op) {
			case ADD : 
			case SUB : 
			case MUL : 
			case DIV : 
				try {
					istr.src1 = Instr.REG.valueOf(mid);
				} catch (IllegalArgumentException e) {
					System.out.println(">>> Error at decode src1 : " + instr);
					return null;
				}

				try {
					istr.src2 = Instr.REG.valueOf(right);
				} catch (IllegalArgumentException e) {
					System.out.println(">>> Error at decode src2 : " + instr);
					return null;
				}
				break;
			case LOAD : 
			case STOR : 
				try {
					istr.imm = Integer.parseInt(mid);
				} catch (Exception e) {
					System.out.println(">>> Error at decode immediate number : " + instr);
					return null;
				}
				
				try {
					istr.src1 = Instr.REG.valueOf(right);
				} catch (IllegalArgumentException e) {
					System.out.println(">>> Error at decode src1 : " + instr);
					return null;
				}
				break;
			default:
				// should not goto here.
				assert(true);
			}
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println(">>> Error at decode operator : " + instr);
			return null;
		}
		catch (Exception e) {
			System.out.println(">>> Unrecognized error : " + instr);
			return null;
		}
		
		return istr;
	}
	
	public static void main(String[] args) {
		InstructionQueue iq = new InstructionQueue();
		Instr is;
		
		is = iq.decodeInstr("SUB F6, 34(F4)  ");
		if (is != null)
			System.out.println(is.toString());
		
		is = iq.decodeInstr("SUB F6, F5,  F4");
		if (is != null)
			System.out.println(is.toString());
		
		is = iq.decodeInstr("DIV F6, F7,F4");
		if (is != null)
			System.out.println(is.toString());
		
		is = iq.decodeInstr("STOR	 F6, 34(F4)  ");
		if (is != null)
			System.out.println(is.toString());
		
		is = iq.decodeInstr("LOAD F6, 34(F4)  ");
		if (is != null)
			System.out.println(is.toString());
	}
	
	public void mount(AbstractHandler ah) {
		
	}
}

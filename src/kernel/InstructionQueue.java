package kernel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import main.MainDriver;
import util.FileReaderUtil;
import util.Instr;

/*
 * 指令队列
 */
public class InstructionQueue {
	private MainDriver main;
	Queue<Instr> itrsQue = new LinkedList<Instr>();
	
	public InstructionQueue() {}	// 便于调试
	
	public InstructionQueue(MainDriver main) {
		this.main = main;
	}
	
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

		String op = null, left = null, mid = null, right = null;
		try {
			int cc = 0;
			while ((op = infos[cc ++]).equals(""));
			while ((left = infos[cc ++]).equals(""));
			while ((mid = infos[cc ++]).equals(""));
			while ((right = infos[cc ++]).equals(""));
			
			if (infos.length > cc) throw new Exception();
		}
		catch (Exception e) {
			System.out.println(">>> Error at decode instruction : \n\t" + instr);
			return null;
		}

		Instr istr = new Instr();
		// 检查操作码
		try {
			istr.op = Instr.OP.valueOf(op);
			istr.des = FP.REG.valueOf(left);
			switch (istr.op) {
			case ADD : 
			case SUB : 
			case MUL : 
			case DIV : 
				try {
					istr.src1 = FP.REG.valueOf(mid);
				} catch (IllegalArgumentException e) {
					System.out.println(">>> Error at decode src1 : " + instr);
					return null;
				}

				try {
					istr.src2 = FP.REG.valueOf(right);
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
					istr.src1 = FP.REG.valueOf(right);
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
	
	public void transfer(AbstractHandler ah) {
		
	}
	
	/*
	 * 调试主函数，
	 * 运行本函数，能够测试指令队列能否正确对各种指令解码。
	 */
	public static void main(String[] args) {
		InstructionQueue iq = new InstructionQueue();
		Instr is;
		
		is = iq.decodeInstr("SUB F6, 34(F4)  ");
		if (is != null)
			System.out.println(is.toString());
		
		is = iq.decodeInstr("  SUB F6, F5,  F4, GET");
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
}

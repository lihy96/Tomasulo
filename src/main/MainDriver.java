package main;

import kernel.Adder;
import kernel.FP;
import kernel.InstructionQueue;
import kernel.Memory;
import kernel.Multiplier;
import kernel.ReserveStackEntry;

public class MainDriver {
	public static Adder adder;
	public static Multiplier multiplier;
	public static FP fp;
	public static InstructionQueue queue;
	public static Memory mem;
	public static ReserveStackEntry rsAdd[], rsMult[], rsStore[], rsLoad[];
	public MainDriver() {
		adder = new Adder();
		multiplier = new Multiplier();
		fp = FP.getInstance();
		queue = new InstructionQueue();
		mem = new Memory();
		rsAdd = new ReserveStackEntry[3];
		rsMult = new ReserveStackEntry[2];
		rsStore = new ReserveStackEntry[3];
		rsLoad = new ReserveStackEntry[3];
	}
	public static void main(String[] args) {
		
	}
}

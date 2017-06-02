package main;

import kernel.Adder;
import kernel.FP;
import kernel.InstructionQueue;
import kernel.Memory;
import kernel.Multiplier;

public class MainDriver {
	public static Adder adder;
	public static Multiplier multiplier;
	public static FP fp;
	public static InstructionQueue queue;
	public static Memory mem;
	public MainDriver() {
		adder = new Adder();
		multiplier = new Multiplier();
		fp = FP.getInstance();
//		fp = new FloatPointRegister();
		queue = new InstructionQueue();
		mem = new Memory();
	}
	public static void main(String[] args) {
		
	}
}

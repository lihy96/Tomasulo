package main;

import kernel.Adder;
import kernel.FP;
import kernel.InstructionQueue;
import kernel.Multiplier;

public class MainDriver {
	public static Adder adder;
	public static Multiplier multiplier;
	public static FP fp;
	public static InstructionQueue queue;
	
	public MainDriver() {
		adder = new Adder(this);
		multiplier = new Multiplier(this);
//		fp = new FloatPointRegister(this);
		fp = FP.getInstance(this);
		queue = new InstructionQueue(this);
	}
	public static void main(String[] args) {
		
	}
}

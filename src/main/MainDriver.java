package main;

import jdk.internal.org.objectweb.asm.tree.IntInsnNode;
import kernel.Adder;
import kernel.FP;
import kernel.InstructionQueue;
import kernel.Memory;
import kernel.Multiplier;
import kernel.ReserveStackEntry;
import util.ConstDefinition;

public class MainDriver {
	public static Adder adder;
	public static Multiplier multiplier;
	public static FP fp;
	public static InstructionQueue queue;
	public static Memory mem;
	// 保留站组
	public static ReserveStackEntry[] addGroup, mulGroup, loadGroup, storeGroup;
	public MainDriver() {
		adder = new Adder();
		multiplier = new Multiplier();
		fp = FP.getInstance();
		queue = new InstructionQueue();
		mem = new Memory();
		addGroup = ReserveStackEntry.initGroup(ConstDefinition.ADD_RESERVE_ENTRY_NUM);
		mulGroup = ReserveStackEntry.initGroup(ConstDefinition.MUL_RESERVE_ENTRY_NUM);
		loadGroup = ReserveStackEntry.initGroup(ConstDefinition.LOAD_RESERVE_ENTRY_NUM);
		storeGroup = ReserveStackEntry.initGroup(ConstDefinition.STORE_RESERVE_ENTRY_NUM);
	}
	public static void main(String[] args) {
		
	}
}

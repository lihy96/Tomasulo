package main;

import java.util.ArrayList;

import kernel.Adder;
import kernel.Loader;
import kernel.FP;
import kernel.InstructionQueue;
import kernel.FakeMemory;
import kernel.Multiplier;
import kernel.ReserveStackEntry;
import kernel.Storer;
import kernel.FP.REG;
import util.ConstDefinition;

public class Clock {
	public static Adder adder;
	public static Multiplier multiplier;
	public static Loader loader;
	public static Storer storer;
	public static FP fp;
	public static InstructionQueue queue;
	public static FakeMemory mem;
	// 保留站组
	public static ReserveStackEntry[] addGroup, mulGroup, loadGroup, storeGroup;
	public static Double CDB_DATA;
	public static void sim_init() {
		adder = new Adder();
		multiplier = new Multiplier();
		loader = new Loader();
		storer = new Storer();
		fp = FP.getInstance();
		queue = new InstructionQueue();
		mem = new FakeMemory();
		addGroup = ReserveStackEntry.initGroup(ConstDefinition.ADD_RESERVE_ENTRY_NUM);
		mulGroup = ReserveStackEntry.initGroup(ConstDefinition.MUL_RESERVE_ENTRY_NUM);
		loadGroup = ReserveStackEntry.initGroup(ConstDefinition.LOAD_RESERVE_ENTRY_NUM);
		storeGroup = ReserveStackEntry.initGroup(ConstDefinition.STORE_RESERVE_ENTRY_NUM);
	}
	
	public static void wake_up(ReserveStackEntry rse, Double ans) {
		/* 将保留站计算的结果放入总线 */
		CDB_DATA = ans;
		/* 唤醒其他等待计算结果的保留站更新数据 */
		ReserveStackEntry.listen(addGroup, rse);
		ReserveStackEntry.listen(mulGroup, rse);
		ReserveStackEntry.listen(loadGroup, rse);
		ReserveStackEntry.listen(storeGroup, rse);
		/* 唤醒等待被写入的寄存器更新数据 */
		FP.listen(fp, rse);
		/* 清空总线数据 */
		CDB_DATA = null;
	}
	
	public static void print_reserver_state() {
//		ReserveStackEntry.print(addGroup);
//		ReserveStackEntry.print(mulGroup);
		ReserveStackEntry.print(loadGroup);
		ReserveStackEntry.print(storeGroup);
	}
	
	public static void print_fp_state() {
		FP.print(fp);
	}
	
	public static ArrayList<ArrayList<String>> update_instr_queue() {
		return queue.get_instr_queue();
	}
	
	private static boolean flag = false;
	private static int clock = 0;
	public static void run() {
		flag = true;
		while (flag) {
			run_one_step();
		}
	}
	public static void run_one_step() {
		queue.activate();
		adder.activate();
		multiplier.activate();
		loader.activate();
		storer.activate();
	}
	public static void stop() {
		flag = false;
	}
	
	public static int get_clock() {
		return clock;
	}
	
	public static void main(String[] args) {
		Clock.sim_init();
		
		fp.set(REG.F2, 6.2);
		fp.set(REG.F5, 3.0);
		fp.set(REG.F1, 7.0);
		fp.set(REG.F3, 2.5);
		fp.set(REG.F7, 4.8);
		fp.set(REG.F6, 7.6);
		fp.set(REG.F8, 1.1);
		fp.set(REG.F9, 1.3);
		ArrayList<String> instrs = new ArrayList<String>();
		instrs.add("ADD F1, F2, F6");
		instrs.add("SUB F2, F4, F3");
		instrs.add("LOAD F4, 3");
		instrs.add("LOAD F2, 0");
		instrs.add("LOAD F5, 2");
		instrs.add("LOAD F5, 0");
		instrs.add("ADD F8, F9, F2");
		instrs.add("MUL F4, F8, F7");
		instrs.add("STOR F3, 1");
		instrs.add("LOAD F7, 1");
		
		queue.load(instrs);
		int cycle = 25;
		while (cycle-- > 0) {
			System.out.println("clock : " + cycle);
			queue.activate();
			print_reserver_state();
			adder.activate();
			multiplier.activate();
			loader.activate();
			storer.activate();
//			print_fp_state();
		}
		
	}
}

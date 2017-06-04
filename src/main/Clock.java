package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import gui.DataLoader;
import gui.UserWindow;
import kernel.Adder;
import kernel.Loader;
import kernel.FP;
import kernel.InstructionQueue;
import kernel.FakeMemory;
import kernel.Multiplier;
import kernel.ReserveStackEntry;
import kernel.Storer;
import kernel.FP.REG;
import sun.security.action.GetBooleanAction;
import util.ConstDefinition;
import util.Instr;

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
	public static ArrayList<Instr> running_state = new ArrayList<Instr>();
	public static void sim_init() {
		adder = new Adder();
		multiplier = new Multiplier();
		loader = new Loader();
		storer = new Storer();
		fp = FP.getInstance();
		queue = new InstructionQueue();
		mem = new FakeMemory();
		addGroup = ReserveStackEntry.initGroup(ConstDefinition.ADD_RESERVE_ENTRY_NUM, "Adder");
		mulGroup = ReserveStackEntry.initGroup(ConstDefinition.MUL_RESERVE_ENTRY_NUM, "Multiplier");
		loadGroup = ReserveStackEntry.initGroup(ConstDefinition.LOAD_RESERVE_ENTRY_NUM, "Loader");
		storeGroup = ReserveStackEntry.initGroup(ConstDefinition.STORE_RESERVE_ENTRY_NUM, "Storer");
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
		ReserveStackEntry.print(addGroup);
		ReserveStackEntry.print(mulGroup);
		ReserveStackEntry.print(loadGroup);
		ReserveStackEntry.print(storeGroup);
	}
	public static void print_fp_state() {
		FP.print(fp);
	}
	
	public static ArrayList<ArrayList<String>> get_running_state() {
		ArrayList<ArrayList<String>> states = new ArrayList<ArrayList<String>>();
		Iterator<Instr> it = running_state.iterator();
		while (it.hasNext()) {
			Instr itr = it.next();
			if (itr == null) continue;
			
			ArrayList<String> info = new ArrayList<String>();
			info.add(itr.toString());
			info.add("" + itr.state.flow);
			info.add("" + itr.state.running);
			info.add("" + itr.state.write_back);
			states.add(info);
			
			if (itr.state.mark) it.remove();
		}
		return states;
	}
	public static ArrayList<ArrayList<String>> get_instr_queue() {
		return queue.get_instr_queue();
	}
	public static ArrayList<ArrayList<String>> get_fake_memory(int begin) {
		return mem.get(begin, begin + 5);
	}
	public static ArrayList<ArrayList<String>> get_fp() {
		return fp.get_fp();
	}
	public static ArrayList<ArrayList<String>> get_reserve_station() {
		ArrayList<ArrayList<String>> reserve_station = new ArrayList<ArrayList<String>>();
		reserve_station.addAll(ReserveStackEntry.get_reserved_entrys(addGroup, adder.getTime()));
		reserve_station.addAll(ReserveStackEntry.get_reserved_entrys(mulGroup, multiplier.getTime()));
		reserve_station.addAll(ReserveStackEntry.get_reserved_entrys(loadGroup, loader.getTime()));
		reserve_station.addAll(ReserveStackEntry.get_reserved_entrys(storeGroup, storer.getTime()));
		return reserve_station;
	}
	
	private static boolean flag = true;
	private static int clock = 0;
	private static int clock_max = 1000;
	private static long timeout = 0;
	
	public static int get_clock_max() {
		return clock_max;
	}
	
	
	public static long get_timeout() {
		return timeout;
	}
	public static void run() {
		while (flag && clock < clock_max) {
			clock ++;
			run_one_step();
			try {
				TimeUnit.MILLISECONDS.sleep(timeout);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		clock = 0;
	}
	public static void setTimeOut(long _timeout) {
		if (_timeout <= 0) return ;
		timeout = _timeout;
	}
	public static void setMaxCycle(int max) {
		if (max <= 0) return ;
		clock_max = max;
	}
	public static void run_one_step() {
		queue.activate();
		adder.activate();
		multiplier.activate();
		loader.activate();
		storer.activate();
		DataLoader.update_all(MainDriver.window.addr_mem);
	}
	public static void stop() {
		flag = !flag;
	}
	public static void clear() {
		ReserveStackEntry.clear(addGroup);
		ReserveStackEntry.clear(mulGroup);
		ReserveStackEntry.clear(loadGroup);
		ReserveStackEntry.clear(storeGroup);
		queue.clear();
		adder.clear();
		multiplier.clear();
		loader.clear();
		storer.clear();
		FP.clear(fp);
		mem.clear();
		running_state.clear();
		clock = 0;
		clock_max = 1000;
		timeout = 0;
		flag = true;
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

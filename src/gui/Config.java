package gui;

public class Config {
	public final static String FILE = "file";
	public final static String INPUT = "input";
	public final static String ASSIGN = "assign";
	public final static String RUN = "run";
	public final static String MODE = "mode";
	public final static String HELP = "help";
	
	public final static Object[] instr_queue_name = {"Name", "Dst_i", "Src_j", "Src_k"};
	public final static Object[] run_state_name = {"指令","流出", "执行", "写结果"};
	public final static Object[] load_queue_name = {"Busy", "Address", "Cache"};
	public final static Object[] store_queue_name = {"Busy", "Address", "Qi"};
	public final static Object[] reserv_sta_name = {"Time", "Name", "Busy", "Op", "Vi", "Vk", "Qi", "Qk"};
	public final static Object[] fu_name = {"F0","F1","F2","F3","F4","F5","F6","F7","F8","F9","F10"};
	public final static Object[] ru_name = {"R0","R1","R2","R3","R4","R5","R6","R7","R8","R9","R10"};
	
	public static float[] columnWidthPercentage = {55.0f,15.0f,15.0f,15.0f};
	public final static String[] cmds = {"ADD", "SUB", "MUL", "DIV", "LOAD", "STOR"};
}

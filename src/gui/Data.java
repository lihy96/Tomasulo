package gui;

import java.util.ArrayList;

public class Data {
	ArrayList<ArrayList<String>> instr_queue;
	ArrayList<ArrayList<String>> run_state;
	ArrayList<ArrayList<String>> load_queue;
	ArrayList<ArrayList<String>> store_queue;
	ArrayList<ArrayList<String>> mem;
	ArrayList<ArrayList<String>> reserv_station;
	ArrayList<ArrayList<String>> fu;
	ArrayList<ArrayList<String>> ru;
	int clock;
	
	public Data() {
		instr_queue = new ArrayList<ArrayList<String>>();
		run_state = new ArrayList<ArrayList<String>>();
		load_queue = new ArrayList<ArrayList<String>>();
		store_queue = new ArrayList<ArrayList<String>>();
		mem = new ArrayList<ArrayList<String>>();
		reserv_station = new ArrayList<ArrayList<String>>();
		fu = new ArrayList<ArrayList<String>>();
		ru = new ArrayList<ArrayList<String>>();
		clock = 0;
	}
}

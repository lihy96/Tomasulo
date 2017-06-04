package main;

import java.util.ArrayList;

import gui.DataLoader;
import gui.UserWindow;
import gui.DataLoader.DataType;

public class MainDriver {
	public static UserWindow window;
	public static DataLoader dataLoader;

	public static void main(String[] args) {
		Clock.sim_init();
		
		window = new UserWindow();
		window.frmSimulator.setVisible(true);
		dataLoader = new DataLoader(window);
		init_data();
	}
	
	private static void init_data() {
//		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
//		for (int i = 0; i < 2; i++) {
//			ArrayList<String> tmp = new ArrayList<String>();
//			for (int j = 0; j < 5; j++) {
//				tmp.add(""+i+j);
//			}
//			table.add(tmp);
//		}
		dataLoader.update_by_data(DataType.MEM, Clock.get_fake_memory(0));
		dataLoader.update_by_data(DataType.FU, Clock.get_fp());
		dataLoader.update_by_data(DataType.RESERV_STARION, Clock.get_reserve_station());
	}
}

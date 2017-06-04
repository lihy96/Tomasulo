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
//		DataLoader.update_table_mem(0);
//		DataLoader.update_table_fu();
//		DataLoader.update_table_reserv();
		DataLoader.update_all(0);
	}
}

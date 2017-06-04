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
		DataLoader.update_all(0);
		DataLoader.clear_console();
	}
}

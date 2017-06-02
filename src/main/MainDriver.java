package main;

import gui.DataLoader;
import gui.UserWindow;

public class MainDriver {
	public static UserWindow window;
	public static DataLoader dataLoader;

	public static void main(String[] args) {
		Clock.sim_init();

		window = new UserWindow();
		window.frmSimulator.setVisible(true);
		dataLoader = new DataLoader(window);
	}
}

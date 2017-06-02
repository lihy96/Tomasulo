package main;

import gui.DataLoader;
import gui.UserWindow;

public class MainDriver {

	public static void main(String[] args) {
		Clock.sim_init();

		UserWindow window = new UserWindow();
		window.frmSimulator.setVisible(true);
		DataLoader dataLoader = new DataLoader(window);
	}
}

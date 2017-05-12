package kernel;

import main.MainDriver;
import util.ConstDefinition;

public class Memory {
	private double m[] = new double[ConstDefinition.MEM_NUM];
	private MainDriver main;
	public Memory(MainDriver main) {
		this.main = main;
	}
	public double get(int k) {
		return m[k];
	}
	public void set(int k, double value) {
		m[k] = value;
	}
}

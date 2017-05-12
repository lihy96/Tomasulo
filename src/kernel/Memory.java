package kernel;

import util.ConstDefinition;

public class Memory {
	private double m[] = new double[ConstDefinition.MEM_NUM];
	public Memory() {}
	public double get(int k) {
		return m[k];
	}
	public void set(int k, double value) {
		m[k] = value;
	}
}

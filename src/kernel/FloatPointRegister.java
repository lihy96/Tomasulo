package kernel;

import main.MainDriver;
import util.ConstDefinition;


public class FloatPointRegister {
	private double FP[] = new double[ConstDefinition.FP_NUM];
	private MainDriver main;
	public FloatPointRegister(MainDriver main) {
		this.main = main;
	}
	public double get(int k) {
		return FP[k];
	}
	public void set(int k, double value) {
		FP[k] = value;
	}
}

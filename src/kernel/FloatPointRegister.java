package kernel;

import util.ConstDefinition;


public class FloatPointRegister {
	private double FP[] = new double[ConstDefinition.FP_NUM];
	public FloatPointRegister() {}
	public double get(int k) {
		return FP[k];
	}
	public void set(int k, double value) {
		FP[k] = value;
	}
}

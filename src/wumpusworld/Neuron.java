package wumpusworld;

import java.util.ArrayList;

public class Neuron {
	private ArrayList<Float> w;
	
	private float sigmoid(float x) {	
		return (float) (1/((1 + Math.pow(Math.E, (-1*x)))));
	}
	
	public float process(ArrayList<Float> input) {
		float sum = w.get(0);
		for(int i = 1; i < w.size(); i++) {
			sum += w.get(i) * input.get(i-1);
		}
		return sigmoid(sum);
	}
}
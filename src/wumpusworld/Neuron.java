package wumpusworld;

import java.util.ArrayList;

public class Neuron {
	private ArrayList<Float> w;
	
	public Neuron() {
		w = new ArrayList<Float>();
	}
	
	public Neuron(int childs) {
		w = new ArrayList<Float>();
		for(int i = 0; i < childs; i++) {
			w.add((float)(Math.random() * 2) - 1);
		}
	}
	
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
package wumpusworld;

import java.io.Serializable;
import java.util.ArrayList;

public class Neuron implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Float> w;
	
	private float newWeight()
	{
		//return (float)(Math.random() * 2) - 1;
		return ((float)(Math.floor(Math.random()*3) - 1));
	}
	
	private float sigmoid(float x) {	
		return (float) (1/((1 + Math.pow(Math.E, (-1*x)))));
	}
	
	private float binVal(float x)
	{
		return ((x>1) ? 1.0f : 0.0f);
	}
	
	public Neuron() {
		w = new ArrayList<Float>();
	}
	
	public Neuron(int childs) {
		w = new ArrayList<Float>();
		for(int i = 0; i < childs+1; i++) {
			w.add(newWeight());
		}
	}
	
	
	
	public float process(ArrayList<Float> input) {
		float sum = w.get(0);
		for(int i = 1; i < w.size(); i++) {
			sum += w.get(i) * input.get(i-1);
		}
		return binVal(sum);
	}
	
	public Neuron breed(Neuron other, double mutationRate) {	
		Neuron child = new Neuron();
		for(int i = 0; i < w.size(); i++) {
			if(mutationRate > Math.random()) {
				child.w.add(newWeight());//Random Mutation
				//System.out.println(i);
			}
			else {
				if(Math.random() < 0.5) {
					child.w.add(w.get(i));//Inherent this w value
				}
				else {
					child.w.add(other.w.get(i));//Inherent other w value
				}
			}
		}		
		return child;
	}
}
package wumpusworld;

import java.util.ArrayList;
public class NeuralNetwork {
	private ArrayList<Neuron> inputLayer;
	private ArrayList<Neuron> outputLayer;
	
	public NeuralNetwork() 
	{
		inputLayer = new ArrayList<Neuron>();
		for(int i = 0; i < 16; i++) {
			Neuron neuron = new Neuron(16 + 16 + 16 + 4);
			inputLayer.add(neuron);
		}
		
		outputLayer = new ArrayList<Neuron>();
		for(int i = 0; i < 4; i++) {
			Neuron neuron = new Neuron(16);
			inputLayer.add(neuron);
		}
	}
	
	public int process(ArrayList<Float> input) {
		ArrayList<Float> firstResult = new ArrayList<Float>();
		for(int i = 0; i < inputLayer.size(); i++) {
			firstResult.add(inputLayer.get(i).process(input));
		}
		ArrayList<Float> finalResult = new ArrayList<Float>();
		for(int i = 0; i < outputLayer.size(); i++) {
			finalResult.add(outputLayer.get(i).process(firstResult));
		}
		//Get the best move
		float max = finalResult.get(0);
		int index = 0;
		for(int i = 1; i < finalResult.size(); i++) {
			if(finalResult.get(i) > max) {
				max = finalResult.get(i);
				index = i;
			}
		}
		return index;
	}
}



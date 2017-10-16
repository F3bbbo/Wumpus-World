package wumpusworld;

import java.io.Serializable;
import java.util.ArrayList;
public class NeuralNetwork implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Neuron> inputLayer;
	private ArrayList<Neuron> outputLayer;
	
	public NeuralNetwork() 
	{
		inputLayer = new ArrayList<Neuron>();
		outputLayer = new ArrayList<Neuron>();
	}
	
	public NeuralNetwork(int input, int output) {
		this();//Calling default constructor
		
		for(int i = 0; i < input; i++) {
			Neuron neuron = new Neuron(16 + 16 + 16 + 4);
			inputLayer.add(neuron);
		}
		
		for(int i = 0; i < output; i++) {
			Neuron neuron = new Neuron(16);
			outputLayer.add(neuron);
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
	
	public NeuralNetwork breed(NeuralNetwork other, float mutationRate) {
		NeuralNetwork child = new NeuralNetwork();
		for(int i = 0; i < inputLayer.size(); i++) {//Breed all nodes in inputLayer
			child.inputLayer.add(inputLayer.get(i).breed(other.inputLayer.get(i), mutationRate));
		}
		
		for(int i = 0; i < outputLayer.size(); i++) {//Breed all nodes in outputLayer
			child.outputLayer.add(outputLayer.get(i).breed(other.outputLayer.get(i), mutationRate));
		}
		
		return child;
	}
}



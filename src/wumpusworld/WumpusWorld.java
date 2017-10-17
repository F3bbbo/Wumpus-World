package wumpusworld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
/**
 * Starting class for the Wumpus World program. The program
 * has three options: 1) Run a GUI where the Wumpus World can be
 * solved step by step manually or by an agent, or 2) run
 * a simulation with random worlds over a number of games,
 * or 3) run a simulation over the worlds read from a map file.
 * 
 * @author Johan Hagelbäck
 */
public class WumpusWorld {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        WumpusWorld ww = new WumpusWorld();
    }

    /**
     * Starts the program.
     * 
     */
    public WumpusWorld()
    {
        String option = Config.getOption();
        
        if (option.equalsIgnoreCase("gui"))
        {
        	runTrainerRandom();
            showGUI();
        }
        if (option.equalsIgnoreCase("sim"))
        {
            runSimulator();
        }
        if (option.equalsIgnoreCase("simdb"))
        {
            runSimulatorDB();
        }
    }
    
    /**
     * Starts the program in GUI mode.
     */
    private void showGUI()
    {
        GUI g = new GUI();
    }
    
    /**
     * Starts the program in simulator mode with
     * maps read from a data file.
     */
    private void runSimulatorDB()
    {
        MapReader mr = new MapReader();
        Vector<WorldMap> maps = mr.readMaps();
        
        double totScore = 0;
        for (int i = 0; i < maps.size(); i++)
        {
            World w = maps.get(i).generateWorld();
            totScore += (double)runSimulation(w);
        }
        totScore = totScore / (double)maps.size();
        System.out.println("Average score: " + totScore);
    }
    
    /**
     * Starts the program in simulator mode
     * with random maps.
     */
    private void runSimulator()
    {
        double totScore = 0;
        for (int i = 0; i < 10; i++)
        {
            WorldMap w = MapGenerator.getRandomMap(i);
            totScore += (double)runSimulation(w.generateWorld());
        }
        totScore = totScore / (double)10;
        System.out.println("Average score: " + totScore);
    }
    
    /**
     * Runs the solver agent for the specified Wumpus
     * World.
     * 
     * @param w Wumpus World
     * @return Achieved score
     */
    private int runSimulation(World w)
    {
        
        int actions = 0;
        Agent a = new MyAgent(w, "NeuralNetwork.ser");
        while (!w.gameOver())
        {
            a.doAction();
            actions++;
        }
        int score = w.getScore();
        System.out.println("Simulation ended after " + actions + " actions. Score " + score);
        return score;
    }
    
    private void runTrainer()
    {
        MapReader mr = new MapReader();
        Vector<WorldMap> maps = mr.readMaps();
        int PopulationSize = 20;
        ArrayList<MyAgent> pop = new ArrayList<MyAgent>();
        //Create start population
        for(int i = 0; i < PopulationSize; i++)
        {
        	World w = maps.get(0).generateWorld();
        	pop.add(new MyAgent(w));
        	pop.get(i).setBestScore(runTrainingSim(w, pop.get(i))); 
        }
        //Sort
        Collections.sort(pop, Collections.reverseOrder());
        int gen = 0;
        while(gen < 10)
        {
	        //Breed
	        for(int i = 0;i+1 < PopulationSize; i+=2)
	        {
	        	World w = maps.get(0).generateWorld();
	        	NeuralNetwork nn = pop.get(i).breed(pop.get(i+1), 0.02);
	        	MyAgent a = new MyAgent(w, nn);
	        	int score = runTrainingSim(w, a);
	        	a.setBestScore(score);
	        	pop.add(a);
	        }
	        //Sort
	        Collections.sort(pop, Collections.reverseOrder());
	        //Remove bad population
	        for(int i = pop.size()-1; i > PopulationSize-1; i--)
	        {
	        	pop.remove(i);
	        }
	        gen++;
	        System.out.println("Gen: " + gen + " Score: " + pop.get(0).getBestScore());
        }
        pop.get(0).saveToFile();    
    }
    
    private void runTrainerRandom()
    {
        int PopulationSize = 200;
        ArrayList<MyAgent> pop = new ArrayList<MyAgent>();
        //Create start population
        for(int i = 0; i < PopulationSize; i++)
        {
        
        	World w = MapGenerator.getRandomMap(i).generateWorld();
        	pop.add(new MyAgent(w));
        	pop.get(i).setBestScore(runTrainingSim(w, pop.get(i))); 
        }
        //Sort
        Collections.sort(pop, Collections.reverseOrder());
        int gen = 0;
        while(gen < 10)
        {
	        //Breed
	        for(int i = 0;i+1 < PopulationSize; i+=2)
	        {
	        	NeuralNetwork nn = pop.get(i).breed(pop.get(i+1), 0.02);
	        	int score = 0;
	        	MyAgent a = new MyAgent();
	        	for(int j = 0; j < 5; j++)
	        	{
		        	World ww = MapGenerator.getRandomMap(j).generateWorld();
		        	a = new MyAgent(ww, nn);
		        	score += runTrainingSim(ww, a);
	        	}
	        	a.setBestScore(score/5);
	        	pop.add(a);
	        }
	        //Sort
	        Collections.sort(pop, Collections.reverseOrder());
	        //Remove bad population
	        for(int i = pop.size()-1; i > PopulationSize-1; i--)
	        {
	        	pop.remove(i);
	        }
	        gen++;
	        System.out.println("Gen: " + gen + " Score: " + pop.get(0).getBestScore());
        }
        pop.get(0).saveToFile();    
    }
    
    private int runTrainingSim(World w, MyAgent a)
    {
    	int actions = 0;   	
    	while (!w.gameOver() && actions < 10000)
    	{
    		a.doAction();
    		actions++;
    	}
    	int score = w.getScore();
    	return score;
    }
}

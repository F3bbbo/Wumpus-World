package wumpusworld;
import java.util.ArrayList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Contains starting code for creating your own Wumpus World agent.
 * Currently the agent only make a random decision each turn.
 * 
 * @author Johan Hagelbäck
 */
public class MyAgent implements Agent,Comparable<MyAgent>
{
	private static final int INPUTLAYERSIZE = 32;
	private static final int OUTPUTSIZE = 8;
    private World w;
    private NeuralNetwork brain;
    private int bestScore;
    int rnd;
    WorldModel Wumpusworld; 
    /**
     * Creates a new instance of your solver agent.
     * 
     * @param world Current world state 
     */
    public MyAgent(World world)
    {
        w = world;   
        Wumpusworld= new WorldModel();
        brain = new NeuralNetwork(INPUTLAYERSIZE, OUTPUTSIZE);
        bestScore = -Integer.MAX_VALUE;
    }
    
    public MyAgent()
    {
    	w = null;
    	brain = new NeuralNetwork(INPUTLAYERSIZE, OUTPUTSIZE);;
    	Wumpusworld =  new WorldModel();
    	bestScore = -Integer.MAX_VALUE;
    }
    
    public MyAgent(NeuralNetwork nn)
    {
    	w = null;
    	brain = nn;
    	Wumpusworld = new WorldModel();
    	bestScore = -Integer.MAX_VALUE;
    	
    }
    
    
    public MyAgent(World world, NeuralNetwork network) {
    	w = world;
    	brain = network;
        Wumpusworld= new WorldModel();
    	bestScore = -Integer.MAX_VALUE;
    }
    
    public MyAgent(World world, String fileName) {
    	w = world;
    	readFromFile(fileName);
        Wumpusworld = new WorldModel();
    	bestScore = -Integer.MAX_VALUE;
    }
    
    public MyAgent(String fileName) {
    	w = null;
    	readFromFile(fileName);
        Wumpusworld = new WorldModel();
    	bestScore = -Integer.MAX_VALUE;
    }
    
    
    
    public NeuralNetwork breed(MyAgent other, double mutationRate) {
    	return brain.breed(other.brain, mutationRate);
    }
   
            
    /**
     * Asks your solver agent to execute an action.
     */

    public void doAction()
    {
        //Location of the player
        int cX = w.getPlayerX();
        int cY = w.getPlayerY();
        if (w.hasGlitter(cX, cY))
        {
            w.doAction(World.A_GRAB);
            return;
        }
        
        //Basic action:
        //We are in a pit. Climb up.
        if (w.isInPit())
        {
            w.doAction(World.A_CLIMB);
            return;
        }
        Wumpusworld.UpdateWorldmap(w,cX,cY);
        
         rnd=brain.process(Wumpusworld.returnworldmap(w));
        //Basic action:
        //Grab Gold if we can.
         if (rnd==0)
        {
            w.doAction(World.A_TURN_LEFT);
            w.doAction(World.A_MOVE);
        }
        
        if (rnd==1)
        {
            w.doAction(World.A_MOVE);
        }
                
        if (rnd==2)
        {
            w.doAction(World.A_TURN_LEFT);
            w.doAction(World.A_TURN_LEFT);
            w.doAction(World.A_MOVE);
        	//w.doAction(World.A_SHOOT);
        }
                        
        if (rnd==3)
        {
            w.doAction(World.A_TURN_RIGHT);
            w.doAction(World.A_MOVE);
        }
        
        if(rnd==4)
        {
        	w.doAction(World.A_TURN_LEFT);
        	w.doAction(World.A_SHOOT);
        }
        
        if(rnd==5)
        {
        	w.doAction(World.A_SHOOT);
        }
        
        if(rnd==6)
        {
        	w.doAction(World.A_TURN_LEFT);
            w.doAction(World.A_TURN_LEFT);
        	w.doAction(World.A_SHOOT);
        }
        
        if(rnd==7)
        {
        	w.doAction(World.A_TURN_RIGHT);
        	w.doAction(World.A_SHOOT);
        }
                
        
               
    }    
    
     /**
     * Genertes a random instruction for the Agent.
     */
    public int decideRandomMove()
    {
      return (int)(Math.random() * 4);
    }
    
    public void saveToFile() {
    	saveToFile("NeuralNetwork.ser");
    }
    
    public void saveToFile(String fileName) {
    	try {
    	//Write object to file
    	FileOutputStream fout = new FileOutputStream(fileName, false);
    	ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(brain);
		oos.close();
	

		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void readFromFile(String fileName)
    {
    	try {
    		//Read object from file
    		FileInputStream fis = new FileInputStream(fileName);
    		ObjectInputStream ois = new ObjectInputStream(fis);
    		brain = (NeuralNetwork) ois.readObject();
    		ois.close();
    		
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		} catch (ClassNotFoundException e) {
    			e.printStackTrace();
    		}
    }
    
    public void setBestScore(int score)
    {
    	bestScore = score;
    }
    
    public int getBestScore()
    {
    	return bestScore;
    }
    
    public void reset()
    {
    	Wumpusworld = new WorldModel();
    }
    
    public void setWorld(World world)
    {
    	reset();
    	w = world;
    }
    
	@Override
	public int compareTo(MyAgent other) {
		// TODO Auto-generated method stub
		return bestScore-other.bestScore;
	}
    
    
}


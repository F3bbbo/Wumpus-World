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
public class MyAgent implements Agent
{
    private World w;
    private NeuralNetwork brain;
    int rnd;
    EasyWumpusWorldForNeuralNetwork Wumpusworld; 
    /**
     * Creates a new instance of your solver agent.
     * 
     * @param world Current world state 
     */
    public MyAgent(World world)
    {
        w = world;   
         Wumpusworld= new EasyWumpusWorldForNeuralNetwork();
        
        w = world;
        brain = new NeuralNetwork(16, 4);
    }
    
    public MyAgent(World world, NeuralNetwork network) {
    	w = world;
    	brain = network;
    }
    
    public NeuralNetwork breed(MyAgent other, float mutationRate) {
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
         Wumpusworld.returnworldmap(w);
        //Basic action:
        //Grab Gold if we can.
    
        
               
    }    
    
     /**
     * Genertes a random instruction for the Agent.
     */
    public int decideRandomMove()
    {
      return (int)(Math.random() * 4);
    }
    
    public void saveToFile() {
    	try {
    	//Write object to file
    	NeuralNetwork brain = new NeuralNetwork(16, 4);
    	FileOutputStream fout = new FileOutputStream("NeuralNetwork.ser", true);
    	ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(brain);
		oos.close();
		
		//Read object from file
		FileInputStream fis = new FileInputStream("NeuralNetwork.ser");
		ObjectInputStream ois = new ObjectInputStream(fis);
		NeuralNetwork brainClone = (NeuralNetwork) ois.readObject();
		ois.close();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
}


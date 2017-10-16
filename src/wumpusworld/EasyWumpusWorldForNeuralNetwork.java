/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

package wumpusworld;
import java.util.ArrayList;
/**
 *
 * @author Dean
 */
public class EasyWumpusWorldForNeuralNetwork {
     private ArrayList<Integer> Worldmap;
   
     
     public EasyWumpusWorldForNeuralNetwork()
     {
         int test = 0;
         Worldmap = new ArrayList<Integer>();
         for(int i = 0;i<16+16+16+16+4;i++)
         Worldmap.add(0);
         //0-15 = knownplaces 16-31 bresses 32-47 stenches 48+16 Position 63+4 riktning
         
     }
    public void UpdateWorldmap(World w,int cX, int cY)
    {
 
        int arrayvalue=0;
        int arrayvaluex = cX -1;
        if(cY>1)
        {
            if(cY==2)
            {
                arrayvalue =4;
            }
            if(cY==3)
            {
                arrayvalue =8;
            }
            if(cY==4)
            {
                arrayvalue =12;
            }
            
        }
         
        //Test the environment
        
          //setsposition
        for(int i = 0;i<16;i++)
        {
           Worldmap.set(47+1+i, 0); 
        }
     
           Worldmap.set(47+arrayvalue+arrayvaluex+1, 1);
        
           
             for(int i = 0;i<4;i++)
        {
           Worldmap.set(63+i+1, 0); 
        }
        if (w.getDirection() == World.DIR_RIGHT)
        {
           Worldmap.set(63+1, 1);
        }
        if (w.getDirection() == World.DIR_LEFT)
        {
           Worldmap.set(63+2,1);
        }
        if (w.getDirection() == World.DIR_UP)
        {
            Worldmap.set(63+3, 1);
        }
        if (w.getDirection() == World.DIR_DOWN)
        {
           Worldmap.set(63+4, 1);
        }
        
        
        if(Worldmap.get(arrayvalue+arrayvaluex) ==1)
        {
            return;
        }
        else
        {
            Worldmap.set(arrayvalue+arrayvaluex,1);
        }
        
        
        if (w.hasBreeze(cX, cY))
        {
            Worldmap.set(15+arrayvalue+arrayvaluex+1, 1);
        }
        if (w.hasStench(cX, cY))
        {
          Worldmap.set(31+arrayvalue+arrayvaluex+1, 1);
        }
   
        
        
    }
 public ArrayList<Integer> returnworldmap(World w)
    {
       
        return Worldmap;
    }
    
     public ArrayList<Integer> printreturnworldmap(World w)
    {
         for(int i = 0;i<4;i++)
         {
             for(int j = 0;j<16;j++)
             {
                 System.out.print(Worldmap.get(j+(16*i)));
                 System.out.print(" ");
             }
             System.out.println();
         }
                 System.out.print(Worldmap.get(63+1));
                 System.out.print(Worldmap.get(63+2));
                 System.out.print(Worldmap.get(63+3));
                 System.out.print(Worldmap.get(63+4));
          System.out.println();
       // System.out.print(Worldmap.get(j+(16*3)));
        
        
        return Worldmap;
    }
            

    


}

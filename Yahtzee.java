/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yahtzee;

/**
 *
 * @author ACER
 */
public class Yahtzee {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) 
    {
        double[] dice = new double[5];
        dice[0] = 2;
        dice[1] = 2;
        dice[2] = 2;
        dice[3] = 4;
        dice[4] = 6;
        Hand hand = new Hand(dice, 2);
        boolean[] demo = hand.pickRerollDice();
        for(int i = 0; i < 5; i++)
        {
            System.out.println("Reroll die " + (i+1) + ": " + demo[i]);
        }
        
    }
    
}

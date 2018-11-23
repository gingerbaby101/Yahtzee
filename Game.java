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
public class Game 
{
    private int score;
    public boolean[] availHands = new boolean[13];
    public Game()
    {
        score = 0;
        for(int i = 0; i < 13; i++)
        {
            availHands[i] = true;
        }
    }
    public void useHand(int n)
    {
        availHands[n] = false;
    }
    public double[] toDoubleArray(boolean[] hands)
    {
        double[] dHands = new double[hands.length];
        for(int i = 0; i < hands.length; i ++)
        {
            if (hands[i] == true)
                dHands[i] = 1;
            else
                dHands[i] = 0;
        }
        return dHands;
    }
    public boolean[] toBooleanArray(double[] reroll)
    {
        boolean[] bReroll = new boolean[5];
        for(int i = 0; i < 5; i++)
        {
            if((int)(reroll[i] + 0.5) == 1)
                bReroll[i] = true;
            if((int)(reroll[i] + 0.5) == 0)
                bReroll[i] = false;
        }
        return bReroll;
    }
    public double sigmoid(double x)
    {
        return (1.0/(1 + Math.exp(-x)));
    }
    public int pickHand(Hand hand)
    {
        double[] diceCopy = hand.dice.clone();
        double[] dHands = toDoubleArray(availHands).clone();
        int size = diceCopy.length + dHands.length;
        double[][] a = new double[4][size];
        double[][][] w = new double[4][size][size];
        double[][] b = new double[4][size];
        for(int i = 0; i < dHands.length; i++)
        {
            a[0][i] = sigmoid(dHands[i]);
        }
        for(int i = dHands.length; i < diceCopy.length + dHands.length; i++)
        {
            a[0][i] = sigmoid(diceCopy[i - dHands.length]);
        }
        // activates first layer of neurons to input conditions
        
        for(int i = 1; i < 4; i++)
        {
            for(int j = 0; j < size; j++)
            {
                for(int k = 0; k < size; k++)
                {
                    w[i][j][k] = Math.random();
                }
            b[i][j] = Math.random();                
            }
        }
        
        double rowSum = 0;
        for(int lay = 1; lay < 3; lay++)
        {
            for(int r = 0; r < size; r++)
            {
                for(int c = 0; c < size; c++)
                {
                    rowSum += w[lay][r][c]*a[lay-1][c];
                }
                a[lay][r] = sigmoid(rowSum + b[lay][r]);
                rowSum = 0;
            }
        }
        for(int r = 0; r < dHands.length; r++)
        {
            for(int c = 0; c < size; c++)
            {
                rowSum += w[3][r][c]*a[2][c];
            }
            a[3][r] = sigmoid(rowSum + b[3][r]);
            rowSum = 0;
        }
        double[] output = new double[5];
        System.out.print("(");
        for(int i = 0; i < 5; i++)
        {
            output[i] = a[3][i];
            System.out.print(output[i]);
            if(i != 4)
                System.out.print(", ");
        }
        System.out.print(")");
        boolean[] bReroll = toBooleanArray(output);
        return bReroll;
    }
    
}

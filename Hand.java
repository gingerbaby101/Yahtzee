/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yahtzee;
import java.util.Arrays; 
/**
 *
 * @author ACER
 */
public class Hand extends Game 
{
    public double[] dice = new double[5];
    private int rollNum;
    public Hand(double[] dvs, int r)
    {
        dice = dvs.clone();
        rollNum = r;
    }
    public void random()
    {
        for(int i = 0; i < 5; i++)
        {
            dice[i] = (int)(Math.random()*6 + 1);
        }
    }
    public void reroll(boolean[] rerolls)
    {
        for(int i = 0; i < 5; i++)
        {
            if(rerolls[i])
            {
                dice[i] = (int)(Math.random()*6 + 1);
            }
        }
    }
    public double[] getDice()
    {
        return dice.clone();
    }
    public int getRollNum()
    {
        return rollNum;
    }
    public int getOnesValue()
    {
        int sum = 0;
        if(availHands[0])
        {
            for(int i = 0; i < 5; i++)
            {
                if(dice[i] == 1)
                    sum += 1;
            }
        }
        return sum;
    }
    public int getTwosValue()
    {
        int sum = 0;
        if(availHands[1])
        {
            for(int i = 0; i < 5; i++)
            {
                if(dice[i] == 2)
                    sum += 2;
            }
        }
        return sum;
    }
    public int getThreesValue()
    {
        int sum = 0;
        if(availHands[2])
        {
            for(int i = 0; i < 5; i++)
            {
                if(dice[i] == 3)
                    sum += 3;
            }
        }
        return sum;
    }
    public int getFoursValue()
    {
        int sum = 0;
        if(availHands[3])
        {
            for(int i = 0; i < 5; i++)
            {
                if(dice[i] == 4)
                    sum += 4;
            }
        }
        return sum;
    }
    public int getFivesValue()
    {
        int sum = 0;
        if(availHands[4])
        {
            for(int i = 0; i < 5; i++)
            {
                if(dice[i] == 5)
                    sum += 5;
            }
        }
        return sum;
    }
    public int getSixesValue()
    {
        int sum = 0;
        if(availHands[5])
        {
            for(int i = 0; i < 5; i++)
            {
                if(dice[i] == 6)
                    sum += 6;
            }
        }
        return sum;
    }
    public int getTOAKValue()
    {
        if(availHands[6])
        {
        double[] copy = dice.clone();
        Arrays.sort(copy);
        for(int i = 0; i < 3; i++)
        {
            if(copy[i] == copy[i+1] && copy[i] == copy[i+2])
                return (int)(copy[0] + copy[1] + copy[2] + copy[3] + copy[4]);
        }
        return 0;
        }
        else return 0;
    }
    public int getFOAKValue()
    {
        if(availHands[7])
        {
        double[] copy = dice.clone();
        Arrays.sort(copy);
        for(int i = 0; i < 2; i++)
        {
            if(copy[i] == copy[i+1] && copy[i] == copy[i+2] && copy[i] == copy[i+3])
                return (int)(copy[0] + copy[1] + copy[2] + copy[3] + copy[4]);
        }
        return 0;
        }
        else return 0;
    }
    public int getFHValue()
    {
        if(availHands[8])
        {
        boolean three = false;
        boolean two = false;
        double[] copy = dice.clone();
        Arrays.sort(copy);
        for(int i = 0; i < 3; i++)
        {
            if(copy[i] == copy[i+1] && copy[i] == copy[i+2])
            {
                three = true;
                i = 10;
            }
        }
        if(copy[0] == copy[1] && copy[3] == copy[4])
            two = true;
        if(two && three)
            return 25;
        else
            return 0;
        }
        else return 0;
    }
    public int getSSValue()
    {
        if(availHands[9])
        {
        double[] copy = dice.clone();
        Arrays.sort(copy);
        for(int i = 0; i < 2; i++)
        {
            if(copy[i] < copy[i+1] && copy[i+1] < copy[i+2] && copy[i+2] < copy[i+3])
                return 30;
        }
        return 0;
        }
        else return 0;
    }
    public int getLSValue()
    {
        if(availHands[10])
        {
        double[] copy = dice.clone();
        Arrays.sort(copy);
        if(copy[0] < copy[1] && copy[1] < copy[2] && copy[2] < copy[3] && copy[3] < copy[4])
                return 40;
        return 0;
        }
        else return 0;
    }
    public int getYahtzeeValue()
    {
        if(availHands[11])
        {
        double[] copy = dice.clone();
        Arrays.sort(copy);
        for(int i = 0; i < 4; i++)
        {
            if(copy[i] != copy[i+1])
                return 0;
        }
        return 50;
        }
        else return 0;
    }
    public int getChanceValue()
    {
        if(availHands[12])
        {
        int sum = 0;
        for(int i = 0; i < 5; i++)
        {
            sum += (dice[i]);
        }
        return sum;
        }
        else return 0;
    }
    
    public boolean[] pickRerollDice()
    {
        double[] diceCopy = dice.clone();
        double[] dHands = toDoubleArray(availHands).clone();
        int size = diceCopy.length + dHands.length + 1;
        double[][] a = new double[4][size];
        double[][][] w = new double[4][size][size];
        double[][] b = new double[4][size];
        a[0][0] = sigmoid(rollNum);
        for(int i = 1; i < dHands.length+1; i++)
        {
            a[0][i] = sigmoid(dHands[i-1]);
        }
        for(int i = dHands.length + 1; i < diceCopy.length + dHands.length + 1; i++)
        {
            a[0][i] = sigmoid(diceCopy[i - (dHands.length + 1)]);
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
        for(int r = 0; r < 5; r++)
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

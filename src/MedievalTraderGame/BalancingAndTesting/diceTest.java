package MedievalTraderGame.BalancingAndTesting;

import MedievalTraderGame.Dice;

public class diceTest {
    public static void main(String[] args) {

        for(int i=0;i<30;i++){
            double x =Dice.nextDouble(0.1d,0.9d);
            System.out.println(x);
        }
    }
}

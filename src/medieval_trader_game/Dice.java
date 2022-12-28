package medieval_trader_game;

import java.util.Random;

public class Dice {
    private static final Random dice = new Random();

    public static int nextInt(int min, int max){
        return dice.nextInt( min, max);
    }

    public static int nextInt( int bound ){
        return dice.nextInt( bound );
    }

    public static double nextDouble( double min, double max ){
        return dice.nextDouble(min,max);
    }

    public static boolean nextBoolean(){
        return dice.nextBoolean();
    }

    //some random int, like item stats, are more fun when distributed abnormally
    //op items are harder to find
    public static int nextIntLogDistribution(int min, int max){
        double range = max - min;
        double x = Dice.nextInt(100);
        double rarityFactor = ( ( 1.0d - customLog( 100 - x )) * 0.4d ) + ( 0.006 * x );
        return min + (int)( range * rarityFactor );
    }

    private static double customLog( double logNumber) {
        return Math.log(logNumber) / Math.log(100);
    }
}

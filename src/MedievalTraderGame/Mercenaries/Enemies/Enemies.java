package MedievalTraderGame.Mercenaries.Enemies;

import MedievalTraderGame.Dice;
import MedievalTraderGame.Interface.UiInfo;
import MedievalTraderGame.MaxValues;
import MedievalTraderGame.Mercenaries.Mercenary;
import MedievalTraderGame.Mercenaries.MercenaryTeam;

// Gathers all types of enemies found in game. Provides methods for
// creating random or specific types of enemies. Can create whole random enemy team depending on players team power and size.

public class Enemies {

    public static MercenaryTeam generateRandomEnemies(int maxPower, int playerTeamSize){

        //in case player has no mercenaries, he cant win fight, he's power is 0, but some enemy needs to be created anyway
        //Easter egg, it will be epic monster
        if(maxPower == 0){
            MercenaryTeam team = new MercenaryTeam();
            team.addMercenary( EpicMonster.random() );
            return team;
        }

        int enemyTeamSize = rollEnemyTeamSize( playerTeamSize );
        int enemyMaxPower = rollEnemyPower( maxPower, enemyTeamSize, playerTeamSize );

        if(Dice.nextBoolean()){
            return generateAnimals(enemyMaxPower, enemyTeamSize);
        }
        else {
            return generateBandits(enemyMaxPower, enemyTeamSize);
        }
    }

    public static MercenaryTeam generateAnimals(int power, int enemyTeamSize){
        MercenaryTeam team = new MercenaryTeam();
        int currentEnemyPower = 0;
        int availablePower;

        for(int i=0; i<enemyTeamSize; i++){
            availablePower = power - currentEnemyPower;
            Mercenary animal = createAnimal( availablePower );
            currentEnemyPower += animal.power();
            team.addMercenary( animal );
        }
        return team;
    }

    public static MercenaryTeam generateBandits(int power, int enemyTeamSize){
        MercenaryTeam team = new MercenaryTeam();
        int currentEnemyPower = 0;
        int availablePower;

        for(int i=0; i<enemyTeamSize; i++){
            availablePower = power - currentEnemyPower;
            Mercenary bandit = createBandit( availablePower );
            currentEnemyPower += bandit.power();
            team.addMercenary( bandit );
        }
        return team;
    }

    public static Mercenary create( Bandit bandit){
        return Bandit.create( bandit );
    }

    public static Mercenary create( Animal animal){
        return Animal.create( animal );
    }

    public static Mercenary createBandit( int maxPower ){
        for( Bandit bandit : Bandit.values() ) {
            if ( maxPower > bandit.power ) {
                return Bandit.create( bandit );
            }
        }
        return Bandit.create(Bandit.DRUNK);
    }

    public static Mercenary createAnimal( int maxPower ){
        for(Animal animal : Animal.values()) {
            if (maxPower > animal.power) {
                return Animal.create(animal);
            }
        }
        return Animal.create(Animal.RAT);
    }

    public static Mercenary create( EpicMonster monster ){
        return EpicMonster.create( monster );
    }

    public static UiInfo printEnemiesInfo(){
        UiInfo info = new UiInfo();
        info.viewAdd("*----------------< ANIMALS >----------------*");
        for (Animal animal : Animal.values())
            info.print( create(animal).printEnemyInfo() );
        info.viewAdd(" ");

        info.viewAdd("*----------------< BANDITS >----------------*");
        for (Bandit bandit : Bandit.values())
            info.print( create(bandit).printEnemyInfo() );
        info.viewAdd(" ");

        info.viewAdd("*-------------< EPIC MONSTERS >-------------*");
        for (EpicMonster monster : EpicMonster.values())
            info.print( create(monster).printEnemyInfo() );
        return info;
    }


    private static int rollEnemyPower( int maxPower, int enemyTeamSize, int playerTeamSize){

        int lowerBoundPower = multiply( maxPower, MaxValues.enemyMinPowerFactor );
        int upperBoundPower = multiply( maxPower,MaxValues.enemyMaxPowerFactor );
        int power = Dice.nextInt(lowerBoundPower, upperBoundPower);

        if(enemyTeamSize>playerTeamSize){
            int extras = enemyTeamSize - playerTeamSize;
            for( int i=0; i<extras; i++){
                power = multiply(power,0.7);
            }
        }
        return power;
    }

    private static int rollEnemyTeamSize( int playerTeamSize ){
        return Dice.nextInt( 1, 5 + playerTeamSize);
    }

    private static int multiply(int value, double factor){
        return (int)( (double)value * factor );
    }


}

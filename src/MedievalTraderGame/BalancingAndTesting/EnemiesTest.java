package MedievalTraderGame.BalancingAndTesting;

import MedievalTraderGame.Interface.MainWindow;
import MedievalTraderGame.Interface.UiInfo;
import MedievalTraderGame.Items.Item;
import MedievalTraderGame.Locations.Location;
import MedievalTraderGame.Mercenaries.Enemies.Enemies;
import MedievalTraderGame.Mercenaries.Mercenary;
import MedievalTraderGame.Mercenaries.MercenaryTeam;
import MedievalTraderGame.Mercenaries.Enemies.Encounter;
import MedievalTraderGame.Player.Player;

public class EnemiesTest {

    public static void main(String[] args) {
        MainWindow ui = new MainWindow();
        ui.print(encounterBalanceTest());
    }

    public UiInfo enemyGenerationTest(){
        UiInfo info = new UiInfo();

        info.viewAdd("< 1 >");
        MercenaryTeam team = Enemies.generateRandomEnemies(500,5);
        info.print(team.printTeamInfo());

        info.viewAdd("< 2 >");
        team = Enemies.generateRandomEnemies(2000,3);
        info.print(team.printTeamInfo());

        info.viewAdd("< 3 >");
        team = Enemies.generateRandomEnemies(5000,10);
        info.print(team.printTeamInfo());

        return info;
    }


    public static UiInfo encounterBalanceTest(){

        int win = 0;

        for(int i=0; i < 10000; i++){
            Player player = new Player(new Location("Town"));
            MercenaryTeam enemyTeam = new MercenaryTeam();
            enemyTeam.addMercenary(new Mercenary(50,60,10,200,"b2"));
            Encounter encounter = new Encounter(player,enemyTeam);
            if(encounter.makeFight()){
                win++;
            }
        }
        UiInfo info = new UiInfo();
        info.consoleAdd(" bob wins: " + win);
        return info;
    }


    public void infoTest() {
        MainWindow ui = new MainWindow();
        ui.print( Enemies.printEnemiesInfo() );
    }


    public static UiInfo winChanceTest(){

        Player player;
        Encounter encounter;
        int win = 0;
        int win2 = 0;
        int win3 = 0;
        int iterations = 10000;

        for(int i=0; i<iterations; i++){
            player = new Player(new Location("Town"));
            player.mercenaryTeam.at(0).equip(new Item());


            encounter = new Encounter(player);
            if(encounter.makeFight()){
                win++;
            }
        }

        for(int i=0; i<iterations; i++){
            player = new Player(new Location("Town"));

            player.mercenaryTeam.at(0).equip(new Item());
            player.mercenaryTeam.addMercenary(new Mercenary());
            player.mercenaryTeam.at(1).equip(new Item());
            player.mercenaryTeam.addMercenary(new Mercenary());
            player.mercenaryTeam.at(2).equip(new Item());


            encounter = new Encounter(player);
            if(encounter.makeFight()){
                win2++;
            }
        }

        for(int i=0; i<iterations; i++){
            player = new Player(new Location("Town"));
            player.mercenaryTeam.at(0).equip(new Item());
            player.mercenaryTeam.addMercenary(new Mercenary());
            player.mercenaryTeam.at(1).equip(new Item());
            player.mercenaryTeam.addMercenary(new Mercenary());
            player.mercenaryTeam.at(2).equip(new Item());
            player.mercenaryTeam.addMercenary(new Mercenary());
            player.mercenaryTeam.at(3).equip(new Item());
            player.mercenaryTeam.addMercenary(new Mercenary());
            player.mercenaryTeam.at(4).equip(new Item());
            player.mercenaryTeam.addMercenary(new Mercenary());
            player.mercenaryTeam.at(5).equip(new Item());

            encounter = new Encounter(player);
            if(encounter.makeFight()){
                win3++;
            }
        }

        UiInfo info = new UiInfo();
        info.viewAdd("bob with no item % win " +   ( (double) win * 100.0d ) / (double) iterations);
        info.viewAdd("       bob +1 % win " + ((double) win2 * 100.0d ) / (double) iterations);
        info.viewAdd("     bunch +9 % win " + ((double) win3 * 100.0d ) / (double) iterations);

        return info;
    }
}

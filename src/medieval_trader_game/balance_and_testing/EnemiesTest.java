package medieval_trader_game.balance_and_testing;

import medieval_trader_game.console_interface.MainWindow;
import medieval_trader_game.console_interface.UiInfo;
import medieval_trader_game.items.Item;
import medieval_trader_game.locations.Location;
import medieval_trader_game.mercenaries.Mercenary;
import medieval_trader_game.mercenaries.MercenaryTeam;
import medieval_trader_game.mercenaries.enemies.Encounter;
import medieval_trader_game.player.Player;

public class EnemiesTest {

    public static void main(String[] args) {
        MainWindow ui = new MainWindow();
        ui.print(encounterBalanceTest());
        ui.print(winChanceTest());
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



    public static UiInfo winChanceTest(){

        Player player;
        Encounter encounter;
        int win = 0;
        int win2 = 0;
        int win3 = 0;
        int iterations = 10000;

        for(int i=0; i<iterations; i++){
            player = new Player(new Location("Town"));
            player.mercenaryTeam().at(0).equip(new Item());


            encounter = new Encounter(player);
            if(encounter.makeFight()){
                win++;
            }
        }

        for(int i=0; i<iterations; i++){
            player = new Player(new Location("Town"));

            player.mercenaryTeam().at(0).equip(new Item());
            player.mercenaryTeam().addMercenary(new Mercenary());
            player.mercenaryTeam().at(1).equip(new Item());
            player.mercenaryTeam().addMercenary(new Mercenary());
            player.mercenaryTeam().at(2).equip(new Item());


            encounter = new Encounter(player);
            if(encounter.makeFight()){
                win2++;
            }
        }

        for(int i=0; i<iterations; i++){
            player = new Player(new Location("Town"));
            player.mercenaryTeam().at(0).equip(new Item());
            player.mercenaryTeam().addMercenary(new Mercenary());
            player.mercenaryTeam().at(1).equip(new Item());
            player.mercenaryTeam().addMercenary(new Mercenary());
            player.mercenaryTeam().at(2).equip(new Item());
            player.mercenaryTeam().addMercenary(new Mercenary());
            player.mercenaryTeam().at(3).equip(new Item());
            player.mercenaryTeam().addMercenary(new Mercenary());
            player.mercenaryTeam().at(4).equip(new Item());
            player.mercenaryTeam().addMercenary(new Mercenary());
            player.mercenaryTeam().at(5).equip(new Item());

            encounter = new Encounter(player);
            if(encounter.makeFight()){
                win3++;
            }
        }

        UiInfo info = new UiInfo();
        info.viewAdd("bob with no item % win " + ( win  * 100.0d ) / iterations);
        info.viewAdd("       bob +1 % win "    + ( win2 * 100.0d ) / iterations);
        info.viewAdd("     bunch +9 % win "    + ( win3 * 100.0d ) / iterations);

        return info;
    }
}

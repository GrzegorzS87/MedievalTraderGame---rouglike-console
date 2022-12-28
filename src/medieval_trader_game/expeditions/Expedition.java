package medieval_trader_game.expeditions;

import medieval_trader_game.console_interface.UiInfo;
import medieval_trader_game.items.Item;
import medieval_trader_game.MaxValues;
import medieval_trader_game.mercenaries.enemies.Enemies;
import medieval_trader_game.mercenaries.enemies.EpicMonster;
import medieval_trader_game.mercenaries.Mercenary;
import medieval_trader_game.mercenaries.MercenaryTeam;
import medieval_trader_game.mercenaries.enemies.Encounter;
import medieval_trader_game.player.Player;

public class Expedition {

    private int expeditionLevel = 0;
    private final int maxLvl = EpicMonster.values().length - 1;
    private Encounter epicEncounter;
    private Mercenary currentMonster;

    public Expedition(){
        currentMonster = Enemies.create( EpicMonster.values()[0] );
    }

    public UiInfo printExpeditionMenu(){
        UiInfo info = new UiInfo();
        info.viewAdd("        __  / \\   / \\    |  |     ____    |   |           _____     __(");
        info.viewAdd("      _(  )_  _\\ /  _\\   |  |     |  |    |   |        __(     )_  (   ");
        info.viewAdd("     (       )  \\_ __ \\  |  |     |__|    |   |     ( (         _)(    ");
        info.viewAdd("    (_       _)\\ _(  )   |  |             |   |    ( (           _)_   ");
        info.viewAdd("     (__    )   (_ . _)  |  |     ____    |   |   (   (___:  .___) (__ ");
        info.viewAdd("        | |    _   |     |  |     |  |    |   |    (_.  ) |  |    ___ |");
        info.viewAdd(" ( )    | |  _( )_ | _-  |  |     |__|    |   |      ||   |  Ⴑ  _(   ) ");
        info.viewAdd("(  ___  | | (     )     /                      \\ -  _|į_  |  } (_    _)");
        info.viewAdd(" _(   )    (_ . :__)   |                        |  .              ||   ");
        info.viewAdd("(_    _)   _  | | _   /                          \\    /--\\<*>/--\\      ");
        info.viewAdd("   ||    ˎ.  -_   i  /           _____            \\   \\||/~~~\\||/  ,.  ");
        info.viewAdd(" . ||_      .lˎ      |           |  .|            |    []~~*~~[]     ˎ.");
        info.viewAdd("    ,.   _-      ,.  |_ _ _ _ _ _|___| _ _ _ _ _ _|    []~~~~~[]       ");
        info.viewAdd(" ");
        info.viewAdd("Mighty wizard lives in this Tower. He welcome's You, and invites in for supper.");
        info.viewAdd("He tell's You a story of terrifying monster and treasure beyond belief.");
        info.viewAdd("This monster is:");
        info.viewAdd(currentMonster.printInlineInfo());
        info.viewAdd(" ");
        info.viewAdd("One drop of monsters blood is what he needs for his experiments.");
        info.viewAdd("He asks if You're brave enought to bring it?");
        info.viewAdd("y) 'Hold my beer.' - jump into portal.");
        info.viewAdd("n) 'My, oh my! Its getting really late... We need to go now...");

        return info;
    }

    private void levelUp(){
        if( expeditionLevel < maxLvl )
            expeditionLevel++;

        currentMonster = Enemies.create( EpicMonster.values()[expeditionLevel] );
    }

    public boolean challangeMonster(Player player){
        MercenaryTeam monsterTeam = new MercenaryTeam();
        monsterTeam.addMercenary(currentMonster);
        epicEncounter = new Encounter( player, monsterTeam);
        epicEncounter.setWin( epicEncounter.makeFight() );
        if( epicEncounter.isWin() ) levelUp();
        return epicEncounter.isWin();
    }

    public UiInfo report() {
        UiInfo report = new UiInfo();
        report.print(epicEncounter.printFightReport());
        if(epicEncounter.isWin()){
            report.print(printWinInfo());
        }
        else{
            report.print(printDefeatInfo());
        }
        report.consoleAdd("Time to go home...");
        return report;
    }

    private UiInfo printWinInfo(){
        UiInfo info = new UiInfo();
        info.viewAdd(" ");
        info.viewAdd("*-------------< W I N >-------------*");
        info.viewAdd(" ");
        info.viewAdd("You fight a glorious battle and bring back precious loot, ");
        info.viewAdd("vial of monster's blood for the experiments, and curious item for You.");
        if( epicEncounter.deadAllays() > 0 )
            info.viewAdd("Unfortunately " + epicEncounter.deadAllays() + " of You're man paid the highest price for it.");
        info.viewAdd("Wizard rewards You also with " + goldReward() + " gold.");
        info.viewAdd(" ");
        info.viewAdd("*----------------<*>----------------*");
        info.viewAdd(" ");
        return info;
    }

    private UiInfo printDefeatInfo(){
        UiInfo info = new UiInfo();
        info.viewAdd(" ");
        info.viewAdd("*----------< D E F E A T >----------*");
        info.viewAdd(" ");
        info.viewAdd("That didn't go well. As the last of You're companions bites the dust You run!");
        info.viewAdd("At the last second some magical force pulls You back in to portal.");
        info.viewAdd("Memories of what You witnessed today will hunt You forever...");
        info.viewAdd(" ");
        info.viewAdd("*----------------<*>----------------*");
        info.viewAdd(" ");
        return info;
    }

    //im not doing another enum today... :D
    public Item expeditionReward(){
        return switch (expeditionLevel) {
            case 1 -> new Item("Wyvern's Epic Claw", 200, 70, 0);
            case 2 -> new Item("Minotaur's Epic 'Horn'", 250, 70, 0);
            case 3 -> new Item("Jelly's Epic... thingy.", 300, 70, 1000);
            case 4 -> new Item("Medusa's Epic Flying Sabre", 350, 70, 1000);
            case 5 -> new Item("Dragon's Epic the 'Game Ender' Zweihander", 1000, 100, 0);
            default -> new Item();
        };
    }

    public int goldReward(){
        return MaxValues.EXPEDITION_REWARD_GOLD * expeditionLevel;
    }

    public void rewardPlayer(Player player) {
        player.inventory().addItem( expeditionReward() );
        player.addGold( goldReward() );
    }
}

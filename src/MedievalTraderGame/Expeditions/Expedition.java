package MedievalTraderGame.Expeditions;

import MedievalTraderGame.Interface.UiInfo;
import MedievalTraderGame.Items.Item;
import MedievalTraderGame.MaxValues;
import MedievalTraderGame.Mercenaries.Enemies.Enemies;
import MedievalTraderGame.Mercenaries.Enemies.EpicMonster;
import MedievalTraderGame.Mercenaries.Mercenary;
import MedievalTraderGame.Mercenaries.MercenaryTeam;
import MedievalTraderGame.Mercenaries.Enemies.Encounter;
import MedievalTraderGame.Player.Player;

public class Expedition {

    private int expeditionLevel = 0;
    private final int maxLvl = EpicMonster.values().length - 1;
    private Encounter epicEncounter;
    private Mercenary currentMonster;
    public UiInfo report;

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

    public void levelUp(){
        if( expeditionLevel < maxLvl )
            expeditionLevel++;

        currentMonster = Enemies.create( EpicMonster.values()[expeditionLevel] );
    }

    public boolean challangeMonster(Player player){
        MercenaryTeam monsterTeam = new MercenaryTeam();
        monsterTeam.addMercenary(currentMonster);
        epicEncounter = new Encounter( player, monsterTeam);
        epicEncounter.win = epicEncounter.makeFight();
        return epicEncounter.win;
    }

    public UiInfo report() {
        UiInfo info = new UiInfo();
        info.print(epicEncounter.printFightReport());
        report = epicEncounter.report;
        if(epicEncounter.win){
            report.viewAdd(" ");
            report.viewAdd("*-------------< W I N >-------------*");
            report.viewAdd(" ");
            report.viewAdd("You fight a glorious battle and bring back precious loot, ");
            report.viewAdd("vial of monster's blood for the experiments, and curious item for You.");
            if( epicEncounter.deadAllays() > 0 ) report.viewAdd("Unfortunately " + epicEncounter.deadAllays() + " of You're man paid the highest price for it.");
            report.viewAdd("Wizard rewards You also with " + goldReward() + " gold.");
            report.viewAdd(" ");
            report.viewAdd("*----------------<*>----------------*");
            report.viewAdd(" ");
        }
        else{
            report.viewAdd(" ");
            report.viewAdd("*----------< D E F E A T >----------*");
            report.viewAdd(" ");
            report.viewAdd("That didn't go well. As the last of You're companions bites the dust You run!");
            report.viewAdd("At the last second some magical force pulls You back in to portal.");
            report.viewAdd("Memories of what You witnessed today will hunt You forever...");
            report.viewAdd(" ");
            report.viewAdd("*----------------<*>----------------*");
            report.viewAdd(" ");
        }

        report.consoleAdd("Time to go home...");
        return report;
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
        return MaxValues.expeditionRewardGold * expeditionLevel;
    }
}

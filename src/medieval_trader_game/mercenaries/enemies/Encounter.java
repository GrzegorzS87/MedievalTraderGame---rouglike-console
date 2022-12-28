package medieval_trader_game.mercenaries.enemies;

import medieval_trader_game.Dice;
import medieval_trader_game.console_interface.UiInfo;
import medieval_trader_game.items.Item;
import medieval_trader_game.MaxValues;
import medieval_trader_game.mercenaries.Mercenary;
import medieval_trader_game.mercenaries.MercenaryTeam;
import medieval_trader_game.player.Player;

public class Encounter {

    private final MercenaryTeam enemyTeam;
    private final Player player;
    private int itemRewardPool;
    private int itemsCollected;
    private int goldCollected;
    private final int startingPlayerTeamSize;
    private final UiInfo report = new UiInfo();
    private boolean win;

    //well, fighting is usually ugly...

    public Encounter(Player player){
        this.player = player;
        int power = player.mercenaryTeam().getTeamPower();
        this.enemyTeam = Enemies.generateRandomEnemies(power, player.mercenaryTeam().size());
        itemRewardPool = enemyTeam.size();
        startingPlayerTeamSize = player.mercenaryTeam().size();
    }

    public Encounter(Player player, MercenaryTeam enemyTeam){
        this.player = player;
        this.enemyTeam = enemyTeam;
        startingPlayerTeamSize = player.mercenaryTeam().size();
    }

    public static boolean wasThereAnEncounter(){
        return (Dice.nextInt(0, 100) <= MaxValues.ENCOUNTER_CHANCE);
    }

    public boolean makeFight(){
        int range = MaxValues.RANGE_ROUNDS;
        int round = 0;

        while ( round < MaxValues.MAX_FIGHT_ROUNDS) {
            round++;
            report.viewAdd(" ");
            if(range>0){
                report.viewAdd("*---- < R O U N D " + round + " - range > ----*");
            }else{
                report.viewAdd("*---- < R O U N D " + round + " - melee  > ----*");
            }
            report.viewAdd("Enemy:");

            if( fightingRound( enemyTeam, player.mercenaryTeam(), range ) )
                return false;

            report.viewAdd(" ");
            report.viewAdd("Allay:");

            if( fightingRound( player.mercenaryTeam(), enemyTeam, range ) )
                return true;

            report.viewAdd(" ");

            if(range>0) {
                range--;
            }

        }
        report.viewAdd("Everyone is too exhausted to fight, enemy fall's back!");
        itemRewardPool = 1;
        return true;
    }

    private boolean fightingRound(MercenaryTeam attackingTeam, MercenaryTeam defendingTeam, int range){
        for(int i=0; i < attackingTeam.size(); i++){
            if( defendingTeam.size() == 0 ) return true; //attacker wins fight

            Mercenary attacker = attackingTeam.at(i);
            if( attacker.isMelee() && range > 0 ) {
                report.viewAdd(attacker.name + " is moving closer.");
                continue;
            }

            Mercenary target = pickTarget ( defendingTeam );
            String entry = attacker.name + " attacks " + target.name;

            boolean hit = tryHit( attacker );
            boolean evade = tryEvade( target );

            if(hit){       //come on, it's not that nested

                if(evade){
                    entry += ", but he manages to evade.";
                    report.viewAdd(entry);
                    continue; //no one died, fight is not over
                }
                else{
                    int dmg = attacker.damage() + attacker.roll();
                    target.takeDamage(dmg);
                    entry += " and deals " + dmg + ". He's now " + target.getHealth() + " health.";
                }

            }
            else{
                entry += " but hits the air.";
            }

            //if lethal
            if(target.isDead()){
                if(target.hasItem()) { // only player team has items
                    player.inventory().addItem( target.unEquipItem() );
                }
                defendingTeam.fireMercenary(target);
                entry += " \n He dies. " + attacker.name + " levels up!";
                attacker.levelUp();
            }

            report.viewAdd(entry);
        }
        return defendingTeam.size() == 0;
    }

    private boolean tryHit(Mercenary attacker) {
        int hitModifier = attacker.getHit() + attacker.roll();
        return Dice.nextInt(0, 100) <= hitModifier;  // modifier is % chance
    }

    public boolean tryEvade(Mercenary target){
        if(target.canParry()){
            int evadeModifier = target.getEvade()+target.roll();
            return Dice.nextInt(0, 100) <= evadeModifier;
        }
        return false;
    }

    private Mercenary pickTarget(MercenaryTeam defendingTeam) {
        return defendingTeam.at( Dice.nextInt(0, defendingTeam.size()));
    }

    private static UiInfo runOrFightPicture(){
        UiInfo info = new UiInfo();
        info.viewAdd(")__     _____                                 /\\            _          ");
        info.viewAdd("   )  _(     )__                        ____ / _\\   /\\    _(  )_      ");
        info.viewAdd("    )(_         ) )                   _(   _)_  \\ __ _\\  (       )     ");
        info.viewAdd("   _(_           ) )                 (_  .    )  (  )_ \\(_       _)    ");
        info.viewAdd(" __) (___.  :___)   )                 (__| :__) (_ . _)   (    __)     ");
        info.viewAdd("| ___    |  | (  ._)                     | |  ( )  |   _    | |        ");
        info.viewAdd(" (   )_  J  |   ||                       | | (   ) | _( )_  | |    ( ) ");
        info.viewAdd("(_    _) {  |  _j|_   .,     -     ,. ___j l__ |.   (     ) | |  ___  )");
        info.viewAdd("   ||  ._|   l_  . .                   ,.   -.,    (__: . _)    (   )_ ");
        info.viewAdd("   |l i   _  ., .l,                       ,.   .l   _ | |  _   (_    _)");
        info.viewAdd("_l .    _     _-     ()--().----.           . - __ - .| |   -'    ||   ");
        info.viewAdd("   -_   ,.  -         \\Oo/ ____  ;___.'         _   i   _-  .,    ||   ");
        info.viewAdd(".,    ..              >v< ^^   ^^                .,     ,l.      _-  . ");
        info.viewAdd(" ");
        return info;



    }

    public UiInfo printRunOrFightMenu() {

        UiInfo info = new UiInfo();
        info.print(runOrFightPicture());
        info.viewAdd("While resting in the woods You notice camp is being surrounded by enemies: ");
        for (int i = 0; i < enemyTeam.size(); i++) {
            info.viewAdd((i + 1) + ") " + enemyTeam.at(i).printInlineInfo());
        }
        info.viewAdd(" ");
        info.viewAdd("You're team:");
        for (int i = 0; i < player.mercenaryTeam().size(); i++) {
            info.viewAdd((i + 1) + ") " + player.mercenaryTeam().at(i).printInlineInfo());
        }
        info.viewAdd(" ");
        info.viewAdd(" ");
        String line = "You have time to escape, but You can save either gold or inventory. ";
        if(player.mercenaryTeam().size() > 0){
            line += "Mercenary's look at You in silence.";
        }
        info.viewAdd(line);
        info.viewAdd("What will You decide?");
        if(player.mercenaryTeam().size() > 0){
            info.viewAdd("1) Fight!");
        }
        info.viewAdd("2) Run with gold");
        info.viewAdd("3) Run with items");

        return info;
    }

    public UiInfo printFightReport(){
        return report;
    }

    public void resolveFight(){
        win = makeFight();

        if(win){
            applyReward();
            report.print(winInfo());
        }
        else{
            applyPenalty();
            report.print(lostInfo());
        }
    }

    public UiInfo runWithGold() {
        UiInfo runInfo = new UiInfo();
        runInfo.consoleAdd("You run away with gold.");
        player.inventory().dumpInventory();

        return runInfo;
    }

    public UiInfo runWithItems(){
        UiInfo runInfo = new UiInfo();
        runInfo.consoleAdd("You run with items.");
        player.setGold(0);

        return runInfo;
    }

    public int deadAllays(){
        return startingPlayerTeamSize - player.mercenaryTeam().size();
    }

    public void applyReward() {

        int itemReward = 0;
        if (itemRewardPool > 0){
            itemReward = Dice.nextInt(1 + itemRewardPool);
            for (itemsCollected = 0; itemsCollected < itemReward; itemsCollected++) {
                Item item = new Item();
                item.setPricePlayerPaid(0);
                player.inventory().addItem(item);
            }
        }

        int goldReward = 100 * (itemRewardPool - itemReward);
        if (goldReward > 0){
            goldCollected = Dice.nextInt(0,goldReward);
            player.addGold(goldCollected);
        }
    }

    private UiInfo winInfo() {
        UiInfo winInfo = new UiInfo();

        winInfo.viewAdd(" ");
        winInfo.viewAdd("*-------------< W I N >-------------*");
        String line = "You live! ";
        if ( deadAllays() > 0 ) line += "Unfortunately " + deadAllays() + " of You're man died in fight.";
        winInfo.viewAdd( line );
        winInfo.viewAdd("After the encounter You collect " + itemsCollected + " items, and " + goldCollected + " gold coins.");

        return winInfo;
    }

    public void applyPenalty(){
        int savedGold = 0;
        if( player.balance()  > 1 ) savedGold = Dice.nextInt( 0, player.balance() );
        player.setGold( savedGold );
        if( player.inventory().size() > 0 && Dice.nextBoolean() ){
            player.inventory().dumpInventorySaveOne();
        }
        else {
            player.inventory().dumpInventory();
        }

    }

    private UiInfo lostInfo(){
        UiInfo lostInfo = new UiInfo();
        lostInfo.viewAdd(" ");
        lostInfo.viewAdd("*----------< D E F E A T >----------*");
        lostInfo.viewAdd("You lost the fight. As the last of You're companions bites the dust You run!");
        lostInfo.viewAdd("You escape with whatever was in You're pocket. (" + player.balance() + ")");
        if(player.inventory().isEmpty()){
            lostInfo.viewAdd("You leave all You're other belongings behind to save You're self.");
        }
        else{
            lostInfo.viewAdd("You also manage to save one item.");
        }

        return lostInfo;
    }

    public void setWin( boolean battleWon ) {
        win = battleWon;
    }

    public boolean isWin() {
        return win;
    }
}

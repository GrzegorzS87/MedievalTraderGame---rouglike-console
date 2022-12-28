package medieval_trader_game;

import medieval_trader_game.expeditions.Expedition;
import medieval_trader_game.console_interface.*;
import medieval_trader_game.items.Inventory;
import medieval_trader_game.items.Item;
import medieval_trader_game.locations.enterprise.Enterprise;
import medieval_trader_game.locations.Location;
import medieval_trader_game.mercenaries.enemies.Encounter;
import medieval_trader_game.mercenaries.enemies.Enemies;
import medieval_trader_game.mercenaries.Mercenary;
import medieval_trader_game.mercenaries.MercenaryTeam;
import medieval_trader_game.player.*;

/*
    *-----------------------------< Medieval Trader Game >-----------------------------*

*   fk reading code, play the game

    GameMLOW object needs UI to be able to display list of strings provided with UiInfo object.
    IMHO UiInfo could be easily changed to some other 'answer' object if needs be.
    UiInfo consists of view and console lists of strings to be displayed at once,
    but updated separately if one of list is empty, so nothing gets updated if both are empty.
    GameMLOW object accepts inputs from UI in form of String, and interprets them depending on game STAGE,
    providing appropriate text to display, and applying player's decisions.

    Call order in UI should look like this:
    * initialization
    * MTGame game = new MTGame()
    * UI.display( game.printInfo() )        //display accepts UiInfo objects

    on_user_input method:
    UI.display( game.playerChoice(choice) ) // accepts player choice, displays information in case of invalid input
    game.isGameOver()                       //if game is hopeless will set game state to "game over", also returns true
    UI.display( game.printInfo() )          // provides new information to display on screen if game state changed

    game.printGeneralInfo() provides user with most basic and useful information, should be updated each time choice is made
    UI can monitor game.stage enum if needed

    game can be extended by creating new STAGE.X and implementing printXMenu() and onXPlayerChoice(String)
    to be called inside printMenu() and playerChoice(String)

    Not sure if I will work on this again but cool extension would be more items, and additional item slots on mercenary,
    like armor slot, shield slot, helmet slot, some abilities?
    Balancing it thou...

    have fun,
    best regards,
    Grzegorz

    *-----------------------------<    <>    >-----------------------------*
    */

public class MTGame {

    private final Player player;
    private Encounter encounter;
    private STAGE stage = STAGE.RULES;
    private int round;

    private final Location town = new Location("Town");
    private final Location farm = new Location("Farm");
    private final Location castle = new Location("Castle");
    private final Location port = new Location("Port");
    private final Expedition expedition = new Expedition();
    private boolean unlockedExpeditions = false;

    public MTGame(){
        player = new Player(town);
        player.location().newEnterpriseOffer();
        round = 0;
    }

    public UiInfo printMenu() {

        switch(stage){
            case RULES:
                return printRules();

            case MAIN_MENU:
                return printMainMenu();

            case TRAVEL_MENU:
                return printTravelMenu();

            case ON_THE_ARRIVAL_INFO:
                return printOnTheArrivalInfo();

            case SELL_INVENTORY:
                return printSellItemsMenu();

            case BUY_VENDOR:
                return printBuyItemsMenu();

            case HIRE_MERC:
                return printHireMercMenu();

            case MANAGE_MERC:
                return printManageMercenaryMenu();

            case PICK_MERC:
                return printPickMercenaryToManageMenu();

            case PICK_ITEM_TO_WEAR:
                return printPickItemToWear();

            case ENTERPRISE_MENU:
                return printEnterpriseMenu();

            case EXPEDITION_MENU:
                return printExpeditionMenu();

            case EXPEDITION_REPORT:
                return printExpeditionReport();

            case RUN_OR_FIGHT_MENU:
                return printRunOrFightMenu();

            case FIGHT:
                return encounter.printFightReport();

            case GAME_OVER:
                return printGameOver();

            case QUIT:
                return printQuitMenu();

            default:
                UiInfo info = new UiInfo();
                info.consoleAdd("Invalid choice, pick within the list, 99 to back.");
                return info;
        }

    }

    public UiInfo playerChoice(String choice){

        UiInfo info = new UiInfo();

        switch (stage){

            case RULES, ON_THE_ARRIVAL_INFO, EXPEDITION_REPORT -> {  //accepts any input and navigates to next menu
                stage = STAGE.MAIN_MENU;
                return info;
            }

            case MAIN_MENU -> {
                return onMainMenuChoice(choice);
            }

            case TRAVEL_MENU -> {
                return onTravelMenuChoice(choice);
            }

            case RUN_OR_FIGHT_MENU -> {
                return onRunOrFightMenu(choice);
            }

            case FIGHT -> {
                arriveAtNewLocation();
                player.mercenaryTeam().healAll();
                return info;
            }

            case SELL_INVENTORY -> {
                return onSellItemChoice(choice);
            }

            case BUY_VENDOR -> {
                return onBuyItemChoice(choice);
            }

            case HIRE_MERC -> {
                return onHireMercChoice(choice);
            }

            case PICK_MERC -> {
                return onPickMercenaryChoice( choice );
            }

            case MANAGE_MERC -> {
                return onManageMercenaryChoice( choice );
            }

            case PICK_ITEM_TO_WEAR -> {
                return onPickItemToWear( choice );
            }

            case ENTERPRISE_MENU -> {
                return onEnterpriseChoice( choice );
            }

            case EXPEDITION_MENU -> {
                return onExpeditionChoice( choice, player );
            }

            case QUIT -> {
                return onQuitMenuChoice(choice);
            }

            default -> { return info; }
        }
    }

    public UiInfo printMainMenu(){
        UiInfo info = new UiInfo();
        info.viewAdd("*--------------------------------{ M A P }----------------------------* OPTIONS:");
        info.viewAdd("|                          ()      ()         ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ | 1) Travel");
        info.viewAdd("|      1)Town  ___        (  )    (  )      3)Castle   ^ ^ ^ ^ ^ ^ ^ ^| ");
        info.viewAdd("|       ___   /___\\         |  ()   |   ( )                   ^ ^ ^ ^ | 2) Local market / BUY");
        info.viewAdd("|      /---\\  |__n|      ( )  (  )     (   )  /\\  ___  /\\        ^ ^ ^| 3) Inventory / SELL");
        info.viewAdd("|      |__n| ..         (   )   | ()     |    ||_/___\\_||         ^ ^ | 4) Hire mercenary ");
        info.viewAdd("|                .        |  ( ) (  ) ()      ||_|_#_|_||            ^| 5) Manage mercenaries ");
        info.viewAdd("| ~~~~~~~         . . ( )  .(   ). |.(  ) . . . . . .                 | 6) Local Enterprise ");

        if(unlockedExpeditions){
        info.viewAdd("|   ~~~~~~        A  (   )    |  ( )   | ( )        ~~~~~~~~~         |");
        info.viewAdd("|    ~~~~~~      / \\   |        (   ).  (   )    ~~~~~~~~~~~~~        | R) rules");
        info.viewAdd("|        ~~~~    [:]    ()        |   .   |     ~~~~~~~~~~      ( )   | ");
        info.viewAdd("|          ~~~~  [:]   (  )    ( )      .         ~~~~~~       (   )  | 99) quit");
        info.viewAdd("|    /\\  5)Tower [#]     |    (   )     .            ( )         |    |");
        }
        else{
        info.viewAdd("|   ~~~~~~           (   )    |  ( )   | ( )        ~~~~~~~~~         |");
        info.viewAdd("|    ~~~~~~            |        (   ).  (   )    ~~~~~~~~~~~~~        | R) rules");
        info.viewAdd("|        ~~~~           ()        |   .   |     ~~~~~~~~~~      ( )   | ");
        info.viewAdd("|          ~~~~        (  )    ( )      .         ~~~~~~       (   )  | 99) quit");
        info.viewAdd("|    /\\                  |    (   )     .            ( )         |    |");
        }
        info.viewAdd("|   /  \\      /\\                |      .            (   )             |");
        info.viewAdd("|  /  _ \\    /  \\       ( )           .  .  . .       |     ( )       |");
        info.viewAdd("| /  / \\\\   /    \\     (   )        .            .         (   )    ~~|");
        info.viewAdd("|     /\\   /      /\\     |       .                .          |    ~~~~|");
        info.viewAdd("|    /  \\        /  \\        .   ( )     ( )        .           ~~~~~~|");
        info.viewAdd("|   /    \\ /\\               .   (   )   (   )( )   . 4)Port  ~/|~~~~~~|");
        info.viewAdd("|  /  /\\  /  \\   2)Farm  _____.   |       | (   ) .  ___    ~/ |~~~~~~|");
        info.viewAdd("|    /  \\ .....         /___/ \\               | .   /___\\ ~~/__|~~~~~~|");
        info.viewAdd("|   /  ......   ....... |n_n| | ..... ( )        .  |_n_|=~~\\_|__/~~~~|");
        info.viewAdd("|    ......  .... .......... .....   (   )       . . .  ~~~~~~~~~~~~~~|");
        info.viewAdd("| ..... .... ......... .......         |               ~~~~~~~~~~~~~~~|");
        info.viewAdd("*---------------------------------------------------------------------*");
        info.viewAdd("What would You like to do now?:");

        return info;
    }

    private UiInfo onMainMenuChoice(String choice) {

        UiInfo info = new UiInfo();

        switch (choice) {
            case "1" -> stage = STAGE.TRAVEL_MENU;
            case "2" -> stage = STAGE.BUY_VENDOR;
            case "3" -> stage = STAGE.SELL_INVENTORY;
            case "4" -> stage = STAGE.HIRE_MERC;
            case "5" -> stage = STAGE.PICK_MERC;
            case "6" -> stage = STAGE.ENTERPRISE_MENU;
            case "99" -> stage = STAGE.QUIT;
            case "R", "r" -> stage = STAGE.RULES;
            default -> info.consoleAdd("Invalid choice, pick within the list, 99 to quit.");
        }
        return info;
    }

    private UiInfo printTravelMenu(){
        UiInfo info = new UiInfo();
        if(player.cantAffordTravle()){
            info.viewAdd("Travel would cost " + player.calculateTravleCost() + " while You have only " + player.balance() + ".");
            info.viewAdd("Try selling something first to get money.");
            info.viewAdd("99) <- Back ");
        }
        else{
            info.viewAdd("You're in " + player.location().name + ", where would You like to go?");
            info.viewAdd("1) Town ");
            info.viewAdd("2) Farm ");
            info.viewAdd("3) Castle ");
            info.viewAdd("4) Port ");
            if(unlockedExpeditions){
                info.viewAdd("5) Tower ");
            }
            info.viewAdd("99) <- Back ");
        }

        return info;
    }

    private UiInfo onTravelMenuChoice(String choice) {

        UiInfo info = new UiInfo();
        Location destination;

        if(player.cantAffordTravle()){
            stage = STAGE.MAIN_MENU;
            return info;
        }

        if( choiceIsBack(choice) ) {
            stage = STAGE.MAIN_MENU;
            info.consoleAdd(" ");
            return info;
        }

        if(unlockedExpeditions && "5".equals(choice) ){
            player.payTravleCost();
            stage = STAGE.EXPEDITION_MENU;
            return info;
        }

        switch (choice) {
            case "1" -> destination = town;
            case "2" -> destination = farm;
            case "3" -> destination = castle;
            case "4" -> destination = port;
            default -> { // if choice is invalid
                info.consoleAdd("Pick option within list or 99 to back.");
                return info;
            }
        }

        if(player.location() == destination){
            info.consoleAdd("You're already in " + destination.name);
            return info;
        }

        round++;
        player.travleTo(destination, round);

        if(Encounter.wasThereAnEncounter()){
            encounter = new Encounter(player);
            stage = STAGE.RUN_OR_FIGHT_MENU;
            return info;
        }

        arriveAtNewLocation();

        info.consoleAdd("You travel safely to " + destination.name + ".");
        return info;
    }

    private UiInfo printOnTheArrivalInfo() {
        UiInfo info = new UiInfo();
        int profit = player.location().collectProfits(round);
        player.addGold(profit);

        info.viewAdd("*---------< A R R I V A L  I N F O >---------*");
        info.viewAdd("You own some business in " + player.location().name);
        info.viewAdd("On the arrival You collect: " + profit);
        info.viewAdd(" ");
        info.viewAdd("press enter...");


        return info;
    }

    public void arriveAtNewLocation(){
        if(player.ownsEnterpriseHere()) {
            stage = STAGE.ON_THE_ARRIVAL_INFO;
        }
        else{
            stage = STAGE.MAIN_MENU;
        }
        player.location().vendor.refreshAllOffers();
        player.location().vendor.makePlayerAnOffer(player);
        player.location().newEnterpriseOffer();

        tryUnlockExpeditions();
    }

    private void tryUnlockExpeditions() {
        if(town.playerOwnsEnterprise()
                && farm.playerOwnsEnterprise()
                && castle.playerOwnsEnterprise()
                && port.playerOwnsEnterprise() )
            unlockedExpeditions = true;
    }

    private UiInfo printBuyItemsMenu(){

        UiInfo info = new UiInfo();
        info.viewAdd(" ");
        info.viewAdd("*------------{ V E N D O R }-------------*");

        Inventory inventory = player.location().vendor.inventory();
        if (inventory.isEmpty()){
            info.viewAdd("This vendor is out of stock, 99 to back.");
            return info;
        }

        for(int i = 0; i < inventory.size(); i++){
            Item item = inventory.itemAt(i);

            info.viewAdd(" ");
            info.viewAdd("    item ID: " + i );
            info.print(item.printInfo());

            int price = item.getOfferPrice();
            String line ="   Price is: " + price;
            if(price <= player.balance()){
                if(price < item.value) line += "  (low price)";
            }
            else{
                line += "  (can't afford)";
            }
            info.viewAdd(line);
            info.viewAdd(" ");
        }
        info.viewAdd("Pick item ID 0 to " + (inventory.size()-1) + " to purchase, 99 to back.");

        return info;
    }

    private UiInfo onBuyItemChoice( String choice ){

        UiInfo info = new UiInfo();
        Inventory vendorInventory = player.location().vendor.inventory();

        if(choiceIsBack( choice )){
            stage = STAGE.MAIN_MENU;
            info.consoleAdd(" "); // clears console when backing
            return info;
        }

        if(vendorInventory.choiceIsInvalidId( choice )){
            return invalidOptionInfo();
        }

        int id = Integer.parseInt( choice );
        Item item = vendorInventory.itemAt( id );
        int price = item.getOfferPrice();
        if( player.balance() >= price ){
            player.buyItem(item);
            info.consoleAdd( "Bought " + item.name );
            info.consoleAdd( "You have " + player.balance() + " gold." );
        }
        else{

            info.consoleAdd( "Can't afford this item." );
        }

        return info;
    }

    private UiInfo printSellItemsMenu(){

        UiInfo info = new UiInfo();
        info.viewAdd(" ");
        info.viewAdd("*---------{ I N V E N T O R Y }----------*");
        info.viewAdd(" ");

        if(!player.inventory().isEmpty()){

            for(int i = 0; i < player.inventory().size(); i++){
                Item item = player.inventory().itemAt(i);
                info.viewAdd("Item " + i + " is:");
                info.print(item.printInfo());
                info.viewAdd("   You paid: " + item.getPricePlayerPaid());
                int difference = item.getOfferPrice() - item.getPricePlayerPaid();
                info.viewAdd("   Offer is: " + item.getOfferPrice() + "   (" + difference + " gold profit)");
                info.viewAdd(" ");
            }
            info.viewAdd("Pick item id 0 to " + (player.inventory().size()-1) + " to sell it, 99 to back.");
        } else {
            info.viewAdd("Inventory is empty.");
            info.viewAdd("Type 99 to back.");
        }

        return info;
    }

    private UiInfo onSellItemChoice(String choice){
        UiInfo info = new UiInfo();

        if(choiceIsBack(choice)){
            stage = STAGE.MAIN_MENU;
            info.consoleAdd(" "); // clears console when backing
            return info;
        }

        if( player.inventory().choiceIsInvalidId( choice ) )
            return invalidOptionInfo();

        Item item = player.inventory().itemAt( Integer.parseInt( choice ) );

        player.addGold( item.getOfferPrice() );
        player.inventory().removeItem( item );

        info.consoleAdd( "Sold " + item.name );
        info.consoleAdd( "You have " + player.balance() + " gold." );
        return info;
    }

    private UiInfo printHireMercMenu(){

        MercenaryTeam tavern = player.location().vendor.mercForHire();
        UiInfo info = new UiInfo();
        info.viewAdd(" ");
        info.viewAdd("*--------{ M E R C E N A R I E S }---------*");
        info.viewAdd(" ");

        if (tavern.size()<1){
            info.viewAdd("No one left to hire!");
            info.viewAdd("Type 99 to back");
            return info;
        }


        for(int i = 0; i < tavern.size(); i++){
            Mercenary merc = tavern.at(i);
            info.viewAdd("Mercenary ID: " + i + " Hiring price: " + merc.getHirePrice() );
            info.print(merc.printInfo());
            info.viewAdd(" ");
            info.viewAdd(" ");
        }
        info.viewAdd("Pick mercenary ID 0 to " + (tavern.size()-1) + " to hire, 99 to back.");

        return info;
    }

    private UiInfo onHireMercChoice(String choice){

        UiInfo info = new UiInfo();
        MercenaryTeam mercForHire = player.location().vendor.mercForHire();

        if(choiceIsBack(choice)){
            stage = STAGE.MAIN_MENU;
            info.consoleAdd(" ");
            return info;
        }

        if(mercForHire.choiceIsIinvalidId(choice)){
            return invalidOptionInfo();
        }

        Mercenary mercenary;
        int mercenaryId = Integer.parseInt( choice );
        mercenary = mercForHire.at( mercenaryId );
        int price = mercenary.getHirePrice();


        if( price > player.balance() ){
            return notEnoughtGoldInfo();
        }
        else {
            player.payGold( price );
            player.mercenaryTeam().addMercenary( mercenary );
            mercForHire.fireMercenary( mercenaryId );

            info.consoleAdd( "Hired!");
            return info;
        }
    }

    private UiInfo printPickMercenaryToManageMenu(){

        UiInfo info = new UiInfo();
        info.viewAdd("*--------{ M E R C E N A R I E S }---------*");
        info.viewAdd(" ");
        info.print( player.mercenaryTeam().printTeamInfo() );
        info.viewAdd("Pick mercenary to manage, 99 to back.");

        return info;
    }

    private UiInfo onPickMercenaryChoice(String choice) {

        UiInfo info = new UiInfo();

        if(choiceIsBack( choice )){
            stage = STAGE.MAIN_MENU;
            info.consoleAdd(" ");
            return info;
        }

        if( player.mercenaryTeam().choiceIsIinvalidId( choice ) ){
            return invalidOptionInfo();
        }

        player.mercToManageId = Integer.parseInt( choice );
        stage = STAGE.MANAGE_MERC;

        return info;
    }

    private UiInfo printManageMercenaryMenu(){

        UiInfo info = new UiInfo();
        info.print( player.mercenaryTeam().at(player.mercToManageId).printInfo() );
        info.viewAdd( " ");
        info.viewAdd( "Would You like to do:" );
        info.viewAdd( " 1) equip item");
        info.viewAdd( " 2) uneqip item");
        info.viewAdd( " 3) fire mercenary!");
        info.viewAdd( " ");
        info.viewAdd( "99) back");

        return info;
    }

    private UiInfo onManageMercenaryChoice( String choice ) {

        UiInfo info = new UiInfo();

        if( choiceIsBack( choice ) ){
            stage = STAGE.PICK_MERC;
            info.consoleAdd("Not You ..." );
            return info;
        }

        switch ( choice ){
            case "1": {
                stage = STAGE.PICK_ITEM_TO_WEAR;
                info.consoleAdd("Grab this...");
                return info;
            }

            case "2":{
                Mercenary mercenary = player.mercenaryTeam().at(player.mercToManageId);

                if( mercenary.hasItem() ){
                    Item item = mercenary.unEquipItem();
                    player.inventory().addItem( item );
                    info.consoleAdd("I'll take that for now...");
                }
                else{
                    info.consoleAdd("You never gave me anything... chief...");
                }

                stage = STAGE.PICK_MERC;
                return info;
            }

            case "3":{
                Mercenary mercenary = player.mercenaryTeam().at(player.mercToManageId);
                if(mercenary.hasItem()){
                    player.inventory().addItem(mercenary.unEquipItem());
                }
                player.mercenaryTeam().fireMercenary(player.mercToManageId);
                info.consoleAdd("You're fired!");

                stage = STAGE.PICK_MERC;
                return info;
            }

            default: return invalidOptionInfo();
        }

    }

    private UiInfo printPickItemToWear(){
        return player.inventory().printEquipItemMenu();
    }

    private UiInfo onPickItemToWear(String choice) {
        UiInfo info = new UiInfo();

        if(choiceIsBack(choice)){
            stage = STAGE.PICK_MERC;
            info.consoleAdd(" "); // clears console when backing
            return info;
        }

        if( player.inventory().choiceIsInvalidId( choice ) )
            return invalidOptionInfo();

        int itemId = Integer.parseInt( choice );
        Item item = player.inventory().itemAt( itemId );
        Mercenary mercenary = player.mercenaryTeam().at(player.mercToManageId);

        if(mercenary.hasItem()){
            player.inventory().addItem( mercenary.unEquipItem() );
        }

        mercenary.equip(item);
        player.inventory().removeItem( item );
        stage = STAGE.PICK_MERC;
        info.consoleAdd("Wow. " + item.name + "... Thanks chief!");
        return info;
    }

    public UiInfo printEnterpriseMenu(){
        UiInfo info = new UiInfo();

        info.viewAdd("*------------< E N T E R P R I S E >------------*");
        info.viewAdd(" ");
        if(player.location().enterprise.list().isEmpty()){
            info.viewAdd("You have no enterprise here.");
            info.viewAdd(" ");
        }
        else{
            info.viewAdd("You collected " + player.location().getCurrentVisitProfit() + " on arrival. You own here:");
            info.viewAdd(" ");
            info.print( player.location().enterprise.printList() );
        }
        info.viewAdd("*--------------------< * >---------------------*");
        info.viewAdd(" ");
        info.viewAdd(" ");

        if( player.location().enterprise.forSale.isForSale() ) {
            info.viewAdd("After some inquire you find out that there is one enterprise for sale, its:");
            info.viewAdd(player.location().enterprise.forSale.getName());
            info.viewAdd("It brings around: " + player.location().enterprise.forSale.profit() + " weekly");
            info.viewAdd("Owner wants " + player.location().enterprise.forSale.price() + " for the whole business.");
            info.viewAdd(" ");
            info.viewAdd("Do You want to Buy it? (y/n) ");
        }
        info.viewAdd("99 to back.");

        return info;
    }

    private UiInfo onEnterpriseChoice(String choice) {

        UiInfo info = new UiInfo();

        if(choiceIsBack( choice )){
            stage = STAGE.MAIN_MENU;
            return info;
        }

        if(!player.location().enterprise.forSale.isForSale()) {
            return info;
        }
        if ("y".equals(choice)) {
            Enterprise enterprise = player.location().enterprise.forSale;

            if (player.balance() > enterprise.price()) {
                player.payGold(enterprise.price());
                enterprise.setSold();
                player.location().enterprise.list().add(enterprise);
                info.consoleAdd("Congratulations! You now own that business.");
                return info;
            }

            info.consoleAdd("You don't have the funds to buy it.");
            return info;
        }
        info.consoleAdd("Offer will be here until the end of the week.");
        return info;

    }

    private UiInfo printExpeditionMenu(){
        return expedition.printExpeditionMenu();
    }

    public UiInfo onExpeditionChoice(String choice, Player player){
        UiInfo info = new UiInfo();

        if ( ! "y".equals(choice) ) {
            stage = STAGE.MAIN_MENU;
            info.consoleAdd("You had to go...");
            return info;
        }

        stage = STAGE.EXPEDITION_REPORT;
        boolean win = expedition.challangeMonster(player);

        if(win){
            expedition.rewardPlayer( player );
        }

        return info;
    }

    private UiInfo printExpeditionReport(){
        return expedition.report();
    }

    private UiInfo printRunOrFightMenu(){
        return encounter.printRunOrFightMenu();
    }

    private UiInfo onRunOrFightMenu(String choice){
        UiInfo info = new UiInfo();

        switch (choice) {
            case "1" -> {
                if (player.mercenaryTeam().size() > 0) {
                    stage = STAGE.FIGHT;
                    encounter.resolveFight();
                }
                else
                    info.print(invalidOptionInfo());
            }
            case "2" -> {
                info = encounter.runWithGold();
                arriveAtNewLocation();
            }
            case "3" -> {
                info = encounter.runWithItems();
                arriveAtNewLocation();
            }
            default -> info.print(invalidOptionInfo());

        }
        return info;
    }

    private static UiInfo printRules(){
        UiInfo info = new UiInfo();

        info.viewAdd(" ");
        info.viewAdd("*------------------< W E L C O M E >------------------*");
        info.viewAdd(" ");
        info.viewAdd("This is a adventure-trading game. ");
        info.viewAdd("As a player You impersonate a trader. You have a gift to determine what is true 'value' of any item.");
        info.viewAdd("Market prices that vendors offer might differ from that value. Use it to You're advantage.");
        info.viewAdd("Try to move merchandise from one place to another for profit.");
        info.viewAdd("Roads can be dangerous, sometimes when traveling You will be attacked by animals or bandits.");
        info.viewAdd("You know nothing about fighting so You should hire mercenaries for protection.");
        info.viewAdd("If You equip them properly they will fight fearlessly, but if they lose better run for Your life.");
        info.viewAdd("There are many opportunities in this world to make profit so keep You're eyes open!");
        info.viewAdd(" ");
        info.viewAdd("Good Luck && Have Fun!");
        info.viewAdd(" ");
        info.viewAdd("*-------------------------<*>-------------------------*");
        info.viewAdd(" ");
        info.viewAdd("press enter...");
        info.viewAdd(" ");
        info.viewAdd(" ");
        info.viewAdd("*------------------< R U L E S >------------------*");
        info.viewAdd(" ");
        info.viewAdd("* Start in Town with 3 random items, some gold and one mercenary (bare-handed old Bob).");
        info.viewAdd("* Each time You travel week passe's (new round)");
        info.viewAdd("* Vendors get new offers for You in different locations every week");
        info.viewAdd("* Traveling costs " + MaxValues.TRAVEL_COST + " for You and 'upkeep' for each mercenary.");
        info.viewAdd("* Inventory has no limit.");
        info.viewAdd("* You can buy local enterprises in each location, they produce income weekly. Can't be sold!");
        info.viewAdd("* Game is over if You cant travel, have no items to sell, and no mercenaries ");
        info.viewAdd("* This is sandbox, but 'end game' type achievement is to loot the 'Game Ender Sword'");
        info.viewAdd("* No save game.");
        info.viewAdd(" ");
        info.viewAdd(" ");
        info.viewAdd(" *------------------< F I G H T >------------------*");
        info.viewAdd(" ");
        info.viewAdd("* You can avoid fights by running with either gold or items.");
        info.viewAdd("* If You volunteer for expedition that are available later in the game, fights happen instantly.");
        info.viewAdd("* Each mercenary can carry one item, item stats are added to characters stats.");
        info.viewAdd("* Stats like 'hit' and 'evade' are % chance to hit.");
        info.viewAdd("* In fight attackers pick random targets, and roll for hit. ");
        info.viewAdd("* If they do, defender is rolling for evade.");
        info.viewAdd("* Each roll is additionally enhanced by secret luck factor, that is character specific,");
        info.viewAdd("  and random, max 10%");
        info.viewAdd("* If target gets hit, and fails to evade damage is dealt, also affected by 'luck' roll.");
        info.viewAdd("* If character kill's enemy, he lvl's up, gets small hit, evade, and health boost");
        info.viewAdd("* On character death items end up in player's inventory.");
        info.viewAdd("* When fight is won You get random reward, when lost, You lose almost all You're belongings,");
        info.viewAdd("  You might save some gold, and random inventory item. You CAN'T lose enterprise You own.");
        info.viewAdd(" ");
        info.viewAdd(" ");

        info.print(printEnemyStats());

        return info;
    }

    private static UiInfo printEnemyStats(){
        UiInfo info = new UiInfo();
        info.viewAdd("Enemies statistics:");
        info.viewAdd("(accessible later from main menu)");
        info.viewAdd(" ");
        info.print( Enemies.printEnemiesInfo() );
        return info;
    }

    public void isGameOver(){
        if(stage == STAGE.MAIN_MENU && player.gameOver()){
            stage = STAGE.GAME_OVER;
        }
    }

    public UiInfo printGameOver(){
        UiInfo info = new UiInfo();
        info.viewAdd(" ");
        info.viewAdd(" ");
        info.viewAdd(" ");
        info.viewAdd("*--------- < G A M E  O V E R > ---------*");
        info.viewAdd(" ");
        info.viewAdd("Market was to taught for You this time...");
        info.viewAdd("After " + round + " weeks You're situation is hopeless.");
        info.viewAdd(" ");
        info.viewAdd("*----------------- < > ------------------*");
        info.viewAdd(" ");
        info.viewAdd("restart and try again...");
        info.viewAdd(" ");
        return info;
    }

    public UiInfo printQuitMenu(){
        UiInfo info = new UiInfo();
        info.consoleAdd("Are You sure You want to Quit game? (y/n)");
        return info;
    }

    private UiInfo onQuitMenuChoice(String choice){

        UiInfo info = new UiInfo();
        if( choice.equals( "y" ) ) {
            stage = STAGE.GAME_OVER;
            info.consoleAdd( "cya!" );
        }
        else{
            stage = STAGE.MAIN_MENU;
            info.consoleAdd( "No means no!" );
        }

        return info;
    }

    private boolean choiceIsBack(String choice){
        return choice.equals("99") || choice.equals("");
    }

    private UiInfo invalidOptionInfo(){
        UiInfo info = new UiInfo();
        info.consoleAdd("Pick option within list, 99 to back.");
        return info;
    }

    private UiInfo notEnoughtGoldInfo() {
        UiInfo info = new UiInfo();
        info.consoleAdd("Not enought gold.");
        return info;
    }

    public String printGeneralInfo(){
        String line;
        line  =   " LOCATION: " + player.location().name +       "  |  ";
        line +=        "GOLD: " + player.balance() + " (" + player.calculateTravleCost() + " travel)  |  ";
        line +=       "ITEMS: " + player.inventory().size() + " (" + player.inventory().inventoryValue() + " gold)  |  ";
        line +=        "TEAM: " + player.mercenaryTeam().size() +  "  |  ";
        line +=        "WEEK: " + round + "  |";
        return line;
    }

}



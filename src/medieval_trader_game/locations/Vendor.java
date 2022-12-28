package medieval_trader_game.locations;

import medieval_trader_game.Dice;
import medieval_trader_game.items.Item;
import medieval_trader_game.MaxValues;
import medieval_trader_game.mercenaries.Mercenary;
import medieval_trader_game.mercenaries.MercenaryTeam;
import medieval_trader_game.items.Inventory;
import medieval_trader_game.player.Player;

public class Vendor {
    private Inventory inventory;
    private MercenaryTeam mercForHire;

    public Vendor(){
        inventory = new Inventory();
        mercForHire = new MercenaryTeam();

        int itemCount = Dice.nextInt(MaxValues.MIN_STOCK_WEAPONS,MaxValues.MAX_STOCK_WEAPONS);

        for(int i=0; i<itemCount; i++){
            inventory.addItem(new Item());
        }

        for(int i = 0; i<MaxValues.MAX_STOCK_MERCENARIES; i++){
            mercForHire.addMercenary(new Mercenary());
        }
    }

    public Inventory inventory(){
        return inventory;
    }

    public MercenaryTeam mercForHire(){
        return mercForHire;
    }

    public void refreshAllOffers() {
        refreshItems();
        createNewOfferingForItems();
        refreshMercenaries();
    }

    private void refreshItems() {

        Inventory freshInventory = new Inventory();
        do {
            freshInventory.addItem(new Item());
        } while (freshInventory.size() < MaxValues.MAX_STOCK_WEAPONS);

        inventory = freshInventory;
    }

    private void refreshMercenaries(){
        mercForHire = new MercenaryTeam();
        do{
            mercForHire.addMercenary(new Mercenary());
        }while ( mercForHire.size() < MaxValues.MAX_STOCK_MERCENARIES);
    }

    public void makePlayerAnOffer(Player player){
        if(player.inventory().size() > 0) {
            for (int pos = 0; pos < player.inventory().size(); pos++) {
                player.inventory().itemAt(pos).newOfferForPlayersItem();
            }
        }

        if(player.mercenaryTeam().size() > 0) {
            for (int pos = 0; pos < player.mercenaryTeam().size(); pos++) {
                if (player.mercenaryTeam().at(pos).hasItem()) {
                    player.mercenaryTeam().at(pos).equippedItem.newOfferForPlayersItem();
                }
            }
        }

    }

    public void createNewOfferingForItems(){
        for(int pos = 0; pos < inventory.size(); pos++){
            inventory.itemAt(pos).newOfferForVendorsItem();
        }
    }
}

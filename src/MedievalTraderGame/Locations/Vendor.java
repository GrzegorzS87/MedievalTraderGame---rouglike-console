package MedievalTraderGame.Locations;

import MedievalTraderGame.Dice;
import MedievalTraderGame.Items.Item;
import MedievalTraderGame.MaxValues;
import MedievalTraderGame.Mercenaries.Mercenary;
import MedievalTraderGame.Mercenaries.MercenaryTeam;
import MedievalTraderGame.Items.Inventory;
import MedievalTraderGame.Player.Player;

public class Vendor {
    public Inventory inventory;
    public MercenaryTeam mercForHire;

    public Vendor(){
        inventory = new Inventory();
        mercForHire = new MercenaryTeam();

        int itemCount = Dice.nextInt(MaxValues.minStockWeapons,MaxValues.maxStockWeapons);

        for(int i=0; i<itemCount; i++){
            inventory.addItem(new Item());
        }

        for(int i=0; i<MaxValues.maxStockMercenaries; i++){
            mercForHire.addMercenary(new Mercenary());
        }
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
        } while (freshInventory.size() < MaxValues.maxStockWeapons);

        inventory = freshInventory;
    }

    private void refreshMercenaries(){
        mercForHire = new MercenaryTeam();
        do{
            mercForHire.addMercenary(new Mercenary());
        }while ( mercForHire.size() < MaxValues.maxStockMercenaries );
    }

    public void makePlayerAnOffer(Player player){
        if(player.inventory.size() > 0) {
            for (int pos = 0; pos < player.inventory.size(); pos++) {
                player.inventory.itemAt(pos).newOfferForPlayersItem();
            }
        }

        if(player.mercenaryTeam.size() > 0) {
            for (int pos = 0; pos < player.mercenaryTeam.size(); pos++) {
                if (player.mercenaryTeam.at(pos).hasItem()) {
                    player.mercenaryTeam.at(pos).equippedItem.newOfferForPlayersItem();
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

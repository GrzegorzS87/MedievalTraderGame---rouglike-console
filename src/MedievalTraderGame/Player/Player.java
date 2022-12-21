package MedievalTraderGame.Player;

import MedievalTraderGame.Interface.UiInfo;
import MedievalTraderGame.Items.Inventory;
import MedievalTraderGame.Items.Item;
import MedievalTraderGame.Locations.Location;
import MedievalTraderGame.MaxValues;
import MedievalTraderGame.Mercenaries.Mercenary;
import MedievalTraderGame.Mercenaries.MercenaryTeam;

public class Player {
    private int gold = MaxValues.startingGold;
    public static final int travleCost = 25;
    public int balance(){return gold;}
    public Location location;
    public Inventory inventory;
    public MercenaryTeam mercenaryTeam;
    public int mercToManageId = 0;
    public UiInfo info;


    public Player(Location startIn){
        mercenaryTeam = new MercenaryTeam();
        inventory = new Inventory();

        int targetValue = MaxValues.inventortStartingValue;
        int currentValue = 0;

        do{
            Item item = new Item();
            currentValue += item.value;
            inventory.addItem( item );
        }
        while( currentValue < targetValue );

        location = startIn;
        location.vendor.refreshAllOffers();
        location.vendor.makePlayerAnOffer(this);
        mercenaryTeam.addMercenary(Mercenary.basicBob());
    }

    public void setGold(int coins){
        gold = coins;
    }

    public void travleTo(Location destination, int round){
        payTravleCost();
        location.leave(round);
        location = destination;
    }


    public void buyItem( Item item){
        int price = item.getOfferPrice();
        item.setPricePlayerPaid( price );
        payGold( price );
        inventory.addItem( item );
        location.vendor.inventory.removeItem( item );
    }

    public boolean cantAffordTravle() {
        return balance() < calculateTravleCost();
    }

    public void payTravleCost() {
        payGold(calculateTravleCost());
    }

    public int calculateTravleCost(){
        int cost = travleCost;
        for (int i = 0; i< mercenaryTeam.size(); i++){
            cost += mercenaryTeam.at(i).getUpkeepCost();
        }
        return cost;
    }

    public void addGold(int ammount){
        gold += ammount;
    }

    public void payGold(int ammount){
        gold -= ammount;
    }

    private boolean nothingToSell(){
        boolean inventoryisEmpty = inventory.isEmpty();
        boolean noItemOnMerc = true;

        if (mercenaryTeam.size()>0) {
            for (int i = 0; i < mercenaryTeam.size(); i++) {
                if (mercenaryTeam.at(i).hasItem())
                    noItemOnMerc = false;
            }
        }

        return inventoryisEmpty && noItemOnMerc;
    }

    public boolean GameIsOver(){
       return ( cantAffordTravle() && nothingToSell() && mercenaryTeam.size() == 0);
    }

    public boolean ownsEnterpriseHere() {
        return location.enterprise.playerOwnsEnterpriseHere();
    }
}




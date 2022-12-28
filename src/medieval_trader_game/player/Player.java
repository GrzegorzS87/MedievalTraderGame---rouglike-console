package medieval_trader_game.player;

import medieval_trader_game.console_interface.UiInfo;
import medieval_trader_game.items.Inventory;
import medieval_trader_game.items.Item;
import medieval_trader_game.locations.Location;
import medieval_trader_game.MaxValues;
import medieval_trader_game.mercenaries.Mercenary;
import medieval_trader_game.mercenaries.MercenaryTeam;

public class Player {
    private int gold = MaxValues.STARTING_GOLD;
    public int balance(){return gold;}
    private Location location;
    private Inventory inventory;
    private MercenaryTeam mercenaryTeam;
    public int mercToManageId = 0;
    public UiInfo info;


    public Player(Location startIn){
        mercenaryTeam = new MercenaryTeam();
        inventory = new Inventory();

        int targetValue = MaxValues.INVENTORY_STARTING_VALUE;
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

    public Location location(){
        return location;
    }

    public Inventory inventory(){
        return inventory;
    }

    public MercenaryTeam mercenaryTeam(){
        return mercenaryTeam;
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
        location.vendor.inventory().removeItem( item );
    }

    public boolean cantAffordTravle() {
        return balance() < calculateTravleCost();
    }

    public void payTravleCost() {
        payGold(calculateTravleCost());
    }

    public int calculateTravleCost(){
        int cost = MaxValues.TRAVEL_COST;
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

    public boolean gameOver(){
       return ( cantAffordTravle() && nothingToSell() && mercenaryTeam.size() == 0);
    }

    public boolean ownsEnterpriseHere() {
        return location.enterprise.playerOwnsEnterpriseHere();
    }
}




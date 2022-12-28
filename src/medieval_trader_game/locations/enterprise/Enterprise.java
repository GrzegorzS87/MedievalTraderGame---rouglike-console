package medieval_trader_game.locations.enterprise;

import medieval_trader_game.Dice;
import medieval_trader_game.MaxValues;

public enum Enterprise {

    Herbary(500),
    Pawnshop(500),
    Grocery(500),
    Stall(500),
    Butcher_shop(500),
    Pottery(500),
    Shop(500),
    Shoemaker(500),
    Carpenter(500),
    Tabaco_shop(700),
    Furniture_store(700),
    Leather_works(700),
    Butcher(700),
    Tailor(700),
    Cooperage(700),
    Smithy(1000),
    Weapon_smith(1000),
    Bowyer(1000),
    Farm(1000),
    Tavern(1000),
    Mill(1000),
    Stonemason(1000),
    Jewellery(1000),
    Bakery(1000),
    Brewery(1000),
    Workshop(1000),
    Orchard(2000),
    Winery(2000),
    Warehouse(2000),
    Manufacture(3000),
    Forge(3000),
    Metal_works(3000),
    Wood_works(3000),
    Quarry(3000),
    Gold_mine(5000),
    Copper_mine(5000),
    Iron_mine(5000);

    private final String name = toString().replace("_", " ");
    private final int worth;
    private final int income;
    private int price;
    private boolean sold = false; // one of each possible to exist

    Enterprise(int worth) {
        this.worth = worth;
        this.income = (int) ((double) worth * MaxValues.ENTERPRISE_INCOME);
        this.price = worth;
    }

    public int price(){
        return price;
    }
    public int profit(){
        return income;
    }

    public static Enterprise random() {
        Enterprise[] type = values();
        return type[Dice.nextInt(type.length)];
    }

    public void newOffer(){
        int maxPrice = (int)((double)worth * 1.15d);
        int minPrice = (int)((double)worth * 0.85d);

        price = Dice.nextInt(minPrice,maxPrice);
    }

    public int generateProfit( int weeks){
        int maxProfit = (int)((double) income * 1.1d) * weeks ;
        int minProfit = (int)((double) income * 0.9d) * weeks ;
        return Dice.nextInt(minProfit,maxProfit);
    }

    public String getName(){
        return name;
    }

    public boolean isForSale(){
        return !sold;
    }

    public void setSold(){
        sold = true;
    }
}

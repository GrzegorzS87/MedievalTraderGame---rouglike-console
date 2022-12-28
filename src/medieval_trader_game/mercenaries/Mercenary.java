package medieval_trader_game.mercenaries;

import medieval_trader_game.Dice;
import medieval_trader_game.console_interface.UiInfo;
import medieval_trader_game.items.Item;
import medieval_trader_game.MaxValues;

public class Mercenary {
    public final String name;
    private int damage;
    private int hit;
    private int evade;    //maybe some armor incoming
    private int range = 0;
    private int lvl = 1;
    private final int luck;
    private boolean canParry;
    private int health;
    private int wounds = 0;
    private final int hirePrice;
    private final int upkeepCost;
    public Item equippedItem;
    private boolean hasItemEquipped = false;

    public Mercenary(){
        name   = randomName();
        hit    = Dice.nextIntLogDistribution(MaxValues.MIN_MERC_HIT, MaxValues.MAX_MERC_HIT);
        health = Dice.nextIntLogDistribution(MaxValues.MIN_HEALTH, MaxValues.MAX_HEALTH);
        damage = Dice.nextIntLogDistribution(MaxValues.MIN_MERC_DAMAGE, MaxValues.MAX_MERC_DAMAGE);
        evade  = Dice.nextInt( 1, MaxValues.MAX_MERC_EVADE); //has to be positive to be used by Random nextInt() method
        luck   = Dice.nextInt( 1, MaxValues.LUCK);
        canParry   = true;
        hirePrice  = getHirePrice();
        upkeepCost = (int) ((double) hirePrice * MaxValues.MERC_UPKEEP);
    }

    public Mercenary( int damage, int hit, int evade, int health, String name){
        this.damage = damage;
        this.hit = hit;
        this.evade = evade;
        this.health = health;
        this.canParry = true;
        this.hirePrice = getHirePrice();
        this.upkeepCost = (int) ((double) hirePrice / 10.d);
        this.name = name;
        this.luck = Dice.nextInt(1, MaxValues.LUCK);
    }

    public String printInlineInfo(){
        String line = name + " ( dmg: " + damage + " hit: " + hit;
        if( range > 0 ) line += " rng: " + range;
        line += " ev: " + evade + " hp: " + health + " )";
        return line;
    }

    public int getHit(){
        return hit;
    }

    public int getEvade(){
        return evade;
    }

    public boolean canParry(){
        return canParry;
    }

    public void takeDamage(int dmg){
        wounds += dmg;
    }

    public boolean isDead(){
        return health <= wounds;
    }

    public void healUp(){
        wounds = 0;
    }

    public int damage(){
        return damage;
    }

    public int getHealth(){
        return health-wounds;

    }

    public void levelUp(){
        if(lvl<MaxValues.MAX_LVL){
            lvl++;
            health += 5;
            if(hit<MaxValues.MAX_MERC_HIT){
                hit++;
            }
            else {
                health += 5;
            }

            if(evade<MaxValues.MAX_MERC_EVADE) {
                evade++;
            }
            else{
                health+= 5;
            }
        }
    }

    public boolean hasItem(){
        return hasItemEquipped;
    }

    public void equip(Item item){
        equippedItem = item;
        hasItemEquipped = true;
        damage += equippedItem.damage;
        hit += item.hit;
        range = equippedItem.range;
        canParry = equippedItem.allowsParry;
    }

    public Item unEquipItem(){
        hasItemEquipped = false;
        damage -= equippedItem.damage;
        hit -= equippedItem.hit;
        range = 0;
        canParry = true;
        return equippedItem;
    }

    public String randomName(){
        return MercenaryName.random() + " " + MercenaryNickname.random();
    }

    public UiInfo printInfo(){

        UiInfo info = new UiInfo();
        info.viewAdd("       Name: " + name );
        info.viewAdd("      level: " + lvl);
        info.viewAdd("     damage: " + damage);
        info.viewAdd("        hit: " + hit);
        info.viewAdd("      evade: " + evade);
        info.viewAdd("     health: " + health);
        info.viewAdd("     upkeep: " + upkeepCost );

        if(hasItem()){
            info.viewAdd(" He's using:");
            info.print(equippedItem.printInfo());
            info.viewAdd(" ");
        }

        return info;
    }

    public String printItemInfo(){

        if(hasItem()){
            return equippedItem.inlineInfo();
        }
        return "has no item";

    }

    public UiInfo printEnemyInfo() {

        UiInfo info = new UiInfo();
        info.viewAdd(" ");
        info.viewAdd("       Name: " + name);
        info.viewAdd(" est. power: " + getHirePrice());
        info.viewAdd("     damage: " + damage);
        info.viewAdd("      hit %: " + hit);
        info.viewAdd("    evade %: " + evade);
        info.viewAdd("     health: " + health);
        info.viewAdd(" ");

        return info;
    }

    public int getHirePrice() {
        return (((int)(((double)( damage * hit )) / 100.0d )) + ( health ) + (int)((double)(health * evade)/100.0d) );
    }

    public int power(){
        return getHirePrice();
    };

    public int getUpkeepCost() {
        return upkeepCost;
    }

    public static Mercenary basicBob(){

        return new Mercenary( 40, 40, 10,200, "Uncle Bob");
    }

    public int getItemValue(){
        if(hasItemEquipped){
            return equippedItem.value;
        }
        else {
            return 0;
        }
    }

    public int roll(){
        return Dice.nextInt(luck);
    }

    public boolean isMelee() {
        return !(range > 0);
    }
}

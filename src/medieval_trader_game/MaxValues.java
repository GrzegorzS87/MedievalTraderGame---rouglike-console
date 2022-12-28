package medieval_trader_game;

public class MaxValues {

    private MaxValues(){

    }

    //weapon
    public static final int MAX_MELEE_WEAPON_DAMAGE = 200;
    public static final int MAX_RANGED_WEAPON_DAMAGE = 150;
    public static final int MIN_WEAPON_DAMAGE = 20;
    public static final int MAX_MELEE_WEAPON_HIT = 60;
    public static final int MIN_WEAPON_HIT = 10;
    public static final int MAX_RANGED_WEAPON_HIT = 50;
    public static final int MAX_RANGE = 300;
    public static final int MIN_RANGE = 100;
    public static final int RANGED_WEAPON_RARITY = 40;
    public static final int SECOND_PREFIX_CHANCE = 20;

    //mercenary
    public static final int MAX_HEALTH = 1200;
    public static final int MIN_HEALTH = 200;
    public static final int MAX_MERC_DAMAGE = 80;
    public static final int MIN_MERC_DAMAGE = 20;
    public static final int MAX_LVL = 1000;
    public static final int LUCK = 10;
    public static final int MAX_MERC_EVADE = 25;
    public static final int MAX_MERC_HIT = 70;
    public static final int MIN_MERC_HIT = 15;
    public static final double MERC_UPKEEP = 0.05; // % of hire cost

    //vendor
    public static final int MAX_STOCK_WEAPONS = 13;
    public static final int MIN_STOCK_WEAPONS = 10;
    public static final int MAX_STOCK_MERCENARIES = 6;
    public static final double MAX_VENDOR_OFFER_FACTOR = 1.2d;
    public static final double MIN_VENDOR_OFFER_FACTOR = 0.7d;
    public static final double MAX_PLAYER_OFFER_FACTOR = 1.3d;
    public static final double MIN_PLAYER_OFFER_FACTOR = 0.8d;

    //map
    public static final int ENCOUNTER_CHANCE = 75;

    //enterprise
    public static final double ENTERPRISE_INCOME = 0.1d;

    //player
    public static final int STARTING_GOLD = 20000000;
    public static final int MAX_FIGHT_ROUNDS = 25;
    public static final int TRAVEL_COST = 25;
    public static final int INVENTORY_STARTING_VALUE = 1000;

    //encounter
    public static final int RANGE_ROUNDS = 2;
    public static final double ENEMY_MIN_POWER_FACTOR = 0.4d;
    public static final double ENEMY_MAX_POWER_FACTOR = 1.0d;

    //window
    public static final int WINDOW_WIDTH = 1050;
    public static final int WINDOW_HEIGHT = 670;

    //expedition
    public static final int EXPEDITION_REWARD_GOLD = 5000;


}

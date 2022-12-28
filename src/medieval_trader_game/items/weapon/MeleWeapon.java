package medieval_trader_game.items.weapon;

import medieval_trader_game.Dice;

public enum MeleWeapon {
    SWORD("Sword"),
    AXE("Axe"),
    HAMMER("Hammer"),
    SCYTHE("Scythe"),
    PIKE("Pike"),
    PILIUM("Pilium"),
    GLADIUS("Gladius"),
    LANCE("Lance"),
    MACE("Mace"),
    PICKAXE("Pickaxe"),
    PAN("Pan"),
    STICK("Stick"),
    TOOTH_PICk("Tooth Pick"),
    CLUB("Club"),
    KNIFE("Knife"),
    SABRE("Sabre"),
    MACHETE("Machete"),
    HALBARD("Halbard"),
    BAT("Bat"),
    FALCATA("Falcata"),
    LONGSWORD("Longsword"),
    ZWEIHANDER("Zweihander"),
    CLAYMORE("Claymore"),
    RAPIER("Rapier"),
    DIRK("Dirk"),
    Kris("Kris"),
    TRIDENT("Trident"),
    SPEAR("Spear"),
    SPOON("Spoon");

    private final String text;

    MeleWeapon(String txt) {
        this.text = txt;
    }

    public static String random(){
        MeleWeapon[] type = values();
        return type[Dice.nextInt(type.length)].text;
    }
}

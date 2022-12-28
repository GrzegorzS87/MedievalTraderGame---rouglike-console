package medieval_trader_game.items.weapon;

import medieval_trader_game.Dice;

public enum RangedWeapon {
    BOW("Bow"),
    CROSSBOW("Crossbow"),
    CATAPULT("Pocket Catapult"),
    SLINGSHOT("Slingshot"),
    BALLISTA("Pocket Ballista"),
    JAVELIN("Javelin"),
    LONGBOW("Longbow"),
    DART("Dart"),
    TOMAHAWK("Tomahawk"),
    BOOMERANG("Boomerang"),
    MOM_JOKE("'Mom' Joke"),
    REFLEX_BOW("Reflex Bow"),
    THROWING_KNIFE("Throwing Knife");

    private final String text;

    RangedWeapon(String txt) {
        this.text = txt;
    }

    public static String random(){
        RangedWeapon[] type = values();
        return type[Dice.nextInt(type.length)].text;
    }

}

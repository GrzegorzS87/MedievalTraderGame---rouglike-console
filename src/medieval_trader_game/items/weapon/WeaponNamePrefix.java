package medieval_trader_game.items.weapon;

import medieval_trader_game.Dice;

public enum  WeaponNamePrefix {
    HANDY("Handy"),
    DEADLY("Deadly"),
    DULL("Dull"),
    BLUNT("Blunt"),
    CURVED("Curved"),
    TWISTED("Twisted"),
    WARPED("Warped"),
    WELL_MADE("Well Made"),
    HIGH_QUALITY("High Quality"),
    lOW_QUALITY("Low Quality"),
    FUNNY_LOOKING("Funny Looking"),
    BARELY_LEGAL("Barely Legal"),
    SHARPENED("Sharpened"),
    RUSTY("Rusty"),
    DUSTY("Dusty"),
    SLIGHTLY_MAGICAL("Slightly Magical"),
    ANCIENT("Ancient"),
    COOL("Cool"),
    ADJUSTABLE("Adjustable"),
    DOUBLE_HANDED("Double Handed"),
    RED("Red"),
    GREEN("Green"),
    BLOODY("Bloody"),
    MYTHIC("Mythic"),
    BROKEN("Broken"),
    FRAGILE("Fragile"),
    STICKY("Sticky"),
    TEMPERED("Tempered"),
    LEFT_HANDED("Left Handed"),
    CHINESE("Chinese"),
    GERMAN("German"),
    OVERPRICED("Overpriced"),
    PROPER("Proper"),
    DAMASCUS("Damascus"),
    MILITARY("Military"),
    IMPROVISED("Improvised"),
    SLIPPERY("Slippery"),
    SECOND_HAND("Second Hand"),
    BADASS("Badass"),
    SCARY("Scary"),
    LEGENDARY("Legendary"),
    SHARP("Sharp"),
    HEAVY("Heavy"),
    LIGHT("Light"),
    STRAIGHT("Straight"),
    DOUBLE_EDGED("Double Edged"),
    CRAZY("Crazy"),
    SPIKED("Spiked"),
    POINTY("Pointy"),
    BURIAL("Burial"),
    FUNKY("Funky"),
    OLD("Old");

    private final String text;

    WeaponNamePrefix(String txt) {
        this.text = txt;
    }

    public static String random(){
        WeaponNamePrefix[] type = values();
        return type[Dice.nextInt(type.length)].text;
    }
}

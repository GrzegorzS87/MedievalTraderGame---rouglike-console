package medieval_trader_game.items.weapon;

import medieval_trader_game.Dice;

public enum Material {

    wood(30),
    bone(40),
    horn(50),
    bronz(60),
    iron(70),
    steel(90),
    composite(110),
    meteorite(150),
    obsidian( 200),
    crystal(300);


    public final int valueFactor;

    Material(int valueFactor) {
        this.valueFactor = valueFactor;
    }

    public static Material random(){
        Material[] type = values();
        return type[Dice.nextInt(type.length)];
    }
}

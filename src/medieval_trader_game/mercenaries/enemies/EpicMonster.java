package medieval_trader_game.mercenaries.enemies;

import medieval_trader_game.Dice;
import medieval_trader_game.mercenaries.Mercenary;

// This enum helps organize creation of enemies. It provides constructor parameters for Mercenary class,
// public create(EpicMonster) returns Mercenary class enemy of predefined statistics.
// Public method random() creates random epic monster
public enum EpicMonster {

    WYVERN( 250, 65, 20, 3000, "Wyvern" ),
    MINOTAUR ( 300, 70, 15, 4500, "Minotaur" ),
    JELLY_CUBE ( 400, 70, 5, 6000, "Jelly Cube (very scary)" ),
    MEDUSA ( 500, 70, 10, 8000, "Medusa" ),
    DRAGON ( 1000, 70, 10, 10000, "Dragon" );

    private final int damage;
    private final int hit;
    private final int evade;
    private final int health;
    public final int power;
    private final String name;

    EpicMonster(int damage, int hit, int evade, int health, String name) {
        this.damage = damage;
        this.hit = hit;
        this.evade = evade;
        this.health = health;
        this.name = name;
        this.power = (int)((double)( damage * hit ) / 100.0d ) + health + (int)((double)(health * evade)/100.0d);
    }

    public static Mercenary create(EpicMonster monster){
        return new Mercenary(monster.damage, monster.hit, monster.evade, monster.health, monster.name);
    }

    public static Mercenary random() {
        EpicMonster[] type = values();
        return create( type[Dice.nextInt(type.length)] );
    }
}

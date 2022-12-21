package MedievalTraderGame.Mercenaries.Enemies;

import MedievalTraderGame.Mercenaries.Mercenary;

// This enum helps organize creation of enemies. It provides constructor parameters for Mercenary class,
// public create(Animal.x) returns Mercenary class enemy of predefined statistics and name
public enum Animal {

    BEAST       (250, 65, 10, 3000, "Horned Beast"),
    ENT         (200, 60, 10, 2500, "Ent"),
    TROLL       (180, 60, 10, 2000, "Troll"),
    SPIDER      (160, 60, 10, 1500, "Giant Spider (hairy asf)"),
    WASP        (140,60,15,1200,"Gigantic Wasp"),
    REPTILIAN   (120,60,15,1000,"Reptilian"),
    BEAR        (100, 60, 15, 850, "Mature Bear"),
    YOUNG_BEAR  (80,60,15,700,"Young Bear"),
    SNARG       (70,60,15,550,"Snarg"),
    ALPHA_WOLF  (65,55,15,450,"Alpha Wolf"),
    WOLF        (45, 55, 15, 350, "Wolf"),
    BOAR        (35, 55, 15, 300, "Boar"),
    SNAKE       (35,55,50,200,"Giant Snake"),
    FOX         (30, 55, 20, 160, "Fox"),
    DOG         (25, 50, 15, 120, "Wild Dog"),
    MAD_OWL     (25,50,15,80,"Mad Owl"),
    ANGRY_BAT   (20, 50, 60, 60, "Angry Bat"),
    RAT         (20, 50, 15, 40, "Huge Rat");

    private final int damage;
    private final int hit;
    private final int evade;
    private final int health;
    private final String name;
    public final int power;

    Animal(int damage, int hit, int evade, int health, String name) {
        this.damage = damage;
        this.hit = hit;
        this.evade = evade;
        this.health = health;
        this.name = name;
        this.power = (int)((double)( damage * hit ) / 100.0d ) + health + (int)((double)(health * evade)/100.0d);
    }

    public static Mercenary create(Animal animal) {
        return new Mercenary(animal.damage, animal.hit, animal.evade, animal.health, animal.name);
    }

}

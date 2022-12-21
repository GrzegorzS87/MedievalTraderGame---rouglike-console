package MedievalTraderGame.Mercenaries.Enemies;

import MedievalTraderGame.Mercenaries.Mercenary;
import MedievalTraderGame.Mercenaries.MercenaryName;

// This enum helps organize creation of enemies. It provides constructor parameters for Mercenary class,
// public create(Bandit) returns Mercenary class enemy of predefined statistics.
public enum Bandit {

    OGRE_CHIEFTAN    ( 250, 60, 15, 2500, "Ogre Chieftan" ),
    OGRE_WETERAN     ( 200, 60, 15, 2000, "Ogre Weteran" ),
    OGRE_WARRIOR     ( 190, 60, 15, 1600, "Ogre Warrior" ),
    OGRE             ( 180, 60, 15, 1200, "Ogre" ),
    ORK_MERCENARY    ( 170, 60, 15, 1000, "Ork Mercenary"),
    ORK_WARRIOR      ( 160, 60, 15, 900, "Ork Warrior" ),
    ORK_BRAWLER      ( 150,  60, 15, 800, "Ork Brawler"),
    ORK_THUG         ( 140,  60, 15, 700, "Ork Thug" ),
    KOBOLD_WARRIOR   ( 130,  60, 15, 700, "Kobold Warrior" ),
    MEAN_KOBOLD      ( 120,  60, 15, 700, "Mean Kobold" ),
    KOBOLD           ( 110,  55, 15, 700, "Kobold" ),
    OUTLAW           ( 90,  55, 15, 500, "Wanted Outlaw"),
    BANDIT           ( 70,  55, 15, 400, "Bandit"),
    CUTTHROAT        ( 50,  55, 15, 300, "Cutthroat" ),
    DRUNK            ( 80,  20, 30, 10, "Drunk" );


    private final int damage;
    private final int hit;
    private final int evade;
    private final int health;
    private final String name;
    public final int power;

    Bandit(int damage, int hit, int evade, int health, String name) {
        this.damage = damage;
        this.hit = hit;
        this.evade = evade;
        this.health = health;
        this.name = name;
        this.power = (int)((double)( damage * hit ) / 100.0d ) + health + (int)((double)(health * evade)/100.0d);
    }

    public static Mercenary create(Bandit bandit){
        String randomName = bandit.name + " " + MercenaryName.random();
        return new Mercenary(bandit.damage, bandit.hit, bandit.evade, bandit.health, randomName);
    }
}

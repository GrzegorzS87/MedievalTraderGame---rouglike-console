package MedievalTraderGame.Items;

import MedievalTraderGame.Dice;
import MedievalTraderGame.Interface.UiInfo;
import MedievalTraderGame.Items.Weapon.Material;
import MedievalTraderGame.Items.Weapon.MeleWeapon;
import MedievalTraderGame.Items.Weapon.RangedWeapon;
import MedievalTraderGame.Items.Weapon.WeaponNamePrefix;
import MedievalTraderGame.MaxValues;

public class Item {

    public final int damage;
    public final int range;                         // range = 0 is melee weapon that allows parry
    public final int hit;
    public final boolean allowsParry;

    public final Material isMadeOf;
    public final int value;
    public final String name;                       // name always random

    private int pricePlayerPaid;
    private int offerPrice;


    public Item() {

        range = newRange();
        if (range > 0) {
            damage = Dice.nextIntLogDistribution(MaxValues.minWeaponDamage, MaxValues.maxRangedWeaponDamage);
            hit = Dice.nextIntLogDistribution(MaxValues.minWeaponHit, MaxValues.maxRangedWeaponHit);
            allowsParry = false;

        } else {
            damage = Dice.nextIntLogDistribution(MaxValues.minWeaponDamage, MaxValues.maxMeleeWeaponDamage);
            hit = Dice.nextIntLogDistribution(MaxValues.minWeaponHit, MaxValues.maxMeleeWeaponHit);
            allowsParry = true;
        }

        isMadeOf = Material.random();
        value = evaluatePrice();
        name = randomName();

        setPricePlayerPaid(value);
        newOfferForPlayersItem();
    }

    public Item( String name, int damage, int hit, int range ){
        this.damage = damage;
        this.range = range;
        this.hit = hit;
        this.allowsParry = true;
        this.isMadeOf = Material.crystal;
        this.value = evaluatePrice() + 1000;
        this.name = name;
        this.pricePlayerPaid = 0;
        this.offerPrice = 1;
    }

    private int evaluatePrice() {
        int value;
        value = damage * 3;
        value += (int) ((double) range * 0.1d);
        value += isMadeOf.valueFactor;

        return value;
    }

    private int newRange() {
        int newWeaponRange;

        int roll = Dice.nextInt(100);

        if (roll > MaxValues.rangedWeaponRarity) //some melee, some ranged
            newWeaponRange = 0;
        else
            newWeaponRange = Dice.nextInt(MaxValues.minRange, MaxValues.maxRange);

        return newWeaponRange;
    }

    public UiInfo printInfo() {
        UiInfo info = new UiInfo();
        info.viewAdd("       name: " + name);
        info.viewAdd("     damage: " + damage);
        info.viewAdd("        hit: " + hit);
        info.viewAdd("      range: " + range);
        info.viewAdd("   material: " + isMadeOf);
        info.viewAdd("  can parry: " + allowsParry);
        info.viewAdd("      value: " + value);

        return info;
    }
    public String inlineInfo(){
        String line = name;
        line += " ( dmg: " + damage + " hit: " + hit + " range: " + range +" )";

        return line;
    }

    private String randomName() {
        String newName;
        String temp;

        newName = WeaponNamePrefix.random();

        if (Dice.nextInt(100) > MaxValues.secondPrefixChance ) { //sometimes multiple prefix
            temp = WeaponNamePrefix.random();
            if (!newName.contains(temp)) {
                newName += " ";
                newName += temp;
            }

        }
        newName += " ";

        if (allowsParry) //only melee weapons allow parry
            newName += MeleWeapon.random();
        else
            newName += RangedWeapon.random();

        return newName;
    }

    public int getPricePlayerPaid() {
        return pricePlayerPaid;
    }

    public int getOfferPrice() {
        return offerPrice;
    }

    public void newOfferForPlayersItem() {
        double randomOffer = (double) value * Dice.nextDouble(MaxValues.minPlayerOfferFactor, MaxValues.maxPlayerOfferFactor);
        setOfferPrice((int) randomOffer);
    }

    public void newOfferForVendorsItem() {
        double randomOffer = (double) value * Dice.nextDouble(MaxValues.minVendorOfferFactor, MaxValues.maxVendorOfferFactor);
        setOfferPrice((int) randomOffer);
    }

    public void setPricePlayerPaid(int newPrice) {
        pricePlayerPaid = newPrice;
    }

    public void setOfferPrice(int newPrice) {
        offerPrice = newPrice;
    }

}

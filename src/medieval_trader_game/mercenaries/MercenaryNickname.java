package medieval_trader_game.mercenaries;

import medieval_trader_game.Dice;

public enum MercenaryNickname {

    SMITH("Smith"),
    GRIMM("Grimm"),
    SMAILY("Smaily"),
    RAMBO("Rambo"),
    SKYWALKER("Skywalker"),
    STORMBORN("Stormborn"),
    JOHNSON("Johnson"),
    NAKRZYNSKI("Nakrzynski"),
    DREBIN("Drebin"),
    BADLUCK("Badluck"),
    LUCKY("Lucky"),
    MCDONALD("McDonald"),
    EVENSEN("Evensen"),
    ALBERTSEN("Albertsen"),
    ALFSON("Alfson"),
    BERNSTEN("Bernsten"),
    HANSEN("Hansen"),
    MOCK("Mock"),
    COWARDSON("Cowardson"),
    SEGAL("Segal"),
    CHAN("Chan"),
    HARALDSON("Haraldson"),
    LARSEN("Larsen"),
    PEERSON("Peerson"),
    DIGDEEP("Digdeep"),
    LICKFEET("Lickfeet"),
    KROGONSEN("Krogonsen"),
    SILY("Sily"),
    MERIT("Merit"),
    EINSTEIN("Einstein"),
    HOHENHOLTZ("Hohenholtz"),
    AYECOCK("Ayecock"),
    BEAVER("Beaver"),
    BORGNINO("Borgnino"),
    Butts("Butts"),
    Butters("Butters"),
    Cobbledick("Cobbledick"),
    COCKBURN("Cockburn"),
    COX("Cox"),
    FARTZ("Fartz"),
    PIPISLAP("Pipislap"),
    DINGDONG("Dingdong");


    private final String text;

    MercenaryNickname(String txt) {
        this.text = txt;
    }

    public static String random() {
        MercenaryNickname[] type = values();
        return type[Dice.nextInt(type.length)].text;
    }
}

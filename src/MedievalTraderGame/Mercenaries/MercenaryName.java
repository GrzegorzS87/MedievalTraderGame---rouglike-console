package MedievalTraderGame.Mercenaries;

import MedievalTraderGame.Dice;

public enum MercenaryName {
        OLAF("Olaf"),
        EDMUND("Edmund"),
        BOB("Bob"),
        BEN("Ben"),
        JASON("Jason"),
        JAMES("James"),
        TONY("Tony"),
        ARCHIBALD("Archibald"),
        STEVE("Steve"),
        JOHN("John"),
        LUKE("Luke"),
        JABBA("Jabba"),
        TIMMY("Timmy"),
        TWINKY("Twinky"),
        DOODLES("Doodles"),
        SPARKY("Sparky"),
        GIGGLES("Giggles"),
        ADAM("Adam"),
        TOM("Tom"),
        JERRY("Jerry"),
        ZACHARY("Zachary"),
        BJORN("Bjorn"),
        SAREVOK("Sarevok"),
        ASTRO("Astro"),
        BRIGER("Briger"),
        HELMUT("Helmut"),
        ERIC("Eric"),
        SUN("Sun"),
        STEN("Sten"),
        TOKE("Toke"),
        TORSTEN("Torsten"),
        SIGNE("Signe"),
        REVNA("Renvan"),
        TOVE("Tove"),
        THYRE("Thyre"),
        FRED("Fred"),
        INGAM("Ingam"),
        LIG("Lig"),
        RANDY("Randy"),
        ASEL("Asel"),
        ARNE("Arne"),
        GORM("Gorm"),
        ULF("Ulf"),
        AVAR("Avar");

        private final String text;

        MercenaryName(String txt) {
            this.text = txt;
        }

        public static String random() {
            MercenaryName[] type = values();
            return type[Dice.nextInt(type.length)].text;
        }
}

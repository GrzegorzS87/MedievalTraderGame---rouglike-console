package medieval_trader_game.locations;

import medieval_trader_game.locations.enterprise.LocalEnterprise;

public class Location {

    public String name;
    public Vendor vendor =  new Vendor();
    public LocalEnterprise enterprise = new LocalEnterprise();
    private int lastVisitRoundNumber = 0;
    private int currentVisitProfit = 0;


    public Location(String name){
        this.name = name;
    }

    public int collectProfits(int currentRound){
        int period = sinceLastVisit( currentRound );
        currentVisitProfit = enterprise.collectProfits( period );
        return currentVisitProfit;
    }

    public int getCurrentVisitProfit(){
        return currentVisitProfit;
    }

    public void leave(int round){
        lastVisitRoundNumber = round;
    }

    public int sinceLastVisit(int currentRound){
        return currentRound - lastVisitRoundNumber;
    }

    public boolean playerOwnsEnterprise(){
        return enterprise.playerOwnsEnterpriseHere();
    }

    public void newEnterpriseOffer(){
        enterprise.newOffer();
    }
}

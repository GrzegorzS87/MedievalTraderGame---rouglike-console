package medieval_trader_game.locations.enterprise;

import medieval_trader_game.console_interface.UiInfo;

import java.util.ArrayList;
import java.util.List;

public class LocalEnterprise {

    private final List<Enterprise> list = new ArrayList<>();
    public Enterprise forSale;
    public int price;

    public LocalEnterprise(){
        forSale = Enterprise.random();
    }

    public void newOffer(){
        forSale = Enterprise.random();
        forSale.newOffer();
        price = forSale.price();
    }

    public int collectProfits(int period){
        int profit = 0;
        for(Enterprise e : list){
            profit += e.generateProfit( period );
        }
        return profit;
    }

    public UiInfo printList(){
        UiInfo info = new UiInfo();
        int totalValue = 0;
        int totalProfit = 0;
        for( Enterprise e : list ){
            info.viewAdd(e.getName());
            info.viewAdd("bought for: " + e.price());
            info.viewAdd("average weekly profit: " + e.profit());
            info.viewAdd(" ");
            totalValue += e.price();
            totalProfit += e.profit();
        }
        info.viewAdd("Total investments: " + totalValue);
        info.viewAdd("Total weekly profit: " + totalProfit);
        info.viewAdd(" ");

        return info;
    }

    public boolean playerOwnsEnterpriseHere(){
        return list.size() > 0;
    }

    public List<Enterprise> list(){
        return list;
    }
}

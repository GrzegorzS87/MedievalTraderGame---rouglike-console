package medieval_trader_game.balance_and_testing;

import medieval_trader_game.locations.enterprise.Enterprise;
import org.testng.annotations.Test;

public class LocationTest {
    @Test
    public void locationTest(){

        for(Enterprise e : Enterprise.values())
            System.out.println(e.getName());
    }
}

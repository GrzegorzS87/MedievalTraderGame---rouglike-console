package MedievalTraderGame.BalancingAndTesting;

import MedievalTraderGame.Locations.Enterprise.Enterprise;
import org.testng.annotations.Test;

public class LocationTest {
    @Test
    public void locationTest(){

        for(Enterprise e : Enterprise.values())
            System.out.println(e.getName());
    }
}

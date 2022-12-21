package MedievalTraderGame.BalancingAndTesting;

import MedievalTraderGame.Interface.MainWindow;
import MedievalTraderGame.Interface.UiInfo;
import MedievalTraderGame.Items.Item;
import org.testng.annotations.Test;

public class MeleeRangedTest {
    @Test
    void howManyAreMeleeOrRanged(){
        int runs =100000;
        int melee = 0;
        for(int i=0; i<runs; i++){
            if(new Item().allowsParry) melee++;
        }
        MainWindow ui = new MainWindow();
        UiInfo info = new UiInfo();
        info.viewAdd( melee + " melee of " + runs );
        ui.print(info);
    }
}

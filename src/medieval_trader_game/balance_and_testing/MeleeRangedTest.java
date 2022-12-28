package medieval_trader_game.balance_and_testing;

import medieval_trader_game.console_interface.MainWindow;
import medieval_trader_game.console_interface.UiInfo;
import medieval_trader_game.items.Item;
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

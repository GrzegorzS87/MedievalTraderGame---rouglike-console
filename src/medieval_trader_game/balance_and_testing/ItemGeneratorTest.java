package medieval_trader_game.balance_and_testing;

import medieval_trader_game.console_interface.MainWindow;
import medieval_trader_game.items.Item;

public class ItemGeneratorTest {
    public static void main(String[] args) {

        MainWindow ui = new MainWindow();
        for(int i=0; i<20; i++){
            Item weapon1 = new Item();
            ui.print(weapon1.printInfo());
        }
    }}

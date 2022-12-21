package MedievalTraderGame.BalancingAndTesting;

import MedievalTraderGame.Interface.MainWindow;
import MedievalTraderGame.Items.Item;

public class ItemGeneratorTest {
    public static void main(String[] args) {

        MainWindow ui = new MainWindow();
        for(int i=0; i<20; i++){
            Item weapon1 = new Item();
            ui.print(weapon1.printInfo());
        }
    }}

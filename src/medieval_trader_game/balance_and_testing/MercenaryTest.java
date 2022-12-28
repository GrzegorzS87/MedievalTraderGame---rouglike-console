package medieval_trader_game.balance_and_testing;

import medieval_trader_game.console_interface.MainWindow;
import medieval_trader_game.console_interface.UiInfo;
import medieval_trader_game.mercenaries.enemies.Enemies;
import medieval_trader_game.mercenaries.MercenaryTeam;

public class MercenaryTest {

    public static void main(String[] args) {
        int power = 1000;

        MainWindow ui = new MainWindow();
        MercenaryTeam m = Enemies.generateRandomEnemies(power,5);
        m.printTeamInfo();
        int averagePower = (int) (((double) m.getTeamPower())/ ((double) m.size()));


        int sum = 0;
        for (int i = 0; i<m.size(); i++) {
            sum += Math.abs(m.at(i).getHirePrice() - averagePower);
        }
        int powerDeviation = (int) (((double) sum) / ((double) m.size()));

        UiInfo info = new UiInfo();

        info.consoleAdd("team power " + m.getTeamPower()
                   + " average power " + averagePower
                       + " deviation " + powerDeviation);

        ui.print(info);
    }
}

package MedievalTraderGame.BalancingAndTesting;

import MedievalTraderGame.Interface.MainWindow;
import MedievalTraderGame.Interface.UiInfo;
import MedievalTraderGame.Mercenaries.Enemies.Enemies;
import MedievalTraderGame.Mercenaries.MercenaryTeam;

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

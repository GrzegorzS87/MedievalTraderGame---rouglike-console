package medieval_trader_game.mercenaries;

import medieval_trader_game.console_interface.UiInfo;
import java.util.ArrayList;
import java.util.List;


public class MercenaryTeam {
    private final List<Mercenary> mercenaries = new ArrayList<>();
    private int teamPower;

    public MercenaryTeam(){
        teamPower = 0;
    }

    public int getTeamPower(){
        teamPower = 0;
        for (Mercenary mercenary : mercenaries) {
            teamPower += mercenary.getHirePrice();
            if (mercenary.hasItem()) {
                teamPower += mercenary.getItemValue();
            }
        }
        return teamPower;
    }

    public void addMercenary(Mercenary mercenary){
        mercenaries.add(mercenary);
    }

    public void fireMercenary(int mercenaryId){
        mercenaries.remove( mercenaryId );
    }

    public void fireMercenary(Mercenary mercenary ){
        mercenaries.remove( mercenary );
    }

    public int size(){
        return mercenaries.size();
    }

    public Mercenary at(int index){
        return mercenaries.get(index);
    }

    public UiInfo printTeamInfo(){

        UiInfo info = new UiInfo();

        for(int i=0; i< mercenaries.size(); i++){
            info.viewAdd("Mercenary " + i);
            info.viewAdd( mercenaries.get(i).printInlineInfo() );
            info.viewAdd( mercenaries.get(i).printItemInfo() );
            info.viewAdd(" ");
        }

        return info;
    }

    public void healAll(){
        for (Mercenary merc: mercenaries) {
            merc.healUp();
        }
    }

    public boolean choiceIsIinvalidId(String choice){

        int mercId;

        // choice is number, valid for parseInt
        try{
            mercId = Integer.parseInt(choice);
        }catch(NumberFormatException e){
            return true;
        }

        // id exceeds array
        return (mercId < 0) || (mercId >= mercenaries.size());
    }

}

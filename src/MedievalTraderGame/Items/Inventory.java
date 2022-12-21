package MedievalTraderGame.Items;

import MedievalTraderGame.Interface.UiInfo;
import MedievalTraderGame.Items.Item;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<Item> inventory = new ArrayList<>();

    public Inventory(){
    }

    public void addItem(Item item){
        inventory.add(item);
    }

    public void removeItem(int item){
        inventory.remove(item);
    }

    public void removeItem(Item item){
        inventory.remove(item);
    }

    public void dumpInventory(){
        while (!inventory.isEmpty()){
            removeItem(0);
        }
    }

    public void dumpInventorySaveOne(){
        while (inventory.size()>1){
            removeItem(0);
        }
    }

    public boolean isEmpty(){
        return inventory.isEmpty();
    }

    public int size(){
        return inventory.size();
    }

    public int inventoryValue(){
        int inventoryValue = 0;
        for (Item item : inventory){
            inventoryValue += item.value;
        }
        return inventoryValue;
    }

    public Item itemAt(int ID){
        return inventory.get(ID);
    }

    public UiInfo printEquipItemMenu() {
        UiInfo info = new UiInfo();
        info.viewAdd("*---------{ I N V E N T O R Y }----------*");

        if (inventory.isEmpty()) {
            info.viewAdd("Inventory is Empty");
            info.viewAdd("  ");
            info.viewAdd("99 to back.");
            return info;
        }

        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            info.viewAdd("Item " + i + " is:");
            info.print( item.printInfo() );
            info.viewAdd("");
        }
        info.viewAdd("Pick item id 0 to " + (inventory.size() - 1) + " to give it to mercenary, 99 to back.");

        return info;
    }

    public boolean choiceIsInvalidId(String choice){
        int itemId;

        // choice is a number, valid for parseInt
        try{
            itemId = Integer.parseInt(choice);
        }catch(NumberFormatException e){
            return true;
        }

        // id exceeds array
        if( (itemId < 0) || (itemId >= inventory.size()) )
            return true;

        return false;
    }
}


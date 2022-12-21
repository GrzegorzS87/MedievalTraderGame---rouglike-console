package MedievalTraderGame.Interface;

import java.util.ArrayList;
import java.util.List;

public class UiInfo {

    private final List<String> view = new ArrayList<>();
    private final List<String> console = new ArrayList<>();

    public UiInfo(){

    }

    public void print( UiInfo info){
        view.addAll(info.view);
        console.addAll(info.console);
    }

    public void viewAdd( String line ){
        view.add(line);
    }

    public void consoleAdd( String line ) {
        console.add(line);
    }

    public boolean viewIsEmpty(){
        return view.isEmpty();
    }

    public boolean consoleIsEmpty(){
        return console.isEmpty();
    }

    public int viewSize(){
        return view.size();
    }

    public  int consoleSize(){
        return console.size();
    }

    public String viewLine( int lineNumber ){
        return view.get( lineNumber );
    }

    public String consoleLine( int lineNumber ){
        return console.get( lineNumber );
    }
}

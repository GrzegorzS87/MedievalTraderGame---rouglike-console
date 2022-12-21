package MedievalTraderGame.Interface;

import javax.swing.*;
import java.awt.*;

public class View extends JTextArea {

    public View(){
        setFont(MyFonts.courier());
        setForeground(Color.WHITE);
        setBackground(Color.BLACK);
        setFocusable(false);
        setEditable(false);
        setVisible(true);
    }

    public void clear(){
        setText(" ");
    }

}

package MedievalTraderGame.Interface;

import MedievalTraderGame.MaxValues;

import javax.swing.*;
import java.awt.*;

public class TextInput extends JTextField {

//    JTextField function = new JTextField(8);

    public TextInput(){
        super(8);
        setForeground(Color.WHITE);
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder( Color.DARK_GRAY));
        setPreferredSize(new Dimension(MaxValues.windowWidth,30));
        setFont(MyFonts.courier());
        setCaretColor(Color.WHITE);

    }

}

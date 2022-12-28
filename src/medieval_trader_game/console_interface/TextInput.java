package medieval_trader_game.console_interface;

import medieval_trader_game.MaxValues;

import javax.swing.*;
import java.awt.*;

public class TextInput extends JTextField {

//    JTextField function = new JTextField(8);

    public TextInput(){
        super(8);
        setForeground(Color.WHITE);
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder( Color.DARK_GRAY));
        setPreferredSize(new Dimension(MaxValues.WINDOW_WIDTH,30));
        setFont(MyFonts.courier());
        setCaretColor(Color.WHITE);

    }

}

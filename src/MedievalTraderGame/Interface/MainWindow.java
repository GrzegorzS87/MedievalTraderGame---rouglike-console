package MedievalTraderGame.Interface;

import MedievalTraderGame.MTGame;
import MedievalTraderGame.MaxValues;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow {

    private static final View view = new View();
    private static final View console = new View();
    private static final View generalInfoBox = new View();
    public static TextInput input = new TextInput();
    private static final MTGame game = new MTGame();
    private String choice;
    JScrollPane scrollPane = new JScrollPane( view );


    public MainWindow(){
        JFrame window = new JFrame("Medieval Trader Game (by Grzegorz Soból)");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(MaxValues.windowWidth,MaxValues.windowHeight);

        window.setLayout( new BorderLayout() );
        window.getContentPane().getInsets().set( 10, 10, 10, 10 );

        console.setPreferredSize( new Dimension(MaxValues.windowWidth, 60) );
        generalInfoBox.setPreferredSize(  new Dimension(MaxValues.windowWidth, 20) );


        scrollPane.setLayout( new ScrollPaneLayout() );
        scrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
        scrollPane.getHorizontalScrollBar().setBackground(Color.BLACK);
        scrollPane.getHorizontalScrollBar().setUI( new NoArrowScrollBarUI() );
        scrollPane.getVerticalScrollBar().setUI( new NoArrowScrollBarUI() );
        scrollPane.getVerticalScrollBar().setUnitIncrement(100);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        input.setPreferredSize( new Dimension(MaxValues.windowWidth, 20) );
        input.addKeyListener( new KeyAdapter() {
            @Override
            public void keyPressed( KeyEvent e ) {
                if( e.getKeyCode() == KeyEvent.VK_ENTER ){
                    choice = input.getText().trim(); //little convenience for user in case of accidental space press
                    input.setText( " " );

                    print( game.playerChoice( choice ) );
                    game.isGameOver();
                    print( game.printMenu() );
                    printGeneralInfo( game.printGeneralInfo() );
                }

                if (e.getKeyCode()==KeyEvent.VK_UP)
                {
                    JScrollBar vertical = scrollPane.getVerticalScrollBar();
                    InputMap im = vertical.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
                    im.put(KeyStroke.getKeyStroke("UP"), "negativeUnitIncrement");
                }

                else if (e.getKeyCode()==KeyEvent.VK_DOWN)
                {
                    JScrollBar vertical = scrollPane.getVerticalScrollBar();
                    InputMap im = vertical.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
                    im.put(KeyStroke.getKeyStroke("DOWN"), "positiveUnitIncrement");
                }
            }});

        window.add( scrollPane,BorderLayout.CENTER );
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add( console,BorderLayout.CENTER );
        bottom.add( input,BorderLayout.PAGE_END );
        window.add( generalInfoBox,BorderLayout.PAGE_START );
        window.add(bottom,BorderLayout.PAGE_END );
        window.setLocationRelativeTo( null );
        window.setVisible( true );

        print( game.printMenu() );
    }

    public void print( UiInfo info ){

        if( !info.viewIsEmpty() ){
            view.clear();
            for(int i=0; i<info.viewSize(); i++){
                view.append( info.viewLine(i) );
                view.append( "\n " );
            }
            view.setCaretPosition(0);
        }

        if( !info.consoleIsEmpty() ){
            console.clear();
            for(int i=0; i<info.consoleSize(); i++){
                console.append( info.consoleLine(i) );
                console.append( "\n " );
            }
            console.setCaretPosition(0);
        }
    }

    public void printGeneralInfo( String info ){
        generalInfoBox.setText( info );
    }

}

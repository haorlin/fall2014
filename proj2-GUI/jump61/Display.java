package jump61;

import ucb.gui.TopLevel;
import ucb.gui.LayoutSpec;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Observable;
import java.util.Observer;
import static jump61.Side.*;

/** The GUI controller for jump61.  To require minimal change to textual
 *  interface, we adopt the strategy of converting GUI input (mouse clicks)
 *  into textual commands that are sent to the Game object through a
 *  a Writer.  The Game object need never know where its input is coming from.
 *  A Display is an Observer of Games and Boards so that it is notified when
 *  either changes.
 *  @author
 */
class Display extends TopLevel implements Observer {

    /** A new window with given TITLE displaying GAME, and using COMMANDWRITER
     *  to send commands to the current game. */
    Display(String title, Game game, Writer commandWriter) {
        super(title, true);
        _game = game;
        _board = game.getBoard();
        _commandOut = new PrintWriter(commandWriter);
        _boardWidget = new BoardWidget(game, _commandOut);
        add(_boardWidget, new LayoutSpec("y", 1, "width", 2));
        addMenuButton("Game->New Game", "newGame");
        addMenuButton("Game->Clear", "clear");
        addMenuButton("Game->Quit", "quit");
        addLabel("", "Score", new LayoutSpec("y", 0));
        Score(_board.numOfSide(BLUE), _board.numOfSide(RED));
        addMenuRadioButton("Options->Red Manual", "Red Player", true, "redManual");
        addMenuRadioButton("Options->Red AI", "Red Player", false, "redAI");
        addMenuRadioButton("Options->Blue Manual", "Blue Player", false, "blueManual");
        addMenuRadioButton("Options->Blue AI", "Blue Player", true, "blueAI");
        addMenuButton("Options->Set Seed", "setSeed");
        addMenuButton("Options->Board Size", "size");
        _board.addObserver(this);
        _game.addObserver(this);
        display(true);
    }

    public void Score(int redScore, int blueScore) {
        setLabel("Score", String.format("Red: %6d / Blue: %6d",
                                        redScore, blueScore));
    }
    
    /** Response to "Clear" button click. */
    void newGame(String dummy) {
        _commandOut.println("start");
    }

    /** Response to "Clear" button click. */
    void clear(String dummy) {
        _commandOut.println("clear");
    }

    /** Response to "Quit" button click. */
    void quit(String dummy) {
        System.exit(0);
    }
    
    /** Response to "Red Manual" button click */
    void redManual(String size) {
        _commandOut.println("manual red");
    }
    
    /** Response to "Red AI" button click */
    void redAI(String dummy) {
        _commandOut.println("auto red");
    }
    
    /** Response to "Manual Blue" button click */
    void blueManual(String dummy) {
        _commandOut.println("manual blue");
    }
    
    /** Response to "Blue AI" button click */
    void blueAI(String dummy) {
        _commandOut.println("auto blue");
    }
    
    /** Response to "Set Seed" button click */
    void setSeed(String dummy) {
        String seedN = getTextInput("Enter seed value", "Seed", "int", "");
        _commandOut.println("seed " + seedN);
    }
    
    /** Response to "Board Size" button click */
    void size(String dummy) {
        String size = getTextInput("Enter Number of Rows and Columns (2 - 10)", "Size", "int", "6");
        _commandOut.println("size " + size);
    }
    
    @Override
    public void update(Observable obs, Object obj) {
        _boardWidget.update();
        frame.pack();
        _boardWidget.repaint();
    }
    
    /** The current game that I am controlling. */
    private Game _game;
    /** The board maintained by _game (readonly). */
    private Board _board;
    /** The widget that displays the actual playing board. */
    private BoardWidget _boardWidget;
    /** Writer that sends commands to our game. */
    private PrintWriter _commandOut;
}

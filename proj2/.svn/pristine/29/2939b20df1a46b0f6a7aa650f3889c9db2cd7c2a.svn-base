package jump61;

import ucb.gui.Pad;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;

import static jump61.Side.*;

/** A GUI component that displays a Jump61 board, and converts mouse clicks
 *  on that board to commands that are sent to the current Game.
 *  @author Hao Ran Raymond Lin
 */
class BoardWidget extends Pad {

    /** Length of the side of one square in pixels. */
    private static final int SQUARE_SIZE = 50;
    /** Width and height of a spot. */
    private static final int SPOT_DIM = 8;
    /** Minimum separation of center of a spot from a side of a square. */
    private static final int SPOT_MARGIN = 10;
    /** Width of the bars separating squares in pixels. */
    private static final int SEPARATOR_SIZE = 3;
    /** Width of square plus one separator. */
    private static final int SQUARE_SEP = SQUARE_SIZE + SEPARATOR_SIZE;
    /** Font for overlay text on board. */
    static final Font OVERLAY_FONT = new Font("SansSerif", 1, 64);
    /** Color for overlay text on board. */
    static final Color OVERLAY_COLOR = new Color(200, 0, 0, 64);
    /** coordinate 1. */
    private static final int COORD1 = 40;
    /** coordinate 2. */
    private static final int COORD2 = 10;

    /** Colors of various parts of the displayed board. */
    private static final Color
        NEUTRAL = Color.WHITE,
        SEPARATOR_COLOR = Color.BLACK,
        SPOT_COLOR = Color.BLACK,
        RED_TINT = new Color(255, 200, 200),
        BLUE_TINT = new Color(200, 200, 255);

    /** A new BoardWidget that monitors and displays GAME and its Board, and
     *  converts mouse clicks to commands to COMMANDWRITER. */
    BoardWidget(Game game, PrintWriter commandWriter) {
        _game = game;
        _board = _bufferedBoard = game.getBoard();
        _side = _board.size() * SQUARE_SEP + SEPARATOR_SIZE;
        setPreferredSize(_side, _side);
        setMouseHandler("click", this, "doClick");
        _commandOut = commandWriter;
    }

    /* .update and .paintComponent are synchronized because they are called
     *  by three different threads (the main thread, the thread that
     *  responds to events, and the display thread.  We don't want the
     *  saved copy of our Board to change while it is being displayed. */

    /** Update my display depending on any changes to my Board.  Here, we
     *  save a copy of the current Board (so that we can deal with changes
     *  to it only when we are ready for them), and resize the Widget if the
     *  size of the Board should change. */
    synchronized void update() {
        _bufferedBoard = new MutableBoard(_board);
        int side0 = _side;
        _side = _board.size() * SQUARE_SEP + SEPARATOR_SIZE;
        if (side0 != _side) {
            setPreferredSize(_side, _side);
        }
    }

    /** Marks end of a game. */
    synchronized void markEnd() {
        _end = true;
        repaint();
    }

    /**
     * Converts X pixel values to columns.
     * @param x value
     * @return column
     */
    int xToC(int x) {
        return (x / SQUARE_SEP) + 1;
    }

    /**
     * Converts Y pixel values to rows.
     * @param y value
     * @return row
     */
    int yToR(int y) {
        return (y / SQUARE_SEP) + 1;
    }

    /**
     * Converts columns to X pixels.
     * @param c column
     * @return X value
     */
    int cToX(int c) {
        return SEPARATOR_SIZE * c + SQUARE_SIZE * (c - 1);
    }

    /**
     * Converts rows values to Y pixels.
     * @param r row
     * @return Y value
     */
    int rToY(int r) {
        return SEPARATOR_SIZE * r + SQUARE_SIZE * (r - 1);
    }

    @Override
    public synchronized void paintComponent(Graphics2D g) {
        g.setColor(SEPARATOR_COLOR);
        g.fillRect(0, 0, _side, _side);

        for (int i = 1; i <= _board.size(); i++) {
            for (int j = 1; j <= _board.size(); j++) {
                displaySpots(g, i, j);
            }
        }
        if (_board.getWinner() == BLUE) {
            markEnd();
            g.setFont(OVERLAY_FONT);
            FontMetrics metrics = g.getFontMetrics();
            g.setColor(OVERLAY_COLOR);
            g.drawString("BLUE WINS",
                    (_side - metrics.stringWidth("BLUE WINS")) / 2,
                    (2 * _side + metrics.getMaxAscent()) / 4);
        } else if (_board.getWinner() == RED) {
            markEnd();
            g.setFont(OVERLAY_FONT);
            FontMetrics metrics = g.getFontMetrics();
            g.setColor(OVERLAY_COLOR);
            g.drawString("RED WINS",
                    (_side - metrics.stringWidth("RED WINS")) / 2,
                    (2 * _side + metrics.getMaxAscent()) / 4);
        }
    }

    /** Color and display the spots on the square at row R and column C
     *  on G.  (Used by paintComponent). */
    private void displaySpots(Graphics2D g, int r, int c) {
        int x = cToX(c);
        int y = rToY(r);
        if (_board.get(r, c).getSide() == RED) {
            g.setColor(RED_TINT);
        } else if (_board.get(r, c).getSide() == BLUE) {
            g.setColor(BLUE_TINT);
        } else if (_board.get(r, c).getSide() == WHITE) {
            g.setColor(NEUTRAL);
        }
        g.fillRect(x, y, SQUARE_SIZE,
                SQUARE_SIZE);
        if (_board.get(r, c).getSpots() == 1) {
            spot(g, x + SQUARE_SIZE / 2, y + SQUARE_SIZE / 2);
        } else if (_board.get(r, c).getSpots() == 2) {
            spot(g, x + COORD1, y + COORD2);
            spot(g, x + COORD2, y + COORD1);
        } else if (_board.get(r, c).getSpots() == 3) {
            spot(g, x + COORD1, y + COORD2);
            spot(g, x + COORD2, y + COORD1);
            spot(g, x + SQUARE_SIZE / 2, y + SQUARE_SIZE / 2);
        } else if (_board.get(r, c).getSpots() == 4) {
            spot(g, x + COORD1, y + COORD2);
            spot(g, x + COORD2, y + COORD1);
            spot(g, x + COORD2, y + COORD2);
            spot(g, x + COORD1, y + COORD1);
        }
    }

    /** Draw one spot centered at position (X, Y) on G. */
    private void spot(Graphics2D g, int x, int y) {
        g.setColor(SPOT_COLOR);
        g.fillOval(x - SPOT_DIM / 2, y - SPOT_DIM / 2, SPOT_DIM, SPOT_DIM);
    }

    /** Respond to the mouse click depicted by EVENT. */
    public void doClick(MouseEvent event) {
        int x = event.getX() - SEPARATOR_SIZE,
            y = event.getY() - SEPARATOR_SIZE;
        int r = yToR(y);
        int c = xToC(x);
        _commandOut.printf("%d %d%n", r, c);
    }

    /** The Game I am playing. */
    private Game _game;
    /** The Board I am displaying. */
    private Board _board;
    /** An internal snapshot of _board (to prevent race conditions). */
    private Board _bufferedBoard;
    /** Dimension in pixels of one side of the board. */
    private int _side;
    /** Destination for commands derived from mouse clicks. */
    private PrintWriter _commandOut;
    /** End of a game. */
    private boolean _end;
}

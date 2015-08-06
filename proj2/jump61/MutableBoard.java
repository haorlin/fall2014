package jump61;
import static jump61.Square.square;
import java.util.Stack;

/** A Jump61 board state that may be modified.
 *  @author Hao Ran Raymond Lin
 */
class MutableBoard extends Board {

    /** An N x N board in initial configuration. */
    MutableBoard(int N) {
        _grid = new Square[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                _grid[i][j] = Square.INITIAL;
            }
        }
        _length = N;
        history = new Stack<Square[][]>();
    }

    /** A board whose initial contents are copied from BOARD0, but whose
     *  undo history is clear. */
    MutableBoard(Board board0) {
        _grid = new Square[board0.size()][board0.size()];
        for (int i = 0; i < board0.size(); i++) {
            for (int j = 0; j < board0.size(); j++) {
                _grid[i][j] = board0.get(i + 1, j + 1);
            }
        }
        _length = board0.size();
        history = new Stack<Square[][]>();
    }

    @Override
    void clear(int N) {
        _grid = new Square[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                _grid[i][j] = Square.INITIAL;
            }
        }
        _length = N;
    }

    @Override
    void copy(Board board) {
        for (int i = 0; i < _length; i++) {
            for (int j = 0; j < _length; j++) {
                _grid[i][j] = board.get(sqNum(i + 1, j + 1));
            }
        }
        while (!history.empty()) {
            history.pop();
        }
    }

    /** Copy the contents of BOARD into me, without modifying my undo
     *  history.  Assumes BOARD and I have the same size. */
    private void internalCopy(MutableBoard board) {
        for (int i = 0; i < _length; i++) {
            for (int j = 0; j < _length; j++) {
                _grid[i][j] = board.get(sqNum(i + 1, j + 1));
            }
        }
    }

    @Override
    int size() {
        return _length;
    }

    @Override
    Square get(int n) {
        int gridR = row(n) - 1;
        int gridC = col(n) - 1;
        return _grid[gridR][gridC];
    }

    @Override
    int numOfSide(Side side) {
        int numOfSide = 0;
        for (int i = 0; i < _length; i++) {
            for (int j = 0; j < _length; j++) {
                if (_grid[i][j].getSide() == side) {
                    numOfSide += 1;
                }
            }
        }
        return numOfSide;
    }

    @Override
    int numPieces() {
        int numSpots = 0;
        for (int i = 0; i < _length; i++) {
            for (int j = 0; j < _length; j++) {
                numSpots += _grid[i][j].getSpots();
            }
        }
        return numSpots;
    }

    @Override
    void addSpot(Side player, int r, int c) {
        markUndo();
        realAddSpot(player, r, c);
        announce();
    }

    @Override
    void addSpot(Side player, int n) {
        addSpot(player, row(n), col(n));
        announce();
    }

    /**
     * Adds a spot to the square and calls checkOverfull if spots is more
     * than neighbors.
     * @param player side of player
     * @param r row
     * @param c column
     */
    void realAddSpot(Side player, int r, int c) {
        _grid[r - 1][c - 1] = Square.square(player,
                _grid[r - 1][c - 1].getSpots() + 1);
        if (numOfSide(player) == _length * _length) {
            return;
        }
        if (_grid[r - 1][c - 1].getSpots() > neighbors(r, c)) {
            _grid[r - 1][c - 1] = Square.square(player, 1);
            checkOverfull(player, r, c);
        }
    }

    /**
     * Square spills spots onto neighboring squares if they exist.
     * @param player side of player
     * @param r row
     * @param c column
     */
    void checkOverfull(Side player, int r, int c) {
        if (exists(r - 1, c)) {
            realAddSpot(player, r - 1, c);
        }
        if (exists(r, c - 1)) {
            realAddSpot(player, r, c - 1);
        }
        if (exists(r + 1, c)) {
            realAddSpot(player, r + 1, c);
        }
        if (exists(r, c + 1)) {
            realAddSpot(player, r, c + 1);
        }
    }

    @Override
    void set(int r, int c, int num, Side player) {
        internalSet(sqNum(r, c), square(player, num));
    }

    @Override
    void set(int n, int num, Side player) {
        internalSet(n, square(player, num));
        announce();
    }

    @Override
    void undo() {
        if (history.empty()) {
            return;
        }
        _grid = history.pop();
    }

    /** Record the beginning of a move in the undo history. */
    private void markUndo() {
        MutableBoard tempBoard = new MutableBoard(_length);
        tempBoard.copy(this);
        history.push(tempBoard._grid);
    }

    /** Set the contents of the square with index IND to SQ. Update counts
     *  of numbers of squares of each color.  */
    private void internalSet(int ind, Square sq) {
        int gridR = row(ind) - 1;
        int gridC = col(ind) - 1;
        _grid[gridR][gridC] = sq;

    }

    /** Notify all Observers of a change. */
    private void announce() {
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MutableBoard)) {
            return obj.equals(this);
        } else {
            return (this.hashCode() == obj.hashCode());
        }
    }

    @Override
    public int hashCode() {
        return _grid.hashCode();
    }

    /** Board to which all operations delegated. */
    private Square[][] _grid;

    /** The size of the board. */
    private int _length;

    /** A history board game states. */
    private Stack<Square[][]> history;
}

package jump61;

import java.util.ArrayList;

/** An automated Player.
 *  @author Hao Ran Raymond Lin
 */
class AI extends Player {

    /** A new player of GAME initially playing COLOR that chooses
     *  moves automatically.
     */
    private static ArrayList<Integer> bestMoves;
    
    private int depth = 3;
    
    private static int MAX_VALUE = 999;
    private static int MIN_VALUE = -999;

    
    AI(Game game, Side color) {
        super(game, color);
    }

    @Override
    void makeMove() {
        MutableBoard tempBoard = new MutableBoard(getBoard());
        minmax(this.getSide(), tempBoard, depth, MAX_VALUE,
                new ArrayList<Integer>());
        System.out.println(bestMoves);
        int moveToMake = bestMoves
                .get((int) (Math.random() * bestMoves.size()));
        getGame().reportMove(this.getSide(), getBoard().row(moveToMake),
                getBoard().col(moveToMake));
        getGame().makeMove(moveToMake);
    }

    /** Return the minimum of CUTOFF and the minmax value of board B
     *  (which must be mutable) for player P to a search depth of D
     *  (where D == 0 denotes statically evaluating just the next move).
     *  If MOVES is not null and CUTOFF is not exceeded, set MOVES to
     *  a list of all highest-scoring moves for P; clear it if
     *  non-null and CUTOFF is exceeded. the contents of B are
     *  invariant over this call. */
    private int minmax(Side p, Board b, int d, int cutoff, ArrayList<Integer> moves) {
        if (b.getWinner() == p) {
            return MAX_VALUE;
        } else if (b.getWinner() == p.opposite()) {
            return MIN_VALUE;
        } else if (d == 0) {
            return guessBestMove(p, b);
        }
        int bestSoFar = MIN_VALUE;
        
        ArrayList<Integer> availableMoves = new ArrayList<Integer>();
        for (int i = 0; i < b.size() * b.size(); i++) {
            if (b.isLegal(p, i)) {
                availableMoves.add(i);
            }
        }

        for (int i = 0; i < availableMoves.size(); i++) {
            MutableBoard copy = new MutableBoard(b);
            copy.addSpot(p, availableMoves.get(i));
            int response = minmax(p.opposite(), copy, d - 1, -1 * bestSoFar,
                    new ArrayList<Integer>());

            if (-1 * response >= bestSoFar) {
                int MValue = -1 * response;
                bestSoFar = MValue;
                if (d == depth) {
                    moves = new ArrayList<Integer>();
                    moves.add(availableMoves.get(i));
                }
                if (MValue >= cutoff)
                    break;
            }
        }
        bestMoves = moves;
        return bestSoFar;
    }

    /**
     * Calculates the best move based on the board given using heuristic values.
     * @param p player
     * @param b board
     * @return The highest score for these set of boards
     */
    public int guessBestMove(Side p, Board b) {
        int bestSoFar = MIN_VALUE;
        ArrayList<Integer> availableMoves = new ArrayList<Integer>();
        for (int i = 0; i < b.size() * b.size(); i++) {
            if (b.isLegal(p, i)) {
                availableMoves.add(i);
            }
        }
        for (int i = 0; i < availableMoves.size(); i++) {
            MutableBoard copy = new MutableBoard(b);
            copy.addSpot(p, availableMoves.get(i));
            int MValue = (b.numOfSide(p) - b.numOfSide(p.opposite()));
            if (MValue > bestSoFar) {
                bestSoFar = MValue;
            }
        }
        return bestSoFar;
    }
}

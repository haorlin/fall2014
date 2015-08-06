package graph;

import java.util.LinkedList;

/** Implements a breadth-first traversal of a graph.  Generally, the
 *  client will extend this class, overriding the visit method as desired
 *  (by default, it does nothing).
 *  @author Hao Ran Raymond Lin
 */
public class BreadthFirstTraversal extends Traversal {

    /** A depth-first Traversal of G, using FRINGE as the fringe. */
    protected BreadthFirstTraversal(Graph G) {
        super(G, new LinkedList<Integer>());
    }

    @Override
    protected boolean visit(int v) {
        return super.visit(v);
    }
}

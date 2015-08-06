package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for traversals
 * @author Hao Ran Raymond Lin
 */
public class TraversalTesting {
    private class TestBFT extends BreadthFirstTraversal {
        public TestBFT(Graph G) {
            super(G);
        }

        @Override
        protected boolean visit(int v) {
            traversed.add(v);
            return true;
        }
        ArrayList<Integer> traversed = new ArrayList<Integer>();
    }

    private class TestDFT extends DepthFirstTraversal {
        public TestDFT(Graph G) {
            super(G);
        }

        @Override
        protected boolean visit(int v) {
            traversed.add(v);
            return true;
        }

        @Override
        protected boolean postVisit(int v) {
            postTraversed.add(v);
            return true;
        }
        ArrayList<Integer> traversed = new ArrayList<Integer>();
        ArrayList<Integer> postTraversed = new ArrayList<Integer>();
    }

    @Test
    public void depthFirstTesting() {
        DirectedGraph g = new DirectedGraph();
        TestDFT t = new TestDFT(g);
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(5, 4);
        g.add(5, 3);
        g.add(4, 1);
        g.add(3, 2);
        g.add(1, 5);
        Integer[] correctTraversal = {5, 4, 1, 3, 2};
        Integer[] correctTraversal2 = {5, 3, 2, 4, 1};
        List<Integer> traversalList = Arrays.asList(correctTraversal);
        List<Integer> traversalList2 = Arrays.asList(correctTraversal2);
        t.traverse(5);
        assertTrue(traversalList.equals(t.traversed)
                || traversalList2.equals(t.traversed));
        Integer[] correctPostTraversal = {1, 4, 2, 3, 5};
        Integer[] correctPostTraversal2 = {2, 3, 1, 4, 5};
        List<Integer> postTraversalList = Arrays.asList(correctPostTraversal);
        List<Integer> postTraversalList2 = Arrays.asList(correctPostTraversal2);
        assertTrue(postTraversalList.equals(t.postTraversed)
              || postTraversalList2.equals(t.postTraversed));
    }

    @Test
    public void breathFirstTesting() {
        DirectedGraph g = new DirectedGraph();
        TestBFT t = new TestBFT(g);
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(5, 4);
        g.add(5, 3);
        g.add(4, 1);
        g.add(3, 2);
        t.traverse(5);
        Integer[] correctTraversal = {5, 4, 3, 1, 2};
        Integer[] correctTraversal2 = {5, 3, 4, 1, 2};
        Integer[] correctTraversal3 = { 5, 4, 3, 2, 1 };
        Integer[] correctTraversal4 = { 5, 3, 4, 2, 1 };
        List<Integer> traversalList = Arrays.asList(correctTraversal);
        List<Integer> traversalList2 = Arrays.asList(correctTraversal2);
        List<Integer> traversalList3 = Arrays.asList(correctTraversal3);
        List<Integer> traversalList4 = Arrays.asList(correctTraversal4);
        assertTrue(traversalList.equals(t.traversed)
                || traversalList2.equals(t.traversed)
                || traversalList3.equals(t.traversed)
                || traversalList4.equals(t.traversed));
    }
}

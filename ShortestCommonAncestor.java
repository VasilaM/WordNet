import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class ShortestCommonAncestor {
    private Digraph graph; // digraph

    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException("");
        graph = G;
        DirectedCycle hasCyc = new DirectedCycle(graph);
        if (hasCyc.hasCycle())
            throw new IllegalArgumentException("");

    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        if (v < 0 || v > graph.V() || w < 0 || w > graph.V())
            throw new IllegalArgumentException("");

        BreadthFirstDirectedPaths first = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths second = new BreadthFirstDirectedPaths(graph, w);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < graph.V(); i++) {
            if (first.hasPathTo(i) && second.hasPathTo(i)) {
                if (first.distTo(i) + second.distTo(i) < min)
                    min = first.distTo(i) + second.distTo(i);
            }
        }
        return min;
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        if (v < 0 || v > graph.V() || w < 0 || w > graph.V())
            throw new IllegalArgumentException("");
        int ancestor = 0;
        BreadthFirstDirectedPaths first = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths second = new BreadthFirstDirectedPaths(graph, w);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < graph.V(); i++) {
            if (first.hasPathTo(i) && second.hasPathTo(i)) {
                if (first.distTo(i) + second.distTo(i) < min) {
                    min = first.distTo(i) + second.distTo(i);
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        for (Integer s : subsetA) {
            if (s == null || s < 0 || s > graph.V())
                throw new IllegalArgumentException("");
        }
        for (Integer s : subsetB) {
            if (s == null || s < 0 || s > graph.V())
                throw new IllegalArgumentException("");
        }

        BreadthFirstDirectedPaths first = new
                BreadthFirstDirectedPaths(graph, subsetA);
        BreadthFirstDirectedPaths second = new
                BreadthFirstDirectedPaths(graph, subsetB);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < graph.V(); i++) {
            if (first.hasPathTo(i) && second.hasPathTo(i)) {
                if (first.distTo(i) + second.distTo(i) < min)
                    min = first.distTo(i) + second.distTo(i);
            }
        }
        return min;
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA,
                              Iterable<Integer> subsetB) {
        for (Integer s : subsetA) {
            if (s == null || s < 0 || s > graph.V())
                throw new IllegalArgumentException("");
        }
        for (Integer s : subsetB) {
            if (s == null || s < 0 || s > graph.V())
                throw new IllegalArgumentException("");
        }
        int ancestor = 0;
        BreadthFirstDirectedPaths first = new
                BreadthFirstDirectedPaths(graph, subsetA);
        BreadthFirstDirectedPaths second = new
                BreadthFirstDirectedPaths(graph, subsetB);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < graph.V(); i++) {
            if (first.hasPathTo(i) && second.hasPathTo(i)) {
                if (first.distTo(i) + second.distTo(i) < min) {
                    min = first.distTo(i) + second.distTo(i);
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        Queue<Integer> q1 = new Queue<>();
        Queue<Integer> q2 = new Queue<>();
        q1.enqueue(6);
        q1.enqueue(3);
        q1.enqueue(1);
        q1.enqueue(0);
        q2.enqueue(9);
        q2.enqueue(5);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            int length1 = sca.lengthSubset(q1, q2);
            int ancestor1 = sca.ancestorSubset(q1, q2);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
            StdOut.printf("length = %d, ancestor = %d\n", length1, ancestor1);
        }
    }

}


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

        graph = new Digraph(G);
        // check to see if DAG is rooted
        DirectedCycle hasCyc = new DirectedCycle(graph);
        int root = 0;
        for (int i = 0; i < graph.V(); i++) {
            if (graph.outdegree(i) == 0)
                root++;
        }
        if (root > 1 || root == 0 || hasCyc.hasCycle())
            throw new IllegalArgumentException("");
    }

    // private helper method that returns the index of the sca
    private int minDisIndex(BreadthFirstDirectedPaths first,
                            BreadthFirstDirectedPaths second) {
        int min = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < graph.V(); i++) {
            // if ancestor is reachable from both vertices, calculate
            // dist and save the min
            if (first.hasPathTo(i) && second.hasPathTo(i)) {
                int distance = first.distTo(i) + second.distTo(i);
                if (distance < min) {
                    min = distance;
                    index = i;
                }
            }
        }
        return index;
    }

    // check length() and ancestor() for exceptions
    private void checkErr(int v, int w) {
        if (v < 0 || v > graph.V() || w < 0 || w > graph.V())
            throw new IllegalArgumentException("");
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        checkErr(v, w);
        BreadthFirstDirectedPaths first = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths second = new BreadthFirstDirectedPaths(graph, w);
        int i = minDisIndex(first, second);
        return first.distTo(i) + second.distTo(i);
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        checkErr(v, w);
        BreadthFirstDirectedPaths first = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths second = new BreadthFirstDirectedPaths(graph, w);
        return minDisIndex(first, second);
    }

    // private helper method that returns the index of the sca
    private int minDisIndexMulti(BreadthFirstDirectedPaths first,
                                 BreadthFirstDirectedPaths second) {
        int index = 0;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < graph.V(); i++) {
            // if ancestor is reachable from both sets of vertices, calculate
            // dist and save the min
            if (first.hasPathTo(i) && second.hasPathTo(i)) {
                int distance = first.distTo(i) + second.distTo(i);
                if (distance < min) {
                    min = distance;
                    index = i;
                }
            }
        }
        return index;
    }

    // check lengthSubset() and ancestorSubset() for exceptions
    private void errMulti(Iterable<Integer>
                                  subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null)
            throw new IllegalArgumentException("");
        for (Integer s : subsetA) {
            if (s == null || s < 0 || s > graph.V())
                throw new IllegalArgumentException("");
        }
        for (Integer s : subsetB) {
            if (s == null || s < 0 || s > graph.V())
                throw new IllegalArgumentException("");
        }
    }


    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        errMulti(subsetA, subsetB);
        BreadthFirstDirectedPaths first = new
                BreadthFirstDirectedPaths(graph, subsetA);
        BreadthFirstDirectedPaths second = new
                BreadthFirstDirectedPaths(graph, subsetB);
        int i = minDisIndexMulti(first, second);
        return first.distTo(i) + second.distTo(i);
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA,
                              Iterable<Integer> subsetB) {
        errMulti(subsetA, subsetB);
        BreadthFirstDirectedPaths first = new
                BreadthFirstDirectedPaths(graph, subsetA);
        BreadthFirstDirectedPaths second = new
                BreadthFirstDirectedPaths(graph, subsetB);
        return minDisIndexMulti(first, second);
    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        Queue<Integer> q1 = new Queue<>();
        Queue<Integer> q2 = new Queue<>();
        q1.enqueue(0);
        q1.enqueue(1);
        q1.enqueue(2);
        q1.enqueue(3);
        q2.enqueue(5);
        q2.enqueue(6);
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


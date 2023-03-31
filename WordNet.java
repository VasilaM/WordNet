import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;

import java.util.HashMap;

public class WordNet {
    private HashMap<Integer, Queue<String>> map;
    private SET<String> set;
    private Digraph digraph;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In file1 = new In(synsets);
        In file2 = new In(hypernyms);
        int size = 0;
        map = new HashMap<>();
        set = new SET<>();
        // read in data and store it
        while (!file1.isEmpty()) {
            String line = file1.readLine();
            String first[] = line.split(",");
            String second[] = first[1].split(" ");
            Queue<String> queue = new Queue<>();
            for (int i = 0; i < second.length; i++) {
                queue.enqueue(second[i]);
                set.add(second[i]);
            }
            map.put(Integer.parseInt(first[0]), queue);
            size++;
        }

        digraph = new Digraph(size);

        while (!file2.isEmpty()) {
            String line = file2.readLine();
            String first[] = line.split(",");
            // create a digraph of the correct size
            for (int i = 1; i < first.length - 1; i++) {
                digraph.addEdge(Integer.parseInt(first[0]),
                                Integer.parseInt(first[i]));
            }
        }
    }

    // ASK IF THIS IS CORRECT
    // the set of all WordNet nouns
    public Iterable<String> nouns() {
        Queue<String> queue = new Queue<>();
        for (int i = 0; i < map.size(); i++) {
            for (String s : map.get(i)) {
                queue.enqueue(s);
            }
        }
        return queue;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return set.contains(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        ShortestCommonAncestor sca = new ShortestCommonAncestor(digraph);
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        ShortestCommonAncestor sca = new ShortestCommonAncestor(digraph);
        sca.length(noun1, noun2);
    }

    // unit testing (required)
    public static void main(String[] args) {
        WordNet word = new WordNet("synsets3.txt", "hypernmyms3.txt");
        for (String s : word.nouns())
            System.out.println(s);
    }
}

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.SeparateChainingHashST;

public class WordNet {
    private SeparateChainingHashST<Integer, Queue<String>> map; // hash map
    // for ID of the vertices and queues
    private SET<String> set; // set stores words for isNoun()
    private Digraph digraph; // digraph

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In file1 = new In(synsets);
        In file2 = new In(hypernyms);
        int size = 0;
        map = new SeparateChainingHashST<>();
        set = new SET<>();

        // read synsets.txt and store it in hash map and set
        while (!file1.isEmpty()) {
            String line = file1.readLine();
            String[] first = line.split(",");
            String[] second = first[1].split(" ");
            Queue<String> queue = new Queue<>();
            for (int i = 0; i < second.length; i++) {
                queue.enqueue(second[i]);
                set.add(second[i]);
            }
            map.put(Integer.parseInt(first[0]), queue);
            size++;
        }

        digraph = new Digraph(size);
        // add edges to digraph from hypernyms.txt
        while (!file2.isEmpty()) {
            String line = file2.readLine();
            String[] first = line.split(",");
            for (int i = 1; i < first.length; i++) {
                digraph.addEdge(Integer.parseInt(first[0]),
                                Integer.parseInt(first[i]));
            }
        }
    }

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
    // of noun1 and noun2
    public String sca(String noun1, String noun2) {
        if (!this.isNoun(noun1) || !this.isNoun(noun2))
            throw new IllegalArgumentException("noun not in WordNet");

        int id1 = 0;
        int id2 = 0;

        // find vertex IDs of noun1 and noun2
        for (int i : map.keys()) { // iterate through vertices
            for (String j : map.get(i)) // iterate through queue at i
                if (j.equals(noun1)) {
                    id1 = i;
                    break; // break when noun found
                }
        }
        for (int i : map.keys()) {
            for (String j : map.get(i))
                if (j.equals(noun2)) {
                    id2 = i;
                    break;
                }
        }
        ShortestCommonAncestor sca = new ShortestCommonAncestor(digraph);
        return map.get(sca.ancestor(id1, id2)).peek();
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        if (!this.isNoun(noun1) || !this.isNoun(noun2)) {
            throw new IllegalArgumentException("noun not in WordNet");
        }

        int id1 = 0;
        int id2 = 0;

        for (int i : map.keys()) {
            for (String j : map.get(i))
                if (j.equals(noun1)) {
                    id1 = i;
                    break;
                }
        }
        for (int i : map.keys()) {
            for (String j : map.get(i))
                if (j.equals(noun2)) {
                    id2 = i;
                    break;
                }
        }
        ShortestCommonAncestor sca = new ShortestCommonAncestor(digraph);
        return sca.length(id1, id2);
    }

    // unit testing (required)
    public static void main(String[] args) {
        WordNet word = new WordNet("synsets11.txt",
                                   "hypernyms11ManyPathsOneAncestor.txt");
        // WordNet word2 = new WordNet("synsets3.txt",
        //                            "hypernmyms3.txt");
        // WordNet word1 = new WordNet("synsets.txt",
        //                             "hypernyms.txt");
        for (String s : word.nouns())
            System.out.println(s);
        String a = "a";
        String b = "b";
        String z = "z";
        String k = "k";
        System.out.println("a is in the wordNet " + word.isNoun(a));
        System.out.println("k is in the wordNet " + word.isNoun(k));
        System.out.println("z is in the wordNet " + word.isNoun(z));
        System.out.println("distance from a to b is " + word.distance(a, b));
        System.out.println("common ancestor of a and b is " + word.sca(a, b));

    }
}

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SeparateChainingHashST;

public class WordNet {
    // map vertex ID to synset
    private SeparateChainingHashST<Integer, String> mapOfInts;
    // for ID of the vertices and queues
    private ShortestCommonAncestor sca;
    // map noun to set of vertex IDs
    private SeparateChainingHashST<String, Queue<Integer>> mapOfStrings;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In file1 = new In(synsets);
        In file2 = new In(hypernyms);
        int size = 0;
        mapOfInts = new SeparateChainingHashST<>();
        mapOfStrings = new SeparateChainingHashST<>();

        // read synsets.txt and store it in hash map and set
        while (!file1.isEmpty()) {
            String line = file1.readLine();
            String[] first = line.split(",");
            mapOfInts.put(Integer.parseInt(first[0]), first[1]);
            String[] second = first[1].split(" ");
            for (int i = 0; i < second.length; i++) {
                if (mapOfStrings.contains(second[i])) {
                    mapOfStrings.get(second[i]).enqueue(
                            Integer.parseInt(first[0]));
                }
                else {
                    Queue<Integer> queue = new Queue<>();
                    queue.enqueue(Integer.parseInt(first[0]));
                    mapOfStrings.put(second[i], queue);
                }
            }
            size++;
        }

        Digraph digraph = new Digraph(size);
        // add edges to digraph from hypernyms.txt
        while (!file2.isEmpty()) {
            String line = file2.readLine();
            String[] first = line.split(",");
            for (int i = 1; i < first.length; i++) {
                digraph.addEdge(Integer.parseInt(first[0]),
                                Integer.parseInt(first[i]));
            }
        }
        sca = new ShortestCommonAncestor(digraph);
    }

    // the set of all WordNet nouns
    public Iterable<String> nouns() {
        return mapOfStrings.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return mapOfStrings.contains(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2
    public String sca(String noun1, String noun2) {
        if (!this.isNoun(noun1) || !this.isNoun(noun2))
            throw new IllegalArgumentException("noun not in WordNet");

        Queue<Integer> queue1 = new Queue<>();
        Queue<Integer> queue2 = new Queue<>();

        // find vertex IDs of noun1 and noun2
        for (String noun : mapOfStrings.keys()) {
            if (noun.equals(noun1)) {
                queue1 = mapOfStrings.get(noun1);
            }
        }
        for (String noun : mapOfStrings.keys()) {
            if (noun.equals(noun2)) {
                queue1 = mapOfStrings.get(noun2);
            }
        }
        return mapOfInts.get(sca.ancestorSubset(queue1, queue2));
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        if (!this.isNoun(noun1) || !this.isNoun(noun2))
            throw new IllegalArgumentException("noun not in WordNet");

        Queue<Integer> queue1 = new Queue<>();
        Queue<Integer> queue2 = new Queue<>();

        // find vertex IDs of noun1 and noun2
        for (String noun : mapOfStrings.keys()) {
            if (noun.equals(noun1)) {
                queue1 = mapOfStrings.get(noun1);
            }
        }
        for (String noun : mapOfStrings.keys()) {
            if (noun.equals(noun2)) {
                queue1 = mapOfStrings.get(noun2);
            }
        }
        return sca.lengthSubset(queue1, queue2);
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

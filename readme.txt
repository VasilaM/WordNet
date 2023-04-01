Programming Assignment 6: WordNet


/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the
 *  information in synsets.txt. Why did you make this choice?
 **************************************************************************** */
We used two hash maps to store information in sysnsets.txt. One mapped
the IDs to the synsets and the other mapped each noun to a queue of
synset IDs that it appeared in. We made this choice because hash maps have
constant amortized time under the uniform hasing assumption, which fit within
the bounds of the assignment. We used the hash map that mapped nouns to
IDs to implement nouns() and hasNoun() because hash map keys are iterable and
have a contains() method. We also used this hash map for sca() and distance()
to get the queues of vertex IDs that each noun appeared in. We used the hash map
that mapped IDs to synsets for sca() because we needed to get the synset(rather
than the individual noun) given the vertex ID of the sca.


/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the
 *  information in hypernyms.txt. Why did you make this choice?
 **************************************************************************** */
We stored the information in hypernyms.txt in a digraph because we needed to
make a rooted DAG with information that connected synsets to each other.
Digraphs have the performance guarentee of big theta(E + V).


/* *****************************************************************************
 *  Describe concisely the algorithm you use in the constructor of
 *  ShortestCommonAncestor to check if the digraph is a rooted DAG.
 *  What is the order of growth of the worst-case running times of
 *  your algorithm? Express your answer as a function of the
 *  number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.) Use Big Theta notation to simplify
 *  your answer.
 **************************************************************************** */

Description: We use DirectedCycle.java to check if the digraph has a cycle
and iterate over each vertex and make sure exactly one vertex has an outdegree
of zero.



Order of growth of running time: (V + E)


/* *****************************************************************************
 *  Describe concisely your algorithm to compute the shortest common ancestor
 *  in ShortestCommonAncestor. For each method, give the order of growth of
 *  the best- and worst-case running times. Express your answers as functions
 *  of the number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.) Use Big Theta notation to simplify your
 *  answers.
 *
 *  If you use hashing, assume the uniform hashing assumption so that put()
 *  and get() take constant time per operation.
 *
 *  Be careful! If you use a BreadthFirstDirectedPaths object, don't forget
 *  to count the time needed to initialize the marked[], edgeTo[], and
 *  distTo[] arrays.
 **************************************************************************** */

Description: To compute the sca, we initialize two BreadthFirstDirectedPaths
objects for verticies v and w. Then, we pass these objects into a private
helper method that returns the vertex ID of the sca. The helper method
initalizes an int variable to keep track of the minimum distance and loops
through all the vertices. For each vertex, if both v and w have a path to it,
we compute the distance from the vertex to both v and w. If the distance is
less than the minimum distance found so far, we update the minimum distance
and store the index of the vertex.


                                 running time
method                  best case            worst case
--------------------------------------------------------
length()                  V                    E + V

ancestor()                V                    E + V

lengthSubset()            V                    E + V

ancestorSubset()          V                    E + V



/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

None

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

None

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */

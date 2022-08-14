/* DFA_Semi.java
** An instance of this class represents a deterministic finite semi-automaton
** (i.e., a semi-DFA).
** What distinguishes a semi-DFA from a DFA is that in the former the
** initial state and the set of final states are left unspecified.
** Essentially, then, a semi-DFA is a triple (Q, Sigma, delta), where Q is
** the state set, Sigma is the alphabet, and delta (the transition function)
** is a mapping from Q x Sigma to Q.  For simplicity, here Q = [0..N) and 
** Sigma = [0..M), where N and M are the number of states and the size of the
** input alphabet, respectively.  delta(p,k) == q corresponds to there being 
** a transition from state p to state q labeled by symbol k.
**
** Authors: R. McCloskey and Ciara Trigg
** Collaborated with: Zack Danchak
** Known Defects: ...
*/

public class DFA_Semi {

   // instance variables
   // ------------------

   private final int N;   // # of states in this DFA
   private final int M;   // # symbols in this DFA's alphabet
   private int[][] delta; // delta[p][k] == q means that the outgoing transition
                          // from state p labeled by symbol k goes to state q

   // constructors
   // ------------

   /* Initializes this Semi-DFA to have the given transition function,
   ** which implicitly determines the # of states and the alphabet size.
   ** pre: Letting N = deltaFunc.length and M = deltaFunc[0].length:
   **      N > 0 && M > 0 && deltaFunc is rectangular (as opposed to
   **      ragged), which is to say that deltaFunc[k].length = M for
   **      all k.
   */
   public DFA_Semi(int[][] deltaFunc) {
      N = deltaFunc.length;
      M = deltaFunc[0].length;
      delta = new int[N][M];
      for (int i=0; i != N; i++) {
         for (int j=0; j != M; j++) {
            delta[i][j] = deltaFunc[i][j];
         }
      }
   }


   /* Initializes this semi-DFA to have the specified number of states and
   ** the specified number of symbols in its alphabet.  All transitions are 
   ** set to be undefined, which, in effect, means that they go to an 
   ** implicit dead state, indicated by -1.  The expectation is that the
   ** setTransition() method will be used to specify the intended transitions.
   ** pre: numStates > 0  &&  alphaSize > 0
   */
   public DFA_Semi(int numStates, int alphaSize) {
      N = numStates;
      M = alphaSize;
      delta = new int[N][M];
      for (int i = 0; i != N; i++) {
         for (int j = 0; j != M; j++) {
            delta[i][j] = -1;  // in effect, a transition to the dead state
         }
      }
   }


   // observers
   // ---------

   /* Returns the number of states in this Semi-DFA.  (The IDs of the
   ** states are the integers in the range [0..numberOfStates().)
   */
   public int numberOfStates() { return N; }

   /* Returns the number of symbols in the alphabet of this Semi-DFA.  (The
   ** IDs of the symbols are the integers in the range [0..alphabetSize()).)
   */
   public int alphabetSize() { return M; }

   /* Returns delta(p,k) (i.e., the state to which state p has an outgoing
   ** transition on symbol k, or -1 if no such transition has been established).
   ** pre: 0 <= p < numberOfStates() && 0 <= k < alphabetSize()
   */
   public int delta(int p, int k) { return delta[p][k]; }


   /* Returns delta*(p,x), where p is a state ID and x is a "string" of 
   ** symbol IDs.  That is, what is returned is the ID of the state reached
   ** from p by traversing the walk labeled by x.  (If -1 is returned, it 
   ** means that the walk could not be completed due to a missing transition
   ** (which could be interpreted to mean that it reached the implicit dead 
   ** state).)
   ** pre: 0 <= p < numberOfStates()  &&  
   **      for all i in [0..x.length), 0 <= x[i] < alphabetSize()
   */
   public int deltaStar(int p, int[] x) {
      int cur = 0; 
      for(int i = 0; i < x.length; i++){
         cur = delta(p, x[i]);
         if(cur == -1){
            return -1; 
         }
      }
      return cur;  // STUB!
   }

   /* Prints a description of this semi-DFA.
   */
   public void print() {
      System.out.printf("Number of states: %d\n", numberOfStates());
      System.out.printf("Alphabet size: %d\n", alphabetSize());
      for (int p = 0; p != numberOfStates(); p++) {
         for (int k = 0; k != alphabetSize(); k++) {
            System.out.printf("  delta(%d,%d) = %d\n", p, k, delta(p,k));
         }
      }
   }


   // mutators
   // --------

   /* Establishes delta(p,k) = q.  That is, it sets the outgoing transition
   ** from state p on symbol k to be directed to state q.
   ** pre: 0 <= p,q < numberOfStates() &&
   **      0 <= k < alphabetSize()
   */
   public void setTransition(int p, int k, int q) { delta[p][k] = q; }


   // private methods
   // ---------------

}
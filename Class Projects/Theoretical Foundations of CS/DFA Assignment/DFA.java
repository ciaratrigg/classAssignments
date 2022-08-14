/* An instance of this class represents a DFA (deterministic finite
** automaton).  The states of a DFA are identified by a contiguous range 
** of natural numbers starting at zero, as are the members of its input 
** alphabet.
**
** Author: R. McCloskey and Ciara Trigg
** Collaborated with: Zack Danchak
** Known defects: Fails to return all living states
*/

public class DFA extends DFA_Semi {

   // instance variables
   // ------------------

   private int initialState;             // ID of initial state
   private SetOverIntRange finalStates;  // set of IDs of final states


   // constructors
   // ------------

   /* Initializes this DFA so that its initial state and set of final states
   ** are as indicated by the first two parameters, respectively, and so that
   ** its transition function is as indicated by the third. 
   ** The meaning of delta[k][j] == m is that the outgoing transition from
   ** state k labeled by symbol j goes to state m.  The number of rows and
   ** columns in delta[][] implicitly indicate, respectively, the number of 
   ** states and the alphabet size.
   **
   ** pre: Letting N = delta.length and M = delta[0].length:
   **  init is in [0..N) &&
   **  finals is a subset of [0..N) &&
   **  delta[k].length = M for all k (i.e., delta is rectangular, not ragged) &&
   **  for all k in [0..N) and j in [0..M), delta[k][j] is in [0..N)
   */
   public DFA(int init, SetOverIntRange finals, int[][] delta) {
      super(delta);
      initialState = init;
      finalStates = finals.clone(0, numberOfStates());
   }

   /* Initializes this DFA to have a number of states, alphabet size, 
   ** initial state, and set of final states as specified by the parameters.
   ** All transitions are made to go to the implicit dead state, -1.
   ** pre: numStates > 0 && alphaSize > 0 && 
   **      initial is in [0..numStates) &&
   **      finals is a subset of [0..numStates).
   */
   public DFA(int numStates, int alphaSize, int init, SetOverIntRange finals) {
      super(numStates, alphaSize);
      initialState = init;
      finalStates = finals.clone(0, numberOfStates());
   }

   /* Initializes this DFA to have a number of states and an alphabet size as
   ** specified by the parameters.  State 0 is made to be the initial state
   ** and the set of final states is made to be empty.
   ** All transitions are made to go to the implicit dead state, -1.
   ** pre: numStates > 0 && alphaSize > 0 
   */
   public DFA(int numStates, int alphaSize) {
      this(numStates, alphaSize, 0, new SetOverIntRange(0,numStates-1));
   }


   // observers
   // ---------

   
   /* Returns (the ID of) the initial state of this DFA.
   */
   public int initialState() { return initialState; }


   /* Returns (a clone of) the set of final states of this DFA.
   */
   public SetOverIntRange finalStates() { return finalStates.clone(); }


   /* Reports whether the specified state (k) is among the final states
   ** of this DFA.
   */
   public boolean isFinal(int k) { return finalStates.isMemberOf(k); }


   /* Returns the set containing the IDs of precisely those states that 
   ** are alive.  Such a state is one from which a final state is
   ** reachable by some sequence of transitions.
   */
   public SetOverIntRange liveStates() {
      int numStates = this.numberOfStates();
      int initState = this.initialState();
      SetOverIntRange alive = new SetOverIntRange(0, numStates);
      for(int i = initState; i < numStates; i++){
         if(search(i)){
            alive.insert(i);
         }
      }
      return alive;     // STUB
   }
   
   public boolean isAlive(int state){
      SetOverIntRange alive = this.liveStates(); 
      return alive.isMemberOf(state); 
   }
   
   public boolean search(int start){
      int numStates = this.numberOfStates();
      for(int i = start; i < numStates; i++){
         if(this.isFinal(i)){
            return true;
         }
      }
      return false; 
   }

   /* Reports whether or not this DFA accepts the given input string.
   */
   public boolean accepts(int[] x) {
      int initState = this.initialState(); 
      if(this.isFinal(deltaStar(initState, x))){
         return true;
      }
      return false;   // STUB
   }

   /* Prints a description of this DFA.
   */
   public void print() {
      super.print();
      System.out.printf("Initial state: %d\n", initialState());
      System.out.printf("Final states: %s\n", finalStates());
   }


   // mutators
   // --------

   /* Makes the specified state be the initial state of this DFA.
   */
   public void setInitial(int newInit) {
      initialState = newInit;
   }

   /* Designates the specified state as being either final or non-final
   ** in this DFA, according to the value of the second parameter.
   ** pre: 0 <= state < numberOfStates()
   */
   public void setFinalStatus(int state, boolean isFinal) {
      if (isFinal) 
         { finalStates.insert(state); }
      else 
         { finalStates.remove(state); }
   }

   /* Designates the set of final states of this DFA to be those in the 
   ** given set.
   */
   public void setFinals(SetOverIntRange newFinals) {
      finalStates = newFinals.clone();
   }

}
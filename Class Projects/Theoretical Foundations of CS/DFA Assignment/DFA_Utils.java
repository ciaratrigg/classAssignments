/* Java class that includes methods that solve a few DFA decision problems.
**
** Authors: R. McCloskey and Ciara Trigg
** Collaborated with: Zack Danchak
** Known defects: Fails to correctly determine whether string x is a substring 
*/
public class DFA_Utils {


   /* Decides the question of whether any prefix of x is a member
   ** of L(M).
  
   ** Starting at the initial state of M, follow the sequence of transitions 
   ** that spell out x. If any state on that path is final, answer YES. 
   ** Otherwise (i.e., all states on that path are non-final), answer NO.
   */
   public static boolean hasMemberPrefix(DFA M, int[] x) {
      int init = M.initialState(); //initial state of M
      int nextNode = M.delta(init, x[0]); //the state of M after transitioning from the initial state on the first element of x
      if(M.isFinal(init)){//checks if initial state is final
         return true;
      }
      for(int i = 1; i < x.length; i++){ //performs each transition specificed in x
         if(M.isFinal(nextNode)){
            return true; //if the state is final, answer YES
         }
         else{
            nextNode = M.delta(nextNode, x[i]);//if the state is not final, continue searching on another transition from x
         }
      }
      return false;  //all states on the path are non-final
      // STUB
   }

   /* Decides the question of whether x is a suffix of any member
   ** of L(M).
   
   ** The string x is the suffix of some member of L iff there exists a 
   ** state q in M such that the sequence of transitions beginning there 
   ** and spelling out x ends in a final state.
   */
   public static boolean isSuffixOfMember(DFA M, int[] x) {
      int init = M.initialState(); //initial state of M
      for(int cur = init; cur < M.numberOfStates(); cur++){ //traverses each node in the DFA
         if(M.isFinal(M.deltaStar(cur, x))){ //performs transitions on x from the current node and 
                                             //determines if the resulting state is final
            return true;
         }
      }
      return false;   // STUB
   }

   /* Decides the question of whether x is a substring of any member
   ** of L(M).
   
   ** The string x is a substring of some member of L iff there exists a 
   ** state q in M such that the sequence of transitions beginning there 
   ** and spelling out x ends in a non-dead state (meaning a state from which 
   ** it is possible to reach a final state).
   */
   public static boolean isSubstringOfMember(DFA M, int[] x) {
      int init = M.initialState(); //initial state of M
      for(int cur = init; cur < M.numberOfStates(); cur++){ //traverses each node in the DFA
         if(M.isAlive(M.deltaStar(cur, x))){ //performs transitions on x from the current node and
                                                //determines if the resulting state is alive
            return true;
         }
      }
      return false;   // STUB
   }

}
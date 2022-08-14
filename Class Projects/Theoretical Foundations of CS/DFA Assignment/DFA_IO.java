import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/* Java class that has static methods to perform input/output with respect to 
** instances of the DFA class.  One method reads a description of an DFA (in
** the format prescribed below) and builds the corresponding instance of 
** the DFA class.  The other takes an instance of the DFA class and produces
** a description of it, in that same format.
**
** Author: R. McCloskey
** Date: June 2021 (last modified)
*/

public class DFA_IO {

   /* Reads a textual description of a deterministic finite automaton (DFA)
   ** via the given Scanner and returns a representation of it in the
   ** form of an instance of the DFA class.
   ** The prescribed format for a textual DFA description is exemplified 
   ** by the following "image" of a file:
   ** +-----------------------------------------------------------------+
   ** |5         // number of states
   ** |2         // alphabet size
   ** |0         // ID of the initial state
   ** |0 2       // IDs of the final/accepting state(s)
   ** |0 3 0     // transitions from state 0 go to states 3 and 0
   ** |1 2 0     // transitions from state 1 go to states 2 and 0
   ** |4 1 4     // transitions from state 4 go to states 1 and 4
   ** |2 4 3     // transitions from state 2 go to states 4 and 3
   ** |3 2 1     // transitions from state 3 go to states 2 and 1
   ** +-----------------------------------------------------------------+
   ** 
   ** The first and second lines, respectively, indicate the # of states in
   ** the DFA and the size of its alphabet.  
   ** The third and fourth lines identify, respectively, the initial state
   ** and the final state(s).  (States are numbered starting at zero, as are 
   ** the symbols in the alphabet.  Thus, here the state IDs are integers in
   ** the range [0..5) and the symbol IDs are integers in the range [0..2).)
   ** Each subsequent line identifies, for one state, the targets of each
   ** of its outgoing transitions.  First the state ID is given, followed
   ** by the IDs of the states to which go its transitions on input symbols
   ** 0, 1, etc.  
   */
   public static DFA read(Scanner input) {
      // read # states
      int N = input.nextInt();  input.nextLine();

      // read alphabet size
      int M = input.nextInt();  input.nextLine();

      // Create a DFA with that many states and an alphabet of that size.
      DFA dfa = new DFA(N,M);

      // Read the ID of the initial state and set it.
      int initState = input.nextInt();  input.nextLine();
      dfa.setInitial(initState);

      // Read the IDs of the final states, and set them.
      String line = input.nextLine();
      Scanner lineScanner = new Scanner(line);
      while (lineScanner.hasNextInt()) {
         int q = lineScanner.nextInt();
         dfa.setFinalStatus(q, true);
      }

      // What remains is to read the transitions and to place them into the dfa.
      while (input.hasNext()) {
         line = input.nextLine();
         lineScanner = new Scanner(line);
         if (lineScanner.hasNextInt()) {
            // first number on line is ID of the source state
            int state = lineScanner.nextInt();
            // subsequent numbers are IDs of target states of the transitions
            // on symbols with IDs in [0..M).
            for (int i = 0; i != M; i++) { 
               int targetState = lineScanner.nextInt();
               dfa.setTransition(state, i, targetState);
            }
         }
         else {
            // ignore line whose first token is not an integer numeral
         }
      }
      return dfa;
   }


   /* Uses the given PrintWriter to write a textual description of the 
   ** given DFA (in the format described above).
   */
   public static void write(DFA dfa, PrintWriter pw) {
      final int N = dfa.numberOfStates();
      final int M = dfa.alphabetSize();
      pw.printf("%d   // Number of states\n", N);
      pw.printf("%d   // Alphabet size\n", M);
      pw.printf("%d   // Initial state\n", dfa.initialState());
      printSetElems(dfa.finalStates(), pw);
      pw.println("   // final states");
      // print descriptions of the transitions
      for (int state = 0; state != N; state++) {
         pw.printf("%d ", state);
         for (int symbol = 0; symbol != M; symbol++) {
            pw.printf("%d ", dfa.delta(state, symbol));
         }
         pw.println();
      }
      pw.println();
   }  


   private static void printSetElems(SetOverIntRange s, PrintWriter pw) {
      int floor = s.lowerBound();
      int ceiling = s.upperBound();
      for (int r = floor; r <= ceiling; r++) {
         if (s.isMemberOf(r)) 
            { pw.printf("%d ", r); }   
      }
   }


   // main method (for testing purposes)
   // ----------------------------------

   /* This method expects two command line arguments, the first of which is
   ** the name of a file containing a textual description of a DFA (in the
   ** format described above) and the second being the name of a file into
   ** which a textual description of the same DFA is to be written.
   ** That is, this method uses the read() method to construct a DFA object
   ** (using as input the file named args[0]) and then uses write() to 
   ** produce a textual description of that object, writing it into the
   ** file named by args[1].
   */
   public static void main(String[] args) throws FileNotFoundException {
      Scanner scanner = new Scanner(new File(args[0]));
      DFA dfa = read(scanner);

      PrintWriter prtW = new PrintWriter(args[1]);
      write(dfa, prtW);
      prtW.close();
   }

}
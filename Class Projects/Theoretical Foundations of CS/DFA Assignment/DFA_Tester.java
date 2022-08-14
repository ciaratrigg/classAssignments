import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/* Java application for the purpose of testing the classes DFA, its
** parent DFA_Semi, and DFA_Utils. 
**
** Author: R. McCloskey
** Date: April 2022
*/
public class DFA_Tester {

   private static Scanner keyboard = new Scanner(System.in);

   private static final int QUIT = 0;
   private static final int DISPLAY = 1;
   private static final int DELTA_STAR = 2;
   private static final int ACCEPTS = 3;
   private static final int HAS_MEMBER_PREFIX = 4;
   private static final int IS_SUFFIX_OF_MEMBER= 5;
   private static final int IS_SUBSTRING_OF_MEMBER= 6;
   private static final int LIVE_STATES = 7;

   /* The command line argument is assumed to hold the name of a file
   ** containing a textual description of an DFA (in a format consistent
   ** with that expected by the DFA_IO class).
   */
   public static void main(String[] args) throws FileNotFoundException {
      if (args.length == 0) {
         System.out.println("** Error; expecting command-line argument to " +
                            "provide name of input file");
         System.out.println("** Program aborting.");
         System.exit(1);
      }
      Scanner dfaInput = new Scanner(new File(args[0]));
      DFA dfa = DFA_IO.read(dfaInput);

      System.out.println("This DFA has been loaded:");
      System.out.println("-------------------------");
      dfa.print();

      boolean keepGoing = true;
      while (keepGoing) {
         printMenu();
         int choice = getInt();
         //debugging:
           //System.out.println("getInt() returned " + choice);
         if (choice == QUIT) 
            { keepGoing = false; }
         else if (choice == DISPLAY) 
            { dfa.print(); }
         else if (choice == DELTA_STAR) 
            { test_deltaStar(dfa); }
         else if (ACCEPTS <= choice  &&  choice <= IS_SUBSTRING_OF_MEMBER) {
            testString(dfa, choice);
         }
         else if (choice == LIVE_STATES) {
            System.out.printf("Live states are %s\n", dfa.liveStates());
         }
         else { 
            System.out.println("Invalid response");
         }
      }

      System.out.println("\nGoodbye.");
   }

   private static int getInt() {
      int result;
      String response = keyboard.nextLine().trim();
      try {
         result = Integer.parseInt(response);
      }
      catch (Exception e) {
         result = -9;
         System.out.println("Invalid response; treating as -9");
      }
      return result;
   }


   private static void printMenu() {
      System.out.println();
      System.out.println(QUIT + ": Quit");
      System.out.println(DISPLAY + ": Display DFA");
      System.out.println(DELTA_STAR + ": Test deltaStar() in DFA_Semi");
      System.out.println(ACCEPTS + ": Test accepts() in DFA");
      System.out.println(HAS_MEMBER_PREFIX + ": Test hasMemberPrefix() in DFA_Utils");
      System.out.println(IS_SUFFIX_OF_MEMBER + ": Test isSuffixOfMember() in DFA_Utils");
      System.out.println(IS_SUBSTRING_OF_MEMBER + ": Test isSubstringOfMember() in DFA_Utils");
      System.out.println(LIVE_STATES + ": Test liveStates() in DFA");
      System.out.print(">");
   }
      

   public static void test_deltaStar(DFA dfa) {
      boolean keepGoing = true;
      while (keepGoing) {
         System.out.print("\nEnter state ID (-1 to quit, -2 to display DFA): ");
         int q = getInt();
         //keyboard.nextLine();
         if (q == -2)
            { dfa.print(); }
         else if (q == -1)
            { keepGoing = false; }
         else if (q < dfa.numberOfStates()) {
            System.out.print("Enter input string: ");
            String str = keyboard.nextLine().trim();
            int[] x = toIntArray(str, dfa.alphabetSize()-1);
            if (x != null) {
               int reachedState = dfa.deltaStar(q,x);
               System.out.printf("delta*(%d,%s) = %d\n", q, str, reachedState);
            }
         }
         else {
            System.out.print("ERROR: Invalid input");
         }
      }
   }


   private static void testString(DFA dfa, int whichTest) {
      boolean keepGoing = true;
      while (keepGoing) {
         System.out.print("\nEnter input string (X to quit, Y to display DFA): ");
         String str = keyboard.nextLine().trim();
         int strLen = str.length();
         if (strLen != 0  &&  str.charAt(0) == 'X') {
            keepGoing = false;
         }
         else if (strLen != 0  &&  str.charAt(0) == 'Y') {
            dfa.print();
         }
         else {
            int[] x = toIntArray(str, dfa.alphabetSize()-1);
            if (x != null) {
               System.out.print(str);

               if (whichTest == ACCEPTS) {
                  if (dfa.accepts(x)) { System.out.print(" IS "); }
                  else { System.out.print(" is NOT "); }
                  System.out.println("accepted.");
               }
               else if (whichTest == HAS_MEMBER_PREFIX) {
                  if (DFA_Utils.hasMemberPrefix(dfa, x))
                     { System.out.print(" has "); }
                  else 
                     { System.out.print(" does not have "); }
                  System.out.println("a prefix that is a member.");
               }
               else if (whichTest == IS_SUFFIX_OF_MEMBER) {
                  if (DFA_Utils.isSuffixOfMember(dfa, x))
                     { System.out.print(" is the suffix of some member."); }
                  else 
                     { System.out.print(" is not the suffix of any member."); }
               }
               else if (whichTest == IS_SUBSTRING_OF_MEMBER) {
                  if (DFA_Utils.isSubstringOfMember(dfa, x))
                     { System.out.print(" is the substring of some member."); }
                  else 
                     { System.out.print(" is not the substring of any member."); }
               }
               else {
                  System.out.println("Invalid choice");
               } 
            }
         }
      }
   }


   /* Given a String of symbol IDs, returns a corresponding int[] array
   ** each of whose elements is a single ID.  (Of course, this means that 
   ** the max ID is less than 10.)  If any of the characters in the string
   ** is not a valid symbol ID, null is returned.
   */
   private static int[] toIntArray(String s, int maxID) {
      int[] result = new int[s.length()];
      boolean goodSoFar = true;
      int i = 0;
      while (i != result.length && goodSoFar) {
         result[i] = s.charAt(i) - '0';
         if (result[i] < 0  ||  result[i] > maxID) {
            goodSoFar = false;
            System.out.println("** WARNING; invalid input string **");
         }
         i = i+1;
      }
      if (goodSoFar) 
         { return result; }
      else 
         { return null; }
   }

}
/* An instance of this class represents a subset of the integers in a range 
** [MIN..MAX] that is established at time of construction.
*/
public class IntSetBounded {

   // instance variables
   // ------------------

   private final int MIN;      // minimum number that can be a set member
   private final int MAX;      // maximum number that can be a set member
   private boolean[] isMember; // keeps track of which numbers are members

   // constructor
   // -----------

   /* Establishes this set to be an empty subset of the range [min..max].
   */
   public IntSetBounded(int min, int max) {
      MIN = min;
      MAX = max;
      isMember = new boolean[MAX-MIN+1];  // elements initially false by default
   }


   // observers
   // ---------

   /* Returns the minimum value that can be a member of this set.
   */
   public int minOf() { return MIN; }

   /* Returns the maximum value that can be a member of this set.
   */
   public int maxOf() { return MAX; }

   /* Returns the number of members of this set.
   */
   public int sizeOf() {
      int cntr = 0;
      for (int i = MIN; i <= MAX; i++) {
         if (isMember(i)) { cntr = cntr+1; }
      }
      return cntr;
   }

   /* Answers the question, "Is k a member of this set?".
   */
   public boolean isMember(int k) { return isMember[k - MIN]; }


   // mutators
   // --------

   /* Ensures that the given number (k) is a member of this set.
   ** pre: minOf() <= k <= maxOf()
   */
   public void include(int k) { isMember[k-MIN] = true; }

   /* Ensures that the given number (k) is not a member of this set.
   ** pre: minOf() <= k <= maxOf()
   */
   public void exclude(int k) { isMember[k-MIN] = false; }
  
}

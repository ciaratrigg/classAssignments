/* An instance of this class represents a subset of the integers in the
** interval [low..high] (referred to as the set's "universe"), where low 
** and high are chosen by the client at time of construction.  
**
** Author: R. McCloskey
** Date: March 2016
*/

public class SetOverIntRange {

   // instance variables
   // ------------------

   private final int FLOOR, CEILING;
   private boolean[] isMember; // isMember[i] iff FLOOR+i is a member of the set
   private int cardinality;    // # members


   // constructor
   // -----------

   /* Establishes this new set to be empty and for its universe of possible
   ** members to be in the specified range [low,high].
   */
   public SetOverIntRange(int low, int high) {
      FLOOR = low;
      CEILING = high;
      if (FLOOR <= CEILING) {
         isMember = new boolean[CEILING - FLOOR + 1]; // by default, all elems
      }                                               // are set to false
      cardinality = 0;
   }


   // observers
   // ---------

   /* Returns the lower bound of this set's universe.
   */
   public int lowerBound() { return FLOOR; }

   /* Returns the upper bound of this set's universe.
   */
   public int upperBound() { return CEILING; }

   /* Returns the cardinality of this set (i.e., the # of members)
   */
   public int cardinalityOf() { 
      return cardinality; 
      // alternative (if omitting instance variable 'cardinality'):
      // int cntr = 0;
      // for (int i = FLOOR; i <= CEILING; i++) {
      //    if (isMemberOf(i)) { cntr++; }
      // }
      // return cntr;
   }

   /* Reports whether or not this set is empty.
   */
   public boolean isEmpty() { return cardinalityOf() == 0; }


   /* Reports whether or not the specified integer is a member of this set.
   */
   public boolean isMemberOf(int k) {
      return lowerBound() <= k  &&  k <= upperBound() &&  isMember[k-FLOOR];
   }

   /* Reports whether this set and the given object are equal, which
   ** requires that object to be an instance of this class and to have 
   ** precisely the same members.
   */
   @Override
   public boolean equals(Object obj) {
      return (obj instanceof SetOverIntRange) &&  
             (equals((SetOverIntRange)obj));
   }

   /* Reports whether this set and the given one have precisely the
   ** same members.
   */
   public boolean equals(SetOverIntRange S) {
      int low = Math.min(this.lowerBound(), S.lowerBound());
      int high = Math.max(this.upperBound(), S.upperBound());
      int m = low;
      // loop invariant: Each number in [low..m) is either a member of
      //                 both 'this' and S, or a member of neither.
      while (m <= high  &&  this.isMemberOf(m) == S.isMemberOf(m)) {
         m = m+1;
      }
      return m == high+1;
   }

   /* Reports whether this set and the given one have precisely the
   ** same universe and the same members.
   */
   public boolean equalsStrict(SetOverIntRange S) {
      return this.lowerBound() == S.lowerBound() &&
             this.upperBound() == S.upperBound() &&
             this.equals(S);
   }

   /* Returns a string showing the members of this set, separated by
   ** oommas and sandwiched between curly braces.  E.g., "{5,7,12}"
   */
   public String toString() {
      StringBuilder result = new StringBuilder();
      final int max = upperBound();
      for (int i = lowerBound(); i <= max; i++) {
         if (isMemberOf(i)) { result.append("," + i); }
      }
      if (result.length() == 0) { return "{}"; }
      else { return "{" + result.substring(1) + "}"; }
   }


   // mutators
   // --------

   /* Ensures that the specified integer is a member of this set.
   ** pre: lowerBound() <= k <= upperBound()
   ** post: isMemberOf(k)
   */
   public void insert(int k) { 
      if (!isMember[k-FLOOR]) {
         isMember[k-FLOOR] = true;
         cardinality++;
      }
   }

   /* Ensures that the specified integer is not a member of this set.
   ** post: !isMemberOf(k)
   */
   public void remove(int k) { 
      if (FLOOR <= k  &&  k <= CEILING &&  isMember[k-FLOOR]) {
         isMember[k-FLOOR] = false;
         cardinality--;
      }
   }

   /* Removes all members from this set, resulting in it being empty.
   */
   public void clear() {
      for (int i = CEILING; i != -1; i--) { remove(i); }
   }

   /* Inserts all members of the universe into this set, resulting in it 
   ** being equal to its universal set.
   */
   public void fill() {
      for (int i = FLOOR; i != CEILING+1; i++) { insert(i); }
   }



   // generators
   // ----------

   /* Returns a new set having the universe indicated by the two
   ** parameters and that is the intersection of that universe
   ** and this set.
   */
   public SetOverIntRange clone(int low, int high) {
      SetOverIntRange result = new SetOverIntRange(low, high);
      int lowMax = Math.max(low, FLOOR);
      int highMin = Math.min(high, CEILING);
      for (int i = lowMax; i <= highMin; i++) {
         if (isMemberOf(i)) { result.insert(i); }
      }
      return result;
   }

   /* Returns a new set that is identical to this one (in both
   ** membership and universe).
   */
   public SetOverIntRange clone() {
      return clone(FLOOR, CEILING);
      // Alternative:
      //SetOverIntRange result = new SetOverIntRange(FLOOR, CEILING);
      //result.isMember = this.isMember.clone();
      //return result;
   }

}
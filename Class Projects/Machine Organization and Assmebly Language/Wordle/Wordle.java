/* Application that allows for the playing of the simple word game known as
   "Wordle" while also keeping track of the number of words in the dictionary
   used to play the game that remain "candidates" to be the target word.
   
   Author: P.M.J.
   Course: CMPS 250
   Date: Spring 2022
*/
import java.io.*;
import java.util.*;
public class Wordle {

   public static final int TRIES_MAX = 9; //The maximum number of tries allowed
   
   public static void main(String args[]) throws FileNotFoundException {
      final int tries;
      int targetIndex;
      if(args.length > 1) {
         //Construct the dictionary
         Dictionary d = new Dictionary(args[0]);
         //Set the number of tries
         tries = Math.min(Integer.parseInt(args[1]),TRIES_MAX);
         //Determine the index of the target word
         if(args.length == 2) {
            targetIndex = -1; //A placeholder value to trigger randomization
         } else {
            targetIndex = Integer.parseInt(args[2]);
         }
         //Determine a target index randomly as needed
         if(!d.isValidIndex(targetIndex)) {
            targetIndex = new Random().nextInt(d.wordsIn());
         }
         String targetWord = d.getWordAt(targetIndex);
         //Echo game values
         System.out.print("Target word at " + targetIndex); 
         //////////////////////////////////////////////////////////////
         System.out.print(" is \"" + targetWord + "\"");
         //////////////////////////////////////////////////////////////
         System.out.println();
         //Play the game
         play(d,targetWord,tries);
      } else {
         throw new IllegalArgumentException("filename and tries arguments required");
      }
   }
   
   /* Carries out the process of playing the game, which includes utilizing
      the attribute values of the words in the dictionary to indicate their
      candidacy as solutions.
   */
   public static boolean play(Dictionary d, String word, int tries) {
      Scanner keyboard = new Scanner(System.in);
      int attempt = 0;
      String[] guess = new String[tries];    //Keeps each guess
      String[] feedback = new String[tries]; //Keeps each corresponding feedback
      d.setAttributes(true);        //Set the candidacy of all words to true
      boolean correctGuess = false;
      //Loop until the word is guessed or attempts are exhausted
      while (!correctGuess && (attempt < tries)) {
         System.out.println("\n" + d.attributesIn() + " candidates!");
         boolean validGuess;
         //Loop to prompt for, read and validate each guessed word
         do {
            System.out.print("Guess " + (attempt+1) + " of " + tries + ":>");
            guess[attempt] = keyboard.nextLine().toUpperCase();
            validGuess = (guess[attempt].length() == Dictionary.WORD_LENGTH) && 
                         Dictionary.isValidWord(guess[attempt]);
            if((guess[attempt].length() > 0) && (guess[attempt].charAt(0) == '?')) {
               printCandidates(d);
            }
            if(!validGuess) {
               System.out.println("ERROR >>> must be " + Dictionary.WORD_LENGTH + 
                                  " letters\n");
            } else {
               //Verify that the guess is a valid word in the dictionary
               //validGuess = inDictionary(d,guess[attempt]);
               if(!validGuess) {
                  System.out.println("ERROR >>> word not in the dictionary\n");
               }
            }
         } while(!validGuess);
         //Formulate the feedback response for the user
         feedback[attempt] = formFeedback(guess[attempt],word);
         //Print the sequence of feedback responses
         for(int i=attempt; i>=0; i--) {
            System.out.println("       |" + guess[i] + "|" + feedback[i] + "|");
         }
         //Determine if the guess is correct
         correctGuess = guess[attempt].equals(word);
         if(!correctGuess) {
            //Specifically remove the incorrect guess from the candidates
            reduceCandidates(d,guess[attempt],feedback[attempt]);
         }
         attempt = attempt + 1;
      }
      if(correctGuess) {
         System.out.println("              !!!!! You got it in " + 
                                          attempt + " attempts!");
      } else {
         System.out.println("              !!!!! Sorry! The word was!");
         System.out.println("             |" + word + "|");
      }
      return true;
   }
   
   /* Returns true if and only if the given word is in the given dictionary.
   */
   public static boolean inDictionary(Dictionary d, String word) {
      boolean result = false;
      for(int i=0; !result && (i<d.wordsIn()); i++) {
         result = word.equals(d.getWordAt(i));
      }
      return result;
   }
   
   // Constants used in formulating the feedback
   public static final char BLANK = ' ';  
   public static final char OTHER = '?';  //Letter appears elsewhere in word
   public static final char NOTIN = '*';  //Letter does not appear in word 
   
   /* Returns a string to serve as the feedback to the user, where an upper-
      case letter indicates the corresponding letter in the guess appears in
      the exact position, a lower-case letter indicates the corresponding
      letter does not appear in that position, but does appear in some other
      position, and a blank indicates the corresponding letter does not appear
      in the word.
   */
   public static String formFeedback(String guess, String target) {
      String result = "";
      //Array equivalents of corresponding string values
      char[] guessA = new char[Dictionary.WORD_LENGTH];
      char[] targetA = new char[Dictionary.WORD_LENGTH];
      char[] resultA = new char[Dictionary.WORD_LENGTH];
      int i;
      //Handle those letters that match in the proper position
      for(i=0; i<Dictionary.WORD_LENGTH; i++) {
         guessA[i] = guess.charAt(i);
         targetA[i] = target.charAt(i); 
         if(guessA[i] == targetA[i]) { //Letters match in the same position
            resultA[i] = targetA[i];
            guessA[i] = NOTIN;
            targetA[i] = NOTIN;
         } else {
            resultA[i] = NOTIN;
         }
      }
      //Handle those letters that do not match in the same position
      for(int iG=0; iG<Dictionary.WORD_LENGTH; iG++) {
         if(guessA[iG] != NOTIN) {  //Letter does not match 
            //Determine index in target of the letter from guess
            int iT = 0;   
            while((iT<Dictionary.WORD_LENGTH) && (guessA[iG] != targetA[iT])) {
               iT = iT + 1;
            }
            if(iT<Dictionary.WORD_LENGTH) {  //The letter appears in the target
               targetA[iT] = BLANK;
               resultA[iG] = OTHER;
            } else {
               resultA[iG] = BLANK;
            }
            guessA[iG] = BLANK;
         }
      }
      //Formulate the result string from the array equivalent
      for(i=0; i<Dictionary.WORD_LENGTH; i++) {
         if(resultA[i] == OTHER) {  //Replace with lower-case letter
            resultA[i] = Character.toLowerCase(guess.charAt(i));
         }
         result = result + resultA[i];
      }
      return result;
   }
   
   /* Modifies the attributes of those words in the dictionary (to false) that
      can be eliminated from further consideration as appropirate guesses.  This
      is done using the given guess and feedback values.      
   */
   public static void reduceCandidates(Dictionary d, String guess, String feedback) {
      boolean[] mask = maskOf(guess,feedback);     
      //Iterate through the dictionary
      for(int index=0; index<d.wordsIn(); index++) {
         //Check to see if the word is a candidate
         if(d.getAttributeOfWordAt(index)) {
            String word = d.getWordAt(index);   //The current word
            boolean keep = true;                //Assume the word will be kept
            boolean[] copy = copyOf(mask);      //Use new mask instance
            //Check each symbol in the feedback one at a time
            for(int iF=0; keep && (iF<Dictionary.WORD_LENGTH); iF++) {
               char f = feedback.charAt(iF);
               boolean upper=true;  boolean lower=true; boolean blank = true;
               //A letter has been found in the correct position
               if(Character.isUpperCase(f)) {
                  upper = guess.charAt(iF) == word.charAt(iF);
               //A letter has been found in the wrong position
               } else if(Character.isLowerCase(f)) {
                  //Check if the letter is in this wrong position
                  lower = (guess.charAt(iF) != word.charAt(iF));
                  if(lower) { //Then check if it is in some other position
                     //Temporarily eliminate this position from consideration
                     boolean save = copy[iF];
                     copy[iF] = false;
                     //Determine the index of the letter
                     int indexL = indexOfLetter(guess.charAt(iF),word,copy);
                     lower = (indexL >= 0);
                     if(lower) {
                        copy[indexL] = false;   //Eliminate position from mask
                     }
                     //Restore the temporarily eliminated consideration 
                     copy[iF] = save;
                  }
               //A letter that does not appear anywhere in the target word
               } else { // (f == BLANK)
                  blank = !hasLetter(guess.charAt(iF),word,copy);
               }
               keep = upper && lower && blank;
            }
            if(!keep) {
               d.setAttributeOfWordAt(index,false);
            }
         }
      }
   }
   
   /* Returns a "mask" indicating those positions in the given feedback string
      that need to be checked when determining the candidacy of words.  Positions
      with upper-case letters (indicating perfect letter matches) do not need to
      be checked and neither do positions with blanks where the corresponding
      letter in the guess is elsewhere in the feedback.
   */
   public static boolean[] maskOf(String guess, String feedback) {
      boolean result[] = new boolean[Dictionary.WORD_LENGTH];
      for(int i=0; i<Dictionary.WORD_LENGTH; i++) {
         char f = feedback.charAt(i);
         char g = Character.toLowerCase(guess.charAt(i));
         result[i] = !Character.isUpperCase(f);
      }
      return result;
   }
   
   /* Returns a copy of the given array of boolean values.
   */
   public static boolean[] copyOf(boolean[] mask) {
      boolean[] result = new boolean[mask.length];
      for(int i=0; i<mask.length; i++) {
         result[i] = mask[i];
      }
      return result;
   }
   
   /* Returns true if any of the letters in the given word at the positions
      indicated by the given mask match the given letter.
   */
   public static boolean hasLetter(char letter, String word, boolean[] mask) {
      return (indexOfLetter(letter,word,mask) >= 0);
   }
   
   /* Returns the index of an occurrence of the given letter in the 
      given word at the positions indicated by the given mask.
   */
   public static int indexOfLetter(char letter, String word, boolean[] mask) {
      int index = 0;
      while((index<Dictionary.WORD_LENGTH) &&
            !(mask[index] && (word.charAt(index) == letter)) ) {
         index = index + 1;
      }
      if(index == Dictionary.WORD_LENGTH) { index = -1; }
      return index;
   }
      
   /* Produces a printout, one word per line, of the remaining words in
      the dictionary whose attribute value is true, indicating they are 
      still candidates to be the target word.
   */
   public static void printCandidates(Dictionary d) {
      int sequence = 0;
      //Iterate through the dictionary
      for(int index=0; index<d.wordsIn(); index++) {
         if(d.getAttributeOfWordAt(index)) {
            sequence = sequence + 1;
            System.out.printf("%4d: %s (%d)\n",sequence,d.getWordAt(index),index);
         }
      }
   }

}
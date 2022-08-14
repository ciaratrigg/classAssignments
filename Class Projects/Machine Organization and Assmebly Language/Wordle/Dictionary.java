/* An instance class that abstracts a collection of "words", each of which is
   a sequence of letters of precisely length WORD_LENGTH.  Note that each word
   in case-insensitive and that no duplicate words are disallowed. Each word
   has an associated boolean attribute, which can be set or cleared.
   
   Author: P.M.J. and C.R.T.
   Course: CMPS 250
   Date: Spring 2022
*/

import java.io.*;
import java.util.Scanner;
public class Dictionary {

/////////////////////////////////////////////////////////////////////////////////
// PUBLIC STATIC MEMBERS
/////////////////////////////////////////////////////////////////////////////////
   
   public static final int LIMIT = 20000;     //Maximum number of words allowed
   public static final int WORD_LENGTH = 5;   //Precise length of each word

   //Returns true if the given word consists of five letters
   public static boolean isValidWord(String word) {
      boolean result = true;
      for(int i=0; result && (i<WORD_LENGTH); i++) {
         result = Character.isLetter(word.charAt(i));
      }
      return result;
   }
   
/////////////////////////////////////////////////////////////////////////////////
// INSTANCE DATA
/////////////////////////////////////////////////////////////////////////////////

   private String[] word = new String[LIMIT];         //The array of words
   private boolean[] attribute = new boolean[LIMIT];  //The parallel array
   private int n = 0;      //The current number of words in the collection
   private int attributes; //The current number of words whose attribute is true

/////////////////////////////////////////////////////////////////////////////////
// CONSTRUCTORS
/////////////////////////////////////////////////////////////////////////////////

   /* Instanciates and loads the object with the words read from the text file
      with the given name.  The file is to be structured with one word per line.
      All invalid and duplicate words are ignored.
   */
   public Dictionary(String fid) throws FileNotFoundException {
      Scanner input = new Scanner(new File(fid.trim()));
      
      // continues reading items from the text file until:
      // 1. there are no more elements 
      // OR
      // 2. the number of words in the collection has reached 2000
      while(input.hasNext() && (n < LIMIT)){
         // sets the next open spot in the word array to the next word 
         // in the input file and removes all spaces
         word[n] = input.nextLine().trim().toUpperCase();
         // ensures that the input meets length and validity requirments
         if((word[n].length() == WORD_LENGTH) && (isValidWord(word[n]))){
            attribute[n] = true;
            n++;
         }
         
      }
      // Stubbed!!!
      if(input.hasNext() && (n == LIMIT)) {
         throw new IllegalStateException("Dictionary LIMIT exceeded");
      }
      attributes = n;
   }

/////////////////////////////////////////////////////////////////////////////////
// ACCESSORS
/////////////////////////////////////////////////////////////////////////////////

   /* Returns the number of words currently in the dictionary.
   */
   public int wordsIn() {
      return n;
   }
   
   /* Returns the number of words, whose attribute is true.
   */
   public int attributesIn() {
      return attributes;
   }
   
   /* Returns the index of the word in the dictionary or -1 if not found.
   */
   public int indexOf(String s) {
      int result = n;
      
      // traverses the word array comparing each element to 's' 
      // until a matching word is found or the array runs out of words
      // because of this design, a return value of '0' indicates that
      // the word is not present in the array
      while((result >= 0) && !s.equals(word[result])){
         result--;
      }
      // Stubbed!!!

      return result;      
   }
   
   /* Returns true if the given index refers to a valid position that holds
      a word in the dictionary.
   */
   public boolean isValidIndex(int index) {
      return (index > -1) && (index < n);
   }
   
   /* Returns the word (as a string of length WORD_LENGTH) at the given index
      position in the dictionary.  If the given index is invalid then 
      IllegalArgumentException is thrown.
   */
   public String getWordAt(int index) {
      String result = null;
      
      if(isValidIndex(index)){
         result = word[index];
      }
      else {
         throw new IllegalArgumentException("Given index is invalid.");
      }
      // Stubbed!!!
      
      return result;
   }
   
   /* Returns the current attribute value of the word at the given index. 
      If the given index is invalid then IllegalArgumentException is thrown.
   */
   public boolean getAttributeOfWordAt(int index) {
      boolean result = false;
      
      if(isValidIndex(index)){
         result = attribute[index]; 
      }
      else {
         throw new IllegalArgumentException("Given index is invalid");
      }
      // Stubbed!!!
      return result;
   }
   
/////////////////////////////////////////////////////////////////////////////////
// MUTATORS
/////////////////////////////////////////////////////////////////////////////////

   /* Sets the attribute values of every word currently in the dictionary to the 
      given state value.
   */
   public void setAttributes(boolean state) {
      for(int i = 0; i < n; i++){
         attribute[i] = state; 
      }
      //update the value of attributes after altering the attribute array
      if(state){
         attributes = n;
      }
      else{
         attributes = 0; 
      }
      // Stubbed!!!

   }
   
   /* Sets the attribute value of the word at the given index position
      to the given state value and returns true. If the given index is invalid 
      then IllegalArgumentException is thrown.
   */
   public boolean setAttributeOfWordAt(int index, boolean state) {
      boolean result = false;
      
      if(isValidIndex(index)){
         if(attribute[index]){
            if(!state){ 
               attributes--; 
            }
         } 
         else {
            if(state){ 
               attributes++; 
            }
         }
         attribute[index] = state;
         result = true;
      } 
      else {
         throw new IllegalArgumentException("Given index is invalid.");
      }
      // Stubbed!!!

      return result;
   }
    
}
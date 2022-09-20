/* An instance of this class represents a chess board for the purpose of 
** solving the 8-Queens Problem (or a generalization thereof on a square
** board of a non-standard size). 
**
** The problem is to place N queens onto an N-by-N board so that exactly
** one queen lies in each row, one queen lies in each column, and at most
** one queen lies in any diagonal.
**
** Rows and columns are numbered 0..N-1.  If columns are numbered from 
** "west to east" and rows are numbered from "south to north", then each 
** NW-SE diagonal can be identified by the row+col sum of its squares 
** (which lie in the range 0..2(N-1)) and each NE-SW diagonal can be 
** identified by the row-col difference of its squares (which lie in the
** range -(N-1)..(N-1)).
**
** Author: R. McCloskey and C. Trigg
** Last modified: October 2020.
** Known defects: ...
*/

public class EightQueensBoard {

   // class constant
   // --------------
   private static final int BOARD_SIZE_DEFAULT = 8;
   private static final char QUEEN_SYMBOL = 'Q';
   private static final char EMPTY_SYMBOL = '-';


   // instance variables 
   // ------------------
   private final int N;    // board is NxN (established upon creation)
   private int numQueens;  // # of queens occupying the board
   private int offset;     // variable to account for the offset in the NE-SW diagonal

   private int[] rowToCol; // rowToCol[i] = column occupied in row i (-1 if none)
   private int[] colToRow; // colToRow[i] = row occupied in column i (-1 in none)
   private boolean [] nwse;//CRT: nwse[i] = true if a queen lies on the diagonal (r + c) 
   private boolean [] nesw;//CRT: nesw[i] = true if a queen lies on the diagonal (r - c)

   // constructor
   // -----------

   /* Initializes this board as having the specified # of rows and columns
   ** and having no queens upon it.
   */
   public EightQueensBoard(int size) {
      N = size;
      offset = N - 1; //CRT
      rowToCol = new int[N];  fillInt(rowToCol, -1);
      colToRow = new int[N];  fillInt(colToRow, -1);
      nwse = new boolean[2 * (N - 1) + 1]; fillBool(nwse, false); //CRT
      nesw = new boolean[2 * (N - 1) + 1]; fillBool(nesw, false); //CRT
   }

   /* Initializes this board as having the standard # of rows and columns
   ** (namely, 8) and no queens upon it.
   */
   public EightQueensBoard() { this(BOARD_SIZE_DEFAULT); }


   // observers
   // ---------

   /* Returns the size of the board (i.e., # of rows and # of columns).
   */
   public int boardSize() { return N; }

   /* Returns the number of queens currently on the board.
   */
   public int numQueensOnBoard() { return numQueens; }

   /* Reports whether or not a queen can be legally placed on the 
   ** specified square.
   ** pre: 0 <= row < boardSize()  &&  0 <= col < boardSize()
   */
   public boolean isAvailable(int row, int col) {
      return (isAvailableRow(row) && isAvailableCol(col) && isAvailableNwse(row, col) && isAvailableNesw(row, col)); 
      // STUB
   }

   /* Reports whether or not a queen can be placed in the specified row.
   ** pre: 0 <= row < boardSize()
   */
   public boolean isAvailableRow(int row) { 
      if(rowToCol[row] == -1){
         return true;
      }
      else{
         return false;
      }
      // STUB
   }

   /* Reports whether or not a queen can be placed in the specified column.
   ** pre: 0 <= col < boardSize()
   */
   public boolean isAvailableCol(int col) { 
      if(colToRow[col] == -1){
         return true;
      }
      else{
         return false; 
      }
      // STUB
   }
   
   /* Reports whether or not a queen can be placed in a NW-SE diagonal
   ** pre:0 <= row < boardSize()  &&  0 <= col < boardSize()
   */
   public boolean isAvailableNwse(int row, int col){
      if(!nwse[row + col]){
         return true;
      }
      else{
         return false;
      }
   }//CRT
   
   /* Reports whether or not a queen can be placed in a NE-SW diagonal
   ** pre:0 <= row < boardSize()  &&  0 <= col < boardSize()
   */
   public boolean isAvailableNesw(int row, int col){
      if(!nesw[(row-col) + offset]){
         return true;
      }
      else{
         return false; 
      }
   }//CRT

   /* Returns the column coordinate of the queen-occupied square 
   ** in the specified row (or -1 if there is no queen in that row).
   ** pre: 0 <= row < boardSize()
   */
   public int rowToColumn(int row) { return rowToCol[row]; }

   /* Returns the row coordinate of the queen-occupied square in the
   ** specified column (or -1 if there is no queen in that column).
   ** pre: 0 <= col < boardSize()
   */
   public int columnToRow(int col) { return colToRow[col]; }

   /* Prints the board, using the specified symbols, respectively,
   ** to indicate squares occupied by, and not occupied by, queens.
   */
   public void printBoard(char queenSymbol, char emptySymbol) {
      int colWidth = numDigits(N-1) + 1;
      for (int row = N-1; row != -1; row--) {
         System.out.printf("%" + (colWidth-1) + "d |", row);
         int colOccupied = rowToCol[row];
         for (int col = 0; col != N; col++) {
            char symbol = col == colOccupied ? queenSymbol : emptySymbol;
            System.out.printf("%" + colWidth + "c", symbol);
         }
         System.out.println();
      }
      // Print line underneath the board
      printChars(' ', colWidth);
      System.out.print("+");
      printChars('-', colWidth*N);
      System.out.println();

      // Print column numbers
      System.out.print("    ");
      for (int col = 0; col != N; col++) {
         System.out.printf("%" + colWidth + "d", col);
      }
      System.out.println();
   }


   /* Prints the board, using QUEEN_SYMBOL  to indicate squares 
   ** occupied by a queen and EMPTY_SYMBOL to indicate empty squares.
   */
   public void printBoard() { 
      printBoard(QUEEN_SYMBOL, EMPTY_SYMBOL);
   }


   // mutators
   // --------

   /* Places a queen on the specified square, unless that would 
   ** result in two queens attacking each other, in which case 
   ** an IllegalArgumentException is thrown.
   ** pre: 0 <= row < boardSize()  &&  0 <= col < boardSize() 
   */
   public void placeQueen(int row, int col) {
      if (isAvailable(row, col)) { 
         placeQueenUnsafe(row, col);
      }
      else {
         throw new IllegalArgumentException("Illegal placement; Queens " +
                                            "would be attacking each other!!");
      }
   }

   /* Places a queen on the specified square.
   ** pre: 0 <= row < boardSize()  &&  0 <= col < boardSize() &&
   **      isAvailable(row,col)
   */
   public void placeQueenUnsafe(int row, int col) {
      rowToCol[row] = col;
      colToRow[col] = row;
      nwse[row + col] = true;
      nesw[(row - col) + offset] = true;
      numQueens++;   
      // STUB
   }

   /* If a queen occupies the specified square, it is removed from 
   ** that square.  Otherwise, there is no effect.
   ** pre: 0 <= row < boardSize()  &&  0 <= col < boardSize() &&
   */
   public void removeQueen(int row, int col) {
      if(!isAvailable(row,col)){
         rowToCol[row] = -1;
         colToRow[col] = -1;
         nwse[row + col] = false;
         nesw[(row - col) + offset] = false;
         numQueens--; 
      }
      // STUB
   }

   /* If a queen occupies a square in the specified row, it is
   ** removed from that square.  Otherwise there is no effect.
   ** pre: 0 <= row < boardSize() 
   */
   public void removeQueenFromRow(int row) {
         int col = rowToCol[row];
         removeQueen(row, col);     
      // STUB
   }

   /* If a queen occupies a square in the specified column, it is
   ** removed from that square.  Otherwise there is no effect.
   ** pre: 0 <= col < boardSize() &&
   */
   public void removeQueenFromCol(int col) {
         int row = colToRow[col];
         removeQueen(row, col);
      // STUB
   }


   // private
   // -------

   /* Returns the number of digits in the decimal numeral that represents
   ** the given integer.
   ** pre: k >= 0
   */
   private int numDigits(int k) {
      if (k < 10) { return 1; }
      else { return 1 + numDigits(k/10); }
   }

   /* Fills every element of the given boolean array with the given value.
   */
   private void fillBool(boolean[] a, boolean val) {
      for (int i=0; i != a.length; i++) { a[i] = val; }
   }

   /* Fills every element of the given int array with the given value.
   */
   private void fillInt(int[] a, int val) {
      for (int i=0; i != a.length; i++) { a[i] = val; }
   }

   /* Prints the specified character the specified # of times.
   */
   private void printChars(char ch, int k) {
      for (int i=0; i<k; i++) { System.out.printf("%c", ch); }
   }

}

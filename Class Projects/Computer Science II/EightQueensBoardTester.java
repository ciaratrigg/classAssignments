import java.util.Scanner;

/* Java application whose purpose is to test the features of the
** EightQueensBoard class.  The user enters commands that either
** place onto or remove queens from the board.  
**
** Author: R. McCloskey
** Date: July 2020
*/

public class EightQueensBoardTester {

   private static final int BOARD_SIZE_DEFAULT = 8;
   private static Scanner keyboard = new Scanner(System.in);
   private static boolean keepGoing;
   private static EightQueensBoard board;

   public static void main(String[] args) {
      print("Welcome to the EightQueensBoardTester program\n\n");
      printHelp();
      
      print("\nEnter size of first board: ");
      String numeral = keyboard.nextLine().trim();
      performCommand("n " + numeral);
      
      keepGoing = true;
      while (keepGoing) {
         String command;
         // Prompt user until a non-empty string is entered
         do {
            print("\n>");
            command = keyboard.nextLine().trim();
         }
         while (command.length() == 0);

         try {
            performCommand(command);
         }
         catch (Exception e) {
            e.printStackTrace(System.out);
            print("\n Something went wrong; try again\n");
         }
      }
      System.out.println("Goodbye.");
   }

   /* Performs the specified command upon the current board.
   */
   private static void performCommand(String command) {

      Scanner comScanner = new Scanner(command);
      String commandCode = comScanner.next();

      if (commandCode.equals("h")) {    // help
         printHelp();
      }
      else if (commandCode.equals("q")) {  // quit
         keepGoing = false;
      }
      else if (commandCode.equals("n")) {  // create new empty board
         if (comScanner.hasNext()) {
            int size = comScanner.nextInt();
            board = new EightQueensBoard(size);
         }
         else {   // create board having default size
            board = new EightQueensBoard();
         }
         print("New board created:\n\n");
         board.printBoard();
      }
      else if (commandCode.equals("p")) { 
         int row = comScanner.nextInt();
         int col = comScanner.nextInt();
         System.out.printf("Placing queen safely onto square (%d,%d):\n\n", 
                           row, col);
         board.placeQueen(row, col);
         board.printBoard();
      }
      else if (commandCode.equals("pu")) { 
         int row = comScanner.nextInt();
         int col = comScanner.nextInt();
         System.out.printf("Placing queen onto square (%d,%d):\n\n", row, col);
         board.placeQueenUnsafe(row, col);
         board.printBoard();
      }
      else if (commandCode.equals("rr")) { 
         int row = comScanner.nextInt();
         System.out.printf("Removing queen from row %d:\n\n", row);
         board.removeQueenFromRow(row);
         board.printBoard();
      }
      else if (commandCode.equals("rc")) { 
         int col = comScanner.nextInt();
         System.out.printf("Removing queen from column %d:\n\n", col);
         board.removeQueenFromCol(col);
         board.printBoard();
      }
      else if (commandCode.equals("num")) { 
         print(board.numQueensOnBoard() + " queens on board.\n");
      }
      else {
         print("Unrecognized command; try again.");
      }
   }

   private static void printHelp() {
      print("Examples of commands:\n");
      print("---------------------\n");
      print("q : to quit\n");
      print("h : for help!\n");
      print("n : to create new board of default size\n");
      print("n 5: to create new board of size 5\n");
      print("p 4 3: to safely place a queen onto square (4,3)\n");
      print("pu 2 4: to unsafely place a queen onto square (2,4)\n");
      print("rr 5: to remove queen from row 5\n");
      print("rc 0: to remove queen from column 0\n");
      print("num: to ask how many queens are on the board\n");
   }

   /* Surrogate for System.out.print()
   */
   private static void print(String s)
      { System.out.print(s); }


}

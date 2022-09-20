public class HashTable {
   //instance variables
   private Node[] hashTable = new Node[10]; 
   private Node temp;
   public static HashTable table;

   //constructor
   public HashTable(){
      for(int i = 0; i < hashTable.length; i++){
         hashTable[i] = null;
      }
   }
   
   //mutators
   public void insert(int val){ 
      if(hashTable[hash(val)] == null){ //empty 
         hashTable[hash(val)] = new Node(val, null);
      }
      else{
         temp = hashTable[hash(val)]; 
         hashTable[hash(val)] = new Node(val, temp);
      }  
   }
   
   public void delete(int val){
      temp = hashTable[hash(val)]; 
      if(temp == null){
         System.out.println("Error: " + val + " is not in the Hash Table\n");
      }
      else if(temp.getNum() == val){ //val is the first item in the list
         hashTable[hash(val)] = temp.getNext(); 
      }
      else{ 
         while(temp.getNext().getNum() != val && temp.getNext() != null){ //find
            temp = temp.getNext();
         }
         if(temp.getNext().getNum() == val && temp.getNext().getNext() == null){ //val is the last item in the list
            temp.setNext(null); 
         }
         else if(temp.getNext().getNum() == val && temp.getNext().getNext() != null){ //val is somewhere in the middle of the list
            temp.setNext(temp.getNext().getNext());
         }
         else{ //val is not in the list
            System.out.println("Error: " + val + " is not in the Hash Table\n"); 
         }
      }
   }
   
   //observers
   public int hash(int val){
      return(val%10); 
   }
   
   public void dumphash(){
      for(int i = 0; i < 10; i++){
         System.out.print(i + ": ");
         if(hashTable[i] != null){
            temp = hashTable[i];
            while(temp != null){
               System.out.print(temp.getNum() + ", " );
               temp = temp.getNext(); 
            }
            System.out.print("\n"); 
         }
         else{
            System.out.print("-");
            System.out.print("\n");
         }
      }
     System.out.print("\n"); 
   }
   
   private class Node{
      Node next; 
      int num; 
      
      public Node(int val){
         next = null; 
         num = val; 
      }
      
      public Node(int numVal, Node nextVal){
         next = nextVal;
         num = numVal;
      }
      
      public int getNum(){
         return num;
      }
      
      public Node getNext(){
         return next; 
      }
      
      public void setNext(Node nextVal){
         next = nextVal; 
      }
   }


}

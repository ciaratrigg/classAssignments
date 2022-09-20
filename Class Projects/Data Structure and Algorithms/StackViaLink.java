public class StackViaLink {
   
      public static void main(String[] args){
         stack = new StackViaLink();
         stack.push(2);
        stack.push(1);
         stack.dumpstack();
         stack.push(3);
         stack.push(10);
         stack.pop();
         stack.push(15);
         stack.dumpstack();
         stack.pop();
         stack.pop();
         stack.dumpstack();
         stack.pop();
         stack.pop();
        stack.pop();
        stack.dumpstack();
   }

   
   
   
   //instance variables
   public static StackViaLink stack;
   private Node head;
   private Node temp;
   private int numItems;
   
   //constructor
   public StackViaLink(){
      head = new Node(null);
      numItems = 0; 
   }
   
   //mutators
   public void push(Object val){
      if(head == null){
         head = new Node(val);
      }
      else{ 
         temp = head; 
         head = new Node(val);
         head.setNext(temp);  
         numItems++;
      }  
   }
   
   public void pop(){
      if(numItems > 0){
         head = head.getNext();
         numItems--; 
      }
      else{
         System.out.println("\nError:Cannot pop from an empty stack."); 
      }
   }
   
   //observers
   public void dumpstack(){ //prints the contents of the stack 
      if(numItems > 0){
         temp = head;
         System.out.println("\nCurrent Stack: ");
         while(temp.getNum() != null){
            System.out.println(temp.getNum());
            temp = temp.getNext();
         }
     }
     else{
         System.out.println("\nCurrent Stack:\nEmpty.");  
     }
   }
   
   private class Node{
      Node next; 
      Object num;
      
      public Node(Object val){
         next = null; 
         num = val; 
      }
      
      public Node(Object numVal, Node nextVal){
         next = nextVal;
         num = numVal;
      }
      
      public Object getNum(){
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

import java.util.*;
public class BinarySearchTree {
   //instance variables 
   private Node root; 
   
   //main method/tester
   public static void main(String[]args){
      BinarySearchTree bst = new BinarySearchTree();
      bst.insert(20);
      bst.insert(10);
      bst.insert(30);
      bst.insert(5);
      bst.insert(7);
      bst.insert(21);
      bst.dumptree(bst.root);
      bst.delete(bst,20);
      bst.delete(bst,21);
      bst.delete(bst,35);
      bst.dumptree(bst.root);
   }  
   
   //constructor
   public BinarySearchTree(){ //initializes the tree to be empty
      root = null;
   }
   
   //observers 
   public void dumptree(Node node){
      LinkedList<Node> queue = new LinkedList<>();
      queue.add(node);
      int numAdds = 0; 
      int levelNum = 1;
      int child = 0; 
      System.out.print("\n             ");
      while(!queue.isEmpty()){
         Node temp = queue.remove();
         if(temp == null){
            numAdds += 2;
            System.out.print("--");
            spaceBetween(levelNum);
         }
         else{  
            queue.add(temp.left);
            queue.add(temp.right);
            numAdds += 2;
            System.out.print(temp.val); 
            spaceBetween(levelNum);     
         }
         if(numAdds == Math.pow(2,levelNum)){
            System.out.print("\n");
            levelNum++;
            numAdds = 0;
            if(levelNum == 2){
               System.out.print("      ");
            }
            else if(levelNum == 3){
               System.out.print("  ");
            }
         } 
      }
      System.out.print("\n");
   }
   public void spaceBetween(int levelNum){
      if(levelNum == 2){
         System.out.print("             ");
      }
      else if(levelNum == 3){
         System.out.print("      ");
      }
      else if(levelNum == 4){
         System.out.print("  ");
      }   
   }
      
   //mutators
   public void insert(int val){
      Node temp = null;
      Node parent = null;
      boolean inserted = false;
      Node newNode = new Node(val);
      if(root == null){ //tree is empty 
         root = newNode;
      }
      else{ //tree has at least one value
         temp = root;  
         while(!inserted){ 
            parent = temp; 
            if(val < temp.val){
               temp = temp.left;
               if(temp == null){
                  parent.left = newNode; 
                  inserted = true;
               }
            }
            else{
               temp = temp.right;
               if(temp == null){
                  parent.right = newNode;
                  inserted = true;
               }
            }
         
         }
      }
   }
   
   public void delete(BinarySearchTree bst, int val){
      Node temp = root;
      Node parent = null;
      while(temp != null && temp.val != val){ //find
         parent = temp;
            if(val < temp.val){
               temp = temp.left;
            }
            else{
               temp = temp.right;
            }
       }
       if(temp == null){ // node does not exist
         System.out.println("\nError: " + val + " is not in the current BST.");
       }
       else if(temp.left != null && temp.right != null){ //deleting a node with 2 children
         if(temp == root){
            graft(bst, temp);
            root = temp.left; 
         }
         else{
            graft(bst, temp); 
            parent.left = temp.left;
         } 
       }
       else if(temp.left == null && temp.right == null){ //deleting a leaf node
         if(parent.left.val == val){
            parent.left = null; 
         }
         else{
            parent.right = null;
         }
       }
       else{ //deleting a node with 1 child
         if(parent.left.val == val){
            parent.left = temp; 
         }
         else{
            parent.right = temp;
         }
       }
   }
   
   public void graft(BinarySearchTree bst, Node temp){
      Node max = null;
      max = temp.left;
         while(max.right != null){ //finds max element in left subtree of temp 
            max = max.right; 
         }
         max.right = temp.right;
   }

   private class Node {
      int val; 
      Node left; 
      Node right;
      
      public Node(int val){
         this.val = val;
         this.left = null;
         this.right = null;
      }
   }
}

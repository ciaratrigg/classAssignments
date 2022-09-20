public class DAlg{
      /*
      Visual representation of the matrix created below
      Start node is 0 and all other nodes are numbered consecutively from left to right according to the diagram
      on the assignment page
      {0, 6, 1, 0, 10, 0},//(0)
      {0, 0, 0, 3, 0, 0}, //(1)
      {0, 4, 0, 0, 0, 2}, //(2)
      {0, 0, 0, 0, 3, 0}, //(3)
      {0, 0, 0, 0, 0, 0}, //(4)
      {0, 1, 0, 0, 0, 0}, //(5)
   */
   public static void main(String[]args){
      int[][] graph = new int[6][6];
      graph[0][1] = 6;
      graph[0][2] = 1;
      graph[0][4] = 10;
      graph[1][3] = 3;
      graph[2][1] = 4;
      graph[2][5] = 2;
      graph[3][4] = 3;
      graph[5][1] = 1;
      dAlg(graph);
   }
   
   //When called this method prints the status of the distance array
   public static void print(int[] distance){
      for(int i = 0; i < 6; i++){
         if(distance[i] == Integer.MAX_VALUE){
            System.out.println(i + ": Infinity");
         }
         else{
            System.out.println(i + ": " + distance[i]);
         } 
      }
      System.out.print("\n");
   }
   //If the value of the current node (a) plus the weight of the path (w) is less than the value of the 
   //node being pointed to (b), then the value of b is replaced with a + w
   public static int relax(int a, int w, int b){
      if(a + w < b){
         b = a + w;
      }
      return b; 
   }
   
   public static void dAlg(int[][] graph){
      int cur = 0; //the node currently being explored
      int[] distance = new int[graph.length]; //holds the current minimum distances between nodes
      boolean[] visited = new boolean[graph.length]; //if a node has been visited, the array holds true. otherwise, false
      
      //initializes all distances except start to "infinity"
      for(int i = 1; i < distance.length; i++){
         distance[i] = Integer.MAX_VALUE;
      }
      //initializes all nodes to be unvisited 
      for(int i = 0; i < visited.length; i++){
         visited[i] = false;
      }
      
      while(cur != -1){
         visited[cur] = true;
         for(int i = 0; i < graph.length; i++){
            if(graph[cur][i] != 0){
               distance[i] = relax(distance[cur], graph[cur][i], distance[i]); //replaces the value of the node cur points to in the distance array with the relaxed value
            }
         }
         cur = shortestDist(distance, visited); // finds the minimum distance unvisited node using the current distance and visted arrays
         print(distance); //prints the current state of the distance array after each node is visited
      }  
   }
   
   public static int shortestDist(int[] distance, boolean[] visited){
      int min = Integer.MAX_VALUE;
      int next = -1; //if all nodes have been visited there will be no minimum distance node and the while loop in dAlg will end
      
      for(int i = 0; i < distance.length; i++){ //traverses the distance array in order to find the minimum distance unvisted node
         if(min > distance[i] && !visited[i]){
            min = distance[i];
            next = i; 
         }
      }
      return next; 
   }
}

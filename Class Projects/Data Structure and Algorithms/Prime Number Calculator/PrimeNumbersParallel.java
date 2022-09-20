public class PrimeNumbersParallel extends Thread{
   
   static int numPrimes = 0;
   
   public static void calcPrimes(int start, int end){
      for(int num = start; num < end; num++){
         boolean prime = true;
         for(int i = 2; i <= (int)Math.sqrt(num); i++){
            if(num % i == 0){
               prime = false; 
            }
         }
         if(prime){
            counter();
         }
      }
   }
   public static synchronized void counter(){
      numPrimes++;
   }
   
   public static void main(String[] args){
      long start = System.currentTimeMillis();
      Thread t1 = new Thread(){
         public void run(){
            calcPrimes(1000000,1250000); 
         }
      };
      Thread t2 = new Thread(){
         public void run(){
            calcPrimes(1250001, 1500000); 
         }
      };
      Thread t3 = new Thread(){
         public void run(){
            calcPrimes(1500001, 1750000);
         }
      };
      Thread t4 = new Thread(){
         public void run(){
            calcPrimes(1750001, 2000000);
         }
      };
      t1.start();
      t2.start();
      t3.start();
      t4.start();
      
      try{
         t1.join();
         t2.join();
         t3.join();
         t4.join();
      }
      catch(Exception e){
      
      }
      long elapsedTimeMillis = System.currentTimeMillis() - start;
      System.out.println("Parallel: ");
      System.out.println(elapsedTimeMillis + " milliseconds");
      System.out.println(numPrimes + " prime numbers between 1000000 and 2000000");
   } 
}

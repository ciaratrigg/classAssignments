public class PrimeNumbersSerial{
   public static void main(String[] args){
      long start = System.currentTimeMillis();
      int numPrimes = 0; 
      for(int num = 1000000; num < 2000000; num++){
         boolean prime = true;
         for(int i = 2; i <= (int)Math.sqrt(num); i++){
            if(num % i == 0){
               prime = false; 
            }
         }
         if(prime){
            numPrimes++;
         }
      }
      long elapsedTimeMillis = System.currentTimeMillis() - start;
      System.out.println("Serial: ");
      System.out.println(elapsedTimeMillis + " milliseconds");
      System.out.print(numPrimes + " prime numbers between 1000000 and 2000000");
   }
   
}

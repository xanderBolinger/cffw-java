package Trooper;
 
import java.io.Serializable;
import java.util.Scanner;
public class makeNameArray implements Serializable {
  
   public static void main(String[] args){
      Scanner foo = new Scanner(System.in); 
      String array = "{";
      Boolean status = true;
      int count = 0;
      while(status == true) {
         String input = foo.nextLine();
         //System.out.println("Input: "+input);
         if(input.equals("0")){
            status = false; 
         } else {
            array = array + ", '" + input + "'";
            count++;
         }
      
      }
      array = array + "}";
      
     // System.out.println(array);
     // System.out.println("Count: "+count);
   
   }

}
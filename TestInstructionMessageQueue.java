import java.util.Random;
import java.util.Random.*;

public class TestInstructionMessageQueue{

 InstructionQueue iq = new InstructionQueue();
 InstructionMessage im;
 boolean suc=true;

 public TestInstructionMessageQueue(int n){

  InstructionMessage im;
  Random r = new Random();

  for (int x=0;x<n;x++){
   im = new InstructionMessage();
   im.instructionType=r.nextInt(98)+1;
   im.productCode=r.nextInt(999999999)+1;
   im.quantity=r.nextInt(999999)+1;
   im.uom=r.nextInt(254)+1;
   im.timeStamp=r.nextInt(99999999)+1;
   iq.addInstructionToQueue(im);
  }

  if (iq.getNumberOfInstructionMessages()!=n) 
   { System.out.println (" failed : check getNumOfMessagesInQueue "); suc=false ;}
  if (iq.queueIsEmpty()!=true) 
   { System.out.println(" failed : check queue is occupied "); suc=false ; }
  
  while (true){
   im=iq.getNextInstructionMessage();
   if (im==null) {break;}
   System.out.println(im.instructionType+" "+im.productCode);
  }

  if (iq.getNumberOfInstructionMessages()!=0) 
   { System.out.println (" check getNumOfMessagesInQueue failed for 0 messages"); suc=false ; }
  if (iq.queueIsEmpty()!=false)               
   { System.out.println("  failed : check queue is empty   "); suc=false ; }

  
  im = new InstructionMessage();
  try{
      iq.addInstructionToQueue(im);
      System.out.println(" failed : catch bad Instruction "); 
      suc=false ;
     }
  catch (InvalidMessageException e) 
     {     }
 
  
 }



 public static void main(String[] args){
  
  TestInstructionMessageQueue t = new TestInstructionMessageQueue(20); 
  System.out.println("test pass = "+t.suc);
 }  

}
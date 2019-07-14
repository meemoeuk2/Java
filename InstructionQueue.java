import java.util.*;

public class InstructionQueue{

 List <InstructionMessage> instructionMessageListHighP   = new ArrayList<>();
 List <InstructionMessage> instructionMessageListMediumP = new ArrayList<>();
 List <InstructionMessage> instructionMessageListLowP    = new ArrayList<>();

 public int getNumberOfInstructionMessages(){

  return instructionMessageListHighP.size() +
         instructionMessageListMediumP.size() +
         instructionMessageListLowP.size() ;
 }

 public void addInstructionToQueue(InstructionMessage i){
  checkInstructionIntegrity(i);

  if (i.instructionType>=0   && i.instructionType<=10 ) { instructionMessageListHighP.add(i)  ; }
  if (i.instructionType>=11  && i.instructionType<=90 ) { instructionMessageListMediumP.add(i); }
  if (i.instructionType>=91  && i.instructionType<=100) { instructionMessageListLowP.add(i)   ; }
 }

 //  how obtain instance? 
 public void removeInstructionFromQueue(InstructionMessage i){
   // if already got handle on instance then just pass it in as parameter
   if (instructionMessageListHighP.remove(i)  ) {return;}
   if (instructionMessageListMediumP.remove(i)) {return;}
   instructionMessageListLowP.remove(i);
   return;
 }


 public void removeInstructionFromQueue(
  int instructionType,
  int productCode,
  int quantity,
  int uom,
  int timeStamp )
  {
   InstructionMessage im;

   im=grabInstructionMessage(instructionMessageListHighP,instructionType,productCode,quantity,uom,timeStamp);
   if (im!=null)  { removeInstructionFromQueue(im); return; }
   im=grabInstructionMessage(instructionMessageListMediumP,instructionType,productCode,quantity,uom,timeStamp);
   if (im!=null)  { removeInstructionFromQueue(im); return; }
   im=grabInstructionMessage(instructionMessageListLowP,instructionType,productCode,quantity,uom,timeStamp);
   if (im!=null)  { removeInstructionFromQueue(im); return; }
  
  }
 

 public InstructionMessage grabInstructionMessage(
  List<InstructionMessage> messageList,
  int instructionType,
  int productCode,
  int quantity,
  int uom,
  int timeStamp )
 {

  for (InstructionMessage x:messageList) {
   if (
        x.instructionType==instructionType &&
        x.productCode==productCode &&
        x.quantity==quantity &&
        x.uom==uom &&
        x.timeStamp==timeStamp
      )
       {return x;}
  }

  return null;
 }



 public InstructionMessage getNextInstructionMessage(){
  if (instructionMessageListHighP.size()>0)   {System.out.print("getting high mes ");return instructionMessageListHighP.remove(0);}
  if (instructionMessageListMediumP.size()>0) {System.out.print("getting mid  mes ");return instructionMessageListMediumP.remove(0);}
  if (instructionMessageListLowP.size()>0)    {System.out.print("getting low  mes ");return instructionMessageListLowP.remove(0);}
  return null;
 }

 public boolean queueIsEmpty(){
  if (instructionMessageListHighP.size()>0)   return true;
  if (instructionMessageListMediumP.size()>0) return true;
  if (instructionMessageListLowP.size()>0)    return true;  
  return false;
 } 


 public void checkInstructionIntegrity(InstructionMessage i) throws InvalidMessageException{
   
  if ( i.instructionType<1  ||
       i.instructionType>99 ||
       i.productCode<1      ||
       i.quantity<1         ||
       i.uom<0              ||
       i.uom>255            ||
       i.timeStamp<1         )
      { throw new InvalidMessageException(); }

 }


 public static void main(String[] args){
 }

}


class InstructionMessage{
 int instructionType;
 int productCode;
 int quantity;
 int uom;
 int timeStamp ;
}



class InvalidMessageException extends RuntimeException{

}
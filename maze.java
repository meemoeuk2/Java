// maze solver by JEGrist 2019 June 30th
//
// instructions on how to run :
// assuming Java sdk and jre installed and java environment variables are already set
//
// at CLI 
//  go to directory containing this file and type
//   javac maze.java
//   java maze "<filepath1>"
//  where filepath is the address of a maze input file
//  e.g.
//   java maze "C:\temp\Gentrack Maze Technical Test\Samples\medium_input.txt"
//  multiple mazes can be loaded
//   java maze "<filepath1>" "<filepath2>" ... "<filepathN>"

import java.io.*; 
import java.nio.file.*;
import java.util.*;



public class maze 
{ 
  int ic=0  ; // input element counter

  int w,h;  ; // width height
  int sx,sy ; // start x,y
  int fx,fy ; // finish x,y

  char[] mazelayout ;



  public int getIntFromString(char[] inputString){
  
   int k=0,c=0;

    while (true)   // move to next element that is numeric
    {            
     c=Character.getNumericValue(inputString[ic]);
     if (c>=0 && c<=9)  {break;}
     ic++;
    }
   
    while (true)  // read numeric characters
    {
     c=Character.getNumericValue(inputString[ic]);
     if (c<0 || c>9) {break;}
     k=k*10+c;
     ic++;
    }
    
   return k;
  }



  public void getMazeLayout(char[] data){
   // convert the input data into useful data array, without the meta data 
   
   mazelayout=new char[w*h];
   int k=0;  ; // data counter

   while (ic<data.length){    
    if (data[ic]=='0' || data[ic]=='1'){
     mazelayout[k]=data[ic];
     k++;
    }

    ic++;
   }

  }



  public void displayMaze(){
   for (int i=0;i<h;i++){
     System.out.println( Arrays.copyOfRange(mazelayout,i*w,(i+1)*w) );
   }   
  }



  public void getMazeData(String fn) throws Exception{
   // load data from file into a String

   String mazeData = new String(Files.readAllBytes(Paths.get(fn))); 
   char[] mazedata = mazeData.toCharArray();

   w= getIntFromString(mazedata);
   h= getIntFromString(mazedata);
   sx=getIntFromString(mazedata);
   sy=getIntFromString(mazedata);
   fx=getIntFromString(mazedata);
   fy=getIntFromString(mazedata);
   getMazeLayout(mazedata);  

  }



  public void draw_solution(){
   // before displaying solution, it needs formating to the output spec

   for (int i=0;i<w*h;i++){
    if (mazelayout[i]=='1') { mazelayout[i]='#'; }
    if (mazelayout[i]=='0') { mazelayout[i]=' '; }
    if (mazelayout[i]=='Z') { mazelayout[i]=' '; }
   }

   mazelayout[sx+sy*w]='S';
   mazelayout[fx+fy*w]='E';
  }



  public void display_solution(){
   // prints solutions

   char[] ca;   

   System.out.println("");
   for (int i=0;i<h;i++){
     ca=Arrays.copyOfRange(mazelayout,i*w,(i+1)*w);
     System.out.println(ca);
   }
  }



  public boolean solveMaze(int x,int y){

  // lazy recursive solution
  // short and simple but inefficient wrt memory

   // wrap around modifcation to location
   if (x<0) x=w-1;   
   x = x % w;
   if (y<0) y=h-1;
   y = y % h;

   if (mazelayout[x+y*w]!='0') return false;
   if (x==fx && y==fy)         return true;
   
   mazelayout[x+y*w]='X'; // optimistic : mark path as correct path

   if (solveMaze(x+1,y)==true)  return true;
   if (solveMaze(x,y+1)==true)  return true;
   if (solveMaze(x-1,y)==true)  return true;
   if (solveMaze(x,y-1)==true)  return true;

   mazelayout[x+y*w]='Z'; // no way found, so remark path as dead end
   return false;
  }



 public static void main(String[] args) throws Exception
 { 
  maze m;
   
  for (String d:args){
   m=new maze();
   m.getMazeData(d);
   m.displayMaze();

   if (m.solveMaze(m.sx,m.sy)==true){
    
    m.draw_solution();
    m.display_solution();
   }
   else{ System.out.println("no path from start to end found");} 

  }

 } 
} // end of class, eof
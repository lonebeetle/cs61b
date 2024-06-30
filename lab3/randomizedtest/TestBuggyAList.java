package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
  @Test
  public void testAddThreeRemoveThree() {
      BuggyAList<Integer> test1 = new BuggyAList<Integer>();
      AListNoResizing<Integer> test2 = new AListNoResizing<Integer>();
      for (int i = 1; i < 4; i += 1) {
          test1.addLast(i);
          test2.addLast(i);
      }
      for(int i=1;i<4;i++){
          test1.removeLast();
          test2.removeLast();
          assertEquals("It seems wrong",test2,test1);
      }
  }
  @Test
    public void randomizedTest(){
      AListNoResizing<Integer> L = new AListNoResizing<>();
      BuggyAList<Integer> L1 =new BuggyAList<>();
      int N=5000;
      for (int i = 0; i < N; i += 1) {
          int operationNumber = StdRandom.uniform(0, 3);
          if (operationNumber == 0) {
              // addLast
              int randVal = StdRandom.uniform(0, 100);
              L.addLast(randVal);
              L1.addLast(randVal);
              /*System.out.println("addLast(" + randVal + ")");**/
          } else if (operationNumber == 1) {
              // size
              int size = L.size();
              assertEquals("size diff",L.size(),L1.size());
              /*System.out.println("size: " + size);**/
          }
          else if(operationNumber == 2){
              if(L.size()>0) {
                  int val = L.removeLast();
                  int val1 = L1.removeLast();
                  assertEquals("getLast diff",val,val1);
                 /* System.out.println("removeLast(" + val + ")");**/
              }
          }
      }
  }
}

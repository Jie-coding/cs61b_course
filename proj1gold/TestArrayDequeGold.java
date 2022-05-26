import static org.junit.Assert.*;

import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;

public class TestArrayDequeGold {



    @Test
    public void TestArrayDeque(){
        ArrayDequeSolution<Integer> ArrayDequeSolution = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> StudentArrayDeque = new StudentArrayDeque<>();
        String message = "";

        for (int i = 0; i < 60; i += 1){
            double Num = StdRandom.uniform();
            Integer numToAdd = StdRandom.uniform(0, 200);

            if (Num < 0.5) {
                if (Num < 0.25) {
                    ArrayDequeSolution.addFirst(numToAdd);
                    StudentArrayDeque.addFirst(numToAdd);
                    message = message + "addFirst(" + numToAdd.toString() +") \n";
                } else {
                    ArrayDequeSolution.addLast(numToAdd);
                    StudentArrayDeque.addLast(numToAdd);
                    message = message + "addLast(" + numToAdd.toString() +") \n";
                }
            }
            else {
                if (ArrayDequeSolution.isEmpty() || StudentArrayDeque.isEmpty()){
                    continue;
                }
                if(Num < 0.75){
                    message = message + "removeFirst() \n";
                    assertEquals(message, ArrayDequeSolution.removeFirst(), StudentArrayDeque.removeFirst());
                }
                else {
                    message = message + "removeLast() \n";
                    assertEquals(message,ArrayDequeSolution.removeLast(), StudentArrayDeque.removeLast());
                }
            }
        }

    }

}
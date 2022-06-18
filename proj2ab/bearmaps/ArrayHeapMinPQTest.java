package bearmaps;

import edu.princeton.cs.introcs.StdRandom;
import org.junit.Before;
import org.junit.Test;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import static org.junit.Assert.*;


public class ArrayHeapMinPQTest {

    ArrayHeapMinPQ<String> PQ;
    @Before
    public void setup() {
        PQ = new ArrayHeapMinPQ<>();
        PQ.add("a", 3);
        PQ.add("b", 4);
        PQ.add("c", 2);
        PQ.add("d", 4);
        PQ.add("e", 5);
        PQ.add("f", 100);
        PQ.add("g", -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addExceptionTest() {
        PQ.add("g", 1);
    }

    @Test
    public void addSizeTest() {
        assertEquals(PQ.size(), 7);
        PQ.add("m", 100);
        PQ.add("n", 1000);
        PQ.contains("m");
        assertEquals(PQ.size(), 9);
    }

    @Test
    public void changePriorityTest() {
        assertEquals(PQ.getSmallest(), "g");
        PQ.changePriority("e", -1000);
        assertEquals(PQ.removeSmallest(), "e");
        assertEquals(PQ.removeSmallest(), "g");
        assertEquals(PQ.removeSmallest(), "c");
        assertEquals(PQ.removeSmallest(), "a");
        assertTrue(PQ.contains("d"));
        assertFalse(PQ.contains("e"));
        assertEquals(PQ.removeSmallest(), "d");
        assertEquals(PQ.removeSmallest(), "b");
        assertEquals(PQ.removeSmallest(), "f");
    }

    @Test
    public void getSmallestRandomTest() {
        int num = 1000;
        ArrayHeapMinPQ<Integer> PQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> NPQ = new NaiveMinPQ<>();
        Random random = new Random(100);
        for (int i = 1; i < num; i += 1) {
            int item = random.nextInt();
            double randomP = random.nextDouble();
            PQ.add(item,  randomP);
            NPQ.add(item, randomP);
            assertEquals(PQ.getSmallest(), NPQ.getSmallest());
        }
    }

    @Test
    public void changePriorityRandomTest() {
        int num = 1000;
        ArrayHeapMinPQ<Integer> PQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> NPQ = new NaiveMinPQ<>();
        Random random = new Random(100);
        int[] items = new int[num];

        for (int i = 0; i < num; i += 1) {
            int item = random.nextInt();
            double randomP = random.nextDouble();
            items[i] = item;
            PQ.add(item,  randomP);
            NPQ.add(item, randomP);
            assertEquals(PQ.getSmallest(), NPQ.getSmallest());
        }
        for (int i : items) {
            double randomNumber = StdRandom.uniform(-1000,1000);
            PQ.changePriority(i, randomNumber);
            NPQ.changePriority(i, randomNumber);
            assertEquals(PQ.getSmallest(), NPQ.getSmallest());
        }
    }

    @Test
    public void runningTimeTest() {
        int exp = 6;
        int base = 10;
        int num = (int) Math.pow(base, exp);
        ArrayHeapMinPQ<Integer> PQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> NPQ = new NaiveMinPQ<>();
        Random random = new Random(num);
        for (int i = 0; i < num; i += 1) {
            int item = i;
            double randomP = random.nextDouble();
            PQ.add(item,  randomP);
            NPQ.add(item, randomP);
        }

        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < num/10; i += 1){
            PQ.changePriority(i, i);
        }
        double timePQ = sw.elapsedTime();

        Stopwatch sw2 = new Stopwatch();
        for (int i = 0; i < num/10; i += 1) {
            NPQ.changePriority(i, i);
        }
        double timeNPQ = sw2.elapsedTime();
        System.out.printf("Time for HeapMinPQ is %.2f\n", timePQ);
        System.out.printf("Time for NaiveMinPQ is %.2f\n", timeNPQ);
    }

}
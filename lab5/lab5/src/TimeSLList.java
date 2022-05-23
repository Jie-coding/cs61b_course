import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
/**
 * Class that collects timing information about SLList getLast method.
 */
public class TimeSLList {
    private static void printTimingTable(SLList<Integer> Ns, SLList<Double> times, SLList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        int thousand = 1000;
        int exp = 10;
        SLList<Double> time = new SLList<Double>();
        SLList<Integer> dataSize = new SLList<Integer>();
        SLList<Integer> opCounts = new SLList<Integer>();

        for (int i = 0; i < exp; i++){
            dataSize.addLast(thousand * (int) Math.pow(2, i));
            opCounts.addLast(10000);
        }

        for(int i = 0; i < dataSize.size(); i++){
            SLList<Integer> timeTest = new SLList<Integer>();
            for (int m = 0; m < dataSize.get(i); m ++){
                timeTest.addLast(1);
            }
            Stopwatch sw = new Stopwatch();
            for (int n = 0; n < 10000; n++){
                timeTest.getLast();
            }
            time.addLast(sw.elapsedTime());
        }
        // getLast method is very slow because it has to loop to the end of the list
        System.out.println("Timing table for getLast");
        printTimingTable(dataSize, time, opCounts);
    }

}
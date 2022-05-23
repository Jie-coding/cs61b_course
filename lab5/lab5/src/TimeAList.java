import java.util.ArrayList;
import java.util.List;

/**
 * Class that collects timing information about AList construction.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
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
        timeAListConstruction();
    }

    public static void timeAListConstruction() {

        int thousand = 1000;
        int exp = 14;


        AList<Double> timeSet = new AList();
        AList<Integer> dataSize = new AList();

        for (int i = 0; i < exp; i++){
            dataSize.addLast( thousand* (int) Math.pow(2,i));
        }

        System.out.println("Timing Table for addLast");


        for (int i = 0; i < dataSize.size(); i++){
            AList<Double> container = new AList<>();
            Stopwatch sw = new Stopwatch();
            for (int m = 0; m < dataSize.get(i); m++){
                container.addLast( (double)1);
            }
            timeSet.addLast(sw.elapsedTime());
    }

        printTimingTable(dataSize, timeSet, dataSize);
    }
}
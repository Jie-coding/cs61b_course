import edu.princeton.cs.introcs.StdAudio;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdRandom;
import synthesizer.GuitarString;

public class GuitarHero {
    private static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static GuitarString[] CONCERTS;

    private static double calcFrequency (int i){
        return 440 * Math.pow(2.0, (i-24)/12.0);
    }

    public static void main(String[] args){
        CONCERTS = new GuitarString[37];
        for (int i = 0; i < 37; i += 1){
            CONCERTS[i] = new GuitarString(calcFrequency(i));
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                if (index < 0 || index > 36){
                    index = StdRandom.uniform(0, 37);
                }
                CONCERTS[index].pluck();
            }

            double sample = 0;
            for (int i = 0; i < 37; i += 1){
                sample += CONCERTS[i].sample();
                CONCERTS[i].tic();
            }
            StdAudio.play(sample);
        }

    }
}

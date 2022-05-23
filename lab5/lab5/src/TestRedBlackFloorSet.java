import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by hug.
 */
public class TestRedBlackFloorSet {
    @Test
    public void randomizedTest() {
        AListFloorSet aSet = new AListFloorSet();
        RedBlackFloorSet rbSet = new RedBlackFloorSet();
        for (int i = 0; i < 1000000; i+=1){
            double num = StdRandom.uniform(-5000, 5000);
            aSet.add(num);
            rbSet.add(num);
        }

        for (int i = 0; i < 1000000; i+=1){
            double floornum = StdRandom.uniform(-5000, 5000);
            assertEquals(aSet.floor(floornum), rbSet.floor(floornum), 0.000001);
        }
    }
}
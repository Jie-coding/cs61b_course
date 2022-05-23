import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    static CharacterComparator cc = new OffByN(5);

    @Test
    public void TestOffByN(){
        assertTrue(cc.equalChars('a','f'));
        assertTrue(cc.equalChars('c', 'h'));
        assertFalse(cc.equalChars('a', 'b'));
    }
}

import static org.junit.Assert.*;
import org.junit.Test;


public class UnionFindTest {

    @Test
    public void UnionFindTest (){
        UnionFind UnionFindDS = new UnionFind(10);
        UnionFindDS.connect(1,0);
        UnionFindDS.connect(3,2);
        UnionFindDS.connect(5,4);
        UnionFindDS.connect(5,2);


        assertFalse(UnionFindDS.isConnected(1,9));
        UnionFindDS.connect(1,5);
        assertTrue(UnionFindDS.isConnected(1,3));
        assertEquals(UnionFindDS.parent(1),2);
        assertEquals(UnionFindDS.parent(5),2);
        assertEquals(UnionFindDS.parent(9),-1);
    }
}

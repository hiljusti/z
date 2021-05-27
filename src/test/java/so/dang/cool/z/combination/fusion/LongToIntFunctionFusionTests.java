package so.dang.cool.z.combination.fusion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static so.dang.cool.z.combination.TestFunctions.*;

import org.junit.jupiter.api.Test;
import so.dang.cool.z.Z;
import so.dang.cool.z.annotation.Evil;

public class LongToIntFunctionFusionTests {

    @Test
    void longToInt() {
        assertEquals(3, longToInt.applyAsInt(3L));
    }

    @Test
    void longToInt_deep() {
        assertEquals(3, Z.with(longToInt).resolve().applyAsInt(3L));
    }

    @Test
    void longToInt_to_intFn() {
        assertEquals("1", Z.fuse(longToInt, intToString).apply(1L));
    }

    @Test
    void longToInt_to_intFn_deep() {
        assertEquals("1", Z.with(longToInt).fuse(intToString).apply(1L));
    }

    @Test
    void longToInt_to_intFn_deeper() {
        assertEquals(
            "1",
            Z.with(longToInt).fusing(intToString).resolve().apply(1L)
        );
    }

    @Test
    void longToInt_to_intToDbl() {
        assertEquals(1.0, Z.fuse(longToInt, intToDouble).applyAsDouble(1L));
    }

    @Test
    void longToInt_to_intToLong() {
        assertEquals(1, Z.fuse(longToInt, intToLong).applyAsLong(1L));
    }

    @Test
    void longToInt_to_intPred() {
        assertTrue(Z.fuse(longToInt, isIntTwo).test(2L));
    }

    @Evil
    @Test
    void longToInt_to_intCns() {
        synchronized (consumedIntA) {
            consumedIntA = 0;

            Z.fuse(longToInt, saveIntA).accept(3L);

            assertEquals(3, consumedIntA);
        }
    }

    @Test
    void longToInt_to_intUnop() {
        assertEquals(3, Z.fuse(longToInt, addTwoToInt).applyAsInt(1L));
    }

    @Test
    void longToInt_to_intBiop() {
        assertEquals(4, Z.fuse(longToInt, addInts).apply(1L).applyAsInt(3));
    }
}

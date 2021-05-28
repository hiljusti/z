package so.dang.cool.z.combination.fusion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static so.dang.cool.z.combination.TestFunctions.*;

import org.junit.jupiter.api.Test;
import so.dang.cool.z.Z;
import so.dang.cool.z.annotation.Evil;

public class BooleanSupplierFusionTests {

    @Test
    void boolSup() {
        assertTrue(getBooleanTrue.getAsBoolean());
        assertTrue(Z.with(getBooleanTrue).resolve().getAsBoolean());
    }

    @Test
    void boolSup_to_bifn() {
        assertEquals("HI", Z.fuse(getBooleanTrue, maybeToUpper).apply("hi"));
        assertEquals(
            "HI",
            Z.with(getBooleanTrue).fuse(maybeToUpper).apply("hi")
        );
        assertEquals(
            "HI",
            Z.with(getBooleanTrue).fusing(maybeToUpper).resolve().apply("hi")
        );
    }

    @Test
    void boolSup_to_boolFn() {
        assertEquals("true", Z.fuse(getBooleanTrue, booleanToString).get());
        assertEquals(
            "true",
            Z.with(getBooleanTrue).fuse(booleanToString).get()
        );
        assertEquals(
            "true",
            Z.with(getBooleanTrue).fusing(booleanToString).resolve().get()
        );
    }

    @Test
    void boolSup_to_toDblFn() {
        assertEquals(
            1.0,
            Z.fuse(getBooleanTrue, maybeOneAsDouble).getAsDouble()
        );
        assertEquals(
            1.0,
            Z.with(getBooleanTrue).fuse(maybeOneAsDouble).getAsDouble()
        );
        assertEquals(
            1.0,
            Z
                .with(getBooleanTrue)
                .fusing(maybeOneAsDouble)
                .resolve()
                .getAsDouble()
        );
    }

    @Test
    void boolSup_to_toDblBifn() {
        assertEquals(
            3.0,
            Z
                .fuse(getBooleanTrue, maybeAddOneToStringAsDouble)
                .applyAsDouble("2.0")
        );
        assertEquals(
            3.0,
            Z
                .with(getBooleanTrue)
                .fuse(maybeAddOneToStringAsDouble)
                .applyAsDouble("2.0")
        );
        assertEquals(
            3.0,
            Z
                .with(getBooleanTrue)
                .fusing(maybeAddOneToStringAsDouble)
                .resolve()
                .applyAsDouble("2.0")
        );
    }

    @Test
    void boolSup_to_toIntFn() {
        assertEquals(2, Z.fuse(getBooleanTrue, maybeTwoAsInt).getAsInt());
        assertEquals(2, Z.with(getBooleanTrue).fuse(maybeTwoAsInt).getAsInt());
        assertEquals(
            2,
            Z.with(getBooleanTrue).fusing(maybeTwoAsInt).resolve().getAsInt()
        );
    }

    @Test
    void boolSup_to_toIntBifn() {
        assertEquals(
            4,
            Z.fuse(getBooleanTrue, maybeAddTwoToStringAsInt).applyAsInt("2")
        );
        assertEquals(
            4,
            Z
                .with(getBooleanTrue)
                .fuse(maybeAddTwoToStringAsInt)
                .applyAsInt("2")
        );
        assertEquals(
            4,
            Z
                .with(getBooleanTrue)
                .fusing(maybeAddTwoToStringAsInt)
                .resolve()
                .applyAsInt("2")
        );
    }

    @Test
    void boolSup_to_toLongFn() {
        assertEquals(3L, Z.fuse(getBooleanTrue, maybeThreeAsLong).getAsLong());
        assertEquals(
            3L,
            Z.with(getBooleanTrue).fuse(maybeThreeAsLong).getAsLong()
        );
        assertEquals(
            3L,
            Z
                .with(getBooleanTrue)
                .fusing(maybeThreeAsLong)
                .resolve()
                .getAsLong()
        );
    }

    @Test
    void boolSup_to_toLongBifn() {
        assertEquals(
            7L,
            Z.fuse(getBooleanTrue, maybeAddThreeToStringAsLong).applyAsLong("4")
        );
        assertEquals(
            7L,
            Z
                .with(getBooleanTrue)
                .fuse(maybeAddThreeToStringAsLong)
                .applyAsLong("4")
        );
        assertEquals(
            7L,
            Z
                .with(getBooleanTrue)
                .fusing(maybeAddThreeToStringAsLong)
                .resolve()
                .applyAsLong("4")
        );
    }

    @Test
    void boolSup_to_pred() {
        assertFalse(Z.fuse(getBooleanTrue, not).getAsBoolean());
        assertFalse(Z.with(getBooleanTrue).fuse(not).getAsBoolean());
        assertFalse(
            Z.with(getBooleanTrue).fusing(not).resolve().getAsBoolean()
        );
    }

    @Test
    void boolSup_to_bipred() {
        assertTrue(Z.fuse(getBooleanTrue, maybeNotFromString).test("false"));
        assertTrue(
            Z.with(getBooleanTrue).fuse(maybeNotFromString).test("false")
        );
        assertTrue(
            Z
                .with(getBooleanTrue)
                .fusing(maybeNotFromString)
                .resolve()
                .test("false")
        );
    }

    @Evil
    @Test
    void boolSup_to_cns() {
        synchronized (consumedBooleanA) {
            consumedBooleanA = false;

            Z.fuse(getBooleanTrue, saveBooleanA).run();
            Z.with(getBooleanTrue).fuse(saveBooleanA).run();
            Z.with(getBooleanTrue).fusing(saveBooleanA).resolve().run();

            assertTrue(consumedBooleanA);
        }
    }

    @Evil
    @Test
    void boolSup_to_bicns() {
        synchronized (consumedBooleanB) {
            synchronized (consumedStringG) {
                consumedBooleanB = false;
                consumedStringG = "";

                Z.fuse(getBooleanTrue, saveBooleanBAndStringG).accept("z");
                Z.with(getBooleanTrue).fuse(saveBooleanBAndStringG).accept("z");
                Z
                    .with(getBooleanTrue)
                    .fusing(saveBooleanBAndStringG)
                    .resolve()
                    .accept("z");

                assertTrue(consumedBooleanB);
                assertEquals("z", consumedStringG);
            }
        }
    }

    @Test
    void boolSup_to_toUnop() {
        assertTrue(Z.fuse(getBooleanTrue, booleanId).getAsBoolean());
        assertTrue(Z.with(getBooleanTrue).fuse(booleanId).getAsBoolean());
        assertTrue(
            Z.with(getBooleanTrue).fusing(booleanId).resolve().getAsBoolean()
        );
    }
}

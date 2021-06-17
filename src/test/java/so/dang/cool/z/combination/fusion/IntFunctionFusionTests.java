package so.dang.cool.z.combination.fusion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static so.dang.cool.z.combination.TestFunctions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import so.dang.cool.z.Z;
import so.dang.cool.z.annotation.Evil;

public class IntFunctionFusionTests {

    @Test
    void intFn() {
        assertEquals("1", intToString.apply(1));
    }

    @Test
    void intFn_deep() {
        assertEquals("1", Z.with(intToString).resolve().apply(1));
    }

    @Test
    void intFn_to_fn() {
        assertEquals("1!", Z.fuse(intToString, addExclamationMark).apply(1));
    }

    @Test
    void intFn_to_fn_deep() {
        assertEquals(
            "1!",
            Z.with(intToString).fuse(addExclamationMark).apply(1)
        );
    }

    @Test
    void intFn_to_fn_deeper() {
        assertEquals(
            "1!",
            Z.with(intToString).fusing(addExclamationMark).resolve().apply(1)
        );
    }

    @Test
    void intFn_to_bifn() {
        Stream
            .of(
                Z.fuse(intToString, concat),
                Z.with(intToString).fuse(concat)
                //Z.with(intToString).fusing(concat).resolve()
            )
            .forEach(
                fusion -> assertEquals("1.0", fusion.apply(1).apply(".0"))
            );

        Stream
            .of(
                Z.with(intToString).fuse(concat, ".0"),
                Z.with(intToString).fusing(concat, ".0").resolve()
            )
            .forEach(fusion -> assertEquals("1.0", fusion.apply(1)));
    }

    @Test
    void intFn_to_toDblFn() {
        Stream
            .of(
                Z.fuse(intToString, stringToDouble),
                Z.with(intToString).fuse(stringToDouble),
                Z.with(intToString).fusing(stringToDouble).resolve()
            )
            .forEach(fusion -> assertEquals(1, fusion.applyAsDouble(1)));
    }

    @Test
    void intFn_to_toDblBifn() {
        Stream
            .of(
                Z.fuse(intToString, addStringsAsDouble),
                Z.with(intToString).fuse(addStringsAsDouble)
                //Z.with(intToString).fusing(addStringsAsDouble).resolve()
            )
            .forEach(
                fusion -> assertEquals(3, fusion.apply(1).applyAsDouble("2"))
            );

        Stream
            .of(
                Z.with(intToString).fuse(addStringsAsDouble, "2"),
                Z.with(intToString).fusing(addStringsAsDouble, "2").resolve()
            )
            .forEach(fusion -> assertEquals(3, fusion.applyAsDouble(1)));
    }

    @Test
    void intFn_to_toIntFn() {
        Stream
            .of(
                Z.fuse(intToString, stringToInt),
                Z.with(intToString).fuse(stringToInt),
                Z.with(intToString).fusing(stringToInt).resolve()
            )
            .forEach(fusion -> assertEquals(1, fusion.applyAsInt(1)));
    }

    @Test
    void intFn_to_toIntBifn() {
        Stream
            .of(
                Z.fuse(intToString, addStringsAsInt),
                Z.with(intToString).fuse(addStringsAsInt)
                //Z.with(intToString).fusing(addStringsAsInt).resolve()
            )
            .forEach(
                fusion -> assertEquals(3, fusion.apply(1).applyAsInt("2"))
            );

        Stream
            .of(
                Z.with(intToString).fuse(addStringsAsInt, "2"),
                Z.with(intToString).fusing(addStringsAsInt, "2").resolve()
            )
            .forEach(fusion -> assertEquals(3, fusion.applyAsInt(1)));
    }

    @Test
    void intFn_to_toLong() {
        Stream
            .of(
                Z.fuse(intToString, stringToLong),
                Z.with(intToString).fuse(stringToLong),
                Z.with(intToString).fusing(stringToLong).resolve()
            )
            .forEach(fusion -> assertEquals(1L, fusion.applyAsLong(1)));
    }

    @Test
    void intFn_to_toLongBifn() {
        Stream
            .of(
                Z.fuse(intToString, addStringsAsLong),
                Z.with(intToString).fuse(addStringsAsLong)
                //Z.with(intToString).fusing(addStringsAsLong).resolve()
            )
            .forEach(
                fusion -> assertEquals(3L, fusion.apply(1).applyAsLong("2"))
            );

        Stream
            .of(
                Z.with(intToString).fuse(addStringsAsLong, "2"),
                Z.with(intToString).fusing(addStringsAsLong, "2").resolve()
            )
            .forEach(fusion -> assertEquals(3L, fusion.applyAsLong(1)));
    }

    @Test
    void intFn_to_pred() {
        Stream
            .of(
                Z.fuse(intToString, isEmpty),
                Z.with(intToString).fuse(isEmpty),
                Z.with(intToString).fusing(isEmpty).resolve()
            )
            .forEach(fusion -> assertFalse(fusion.test(1)));
    }

    @Test
    void intFn_to_bipred() {
        Stream
            .of(
                Z.fuse(intToString, startsWith),
                Z.with(intToString).fuse(startsWith)
                // Z.with(intToString).fusing(startsWith).resolve()
            )
            .forEach(fusion -> assertTrue(fusion.apply(1).test("1")));

        Stream
            .of(
                Z.with(intToString).fuse(startsWith, "1"),
                Z.with(intToString).fusing(startsWith, "1").resolve()
            )
            .forEach(fusion -> assertTrue(fusion.test(1)));
    }

    @Evil
    @Test
    void intFn_to_cns() {
        synchronized (consumedStringA) {
            Stream
                .of(
                    Z.fuse(intToString, saveStringA),
                    Z.with(intToString).fuse(saveStringA),
                    Z.with(intToString).fusing(saveStringA).resolve()
                )
                .forEach(
                    fusion -> {
                        consumedStringA = "";

                        fusion.accept(2);

                        assertEquals("2", consumedStringA);
                    }
                );
        }
    }

    @Evil
    @Test
    void intFn_to_bicns() {
        synchronized (consumedStringB) {
            synchronized (consumedStringC) {
                Stream
                    .of(
                        Z.fuse(intToString, saveStringsBandC),
                        Z.with(intToString).fuse(saveStringsBandC)
                        //Z.with(intToString).fusing(saveStringsBandC).resolve()
                    )
                    .forEach(
                        fusion -> {
                            consumedStringB = "";
                            consumedStringC = "";

                            fusion.apply(3).accept("two and a half");

                            assertEquals("3", consumedStringB);
                            assertEquals("two and a half", consumedStringC);
                        }
                    );

                Stream
                    .of(
                        Z
                            .with(intToString)
                            .fuse(saveStringsBandC, "two and a half"),
                        Z
                            .with(intToString)
                            .fusing(saveStringsBandC, "two and a half")
                            .resolve()
                    )
                    .forEach(
                        fusion -> {
                            consumedStringB = "";
                            consumedStringC = "";

                            fusion.accept(3);

                            assertEquals("3", consumedStringB);
                            assertEquals("two and a half", consumedStringC);
                        }
                    );
            }
        }
    }

    @Evil
    @Test
    void intFn_to_objDblCns() {
        synchronized (consumedStringD) {
            synchronized (consumedDoubleB) {
                Stream
                    .of(
                        Z.fuse(intToString, saveStringDDoubleB),
                        Z.with(intToString).fuse(saveStringDDoubleB)
                        // Z.with(intToString).fusing(saveStringDDoubleB).resolve()
                    )
                    .forEach(
                        fusion -> {
                            consumedStringD = "";
                            consumedDoubleB = 0.0;

                            fusion.apply(4).accept(5.0);

                            assertEquals("4", consumedStringD);
                            assertEquals(5.0, consumedDoubleB);
                        }
                    );

                Stream
                    .of(
                        Z.with(intToString).fuse(saveStringDDoubleB, 5.0),
                        Z
                            .with(intToString)
                            .fusing(saveStringDDoubleB, 5.0)
                            .resolve()
                    )
                    .forEach(
                        fusion -> {
                            consumedStringD = "";
                            consumedDoubleB = 0.0;

                            fusion.accept(4);

                            assertEquals("4", consumedStringD);
                            assertEquals(5.0, consumedDoubleB);
                        }
                    );
            }
        }
    }

    @Evil
    @Test
    void intFn_to_objIntCns() {
        synchronized (consumedStringE) {
            synchronized (consumedIntB) {
                Stream
                    .of(
                        Z.fuse(intToString, saveStringEIntB),
                        Z.with(intToString).fuse(saveStringEIntB)
                        // Z.with(intToString).fusing(saveStringEIntB).resolve()
                    )
                    .forEach(
                        fusion -> {
                            consumedStringE = "";
                            consumedIntB = 0;

                            fusion.apply(8).accept(8);

                            assertEquals("8", consumedStringE);
                            assertEquals(8, consumedIntB);
                        }
                    );

                Stream
                    .of(
                        Z.with(intToString).fuse(saveStringEIntB, 8),
                        Z.with(intToString).fusing(saveStringEIntB, 8).resolve()
                    )
                    .forEach(
                        fusion -> {
                            consumedStringE = "";
                            consumedIntB = 0;

                            fusion.accept(8);

                            assertEquals("8", consumedStringE);
                            assertEquals(8, consumedIntB);
                        }
                    );
            }
        }
    }

    @Evil
    @Test
    void intFn_to_objLongCns() {
        synchronized (consumedStringF) {
            synchronized (consumedLongB) {
                Stream
                    .of(
                        Z.fuse(intToString, saveStringFLongB),
                        Z.with(intToString).fuse(saveStringFLongB)
                        // Z.with(intToString).fusing(saveStringFLongB).resolve()
                    )
                    .forEach(
                        fusion -> {
                            consumedStringF = "";
                            consumedLongB = 0L;

                            fusion.apply(6).accept(6L);

                            assertEquals("6", consumedStringF);
                            assertEquals(6L, consumedLongB);
                        }
                    );

                Stream
                    .of(
                        Z.with(intToString).fuse(saveStringFLongB, 6L),
                        Z
                            .with(intToString)
                            .fusing(saveStringFLongB, 6L)
                            .resolve()
                    )
                    .forEach(
                        fusion -> {
                            consumedStringF = "";
                            consumedLongB = 0L;

                            fusion.accept(6);

                            assertEquals("6", consumedStringF);
                            assertEquals(6L, consumedLongB);
                        }
                    );
            }
        }
    }

    @Test
    void intFn_to_unop() {
        Stream
            .of(
                Z.fuse(intToString, addQuestionMark),
                Z.with(intToString).fuse(addQuestionMark),
                Z.with(intToString).fusing(addQuestionMark).resolve()
            )
            .forEach(fusion -> assertEquals("10?", fusion.apply(10)));
    }

    @Test
    void intFn_to_biop() {
        Stream
            .of(
                Z.fuse(intToString, relation),
                Z.with(intToString).fuse(relation)
                // Z.with(intToString).fusing(relation).resolve()
            )
            .forEach(
                fusion -> assertEquals("same-ish", fusion.apply(11).apply("11"))
            );

        Stream
            .of(
                Z.with(intToString).fuse(relation, "11"),
                Z.with(intToString).fusing(relation, "11").resolve()
            )
            .forEach(fusion -> assertEquals("same-ish", fusion.apply(11)));
    }
}

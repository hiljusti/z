package so.dang.cool.z.internal.combination;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import so.dang.cool.z.Z;
import so.dang.cool.z.annotation.Evil;
import so.dang.cool.z.annotation.Experimental;
import so.dang.cool.z.function.BooleanConsumer;
import so.dang.cool.z.function.BooleanFunction;
import so.dang.cool.z.function.BooleanPredicate;
import so.dang.cool.z.function.BooleanToDoubleFunction;
import so.dang.cool.z.function.BooleanToIntFunction;
import so.dang.cool.z.function.BooleanToLongFunction;
import so.dang.cool.z.function.Operator;

/**
 * Deep fusions involving many functions.
 */
@Experimental
public abstract class Combine<A, Fn> {

    private Combine() {}

    /**
     * Retrieve the resulting fusion.
     */
    public abstract Fn resolve();

    /* Function -> ... [TODO: Incomplete] */

    public static class WithFunction<A, B> extends Combine<B, Function<A, B>> {

        private final transient Function<A, B> initial;

        private WithFunction(Function<A, B> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A, B> WithFunction<A, B> of(Function<A, B> initial) {
            return new WithFunction<>(initial);
        }

        @Override
        public Function<A, B> resolve() {
            return initial;
        }

        /* Function<A, B> -> Function<B, C> */

        public <C> Function<A, C> fuseFunction(Function<B, C> next) {
            return (A a) -> next.apply(initial.apply(a));
        }

        public <C> Function<A, C> fuse(Function<B, C> next) {
            return fuseFunction(next);
        }

        public <C> WithFunction<A, C> fusingFunction(Function<B, C> next) {
            return WithFunction.of(fuseFunction(next));
        }

        public <C> WithFunction<A, C> fusing(Function<B, C> next) {
            return fusingFunction(next);
        }

        /* Function<A, B> -> BiFunction<B, C, D> */

        public <C, D> Function<A, Function<C, D>> fuseBiFunction(
            BiFunction<B, C, D> next
        ) {
            return (A a) -> (C c) -> next.apply(initial.apply(a), c);
        }

        public <C, D> Function<A, Function<C, D>> fuse(
            BiFunction<B, C, D> next
        ) {
            return fuseBiFunction(next);
        }

        public <C, D> WithBiFunction<A, C, D> fusingBiFunction(
            BiFunction<B, C, D> next
        ) {
            // TODO: Need a currying combinator?
            return WithBiFunction.of(Z.assimilate2(fuseBiFunction(next)));
        }

        public <C, D> WithBiFunction<A, C, D> fusing(BiFunction<B, C, D> next) {
            return fusingBiFunction(next);
        }

        /* Function<A, B> -> DoubleFunction<B> */

        public ToDoubleFunction<A> fuseToDoubleFunction(
            ToDoubleFunction<B> next
        ) {
            return (A a) -> next.applyAsDouble(initial.apply(a));
        }

        public ToDoubleFunction<A> fuse(ToDoubleFunction<B> next) {
            return fuseToDoubleFunction(next);
        }

        public WithToDoubleFunction<A> fusingToDoubleFunction(
            ToDoubleFunction<B> next
        ) {
            return WithToDoubleFunction.of(fuseToDoubleFunction(next));
        }

        public WithToDoubleFunction<A> fusing(ToDoubleFunction<B> next) {
            return fusingToDoubleFunction(next);
        }

        /* Function<A, B> -> ToDoubleBiFunction<B> [TODO] */

        /* Function<A, B> -> ToIntFunction<B> */

        public ToIntFunction<A> fuseToIntFunction(ToIntFunction<B> next) {
            return (A a) -> next.applyAsInt(initial.apply(a));
        }

        public ToIntFunction<A> fuse(ToIntFunction<B> next) {
            return fuseToIntFunction(next);
        }

        public WithToIntFunction<A> fusingToIntFunction(ToIntFunction<B> next) {
            return WithToIntFunction.of(fuseToIntFunction(next));
        }

        public WithToIntFunction<A> fusing(ToIntFunction<B> next) {
            return fusingToIntFunction(next);
        }

        /* Function<A, B> -> ToIntBiFunction<B> [TODO] */

        /* Function<A, B> -> ToLongFunction<B> */

        public ToLongFunction<A> fuseToLongFunction(ToLongFunction<B> next) {
            return (A a) -> next.applyAsLong(initial.apply(a));
        }

        public ToLongFunction<A> fuse(ToLongFunction<B> next) {
            return fuseToLongFunction(next);
        }

        public WithToLongFunction<A> fusingToLongFunction(
            ToLongFunction<B> next
        ) {
            return WithToLongFunction.of(fuseToLongFunction(next));
        }

        public WithToLongFunction<A> fusing(ToLongFunction<B> next) {
            return fusingToLongFunction(next);
        }

        /* Function<A, B> -> ToLongBiFunction<B> [TODO] */

        /* Function<A, B> -> Predicate<B> */

        public Predicate<A> fusePredicate(Predicate<B> next) {
            return (A a) -> next.test(resolve().apply(a));
        }

        public Predicate<A> fuse(Predicate<B> next) {
            return fusePredicate(next);
        }

        public WithPredicate<A> fusingPredicate(Predicate<B> next) {
            return WithPredicate.of(fusePredicate(next));
        }

        public WithPredicate<A> fusing(Predicate<B> next) {
            return fusingPredicate(next);
        }

        /* Function<A, B> -> BiPredicate<B, C> [TODO] */

        /* Function<A, B> -> Consumer<B> */

        public Consumer<A> fuseConsumer(Consumer<B> next) {
            return (A a) -> next.accept(initial.apply(a));
        }

        public Consumer<A> fuse(Consumer<B> next) {
            return fuseConsumer(next);
        }

        public WithConsumer<A> fusingConsumer(Consumer<B> next) {
            return WithConsumer.of(fuseConsumer(next));
        }

        public WithConsumer<A> fusing(Consumer<B> next) {
            return fusingConsumer(next);
        }

        /* Function<A, B> -> BiConsumer<B, C> */

        public <C> Function<A, Consumer<C>> fuseBiConsumer(
            BiConsumer<B, C> next
        ) {
            return (A a) -> (C c) -> next.accept(initial.apply(a), c);
        }

        public <C> Function<A, Consumer<C>> fuse(BiConsumer<B, C> next) {
            return fuseBiConsumer(next);
        }

        public <C> WithBiConsumer<A, C> fusingBiConsumer(
            BiConsumer<B, C> next
        ) {
            return WithBiConsumer.of(fuseBiConsumer(next));
        }

        public <C> WithBiConsumer<A, C> fusing(BiConsumer<B, C> next) {
            return fusingBiConsumer(next);
        }

        /* Function<A, B> -> ObjDoubleConsumer<B, C> [TODO] */

        /* Function<A, B> -> ObjIntConsumer<B, C> [TODO] */

        /* Function<A, B> -> ObjLongConsumer<B, C> [TODO] */

        /* Function<A, B> -> UnaryOperator<B> */

        public Function<A, B> fuseUnaryOperator(UnaryOperator<B> next) {
            return (A a) -> next.apply(initial.apply(a));
        }

        public Function<A, B> fuse(UnaryOperator<B> next) {
            return fuseUnaryOperator(next);
        }

        public WithFunction<A, B> fusingUnaryOperator(UnaryOperator<B> next) {
            return WithFunction.of(fuseUnaryOperator(next));
        }

        public WithFunction<A, B> fusing(UnaryOperator<B> next) {
            return fusingUnaryOperator(next);
        }

        /* Function<A, B> -> BinaryOperator<B> */

        public Function<A, UnaryOperator<B>> fuseBinaryOperator(
            BinaryOperator<B> next
        ) {
            return (A a) -> (B b) -> next.apply(initial.apply(a), b);
        }

        public Function<A, UnaryOperator<B>> fuse(BinaryOperator<B> next) {
            return fuseBinaryOperator(next);
        }

        public WithBiFunction<A, B, B> fusingBinaryOperator(
            BinaryOperator<B> next
        ) {
            // TODO: simplify with currying
            return WithBiFunction.of(
                (A a, B b) -> next.apply(initial.apply(a), b)
            );
        }

        public WithBiFunction<A, B, B> fusing(BinaryOperator<B> next) {
            return fusingBinaryOperator(next);
        }
        /* Function<A, B> -> [Multi]Function<B...> [TODO...?] */
    }

    /*
        BiFunction<A, B, C> -> BiFunction<C, D, E>
        = Function<A, Function<B, Function<D, E>>>

        Function<A, B> -> BiFunction<B, C, D>
        = Function<A, Function<C, D>>
    */

    public static final class WithBiFunction<A, B, C>
        extends Combine<C, Function<A, Function<B, C>>> {

        private final transient BiFunction<A, B, C> initial;

        private WithBiFunction(BiFunction<A, B, C> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A, B, C> WithBiFunction<A, B, C> of(
            BiFunction<A, B, C> initial
        ) {
            return new WithBiFunction<>(initial);
        }

        public static <A, B, C> WithBiFunction<A, B, C> of(
            Function<A, Function<B, C>> initial
        ) {
            return new WithBiFunction<>(
                (A a, B b) -> initial.apply(a).apply(b)
            );
        }

        @Override
        public Function<A, Function<B, C>> resolve() {
            return (A a) -> (B b) -> initial.apply(a, b);
        }

        /* BiFunction<A, B, C> -> Function<C, D> */

        public <D> Function<A, Function<B, D>> fuseFunction(
            Function<C, D> next
        ) {
            return (A a) -> (B b) -> next.apply(initial.apply(a, b));
        }

        public <D> Function<A, Function<B, D>> fuse(Function<C, D> next) {
            return fuseFunction(next);
        }

        public <D> WithBiFunction<A, B, D> fusingFunction(Function<C, D> next) {
            return WithBiFunction.of(fuseFunction(next));
        }

        public <D> WithBiFunction<A, B, D> fusing(Function<C, D> next) {
            return fusingFunction(next);
        }
    }

    public static final class WithBooleanFunction<A>
        extends Combine<A, BooleanFunction<A>> {

        private final transient BooleanFunction<A> initial;

        private WithBooleanFunction(BooleanFunction<A> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithBooleanFunction<A> of(
            BooleanFunction<A> initial
        ) {
            return new WithBooleanFunction<>(initial);
        }

        @Override
        public BooleanFunction<A> resolve() {
            return initial;
        }

        /* BooleanFunction<A> -> Function<A,B> */

        public <B> BooleanFunction<B> fuseFunction(Function<A, B> next) {
            return (boolean b) -> next.apply(initial.apply(b));
        }

        public <B> BooleanFunction<B> fuse(Function<A, B> next) {
            return fuseFunction(next);
        }

        public <B> WithBooleanFunction<B> fusingFunction(Function<A, B> next) {
            return WithBooleanFunction.of(fuseFunction(next));
        }

        public <B> WithBooleanFunction<B> fusing(Function<A, B> next) {
            return fusingFunction(next);
        }
    }

    public static final class WithBooleanToDoubleFunction
        extends Combine<Double, BooleanToDoubleFunction> {

        private final transient BooleanToDoubleFunction initial;

        private WithBooleanToDoubleFunction(BooleanToDoubleFunction initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static WithBooleanToDoubleFunction of(
            BooleanToDoubleFunction initial
        ) {
            return new WithBooleanToDoubleFunction(initial);
        }

        @Override
        public BooleanToDoubleFunction resolve() {
            return initial;
        }

        /* BooleanToDoubleFunction<A> -> DoubleFunction<B> */

        public <A> BooleanFunction<A> fuseDoubleFunction(
            DoubleFunction<A> next
        ) {
            return (boolean b) -> next.apply(initial.applyAsDouble(b));
        }

        public <A> BooleanFunction<A> fuse(DoubleFunction<A> next) {
            return fuseDoubleFunction(next);
        }

        public <A> WithBooleanFunction<A> fusingDoubleFunction(
            DoubleFunction<A> next
        ) {
            return WithBooleanFunction.of(fuseDoubleFunction(next));
        }

        public <A> WithBooleanFunction<A> fusing(DoubleFunction<A> next) {
            return fusingDoubleFunction(next);
        }
    }

    public static final class WithBooleanToIntFunction
        extends Combine<Integer, BooleanToIntFunction> {

        private final transient BooleanToIntFunction initial;

        private WithBooleanToIntFunction(BooleanToIntFunction initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static WithBooleanToIntFunction of(
            BooleanToIntFunction initial
        ) {
            return new WithBooleanToIntFunction(initial);
        }

        @Override
        public BooleanToIntFunction resolve() {
            return initial;
        }

        /* BooleanToIntFunction<A> -> IntFunction<B> */

        public <A> BooleanFunction<A> fuseIntFunction(IntFunction<A> next) {
            return (boolean b) -> next.apply(initial.applyAsInt(b));
        }

        public <A> BooleanFunction<A> fuse(IntFunction<A> next) {
            return fuseIntFunction(next);
        }

        public <A> WithBooleanFunction<A> fusingIntFunction(
            IntFunction<A> next
        ) {
            return WithBooleanFunction.of(fuseIntFunction(next));
        }

        public <A> WithBooleanFunction<A> fusing(IntFunction<A> next) {
            return fusingIntFunction(next);
        }
    }

    public static final class WithBooleanToLongFunction
        extends Combine<Long, BooleanToLongFunction> {

        private final transient BooleanToLongFunction initial;

        private WithBooleanToLongFunction(BooleanToLongFunction initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static WithBooleanToLongFunction of(
            BooleanToLongFunction initial
        ) {
            return new WithBooleanToLongFunction(initial);
        }

        @Override
        public BooleanToLongFunction resolve() {
            return initial;
        }

        /* BooleanToLongFunction<A> -> LongFunction<B> */

        public <A> BooleanFunction<A> fuseLongFunction(LongFunction<A> next) {
            return (boolean b) -> next.apply(initial.applyAsLong(b));
        }

        public <A> BooleanFunction<A> fuse(LongFunction<A> next) {
            return fuseLongFunction(next);
        }

        public <A> WithBooleanFunction<A> fusingLongFunction(
            LongFunction<A> next
        ) {
            return WithBooleanFunction.of(fuseLongFunction(next));
        }

        public <A> WithBooleanFunction<A> fusing(LongFunction<A> next) {
            return fusingLongFunction(next);
        }
    }

    public static final class WithDoubleFunction<A>
        extends Combine<A, DoubleFunction<A>> {

        private final transient DoubleFunction<A> initial;

        private WithDoubleFunction(DoubleFunction<A> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithDoubleFunction<A> of(DoubleFunction<A> initial) {
            return new WithDoubleFunction<>(initial);
        }

        @Override
        public DoubleFunction<A> resolve() {
            return initial;
        }

        /* DoubleFunction<A> -> Function<A,B> */

        public <B> DoubleFunction<B> fuseFunction(Function<A, B> next) {
            return (double d) -> next.apply(initial.apply(d));
        }

        public <B> DoubleFunction<B> fuse(Function<A, B> next) {
            return fuseFunction(next);
        }

        public <B> WithDoubleFunction<B> fusingFunction(Function<A, B> next) {
            return WithDoubleFunction.of(fuseFunction(next));
        }

        public <B> WithDoubleFunction<B> fusing(Function<A, B> next) {
            return fusingFunction(next);
        }
    }

    public static final class WithDoubleToIntFunction
        extends Combine<Integer, DoubleToIntFunction> {

        private final transient DoubleToIntFunction initial;

        private WithDoubleToIntFunction(DoubleToIntFunction initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static WithDoubleToIntFunction of(DoubleToIntFunction initial) {
            return new WithDoubleToIntFunction(initial);
        }

        @Override
        public DoubleToIntFunction resolve() {
            return initial;
        }

        /* DoubleToIntFunction -> IntFunction<A> */

        public <A> DoubleFunction<A> fuseFunction(IntFunction<A> next) {
            return (double d) -> next.apply(initial.applyAsInt(d));
        }

        public <A> DoubleFunction<A> fuse(IntFunction<A> next) {
            return fuseFunction(next);
        }

        public <A> WithDoubleFunction<A> fusingFunction(IntFunction<A> next) {
            return WithDoubleFunction.of(fuseFunction(next));
        }

        public <A> WithDoubleFunction<A> fusing(IntFunction<A> next) {
            return fusingFunction(next);
        }
    }

    public static final class WithDoubleToLongFunction
        extends Combine<Long, DoubleToLongFunction> {

        private final transient DoubleToLongFunction initial;

        private WithDoubleToLongFunction(DoubleToLongFunction initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static WithDoubleToLongFunction of(
            DoubleToLongFunction initial
        ) {
            return new WithDoubleToLongFunction(initial);
        }

        @Override
        public DoubleToLongFunction resolve() {
            return initial;
        }

        /* DoubleToLongFunction -> LongFunction<A> */

        public <A> DoubleFunction<A> fuseFunction(LongFunction<A> next) {
            return (double d) -> next.apply(initial.applyAsLong(d));
        }

        public <A> DoubleFunction<A> fuse(LongFunction<A> next) {
            return fuseFunction(next);
        }

        public <A> WithDoubleFunction<A> fusingFunction(LongFunction<A> next) {
            return WithDoubleFunction.of(fuseFunction(next));
        }

        public <A> WithDoubleFunction<A> fusing(LongFunction<A> next) {
            return fusingFunction(next);
        }
    }

    public static final class WithToDoubleFunction<A>
        extends Combine<Double, ToDoubleFunction<A>> {

        private final transient ToDoubleFunction<A> initial;

        private WithToDoubleFunction(ToDoubleFunction<A> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithToDoubleFunction<A> of(
            ToDoubleFunction<A> initial
        ) {
            return new WithToDoubleFunction<>(initial);
        }

        @Override
        public ToDoubleFunction<A> resolve() {
            return initial;
        }

        /* ToDoubleFunction<A> -> DoubleFunction<B> */

        public <B> Function<A, B> fuseDoubleFunction(DoubleFunction<B> next) {
            return (A a) -> next.apply(initial.applyAsDouble(a));
        }

        public <B> Function<A, B> fuse(DoubleFunction<B> next) {
            return fuseDoubleFunction(next);
        }

        public <B> WithFunction<A, B> fusingDoubleFunction(
            DoubleFunction<B> next
        ) {
            return WithFunction.of(fuseDoubleFunction(next));
        }

        public <B> WithFunction<A, B> fusing(DoubleFunction<B> next) {
            return fusingDoubleFunction(next);
        }
    }

    public static final class WithToDoubleBiFunction<A, B>
        extends Combine<Double, Function<A, ToDoubleFunction<B>>> {

        private final transient ToDoubleBiFunction<A, B> initial;

        private WithToDoubleBiFunction(ToDoubleBiFunction<A, B> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A, B> WithToDoubleBiFunction<A, B> of(
            ToDoubleBiFunction<A, B> initial
        ) {
            return new WithToDoubleBiFunction<>(initial);
        }

        @Override
        public Function<A, ToDoubleFunction<B>> resolve() {
            return (A a) -> (B b) -> initial.applyAsDouble(a, b);
        }

        /* ToDoubleBiFunction<A, B> -> DoubleFunction<C> */

        public <C> Function<A, Function<B, C>> fuseDoubleFunction(
            DoubleFunction<C> next
        ) {
            return (A a) -> (B b) -> next.apply(initial.applyAsDouble(a, b));
        }

        public <C> Function<A, Function<B, C>> fuse(DoubleFunction<C> next) {
            return fuseDoubleFunction(next);
        }

        public <C> WithBiFunction<A, B, C> fusingDoubleFunction(
            DoubleFunction<C> next
        ) {
            return WithBiFunction.of(fuseDoubleFunction(next));
        }

        public <C> WithBiFunction<A, B, C> fusing(DoubleFunction<C> next) {
            return fusingDoubleFunction(next);
        }
    }

    public static final class WithIntFunction<A>
        extends Combine<A, IntFunction<A>> {

        private final transient IntFunction<A> initial;

        private WithIntFunction(IntFunction<A> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithIntFunction<A> of(IntFunction<A> initial) {
            return new WithIntFunction<>(initial);
        }

        @Override
        public IntFunction<A> resolve() {
            return initial;
        }

        /* IntFunction<A> -> Function<A,B> */

        public <B> IntFunction<B> fuseFunction(Function<A, B> next) {
            return (int i) -> next.apply(initial.apply(i));
        }

        public <B> IntFunction<B> fuse(Function<A, B> next) {
            return fuseFunction(next);
        }

        public <B> WithIntFunction<B> fusingFunction(Function<A, B> next) {
            return WithIntFunction.of(fuseFunction(next));
        }

        public <B> WithIntFunction<B> fusing(Function<A, B> next) {
            return fusingFunction(next);
        }
    }

    public static final class WithIntToDoubleFunction
        extends Combine<Integer, IntToDoubleFunction> {

        private final transient IntToDoubleFunction initial;

        private WithIntToDoubleFunction(IntToDoubleFunction initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static WithIntToDoubleFunction of(IntToDoubleFunction initial) {
            return new WithIntToDoubleFunction(initial);
        }

        @Override
        public IntToDoubleFunction resolve() {
            return initial;
        }

        /* IntToDoubleFunction -> DoubleFunction<A> */

        public <A> IntFunction<A> fuseFunction(DoubleFunction<A> next) {
            return (int i) -> next.apply(initial.applyAsDouble(i));
        }

        public <A> IntFunction<A> fuse(DoubleFunction<A> next) {
            return fuseFunction(next);
        }

        public <A> WithIntFunction<A> fusingFunction(DoubleFunction<A> next) {
            return WithIntFunction.of(fuseFunction(next));
        }

        public <A> WithIntFunction<A> fusing(DoubleFunction<A> next) {
            return fusingFunction(next);
        }
    }

    public static final class WithIntToLongFunction
        extends Combine<Long, IntToLongFunction> {

        private final transient IntToLongFunction initial;

        private WithIntToLongFunction(IntToLongFunction initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static WithIntToLongFunction of(IntToLongFunction initial) {
            return new WithIntToLongFunction(initial);
        }

        @Override
        public IntToLongFunction resolve() {
            return initial;
        }

        /* IntToLongFunction -> LongFunction<A> */

        public <A> IntFunction<A> fuseFunction(LongFunction<A> next) {
            return (int i) -> next.apply(initial.applyAsLong(i));
        }

        public <A> IntFunction<A> fuse(LongFunction<A> next) {
            return fuseFunction(next);
        }

        public <A> WithIntFunction<A> fusingFunction(LongFunction<A> next) {
            return WithIntFunction.of(fuseFunction(next));
        }

        public <A> WithIntFunction<A> fusing(LongFunction<A> next) {
            return fusingFunction(next);
        }
    }

    public static final class WithToIntFunction<A>
        extends Combine<Integer, ToIntFunction<A>> {

        private final transient ToIntFunction<A> initial;

        private WithToIntFunction(ToIntFunction<A> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithToIntFunction<A> of(ToIntFunction<A> initial) {
            return new WithToIntFunction<>(initial);
        }

        @Override
        public ToIntFunction<A> resolve() {
            return initial;
        }

        /* ToIntFunction<A> -> IntFunction<B> */

        public <B> Function<A, B> fuseIntFunction(IntFunction<B> next) {
            return (A a) -> next.apply(initial.applyAsInt(a));
        }

        public <B> Function<A, B> fuse(IntFunction<B> next) {
            return fuseIntFunction(next);
        }

        public <B> WithFunction<A, B> fusingIntFunction(IntFunction<B> next) {
            return WithFunction.of(fuseIntFunction(next));
        }

        public <B> WithFunction<A, B> fusing(IntFunction<B> next) {
            return fusingIntFunction(next);
        }
    }

    public static final class WithToIntBiFunction<A, B>
        extends Combine<Integer, Function<A, ToIntFunction<B>>> {

        private final transient ToIntBiFunction<A, B> initial;

        private WithToIntBiFunction(ToIntBiFunction<A, B> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A, B> WithToIntBiFunction<A, B> of(
            ToIntBiFunction<A, B> initial
        ) {
            return new WithToIntBiFunction<>(initial);
        }

        @Override
        public Function<A, ToIntFunction<B>> resolve() {
            return (A a) -> (B b) -> initial.applyAsInt(a, b);
        }

        /* ToIntBiFunction<A, B> -> IntFunction<C> */

        public <C> Function<A, Function<B, C>> fuseIntFunction(
            IntFunction<C> next
        ) {
            return (A a) -> (B b) -> next.apply(initial.applyAsInt(a, b));
        }

        public <C> Function<A, Function<B, C>> fuse(IntFunction<C> next) {
            return fuseIntFunction(next);
        }

        public <C> WithBiFunction<A, B, C> fusingIntFunction(
            IntFunction<C> next
        ) {
            return WithBiFunction.of(fuseIntFunction(next));
        }

        public <C> WithBiFunction<A, B, C> fusing(IntFunction<C> next) {
            return fusingIntFunction(next);
        }
    }

    public static final class WithLongFunction<A>
        extends Combine<A, LongFunction<A>> {

        private final transient LongFunction<A> initial;

        private WithLongFunction(LongFunction<A> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithLongFunction<A> of(LongFunction<A> initial) {
            return new WithLongFunction<>(initial);
        }

        @Override
        public LongFunction<A> resolve() {
            return initial;
        }

        /* LongFunction<A> -> Function<A,B> */

        public <B> LongFunction<B> fuseFunction(Function<A, B> next) {
            return (long n) -> next.apply(initial.apply(n));
        }

        public <B> LongFunction<B> fuse(Function<A, B> next) {
            return fuseFunction(next);
        }

        public <B> WithLongFunction<B> fusingFunction(Function<A, B> next) {
            return WithLongFunction.of(fuseFunction(next));
        }

        public <B> WithLongFunction<B> fusing(Function<A, B> next) {
            return fusingFunction(next);
        }
    }

    public static final class WithLongToDoubleFunction
        extends Combine<Long, LongToDoubleFunction> {

        private final transient LongToDoubleFunction initial;

        private WithLongToDoubleFunction(LongToDoubleFunction initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static WithLongToDoubleFunction of(
            LongToDoubleFunction initial
        ) {
            return new WithLongToDoubleFunction(initial);
        }

        @Override
        public LongToDoubleFunction resolve() {
            return initial;
        }

        /* LongToDoubleFunction -> DoubleFunction<A> */

        public <A> LongFunction<A> fuseFunction(DoubleFunction<A> next) {
            return (long n) -> next.apply(initial.applyAsDouble(n));
        }

        public <A> LongFunction<A> fuse(DoubleFunction<A> next) {
            return fuseFunction(next);
        }

        public <A> WithLongFunction<A> fusingFunction(DoubleFunction<A> next) {
            return WithLongFunction.of(fuseFunction(next));
        }

        public <A> WithLongFunction<A> fusing(DoubleFunction<A> next) {
            return fusingFunction(next);
        }
    }

    public static final class WithLongToIntFunction
        extends Combine<Long, LongToIntFunction> {

        private final transient LongToIntFunction initial;

        private WithLongToIntFunction(LongToIntFunction initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static WithLongToIntFunction of(LongToIntFunction initial) {
            return new WithLongToIntFunction(initial);
        }

        @Override
        public LongToIntFunction resolve() {
            return initial;
        }

        /* LongToIntFunction -> IntFunction<A> */

        public <A> LongFunction<A> fuseFunction(IntFunction<A> next) {
            return (long n) -> next.apply(initial.applyAsInt(n));
        }

        public <A> LongFunction<A> fuse(IntFunction<A> next) {
            return fuseFunction(next);
        }

        public <A> WithLongFunction<A> fusingFunction(IntFunction<A> next) {
            return WithLongFunction.of(fuseFunction(next));
        }

        public <A> WithLongFunction<A> fusing(IntFunction<A> next) {
            return fusingFunction(next);
        }
    }

    public static final class WithToLongFunction<A>
        extends Combine<Long, ToLongFunction<A>> {

        private final transient ToLongFunction<A> initial;

        private WithToLongFunction(ToLongFunction<A> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithToLongFunction<A> of(ToLongFunction<A> initial) {
            return new WithToLongFunction<>(initial);
        }

        @Override
        public ToLongFunction<A> resolve() {
            return initial;
        }

        /* ToLongFunction<A> -> LongFunction<B> */

        public <B> Function<A, B> fuseLongFunction(LongFunction<B> next) {
            return (A a) -> next.apply(initial.applyAsLong(a));
        }

        public <B> Function<A, B> fuse(LongFunction<B> next) {
            return fuseLongFunction(next);
        }

        public <B> WithFunction<A, B> fusingLongFunction(LongFunction<B> next) {
            return WithFunction.of(fuseLongFunction(next));
        }

        public <B> WithFunction<A, B> fusing(LongFunction<B> next) {
            return fusingLongFunction(next);
        }
    }

    public static final class WithToLongBiFunction<A, B>
        extends Combine<Long, Function<A, ToLongFunction<B>>> {

        private final transient ToLongBiFunction<A, B> initial;

        private WithToLongBiFunction(ToLongBiFunction<A, B> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A, B> WithToLongBiFunction<A, B> of(
            ToLongBiFunction<A, B> initial
        ) {
            return new WithToLongBiFunction<>(initial);
        }

        @Override
        public Function<A, ToLongFunction<B>> resolve() {
            return (A a) -> (B b) -> initial.applyAsLong(a, b);
        }

        /* ToLongBiFunction<A, B> -> LongFunction<C> */

        public <C> Function<A, Function<B, C>> fuseLongFunction(
            LongFunction<C> next
        ) {
            return (A a) -> (B b) -> next.apply(initial.applyAsLong(a, b));
        }

        public <C> Function<A, Function<B, C>> fuse(LongFunction<C> next) {
            return fuseLongFunction(next);
        }

        public <C> WithBiFunction<A, B, C> fusingLongFunction(
            LongFunction<C> next
        ) {
            return WithBiFunction.of(fuseLongFunction(next));
        }

        public <C> WithBiFunction<A, B, C> fusing(LongFunction<C> next) {
            return fusingLongFunction(next);
        }
    }

    @Evil
    public static final class WithConsumer<A>
        extends Combine<Void, Consumer<A>> {

        private final transient Consumer<A> initial;

        private WithConsumer(Consumer<A> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithConsumer<A> of(Consumer<A> initial) {
            return new WithConsumer<>(initial);
        }

        @Override
        public Consumer<A> resolve() {
            return initial;
        }

        /* Consumer<A> -> Function<B, C> [TODO? And etc?] */

        /* Consumer<A> -> Supplier<B> */

        @Evil
        public <B> Function<A, B> absorbSupplier(Supplier<B> next) {
            return (A a) -> {
                initial.accept(a);
                return next.get();
            };
        }

        @Evil
        public <B> Function<A, B> absorb(Supplier<B> next) {
            return absorbSupplier(next);
        }

        @Evil
        public <B> WithFunction<A, B> absorbingSupplier(Supplier<B> next) {
            return WithFunction.of(absorbSupplier(next));
        }

        @Evil
        public <B> WithFunction<A, B> absorbing(Supplier<B> next) {
            return absorbingSupplier(next);
        }
    }

    @Evil
    public static final class WithBiConsumer<A, B>
        extends Combine<Void, Function<A, Consumer<B>>> {

        private final transient BiConsumer<A, B> initial;

        private WithBiConsumer(BiConsumer<A, B> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A, B> WithBiConsumer<A, B> of(BiConsumer<A, B> initial) {
            return new WithBiConsumer<>(initial);
        }

        public static <A, B> WithBiConsumer<A, B> of(
            Function<A, Consumer<B>> initial
        ) {
            return new WithBiConsumer<>(
                (A a, B b) -> initial.apply(a).accept(b)
            );
        }

        @Override
        public Function<A, Consumer<B>> resolve() {
            return (A a) -> (B b) -> initial.accept(a, b);
        }

        /* Consumer<A> -> Function<B, C> [TODO? And etc?] */

        /* Consumer<A> -> Supplier<B> */

        @Evil
        public <C> Function<A, Function<B, C>> absorbSupplier(
            Supplier<C> next
        ) {
            return (A a) ->
                (B b) -> {
                    initial.accept(a, b);
                    return next.get();
                };
        }

        @Evil
        public <C> Function<A, Function<B, C>> absorb(Supplier<C> next) {
            return absorbSupplier(next);
        }

        @Evil
        public <C> WithBiFunction<A, B, C> absorbingSupplier(Supplier<C> next) {
            return WithBiFunction.of(absorbSupplier(next));
        }

        @Evil
        public <C> WithBiFunction<A, B, C> absorbing(Supplier<C> next) {
            return absorbingSupplier(next);
        }
    }

    @Evil
    public static final class WithBooleanConsumer
        extends Combine<Void, BooleanConsumer> {

        private final transient BooleanConsumer initial;

        private WithBooleanConsumer(BooleanConsumer initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static WithBooleanConsumer of(BooleanConsumer initial) {
            return new WithBooleanConsumer(initial);
        }

        @Override
        public BooleanConsumer resolve() {
            return initial;
        }
    }

    @Evil
    public static final class WithDoubleConsumer
        extends Combine<Void, DoubleConsumer> {

        private final transient DoubleConsumer initial;

        private WithDoubleConsumer(DoubleConsumer initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static WithDoubleConsumer of(DoubleConsumer initial) {
            return new WithDoubleConsumer(initial);
        }

        @Override
        public DoubleConsumer resolve() {
            return initial;
        }
    }

    @Evil
    public static final class WithObjDoubleConsumer<A>
        extends Combine<Void, Function<A, DoubleConsumer>> {

        private final transient ObjDoubleConsumer<A> initial;

        private WithObjDoubleConsumer(ObjDoubleConsumer<A> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithObjDoubleConsumer<A> of(
            ObjDoubleConsumer<A> initial
        ) {
            return new WithObjDoubleConsumer<>(initial);
        }

        @Override
        public Function<A, DoubleConsumer> resolve() {
            return (A a) -> (double d) -> initial.accept(a, d);
        }
    }

    @Evil
    public static final class WithIntConsumer
        extends Combine<Void, IntConsumer> {

        private final transient IntConsumer initial;

        private WithIntConsumer(IntConsumer initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static WithIntConsumer of(IntConsumer initial) {
            return new WithIntConsumer(initial);
        }

        @Override
        public IntConsumer resolve() {
            return initial;
        }
    }

    @Evil
    public static final class WithObjIntConsumer<A>
        extends Combine<Void, Function<A, IntConsumer>> {

        private final transient ObjIntConsumer<A> initial;

        private WithObjIntConsumer(ObjIntConsumer<A> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithObjIntConsumer<A> of(ObjIntConsumer<A> initial) {
            return new WithObjIntConsumer<>(initial);
        }

        @Override
        public Function<A, IntConsumer> resolve() {
            return (A a) -> (int n) -> initial.accept(a, n);
        }
    }

    @Evil
    public static final class WithLongConsumer
        extends Combine<Void, LongConsumer> {

        private final transient LongConsumer initial;

        private WithLongConsumer(LongConsumer initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static WithLongConsumer of(LongConsumer initial) {
            return new WithLongConsumer(initial);
        }

        @Override
        public LongConsumer resolve() {
            return initial;
        }
    }

    @Evil
    public static final class WithObjLongConsumer<A>
        extends Combine<Void, Function<A, LongConsumer>> {

        private final transient ObjLongConsumer<A> initial;

        private WithObjLongConsumer(ObjLongConsumer<A> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithObjLongConsumer<A> of(
            ObjLongConsumer<A> initial
        ) {
            return new WithObjLongConsumer<>(initial);
        }

        @Override
        public Function<A, LongConsumer> resolve() {
            return (A a) -> (long n) -> initial.accept(a, n);
        }
    }

    public static final class WithPredicate<A>
        extends Combine<Boolean, Predicate<A>> {

        private final transient Predicate<A> initial;

        private WithPredicate(Predicate<A> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithPredicate<A> of(Predicate<A> initial) {
            return new WithPredicate<>(initial);
        }

        @Override
        public Predicate<A> resolve() {
            return initial;
        }

        /* Predicate<A> -> BooleanFunction<B> */

        public <B> Function<A, B> fuseFunction(BooleanFunction<B> next) {
            return (A a) -> next.apply(initial.test(a));
        }

        public <B> Function<A, B> fuse(BooleanFunction<B> next) {
            return fuseFunction(next);
        }

        public <B> WithFunction<A, B> fusingFunction(BooleanFunction<B> next) {
            return WithFunction.of(fuseFunction(next));
        }

        public <B> WithFunction<A, B> fusing(BooleanFunction<B> next) {
            return fusingFunction(next);
        }
    }

    public static final class WithBiPredicate<A, B>
        extends Combine<Boolean, Function<A, Predicate<B>>> {

        private final transient BiPredicate<A, B> initial;

        private WithBiPredicate(BiPredicate<A, B> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A, B> WithBiPredicate<A, B> of(
            BiPredicate<A, B> initial
        ) {
            return new WithBiPredicate<>(initial);
        }

        @Override
        public Function<A, Predicate<B>> resolve() {
            return (A a) -> (B b) -> initial.test(a, b);
        }

        /* BiPredicate<A, B> -> BooleanFunction<C> */

        public <C> Function<A, Function<B, C>> fuseBooleanFunction(
            BooleanFunction<C> next
        ) {
            return (A a) -> (B b) -> next.apply(initial.test(a, b));
        }

        public <C> Function<A, Function<B, C>> fuse(BooleanFunction<C> next) {
            return fuseBooleanFunction(next);
        }

        public <C> WithBiFunction<A, B, C> fusingBooleanFunction(
            BooleanFunction<C> next
        ) {
            return WithBiFunction.of(fuseBooleanFunction(next));
        }

        public <C> WithBiFunction<A, B, C> fusing(BooleanFunction<C> next) {
            return fusingBooleanFunction(next);
        }
    }

    public static final class WithBooleanPredicate
        extends Combine<Boolean, BooleanPredicate> {

        private final transient BooleanPredicate initial;

        private WithBooleanPredicate(BooleanPredicate initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static WithBooleanPredicate of(BooleanPredicate initial) {
            return new WithBooleanPredicate(initial);
        }

        @Override
        public BooleanPredicate resolve() {
            return initial;
        }

        /* BooleanPredicate -> BooleanFunction<A> */

        public <A> BooleanFunction<A> fuseBooleanFunction(
            BooleanFunction<A> next
        ) {
            return (boolean b) -> next.apply(initial.test(b));
        }

        public <A> BooleanFunction<A> fuse(BooleanFunction<A> next) {
            return fuseBooleanFunction(next);
        }

        public <A> WithBooleanFunction<A> fusingBooleanFunction(
            BooleanFunction<A> next
        ) {
            return WithBooleanFunction.of(fuseBooleanFunction(next));
        }

        public <A> WithBooleanFunction<A> fusing(BooleanFunction<A> next) {
            return fusingBooleanFunction(next);
        }
    }

    public static final class WithDoublePredicate
        extends Combine<Double, DoublePredicate> {

        private final transient DoublePredicate initial;

        private WithDoublePredicate(DoublePredicate initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static WithDoublePredicate of(DoublePredicate initial) {
            return new WithDoublePredicate(initial);
        }

        @Override
        public DoublePredicate resolve() {
            return initial;
        }

        /* DoublePredicate -> BooleanFunction<A> */

        public <A> DoubleFunction<A> fuseBooleanFunction(
            BooleanFunction<A> next
        ) {
            return (double d) -> next.apply(initial.test(d));
        }

        public <A> DoubleFunction<A> fuse(BooleanFunction<A> next) {
            return fuseBooleanFunction(next);
        }

        public <A> WithDoubleFunction<A> fusingBooleanFunction(
            BooleanFunction<A> next
        ) {
            return WithDoubleFunction.of(fuseBooleanFunction(next));
        }

        public <A> WithDoubleFunction<A> fusing(BooleanFunction<A> next) {
            return fusingBooleanFunction(next);
        }
    }

    public static final class WithIntPredicate
        extends Combine<Integer, IntPredicate> {

        private final transient IntPredicate initial;

        private WithIntPredicate(IntPredicate initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static WithIntPredicate of(IntPredicate initial) {
            return new WithIntPredicate(initial);
        }

        @Override
        public IntPredicate resolve() {
            return initial;
        }

        /* IntPredicate -> BooleanFunction<A> */

        public <A> IntFunction<A> fuseBooleanFunction(BooleanFunction<A> next) {
            return (int i) -> next.apply(initial.test(i));
        }

        public <A> IntFunction<A> fuse(BooleanFunction<A> next) {
            return fuseBooleanFunction(next);
        }

        public <A> WithIntFunction<A> fusingBooleanFunction(
            BooleanFunction<A> next
        ) {
            return WithIntFunction.of(fuseBooleanFunction(next));
        }

        public <A> WithIntFunction<A> fusing(BooleanFunction<A> next) {
            return fusingBooleanFunction(next);
        }
    }

    public static final class WithLongPredicate
        extends Combine<Long, LongPredicate> {

        private final transient LongPredicate initial;

        private WithLongPredicate(LongPredicate initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static WithLongPredicate of(LongPredicate initial) {
            return new WithLongPredicate(initial);
        }

        @Override
        public LongPredicate resolve() {
            return initial;
        }

        /* LongPredicate -> BooleanFunction<A> */

        public <A> LongFunction<A> fuseBooleanFunction(
            BooleanFunction<A> next
        ) {
            return (long n) -> next.apply(initial.test(n));
        }

        public <A> LongFunction<A> fuse(BooleanFunction<A> next) {
            return fuseBooleanFunction(next);
        }

        public <A> WithLongFunction<A> fusingBooleanFunction(
            BooleanFunction<A> next
        ) {
            return WithLongFunction.of(fuseBooleanFunction(next));
        }

        public <A> WithLongFunction<A> fusing(BooleanFunction<A> next) {
            return fusingBooleanFunction(next);
        }
    }

    public static final class WithSupplier<A> extends Combine<A, Supplier<A>> {

        private final transient Supplier<A> initial;

        private WithSupplier(Supplier<A> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithSupplier<A> of(Supplier<A> initial) {
            return new WithSupplier<>(initial);
        }

        @Override
        public Supplier<A> resolve() {
            return initial;
        }

        /* Supplier<A> -> Function<A, B> */

        public <B> Supplier<B> fuseFunction(Function<A, B> next) {
            return () -> next.apply(resolve().get());
        }

        public <B> Supplier<B> fuse(Function<A, B> next) {
            return fuseFunction(next);
        }

        public <B> WithSupplier<B> fusingFunction(Function<A, B> next) {
            return WithSupplier.of(fuse(next));
        }

        public <B> WithSupplier<B> fusing(Function<A, B> next) {
            return fusingFunction(next);
        }

        /* Supplier<A> -> BiFunction<A, B, C> */

        public <B, C> Function<B, C> fuseBiFunction(BiFunction<A, B, C> next) {
            return (B b) -> next.apply(resolve().get(), b);
        }

        public <B, C> Function<B, C> fuse(BiFunction<A, B, C> next) {
            return fuseBiFunction(next);
        }

        public <B, C> WithFunction<B, C> fusingBiFunction(
            BiFunction<A, B, C> next
        ) {
            return WithFunction.of(fuseBiFunction(next));
        }

        public <B, C> WithFunction<B, C> fusing(BiFunction<A, B, C> next) {
            return fusingBiFunction(next);
        }

        /* Supplier<A> -> ToDoubleFunction<A> */

        public DoubleSupplier fuseToDoubleFunction(ToDoubleFunction<A> next) {
            return () -> next.applyAsDouble(initial.get());
        }

        public DoubleSupplier fuse(ToDoubleFunction<A> next) {
            return fuseToDoubleFunction(next);
        }

        public WithDoubleSupplier fusingToDoubleFunction(
            ToDoubleFunction<A> next
        ) {
            return WithDoubleSupplier.of(fuseToDoubleFunction(next));
        }

        public WithDoubleSupplier fusing(ToDoubleFunction<A> next) {
            return fusingToDoubleFunction(next);
        }

        /* Supplier<A> -> ToDoubleBiFunction<A, B> */

        public <B> ToDoubleFunction<B> fuseToDoubleBiFunction(
            ToDoubleBiFunction<A, B> next
        ) {
            return (B b) -> next.applyAsDouble(initial.get(), b);
        }

        public <B> ToDoubleFunction<B> fuse(ToDoubleBiFunction<A, B> next) {
            return fuseToDoubleBiFunction(next);
        }

        public <B> WithToDoubleFunction<B> fusingToDoubleBiFunction(
            ToDoubleBiFunction<A, B> next
        ) {
            return WithToDoubleFunction.of(fuseToDoubleBiFunction(next));
        }

        public <B> WithToDoubleFunction<B> fusing(
            ToDoubleBiFunction<A, B> next
        ) {
            return fusingToDoubleBiFunction(next);
        }

        /* Supplier<A> -> ToIntFunction<A> */

        public IntSupplier fuseToIntFunction(ToIntFunction<A> next) {
            return () -> next.applyAsInt(initial.get());
        }

        public IntSupplier fuse(ToIntFunction<A> next) {
            return fuseToIntFunction(next);
        }

        public WithIntSupplier fusingToIntFunction(ToIntFunction<A> next) {
            return WithIntSupplier.of(fuseToIntFunction(next));
        }

        public WithIntSupplier fusing(ToIntFunction<A> next) {
            return fusingToIntFunction(next);
        }

        /* Supplier<A> -> ToIntBiFunction<A, B> */

        public <B> ToIntFunction<B> fuseToIntBiFunction(
            ToIntBiFunction<A, B> next
        ) {
            return (B b) -> next.applyAsInt(initial.get(), b);
        }

        public <B> ToIntFunction<B> fuse(ToIntBiFunction<A, B> next) {
            return fuseToIntBiFunction(next);
        }

        public <B> WithToIntFunction<B> fusingToIntBiFunction(
            ToIntBiFunction<A, B> next
        ) {
            return WithToIntFunction.of(fuseToIntBiFunction(next));
        }

        public <B> WithToIntFunction<B> fusing(ToIntBiFunction<A, B> next) {
            return fusingToIntBiFunction(next);
        }

        /* Supplier<A> -> ToLongFunction<A> */

        public LongSupplier fuseToLongFunction(ToLongFunction<A> next) {
            return () -> next.applyAsLong(initial.get());
        }

        public LongSupplier fuse(ToLongFunction<A> next) {
            return fuseToLongFunction(next);
        }

        public WithLongSupplier fusingToLongFunction(ToLongFunction<A> next) {
            return WithLongSupplier.of(fuseToLongFunction(next));
        }

        public WithLongSupplier fusing(ToLongFunction<A> next) {
            return fusingToLongFunction(next);
        }

        /* Supplier<A> -> ToLongBiFunction<A, B> */

        public <B> ToLongFunction<B> fuseToLongBiFunction(
            ToLongBiFunction<A, B> next
        ) {
            return (B b) -> next.applyAsLong(initial.get(), b);
        }

        public <B> ToLongFunction<B> fuse(ToLongBiFunction<A, B> next) {
            return fuseToLongBiFunction(next);
        }

        public <B> WithToLongFunction<B> fusingToLongBiFunction(
            ToLongBiFunction<A, B> next
        ) {
            return WithToLongFunction.of(fuseToLongBiFunction(next));
        }

        public <B> WithToLongFunction<B> fusing(ToLongBiFunction<A, B> next) {
            return fusingToLongBiFunction(next);
        }

        /* Supplier<A> -> Predicate<A> */

        public BooleanSupplier fusePredicate(Predicate<A> next) {
            return () -> next.test(initial.get());
        }

        public BooleanSupplier fuse(Predicate<A> next) {
            return fusePredicate(next);
        }

        public WithBooleanSupplier fusingPredicate(Predicate<A> next) {
            return WithBooleanSupplier.of(fusePredicate(next));
        }

        public WithBooleanSupplier fusing(Predicate<A> next) {
            return fusingPredicate(next);
        }

        /* Supplier<A> -> BiPredicate<A, B> */

        public <B> Predicate<B> fuseBiPredicate(BiPredicate<A, B> next) {
            return (B b) -> next.test(initial.get(), b);
        }

        public <B> Predicate<B> fuse(BiPredicate<A, B> next) {
            return fuseBiPredicate(next);
        }

        public <B> WithPredicate<B> fusingBiPredicate(BiPredicate<A, B> next) {
            return WithPredicate.of(fuseBiPredicate(next));
        }

        public <B> WithPredicate<B> fusing(BiPredicate<A, B> next) {
            return fusingBiPredicate(next);
        }

        /* Supplier<A> -> Consumer<A> */

        public Operator fuseConsumer(Consumer<A> next) {
            return () -> next.accept(initial.get());
        }

        public Operator fuse(Consumer<A> next) {
            return fuseConsumer(next);
        }

        public WithOperator fusingConsumer(Consumer<A> next) {
            return WithOperator.of(fuseConsumer(next));
        }

        public WithOperator fusing(Consumer<A> next) {
            return fusingConsumer(next);
        }

        /* Supplier<A> -> BiConsumer<A, B> */

        public <B> Consumer<B> fuseBiConsumer(BiConsumer<A, B> next) {
            return (B b) -> next.accept(initial.get(), b);
        }

        public <B> Consumer<B> fuse(BiConsumer<A, B> next) {
            return fuseBiConsumer(next);
        }

        public <B> WithConsumer<B> fusingBiConsumer(BiConsumer<A, B> next) {
            return WithConsumer.of(fuseBiConsumer(next));
        }

        public <B> WithConsumer<B> fusing(BiConsumer<A, B> next) {
            return fusingBiConsumer(next);
        }

        /* Supplier<A> -> ObjDoubleConsumer<A, B> */

        public DoubleConsumer fuseObjDoubleConsumer(ObjDoubleConsumer<A> next) {
            return (double d) -> next.accept(initial.get(), d);
        }

        public DoubleConsumer fuse(ObjDoubleConsumer<A> next) {
            return fuseObjDoubleConsumer(next);
        }

        public WithDoubleConsumer fusingObjDoubleConsumer(
            ObjDoubleConsumer<A> next
        ) {
            return WithDoubleConsumer.of(fuseObjDoubleConsumer(next));
        }

        public WithDoubleConsumer fusing(ObjDoubleConsumer<A> next) {
            return fusingObjDoubleConsumer(next);
        }

        /* Supplier<A> -> ObjIntConsumer<A, B> */

        public IntConsumer fuseObjIntConsumer(ObjIntConsumer<A> next) {
            return (int i) -> next.accept(initial.get(), i);
        }

        public IntConsumer fuse(ObjIntConsumer<A> next) {
            return fuseObjIntConsumer(next);
        }

        public WithIntConsumer fusingObjIntConsumer(ObjIntConsumer<A> next) {
            return WithIntConsumer.of(fuseObjIntConsumer(next));
        }

        public WithIntConsumer fusing(ObjIntConsumer<A> next) {
            return fusingObjIntConsumer(next);
        }

        /* Supplier<A> -> ObjLongConsumer<A, B> */

        public LongConsumer fuseObjLongConsumer(ObjLongConsumer<A> next) {
            return (long n) -> next.accept(initial.get(), n);
        }

        public LongConsumer fuse(ObjLongConsumer<A> next) {
            return fuseObjLongConsumer(next);
        }

        public WithLongConsumer fusingObjLongConsumer(ObjLongConsumer<A> next) {
            return WithLongConsumer.of(fuseObjLongConsumer(next));
        }

        public WithLongConsumer fusing(ObjLongConsumer<A> next) {
            return fusingObjLongConsumer(next);
        }

        /* Supplier<A> -> UnaryOperator<A> */

        public Supplier<A> fuseUnaryOperator(UnaryOperator<A> next) {
            return () -> next.apply(initial.get());
        }

        public Supplier<A> fuse(UnaryOperator<A> next) {
            return fuseUnaryOperator(next);
        }

        public WithSupplier<A> fusingUnaryOperator(UnaryOperator<A> next) {
            return WithSupplier.of(fuseUnaryOperator(next));
        }

        public WithSupplier<A> fusing(UnaryOperator<A> next) {
            return fusingUnaryOperator(next);
        }

        /* Supplier<A> -> BinaryOperator<A, A> */

        public UnaryOperator<A> fuseBinaryOperator(BinaryOperator<A> next) {
            return (A a) -> next.apply(initial.get(), a);
        }

        public UnaryOperator<A> fuse(BinaryOperator<A> next) {
            return fuseBinaryOperator(next);
        }

        public WithUnaryOperator<A> fusingBinaryOperator(
            BinaryOperator<A> next
        ) {
            return WithUnaryOperator.of(fuseBinaryOperator(next));
        }

        public WithUnaryOperator<A> fusing(BinaryOperator<A> next) {
            return fusingBinaryOperator(next);
        }
    }

    public static final class WithBooleanSupplier
        extends Combine<Boolean, BooleanSupplier> {

        private final transient BooleanSupplier initial;

        private WithBooleanSupplier(BooleanSupplier initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithBooleanSupplier of(BooleanSupplier initial) {
            return new WithBooleanSupplier(initial);
        }

        @Override
        public BooleanSupplier resolve() {
            return initial;
        }

        /* BooleanSupplier -> BooleanFunction<A> */

        public <A> Supplier<A> fuseFunction(BooleanFunction<A> next) {
            return () -> next.apply(resolve().getAsBoolean());
        }

        public <A> Supplier<A> fuse(BooleanFunction<A> next) {
            return fuseFunction(next);
        }

        public <A> WithSupplier<A> fusingFunction(BooleanFunction<A> next) {
            return WithSupplier.of(fuse(next));
        }

        public <A> WithSupplier<A> fusing(BooleanFunction<A> next) {
            return fusingFunction(next);
        }
    }

    public static final class WithDoubleSupplier
        extends Combine<Double, DoubleSupplier> {

        private final transient DoubleSupplier initial;

        private WithDoubleSupplier(DoubleSupplier initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithDoubleSupplier of(DoubleSupplier initial) {
            return new WithDoubleSupplier(initial);
        }

        @Override
        public DoubleSupplier resolve() {
            return initial;
        }

        /* DoubleSupplier -> DoubleFunction<A> */

        public <A> Supplier<A> fuseFunction(DoubleFunction<A> next) {
            return () -> next.apply(resolve().getAsDouble());
        }

        public <A> Supplier<A> fuse(DoubleFunction<A> next) {
            return fuseFunction(next);
        }

        public <A> WithSupplier<A> fusingFunction(DoubleFunction<A> next) {
            return WithSupplier.of(fuse(next));
        }

        public <A> WithSupplier<A> fusing(DoubleFunction<A> next) {
            return fusingFunction(next);
        }
    }

    public static final class WithIntSupplier
        extends Combine<Integer, IntSupplier> {

        private final transient IntSupplier initial;

        private WithIntSupplier(IntSupplier initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithIntSupplier of(IntSupplier initial) {
            return new WithIntSupplier(initial);
        }

        @Override
        public IntSupplier resolve() {
            return initial;
        }

        /* IntSupplier -> IntFunction<A> */

        public <A> Supplier<A> fuseFunction(IntFunction<A> next) {
            return () -> next.apply(resolve().getAsInt());
        }

        public <A> Supplier<A> fuse(IntFunction<A> next) {
            return fuseFunction(next);
        }

        public <A> WithSupplier<A> fusingFunction(IntFunction<A> next) {
            return WithSupplier.of(fuse(next));
        }

        public <A> WithSupplier<A> fusing(IntFunction<A> next) {
            return fusingFunction(next);
        }
    }

    public static final class WithLongSupplier
        extends Combine<Long, LongSupplier> {

        private final transient LongSupplier initial;

        private WithLongSupplier(LongSupplier initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithLongSupplier of(LongSupplier initial) {
            return new WithLongSupplier(initial);
        }

        @Override
        public LongSupplier resolve() {
            return initial;
        }

        /* LongSupplier -> LongFunction<A> */

        public <A> Supplier<A> fuseFunction(LongFunction<A> next) {
            return () -> next.apply(resolve().getAsLong());
        }

        public <A> Supplier<A> fuse(LongFunction<A> next) {
            return fuseFunction(next);
        }

        public <A> WithSupplier<A> fusingFunction(LongFunction<A> next) {
            return WithSupplier.of(fuse(next));
        }

        public <A> WithSupplier<A> fusing(LongFunction<A> next) {
            return fusingFunction(next);
        }
    }

    public static final class WithOperator extends Combine<Void, Operator> {

        private final transient Operator initial;

        private WithOperator(Operator initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static WithOperator of(Operator initial) {
            return new WithOperator(initial);
        }

        @Override
        public Operator resolve() {
            return initial;
        }
    }

    public static final class WithUnaryOperator<A>
        extends Combine<A, UnaryOperator<A>> {

        private final transient UnaryOperator<A> initial;

        private WithUnaryOperator(UnaryOperator<A> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithUnaryOperator<A> of(UnaryOperator<A> initial) {
            return new WithUnaryOperator<>(initial);
        }

        @Override
        public UnaryOperator<A> resolve() {
            return initial;
        }

        /* UnaryOperator -> Function<A, B> */

        public <B> Function<A, B> fuseFunction(Function<A, B> next) {
            return (A a) -> next.apply(resolve().apply(a));
        }

        public <B> Function<A, B> fuse(Function<A, B> next) {
            return fuseFunction(next);
        }

        public <B> WithFunction<A, B> fusingFunction(Function<A, B> next) {
            return WithFunction.of(fuse(next));
        }

        public <B> WithFunction<A, B> fusing(Function<A, B> next) {
            return fusingFunction(next);
        }

        /* UnaryOperator -> BiFunction<A, B, C> */

        public <B, C> Function<A, Function<B, C>> fuseBiFunction(
            BiFunction<A, B, C> next
        ) {
            return (A a) -> (B c) -> next.apply(resolve().apply(a), c);
        }

        public <B, C> Function<A, Function<B, C>> fuse(
            BiFunction<A, B, C> next
        ) {
            return fuseBiFunction(next);
        }

        public <B, C> WithBiFunction<A, B, C> fusingBiFunction(
            BiFunction<A, B, C> next
        ) {
            return WithBiFunction.of(fuse(next));
        }

        public <B, C> WithBiFunction<A, B, C> fusing(BiFunction<A, B, C> next) {
            return fusingBiFunction(next);
        }
    }

    public static final class WithBinaryOperator<A>
        extends Combine<A, Function<A, UnaryOperator<A>>> {

        private final transient BinaryOperator<A> initial;

        private WithBinaryOperator(BinaryOperator<A> initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithBinaryOperator<A> of(BinaryOperator<A> initial) {
            return new WithBinaryOperator<>(initial);
        }

        @Override
        public Function<A, UnaryOperator<A>> resolve() {
            return (A a1) -> (A a2) -> initial.apply(a1, a2);
        }

        /* BinaryOperator -> Function<A, B> */

        public <B> Function<A, Function<A, B>> fuseFunction(
            Function<A, B> next
        ) {
            return (A a) -> (A b) -> next.apply(initial.apply(a, b));
        }

        public <B> Function<A, Function<A, B>> fuse(Function<A, B> next) {
            return fuseFunction(next);
        }

        public <B> WithBiFunction<A, A, B> fusingFunction(Function<A, B> next) {
            return WithBiFunction.of(fuse(next));
        }

        public <B> WithBiFunction<A, A, B> fusing(Function<A, B> next) {
            return fusingFunction(next);
        }
    }

    public static final class WithDoubleUnaryOperator
        extends Combine<Double, DoubleUnaryOperator> {

        private final transient DoubleUnaryOperator initial;

        private WithDoubleUnaryOperator(DoubleUnaryOperator initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithDoubleUnaryOperator of(
            DoubleUnaryOperator initial
        ) {
            return new WithDoubleUnaryOperator(initial);
        }

        @Override
        public DoubleUnaryOperator resolve() {
            return initial;
        }

        /* DoubleUnaryOperator -> DoubleFunction<A> */

        public <A> DoubleFunction<A> fuseDoubleFunction(
            DoubleFunction<A> next
        ) {
            return (double d) -> next.apply(resolve().applyAsDouble(d));
        }

        public <A> DoubleFunction<A> fuse(DoubleFunction<A> next) {
            return fuseDoubleFunction(next);
        }

        public <A> WithDoubleFunction<A> fusingDoubleFunction(
            DoubleFunction<A> next
        ) {
            return WithDoubleFunction.of(fuseDoubleFunction(next));
        }

        public <A> WithDoubleFunction<A> fusing(DoubleFunction<A> next) {
            return fusingDoubleFunction(next);
        }
    }

    public static final class WithDoubleBinaryOperator
        extends Combine<Double, DoubleFunction<DoubleUnaryOperator>> {

        private final transient DoubleBinaryOperator initial;

        private WithDoubleBinaryOperator(DoubleBinaryOperator initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithDoubleBinaryOperator of(
            DoubleBinaryOperator initial
        ) {
            return new WithDoubleBinaryOperator(initial);
        }

        @Override
        public DoubleFunction<DoubleUnaryOperator> resolve() {
            return (double d1) -> (double d2) -> initial.applyAsDouble(d1, d2);
        }

        /* DoubleBinaryOperator -> DoubleFunction<A> */

        public <A> DoubleFunction<DoubleFunction<A>> fuseDoubleFunction(
            DoubleFunction<A> next
        ) {
            return (double d) ->
                (double d2) -> next.apply(initial.applyAsDouble(d, d2));
        }

        public <A> DoubleFunction<DoubleFunction<A>> fuse(
            DoubleFunction<A> next
        ) {
            return fuseDoubleFunction(next);
        }
        // TODO: "fusing" -- Need better currying support or DoubleBiFunction functional interface?
    }

    public static final class WithIntUnaryOperator
        extends Combine<Integer, IntUnaryOperator> {

        private final transient IntUnaryOperator initial;

        private WithIntUnaryOperator(IntUnaryOperator initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithIntUnaryOperator of(IntUnaryOperator initial) {
            return new WithIntUnaryOperator(initial);
        }

        @Override
        public IntUnaryOperator resolve() {
            return initial;
        }

        /* IntUnaryOperator -> IntFunction<A> */

        public <A> IntFunction<A> fuseIntFunction(IntFunction<A> next) {
            return (int i) -> next.apply(resolve().applyAsInt(i));
        }

        public <A> IntFunction<A> fuse(IntFunction<A> next) {
            return fuseIntFunction(next);
        }

        public <A> WithIntFunction<A> fusingIntFunction(IntFunction<A> next) {
            return WithIntFunction.of(fuseIntFunction(next));
        }

        public <A> WithIntFunction<A> fusing(IntFunction<A> next) {
            return fusingIntFunction(next);
        }
    }

    public static final class WithIntBinaryOperator
        extends Combine<Integer, IntFunction<IntUnaryOperator>> {

        private final transient IntBinaryOperator initial;

        private WithIntBinaryOperator(IntBinaryOperator initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithIntBinaryOperator of(IntBinaryOperator initial) {
            return new WithIntBinaryOperator(initial);
        }

        @Override
        public IntFunction<IntUnaryOperator> resolve() {
            return (int i1) -> (int i2) -> initial.applyAsInt(i1, i2);
        }

        /* IntBinaryOperator -> IntFunction<A> */

        public <A> IntFunction<IntFunction<A>> fuseIntFunction(
            IntFunction<A> next
        ) {
            return (int i) -> (int i2) -> next.apply(initial.applyAsInt(i, i2));
        }

        public <A> IntFunction<IntFunction<A>> fuse(IntFunction<A> next) {
            return fuseIntFunction(next);
        }
        // TODO: "fusing" -- Need better currying support or IntBiFunction functional interface?
    }

    public static final class WithLongUnaryOperator
        extends Combine<Long, LongUnaryOperator> {

        private final transient LongUnaryOperator initial;

        private WithLongUnaryOperator(LongUnaryOperator initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithLongUnaryOperator of(LongUnaryOperator initial) {
            return new WithLongUnaryOperator(initial);
        }

        @Override
        public LongUnaryOperator resolve() {
            return initial;
        }

        /* LongUnaryOperator -> LongFunction<A> */

        public <A> LongFunction<A> fuseLongFunction(LongFunction<A> next) {
            return (long n) -> next.apply(resolve().applyAsLong(n));
        }

        public <A> LongFunction<A> fuse(LongFunction<A> next) {
            return fuseLongFunction(next);
        }

        public <A> WithLongFunction<A> fusingLongFunction(
            LongFunction<A> next
        ) {
            return WithLongFunction.of(fuseLongFunction(next));
        }

        public <A> WithLongFunction<A> fusing(LongFunction<A> next) {
            return fusingLongFunction(next);
        }
    }

    public static final class WithLongBinaryOperator
        extends Combine<Long, LongFunction<LongUnaryOperator>> {

        private final transient LongBinaryOperator initial;

        private WithLongBinaryOperator(LongBinaryOperator initial) {
            this.initial = Objects.requireNonNull(initial);
        }

        public static <A> WithLongBinaryOperator of(
            LongBinaryOperator initial
        ) {
            return new WithLongBinaryOperator(initial);
        }

        @Override
        public LongFunction<LongUnaryOperator> resolve() {
            return (long n1) -> (long n2) -> initial.applyAsLong(n1, n2);
        }

        /* LongBinaryOperator -> LongFunction<A> */

        public <A> LongFunction<LongFunction<A>> fuseLongFunction(
            LongFunction<A> next
        ) {
            return (long n) ->
                (long n2) -> next.apply(initial.applyAsLong(n, n2));
        }

        public <A> LongFunction<LongFunction<A>> fuse(LongFunction<A> next) {
            return fuseLongFunction(next);
        }
        // TODO: "fusing" -- Need better currying support or LongBiFunction functional interface?
    }
}

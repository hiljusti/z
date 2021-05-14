package so.dang.cool.z.continuation;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.Function;
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

public abstract class Continue<A, Fn, Prev> {
    private Continue() {}

    public abstract Fn resolve();

    /* Function -> ... [TODO: Incomplete] */

    public static final class WithFunction<A, B> extends Continue<B, Function<A, B>, Void> {
        private final Function<A, B> initial;
        private WithFunction(Function<A, B> initial) {
            this.initial = initial;
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
            return Z.fuse(initial, next);
        }

        public <C> Function<A, C> fuse(Function<B, C> next) {
            return Z.fuse(initial, next);
        }

        public <C> WithFunction<A, C> fusingFunction(Function<B, C> next) {
            return WithFunction.of(Z.fuse(initial, next));
        }

        public <C> WithFunction<A, C> fusing(Function<B, C> next) {
            return WithFunction.of(Z.fuse(initial, next));
        }

        /* Function<A, B> -> BiFunction<B, C, D> */

        public <C, D> Function<A, Function<C, D>> fuseBiFunction(BiFunction<B, C, D> next) {
            return Z.fuse(initial, next);
        }

        public <C, D> Function<A, Function<C, D>> fuse(BiFunction<B, C, D> next) {
            return Z.fuse(initial, next);
        }

        public <C, D> WithBiFunction<A, C, D> fusingBiFunction(BiFunction<B, C, D> next) {
            // TODO: Need a currying continuation?
            return WithBiFunction.of(Z.assimilate2(Z.fuse(initial, next)));
        }

        public <C, D> WithBiFunction<A, C, D> fusing(BiFunction<B, C, D> next) {
            // TODO: Need a currying continuation?
            return WithBiFunction.of(Z.assimilate2(Z.fuse(initial, next)));
        }

        /* Function<A, B> -> DoubleFunction<B> */

        public ToDoubleFunction<A> fuseToDoubleFunction(ToDoubleFunction<B> next) {
            return Z.fuse(initial, next);
        }

        public ToDoubleFunction<A> fuse(ToDoubleFunction<B> next) {
            return Z.fuse(initial, next);
        }

        public WithToDoubleFunction<A> fusingToDoubleFunction(ToDoubleFunction<B> next) {
            return WithToDoubleFunction.of(Z.fuse(initial, next));
        }

        public WithToDoubleFunction<A> fusing(ToDoubleFunction<B> next) {
            return WithToDoubleFunction.of(Z.fuse(initial, next));
        }

        /* Function<A, B> -> ToDoubleBiFunction<B> [TODO] */

        /* Function<A, B> -> ToIntFunction<B> */

        public ToIntFunction<A> fuseToIntFunction(ToIntFunction<B> next) {
            return Z.fuse(initial, next);
        }

        public ToIntFunction<A> fuse(ToIntFunction<B> next) {
            return Z.fuse(initial, next);
        }

        public WithToIntFunction<A> fusingToIntFunction(ToIntFunction<B> next) {
            return WithToIntFunction.of(Z.fuse(initial, next));
        }

        public WithToIntFunction<A> fusing(ToIntFunction<B> next) {
            return WithToIntFunction.of(Z.fuse(initial, next));
        }

        /* Function<A, B> -> ToIntBiFunction<B> [TODO] */

        /* Function<A, B> -> ToLongFunction<B> */

        public ToLongFunction<A> fuseToLongFunction(ToLongFunction<B> next) {
            return Z.fuse(initial, next);
        }

        public ToLongFunction<A> fuse(ToLongFunction<B> next) {
            return Z.fuse(initial, next);
        }

        public WithToLongFunction<A> fusingToLongFunction(ToLongFunction<B> next) {
            return WithToLongFunction.of(Z.fuse(initial, next));
        }

        public WithToLongFunction<A> fusing(ToLongFunction<B> next) {
            return WithToLongFunction.of(Z.fuse(initial, next));
        }

        /* Function<A, B> -> ToLongBiFunction<B> [TODO] */

        /* Function<A, B> -> Predicate<B> [TODO: Incomplete] */

        public Predicate<A> fusePredicate(Predicate<B> next) {
            return Z.fuse(resolve(), next);
        }

        public Predicate<A> fuse(Predicate<B> next) {
            return fusePredicate(next);
        }

        public WithPredicate<A> fusingPredicate(Predicate<B> next) {
            return WithPredicate.of(Z.fuse(initial, next));
        }

        public WithPredicate<A> fusing(Predicate<B> next) {
            return WithPredicate.of(Z.fuse(initial, next));
        }

        /* Function<A, B> -> BiPredicate<B, C> [TODO] */

        /* Function<A, B> -> Consumer<B> */

        public Consumer<A> fuseConsumer(Consumer<B> next) {
            return Z.fuse(initial, next);
        }

        public Consumer<A> fuse(Consumer<B> next) {
            return Z.fuse(initial, next);
        }

        public WithConsumer<A> fusingConsumer(Consumer<B> next) {
            return WithConsumer.of(Z.fuse(initial, next));
        }

        public WithConsumer<A> fusing(Consumer<B> next) {
            return WithConsumer.of(Z.fuse(initial, next));
        }

        /* Function<A, B> -> BiConsumer<B, C> [TODO] */

        /* Function<A, B> -> ObjDoubleConsumer<B, C> [TODO] */

        /* Function<A, B> -> ObjIntConsumer<B, C> [TODO] */

        /* Function<A, B> -> ObjLongConsumer<B, C> [TODO] */

        /* Function<A, B> -> UnaryOperator<B> */

        public Function<A, B> fuseUnaryOperator(UnaryOperator<B> next) {
            return Z.fuse(initial, next);
        }

        public Function<A, B> fuse(UnaryOperator<B> next) {
            return Z.fuse(initial, next);
        }

        public WithFunction<A, B> fusingUnaryOperator(UnaryOperator<B> next) {
            return WithFunction.of(Z.fuse(initial, next));
        }

        public WithFunction<A, B> fusing(UnaryOperator<B> next) {
            return WithFunction.of(Z.fuse(initial, next));
        }

        /* Function<A, B> -> BinaryOperator<B> */
        
        public Function<A, UnaryOperator<B>> fuseBinaryOperator(BinaryOperator<B> next) {
            return Z.fuse(initial, next);
        }
        
        public Function<A, UnaryOperator<B>> fuse(BinaryOperator<B> next) {
            return Z.fuse(initial, next);
        }
        
        public WithBiFunction<A, B, B> fusingBinaryOperator(BinaryOperator<B> next) {
            // TODO: simplify with currying
            return WithBiFunction.of((A a, B b) -> next.apply(initial.apply(a), b));
        }
        
        public WithBiFunction<A, B, B> fusing(BinaryOperator<B> next) {
            // TODO: simplify with currying
            return WithBiFunction.of((A a, B b) -> next.apply(initial.apply(a), b));
        }

        /* Function<A, B> -> [Multi]Function<B...> [TODO...?] */
    }

    /*
        BiFunction<A, B, C> -> BiFunction<C, D, E>
        = Function<A, Function<B, Function<D, E>>>

        Function<A, B> -> BiFunction<B, C, D>
        = Function<A, Function<C, D>>
    */

    public static final class WithBiFunction<A, B, C> extends Continue<C, Function<A, Function<B, C>>, Void> {
        private final BiFunction<A, B, C> initial;
        private WithBiFunction(BiFunction<A, B, C> initial) {
            this.initial = initial;
        }

        public static <A, B, C> WithBiFunction<A, B, C> of(BiFunction<A, B, C> initial) {
            return new WithBiFunction<>(initial);
        }

        public static <A, B, C> WithBiFunction<A, B, C> of(Function<A, Function<B, C>> initial) {
            return new WithBiFunction<>((A a, B b) -> initial.apply(a).apply(b));
        }

        @Override
        public Function<A, Function<B, C>> resolve() {
            return Z.split(initial);
        }

        /* BiFunction<A, B, C> -> Function<C, D> */

        public <D> Function<A, Function<B, D>> fuseFn(Function<C, D> next) {
            return Z.fuse(initial, next);
        }

        public <D> Function<A, Function<B, D>> fuse(Function<C, D> next) {
            return fuseFn(next);
        }

        public <D> WithBiFunction<A, B, D> fusing(Function<C, D> next) {
            return WithBiFunction.of(fuseFn(next));
        }
    }

    public static final class WithToDoubleFunction<A> extends Continue<Double, ToDoubleFunction<A>, Void> {
        private final ToDoubleFunction<A> initial;
        private WithToDoubleFunction(ToDoubleFunction<A> initial) {
            this.initial = initial;
        }

        public static <A> WithToDoubleFunction<A> of(ToDoubleFunction<A> initial) {
            return new WithToDoubleFunction<>(initial);
        }

        @Override
        public ToDoubleFunction<A> resolve() {
            return initial;
        }
    }

    public static final class WithToDoubleBiFunction<A, B> extends Continue<Double, ToDoubleBiFunction<A, B>, Void> {
        private final ToDoubleBiFunction<A, B> initial;
        private WithToDoubleBiFunction(ToDoubleBiFunction<A, B> initial) {
            this.initial = initial;
        }

        public static <A, B> WithToDoubleBiFunction<A, B> of(ToDoubleBiFunction<A, B> initial) {
            return new WithToDoubleBiFunction<>(initial);
        }

        @Override
        public ToDoubleBiFunction<A, B> resolve() {
            return initial;
        }
    }

    public static final class WithToIntFunction<A> extends Continue<Integer, ToIntFunction<A>, Void> {
        private final ToIntFunction<A> initial;
        private WithToIntFunction(ToIntFunction<A> initial) {
            this.initial = initial;
        }

        public static <A> WithToIntFunction<A> of(ToIntFunction<A> initial) {
            return new WithToIntFunction<>(initial);
        }

        @Override
        public ToIntFunction<A> resolve() {
            return initial;
        }
    }

    public static final class WithToIntBiFunction<A, B> extends Continue<Integer, ToIntBiFunction<A, B>, Void> {
        private final ToIntBiFunction<A, B> initial;
        private WithToIntBiFunction(ToIntBiFunction<A, B> initial) {
            this.initial = initial;
        }

        public static <A, B> WithToIntBiFunction<A, B> of(ToIntBiFunction<A, B> initial) {
            return new WithToIntBiFunction<>(initial);
        }

        @Override
        public ToIntBiFunction<A, B> resolve() {
            return initial;
        }
    }

    public static final class WithToLongFunction<A> extends Continue<Long, ToLongFunction<A>, Void> {
        private final ToLongFunction<A> initial;
        private WithToLongFunction(ToLongFunction<A> initial) {
            this.initial = initial;
        }

        public static <A> WithToLongFunction<A> of(ToLongFunction<A> initial) {
            return new WithToLongFunction<>(initial);
        }

        @Override
        public ToLongFunction<A> resolve() {
            return initial;
        }
    }

    public static final class WithToLongBiFunction<A, B> extends Continue<Long, ToLongBiFunction<A, B>, Void> {
        private final ToLongBiFunction<A, B> initial;
        private WithToLongBiFunction(ToLongBiFunction<A, B> initial) {
            this.initial = initial;
        }

        public static <A, B> WithToLongBiFunction<A, B> of(ToLongBiFunction<A, B> initial) {
            return new WithToLongBiFunction<>(initial);
        }

        @Override
        public ToLongBiFunction<A, B> resolve() {
            return initial;
        }
    }

    public static final class WithConsumer<A> extends Continue<Void, Consumer<A>, Void> {
        private final Consumer<A> initial;
        private WithConsumer(Consumer<A> initial) {
            this.initial = initial;
        }

        public static <A> WithConsumer<A> of(Consumer<A> initial) {
            return new WithConsumer<>(initial);
        }

        @Override
        public Consumer<A> resolve() {
            return initial;
        }
    }

    public static final class WithBiConsumer<A, B> extends Continue<Void, BiConsumer<A, B>, Void> {
        private final BiConsumer<A, B> initial;
        private WithBiConsumer(BiConsumer<A, B> initial) {
            this.initial = initial;
        }

        public static <A, B> WithBiConsumer<A, B> of(BiConsumer<A, B> initial) {
            return new WithBiConsumer<>(initial);
        }

        @Override
        public BiConsumer<A, B> resolve() {
            return initial;
        }
    }

    /* Predicate -> ... [TODO: Incomplete] */

    public static final class WithPredicate<A> extends Continue<Boolean, Predicate<A>, Void> {
        private final Predicate<A> initial;
        private WithPredicate(Predicate<A> initial) {
            this.initial = initial;
        }

        public static <A> WithPredicate<A> of(Predicate<A> initial) {
            return new WithPredicate<>(initial);
        }

        @Override
        public Predicate<A> resolve() {
            return initial;
        }

        /* Predicate<A> -> Function<Boolean, B> */

        public <B> Function<A, B> fuseFn(Function<Boolean, B> next) {
            return Z.fuse(initial, next);
        }

        public <B> Function<A, B> fuse(Function<Boolean, B> next) {
            return fuseFn(next);
        }

        public <B> WithFunction<A, B> fusingFn(Function<Boolean, B> next) {
            return WithFunction.of(fuseFn(next));
        }

        public <B> WithFunction<A, B> fusing(Function<Boolean, B> next) {
            return fusingFn(next);
        }
    }

    public static final class WithBiPredicate<A, B> extends Continue<Boolean, BiPredicate<A, B>, Void> {
        private final BiPredicate<A, B> initial;
        private WithBiPredicate(BiPredicate<A, B> initial) {
            this.initial = initial;
        }

        public static <A, B> WithBiPredicate<A, B> of(BiPredicate<A, B> initial) {
            return new WithBiPredicate<>(initial);
        }

        @Override
        public BiPredicate<A, B> resolve() {
            return initial;
        }
    }

    /* Consumer -> ... [TODO: Incomplete]  */

    /* Supplier -> ... [TODO: Incomplete]  */

    public static final class WithSupplier<A> extends Continue<A, Supplier<A>, Void> {
        private final Supplier<A> initial;
        private WithSupplier(Supplier<A> initial) {
            this.initial = initial;
        }

        public static <A> WithSupplier<A> of(Supplier<A> initial) {
            return new WithSupplier<>(initial);
        }

        @Override
        public Supplier<A> resolve() {
            return initial;
        }

        /* Supplier<A> -> Function<A, B> */

        public <B> Supplier<B> fuseFn(Function<A, B> next) {
            return Z.fuse(resolve(), next);
        }

        public <B> Supplier<B> fuse(Function<A, B> next) {
            return fuseFn(next);
        }

        public <B> WithSupplier<B> fusingFn(Function<A, B> next) {
            return WithSupplier.of(fuse(next));
        }

        public <B> WithSupplier<B> fusing(Function<A, B> next) {
            return fusingFn(next);
        }

        /* Supplier<A> -> BiFunction<A, B, C> */

        public <B, C> Function<B, C> fuseBifn(BiFunction<A, B, C> next) {
            return Z.fuse(resolve(), next);
        }

        public <B, C> Function<B, C> fuse(BiFunction<A, B, C> next) {
            return fuseBifn(next);
        }

        public <B, C> WithFunction<B, C> fusingBifn(BiFunction<A, B, C> next) {
            return WithFunction.of(fuseBifn(next));
        }

        public <B, C> WithFunction<B, C> fusing(BiFunction<A, B, C> next) {
            return fusingBifn(next);
        }
    }
}

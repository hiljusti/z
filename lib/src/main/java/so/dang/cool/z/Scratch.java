package so.dang.cool.z;

import java.util.List;
import java.util.function.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class Scratch {
    private static final String URL_RE = "https?://localhost(:\\d+)?(/\\S*)?";

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile(URL_RE);

        Predicate<CharSequence> validator1 = s -> pattern.matcher(s).matches();

        Predicate<CharSequence> validator2 = Z.fuse(pattern::matcher, Matcher::matches);

        Predicate<CharSequence> validator3 = Z.with(URL_RE)
            .fusingFn(Pattern::compile)
            .fusing(Pattern::matcher)
            .fuse(Matcher::matches);

        List<String> cases = List.of("invalid", "http://invalid/localhost", "http://localhost", "https://localhost:443/");

        Stream.of(validator1, validator2, validator3)
            .flatMap(v -> cases.stream().filter(v))
            .forEach(System.out::println);
    }
}

class Z {
    public static <A> SupplierContinuation<A> with(A initial) {
        return SupplierContinuation.of(initial);
    }
    
    public static <A,B,C> Function<A, C> fuse(Function<A, B> initial, Function<B, C> next) {
        return next.compose(initial);
    }

    public static <A, B> Predicate<A> fuse(Function<A, B> initial, Predicate<B> next) {
        return (A a) -> next.test(initial.apply(a));
    }

    public static <A, B> Consumer<A> fuse(Function<A, B> initial, Consumer<B> next) {
        return (A a) -> next.accept(initial.apply(a));
    }

    public static <A, B> Supplier<B> fuse(Supplier<A> initial, Function<A, B> next) {
        return () -> next.apply(initial.get());
    }

    public static <A> BooleanSupplier fuse(Supplier<A> initial, Predicate<A> next) {
        return () -> next.test(initial.get());
    }

    public static <A> Operator fuse(Supplier<A> initial, Consumer<A> next) {
        return () -> next.accept(initial.get());
    }

    public static <A, B, C> FunctionContinuation<A, C> fusing(Function<A, B> initial, Function<B, C> next) {
        return FunctionContinuation.of(initial, next);
    }

    public static <A, B> SupplierContinuation<B> fusing(Supplier<A> initial, Function<A, B> next) {
        return SupplierContinuation.of(initial, next);
    }
}

interface Operator {
    void run();
}

class SupplierContinuation <A> {
    private final Supplier<A> supplier;
    
    private SupplierContinuation(Supplier<A> supplier) {
        this.supplier = supplier;
    }
    
    public static <A> SupplierContinuation<A> of(A initial) {
        return new SupplierContinuation<>(() -> initial);
    }

    public static <A> SupplierContinuation<A> of(Supplier<A> initial) {
        return new SupplierContinuation<>(initial);
    }

    public static <A, B> SupplierContinuation<B> of(Supplier<A> initial, Function<A, B> next) {
        return new SupplierContinuation<>(Z.fuse(initial, next));
    }

    public <B> Supplier<B> fuse(Function<A, B> next) {
        return Z.fuse(supplier, next);
    }

    public <B> SupplierContinuation<B> fusing(Function<A, B> next) {
        return fusingFn(next);
    }

    public <B> SupplierContinuation<B> fusingFn(Function<A, B> next) {
        return Z.fusing(supplier, next);
    }

    public <B, C> FunctionContinuation<B, C> fusing(BiFunction<A, B, C> next) {
        return fusingBiFn(next);
    }

    public <B, C> FunctionContinuation<B, C> fusingBiFn(BiFunction<A, B, C> next) {
        return FunctionContinuation.of((B b) -> next.apply(supplier.get(), b));
    }
}

class FunctionContinuation <A, B> {
    private final Function<A, B> function;

    private FunctionContinuation(Function <A, B> function) {
        this.function = function;
    }

    public static <A, B> FunctionContinuation<A, B> of(Function<A, B> function) {
        return new FunctionContinuation<>(function);
    }

    public static <A, B, C> FunctionContinuation<A, C> of(Function<A, B> initial, Function<B, C> next) {
        return new FunctionContinuation<>(Z.fuse(initial, next));
    }

    public <C> Function<A, C> fuse(Function<B, C> next) {
        return Z.fuse(function, next);
    }

    public Predicate<A> fuse(Predicate<B> next) {
        return Z.fuse(function, next);
    }

    public <C> FunctionContinuation<A, C> fusing(Function<B, C> next) {
        return Z.fusing(function, next);
    }
}

package so.dang.cool.z;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

public class UsageExamples {
    @Test
    public void simple_regex_example() {
        final String urlRegex = "https?://localhost(:\\d+)?(/\\S*)?";
        Pattern pattern = Pattern.compile(urlRegex);

        Predicate<CharSequence> predicate = Z.fuse(pattern::matcher, Matcher::matches);

        assertFalse(predicate.test("invalid"));
        assertTrue(predicate.test("https://localhost:443"));
    }

    @Test
    public void complex_regex_example() {
        Predicate<CharSequence> predicate = Z.with("https?://localhost(:\\d+)?(/\\S*)?")
            .fusing(Pattern::compile)
            .fusing(Pattern::matcher)
            .fuse(Matcher::matches);

        assertFalse(predicate.test("invalid"));
        assertTrue(predicate.test("https://localhost:443"));
    }

    @Test
    public void uhawwwwwww_example() {
        Function<String, String> addW = (String s) -> s.concat("ｗ");

        var addSevenWs = Z.with(addW)
            .fusing(addW)
            .fusing(addW)
            .fusing(addW)
            .fusing(addW)
            .fusing(addW)
            .fuse(addW);

        assertEquals("うはｗｗｗｗｗｗｗ", addSevenWs.apply("うは"));
    }

    @Test
    public void string_builder_example() {
        // TODO: Figure out how to make this work

        // BiFunction<String, String, String> append = (String base, String suffix) -> base.concat(suffix);

        // var fourPartStringBuilder = Z.with(String.class)
        //     .fusing(append)
        //     .fusing(append)
        //     .fusing(append)
        //     .fuse(append);

        // assertEquals("hello world!", fourPartStringBuilder.accept("hello").apply(" ").apply("world").apply("!"));
    }
}

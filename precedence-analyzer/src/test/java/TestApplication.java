import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

public class TestApplication {

    @Test
    public void testExpression1() {
        List<String> expression = Lists.newArrayList(Splitter.on(" ").split("! a & b"));
        String result = PrecedenceParser.parse(expression, DataConstructor.getTokens(), RelationCreator.getRelations());
        System.out.println(result);
        assert(result.equals("a!b&"));
    }

    @Test(expected = ParserException.class)
    public void testExpression2() {
        List<String> expression = Lists.newArrayList(Splitter.on(" ").split("( ( a & b ) ) )"));
        PrecedenceParser.parse(expression, DataConstructor.getTokens(), RelationCreator.getRelations());
    }

    @Test
    public void testExpression3() {
        List<String> expression = Lists.newArrayList(Splitter.on(" ").split("a | ( b & true ) | c"));
        String result = PrecedenceParser.parse(expression, DataConstructor.getTokens(), RelationCreator.getRelations());
        System.out.println(result);
        assert(result.equals("abtrue&|c|"));
    }

    @Test
    public void testExpression4() {
        List<String> expression = Lists.newArrayList(Splitter.on(" ").split("! b"));
        String result = PrecedenceParser.parse(expression, DataConstructor.getTokens(), RelationCreator.getRelations());
        System.out.println(result);
        assert(result.equals("b!"));
    }

    @Test
    public void testExpression5() {
        List<String> expression = Lists.newArrayList(Splitter.on(" ").split("( ! ( c | d & a ) ~ b )"));
        String result = PrecedenceParser.parse(expression, DataConstructor.getTokens(), RelationCreator.getRelations());
        System.out.println(result);
        assert(result.equals("cd|a&!b~"));
    }
}

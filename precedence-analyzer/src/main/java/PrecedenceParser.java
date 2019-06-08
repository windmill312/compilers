import com.sun.javafx.binding.StringFormatter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class PrecedenceParser {

    public static String parse(List<String> tokens, Set<String> allTokens, Map<String, Map<String, String>> relations) {
        StringBuilder result = new StringBuilder();

        tokens.add("$");
        Iterator<String> iterator = tokens.listIterator();
        String nextToken = iterator.next();
        int nextTokenNumber = 0;

        String stackHead = "$";
        Stack<String> stack = new Stack<>();
        while (true) {
            if (allTokens.contains(nextToken)) {
                if (stackHead.equals(DataConstructor.marker) && nextToken.equals(DataConstructor.marker)) {
                    break;
                }
                String relation = relations.get(stackHead).get(nextToken);
                if (relation.equals("<") || relation.equals("=")) {
                    stack.push(stackHead);
                    stackHead = nextToken;
                    nextTokenNumber++;
                    nextToken = iterator.next();
                    continue;
                }
                if (relation.equals(">")) {
                    while (true) {
                        if (!stackHead.equals("(") && !stackHead.equals(")")) {
                            result.append(stackHead);
                        }
                        String oldStackHead = stackHead;
                        stackHead = stack.pop();
                        if (relations.get(stackHead).get(oldStackHead).equals("<")) {
                            break;
                        }
                    }
                    continue;
                }
            }

            throw new ParserException(StringFormatter.format("Error in %s token!", nextTokenNumber).getValue());
        }

        return result.toString();
    }
}
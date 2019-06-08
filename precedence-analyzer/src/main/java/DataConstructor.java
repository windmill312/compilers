import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class DataConstructor {

    public static final String marker = "$";
    public static final String prefix = "!";

    public static Map<String, Integer> getPrecedences() {
        Map<String, Integer> precedence = new HashMap<>();
        precedence.put("!", 2);
        precedence.put("&", 1);
        precedence.put("|", 1);
        precedence.put("~", 1);

        return precedence;
    }

    public static Set<String> getConstants() {
        Set<String> constant = new HashSet<>();
        constant.add("true");
        constant.add("false");
        char[] letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        for (char letter : letters) {
            constant.add(Character.toString(letter));
        }

        return constant;
    }

    public static Set<String> getTokens() {
        Set<String> tokens = new HashSet<>();
        tokens.addAll(getPrecedences().keySet());
        tokens.addAll(getConstants());
        tokens.add(prefix);
        tokens.add(marker);
        tokens.add("(");
        tokens.add(")");

        return tokens;

    }
}

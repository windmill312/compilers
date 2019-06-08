import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RelationCreator {

    public static Map<String, Map<String, String>> getRelations() {
        Set<String> tokens = DataConstructor.getTokens();

        final Map<String, Map<String, String>> relations = new HashMap<>();

        for (String value1 : tokens) {
            Map<String, String> relationValue = new HashMap<>();
            for (String value2 : tokens) {
                relationValue.put(value2, "");
                relations.put(value1, relationValue);
            }
        }

        relations.put("(", createMap("(",")", "=", relations));
        relations.put("$", createMap("$","(", "<", relations));
        relations.put("(", createMap("(","(", "<", relations));
        relations.put(")", createMap(")","$", ">", relations));
        relations.put(")", createMap(")",")", ">", relations));

        Set<String> constants = DataConstructor.getConstants();
        for (String constant: constants) {
            relations.put("$", createMap("$", constant, "<", relations));
            relations.put("(", createMap("(", constant, "<", relations));
            relations.put(constant, createMap(constant, "$", ">", relations));
            relations.put(constant, createMap(constant, ")", ">", relations));
        }

        Map<String, Integer> precedences = DataConstructor.getPrecedences();
        precedences.forEach((precKey1, precValue1) -> {
            relations.put(precKey1, createMap(precKey1, "$", ">", relations));
            relations.put("$", createMap("$", precKey1, "<", relations));
            relations.put(precKey1, createMap(precKey1, "(", "<", relations));
            relations.put("(", createMap("(", precKey1, "<", relations));
            relations.put(precKey1, createMap(precKey1, ")", ">", relations));
            relations.put(")", createMap(")", precKey1, ">", relations));

            for (String constant : constants) {
                relations.put(precKey1, createMap(precKey1, constant, "<", relations));
                relations.put(constant, createMap(constant, precKey1, ">", relations));
            }

            if (precKey1.equals(DataConstructor.prefix)) {
                precedences.forEach((precKey2, precValue2) -> {
                    relations.put(precKey2, createMap(precKey2, precKey1, "<", relations));
                    if (precValue1 > precValue2) {
                        relations.put(precKey1, createMap(precKey1, precKey2, ">", relations));
                    } else {
                        relations.put(precKey1, createMap(precKey1, precKey2, "<", relations));
                    }
                });
            } else {
                precedences.forEach((precKey2, precValue2) -> {
                    if (precValue1 < precValue2) {
                        relations.put(precKey1, createMap(precKey1, precKey2, "<", relations));
                    } else {
                        relations.put(precKey1, createMap(precKey1, precKey2, ">", relations));
                    }
                });
            }
        });

        return relations;
    }

    private static Map<String, String> createMap(
            String key1,
            String key2,
            String value,
            Map<String, Map<String, String>> relations) {
        Map<String, String> relationValue = relations.get(key1);
        relationValue.put(key2, value);
        return relationValue;
    }
}
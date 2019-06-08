package com.windmill312;

import com.windmill312.compiler.PythonEvaluator;
import com.windmill312.compiler.PythonLexer;
import com.windmill312.compiler.PythonParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Application {

    public static final String SCRIPT = "listInversion.py";
    private static final String RESOURCES_PATH = "src/main/resources/application.properties";

    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        prop.load(new FileReader(RESOURCES_PATH));

        CharStream input = CharStreams.fromFileName(prop.getProperty("samples-path") + SCRIPT);
        PythonLexer lexer = new PythonLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PythonParser parser = new PythonParser(tokens);
        PythonParser.ProgContext tree = parser.prog();
        ParseTreeWalker walker = new ParseTreeWalker();

        if (tree.stop.getText().equals(tree.EOF().toString())) {
            PythonEvaluator pythonEvaluator = new PythonEvaluator(parser);
            walker.walk(pythonEvaluator, tree);
        }
    }
}
